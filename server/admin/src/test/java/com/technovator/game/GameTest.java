package com.technovator.game;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GameTest {

    @TestHTTPResource("/game/white")
    URI uriWhite;
    @TestHTTPResource("/game/black")
    URI uriBlack;
    @Test
    public void testWebsocketChat() throws Exception {
    	Session sessionWhite = ContainerProvider.getWebSocketContainer().connectToServer(ClientWhite.class, uriWhite);
    	Session sessionWhite1 = ContainerProvider.getWebSocketContainer().connectToServer(ClientWhite.class, uriWhite);
    	Session sessionBlack = ContainerProvider.getWebSocketContainer().connectToServer(ClientBlack.class, uriBlack);
    	Thread.sleep(1000);
    }
    @ClientEndpoint
    public static class ClientWhite {

        @OnOpen
        public void open(Session session) {
            session.getAsyncRemote().sendText("_ready_");
        }

        @OnMessage
        void message(String msg) {
            print(msg);
        }
        private void print(String msg) {
        	System.out.println("Client: "+msg);
        }
        @OnError
        public void onError(Session session, Throwable throwable) {
            print("Client : "+throwable.getMessage());
        }

    }
    @ClientEndpoint
    public static class ClientBlack {

        @OnOpen
        public void open(Session session) {
            session.getAsyncRemote().sendText("_ready_");
        }

        @OnMessage
        void message(String msg) {
            print(msg);
        }
        private void print(String msg) {
        	System.out.println("Client: "+msg);
        }
    }
}