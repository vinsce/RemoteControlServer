package me.vinsce.remote.server.actions;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class KeyPressAction extends OSAwareAction<Void> {

    private static final String WINDOWS_PS_PRESS_KEY_CMD_FORMAT = "powershell.exe -Command $(New-Object -ComObject WScript.Shell).SendKeys(%s)";

    // Requires `xdotool` installed
    private static final String LINUX_PRESS_KEY_CMD_FORMAT = "";
    private static final String MAC_PRESS_KEY_CMD_FORMAT = "";

    private final Key key;

    @Override
    protected Void executeForLinux() {
        return executeForUnknown();
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

    @RequiredArgsConstructor
    public enum Key {
        VOLUME_UP(null, "[char]0xAF", null),
        VOLUME_DOWN(null, "[char]0xAE", null),
        VOLUME_MUTE(null, "[char]0xAD", null),
        MEDIA_PLAY_PAUSE(null, "[char]0xB3", null),
        MEDIA_STOP(null, "[char]0xB2", null),
        MEDIA_NEXT_TRACK(null, "[char]0xB0", null),
        MEDIA_PREV_TRACK(null, "[char]0xB1", null);

        private final String linuxCode;
        private final String windowsCode;
        private final String macCode;
    }
}
