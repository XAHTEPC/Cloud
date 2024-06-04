package com.example.cloud.Model;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.View.AllZdanieEmployee;
import com.example.cloud.View.AllZdanieService;
import com.example.cloud.View.EditZdanie;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Zdanie {
    public String id;
    public String address;
    public String index;
    public String tel;
    public String num_empl;

    public Zdanie(String id, String address, String tel, String num_empl, String index) {
        this.id = id;
        this.address = address;
        this.tel = tel;
        this.num_empl = num_empl;
        this.index = index;
    }

    public static Pane getPane(int fl) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text address_text = new Text("Адрес");
        address_text.setLayoutX(50);
        address_text.setLayoutY(60);
        address_text.setFont(Font.font("Verdana",13));


        Text index_text = new Text("Индекс");
        index_text.setLayoutX(410);
        index_text.setLayoutY(60);
        index_text.setFont(Font.font("Verdana",13));

        Text tel_text = new Text("Телефон");
        tel_text.setLayoutX(510);
        tel_text.setLayoutY(60);
        tel_text.setFont(Font.font("Verdana",13));

        Text num_empl_text = new Text("Кол-во \nсотрудников");
        num_empl_text.setLayoutX(610);
        num_empl_text.setLayoutY(60);
        num_empl_text.setFont(Font.font("Verdana",13));

        ArrayList<Zdanie> mas = Postgre.getAllCOD();
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

            TextField address =new TextField();
            address.setText(mas.get(i).address);
            address.setBackground(null);
            address.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            address.setEditable(false);
            address.setLayoutX(50);
            address.setLayoutY(0 + u);
            address.setMaxHeight(40);
            address.setMaxWidth(350);
            address.setMinWidth(350);

            TextField index = new TextField();
            index.setText(mas.get(i).index);
            index.setBackground(null);
            index.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            index.setEditable(false);
            index.setLayoutX(410);
            index.setLayoutY(0 + u);
            index.setMaxHeight(40);
            index.setMaxWidth(80);
            index.setMinHeight(40);
            index.setMinWidth(80);

            TextField tel = new TextField();
            tel.setText(mas.get(i).tel);
            tel.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            tel.setEditable(false);
            tel.setLayoutX(510);
            tel.setLayoutY(0 + u);
            tel.setMaxHeight(40);
            tel.setMaxWidth(100);
            tel.setMinHeight(40);
            tel.setMinWidth(100);

            TextField num_empl = new TextField();
            num_empl.setText(mas.get(i).num_empl);
            num_empl.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            num_empl.setEditable(false);
            num_empl.setLayoutX(620);
            num_empl.setLayoutY(0 + u);
            num_empl.setMaxHeight(40);
            num_empl.setMaxWidth(70);

            final String id = mas.get(i).id;
            if(fl!=1) {

                Button edit = new Button("Изменить");
                edit.setLayoutX(700);
                edit.setLayoutY(0 + u);
                edit.setOnAction(t->{
                    Front.root.getChildren().removeAll(Front.pane);
                    try {
                        Front.pane = EditZdanie.editCOD(id,fl);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Front.root.getChildren().add(Front.pane);
                });
                pane.getChildren().add(edit);
                Button employee = new Button("Сотрудники");
                employee.setLayoutX(790);
                employee.setLayoutY(0 + u);
                employee.setOnAction(t->{
                    Front.root.getChildren().remove(Front.pane);
                    try {
                        Front.pane = AllZdanieEmployee.getStartFront(id);
                        Front.root.getChildren().add(Front.pane);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                pane.getChildren().add(employee);

                Button service = new Button("Услуги");
                service.setLayoutX(900);
                service.setLayoutY(0 + u);
                service.setOnAction(t->{
                    Front.root.getChildren().remove(Front.pane);
                    try {
                        Front.pane = AllZdanieService.getStartFront(id,fl);
                        Front.root.getChildren().add(Front.pane);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                pane.getChildren().add(service);
            }
            pane.getChildren().addAll(num,address,index,tel,num_empl);
        }

        pane.getChildren().addAll(num_text, address_text,  index_text, tel_text, num_empl_text);
        return pane;
    }
}
