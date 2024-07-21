package org.dreaght.chatstatissue.chat;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class ChatClient extends WebSocketClient {

    private ChatManager chatManager;

    public ChatClient(URI serverUri, ChatManager chatManager) {
        super(serverUri);
        this.chatManager = chatManager;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server");
    }

    @Override
    public void onMessage(String message) {
        chatManager.onMessageReceived(message);
    }

    public void onMessage(byte[] data, boolean isBinary) {
        if (isBinary) {
            chatManager.onScreenShareReceived(data);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from server");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendMessage(String message) {
        send(message);
    }

    public void sendScreenFrame(byte[] data) {
        send(data); // Send as binary
    }
}
