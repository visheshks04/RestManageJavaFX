package com.example.fxdemo1;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class MenuScene {
    public static Scene createMenuScene(Stage primaryStage, Scene mainScene, List<MenuItem> menuItems) {

        Label welcomeLabel = new Label("Current Menu");
        welcomeLabel.getStyleClass().add("header-label");

        VBox layout = new VBox(10);

        Button addMenuItemButton = new Button("Add Menu Item");
        addMenuItemButton.getStyleClass().add("button");
        addMenuItemButton.setOnAction(e -> openAddMenuItemWindow(primaryStage, layout, menuItems));

        layout.getChildren().add(welcomeLabel);
        layout.getChildren().add(addMenuItemButton);

        for (MenuItem menuItem : menuItems) {
            layout.getChildren().add(createMenuItemBox(primaryStage, menuItem, menuItems));
        }

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        layout.getChildren().add(backButton);

        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(MenuScene.class.getResource("style.css").toExternalForm());

        return scene;
    }

    private static VBox createMenuItemBox(Stage primaryStage, MenuItem menuItem, List<MenuItem> menuItems) {
        VBox itemBox = new VBox(5);
        itemBox.getStyleClass().add("menu-item-box");

        Label itemNameLabel = new Label(menuItem.getName());
        Label itemPriceLabel = new Label("$" + menuItem.getPrice().get());

        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> openDeleteMenuItemConfirmation(primaryStage, menuItem, menuItems));

        itemBox.getChildren().addAll(itemNameLabel, itemPriceLabel, deleteButton);

        return itemBox;
    }

    private static void openAddMenuItemWindow(Stage parentStage, VBox layout, List<MenuItem> menuItems) {
        Stage addMenuItemStage = new Stage();
        addMenuItemStage.initModality(Modality.WINDOW_MODAL);
        addMenuItemStage.initOwner(parentStage);
        addMenuItemStage.setTitle("Add Menu Item");

        VBox popupLayout = new VBox(10);

        Label nameLabel = new Label("Item Name:");
        TextField nameTextField = new TextField();

        Label priceLabel = new Label("Item Price:");
        TextField priceTextField = new TextField();

        Button addButton = new Button("Add Item");
        addButton.setOnAction(e -> {
            String itemName = nameTextField.getText();
            Double itemPrice = parseDouble(priceTextField.getText());

            if (!itemName.isEmpty() && !itemPrice.isNaN()) {
                menuItems.add(new MenuItem(itemName, itemPrice));
                System.out.println("Added Menu Item: " + itemName + " - $" + itemPrice);
                addMenuItemStage.close();
                refreshMenu(layout, parentStage, menuItems);
            } else {
                System.out.println("Error, Both item name and price are required.");
            }
            addMenuItemStage.close();
        });

        popupLayout.getChildren().addAll(nameLabel, nameTextField, priceLabel, priceTextField, addButton);

        Scene scene = new Scene(popupLayout, 300, 200);
        addMenuItemStage.setScene(scene);
        addMenuItemStage.show();

    }

    private static void refreshMenu(VBox layout, Stage primaryStage, List<MenuItem> menuItems) {
        layout.getChildren().removeIf(node -> node instanceof VBox);

        for (MenuItem menuItem : menuItems) {
            layout.getChildren().add(createMenuItemBox(primaryStage, menuItem, menuItems));
        }

    }

    private static void openDeleteMenuItemConfirmation(Stage primaryStage, MenuItem menuItem, List<MenuItem> menuItems) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.initModality(Modality.WINDOW_MODAL);
        confirmationAlert.initOwner(primaryStage);
        confirmationAlert.setTitle("Delete Menu Item");
        confirmationAlert.setHeaderText("Confirm Deletion");
        confirmationAlert.setContentText("Are you sure you want to delete this menu item?");

        confirmationAlert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                menuItems.remove(menuItem);
                refreshMenu((VBox) primaryStage.getScene().getRoot(), primaryStage, menuItems);
                System.out.println("Menu item deleted");  // Replace with actual delete logic
            }
        });
    }

    private static Double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
