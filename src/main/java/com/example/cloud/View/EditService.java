package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.Model.Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class EditService {
    public static Pane editService(int fl, String id_serv) throws SQLException {
        Service service = Postgre.getServicebyID(id_serv);
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);

        FileInputStream Url1;
        TextField os =new TextField();
        TextField kol =new TextField();

        try {
            Url1 = new FileInputStream("png/editservice.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);

        String[] mastype = {"Контейнеризация","Виртуальная машина"};
        ObservableList<String> types = FXCollections.observableArrayList(mastype);
        ComboBox<String> comboBox_type = new ComboBox<String>(types);
        comboBox_type.setMaxWidth(260);
        comboBox_type.setEditable(false);
        comboBox_type.setMinWidth(260);
        comboBox_type.setBackground(null);
        comboBox_type.setLayoutX(320);
        comboBox_type.setLayoutY(359);
        comboBox_type.setValue(service.type);
        pane1.getChildren().add(comboBox_type);
        comboBox_type.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String type = (comboBox_type.getSelectionModel().getSelectedItem());
                if(type.equals("Виртуальная машина")){
                    kol.setEditable(false);
                    os.setEditable(true);
                }
                else {
                    kol.setEditable(true);
                    os.setEditable(false);
                }
            }
        });


        TextField price = new TextField();
        price.setText(service.price);
        price.setBackground(null);
        price.setFont(Font.font("STXihei", 16));
        price.setLayoutX(210);
        price.setLayoutY(436);
        price.setPrefSize(380,32);
        pane1.getChildren().add(price);

        TextField desc =  new TextField();
        desc.setText(service.descr);
        desc.setBackground(null);
        desc.setFont(Font.font("STXihei", 16));
        desc.setLayoutX(290);
        desc.setLayoutY(513);
        desc.setPrefSize(290,32);
        pane1.getChildren().add(desc);

        TextField cpu = new TextField();
        cpu.setText(service.cpu);
        cpu.setBackground(null);
        cpu.setFont(Font.font("STXihei", 16));
        cpu.setLayoutX(180);
        cpu.setLayoutY(590);
        cpu.setPrefSize(405,32);
        pane1.getChildren().add(cpu);

        TextField ram =new TextField();
        ram.setText(service.ram);
        ram.setBackground(null);
        ram.setFont(Font.font("STXihei", 16));
        ram.setLayoutX(190);
        ram.setLayoutY(667);
        ram.setPrefSize(390,32);
        pane1.getChildren().add(ram);

        if(service.type.equals("Виртуальная машина")){
            os.setText(service.extra);
        } else
            kol.setText(service.extra);

        os.setBackground(null);

        os.setFont(Font.font("STXihei", 16));
        os.setLayoutX(720);
        os.setLayoutY(359);
        os.setPrefSize(420,32);
        pane1.getChildren().add(os);


        kol.setBackground(null);
        kol.setFont(Font.font("STXihei", 16));
        kol.setLayoutX(1050);
        kol.setLayoutY(436);
        kol.setPrefSize(90,32);
        pane1.getChildren().add(kol);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(792);
        save.setLayoutY(634);
        save.setPrefSize(157,47);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String kol_text = kol.getText();
            String desc_text = desc.getText();
            String cpu_text = cpu.getText();
            String ram_text = ram.getText();
            String type_text = comboBox_type.getSelectionModel().getSelectedItem();
            String price_text = price.getText();
            String os_text = os.getText();
            if(!cpu_text.isEmpty()&&!ram_text.isEmpty()&&!desc_text.isEmpty()
                    &&!price_text.isEmpty()&&(!kol_text.isEmpty()||!os_text.isEmpty())&&!type_text.isEmpty()){
                try {
                    String extra = "";
                    if(type_text.equals("Виртуальная машина"))
                        extra = os_text;
                    else
                        extra = kol_text;
                    Postgre.updateService(id_serv,type_text,price_text,desc_text,cpu_text,ram_text,extra);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllService.getStartFront(fl);
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
        delete.setLayoutX(824);
        delete.setLayoutY(688);
        delete.setPrefSize(153,36);
        pane1.getChildren().add(delete);
        delete.setOnAction(t->{
            try {
                Postgre.deleteService(id_serv);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = AllService.getStartFront(fl);
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
