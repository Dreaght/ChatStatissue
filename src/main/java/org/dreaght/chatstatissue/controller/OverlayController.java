package org.dreaght.chatstatissue.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class OverlayController {

    @FXML
    private ListView<String> messageList;

    public void addMessage(String message) {
        messageList.getItems().add(message);
        messageList.scrollTo(messageList.getItems().size() - 1);
    }

    @FXML
    private void initialize() {
        messageList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    Text text = new Text(item);
                    text.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial'; -fx-fill: white;");
                    text.setMouseTransparent(true);
                    text.setOpacity(0.8);

                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setOffsetX(2.0);
                    dropShadow.setOffsetY(2.0);
                    dropShadow.setColor(Color.BLACK);
                    text.setEffect(dropShadow);

                    setGraphic(text);
                }
            }
        });
    }
}
