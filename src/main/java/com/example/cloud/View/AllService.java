package com.example.cloud.View;

import com.example.cloud.Front;
import com.example.cloud.Model.COD;
import com.example.cloud.Model.Service;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AllService {
    public static ScrollPane scrollPane;

    public static Pane getStartFront(int fl) throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/service.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button addservice = new Button();
        addservice.setBackground(null);
        addservice.setLayoutY(652);
        addservice.setLayoutX(1045);
        addservice.setPrefSize(41,60);
        pane.getChildren().add(addservice);
        addservice.setOnAction(t ->{
            Front.root.getChildren().remove(Front.pane);
            Front.pane = AddService.addService(fl);
            Front.root.getChildren().add(Front.pane);
        });
        if(fl==1){
            pane.getChildren().removeAll(addservice);
        }
        Pane paneScroll = Service.getPane2(fl);

        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(119);
        scrollPane.setLayoutY(160);
        scrollPane.setMaxHeight(550);
        scrollPane.setMaxWidth(990);
        scrollPane.setMinHeight(500);
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
                if(fl==0){
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AdminFront.getStartFront();
                    Front.root.getChildren().add(Front.pane);
                }
                else if(fl==1){
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AnalystFront.getStartFront();
                    Front.root.getChildren().add(Front.pane);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });



        return pane;
    }
}
