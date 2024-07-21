package org.dreaght.chatstatissue.controller.init;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dreaght.chatstatissue.ChatSIApplication;
import org.dreaght.chatstatissue.chat.ChatManager;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InitWindowController implements Initializable {

    @FXML
    private Button startServerButton;

    @FXML
    private Button connectServerButton;

    private ChatSIApplication application;
    private ChatManager chatManager;
    private Stage stage;

    public void setApplication(ChatSIApplication application) {
        this.application = application;
        this.chatManager = application.getChatManager();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        if (stage != null) {
            Scene scene = stage.getScene();
            if (scene != null) {
                scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        hideInitWindow();
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

    @FXML
    private void onStartServer() {
        Optional<String> result = showPortInputDialog("Start Server", "Enter Port Number:");
        result.ifPresent(port -> {
            try {
                int portNumber = Integer.parseInt(port);
                chatManager.startServer(portNumber);
                application.toggleSettings(true);
                hideInitWindow();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void onConnectServer() {
        Optional<String> addressResult = showAddressInputDialog("Connect to Server", "Enter Server Address:");

        if (addressResult.isEmpty()) return;

        Optional<String> portResult = showPortInputDialog("Connect to Server", "Enter Port Number:");

        if (portResult.isPresent()) {
            try {
                String address = addressResult.get();
                int portNumber = Integer.parseInt(portResult.get());
                chatManager.connectToServer(address, portNumber);
                application.toggleSettings(false);
                hideInitWindow();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private Optional<String> showPortInputDialog(String title, String header) {
        hideInitWindow();
        return showInputDialog("/org/dreaght/chatstatissue/init/port_input_dialog.fxml", title, header);
    }

    private Optional<String> showAddressInputDialog(String title, String header) {
        hideInitWindow();
        return showInputDialog("/org/dreaght/chatstatissue/init/address_input_dialog.fxml", title, header);
    }

    private Optional<String> showInputDialog(String fxmlFile, String title, String header) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Stage dialogStage = new Stage(StageStyle.TRANSPARENT);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle(title);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(application.getClass().getResource("/org/dreaght/chatstatissue/styles.css").toExternalForm());
            scene.setFill(null); // Ensure the scene has a transparent fill color
            dialogStage.setScene(scene);
            InputDialogController controller = loader.getController();
            controller.setHeaderText(header);
            dialogStage.showAndWait();
            return controller.getResult();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private void hideInitWindow() {
        if (stage != null) {
            stage.hide();
        }
    }

    public void showInitWindow() {
        if (stage != null) {
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
