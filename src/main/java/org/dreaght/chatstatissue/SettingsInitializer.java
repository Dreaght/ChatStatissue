package org.dreaght.chatstatissue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class SettingsInitializer {

    public static void initializeSettings(ChatSIApplication application) throws Exception {
        FXMLLoader settingsLoader = new FXMLLoader(application.getClass().getResource("settings.fxml"));
        Pane settingsRoot = settingsLoader.load();
        application.settingsController = settingsLoader.getController();
        Scene settingsScene = new Scene(settingsRoot);

        application.settingsStage = new Stage(StageStyle.TRANSPARENT);
        application.settingsStage.setAlwaysOnTop(true);
        application.settingsStage.setScene(settingsScene);
        application.settingsStage.setUserData(application);

        settingsScene.getAccelerators().put(KeyCombination.keyCombination("ESC"), application::closeSettings);

        application.settingsController.setApplicationHandler(application);
        application.settingsController.setChatManager(application.getChatManager());
    }
}
