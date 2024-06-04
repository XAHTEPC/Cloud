package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.Model.COD;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class EditCod {
    public static Pane editCOD(String id, int fl) throws SQLException {
        COD cod = Postgre.getCODbyID(id);
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/editcod.png");
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
        address.setText(cod.address);
        address.setFont(Font.font("STXihei", 16));
        address.setLayoutX(230);
        address.setLayoutY(360);
        address.setPrefSize(350,32);
        pane1.getChildren().add(address);

        TextField index =  new TextField();
        index.setText(cod.index);
        index.setBackground(null);
        index.setFont(Font.font("STXihei", 16));
        index.setLayoutX(250);
        index.setLayoutY(440);
        index.setPrefSize(330,32);
        pane1.getChildren().add(index);

        TextField tel = new TextField();
        tel.setText(cod.tel);
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(270);
        tel.setLayoutY(513);
        tel.setPrefSize(300,32);
        pane1.getChildren().add(tel);

        TextField kol =new TextField();
        kol.setText(cod.num_empl);
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
                    Postgre.updateCOD(cod.id,address_text,index_text,tel_text,kol_text);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllCod.getStartFront(fl);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Button delete = new Button();
        delete.setBackground(null);
        delete.setLayoutX(832);
        delete.setLayoutY(689);
        delete.setPrefSize(130,36);
        pane1.getChildren().add(delete);
        delete.setOnAction(t->{
            try {
                Postgre.deleteCOD(cod.id);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllCod.getStartFront(fl);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return pane1;
    }
}
