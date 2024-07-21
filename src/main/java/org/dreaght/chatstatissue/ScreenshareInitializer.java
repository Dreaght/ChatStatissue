package org.dreaght.chatstatissue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScreenshareInitializer {

    public static void initializeScreenshare(ChatSIApplication application) throws Exception {
        FXMLLoader screenshareLoader = new FXMLLoader(application.getClass().getResource("screenshare.fxml"));
        AnchorPane screenshareRoot = screenshareLoader.load();
        application.screenshareController = screenshareLoader.getController();

        application.screenshareStage = new Stage();
        application.screenshareStage.initStyle(StageStyle.TRANSPARENT);
        application.screenshareStage.setAlwaysOnTop(true);
        application.screenshareStage.setScene(new Scene(screenshareRoot));
        application.screenshareStage.setWidth(300);
        application.screenshareStage.setHeight(200);

        application.screenshareStage.setResizable(true);

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double windowWidth = application.screenshareStage.getWidth();
        double windowHeight = application.screenshareStage.getHeight();

        application.screenshareStage.setX(screenWidth - windowWidth - 50);
        application.screenshareStage.setY(screenHeight - windowHeight - 50);

        application.screenshareStage.show();
    }
}