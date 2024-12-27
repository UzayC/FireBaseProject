package com.example;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

public class AuthMiddleware {
    public static boolean isAuthenticated(String idToken) {
        try {
            if (idToken == null || idToken.isEmpty()) {
                System.err.println("Missing token");
                return false;
            }

            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            System.out.println("User authenticated: " + uid);
            return true;
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }
}

