package com.example.cloud.View;

import com.example.cloud.DataBase.Postgre;
import com.example.cloud.Front;
import com.example.cloud.Model.ReceiptServ;
import com.example.cloud.Model.Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.util.ArrayList;

public class AddService {
    public static Pane addService(int fl){
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);

        FileInputStream Url1;
        TextField os =new TextField();
        TextField kol =new TextField();

        try {
            Url1 = new FileInputStream("png/addservice.png");
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
        price.setBackground(null);
        price.setFont(Font.font("STXihei", 16));
        price.setLayoutX(210);
        price.setLayoutY(436);
        price.setPrefSize(380,32);
        pane1.getChildren().add(price);

        TextField desc =  new TextField();
        desc.setBackground(null);
        desc.setFont(Font.font("STXihei", 16));
        desc.setLayoutX(290);
        desc.setLayoutY(513);
        desc.setPrefSize(290,32);
        pane1.getChildren().add(desc);

        TextField cpu = new TextField();
        cpu.setBackground(null);
        cpu.setFont(Font.font("STXihei", 16));
        cpu.setLayoutX(180);
        cpu.setLayoutY(590);
        cpu.setPrefSize(405,32);
        pane1.getChildren().add(cpu);

        TextField ram =new TextField();
        ram.setBackground(null);
        ram.setFont(Font.font("STXihei", 16));
        ram.setLayoutX(190);
        ram.setLayoutY(667);
        ram.setPrefSize(390,32);
        pane1.getChildren().add(ram);


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
                    Postgre.addService(type_text,price_text,desc_text,cpu_text,ram_text,extra);
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

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(838);
        back.setLayoutY(688);
        back.setPrefSize(65,36);
        pane1.getChildren().add(back);
        back.setOnAction(t->{
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
static String id_cod;
    static String id_serv;
    static ComboBox<String> comboBox_serv;
    static ArrayList<ReceiptServ> arrayList;

    public static void addService2(boolean isAdd) throws SQLException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 1200, 800);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().add(pane1);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addservicereceipt.png");
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
        comboBox_cod.setMaxWidth(400);
        comboBox_cod.setEditable(false);
        comboBox_cod.setMinWidth(400);
        comboBox_cod.setBackground(null);
        comboBox_cod.setLayoutX(190);
        comboBox_cod.setLayoutY(439);
        pane1.getChildren().add(comboBox_cod);


        comboBox_cod.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String address_cod = (comboBox_cod.getSelectionModel().getSelectedItem());
                System.out.println("NAme:" + address_cod);
                String id_cod = null;
                try {
                    id_cod = Postgre.getCodIDbyAddress(address_cod);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String[] newmas_serv = new String[0];
                try {
                    arrayList = Postgre.getServiceName(id_cod);
                    newmas_serv = new String[arrayList.size()];
                    for(int i=0;i<arrayList.size();i++){
                        newmas_serv[i] =arrayList.get(i).name;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String[] masserv = newmas_serv;
                ObservableList<String> serv = FXCollections.observableArrayList(masserv);
                pane1.getChildren().remove(comboBox_serv);
                comboBox_serv = new ComboBox<String>(serv);
                comboBox_serv.setMaxWidth(340);
                comboBox_serv.setEditable(false);
                comboBox_serv.setMinWidth(340);
                comboBox_serv.setBackground(null);
                comboBox_serv.setLayoutX(240);
                comboBox_serv.setLayoutY(513);
                pane1.getChildren().add(comboBox_serv);
            }
        });

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(792);
        save.setLayoutY(634);
        save.setPrefSize(157,47);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String name = comboBox_serv.getSelectionModel().getSelectedItem();
            String id = "";
            String price = "";
            for(int i=0;i<arrayList.size();i++){
                if(arrayList.get(i).name.equals(name)){
                    id = arrayList.get(i).id_serv;
                    price = arrayList.get(i).summ;
                }
            }
            ReceiptServ receiptServ = new ReceiptServ(id,price,name);
            try {
                if(isAdd){
                    AddReceipt.addPos(receiptServ);
                }
//                else {
//                    EditReceipt.addPos(receiptServ);
//                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            newWindow.close();
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(838);
        back.setLayoutY(688);
        back.setPrefSize(65,36);
        pane1.getChildren().add(back);
        back.setOnAction(t->{
            newWindow.close();
        });
        newWindow.setScene(scene_add);
        newWindow.show();
    }

    public static Pane addService3(String id_cod, int fl) throws SQLException {
        Pane pane1 = new Pane();
        pane1.setPrefSize(1200,800);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);

        FileInputStream Url1;
        TextField os =new TextField();
        TextField kol =new TextField();

        try {
            Url1 = new FileInputStream("png/addserv.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);

        ArrayList<ReceiptServ> mas = Postgre.getServiceName2();
        String[] masServ = new String[mas.size()];
        for(int i=0;i<mas.size();i++){
            masServ[i] = mas.get(i).name;
        }
        ObservableList<String> types = FXCollections.observableArrayList(masServ);
        ComboBox<String> comboBox_type = new ComboBox<String>(types);
        comboBox_type.setMaxWidth(350);
        comboBox_type.setEditable(false);
        comboBox_type.setMinWidth(350);
        comboBox_type.setBackground(null);
        comboBox_type.setLayoutX(240);
        comboBox_type.setLayoutY(537);
        pane1.getChildren().add(comboBox_type);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(792);
        save.setLayoutY(634);
        save.setPrefSize(157,47);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String serv_text = comboBox_type.getSelectionModel().getSelectedItem();
            if(!serv_text.isEmpty()){
                try {
                    String id_serv = "";
                    for(int i=0;i<mas.size();i++){
                        if(mas.get(i).name.equals(serv_text)){
                            id_serv = mas.get(i).id_serv;
                        }
                    }
                    Postgre.addServiceCod(id_serv,id_cod);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllCodService.getStartFront(id_cod,fl);
                    Front.root.getChildren().add(Front.pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(838);
        back.setLayoutY(688);
        back.setPrefSize(65,36);
        pane1.getChildren().add(back);
        back.setOnAction(t->{
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
