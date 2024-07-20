package org.dreaght.chatstatissue.chat;

import javafx.application.Platform;
import org.dreaght.chatstatissue.ChatSIApplication;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatManager {

    private ChatSIApplication application;
    private ChatClient chatClient;
    private ChatServer chatServer;
    private boolean isServerMode = false;

    public ChatManager(ChatSIApplication application) {
        this.application = application;
    }

    public boolean isSessionInitialized() {
        return chatServer != null || chatClient != null;
    }

    public void startServer(int port) {
        stopCurrentSession();
        chatServer = new ChatServer(port);
        chatServer.start();
        isServerMode = true;
    }

    public void connectToServer(String address, int port) {
        stopCurrentSession();
        try {
            URI serverUri = new URI("ws://" + address + ":" + port);
            chatClient = new ChatClient(serverUri, this);
            chatClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (chatClient != null) {
            chatClient.send(message);
        } else if (chatServer != null) {
            chatServer.broadcast(message);
        }
    }

    public void onMessageReceived(String message) {
        Platform.runLater(() -> {
            application.addMessageToOverlay(message);
        });
    }

    public void stop() throws InterruptedException {
        stopCurrentSession();
    }

    private void stopCurrentSession() {
        if (chatClient != null) {
            chatClient.close();
            chatClient = null;
        }
        if (chatServer != null) {
            try {
                chatServer.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            chatServer = null;
        }
        isServerMode = false;
    }
}
