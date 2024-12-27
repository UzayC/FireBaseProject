package com.example;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.util.Map;

public class ConfigurationHandler implements HttpHandler {

    private final Firestore firestore;

    public ConfigurationHandler() {
        this.firestore = FirestoreClient.getFirestore();
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            System.out.println("Incoming request: " + exchange.getRequestURI());

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // 204 No Content yanıtı
                return;
            }

            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    handleGetRequest(exchange);
                    break;
                case "POST":
                    handlePostRequest(exchange);
                    break;
                case "DELETE":
                    handleDeleteRequest(exchange);
                    break;
                default:
                    exchange.sendResponseHeaders(405, -1);
                    break;
            }
        } catch (Exception e) {
            try {
                String error = "Error handling request: " + e.getMessage();
                exchange.sendResponseHeaders(500, error.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(error.getBytes());
                os.close();
            } catch (Exception ignored) {
            }
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws Exception {
        String id = exchange.getRequestURI().getPath().split("/")[2];
        DocumentSnapshot document = firestore.collection("configurations").document(id).get().get();

        String response = document.exists() ? new Gson().toJson(document.getData()) : "Configuration not found";

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handlePostRequest(HttpExchange exchange) throws Exception {
        String id = exchange.getRequestURI().getPath().split("/")[2];
        String body = new String(exchange.getRequestBody().readAllBytes());
        Map<String, Object> data = new Gson().fromJson(body, Map.class);

        data.put("lastUpdated", System.currentTimeMillis());
        firestore.collection("configurations").document(id).set(data).get();

        String response = "Configuration created/updated!";
        exchange.sendResponseHeaders(201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleDeleteRequest(HttpExchange exchange) throws Exception {
        String id = exchange.getRequestURI().getPath().split("/")[2];
        firestore.collection("configurations").document(id).delete().get();

        String response = "Configuration deleted successfully!";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
