package me.vinsce.remote.server.actions;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class KeyPressAction extends OSAwareAction<Void> {

    private static final String WINDOWS_PS_PRESS_KEY_CMD_FORMAT = "powershell.exe -Command $(New-Object -ComObject WScript.Shell).SendKeys([char]%s)";

    // Requires `xdotool` installed
    private static final String LINUX_PRESS_KEY_CMD_FORMAT = "xdotool key %s";
    private static final String MAC_PRESS_KEY_CMD_FORMAT = "";

    private final Key key;

    @SneakyThrows
    @Override
    protected Void executeForLinux() {
        Runtime.getRuntime().exec(String.format(LINUX_PRESS_KEY_CMD_FORMAT, key.linuxCode));
        return null;
    }

    @SneakyThrows
    @Override
    protected Void executeForWindows() {
        Runtime.getRuntime().exec(String.format(WINDOWS_PS_PRESS_KEY_CMD_FORMAT, key.windowsCode));
        return null;
    }

    @Override
    protected Void executeForMac() {
        return executeForUnknown();
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", getClass().getSimpleName(), key);
    }

    @RequiredArgsConstructor
    public enum Key {
        // Windows reference: https://docs.microsoft.com/en-us/windows/win32/inputdev/virtual-key-codes
        VOLUME_UP("XF86AudioRaiseVolume", "0xAF", null),
        VOLUME_DOWN("XF86AudioLowerVolume", "0xAE", null),
        VOLUME_MUTE("XF86AudioMute", "0xAD", null),
        MEDIA_PLAY_PAUSE("XF86AudioPlay", "0xB3", null),
        MEDIA_STOP("XF86AudioStop", "0xB2", null),
        MEDIA_NEXT_TRACK("XF86AudioNext", "0xB0", null),
        MEDIA_PREV_TRACK("XF86AudioPrev", "0xB1", null);

        private final String linuxCode;
        private final String windowsCode;
        private final String macCode;
    }
}
