package com.example.cloud.Model;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.View.EditReceipt;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Receipt {
    public String id_receipt;
    public String name_cli;
    public String name_empl;
    public String id_empl;
    public String summ;
    public String type;
    public String date;

    public Receipt(String id, String name_cli, String name_empl, String summ, String  type, String id_empl) {
        this.id_empl = id_empl;
        this.id_receipt = id;
        this.name_cli = name_cli;
        this.name_empl = name_empl;
        this.summ = summ;
        this.type = type;
    }

    public Receipt(String id_receipt, String name_cli, String name_empl, String id_empl, String summ, String type, String date) {
        this.id_receipt = id_receipt;
        this.name_cli = name_cli;
        this.name_empl = name_empl;
        this.id_empl = id_empl;
        this.summ = summ;
        this.type = type;
        this.date = date;
    }

    public static Pane getPane(int status) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text nameCli_text = new Text("ФИО клиента");
        nameCli_text.setLayoutX(50);
        nameCli_text.setLayoutY(60);
        nameCli_text.setFont(Font.font("Verdana",13));


        Text nameEmpl_text = new Text("ФИО сотрудника");
        nameEmpl_text.setLayoutX(350);
        nameEmpl_text.setLayoutY(60);
        nameEmpl_text.setFont(Font.font("Verdana",13));

        Text price_text = new Text("Цена");
        price_text.setLayoutX(650);
        price_text.setLayoutY(60);
        price_text.setFont(Font.font("Verdana",13));

        ArrayList<Receipt> mas = Postgre.getAllReceipt();
        int u = 80;
        for(int i=0; i<mas.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            num.setText(mas.get(i).id_receipt);
            num.setLayoutX(10);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(30);
            num.setMinHeight(40);
            num.setMinWidth(30);

            TextField nameCli =new TextField();
            nameCli.setText(mas.get(i).name_cli);
            nameCli.setBackground(null);
            nameCli.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            nameCli.setEditable(false);
            nameCli.setLayoutX(50);
            nameCli.setLayoutY(0 + u);
            nameCli.setMaxHeight(40);
            nameCli.setMaxWidth(350);
            nameCli.setMinWidth(350);

            TextField nameEmpl = new TextField();
            nameEmpl.setText(mas.get(i).name_empl);
            nameEmpl.setBackground(null);
            nameEmpl.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            nameEmpl.setEditable(false);
            nameEmpl.setLayoutX(350);
            nameEmpl.setLayoutY(0 + u);
            nameEmpl.setMaxHeight(40);
            nameEmpl.setMaxWidth(300);
            nameEmpl.setMinHeight(40);
            nameEmpl.setMinWidth(300);

            TextField price = new TextField();
            price.setText(mas.get(i).summ);
            price.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            price.setEditable(false);
            price.setLayoutX(650);
            price.setLayoutY(0 + u);
            price.setMaxHeight(40);
            price.setMaxWidth(100);
            price.setMinHeight(40);
            price.setMinWidth(60);
            final String id = mas.get(i).id_receipt;
            Button edit = new Button("Изменить");
            edit.setLayoutX(810);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                try {
                    EditReceipt.editReceipt(id,status);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            if(status != 1) {
                pane.getChildren().add(edit);
            }

            pane.getChildren().addAll(num,nameCli,nameEmpl,price);
        }

        pane.getChildren().addAll(num_text, nameCli_text,  nameEmpl_text, price_text);
        return pane;
    }
}
