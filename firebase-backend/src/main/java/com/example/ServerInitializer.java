package com.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerInitializer {

    private static final int PORT = 4567;

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            System.out.println("HTTP Server initialized on port " + PORT);

            server.createContext("/configurations", new ConfigurationHandler());

            server.setExecutor(null);
            server.start();

            System.out.println("Server is running and ready to accept requests.");
        } catch (IOException e) {
            System.err.println("Error initializing server: " + e.getMessage());
        }
    }
}
