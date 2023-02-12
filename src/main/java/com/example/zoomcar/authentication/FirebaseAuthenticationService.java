package com.example.zoomcar.authentication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import java.io.InputStream;

import static ch.qos.logback.core.util.Loader.getResource;

//import java.io.FileInputStream;

@Service
public class FirebaseAuthenticationService {
    @Autowired
    ResourceLoader resourceLoader;

    FirebaseApp firebaseApp;
    public FirebaseAuthenticationService(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        this.initializeFirebaseApp();
    }

    public void initializeFirebaseApp() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:firebase-service-credentials.json");
        InputStream inputStream = resource.getInputStream();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        this.firebaseApp = FirebaseApp.initializeApp(options);
    }

    public String generateToken(AuthenticationModel authData) throws FirebaseAuthException {
        String token =  FirebaseAuth.getInstance().createCustomToken(authData.getUuid());
        return token;
    }

    public boolean verifyToken(AuthenticationModel authData) {
        FirebaseToken decodedToken;
        try{
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(authData.getUuid());
            String uuid = decodedToken.getUid();
            System.out.println(uuid);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
