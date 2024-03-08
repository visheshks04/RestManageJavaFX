package com.example.fxdemo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RestaurantManagementApp extends Application {

    private static List<MenuItem> menuItems = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {

        menuItems.add(new MenuItem("Cheese Burger", 29.0));
        menuItems.add(new MenuItem("Paneer Sandwich", 23.0));
        menuItems.add(new MenuItem("Veg Sandwich", 20.0));

        Label welcomeLabel = new Label("Welcome to Restaurant Management System");
        welcomeLabel.getStyleClass().add("header-label");

        stage.setTitle("Restaurant Management System");

        MenuScene menuScene = new MenuScene();
        OrderScene orderScene = new OrderScene();

        Button goToMenuButton = new Button("Go to Menu");
        goToMenuButton.setOnAction(e -> stage.setScene(menuScene.createMenuScene(stage, stage.getScene(), menuItems)));

        Button goToOrderButton = new Button("Place Order!");
        goToOrderButton.setOnAction(e -> stage.setScene(orderScene.createOrderScene(stage, stage.getScene(), menuItems)));

        VBox mainLayout = new VBox(10, welcomeLabel, goToMenuButton, goToOrderButton);

        Scene mainScene = new Scene(mainLayout, 400, 300);
        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(mainScene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}