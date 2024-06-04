package com.example.cloud.Model;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.View.AllCodService;
import com.example.cloud.View.EditService;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Service {
    public String id;
    public String type;
    public String price;
    public String descr;
    public String cpu;
    public String ram;
    public String extra;
    public String fl;

    public Service(String id, String type, String price, String descr, String cpu, String ram, String extra, String fl) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.descr = descr;
        this.cpu = cpu;
        this.ram = ram;
        this.extra = extra;
        this.fl = fl;
    }

    public Service(String id, String type, String price, String descr, String cpu, String ram, String extra) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.descr = descr;
        this.cpu = cpu;
        this.ram = ram;
        this.extra = extra;
    }




    public static Pane getPane(String id_cod,int fl) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text type_text = new Text("Тип");
        type_text.setLayoutX(50);
        type_text.setLayoutY(60);
        type_text.setFont(Font.font("Verdana",13));


        Text descr_text = new Text("Описание");
        descr_text.setLayoutX(180);
        descr_text.setLayoutY(60);
        descr_text.setFont(Font.font("Verdana",13));

        Text cpu_text = new Text("CPU");
        cpu_text.setLayoutX(450);
        cpu_text.setLayoutY(60);
        cpu_text.setFont(Font.font("Verdana",13));

        Text ram_text = new Text("ОЗУ");
        ram_text.setLayoutX(520);
        ram_text.setLayoutY(60);
        ram_text.setFont(Font.font("Verdana",13));

        Text extra_text = new Text("ОС/Кол-во МУ");
        extra_text.setLayoutX(590);
        extra_text.setLayoutY(60);
        extra_text.setFont(Font.font("Verdana",13));

        Text price_text = new Text("Цена");
        price_text.setLayoutX(740);
        price_text.setLayoutY(60);
        price_text.setFont(Font.font("Verdana",13));

        ArrayList<Service> mas = Postgre.getAllService(id_cod);
        int u = 80;
        for(int i=0; i<mas.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
//            num.setBackground(null);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
//            num.setStyle("-fx-background: rgb(190, 196, 213);-fx-background-color: rgb(190, 196, 213);");
            num.setText(mas.get(i).id);
            num.setLayoutX(10);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(30);
            num.setMinHeight(40);
            num.setMinWidth(30);

            TextField type =new TextField();
            type.setText(mas.get(i).type);
            type.setBackground(null);
            type.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            type.setEditable(false);
            type.setLayoutX(50);
            type.setLayoutY(0 + u);
            type.setMaxHeight(40);
            type.setMaxWidth(350);
            type.setMinWidth(350);

            TextField desc = new TextField();
            desc.setText(mas.get(i).descr);
            desc.setBackground(null);
            desc.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            desc.setEditable(false);
            desc.setLayoutX(180);
            desc.setLayoutY(0 + u);
            desc.setMaxHeight(40);
            desc.setMaxWidth(250);
            desc.setMinHeight(40);
            desc.setMinWidth(250);

            TextField cpu = new TextField();
            cpu.setText(mas.get(i).cpu);
            cpu.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            cpu.setEditable(false);
            cpu.setLayoutX(450);
            cpu.setLayoutY(0 + u);
            cpu.setMaxHeight(40);
            cpu.setMaxWidth(100);
            cpu.setMinHeight(40);
            cpu.setMinWidth(100);

            TextField ram = new TextField();
            ram.setText(mas.get(i).ram);
            ram.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            ram.setEditable(false);
            ram.setLayoutX(520);
            ram.setLayoutY(0 + u);
            ram.setMaxHeight(40);
            ram.setMaxWidth(70);

            TextField extra = new TextField();
            extra.setText(mas.get(i).extra);
            extra.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            extra.setEditable(false);
            extra.setLayoutX(590);
            extra.setLayoutY(0 + u);
            extra.setMaxHeight(40);
            extra.setMaxWidth(100);
            extra.setMinHeight(40);
            extra.setMinWidth(60);

            TextField price = new TextField();
            price.setText(mas.get(i).price);
            price.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            price.setEditable(false);
            price.setLayoutX(740);
            price.setLayoutY(0 + u);
            price.setMaxHeight(40);
            price.setMaxWidth(100);
            price.setMinHeight(40);
            price.setMinWidth(60);
            final String id = mas.get(i).id;
            Button edit = new Button("Удалить");
            edit.setLayoutX(900);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                try {
                    Postgre.deleteServiceCod(id);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllCodService.getStartFront(id_cod,fl);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(edit);

            pane.getChildren().addAll(num,type,desc,cpu,ram,price,extra);
        }

        pane.getChildren().addAll(num_text, type_text,  descr_text, cpu_text, price_text, ram_text,extra_text);
        return pane;
    }

    public static Pane getPane2(int fl) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text type_text = new Text("Тип");
        type_text.setLayoutX(50);
        type_text.setLayoutY(60);
        type_text.setFont(Font.font("Verdana",13));


        Text descr_text = new Text("Описание");
        descr_text.setLayoutX(180);
        descr_text.setLayoutY(60);
        descr_text.setFont(Font.font("Verdana",13));

        Text cpu_text = new Text("CPU");
        cpu_text.setLayoutX(450);
        cpu_text.setLayoutY(60);
        cpu_text.setFont(Font.font("Verdana",13));

        Text ram_text = new Text("ОЗУ");
        ram_text.setLayoutX(520);
        ram_text.setLayoutY(60);
        ram_text.setFont(Font.font("Verdana",13));

        Text extra_text = new Text("ОС/Кол-во МУ");
        extra_text.setLayoutX(590);
        extra_text.setLayoutY(60);
        extra_text.setFont(Font.font("Verdana",13));

        Text price_text = new Text("Цена");
        price_text.setLayoutX(740);
        price_text.setLayoutY(60);
        price_text.setFont(Font.font("Verdana",13));

        ArrayList<Service> mas = Postgre.getAllService2();
        int u = 80;
        for(int i=0; i<mas.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
//            num.setBackground(null);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
//            num.setStyle("-fx-background: rgb(190, 196, 213);-fx-background-color: rgb(190, 196, 213);");
            num.setText(mas.get(i).id);
            num.setLayoutX(10);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(30);
            num.setMinHeight(40);
            num.setMinWidth(30);

            TextField type =new TextField();
            type.setText(mas.get(i).type);
            type.setBackground(null);
            type.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            type.setEditable(false);
            type.setLayoutX(50);
            type.setLayoutY(0 + u);
            type.setMaxHeight(40);
            type.setMaxWidth(350);
            type.setMinWidth(350);

            TextField desc = new TextField();
            desc.setText(mas.get(i).descr);
            desc.setBackground(null);
            desc.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            desc.setEditable(false);
            desc.setLayoutX(180);
            desc.setLayoutY(0 + u);
            desc.setMaxHeight(40);
            desc.setMaxWidth(250);
            desc.setMinHeight(40);
            desc.setMinWidth(250);

            TextField cpu = new TextField();
            cpu.setText(mas.get(i).cpu);
            cpu.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            cpu.setEditable(false);
            cpu.setLayoutX(450);
            cpu.setLayoutY(0 + u);
            cpu.setMaxHeight(40);
            cpu.setMaxWidth(100);
            cpu.setMinHeight(40);
            cpu.setMinWidth(100);

            TextField ram = new TextField();
            ram.setText(mas.get(i).ram);
            ram.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            ram.setEditable(false);
            ram.setLayoutX(520);
            ram.setLayoutY(0 + u);
            ram.setMaxHeight(40);
            ram.setMaxWidth(70);

            TextField extra = new TextField();
            extra.setText(mas.get(i).extra);
            extra.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            extra.setEditable(false);
            extra.setLayoutX(590);
            extra.setLayoutY(0 + u);
            extra.setMaxHeight(40);
            extra.setMaxWidth(100);
            extra.setMinHeight(40);
            extra.setMinWidth(60);

            TextField price = new TextField();
            price.setText(mas.get(i).price);
            price.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            price.setEditable(false);
            price.setLayoutX(740);
            price.setLayoutY(0 + u);
            price.setMaxHeight(40);
            price.setMaxWidth(100);
            price.setMinHeight(40);
            price.setMinWidth(60);
            final String id = mas.get(i).id;
            if(fl==0){
                Button edit = new Button("Изменить");
                edit.setLayoutX(900);
                edit.setLayoutY(0 + u);
                edit.setOnAction(t->{
                    Front.root.getChildren().remove(Front.pane);
                    try {
                        Front.pane = EditService.editService(fl,id);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Front.root.getChildren().add(Front.pane);
                });
                pane.getChildren().add(edit);
            }


            pane.getChildren().addAll(num,type,desc,cpu,ram,price,extra);
        }

        pane.getChildren().addAll(num_text, type_text,  descr_text, cpu_text, price_text, ram_text,extra_text);
        return pane;
    }

}
