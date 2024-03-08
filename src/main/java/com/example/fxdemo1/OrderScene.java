package com.example.fxdemo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class OrderScene {
    private static ObservableList<OrderItem> cartItems = FXCollections.observableArrayList();

    public static Scene createOrderScene(Stage primaryStage, Scene mainScene, List<MenuItem> menuItems) {

        Label welcomeLabel = new Label("Place a new Order");
        welcomeLabel.getStyleClass().add("header-label");

        VBox layout = new VBox(10);
        layout.getChildren().add(welcomeLabel);
        // Display menu items
        for (MenuItem menuItem : menuItems) {
            layout.getChildren().add(createMenuItemBox(primaryStage, menuItem));
        }

        Separator separator = new Separator();

        // Display cart
        VBox cartLayout = createCartLayout(primaryStage);
        cartLayout.getStyleClass().add("cart-layout");

        layout.getChildren().addAll(separator, new Label("Cart"), cartLayout);

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        layout.getChildren().add(backButton);


        Scene scene = new Scene(layout, 400, 500);
        scene.getStylesheets().add(OrderScene.class.getResource("style.css").toExternalForm());

        return scene;
    }

    private static VBox createMenuItemBox(Stage primaryStage, MenuItem menuItem) {
        VBox itemBox = new VBox(5);

        Label itemNameLabel = new Label(menuItem.getName());
        Label itemPriceLabel = new Label("$" + menuItem.getPrice().get());

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.getStyleClass().add("add-to-cart-button");
        addToCartButton.setOnAction(e -> openAddToCartConfirmation(primaryStage, menuItem));

        itemBox.getChildren().addAll(itemNameLabel, itemPriceLabel, addToCartButton);

        return itemBox;
    }

    private static VBox createCartLayout(Stage primaryStage) {
        VBox cartLayout = new VBox(5);

        ListView<OrderItem> cartListView = new ListView<>(cartItems);
        cartListView.setPrefHeight(200);

        Label totalLabel = new Label("Total: $0.0");
        totalLabel.getStyleClass().add("total-label");

        Button checkoutButton = new Button("Checkout");
        checkoutButton.getStyleClass().add("checkout-button");
        checkoutButton.setOnAction(e -> openCheckoutConfirmation(primaryStage));

        cartLayout.getChildren().addAll(cartListView, totalLabel, checkoutButton);

        return cartLayout;
    }

    private static void openAddToCartConfirmation(Stage primaryStage, MenuItem menuItem) {
        TextInputDialog quantityDialog = new TextInputDialog("1");
        quantityDialog.setTitle("Add to Cart");
        quantityDialog.setHeaderText("Enter quantity:");
        quantityDialog.setContentText("Quantity:");

        quantityDialog.showAndWait().ifPresent(quantity -> {
            try {
                int itemQuantity = Integer.parseInt(quantity);
                if (itemQuantity > 0) {
                    // Check if the item is already in the cart
                    OrderItem existingItem = findCartItem(menuItem);
                    if (existingItem != null) {
                        // Update quantity
                        existingItem.setQuantity(existingItem.getQuantity() + itemQuantity);
                    } else {
                        // Add item to the cart
                        cartItems.add(new OrderItem(menuItem, itemQuantity));
                    }

                    // Refresh the cart layout
                    refreshCartLayout(primaryStage);
                } else {
                    showAlert("Invalid Quantity", "Quantity must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Quantity", "Please enter a valid integer quantity.");
            }
        });
    }

    private static void openCheckoutConfirmation(Stage primaryStage) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.initModality(Modality.WINDOW_MODAL);
        confirmationAlert.initOwner(primaryStage);
        confirmationAlert.setTitle("Checkout");
        confirmationAlert.setHeaderText("Confirm Checkout");
        confirmationAlert.setContentText("Are you sure you want to proceed with the checkout?");

        confirmationAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                primaryStage.setScene(ReceiptScene.createReceiptScene(primaryStage, cartItems));
                cartItems.clear();
                System.out.println("Checkout completed!");
            }
        });
    }

    private static OrderItem findCartItem(MenuItem menuItem) {
        for (OrderItem orderItem : cartItems) {
            if (orderItem.getMenuItem().equals(menuItem)) {
                return orderItem;
            }
        }
        return null;
    }

    private static void refreshCartLayout(Stage primaryStage) {
        VBox mainLayout = (VBox) primaryStage.getScene().getRoot();
        mainLayout.getChildren().removeIf(node -> node instanceof VBox && ((VBox) node).getChildren().stream().anyMatch(child -> child instanceof ListView));

        VBox cartLayout = createCartLayout(primaryStage);
        mainLayout.getChildren().add(cartLayout);

        double totalAmount = calculateTotal();
        Label totalLabel = (Label) cartLayout.getChildren().get(cartLayout.getChildren().size() - 2); // Assuming totalLabel is the second last child
        totalLabel.setText("Total: $" + totalAmount);
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private static double calculateTotal() {
        double total = 0.0;
        for (OrderItem orderItem : cartItems) {
            total += orderItem.getMenuItem().getPrice().get() * orderItem.getQuantity();
        }
        return total;
    }

}
