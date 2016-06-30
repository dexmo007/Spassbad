package de.ostfalia.test.ss16.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main GUI Klasse
 *
 * @author Henrik Drefs, David N. Winterland
 */
public class MainApp extends Application {

    /**
     * main-methode
     *
     * @param args Argumente
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getClassLoader().getResource("main_view.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Spassbäder");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // ignore
        }
    }
}
