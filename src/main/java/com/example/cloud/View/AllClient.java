package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.Model.Client;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AllClient {
    public static ScrollPane scrollPane;

    public static Pane getStartFront(int fl) throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/allclient.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button addPhys = new Button();
        addPhys.setBackground(null);
        addPhys.setLayoutY(678);
        addPhys.setLayoutX(947);
        addPhys.setPrefSize(143,24);
        pane.getChildren().add(addPhys);
        addPhys.setOnAction(t ->{
            Front.root.getChildren().remove(Front.pane);
            Front.pane = AddPhysCli.addPhys(fl);
            Front.root.getChildren().add(Front.pane);
        });
        Button addLegal = new Button();
        addLegal.setBackground(null);
        addLegal.setLayoutY(678);
        addLegal.setLayoutX(149);
        addLegal.setPrefSize(133,24);
        pane.getChildren().add(addLegal);
        addLegal.setOnAction(t ->{
            Front.root.getChildren().remove(Front.pane);
            Front.pane = AddUriCli.addLegal(fl);
            Front.root.getChildren().add(Front.pane);
        });
        if(fl==1){
            pane.getChildren().removeAll(addLegal,addPhys);
        }
        Pane paneScroll = Client.getPane(fl);

        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(119);
        scrollPane.setLayoutY(210);
        scrollPane.setMaxHeight(450);
        scrollPane.setMaxWidth(990);
        scrollPane.setMinHeight(400);
        scrollPane.setMinWidth(990);
        scrollPane.setStyle("-fx-background: rgb(217, 234, 245);-fx-background-color: rgb(217, 234, 245);");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(paneScroll);
        pane.getChildren().add(scrollPane);

        TextField findText = new TextField();
        findText.setPrefSize(200,63);
        findText.setLayoutX(850);
        findText.setLayoutY(158);
        findText.setPromptText("ФИО или номер телефона");
        findText.setFont(Font.font("STXihei", 14));
        findText.setBackground(null);
        pane.getChildren().add(findText);


        Button find = new Button();
        find.setBackground(null);
        find.setLayoutX(840);
        find.setLayoutY(176);
        find.setPrefSize(19,19);
        pane.getChildren().add(find);
        find.setOnAction(t->{
            String f = findText.getText();
            if(!f.isEmpty()){
                Pane pane1 = null;
                try {
                    pane1 = Client.getPane2(fl,f);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                scrollPane.setContent(pane1);
            }

        });


        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(1045);
        back.setLayoutY(38);
        back.setPrefSize(69,24);
        pane.getChildren().add(back);
        back.setOnAction(t ->{
            try {
                if(fl==0) {
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AdminFront.getStartFront();
                    Front.root.getChildren().add(Front.pane);
                }
                else if(fl==1){
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AnalystFront.getStartFront();
                    Front.root.getChildren().add(Front.pane);
                }
                else if(fl==2){
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = ManagerFront.getStartFront();
                    Front.root.getChildren().add(Front.pane);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });



        return pane;
    }
}
