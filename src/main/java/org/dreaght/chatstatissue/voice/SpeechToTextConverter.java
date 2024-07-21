package org.dreaght.chatstatissue.voice;

public interface SpeechToTextConverter {
    String convert(byte[] audioData);

    void close();
}
