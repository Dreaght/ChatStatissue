package org.dreaght.chatstatissue;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.dreaght.chatstatissue.chat.ChatManager;
import org.dreaght.chatstatissue.controller.ScreenshareController;
import org.dreaght.chatstatissue.controller.init.InitWindowController;
import org.dreaght.chatstatissue.controller.OverlayController;
import org.dreaght.chatstatissue.controller.SettingsController;
import org.dreaght.chatstatissue.handler.FadeOutHandler;
import org.dreaght.chatstatissue.handler.RobotHandler;
import org.dreaght.chatstatissue.voice.VoiceToChat;

public class ChatSIApplication extends Application implements ApplicationHandler {

    Stage primaryStage;
    Stage settingsStage;
    Stage initWindowStage;
    Stage screenshareStage;

    InitWindowController initWindowController;
    SettingsController settingsController;
    OverlayController overlayController;
    ScreenshareController screenshareController;

    private ChatManager chatManager;
    private RobotHandler robotHandler;
    private FadeOutHandler fadeOutHandler;
    private VoiceToChat voiceToChat;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        chatManager = new ChatManager(this);
        robotHandler = new RobotHandler();
        voiceToChat = new VoiceToChat(this, chatManager);

        initializeSettings();
        fadeOutHandler = new FadeOutHandler(settingsStage);

        initializeOverlay();
        initializeInitWindow();
        initializeGlobalKeyListener();
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    @Override
    public void toggleSettings(boolean show) {
        fadeOutHandler.toggleSettings(show, settingsStage, settingsController);
    }

    public void startFadeOut() {
        fadeOutHandler.startFadeOut();
    }

    @Override
    public void stopFadeOut() {
        fadeOutHandler.stopFadeOut();
    }

    @Override
    public void startListeningVoice() {
        voiceToChat.startListening();
    }

    @Override
    public void stopListening() {
        voiceToChat.stopListening();
    }

    private void initializeInitWindow() throws Exception {
        InitWindowInitializer.initializeInitWindow(this);
        initWindowController.showInitWindow();
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

    public void initializeScreenShare() throws Exception {
        ScreenshareInitializer.initializeScreenshare(this);
    }

    @Override
    public void addMessageToOverlay(String message) {
        Platform.runLater(() -> overlayController.addMessage(message));
    }

    @Override
    public void updateScreenView(Image image) {
        if (screenshareController != null) {
            screenshareController.updateImageView(image);
        }
    }

    @Override
    public void closeScreenshare() {
        if (screenshareStage != null) {
            screenshareStage.close();
        }
    }

    @Override
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
