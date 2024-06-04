package com.example.cloud.Model;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.View.AddReceipt;
import com.example.cloud.View.EditLegal;
import com.example.cloud.View.EditPhys;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class Client {
    public static Pane getPane(int fl) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text name_text = new Text("ФИО");
        name_text.setLayoutX(50);
        name_text.setLayoutY(60);
        name_text.setFont(Font.font("Verdana",13));


        Text tel_text = new Text("Телефон");
        tel_text.setLayoutX(260);
        tel_text.setLayoutY(60);
        tel_text.setFont(Font.font("Verdana",13));

        Text mail_text = new Text("Почта");
        mail_text.setLayoutX(360);
        mail_text.setLayoutY(60);
        mail_text.setFont(Font.font("Verdana",13));

        Text bonus_text = new Text("Бонус");
        bonus_text.setLayoutX(510);
        bonus_text.setLayoutY(60);
        bonus_text.setFont(Font.font("Verdana",13));

        Text status_text = new Text("Статус");
        status_text.setLayoutX(610);
        status_text.setLayoutY(60);
        status_text.setFont(Font.font("Verdana",13));
        if(Front.isStart) {
            Postgre.makeTMP();
            Front.isStart = false;
        }
        ArrayList<LegalClient> mas = Postgre.getAllLegalClient();
        int u = 80;
        for(int i=0; i<mas.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            num.setText(mas.get(i).id);
            num.setLayoutX(0);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(45);
            num.setMinHeight(40);
            num.setMinWidth(45);

            TextField name =new TextField();
            name.setText(mas.get(i).orgname);
            name.setBackground(null);
            name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            name.setEditable(false);
            name.setLayoutX(50);
            name.setLayoutY(0 + u);
            name.setMaxHeight(40);
            name.setMaxWidth(350);
            name.setMinWidth(350);

            TextField tel = new TextField();
            tel.setText(mas.get(i).tel);
            tel.setBackground(null);
            tel.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            tel.setEditable(false);
            tel.setLayoutX(260);
            tel.setLayoutY(0 + u);
            tel.setMaxHeight(40);
            tel.setMaxWidth(80);
            tel.setMinHeight(40);
            tel.setMinWidth(80);

            TextField mail = new TextField();
            mail.setText(mas.get(i).mail);
            mail.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            mail.setEditable(false);
            mail.setLayoutX(360);
            mail.setLayoutY(0 + u);
            mail.setMaxHeight(40);
            mail.setMaxWidth(150);
            mail.setMinHeight(40);
            mail.setMinWidth(150);

            TextField bonus = new TextField();
            bonus.setText(mas.get(i).bonus);
            bonus.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            bonus.setEditable(false);
            bonus.setLayoutX(510);
            bonus.setLayoutY(0 + u);
            bonus.setMaxHeight(40);
            bonus.setMaxWidth(70);

            TextField status = new TextField();
            status.setText(mas.get(i).status);
            status.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            status.setEditable(false);
            status.setLayoutX(600);
            status.setLayoutY(0 + u);
            status.setMaxHeight(40);
            status.setMaxWidth(100);
            status.setMinHeight(40);
            status.setMinWidth(60);
            final String id = mas.get(i).id;
            Button add = new Button("Новый чек");
            add.setLayoutX(730);
            add.setLayoutY(0 + u);
            add.setOnAction(t->{
                try {
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane =AddReceipt.addReceipt(true, id,fl);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(add);
            Button edit = new Button("Изменить");
            edit.setLayoutX(850);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                Front.root.getChildren().remove(Front.pane);
                try {
                    Front.pane = EditLegal.editLegal(id,fl);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Front.root.getChildren().add(Front.pane);
            });
            pane.getChildren().add(edit);
            pane.getChildren().addAll(num,name,tel,mail,bonus,status);
            if(fl == 1) {
                pane.getChildren().removeAll(edit,add);
            }
        }
        ArrayList<PhysClient> mas2 = Postgre.getAllPhysClient();
        for(int i=0; i<mas2.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            num.setText(mas2.get(i).id);
            num.setLayoutX(0);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(45);
            num.setMinHeight(40);
            num.setMinWidth(45);

            TextField name =new TextField();
            name.setText(mas2.get(i).name);
            name.setBackground(null);
            name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            name.setEditable(false);
            name.setLayoutX(50);
            name.setLayoutY(0 + u);
            name.setMaxHeight(40);
            name.setMaxWidth(350);
            name.setMinWidth(350);

            TextField tel = new TextField();
            tel.setText(mas2.get(i).tel);
            tel.setBackground(null);
            tel.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            tel.setEditable(false);
            tel.setLayoutX(250);
            tel.setLayoutY(0 + u);
            tel.setMaxHeight(40);
            tel.setMaxWidth(80);
            tel.setMinHeight(40);
            tel.setMinWidth(80);

            TextField mail = new TextField();
            mail.setText(mas2.get(i).mail);
            mail.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            mail.setEditable(false);
            mail.setLayoutX(360);
            mail.setLayoutY(0 + u);
            mail.setMaxHeight(40);
            mail.setMaxWidth(150);
            mail.setMinHeight(40);
            mail.setMinWidth(150);

            TextField bonus = new TextField();
            bonus.setText(mas2.get(i).bonus);
            bonus.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            bonus.setEditable(false);
            bonus.setLayoutX(520);
            bonus.setLayoutY(0 + u);
            bonus.setMaxHeight(40);
            bonus.setMaxWidth(70);

            TextField status = new TextField();
            status.setText(mas2.get(i).status);
            status.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            status.setEditable(false);
            status.setLayoutX(600);
            status.setLayoutY(0 + u);
            status.setMaxHeight(40);
            status.setMaxWidth(100);
            status.setMinHeight(40);
            status.setMinWidth(60);
            final String id = mas2.get(i).id;
            Button add = new Button("Новый чек");
            add.setLayoutX(730);
            add.setLayoutY(0 + u);
            add.setOnAction(t->{
                try {
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane =AddReceipt.addReceipt(false, id,fl);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(add);
            Button edit = new Button("Изменить");
            edit.setLayoutX(850);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                try {
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = EditPhys.editPhys(id,fl);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(edit);
            pane.getChildren().addAll(num,name,tel,mail,bonus,status);
            if(fl == 1) {
                pane.getChildren().removeAll(edit,add);
            }
        }

        pane.getChildren().addAll(num_text, name_text,  tel_text, mail_text, status_text, bonus_text);
        return pane;
    }
}

