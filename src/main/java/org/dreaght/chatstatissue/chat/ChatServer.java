package org.dreaght.chatstatissue.chat;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.Collection;

public class ChatServer extends WebSocketServer {

    public ChatServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Client connected: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message: " + message);
        broadcastMessage(message);
    }

    public void onMessage(WebSocket conn, byte[] data, boolean isBinary) {
        if (isBinary) {
            broadcastScreenFrame(data);
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client disconnected: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully");
    }

    public void broadcastMessage(String message) {
        Collection<WebSocket> connections = getConnections();
        for (WebSocket client : connections) {
            client.send(message);
        }
    }

    public void broadcastScreenFrame(byte[] data) {
        Collection<WebSocket> connections = getConnections();
        for (WebSocket client : connections) {
            client.send(data);
        }
    }
}
