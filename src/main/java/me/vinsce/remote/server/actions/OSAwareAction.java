package me.vinsce.remote.server.actions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class OSAwareAction<R> implements Action<R> {
    protected OSFamily os = getOSFamily();

    @Override
    public final R execute() {
        log.debug("Executing {} for OS '{}'", getClass().getSimpleName(), os);
        switch (os) {
            case LINUX:
                return executeForLinux();
            case WINDOWS:
                return executeForWindows();
            case MAC:
                return executeForMac();
            default:
                return executeForUnknown();
        }
    }

    protected abstract R executeForLinux();

    protected abstract R executeForWindows();

    protected abstract R executeForMac();

    protected R executeForUnknown() {
        throw new UnsupportedOperationException("Action not available for OS: " + System.getProperty("os.name"));
    }

    private static OSFamily getOSFamily() {
        String osName = System.getProperty("os.name");

        for (OSFamily value : OSFamily.values()) {
            for (String prefix : value.PREFIXES) {
                if (osName != null && osName.startsWith(prefix))
                    return value;
            }
        }

        return OSFamily.UNKNOWN;
    }

    @RequiredArgsConstructor
    @Getter
    private enum OSFamily {
        UNKNOWN(List.of()),
        LINUX(List.of("Linux", "LINUX")),
        WINDOWS(List.of("Windows")),
        MAC(List.of("Mac"));

        private final List<String> PREFIXES;
    }
}
