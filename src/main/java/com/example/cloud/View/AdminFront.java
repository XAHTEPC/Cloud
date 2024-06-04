package com.example.cloud.View;

import com.example.cloud.Front;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AdminFront {
    public static Pane getStartFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/admin.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button allcod = new Button();
        allcod.setBackground(null);
        allcod.setLayoutY(102);
        allcod.setLayoutX(745);
        allcod.setPrefSize(369,70);
        pane.getChildren().add(allcod);
        allcod.setOnAction(t ->{
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllCod.getStartFront(0);
                Front.root.getChildren().add(Front.pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button exit = new Button();
        exit.setBackground(null);
        exit.setLayoutX(1045);
        exit.setLayoutY(38);
        exit.setPrefSize(69,24);
        pane.getChildren().add(exit);
        exit.setOnAction(t ->{
            try {
                Front.exit();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button allEmployee = new Button();
        allEmployee.setBackground(null);
        allEmployee.setLayoutX(745);
        allEmployee.setLayoutY(654);
        allEmployee.setPrefSize(369,70);
        pane.getChildren().add(allEmployee);
        allEmployee.setOnAction(t1 ->{
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllEmpl.getStartFront(0);
                Front.root.getChildren().add(Front.pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button allClient = new Button();
        allClient.setBackground(null);
        allClient.setLayoutX(119);
        allClient.setLayoutY(654);
        allClient.setPrefSize(369,70);
        pane.getChildren().add(allClient);
        allClient.setOnAction(t1 -> {
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllClient.getStartFront(0);
                Front.root.getChildren().add(Front.pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button allreceipt = new Button();
        allreceipt.setBackground(null);
        allreceipt.setLayoutX(119);
        allreceipt.setLayoutY(102);
        allreceipt.setPrefSize(369,70);
        pane.getChildren().add(allreceipt);
        allreceipt.setOnAction(t1 -> {
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllReceipt.getStartFront(0);
                Front.root.getChildren().add(Front.pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button allService = new Button();
        allService.setBackground(null);
        allService.setLayoutX(852);
        allService.setLayoutY(378);
        allService.setPrefSize(294,70);
        pane.getChildren().add(allService);
        allService.setOnAction(t1 -> {
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllService.getStartFront(0);
                Front.root.getChildren().add(Front.pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        return pane;
    }
}
