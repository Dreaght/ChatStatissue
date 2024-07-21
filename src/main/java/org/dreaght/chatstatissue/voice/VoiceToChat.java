package org.dreaght.chatstatissue.voice;

import org.dreaght.chatstatissue.ChatSIApplication;
import org.dreaght.chatstatissue.chat.ChatManager;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class VoiceToChat {

    private AudioCapture audioCapture;
    private SpeechToTextConverter speechToTextConverter;
    private ChatManager chatManager;
    private ChatSIApplication application;

    public VoiceToChat(ChatSIApplication application, ChatManager chatManager) throws IOException {
        this.application = application;
        this.chatManager = chatManager;
        audioCapture = new AudioCapture();
        speechToTextConverter = null;
    }

    public void startListening() {
        try {
            audioCapture.startCapture();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (true) {
                byte[] audioData = audioCapture.getCapturedAudio();
                if (audioData.length > 0) {
                    String text = speechToTextConverter.convert(audioData);
                    application.addMessageToOverlay(text);
                    chatManager.sendMessage(text);
                }
                try {
                    Thread.sleep(5000); // audio interval
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopListening() {
        audioCapture.stopCapture();

        if (speechToTextConverter != null)
            speechToTextConverter.close();
    }
}
