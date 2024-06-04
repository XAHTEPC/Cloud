package com.example.cloud.Model;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.View.AllCodEmployee;
import com.example.cloud.View.EditEmpl;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Employee {
    public String id;
    public String name;
    public String post;
    public String exp;
    public String zp;
    public String age;
    public String passHash;
    public String login;

    public Employee(String id, String name, String post, String exp, String zp, String age, String passHash, String login) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.exp = exp;
        this.zp = zp;
        this.age = age;
        this.passHash = passHash;
        this.login = login;
    }

    public Employee(String id, String name, String post, String exp, String zp, String age) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.exp = exp;
        this.zp = zp;
        this.age = age;
    }
    public static Pane getPane(String id_cod) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text name_text = new Text("ФИО");
        name_text.setLayoutX(50);
        name_text.setLayoutY(60);
        name_text.setFont(Font.font("Verdana",13));


        Text post_text = new Text("Должность");
        post_text.setLayoutX(360);
        post_text.setLayoutY(60);
        post_text.setFont(Font.font("Verdana",13));

        Text exp_text = new Text("Стаж");
        exp_text.setLayoutX(460);
        exp_text.setLayoutY(60);
        exp_text.setFont(Font.font("Verdana",13));

        Text zp_text = new Text("Зарплата");
        zp_text.setLayoutX(560);
        zp_text.setLayoutY(60);
        zp_text.setFont(Font.font("Verdana",13));

        Text age_text = new Text("Возраст");
        age_text.setLayoutX(660);
        age_text.setLayoutY(60);
        age_text.setFont(Font.font("Verdana",13));
        ArrayList<Employee> mas = Postgre.getAllEmployeeMin(id_cod);
        int u = 80;
        for(int i=0; i<mas.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            num.setText(mas.get(i).id);
            num.setLayoutX(10);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(30);
            num.setMinHeight(40);
            num.setMinWidth(30);

            TextField name =new TextField();
            name.setText(mas.get(i).name);
            name.setBackground(null);
            name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            name.setEditable(false);
            name.setLayoutX(50);
            name.setLayoutY(0 + u);
            name.setMaxHeight(40);
            name.setMaxWidth(350);
            name.setMinWidth(350);

            TextField post = new TextField();
            post.setText(mas.get(i).post);
            post.setBackground(null);
            post.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            post.setEditable(false);
            post.setLayoutX(360);
            post.setLayoutY(0 + u);
            post.setMaxHeight(40);
            post.setMaxWidth(80);
            post.setMinHeight(40);
            post.setMinWidth(80);

            TextField exp = new TextField();
            exp.setText(mas.get(i).exp);
            exp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            exp.setEditable(false);
            exp.setLayoutX(460);
            exp.setLayoutY(0 + u);
            exp.setMaxHeight(40);
            exp.setMaxWidth(100);
            exp.setMinHeight(40);
            exp.setMinWidth(100);

            TextField sal = new TextField();
            sal.setText(mas.get(i).zp);
            sal.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            sal.setEditable(false);
            sal.setLayoutX(570);
            sal.setLayoutY(0 + u);
            sal.setMaxHeight(40);
            sal.setMaxWidth(70);

            TextField age = new TextField();
            age.setText(mas.get(i).age);
            age.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            age.setEditable(false);
            age.setLayoutX(670);
            age.setLayoutY(0 + u);
            age.setMaxHeight(40);
            age.setMaxWidth(100);
            age.setMinHeight(40);
            age.setMinWidth(60);
            final String id = mas.get(i).id;
            Button edit = new Button("Удалить с этого места работы");
            edit.setLayoutX(800);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                try {
                    Postgre.deleteWork(id,id_cod);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllCodEmployee.getStartFront(id_cod);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(edit);
            pane.getChildren().addAll(num,name,post,exp,sal,age);
        }

        pane.getChildren().addAll(num_text, name_text,  post_text, exp_text, age_text, zp_text);
        return pane;
    }
    public static Pane getPane2(int fl) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text name_text = new Text("ФИО");
        name_text.setLayoutX(50);
        name_text.setLayoutY(60);
        name_text.setFont(Font.font("Verdana",13));


        Text post_text = new Text("Должность");
        post_text.setLayoutX(410);
        post_text.setLayoutY(60);
        post_text.setFont(Font.font("Verdana",13));

        Text exp_text = new Text("Стаж");
        exp_text.setLayoutX(510);
        exp_text.setLayoutY(60);
        exp_text.setFont(Font.font("Verdana",13));

        Text zp_text = new Text("Зарплата");
        zp_text.setLayoutX(610);
        zp_text.setLayoutY(60);
        zp_text.setFont(Font.font("Verdana",13));

        Text age_text = new Text("Возраст");
        age_text.setLayoutX(710);
        age_text.setLayoutY(60);
        age_text.setFont(Font.font("Verdana",13));

        ArrayList<Employee> mas = Postgre.getAllEmployeeMax();
        int u = 80;
        for(int i=0; i<mas.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            num.setText(mas.get(i).id);
            num.setLayoutX(10);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(30);
            num.setMinHeight(40);
            num.setMinWidth(30);

            TextField name =new TextField();
            name.setText(mas.get(i).name);
            name.setBackground(null);
            name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            name.setEditable(false);
            name.setLayoutX(50);
            name.setLayoutY(0 + u);
            name.setMaxHeight(40);
            name.setMaxWidth(350);
            name.setMinWidth(350);

            TextField post = new TextField();
            post.setText(mas.get(i).post);
            post.setBackground(null);
            post.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            post.setEditable(false);
            post.setLayoutX(390);
            post.setLayoutY(0 + u);
            post.setMaxHeight(40);
            post.setMaxWidth(100);
            post.setMinHeight(40);
            post.setMinWidth(100);

            TextField exp = new TextField();
            exp.setText(mas.get(i).exp);
            exp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            exp.setEditable(false);
            exp.setLayoutX(510);
            exp.setLayoutY(0 + u);
            exp.setMaxHeight(40);
            exp.setMaxWidth(100);
            exp.setMinHeight(40);
            exp.setMinWidth(100);

            TextField sal = new TextField();
            sal.setText(mas.get(i).zp);
            sal.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            sal.setEditable(false);
            sal.setLayoutX(620);
            sal.setLayoutY(0 + u);
            sal.setMaxHeight(40);
            sal.setMaxWidth(70);

            TextField age = new TextField();
            age.setText(mas.get(i).age);
            age.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            age.setEditable(false);
            age.setLayoutX(720);
            age.setLayoutY(0 + u);
            age.setMaxHeight(40);
            age.setMaxWidth(100);
            age.setMinHeight(40);
            age.setMinWidth(60);
            final String id = mas.get(i).id;

            Button edit = new Button("Редактировать");
            edit.setLayoutX(850);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                Front.root.getChildren().remove(Front.pane);
                try {
                    Front.pane = EditEmpl.editEmpl(id,fl);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Front.root.getChildren().add(Front.pane);
            });
            pane.getChildren().add(edit);
            if(fl==1){
                pane.getChildren().removeAll(edit);
            }
            pane.getChildren().addAll(num,name,post,exp,sal,age);
        }

        pane.getChildren().addAll(num_text, name_text,  post_text, exp_text, age_text, zp_text);
        return pane;
    }


}
