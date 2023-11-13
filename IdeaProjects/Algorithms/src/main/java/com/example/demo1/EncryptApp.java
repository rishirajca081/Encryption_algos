package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EncryptApp extends Application {
    private double x;
    private double y;

    public void start(Stage stage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Encrypt.fxml"));
            Scene scene = new Scene(root);

//        String css = this.getClass().getResource("Encrypt.css").toExternalForm();
//            scene.getStylesheets().add(getClass().getResource("Encrypt/Encrypt.css").toExternalForm());


            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
//                stage.setX(event.getSceneX() - x);
//                stage.setY(event.getSceneY() - y);

                stage.setOpacity(.8);
            });

            root.setOnMouseReleased((MouseEvent event) -> {
                stage.setOpacity(1);
            });


//            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);


//            stage.setMinHeight(400);
//            stage.setMinWidth(400);

            stage.show();

            // Let's Animate...
//            new Bounce(root).play();

        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}