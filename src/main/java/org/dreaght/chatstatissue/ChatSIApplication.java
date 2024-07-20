package org.dreaght.chatstatissue;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.dreaght.chatstatissue.controller.InitWindowController;
import org.dreaght.chatstatissue.controller.OverlayController;
import org.dreaght.chatstatissue.controller.SettingsController;
import org.dreaght.chatstatissue.handler.FadeOutHandler;
import org.dreaght.chatstatissue.handler.RobotHandler;
import org.dreaght.chatstatissue.chat.ChatManager;

public class ChatSIApplication extends Application implements ApplicationHandler {
    Stage primaryStage;
    Stage settingsStage;
    Stage initWindowStage;
    InitWindowController initWindowController;
    SettingsController settingsController;
    OverlayController overlayController;
    private ChatManager chatManager;
    private RobotHandler robotHandler;
    private FadeOutHandler fadeOutHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        chatManager = new ChatManager(this);
        robotHandler = new RobotHandler();

        // Initialize settings before fadeOutHandler!
        initializeSettings();
        fadeOutHandler = new FadeOutHandler(settingsStage);

        initializeOverlay();
        initializeInitWindow();
        setupPrimaryStageFocusListener();
        initializeGlobalKeyListener();
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public void toggleSettings(boolean show) {
        fadeOutHandler.toggleSettings(show, settingsStage, settingsController);
    }

    public void startFadeOut() {
        fadeOutHandler.startFadeOut();
    }

    public void stopFadeOut() {
        fadeOutHandler.stopFadeOut();
    }

    private void initializeInitWindow() throws Exception {
        InitWindowInitializer.initializeInitWindow(this);
    }

    private void setupPrimaryStageFocusListener() {
        primaryStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                robotHandler.simulateAltTab();
            }
        });
    }

    private void initializeGlobalKeyListener() {
        GlobalKeyListenerInitializer.initializeGlobalKeyListener(this);
    }

    private void initializeOverlay() throws Exception {
        OverlayInitializer.initializeOverlay(this);
    }

    private void initializeSettings() throws Exception {
        SettingsInitializer.initializeSettings(this);
    }

    public void addMessageToOverlay(String message) {
        Platform.runLater(() -> overlayController.addMessage(message));
    }

    public void closeSettings() {
        Platform.runLater(() -> settingsStage.close());
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        GlobalKeyListenerInitializer.unregisterGlobalKeyListener();

        if (primaryStage != null) {
            Platform.runLater(primaryStage::close);
        }
        if (settingsStage != null) {
            Platform.runLater(settingsStage::close);
        }
        if (initWindowStage != null) {
            Platform.runLater(initWindowStage::close);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
