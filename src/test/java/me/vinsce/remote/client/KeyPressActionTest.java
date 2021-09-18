package me.vinsce.remote.client;

import me.vinsce.remote.server.actions.KeyPressAction;

public class KeyPressActionTest {
    public static void main(String[] args) {
        new KeyPressAction(KeyPressAction.Key.VOLUME_UP).execute();
    }
}
