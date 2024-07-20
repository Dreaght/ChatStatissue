package org.dreaght.chatstatissue.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dreaght.chatstatissue.ApplicationHandler;
import org.dreaght.chatstatissue.ChatSIApplication;
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

        reconnectButton.setOnAction(event -> {
            openInitWindow();
        });
    }

    private void openInitWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dreaght/chatstatissue/init_window.fxml"));
            Pane initRoot = loader.load();
            InitWindowController controller = loader.getController();
            controller.setApplication((ChatSIApplication) applicationHandler);

            Stage initWindowStage = new Stage(StageStyle.TRANSPARENT);
            initWindowStage.setScene(new Scene(initRoot));
            initWindowStage.initStyle(StageStyle.UNDECORATED);
            initWindowStage.setTitle("Initialize Chat");

            controller.setStage(initWindowStage);

            initWindowStage.show();

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
