package com.example.labassignment3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class HelloApplication extends Application {
    private ArrayList<User> userCredentials = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        loadUserCredentials();

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(new FileInputStream("nature.jpg"));
            imageView.setImage(image);
            imageView.setFitWidth(250);
            imageView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            System.out.println("Image file not found: " + e.getMessage());
        }

        Label usernameLabel = new Label("User Name:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");
        Label notificationLabel = new Label();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(exitButton, 1, 2);
        gridPane.add(notificationLabel, 0, 3, 2, 1);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (validateLogin(username, password)) {
                notificationLabel.setText("Login successful!");
            } else {
                notificationLabel.setText("Invalid username or password!");
            }
        });

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        VBox root = new VBox(10, imageView, new Label("Lab Assignment"), gridPane);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 300, 400);

        primaryStage.setTitle("Login App with Image");
        primaryStage.setScene(scene);

        primaryStage.setResizable(true);

        primaryStage.show();
    }

    private void loadUserCredentials() {
        File file = new File("users.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        userCredentials.add(new User(parts[0].trim(), parts[1].trim()));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading user file: " + e.getMessage());
            }
        } else {
            System.out.println("User file not found. Creating a default user file.");
            createDefaultUserFile();
        }
    }

    private void createDefaultUserFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
            writer.println("Hammas,haemy12");
            writer.println("Wajee,wajee12");
        } catch (IOException e) {
            System.out.println("Error creating default user file: " + e.getMessage());
        }
    }

    private boolean validateLogin(String username, String password) {
        for (User user : userCredentials) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
