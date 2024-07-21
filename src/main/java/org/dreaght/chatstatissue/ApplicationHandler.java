package org.dreaght.chatstatissue;

import javafx.scene.image.Image;

public interface ApplicationHandler {
    void addMessageToOverlay(String message);
    void updateScreenView(Image image);
    void closeScreenshare();
    void toggleSettings(boolean show);
    void closeSettings();
    void stopFadeOut();
    void startListeningVoice();
    void stopListening();
}
