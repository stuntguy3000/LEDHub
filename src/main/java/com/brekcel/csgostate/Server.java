package com.brekcel.csgostate;

import com.brekcel.csgostate.JSON.JsonResponse;
import com.brekcel.csgostate.post.PostHandler;
import com.brekcel.csgostate.post.PostReceiver;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    public boolean onlyUser;
    private HttpServer serv;
    private String authToken;
    private int port;
    private PostReceiver receive;
    private Info info;

    public Server(int port, PostHandler postHandle, boolean onlyUser, String authToken) throws IOException {
        this.port = port;
        this.authToken = authToken;
        this.onlyUser = onlyUser;
        serv = HttpServer.create(new InetSocketAddress(port), 0);
        receive = new PostReceiver(this, postHandle);
        serv.createContext("/", receive);
        serv.setExecutor(Executors.newCachedThreadPool());
        serv.start();
        info = new Info(this);
        System.out.println("Server successfully started on port " + port + (authToken == null ? "." : " with Auth Token: " + authToken));
    }

    public Server(int port, PostHandler postHandle, boolean onlyUser) throws IOException {
        new Server(port, postHandle, onlyUser, null);
    }

    public Info getInfo() {
        return info;
    }

    public JsonResponse getCurrentJSR() {
        return receive.getCurrentJSR();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void stop() {
        serv.stop(0);
    }

    public int getPort() {
        return port;
    }
}
