package org.dreaght.chatstatissue.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ScreenshareController {
    @FXML
    private ImageView screenView;

    public void updateImageView(Image image) {
        screenView.setImage(image);
    }

    public void closeWindow() {
        ((Stage) screenView.getScene().getWindow()).close();
    }
}
