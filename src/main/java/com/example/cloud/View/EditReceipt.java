package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.Model.Receipt;
import com.example.cloud.Model.ReceiptServ;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
import java.time.LocalDate;
import java.util.ArrayList;

public class EditReceipt {
    public static ArrayList<ReceiptServ> arrayList;
    public static ScrollPane scrollPane;
    public static void editReceipt(String id_receipt, int status) throws SQLException, FileNotFoundException {
        arrayList = Postgre.getServicebyIDreceipt(id_receipt);

        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 1200, 800);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/editreceipt.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField nameEmpl = new TextField();
        String name = Postgre.getEmployeeNamebyLogin();
        nameEmpl.setText(name);
        nameEmpl.setEditable(false);
        nameEmpl.setBackground(null);
        nameEmpl.setFont(Font.font("STXihei", 16));
        nameEmpl.setLayoutX(320);
        nameEmpl.setLayoutY(360);
        nameEmpl.setPrefSize(270,32);
        pane1.getChildren().add(nameEmpl);
        Receipt receipt = Postgre.getReceiptbyID(id_receipt);

        TextField type =  new TextField();
        type.setText(receipt.type);
        type.setEditable(false);
        type.setBackground(null);
        type.setFont(Font.font("STXihei", 16));
        type.setLayoutX(340);
        type.setLayoutY(436);
        type.setPrefSize(240, 32);
        pane1.getChildren().add(type);


        TextField cliName = new TextField();
        cliName.setText(receipt.name_cli);
        cliName.setBackground(null);
        cliName.setFont(Font.font("STXihei", 16));
        cliName.setLayoutX(246);
        cliName.setEditable(false);
        cliName.setLayoutY(513);
        cliName.setPrefSize(340, 32);
        pane1.getChildren().add(cliName);

        TextField date =new TextField();
        date.setText(receipt.date);
        date.setBackground(null);
        date.setFont(Font.font("STXihei", 16));
        date.setLayoutX(205);
        date.setLayoutY(590);
        date.setPrefSize(380, 32);
        pane1.getChildren().add(date);

        Pane paneScroll = new Pane();

        scrollPane = new ScrollPane();
        print();
        scrollPane.setLayoutX(665);
        scrollPane.setLayoutY(405);
        scrollPane.setMaxHeight(160);
        scrollPane.setMaxWidth(475);
        scrollPane.setMinHeight(160);
        scrollPane.setMinWidth(475);
        scrollPane.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
//        scrollPane.setContent(paneScroll);
        pane1.getChildren().add(scrollPane);

        Button addServ = new Button();
        addServ.setBackground(null);
        addServ.setLayoutX(213);
        addServ.setLayoutY(359);
        addServ.setPrefSize(178,14);
        pane1.getChildren().add(addServ);
        addServ.setOnAction(t->{
            try {
                AddService.addService2(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(957);
        save.setLayoutY(668);
        save.setPrefSize(157, 47);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String id_empl = receipt.id_empl;
            String summ ="0";
            for(int i=0;i<arrayList.size();i++){
                int a = Integer.parseInt(summ);
                int b = Integer.parseInt(arrayList.get(i).summ);
                a = a+b;
                summ = String.valueOf(a);
            }
            try {
                Postgre.updateReceipt(id_receipt,date.getText(),summ,arrayList);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllReceipt.getStartFront(status);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            newWindow.close();
        });

        Button delete = new Button();
        delete.setBackground(null);
        delete.setLayoutX(991);
        delete.setLayoutY(727);
        delete.setPrefSize(130,36);
        pane1.getChildren().add(delete);
        delete.setOnAction(t->{
            try {
                Postgre.deleteReceipt(id_receipt);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllReceipt.getStartFront(status);
                Front.root.getChildren().add(Front.pane);
                newWindow.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        newWindow.setTitle("Добавление Физ лица");
        newWindow.setScene(scene_add);
        newWindow.show();

    }
    public static void addPos(ReceiptServ receiptServ) throws FileNotFoundException {
        arrayList.add(receiptServ);
        Pane pane = new Pane();
        int u=0;
        for(int i=0;i<arrayList.size();i++, u+=30){
            FileInputStream Url = new FileInputStream("png/bin.png");
            Image url = new Image(Url);
            ImageView pen = new ImageView(url);

            Button edit = new Button();
            edit.setGraphic(pen);
            edit.setLayoutX(0);
            edit.setLayoutY(0 + u);
            edit.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            pane.getChildren().add(edit);
            final ReceiptServ receiptServ1 = arrayList.get(i);
            edit.setOnAction(t->{
                try {
                    delPos(receiptServ1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            TextField name = new TextField();
            name.setText(arrayList.get(i).name);
            name.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            name.setPrefSize(170,30);
            name.setLayoutX(30);
            name.setLayoutY(0+u);
            pane.getChildren().add(name);
        }
        scrollPane.setContent(pane);
    }
    public static void print() throws FileNotFoundException {
        Pane pane = new Pane();
        int u=0;
        for(int i=0;i<arrayList.size();i++, u+=30){
            FileInputStream Url = new FileInputStream("png/bin.png");
            Image url = new Image(Url);
            ImageView pen = new ImageView(url);

            Button edit = new Button();
            edit.setGraphic(pen);
            edit.setLayoutX(0);
            edit.setLayoutY(0 + u);
            edit.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            pane.getChildren().add(edit);
            final ReceiptServ receiptServ1 = arrayList.get(i);
            edit.setOnAction(t->{
                try {
                    delPos(receiptServ1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            TextField name = new TextField();
            name.setText(arrayList.get(i).name);
            name.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            name.setPrefSize(170,30);
            name.setLayoutX(30);
            name.setLayoutY(0+u);
            pane.getChildren().add(name);
        }
        scrollPane.setContent(pane);
    }
    public static void delPos(ReceiptServ receiptServ) throws FileNotFoundException {
        arrayList.remove(receiptServ);
        Pane pane = new Pane();
        int u=0;
        for(int i=0;i<arrayList.size();i++, u+=30){
            FileInputStream Url = new FileInputStream("png/bin.png");
            Image url = new Image(Url);
            ImageView pen = new ImageView(url);

            Button edit = new Button();
            edit.setGraphic(pen);
            edit.setLayoutX(0);
            edit.setLayoutY(0 + u);
            edit.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            pane.getChildren().add(edit);
            final ReceiptServ receiptServ1 = arrayList.get(i);
            edit.setOnAction(t->{
                try {
                    delPos(receiptServ1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });


            TextField name = new TextField();
            name.setText(arrayList.get(i).name);
            name.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            name.setPrefSize(170,30);
            name.setLayoutX(30);
            name.setLayoutY(0+u);
            pane.getChildren().add(name);
        }
        scrollPane.setContent(pane);
    }
}
