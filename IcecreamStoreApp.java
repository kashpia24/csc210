package com.example.icecreamshop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class IcecreamStoreApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        IcecreamStore store = new IcecreamStore();
        primaryStage.setTitle("Login");

        // Login Screen
        GridPane loginScreen = new GridPane();
        loginScreen.setPadding(new Insets(10));
        loginScreen.setVgap(10);
        loginScreen.setHgap(10);

        Label user_label = new Label("Username:");
        TextField username = new TextField();
        Label pass_label = new Label("Password:");
        TextField password = new TextField();
        Button loginbtn = new Button("Sign In");
        Label loginStatusLabel = new Label();

        loginScreen.add(user_label, 0, 0);
        loginScreen.add(username, 1, 0);
        loginScreen.add(pass_label, 0, 1);
        loginScreen.add(password, 1, 1);
        loginScreen.add(loginbtn, 1, 2);
        loginScreen.add(loginStatusLabel, 1, 3);

        Scene loginScene = new Scene(loginScreen, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        loginbtn.setOnAction(e -> {
            String user_name = username.getText();
            String pass = password.getText();
            if (store.get_user().equals(user_name) && store.get_pass().equals(pass)) {
                showMenuScene(primaryStage, store);
            } else {
                loginStatusLabel.setText("Invalid username or password. Try again.");
            }
        });
    }

    private void showMenuScene(Stage primaryStage, IcecreamStore store) {
        GridPane menu = new GridPane();
        primaryStage.setTitle("Ice Cream Store");
        menu.setPadding(new Insets(10));
        menu.setVgap(10);
        menu.setHgap(10);

        // Display menu items
        Label menuLabel = new Label("Menu Items:");
        menu.add(menuLabel, 0, 0);
        String[] items = store.get_item();
        TextField[] quantityFields = new TextField[items.length];
        for (int i = 0; i < items.length; i++) {
            menu.add(new Label(items[i] + " - $" +
                    store.get_price(items[i])), 0, i + 1);
            Label quantityLabel = new Label("Enter Quantity for " + items[i] + ":");
            TextField quantityField = new TextField();
            menu.add(quantityLabel, 0, items.length + 2 + i);
            menu.add(quantityField, 1, items.length + 2 + i);
            quantityFields[i] = quantityField;
        }

        Button calculateButton = new Button("Add to Cart");
        Label resultLabel = new Label();

        menu.add(calculateButton, 1, items.length + 2 + items.length);
        menu.add(resultLabel, 1, items.length + 3 + items.length);

        Scene menuScene = new Scene(menu, 700, 450);
        primaryStage.setScene(menuScene);
        primaryStage.show();

        calculateButton.setOnAction(e -> {
            double totalCost = 0;
            for (int i = 0; i < items.length; i++) {
                String itemName = items[i];
                TextField quantityField = quantityFields[i];
                int quantity = 0;

                // Check if the quantity field is empty and skip if it is
                if (quantityField.getText().isEmpty()) {
                    continue; // Skip to the next item if the quantity field is empty
                }

                try {
                    quantity = Integer.parseInt(quantityField.getText());
                    if (quantity < 0) {
                        resultLabel.setText("Quantity for " + itemName + " must be greater than or equal to 0.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid quantity for " + itemName + ". Please enter a number.");
                    return;
                }

                double price = store.get_price(itemName);
                if (price != -1 && quantity > 0) {
                    totalCost += store.cost(price, quantity);
                }
            }

            double tax = store.tax(totalCost);
            double finalTotalCost = store.totalcost(totalCost, tax);
            resultLabel.setText(String.format("Pre-Tax Cost: $%.2f, Tax: $%.2f, Total Cost: $%.2f", totalCost, tax, finalTotalCost));
        });

    }
}