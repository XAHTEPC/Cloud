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

public class AddLegal {
    public static Pane addLegal(int fl){
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addLegal.png");
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
        name.setLayoutX(200);
        name.setLayoutY(360);
        name.setPrefSize(380,32);
        pane1.getChildren().add(name);

        TextField orgName =  new TextField();
        orgName.setBackground(null);
        orgName.setFont(Font.font("STXihei", 16));
        orgName.setLayoutX(360);
        orgName.setLayoutY(436);
        orgName.setPrefSize(230,32);
        pane1.getChildren().add(orgName);

        TextField inn = new TextField();
        inn.setBackground(null);
        inn.setFont(Font.font("STXihei", 16));
        inn.setLayoutX(200);
        inn.setLayoutY(513);
        inn.setPrefSize(390,32);
        pane1.getChildren().add(inn);

        TextField kpp =new TextField();
        kpp.setBackground(null);
        kpp.setFont(Font.font("STXihei", 16));
        kpp.setLayoutX(200);
        kpp.setLayoutY(590);
        kpp.setPrefSize(390,32);
        pane1.getChildren().add(kpp);

        TextField ogrn = new TextField();
        ogrn.setBackground(null);
        ogrn.setFont(Font.font("STXihei", 16));
        ogrn.setLayoutX(730);
        ogrn.setLayoutY(359);
        ogrn.setPrefSize(360,32);
        pane1.getChildren().add(ogrn);

        TextField tel =new TextField();
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(800);
        tel.setLayoutY(436);
        tel.setPrefSize(290,32);
        pane1.getChildren().add(tel);

        TextField mail =new TextField();
        mail.setBackground(null);
        mail.setFont(Font.font("STXihei", 16));
        mail.setLayoutX(750);
        mail.setLayoutY(513);
        mail.setPrefSize(340,32);
        pane1.getChildren().add(mail);

        TextField address =new TextField();
        address.setBackground(null);
        address.setFont(Font.font("STXihei", 16));
        address.setLayoutX(750);
        address.setLayoutY(590);
        address.setPrefSize(340,32);
        pane1.getChildren().add(address);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(957);
        save.setLayoutY(668);
        save.setPrefSize(157,47);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String name_text = name.getText();
            String orgName_text = orgName.getText();
            String inn_text = inn.getText();
            String kpp_text = kpp.getText();
            String ogrn_text = ogrn.getText();
            String tel_text = tel.getText();
            String mail_text = mail.getText();
            String address_text = address.getText();
            if(!name_text.isEmpty()&&!orgName_text.isEmpty()&&!inn_text.isEmpty()
                    &&!kpp_text.isEmpty()&&!ogrn_text.isEmpty()&&!tel_text.isEmpty()&&
                    !mail_text.isEmpty()&&!address_text.isEmpty()){
                try {
                    Postgre.addLegalClient(name_text,orgName_text,inn_text,kpp_text,ogrn_text,tel_text,mail_text,address_text);
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
        back.setLayoutX(1003);
        back.setLayoutY(722);
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
