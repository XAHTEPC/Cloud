package com.example.cloud.Model;

public class LegalClient {
    public String id;
    public String orgname;
    public String status;
    public String tel;
    public String bonus;
    public String inn;
    public String mail;
    public String name;
    public String address;
    public String kpp;
    public String ogrn;


    public LegalClient(String id, String name, String status, String tel, String bonus, String inn, String mail) {
        this.id = id;
        this.orgname = name;
        this.status = status;
        this.tel = tel;
        this.bonus = bonus;
        this.inn = inn;
        this.mail = mail;
    }

    public LegalClient(String id, String orgname, String status, String tel, String bonus, String inn, String mail, String name, String address, String kpp, String ogrn) {
        this.id = id;
        this.orgname = orgname;
        this.status = status;
        this.tel = tel;
        this.bonus = bonus;
        this.inn = inn;
        this.mail = mail;
        this.name = name;
        this.address = address;
        this.kpp = kpp;
        this.ogrn = ogrn;
    }
}
