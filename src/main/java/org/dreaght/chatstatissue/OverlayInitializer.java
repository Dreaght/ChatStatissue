package org.dreaght.chatstatissue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;

class OverlayInitializer {

    public static void initializeOverlay(ChatSIApplication application) throws Exception {
        FXMLLoader overlayLoader = new FXMLLoader(application.getClass().getResource("overlay.fxml"));
        Pane overlayRoot = overlayLoader.load();
        application.overlayController = overlayLoader.getController();
        Scene overlayScene = new Scene(overlayRoot);
        overlayScene.setFill(null);

        application.primaryStage.initStyle(StageStyle.TRANSPARENT);
        application.primaryStage.setAlwaysOnTop(true);
        application.primaryStage.setScene(overlayScene);
        application.primaryStage.setX(0);
        application.primaryStage.setY(0);
        application.primaryStage.show();

        overlayRoot.setOnMouseClicked(MouseEvent::consume);
    }
}
