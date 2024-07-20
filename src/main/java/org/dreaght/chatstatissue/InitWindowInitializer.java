package org.dreaght.chatstatissue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dreaght.chatstatissue.controller.InitWindowController;

class InitWindowInitializer {

    public static void initializeInitWindow(ChatSIApplication application) throws Exception {
        FXMLLoader initLoader = new FXMLLoader(application.getClass().getResource("init_window.fxml"));
        Pane initRoot = initLoader.load();
        InitWindowController initWindowController = initLoader.getController();
        initWindowController.setApplication(application);

        Scene initScene = new Scene(initRoot);
        Stage initWindowStage = new Stage(StageStyle.TRANSPARENT);
        initWindowStage.setScene(initScene);
        initWindowStage.initStyle(StageStyle.UNDECORATED);
        initWindowStage.setTitle("Initialize Chat");
        initWindowStage.show();

        initWindowController.setStage(initWindowStage);

        // Set the instance variables in the application instance
        application.initWindowController = initWindowController;
        application.initWindowStage = initWindowStage;
    }
}
