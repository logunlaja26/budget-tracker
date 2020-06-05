package com.lyomann.budgettracker.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class ApplicationConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    FirebaseOptions firebaseOptions(@Value("${firebase.url}") String firebaseUrl) throws Exception {
        FileInputStream serviceAccount = new FileInputStream("budget-tracker-firebase-adminsdk.json");

        return new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(firebaseUrl)
                .build();
    }

    @Bean
    FirebaseApp firebaseApp(FirebaseOptions firebaseOptions) {
        FirebaseApp.initializeApp(firebaseOptions);
        return FirebaseApp.getInstance();
    }

    @Bean
    FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

}
