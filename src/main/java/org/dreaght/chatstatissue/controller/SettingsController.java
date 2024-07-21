package org.dreaght.chatstatissue.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.dreaght.chatstatissue.ApplicationHandler;
import org.dreaght.chatstatissue.ChatSIApplication;
import org.dreaght.chatstatissue.InitWindowInitializer;
import org.dreaght.chatstatissue.chat.ChatManager;

public class SettingsController {

    @FXML
    private TextField textField;

    @FXML
    private Button reconnectButton;

    private boolean userTyped = false;
    private ChatManager chatManager;
    private ApplicationHandler applicationHandler;

    private boolean screenshareEnabled = false;
    private boolean listeningVoice = false;

    @FXML
    private void initialize() {
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            userTyped = true;
            textField.getScene().getWindow().setOpacity(1);

            if (applicationHandler != null) {
                applicationHandler.stopFadeOut();
            }

            switch (event.getCode()) {
                case ENTER:
                    String message = textField.getText();
                    if (!message.trim().isEmpty()) {
                        String finalMessage = chatManager.sendMessage(message);
                        applicationHandler.addMessageToOverlay(finalMessage);
                        textField.clear();
                    }
                    break;
                case ESCAPE:
                    applicationHandler.toggleSettings(false);
                    break;
            }
        });
    }

    @FXML
    private void onReconnectButtonPress() {
        openInitWindow();
    }

    @FXML
    private void onScreenshareButton() throws Exception {
        screenshareEnabled = !screenshareEnabled;

        if (screenshareEnabled) {
            chatManager.startScreenSharing();
            ((ChatSIApplication) applicationHandler).initializeScreenShare();
        } else {
            chatManager.stopScreenSharing();
            applicationHandler.closeScreenshare();
        }

    }

    @FXML
    private void onVoiceButton() {
        listeningVoice = !listeningVoice;

        if (listeningVoice) {
            applicationHandler.startListeningVoice();
        } else {
            applicationHandler.stopListening();
        }
    }

    private void openInitWindow() {
        try {
            InitWindowInitializer.initializeInitWindow((ChatSIApplication) applicationHandler);
            ((ChatSIApplication) applicationHandler).closeSettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activateTextField() {
        Platform.runLater(() -> textField.requestFocus());
    }

    public boolean hasUserTyped() {
        return userTyped;
    }

    public void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    public void setApplicationHandler(ApplicationHandler applicationHandler) {
        this.applicationHandler = applicationHandler;
    }
}
