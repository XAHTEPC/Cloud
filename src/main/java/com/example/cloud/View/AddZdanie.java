package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AddZdanie {
    public static Pane addCOD(int fl){
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addcod.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField address = new TextField();
        address.setBackground(null);
        address.setFont(Font.font("STXihei", 16));
        address.setLayoutX(230);
        address.setLayoutY(360);
        address.setPrefSize(350,32);
        pane1.getChildren().add(address);

        TextField index =  new TextField();
        index.setBackground(null);
        index.setFont(Font.font("STXihei", 16));
        index.setLayoutX(250);
        index.setLayoutY(440);
        index.setPrefSize(330,32);
        pane1.getChildren().add(index);

        TextField tel = new TextField();
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(270);
        tel.setLayoutY(513);
        tel.setPrefSize(300,32);
        pane1.getChildren().add(tel);

        TextField kol =new TextField();
        kol.setBackground(null);
        kol.setFont(Font.font("STXihei", 16));
        kol.setLayoutX(210);
        kol.setLayoutY(590);
        kol.setPrefSize(340,32);
        pane1.getChildren().add(kol);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(792);
        save.setLayoutY(634);
        save.setPrefSize(157,47);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String address_text = address.getText();
            String index_text = index.getText();
            String tel_text = tel.getText();
            String kol_text = kol.getText();
            if(!address_text.isEmpty()&&!index_text.isEmpty()&&!tel_text.isEmpty()
                    &&!kol_text.isEmpty()){
                try {
                    Postgre.addCod(address_text,index_text,tel_text,kol_text);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllZdanie.getStartFront(fl);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(838);
        back.setLayoutY(688);
        back.setPrefSize(65,36);
        pane1.getChildren().add(back);
        back.setOnAction(t->{
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = AllZdanie.getStartFront(fl);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);
        });

        return pane1;
    }
}
