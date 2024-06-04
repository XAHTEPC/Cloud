package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.Model.UriCli;
import com.example.cloud.Model.PhysCli;
import com.example.cloud.Model.ReceiptServ;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddReceipt {
    public static ArrayList<ReceiptServ> arrayList;
    public static ScrollPane scrollPane;

    public static Pane addReceipt(boolean fl, String id_client, int fl2) throws SQLException {
        arrayList = new ArrayList<>();
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200, 800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addreceipt.png");
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
        nameEmpl.setPrefSize(270, 32);
        pane1.getChildren().add(nameEmpl);

        TextField type = new TextField();
        if (fl) {
            type.setText("Юридическое лицо");
        } else {
            type.setText("Физическое лицо");
        }
        type.setEditable(false);
        type.setBackground(null);
        type.setFont(Font.font("STXihei", 16));
        type.setLayoutX(340);
        type.setLayoutY(436);
        type.setPrefSize(240, 32);
        pane1.getChildren().add(type);

        TextField cliName = new TextField();
        if (fl) {
            UriCli uriCli = Postgre.getLegalClientbyId(id_client);
            cliName.setText(uriCli.orgname);
        } else {
            PhysCli physCli = Postgre.getPhysClientbyId(id_client);
            cliName.setText(physCli.name);
        }
        cliName.setBackground(null);
        cliName.setFont(Font.font("STXihei", 16));
        cliName.setLayoutX(246);
        cliName.setEditable(false);
        cliName.setLayoutY(513);
        cliName.setPrefSize(340, 32);
        pane1.getChildren().add(cliName);

        TextField date = new TextField();
        LocalDate currentDate = LocalDate.now();
        date.setText(currentDate.toString());
        date.setBackground(null);
        date.setFont(Font.font("STXihei", 16));
        date.setLayoutX(205);
        date.setLayoutY(590);
        date.setPrefSize(380, 32);
        pane1.getChildren().add(date);

        Pane paneScroll = new Pane();
        scrollPane = new ScrollPane();
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
        scrollPane.setContent(paneScroll);
        pane1.getChildren().add(scrollPane);

        Button addServ = new Button();
        addServ.setBackground(null);
        addServ.setLayoutX(1093);
        addServ.setLayoutY(560);
        addServ.setPrefSize(41, 60);
        pane1.getChildren().add(addServ);
        addServ.setOnAction(t -> {
            try {
                AddService.addService2(true);
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
        save.setOnAction(t -> {
                String id_empl = null;
                try {
                    id_empl = Postgre.getEmployeeIDbyLogin();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String summ = "0";
                for (int i = 0; i < arrayList.size(); i++) {
                    int a = Integer.parseInt(summ);
                    int b = Integer.parseInt(arrayList.get(i).summ);
                    a = a + b;
                    summ = String.valueOf(a);
                }
                try {
                    Postgre.addReceipt(arrayList, id_client, id_empl, summ, type.getText(),date.getText());
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllClient.getStartFront(fl2);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(1003);
        back.setLayoutY(722);
        back.setPrefSize(80, 14);
        pane1.getChildren().add(back);
        back.setOnAction(t -> {
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = AllClient.getStartFront(fl2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Front.root.getChildren().add(Front.pane);

        });
        return pane1;
    }

    public static void addPos(ReceiptServ receiptServ) throws FileNotFoundException {
        arrayList.add(receiptServ);
        Pane pane = new Pane();
        int u = 0;
        for (int i = 0; i < arrayList.size(); i++, u += 30) {
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
            edit.setOnAction(t -> {
                try {
                    delPos(receiptServ1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            TextField name = new TextField();
            name.setText(arrayList.get(i).name);
            name.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            name.setPrefSize(440, 30);
            name.setLayoutX(30);
            name.setLayoutY(0 + u);
            pane.getChildren().add(name);
        }
        scrollPane.setContent(pane);
    }

    public static void delPos(ReceiptServ receiptServ) throws FileNotFoundException {
        arrayList.remove(receiptServ);
        Pane pane = new Pane();
        int u = 0;
        for (int i = 0; i < arrayList.size(); i++, u += 30) {
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
            edit.setOnAction(t -> {
                try {
                    delPos(receiptServ1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });


            TextField name = new TextField();
            name.setText(arrayList.get(i).name);
            name.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            name.setPrefSize(440, 30);
            name.setLayoutX(30);
            name.setLayoutY(0 + u);
            pane.getChildren().add(name);
        }
        scrollPane.setContent(pane);
    }
}
