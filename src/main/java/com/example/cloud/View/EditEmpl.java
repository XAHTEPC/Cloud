package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.Model.Employee;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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

public class EditEmpl {
    public static Pane editEmpl(String id_empl, int fl) throws SQLException {
        Employee employee = Postgre.getEmployeebyId(id_empl);
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/editEmpl.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField name = new TextField();
        name.setText(employee.name);
        name.setBackground(null);
        name.setFont(Font.font("STXihei", 16));
        name.setLayoutX(220);
        name.setLayoutY(290);
        name.setPrefSize(390,32);
        pane1.getChildren().add(name);

        TextField exp =  new TextField();
        exp.setText(employee.exp);
        exp.setBackground(null);
        exp.setFont(Font.font("STXihei", 16));
        exp.setLayoutX(210);
        exp.setLayoutY(360);
        exp.setPrefSize(390,32);
        pane1.getChildren().add(exp);

        TextField age = new TextField();
        age.setText(employee.age);
        age.setBackground(null);
        age.setFont(Font.font("STXihei", 16));
        age.setLayoutX(260);
        age.setLayoutY(440);
        age.setPrefSize(340,32);
        pane1.getChildren().add(age);

        TextField dolz = new TextField();
        dolz.setText(employee.post);
        dolz.setBackground(null);
        dolz.setFont(Font.font("STXihei", 16));
        dolz.setLayoutX(320);
        dolz.setLayoutY(520);
        dolz.setPrefSize(280,32);
        pane1.getChildren().add(dolz);

        TextField login =new TextField();
        login.setText(employee.login);
        login.setBackground(null);
        login.setFont(Font.font("STXihei", 16));
        login.setLayoutX(210);
        login.setLayoutY(590);
        login.setPrefSize(390,32);
        pane1.getChildren().add(login);

        PasswordField pass =new PasswordField();
        pass.setBackground(null);
        pass.setFont(Font.font("STXihei", 16));
        pass.setLayoutX(250);
        pass.setLayoutY(670);
        pass.setPrefSize(350,32);
        pane1.getChildren().add(pass);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(792);
        save.setLayoutY(634);
        save.setPrefSize(157,47);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String name_text = name.getText();
            String exp_text = exp.getText();
            String age_text = age.getText();
            String login_text = login.getText();
            String pass_text = pass.getText();
            String dolz_text = dolz.getText();
            if(!name_text.isEmpty()&&!exp_text.isEmpty()&&!age_text.isEmpty()
                    &&!dolz_text.isEmpty()){
                try {
                    String zp = "45000";
                    if(dolz_text.equals("Администратор"))
                        zp = "70000";
                    else if(dolz_text.equals("Инженер"))
                        zp = "55000";
                    else if(dolz_text.equals("Аналитик"))
                        zp = "50000";

                    Postgre.updateEmpl(id_empl,employee.post,dolz_text, name_text,exp_text,age_text,login_text,pass_text,zp);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllEmpl.getStartFront(fl);
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
        delete.setLayoutX(834);
        delete.setLayoutY(690);
        delete.setPrefSize(100,36);
        pane1.getChildren().add(delete);
        delete.setOnAction(t->{
            try {
                Postgre.deleteEmpl(id_empl);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllEmpl.getStartFront(fl);
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
