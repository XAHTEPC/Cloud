package com.example.cloud.View;

import com.example.cloud.Front;
import com.example.cloud.Model.Service;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AllCodService {
    public static ScrollPane scrollPane;

    public static Pane getStartFront(String id, int fl) throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/codservice.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button addService = new Button();
        addService.setBackground(null);
        addService.setLayoutY(652);
        addService.setLayoutX(1045);
        addService.setPrefSize(41,60);
        pane.getChildren().add(addService);
        addService.setOnAction(t ->{
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AddService.addService3(id,fl);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        Pane paneScroll = Service.getPane(id,fl);
        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(119);
        scrollPane.setLayoutY(160);
        scrollPane.setMaxHeight(450);
        scrollPane.setMaxWidth(990);
        scrollPane.setMinHeight(450);
        scrollPane.setMinWidth(990);
        scrollPane.setStyle("-fx-background: rgb(217, 234, 245);-fx-background-color: rgb(217, 234, 245);");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(paneScroll);
        pane.getChildren().add(scrollPane);


        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(1045);
        back.setLayoutY(38);
        back.setPrefSize(69,24);
        pane.getChildren().add(back);
        back.setOnAction(t ->{
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AdminFront.getStartFront();
                Front.root.getChildren().add(Front.pane);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });



        return pane;
    }
}
