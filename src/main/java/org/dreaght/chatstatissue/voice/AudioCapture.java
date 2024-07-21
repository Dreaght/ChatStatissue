package org.dreaght.chatstatissue.voice;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AudioCapture {

    private TargetDataLine microphone;
    private AudioFormat format;
    private boolean running;

    public AudioCapture() {
        format = new AudioFormat(16000, 16, 1, true, true);
    }

    public void startCapture() throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();
        running = true;

        Thread captureThread = new Thread(() -> {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (running) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                out.write(buffer, 0, bytesRead);
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        captureThread.start();
    }

    public void stopCapture() {
        running = false;
        microphone.stop();
        microphone.close();
    }

    public byte[] getCapturedAudio() {
        return new byte[0];
    }
}
