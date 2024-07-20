package org.dreaght.chatstatissue.handler;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.dreaght.chatstatissue.controller.SettingsController;

public class FadeOutHandler {
    private Timeline fadeOutTimeline;

    public FadeOutHandler(Stage settingsStage) {
        initFadeOutTimeline(settingsStage);
    }

    private void initFadeOutTimeline(Stage settingsStage) {
        fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), new KeyValue(settingsStage.opacityProperty(), 0))
        );
        fadeOutTimeline.setOnFinished(event -> settingsStage.hide());
    }

    public void toggleSettings(boolean show, Stage settingsStage, SettingsController settingsController) {
        if (show) {
            fadeOutTimeline.stop();
            settingsStage.setOpacity(1);
            settingsStage.show();
            settingsController.activateTextField();
        } else {
            if (!settingsController.hasUserTyped()) {
                startFadeOut();
            } else {
                settingsStage.hide();
            }
        }
    }

    public void startFadeOut() {
        fadeOutTimeline.playFromStart();
    }

    public void stopFadeOut() {
        fadeOutTimeline.stop();
    }
}
