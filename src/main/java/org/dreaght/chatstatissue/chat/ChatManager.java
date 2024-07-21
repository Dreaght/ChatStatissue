package org.dreaght.chatstatissue.chat;

import javafx.application.Platform;
import javafx.scene.image.Image;
import org.dreaght.chatstatissue.ChatSIApplication;
import org.dreaght.chatstatissue.util.ScreenCaptureUtil;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class ChatManager {

    private ChatSIApplication application;
    private ChatClient chatClient;
    private ChatServer chatServer;
    private boolean isServerMode = false;
    private boolean isScreenSharing = false;

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

    public String sendMessage(String message) {
        String finalMessage = message;
        if (chatClient != null) {
            if (!(chatClient.getSocket().getInetAddress() == null)) {
                finalMessage = chatClient.getSocket().getInetAddress().getHostAddress() + ": " + message;
            }

            chatClient.sendMessage(finalMessage);
        } else if (chatServer != null) {
            finalMessage = chatServer.getAddress().getAddress().getHostAddress() + ": " + message;
            chatServer.broadcastMessage(finalMessage);
        }
        return finalMessage;
    }

    public void startScreenSharing() {
        isScreenSharing = true;
        new Thread(this::sendScreenFrames).start();
    }

    public void stopScreenSharing() {
        isScreenSharing = false;
    }

    private void sendScreenFrames() {
        while (isScreenSharing) {
            byte[] screenCapture = ScreenCaptureUtil.captureScreen();

            if (screenCapture != null) {
                showPreview(screenCapture);

                if (chatClient != null) {
                    chatClient.sendScreenFrame(screenCapture);
                } else if (chatServer != null) {
                    chatServer.broadcastScreenFrame(screenCapture);
                }
            }
            try {
                Thread.sleep(100); // frame rate control
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showPreview(byte[] screenCapture) {
        ByteArrayInputStream bais = new ByteArrayInputStream(screenCapture);
        Image image = new Image(bais);
        application.updateScreenView(image);
    }

    public void onMessageReceived(String message) {
        Platform.runLater(() -> {
            application.addMessageToOverlay(message);
        });
    }

    public void onScreenShareReceived(byte[] imageData) {
        System.out.println("screenshare received");
        Platform.runLater(() -> {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
            Image image = new Image(bais);
            application.updateScreenView(image);
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
