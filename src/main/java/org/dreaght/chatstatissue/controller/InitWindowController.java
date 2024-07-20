package org.dreaght.chatstatissue.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.dreaght.chatstatissue.ChatSIApplication;
import org.dreaght.chatstatissue.chat.ChatManager;

public class InitWindowController implements Initializable {

    @FXML
    private Button startServerButton;

    @FXML
    private Button connectServerButton;

    private ChatSIApplication application;
    private ChatManager chatManager;

    public void setApplication(ChatSIApplication application) {
        this.application = application;
        this.chatManager = application.getChatManager();
    }

    @FXML
    private void onStartServer() {
        TextInputDialog dialog = new TextInputDialog("12345");
        dialog.setTitle("Start Server");
        dialog.setHeaderText("Enter Port Number");
        dialog.setContentText("Port:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(port -> {
            try {
                int portNumber = Integer.parseInt(port);
                chatManager.startServer(portNumber);
                application.toggleSettings(true);
                closeInitWindow();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void onConnectServer() {
        TextInputDialog addressDialog = new TextInputDialog("localhost");
        addressDialog.setTitle("Connect to Server");
        addressDialog.setHeaderText("Enter Server Address");
        addressDialog.setContentText("Address:");

        Optional<String> addressResult = addressDialog.showAndWait();

        TextInputDialog portDialog = new TextInputDialog("12345");
        portDialog.setTitle("Connect to Server");
        portDialog.setHeaderText("Enter Port Number");
        portDialog.setContentText("Port:");

        Optional<String> portResult = portDialog.showAndWait();

        if (addressResult.isPresent() && portResult.isPresent()) {
            try {
                String address = addressResult.get();
                int portNumber = Integer.parseInt(portResult.get());
                chatManager.connectToServer(address, portNumber);
                application.toggleSettings(false);
                closeInitWindow();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeInitWindow() {
        Stage stage = (Stage) startServerButton.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setStage(Stage stage) {
        if (stage != null) {
            Scene scene = stage.getScene();
            if (scene != null) {
                scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        closeInitWindow();
                        if (!chatManager.isSessionInitialized()) {
                            try {
                                application.stop();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        }
    }
}
