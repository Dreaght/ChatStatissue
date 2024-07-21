package org.dreaght.chatstatissue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dreaght.chatstatissue.controller.init.InitWindowController;

public class InitWindowInitializer {

    public static void initializeInitWindow(ChatSIApplication application) throws Exception {
        System.out.println("Init window initializes");

        FXMLLoader initLoader = new FXMLLoader(application.getClass().getResource("/org/dreaght/chatstatissue/init/init_window.fxml"));
        Pane initRoot = initLoader.load();
        InitWindowController initWindowController = initLoader.getController();
        initWindowController.setApplication(application);

        Scene initScene = new Scene(initRoot);
        initScene.getStylesheets().add(application.getClass().getResource("/org/dreaght/chatstatissue/styles.css").toExternalForm());
        initScene.setFill(null);

        Stage initWindowStage = new Stage(StageStyle.TRANSPARENT);
        initWindowStage.setScene(initScene);
        initWindowStage.initStyle(StageStyle.TRANSPARENT);

        initWindowController.setStage(initWindowStage);

        // Set the instance variables in the application instance
        application.initWindowController = initWindowController;
        application.initWindowStage = initWindowStage;

        initWindowStage.show();
    }
}
