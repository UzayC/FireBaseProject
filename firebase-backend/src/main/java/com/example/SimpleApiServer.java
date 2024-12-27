package com.example;

import com.sun.net.httpserver.HttpServer;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.InetSocketAddress;

public class SimpleApiServer {

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("C:\\Projects")
                    .load();
            String portEnv = dotenv.get("SERVER_PORT", "4567");
            int port = Integer.parseInt(portEnv);

            FirebaseInitializer.initializeFirebase();
            System.out.println("Firebase initialized successfully!");

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/configurations", new ConfigurationHandler());

            server.setExecutor(null);
            server.start();
            System.out.println("Server is running on port 4567");

        } catch (Exception e) {
            System.err.println("Error initializing server: " + e.getMessage());
        }
    }
}