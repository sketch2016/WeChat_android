package com.qdrs.sketchxu.wechat.model;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class ChatUtil {

    public static WebSocketClient client;

    public static void connect() throws URISyntaxException {
        client = new WebSocketClient(new URI("ws://192.168.8.157:8989/ws"),new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake arg0) {
                System.out.println("打开链接");
            }

            @Override
            public void onMessage(String receive) {

                System.out.println("收到消息:" + receive);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                System.out.println("发生错误已关闭");
            }

            @Override
            public void onClose(int code, String arg1, boolean arg2) {
                System.out.println("链接已关闭");
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                try {
                    System.out.println(new String(bytes.array(),"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        };

        client.connect();

        while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            System.out.println("还没有打开");
        }
        System.out.println("打开了");
        //send("hello world".getBytes("utf-8"));
        client.send("hello world heheda");
    }

    public static void sendMessage(String msg) {
        client.send(msg);
    }
}
