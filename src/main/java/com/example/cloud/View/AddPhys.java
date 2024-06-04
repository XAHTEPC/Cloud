package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AddPhys {
    public static Pane addPhys(int fl){
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addPhys.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField name = new TextField();
        name.setBackground(null);
        name.setFont(Font.font("STXihei", 16));
        name.setLayoutX(210);
        name.setLayoutY(360);
        name.setPrefSize(390,32);
        pane1.getChildren().add(name);

        TextField mail =  new TextField();
        mail.setBackground(null);
        mail.setFont(Font.font("STXihei", 16));
        mail.setLayoutX(230);
        mail.setLayoutY(440);
        mail.setPrefSize(370,32);
        pane1.getChildren().add(mail);

        TextField tel = new TextField();
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(240);
        tel.setLayoutY(513);
        tel.setPrefSize(360,32);
        pane1.getChildren().add(tel);

        TextField inn =new TextField();
        inn.setBackground(null);
        inn.setFont(Font.font("STXihei", 16));
        inn.setLayoutX(200);
        inn.setLayoutY(590);
        inn.setPrefSize(400,32);
        pane1.getChildren().add(inn);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(792);
        save.setLayoutY(634);
        save.setPrefSize(157,47);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String name_text = name.getText();
            String mail_text = mail.getText();
            String tel_text = tel.getText();
            String inn_text = inn.getText();
            if(!name_text.isEmpty()&&!mail_text.isEmpty()&&!tel_text.isEmpty()
                    &&!inn_text.isEmpty()){
                try {
                    Postgre.addPhysClient(name_text,tel_text,inn_text,mail_text);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllClient.getStartFront(fl);
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
                Front.pane = AllClient.getStartFront(fl);
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
