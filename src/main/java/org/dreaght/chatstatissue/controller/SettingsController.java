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
                        applicationHandler.addMessageToOverlay(message);
                        chatManager.sendMessage(message);
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
