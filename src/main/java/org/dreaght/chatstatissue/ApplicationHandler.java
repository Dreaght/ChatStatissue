package org.dreaght.chatstatissue;

public interface ApplicationHandler {
    void addMessageToOverlay(String message);
    void toggleSettings(boolean show);
    void stopFadeOut();
}
