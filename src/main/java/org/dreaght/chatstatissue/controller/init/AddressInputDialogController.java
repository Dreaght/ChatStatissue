package org.dreaght.chatstatissue.controller.init;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Optional;

public class AddressInputDialogController implements InputDialogController {

    @FXML
    private TextField addressTextField;

    private Optional<String> result = Optional.empty();

    @FXML
    private void initialize() {
        addressTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                closeDialog();
            }
        });
    }

    @FXML
    private void onOk(KeyEvent event) {
        if (!event.getCode().equals(KeyCode.ENTER)) return;

        result = Optional.of(addressTextField.getText());
        closeDialog();
    }

    @Override
    public Optional<String> getResult() {
        return result;
    }

    @Override
    public void setHeaderText(String text) {
    }

    private void closeDialog() {
        Stage stage = (Stage) addressTextField.getScene().getWindow();
        stage.close();
    }
}
