package me.vinsce.remote.server.actions.power;

import lombok.SneakyThrows;
import me.vinsce.remote.server.actions.OSAwareAction;

public class ShutdownAction extends OSAwareAction<Void> {

    @SneakyThrows
    @Override
    protected Void executeForLinux() {
        try {
            Runtime.getRuntime().exec("systemctl poweroff");
        } catch (Exception e) {
            // Note: shutdown requires super user
            Runtime.getRuntime().exec("shutdown -h now");
        }
        return null;
    }

    @SneakyThrows
    @Override
    protected Void executeForWindows() {
        Runtime.getRuntime().exec("shutdown -s -t 0");
        return null;
    }

    @SneakyThrows
    @Override
    protected Void executeForMac() {
        Runtime.getRuntime().exec("shutdown -h now");
        return null;
    }
}
