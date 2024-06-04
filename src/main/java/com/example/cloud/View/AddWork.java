package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AddWork {
    public static Pane addWork(String id_cod) throws SQLException {
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200, 800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addwork.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);
        String[] mascod = Postgre.getCODAddress();
        ObservableList<String> work = FXCollections.observableArrayList(mascod);
        ComboBox<String> comboBox_cod = new ComboBox<String>(work);
        comboBox_cod.setMaxWidth(350);
        comboBox_cod.setEditable(false);
        comboBox_cod.setMinWidth(350);
        comboBox_cod.setBackground(null);
        comboBox_cod.setLayoutX(210);
        comboBox_cod.setLayoutY(510);
        pane1.getChildren().add(comboBox_cod);


        String[] masempl = Postgre.getEmployeeNameMin();
        ObservableList<String> empl = FXCollections.observableArrayList(masempl);
        ComboBox<String> combobox_empl = new ComboBox<String>(empl);
        combobox_empl.setMaxWidth(253);
        combobox_empl.setEditable(false);
        combobox_empl.setMinWidth(253);
        combobox_empl.setBackground(null);
        combobox_empl.setLayoutX(310);
        combobox_empl.setLayoutY(590);
        pane1.getChildren().add(combobox_empl);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(792);
        save.setLayoutY(634);
        save.setPrefSize(157, 47);
        pane1.getChildren().add(save);
        save.setOnAction(t -> {
            String employee = combobox_empl.getSelectionModel().getSelectedItem();
            String cod = comboBox_cod.getSelectionModel().getSelectedItem();
            try {
                Postgre.addWork(cod, employee);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllCodEmployee.getStartFront(id_cod);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(838);
        back.setLayoutY(688);
        back.setPrefSize(65, 36);
        pane1.getChildren().add(back);
        back.setOnAction(t -> {
            Front.root.getChildren().remove(Front.pane);
            try {
                Front.pane = AllCodEmployee.getStartFront(id_cod);
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
