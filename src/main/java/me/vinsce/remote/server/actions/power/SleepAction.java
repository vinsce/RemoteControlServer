package me.vinsce.remote.server.actions.power;

import lombok.SneakyThrows;
import me.vinsce.remote.server.actions.OSAwareAction;

public class SleepAction extends OSAwareAction<Void> {

    @SneakyThrows
    @Override
    protected Void executeForLinux() {
        Runtime.getRuntime().exec("pm-suspend");
        return null;
    }

    @SneakyThrows
    @Override
    protected Void executeForWindows() {
        Runtime.getRuntime().exec("rundll32.exe powrprof.dll, SetSuspendState Sleep");
        return null;
    }

    @SneakyThrows
    @Override
    protected Void executeForMac() {
        Runtime.getRuntime().exec("pmset sleepnow");
        return null;
    }
}
