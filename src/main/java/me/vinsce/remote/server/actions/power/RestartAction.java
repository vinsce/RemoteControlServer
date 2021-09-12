package me.vinsce.remote.server.actions.power;

import lombok.SneakyThrows;
import me.vinsce.remote.server.actions.OSAwareAction;

public class RestartAction extends OSAwareAction<Void> {

    @SneakyThrows
    @Override
    protected Void executeForLinux() {
        Runtime.getRuntime().exec("shutdown -r now");
        return null;
    }

    @SneakyThrows
    @Override
    protected Void executeForWindows() {
        Runtime.getRuntime().exec("shutdown -r -t 0");
        return null;
    }

    @Override
    protected Void executeForMac() {
        return executeForLinux();
    }
}
