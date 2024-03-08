package com.example.fxdemo1;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReceiptScene {

    public static Scene createReceiptScene(Stage primaryStage, ObservableList<OrderItem> cartItems) {
        Label welcomeLabel = new Label("Receipt");
        welcomeLabel.getStyleClass().add("header-label");

        VBox layout = new VBox(10);

        layout.getChildren().add(welcomeLabel);

        // Display receipt details
        for (OrderItem orderItem : cartItems) {
            layout.getChildren().add(createReceiptItemBox(orderItem));
        }

        // Calculate and display the total amount
        double totalAmount = calculateTotal(cartItems);
        Label totalLabel = new Label("Grand Total: $" + totalAmount);
        totalLabel.getStyleClass().add("total-label");
        layout.getChildren().add(totalLabel);

        Scene scene = new Scene(layout, 400, 500);
        scene.getStylesheets().add(ReceiptScene.class.getResource("style.css").toExternalForm());

        return scene;
    }

    private static VBox createReceiptItemBox(OrderItem orderItem) {
        VBox itemBox = new VBox(5);
        itemBox.getStyleClass().add("receipt-item-box");

        Label itemNameLabel = new Label(orderItem.getMenuItem().getName());
        Label itemPriceLabel = new Label("$" + orderItem.getMenuItem().getPrice().get());
        Label quantityLabel = new Label("Quantity: " + orderItem.getQuantity());

        itemNameLabel.getStyleClass().add("receipt-item-label");
        itemPriceLabel.getStyleClass().add("receipt-item-label");
        quantityLabel.getStyleClass().add("receipt-item-label");

        itemBox.getChildren().addAll(itemNameLabel, itemPriceLabel, quantityLabel);

        return itemBox;
    }

    private static double calculateTotal(ObservableList<OrderItem> cartItems) {
        double total = 0.0;
        for (OrderItem orderItem : cartItems) {
            total += orderItem.getMenuItem().getPrice().get() * orderItem.getQuantity();
        }
        return total;
    }
}
