package org.dreaght.chatstatissue.listener;

import javafx.application.Platform;
import org.dreaght.chatstatissue.ChatSIApplication;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class SettingsKeyListener implements NativeKeyListener {

    private final ChatSIApplication application;

    public SettingsKeyListener(ChatSIApplication application) {
        this.application = application;
    }

    private boolean altPressed = false;
    private boolean xPressed = false;

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        // Nothing here.
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            altPressed = true;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_X) {
            xPressed = true;
        }
        if (altPressed && xPressed) {
            Platform.runLater(() -> application.toggleSettings(true));
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            Platform.runLater(() -> application.toggleSettings(false));
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            altPressed = false;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_X) {
            xPressed = false;
        }
        if (!altPressed || !xPressed) {
            Platform.runLater(() -> application.startFadeOut());
        }
    }
}
