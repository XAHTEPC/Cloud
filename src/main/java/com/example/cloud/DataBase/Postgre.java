package com.example.cloud.DataBase;

import com.example.cloud.Front;
import com.example.cloud.Logic.Crypto;
import com.example.cloud.Model.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.*;
import java.util.ArrayList;

public class Postgre {
    static Connection data_connection;

    static Connection connection;
    static Statement data_statmt;

    static Statement statmt;
    static ResultSet data_resSet;
    public Postgre(String login, String pass) throws SQLException {
        data_connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wqwq",login,pass);
        data_statmt = data_connection.createStatement();
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wqwq","postgres","123");
        statmt = connection.createStatement();
    }
    public static String getRole() throws SQLException {
        data_resSet = data_statmt   .executeQuery("select rolname from pg_user\n" +
                "join pg_auth_members on (pg_user.usesysid=pg_auth_members.member)\n" +
                "join pg_roles on (pg_roles.oid=pg_auth_members.roleid)\n" +
                "where usename = current_user;");
        String role="";
        while (data_resSet.next()){
            role = data_resSet.getString("rolname");
        }
        return role;
    }

    public static void addCod(String address, String index, String tel, String kol) throws SQLException {
        statmt.execute("INSERT INTO public.\"Zdanie\"(\n" +
                "\taddress_zdanie, index_zdanie, tel_zdanie, empl_count_zdanie)\n" +
                "\tVALUES ('"+address+"', '"+index+"',  '"+tel+"', '"+kol+"');");
    }
    public static ArrayList<COD> getAllCOD() throws SQLException{
        data_resSet = data_statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Zdanie\";");
        ArrayList<COD> arrayList = new ArrayList<>();
        while (data_resSet.next()){
            String id = data_resSet.getString("id_zdanie");
            String address = data_resSet.getString("address_zdanie");
            String index = data_resSet.getString("index_zdanie");
            String tel = data_resSet.getString("tel_zdanie");
            String num = data_resSet.getString("empl_count_zdanie");
            COD cod = new COD(id,address,tel,num,index);
            arrayList.add(cod);
        }
        return arrayList;
    }
    public static COD getCODbyID(String id_cod) throws SQLException {
        data_resSet = data_statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Zdanie\" WHERE id_zdanie ='"+id_cod+"';");
        ArrayList<COD> arrayList = new ArrayList<>();
        COD cod = null;
        while (data_resSet.next()){
            String id = data_resSet.getString("id_zdanie");
            String address = data_resSet.getString("address_zdanie");
            String index = data_resSet.getString("index_zdanie");
            String tel = data_resSet.getString("tel_zdanie");
            String num = data_resSet.getString("empl_count_zdanie");
            cod = new COD(id,address,tel,num,index);
        }
        return cod;
    }
    public static void deleteCOD(String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"Zdanie\"\n" +
                "\tWHERE id_zdanie='"+id+"';");
    }
    public static void updateCOD(String id, String address, String index, String tel, String kol) throws SQLException {
        statmt.execute("UPDATE public.\"Zdanie\"\n" +
                "\tSET address_zdanie='"+address+"', index_zdanie = '"+index+"', tel_zdanie='"+tel+"', empl_count_zdanie='"+kol+"'\n" +
                "\tWHERE id_zdanie='"+id+"';");
    }

    public static ArrayList<Employee> getAllEmployeeMin(String id_cod) throws SQLException {
        ArrayList<Employee> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Zdanie\" " +
                "JOIN public.\"zdanie_employee\" using(id_zdanie) " +
                "join public.\"Employee\" using (id_empl)" +
                "WHERE id_zdanie ='"+id_cod+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_empl");
            String name = data_resSet.getString("name_empl");
            String post = "Менеджер";
            String age = data_resSet.getString("age_empl");
            String exp = data_resSet.getString("staj_empl");
            String sal = data_resSet.getString("sal_empl");
            Employee employee = new Employee(id,name,post,exp,sal,age);
            arrayList.add(employee);
        }
        return arrayList;
    }
    public static void deleteWork(String id_empl, String id_cod) throws  SQLException {
        System.out.println("OK");
        System.out.println(id_empl);
        System.out.println(id_cod);
        statmt.execute("DELETE FROM public.\"zdanie_employee\" WHERE id_empl ="+id_empl+" and id_zdanie ="+id_cod+";");
    }

    public static String[] getCODAddress() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT address_zdanie as a\n" +
                "\tFROM public.\"Zdanie\";");
        while (data_resSet.next()){
            String name = data_resSet.getString("a");
            arrayList.add(name);
        }
        String[] mas = new String[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){
            mas[i] = arrayList.get(i);
        }
        return mas;
    }
    public static String[] getEmployeeNameMin() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT name_empl as a\n" +
                "\tFROM public.\"Employee\" WHERE rank_empl ='Менеджер';");
        while (data_resSet.next()){
            String name = data_resSet.getString("a");
            arrayList.add(name);
        }
        String[] mas = new String[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){
            mas[i] = arrayList.get(i);
        }
        return mas;
    }
    public static String getEmployeeIDbyName(String name) throws SQLException {
        String id = "";
        data_resSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Employee\" WHERE name_empl = '"+name+"';");
        while (data_resSet.next()){
            id = data_resSet.getString("id_empl");
        }
        return id;
    }
    public static void addWork(String cod, String employee) throws SQLException {
        String id_empl = Postgre.getEmployeeIDbyName(employee);
        String id_cod = Postgre.getCodIDbyAddress(cod);
        statmt.execute("INSERT INTO public.\"zdanie_employee\"(\n" +
                "\tid_empl, id_zdanie)\n" +
                "\tVALUES ('"+id_empl+"', '"+id_cod+"');");
    }
    public static String getCodIDbyAddress(String address) throws SQLException {
        String id = "";
        data_resSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Zdanie\" where address_zdanie = '"+address+"';");
        while (data_resSet.next()){
            id = data_resSet.getString("id_zdanie");
        }
        return id;
    }
    public static ArrayList<Service> getAllService(String id_cod) throws SQLException {
        ArrayList<Service> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Service\" JOIN public.\"service_zdanie\" using (id_serv) WHERE id_zdanie ='"+id_cod+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_service_zdanie");
            String type = data_resSet.getString("type_serv");
            String price = data_resSet.getString("price_serv");
            String desc = data_resSet.getString("desc_serv");
            String cpu = data_resSet.getString("cpu_serv");
            String ram = data_resSet.getString("ram_serv");
            String extra = data_resSet.getString("extra_param_serv");
            Service service = new Service(id,type,price,desc,cpu,ram,extra,"");
            arrayList.add(service);
        }
        return arrayList;
    }
    public static ArrayList<Service> getAllService2() throws SQLException {
        ArrayList<Service> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Service\" ;");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_serv");
            String type = data_resSet.getString("type_serv");
            String price = data_resSet.getString("price_serv");
            String desc = data_resSet.getString("desc_serv");
            String cpu = data_resSet.getString("cpu_serv");
            String ram = data_resSet.getString("ram_serv");
            String extra = data_resSet.getString("extra_param_serv");
            Service service = new Service(id,type,price,desc,cpu,ram,extra);
            arrayList.add(service);
        }
        return arrayList;
    }
    public static void deleteServiceCod(String id_zdanie_serv) throws SQLException{
        statmt.execute("DELETE FROM public.service_zdanie\n" +
                "\tWHERE id_service_zdanie = '"+id_zdanie_serv+"';");
    }
    public static ArrayList<Employee> getAllEmployeeMax() throws SQLException {
        ArrayList<Employee> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Employee\";");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_empl");
            String name = data_resSet.getString("name_empl");
            String post = data_resSet.getString("rank_empl");
            String age = data_resSet.getString("age_empl");
            String exp = data_resSet.getString("staj_empl");
            String sal = data_resSet.getString("sal_empl");
            Employee employee = new Employee(id,name,post,exp,sal,age);
            arrayList.add(employee);
        }
        return arrayList;
    }

    public static Employee getEmployeebyId(String id_empl) throws SQLException {
        data_resSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Users\" JOIN public.\"Employee\" using (id_empl) WHERE id_empl = '"+id_empl+"';");
        Employee employee = null;
        while (data_resSet.next()){
            String id = data_resSet.getString("id_empl");
            String name = data_resSet.getString("name_empl");
            String age = data_resSet.getString("age_empl");
            String post = data_resSet.getString("rank_empl");
            String exp = data_resSet.getString("staj_empl");
            String login = data_resSet.getString("login");
            String passHash = data_resSet.getString("password");
            employee = new Employee(id,name,post,exp,"",age,passHash,login);
        }
        return employee;
    }
    public static void deleteEmpl(String id_empl) throws SQLException {
        statmt.execute("DELETE FROM public.\"zdanie_employee\"\n" +
                "\tWHERE id_empl = '"+id_empl+"';");
        statmt.execute("DELETE FROM public.\"Receipt\"\n" +
                "\tWHERE id_empl = '"+id_empl+"';");
        statmt.execute("DELETE FROM public.\"Employee\"\n" +
                "\tWHERE id_empl = '"+id_empl+"';");
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Users\" WHERE id_empl = '"+id_empl+"';");
        String login = "";
        while (data_resSet.next()){
            login = data_resSet.getString("login");
        }
        statmt.execute("DROP ROLE IF EXISTS \""+login+"\";\n");
    }
    public static void updateEmpl (String id,String oldpost, String post ,String name, String exp, String age, String login,
                                   String pass, String zp) throws SQLException {

        statmt.execute("UPDATE public.\"Employee\"\n" +
                "\tSET name_empl='"+name+"', rank_empl='"+post+"', staj_empl='"+exp+"', sal_empl='"+zp+"', " +
                "age_empl='"+age+"'\n" +
                "\tWHERE id_empl='"+id+"';");
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Users\" WHERE id_empl = '" + id + "';");
        String oldLogin = "";
        String oldPassHash = "";
        while (data_resSet.next()) {
            oldLogin = data_resSet.getString("login");
            oldPassHash = data_resSet.getString("password");

        }
        String newPassHash = Crypto.hash(pass);
        if (!oldLogin.equals(login)) {
            statmt.execute("ALTER ROLE \"" + oldLogin + "\"\n" +
                    "\tRENAME TO \"" + login + "\";");
            statmt.execute("UPDATE public.\"Users\"\n" +
                    "\tSET login='"+login+"\n" +
                    "\tWHERE id_empl = '"+id+"';");
        }
        if(!oldPassHash.equals(newPassHash)&&!pass.isEmpty()) {
            statmt.execute("ALTER ROLE \"" + login + "\"\n" +
                    "\tPASSWORD '" + pass + "';");
            statmt.execute("UPDATE public.\"Users\"\n" +
                    "\tSET password='" + newPassHash + "'\n" +
                    "\tWHERE id_empl = '" + id + "';");
        }
        if(!post.equals(oldpost)) {
            oldpost = convertPost(oldpost);
            post = convertPost(post);

            statmt.execute("REVOKE \"" + oldpost + "\" FROM \"" + login + "\";\n");
            statmt.execute("GRANT \"" + post + "\" TO \"" + login + "\";");
        }
    }
    public static String  convertPost(String post){
        String answer = "";
        if(post.equals("Менеджер")){
            answer = "Manager";
        }
        if(post.equals("Аналитик")){
            answer = "Analyst";
        }
        if(post.equals("Инженер")){
            answer = "Engineer";
        }
        if(post.equals("Администратор")){
            answer = "Admin";
        }
        return answer;
    }
    public static void addEmpl(String post ,String name, String exp, String age, String login,
                               String pass, String zp) throws SQLException {

        System.out.println(post);
        statmt.execute("INSERT INTO public.\"Employee\"(\n" +
                "\tname_empl, rank_empl, staj_empl, sal_empl, age_empl, email_empl)\n" +
                "\tVALUES ('" + name + "', '" + post + "', '" + exp + "', '" + zp + "', '" + age + "', '"+login+"');");
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Employee\" WHERE name_empl = '" + name + "';");
        String id = "Error";
        while (data_resSet.next()) {
            id = data_resSet.getString("id_empl");

        }
        if (!id.equals("Error")) {
            String passSalt = Crypto.hash(pass);
            statmt.execute("INSERT INTO public.\"Users\"(\n" +
                    "\tlogin, password, id_empl)\n" +
                    "\tVALUES ('" + login + "', '" + passSalt + "', '" + id + "');");
            statmt.execute("CREATE ROLE \"" + login + "\"\n" +
                    "                LOGIN\n" +
                    "                NOSUPERUSER \n" +
                    "                NOCREATEDB \n" +
                    "                NOCREATEROLE \n" +
                    "                INHERIT \n" +
                    "                NOREPLICATION\n" +
                    "                CONNECTION LIMIT -1 \n" +
                    "                PASSWORD '" + pass + "';\n");
            if (post.equals("Администратор"))
                statmt.execute("GRANT \"Admin\" TO \"" + login + "\" WITH ADMIN OPTION;\n");
            else if (post.equals("Инженер"))
                statmt.execute("GRANT \"Engineer\" TO \"" + login + "\" WITH ADMIN OPTION;\n");
            else if (post.equals("Аналитик"))
                statmt.execute("GRANT \"Analyst\" TO \"" + login + "\" WITH ADMIN OPTION;\n");
            else
                statmt.execute("GRANT \"Manager\" TO \"" + login + "\" WITH ADMIN OPTION;\n");

        }
    }

    public static void addPhysClient(String name, String tel, String inn, String mail) throws SQLException {
        statmt.execute("INSERT INTO public.\"PhysCli\"(\n" +
                "\tname_client, tel_client, inn_client, mail_client)\n" +
                "\tVALUES ('"+name+"', '"+tel+"', '"+inn+"', '"+mail+"');");

    }

    public static void addLegalClient(String name, String orgName, String inn, String kpp, String ogrn,
                                      String tel, String mail, String address) throws SQLException {
        statmt.execute("INSERT INTO public.\"UriCli\"(\n" +
                "\tname_client, uri_name_client, inn_client, kpp_client, ogrn_client, " +
                "\ttel_client, mail_client, uri_address_client)\n" +
                "\tVALUES ('"+name+"', '"+ orgName+"', '"+inn+"', '"+ kpp+"', '"+ ogrn+"', '"
                +tel+"', '"+mail+"', '"+address+"');");
    }

    public static void makeTMP() throws SQLException {
        statmt.execute("CREATE TEMP TABLE all_clients (\n" +
                "    temp_id SERIAL PRIMARY KEY,\n" +
                "    original_id INT,\n" +
                "    fio VARCHAR(255),\n" +
                "    phone VARCHAR(50),\n" +
                "    email VARCHAR(255),\n" +
                "    bonus INT,\n" +
                "    status VARCHAR(50),\n" +
                "    client_type VARCHAR(20)\n" +
                ");");
        statmt.execute("INSERT INTO all_clients (original_id, fio, phone, email, bonus, status, client_type)\n" +
                "SELECT id_client, name_client, tel_client, mail_client, bonus_client, status_client, 'physical' as client_type\n" +
                "FROM public.\"PhysCli\";");
        statmt.execute("INSERT INTO all_clients (original_id, fio, phone, email, bonus, status, client_type)\n" +
                "SELECT id_client, uri_name_client, tel_client, mail_client, bonus_client, status_client, 'legal' as client_type\n" +
                "FROM public.\"UriCli\";");
    }
    public static ArrayList<LegalClient> getAllLegalClient() throws SQLException{
        data_resSet = statmt.executeQuery("SELECT id_client, status_client, name_client, " +
                "uri_name_client, inn_client, kpp_client, ogrn_client, tel_client, mail_client, " +
                "uri_address_client, bonus_client\n" +
                "\tFROM public.\"UriCli\";");
        ArrayList<LegalClient> arrayList = new ArrayList<>();
        while (data_resSet.next()){
            String id = data_resSet.getString("id_client");
            String status = data_resSet.getString("status_client");
            String name = data_resSet.getString("uri_name_client");
            String tel = data_resSet.getString("tel_client");
            String bonus = data_resSet.getString("bonus_client");
            String inn = data_resSet.getString("inn_client");
            String mail = data_resSet.getString("mail_client");
            LegalClient physClient = new LegalClient(id,name,status,tel,bonus,inn,mail);
            arrayList.add(physClient);
        }
        return arrayList;
    }
    public static ArrayList<PhysClient> getAllPhysClient() throws SQLException{
        data_resSet = statmt.executeQuery("SELECT id_client, status_client, name_client, " +
                "tel_client, bonus_client, inn_client, mail_client\n" +
                "\tFROM public.\"PhysCli\";");
        ArrayList<PhysClient> arrayList = new ArrayList<>();
        while (data_resSet.next()){
            String id = data_resSet.getString("id_client");
            String status = data_resSet.getString("status_client");
            String name = data_resSet.getString("name_client");
            String tel = data_resSet.getString("tel_client");
            String bonus = data_resSet.getString("bonus_client");
            String inn = data_resSet.getString("inn_client");
            String mail = data_resSet.getString("mail_client");
            PhysClient physClient = new PhysClient(id,name,status,tel,bonus,inn,mail);
            arrayList.add(physClient);
        }
        return arrayList;
    }
    public static LegalClient getLegalClientbyId(String id_legal) throws SQLException {
        data_resSet = statmt.executeQuery("SELECT id_client, status_client, name_client, " +
                "uri_name_client, inn_client, kpp_client, ogrn_client, tel_client, mail_client, " +
                "uri_address_client, bonus_client\n" +
                "\tFROM public.\"UriCli\" where id_client ='"+id_legal+"';");
        LegalClient legalClient = null;
        while (data_resSet.next()){
            String id = data_resSet.getString("id_client");
            String orgname = data_resSet.getString("uri_name_client");
            String tel = data_resSet.getString("tel_client");
            String inn = data_resSet.getString("inn_client");
            String mail = data_resSet.getString("mail_client");
            String kpp = data_resSet.getString("kpp_client");
            String name = data_resSet.getString("name_client");
            String ogrn = data_resSet.getString("ogrn_client");
            String address = data_resSet.getString("uri_address_client");
            legalClient = new LegalClient(id,orgname,"",tel,"",inn,mail,name,address,kpp,ogrn);
        }
        return legalClient;
    }
    public static void updateLegalClient(String id, String name, String orgname, String inn, String kpp, String ogrn,
                                         String tel, String mail, String address) throws SQLException {
        statmt.execute("UPDATE public.\"UriCli\"\n" +
                "\tSET name_client='"+name+"', uri_name_client='"+orgname+"', inn_client='"+inn+"', kpp_client='"+kpp+"'," +
                " ogrn_client='"+ogrn+"', tel_client='"+tel+"', mail_client='"+mail+"', uri_address_client='"+address+"'\n" +
                "\tWHERE id_client='"+id+"';");
    }
    public static void deleteLegalClient(String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"UriCli\"\n" +
                "\tWHERE id_client ='"+id+"';");
    }
    public static PhysClient getPhysClientbyId(String id_phys) throws  SQLException {
        data_resSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"PhysCli\" WHERE id_client = '"+id_phys+"';");
        PhysClient physClient = null;
        while (data_resSet.next()){
            String id = data_resSet.getString("id_client");
            String status = data_resSet.getString("status_client");
            String name = data_resSet.getString("name_client");
            String tel = data_resSet.getString("tel_client");
            String bonus = data_resSet.getString("bonus_client");
            String inn = data_resSet.getString("inn_client");
            String mail = data_resSet.getString("mail_client");
            physClient = new PhysClient(id,name,status,tel,bonus,inn,mail);
        }
        return physClient;
    }
    public static void updatePhysClient(String id, String name, String tel, String inn, String mail) throws SQLException {
        statmt.execute("UPDATE public.\"PhysCli\"\n" +
                "\tSET  name_client='"+name+"', tel_client='"+tel+"', inn_client='"+inn+"', mail_client='"+mail+"'\n" +
                "\tWHERE id_client='"+id+"';");
    }
    public static void deletePhysClient(String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"PhysCli\"\n" +
                "\tWHERE id_client ='"+id+"';");
    }
    public static String getEmployeeNamebyLogin() throws SQLException {
        data_resSet = statmt.executeQuery("SELECT * FROM public.\"Users\" " +
                "JOIN public.\"Employee\" using (id_empl) " +
                "WHERE login = '"+ Front.login+"';");
        String name ="";
        while (data_resSet.next()){
            name = data_resSet.getString("name_empl");
            String[] mas = name.split(" ");
            char[] name_ = mas[1].toCharArray();
            char[] secname_ = mas[2].toCharArray();
            name = mas[0]+" "+name_[0]+". "+secname_[0]+".";
        }
        return name;
    }
    public static ArrayList<ReceiptServ> getServiceName(String id_cod) throws SQLException {
        ArrayList<ReceiptServ> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Service\"  JOIN public.\"service_zdanie\" using(id_serv) WHERE id_zdanie ='"+id_cod+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_serv");
            String type = data_resSet.getString("type_serv");
            String price = data_resSet.getString("price_serv");
            String extra = data_resSet.getString("extra_param_serv");
            String ram = data_resSet.getString("ram_serv");
            String cpu = data_resSet.getString("cpu_serv");
            String answer = type+" "+extra+" CPU:"+ cpu+" RAM:" +ram;
            ReceiptServ receiptServ = new ReceiptServ(id,price,answer);
            arrayList.add(receiptServ);
        }
        return arrayList;
    }
    public static ArrayList<ReceiptServ> getServiceName2() throws SQLException {
        ArrayList<ReceiptServ> arrayList = new ArrayList<>();
        data_resSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Service\" ;");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_serv");
            String type = data_resSet.getString("type_serv");
            String price = data_resSet.getString("price_serv");
            String extra = data_resSet.getString("extra_param_serv");
            String ram = data_resSet.getString("ram_serv");
            String cpu = data_resSet.getString("cpu_serv");
            String answer = type+" "+extra+" CPU:"+ cpu+" RAM:" +ram;
            ReceiptServ receiptServ = new ReceiptServ(id,price,answer);
            arrayList.add(receiptServ);
        }
        return arrayList;
    }
    public static void addServiceCod(String id_serv, String id_cod) throws SQLException {
        statmt.execute("INSERT INTO public.service_zdanie(\n" +
                "\tid_zdanie, id_serv)\n" +
                "\tVALUES ('"+id_cod+"', '"+id_serv+"');");
    }
    public static String getEmployeeIDbyLogin() throws SQLException {
        String id = "";
        data_resSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Users\" WHERE login = '"+Front.login+"';");
        while (data_resSet.next()){
            id = data_resSet.getString("id_empl");
        }
        return id;
    }
    public static void addReceipt(ArrayList<ReceiptServ> receiptServs,String id_client,
                                  String id_empl, String summ, String type, String date) throws SQLException {
        String typeC;
        if(type.equals("Юридическое лицо"))
            typeC = "L";
        else
            typeC = "I";
        statmt.execute("INSERT INTO public.\"AllCli\"(\n" +
                "\tcustomer_type, id_client)\n" +
                "\tVALUES ('"+typeC+"', '"+id_client+"') ON CONFLICT (customer_type, id_client) DO NOTHING;");

        statmt.execute("INSERT INTO public.\"Receipt\"(\n" +
                "\tid_client, id_empl, summ_receipt, customer_type,date_receipt)\n" +
                "\tVALUES ('"+id_client+"', '"+id_empl+"', '"+summ+"', '"+typeC+"', '"+date+"');");
        String id_receipt = "";
        data_resSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"Receipt\" WHERE id_client = '"+id_client+"' and customer_type ='"+typeC+"';");
        while (data_resSet.next()){
            id_receipt = data_resSet.getString("id_receipt");
        }
        if(!id_receipt.isEmpty()){
            for(int i=0;i<receiptServs.size();i++){
                statmt.execute("INSERT INTO public.\"receipt_service\"(\n" +
                        "\tid_receipt, id_serv)\n" +
                        "\tVALUES ('"+id_receipt+"', '"+receiptServs.get(i).id_serv+"');");
            }
        }
    }
    public static ArrayList<Receipt> getAllReceipt() throws SQLException {
        data_resSet = data_statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Receipt\" JOIN public.\"UriCli\" " +
                "using (id_client) JOIN public.\"Employee\" using (id_empl)" +
                "where customer_type='L';");
        ArrayList<Receipt> arrayList = new ArrayList<>();
        while (data_resSet.next()){
            String id_receipt = data_resSet.getString("id_receipt");
            String name = data_resSet.getString("uri_name_client");
            String summ = data_resSet.getString("summ_receipt");
            String nameEmpl = data_resSet.getString("name_empl");
            String typeEmpl = data_resSet.getString("customer_type");
            String id_empl = data_resSet.getString("id_empl");
            Receipt receipt = new Receipt(id_receipt,name,nameEmpl,summ,typeEmpl,id_empl);
            arrayList.add(receipt);
        }
        data_resSet = data_statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Receipt\" \n" +
                "\tJOIN public.\"PhysCli\" using (id_client) \n" +
                "\tJOIN public.\"Employee\" using (id_empl)\n" +
                "\twhere customer_type='I';");
        while (data_resSet.next()){
            String id_receipt = data_resSet.getString("id_receipt");
            String name = data_resSet.getString("name_client");
            String summ = data_resSet.getString("summ_receipt");
            String nameEmpl = data_resSet.getString("name_empl");
            String typeEmpl = data_resSet.getString("customer_type");
            String id_empl = data_resSet.getString("id_empl");
            Receipt receipt = new Receipt(id_receipt,name,nameEmpl,summ,typeEmpl,id_empl);
            arrayList.add(receipt);
        }
        return arrayList;
    }
    public static ArrayList<ReceiptServ> getServicebyIDreceipt(String id_receipt) throws SQLException {
        ArrayList<ReceiptServ> arrayList = new ArrayList<>();
        ArrayList<String> idMas = new ArrayList<>();
        data_resSet = data_statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"receipt_service\" " +
                "JOIN public.\"Service\" using(id_serv)  WHERE id_receipt='"+id_receipt+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("id_serv");
            String type = data_resSet.getString("type_serv");
            String price = data_resSet.getString("price_serv");
            String extra = data_resSet.getString("extra_param_serv");
            String ram = data_resSet.getString("ram_serv");
            String cpu = data_resSet.getString("cpu_serv");
            String answer = type+" "+extra+" CPU:"+ cpu+" RAM:" +ram;
            ReceiptServ receiptServ = new ReceiptServ(id,price,answer);
            arrayList.add(receiptServ);
        }
        return arrayList;
    }

    public static Receipt getReceiptbyID(String id_receipt) throws SQLException {
        ArrayList<Receipt> arrayList = new ArrayList<>();
        data_resSet = data_statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Receipt\" JOIN public.\"UriCli\" " +
                "using (id_client) JOIN public.\"Employee\" using (id_empl)" +
                "where customer_type='L' and id_receipt ='"+id_receipt+"';");
        while (data_resSet.next()){
            String name = data_resSet.getString("uri_name_client");
            String summ = data_resSet.getString("summ_receipt");
            String nameEmpl = data_resSet.getString("name_empl");
            String typeEmpl = "Юридическое лицо";
            String id_empl = data_resSet.getString("id_empl");
            String date = data_resSet.getString("date_receipt");
            Receipt receipt = new Receipt(id_receipt,name,nameEmpl,id_empl,summ,typeEmpl,date);
            arrayList.add(receipt);
        }
        data_resSet = data_statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Receipt\" \n" +
                "\tJOIN public.\"PhysCli\" using (id_client) \n" +
                "\tJOIN public.\"Employee\" using (id_empl)\n" +
                "\twhere customer_type='I' and id_receipt ='"+id_receipt+"';");
        while (data_resSet.next()){
            String name = data_resSet.getString("name_client");
            String summ = data_resSet.getString("summ_receipt");
            String nameEmpl = data_resSet.getString("name_empl");
            String typeEmpl ="Физическое лицо";
            String id_empl = data_resSet.getString("id_empl");
            String date = data_resSet.getString("date_receipt");
            Receipt receipt = new Receipt(id_receipt,name,nameEmpl,id_empl,summ,typeEmpl,date);
            arrayList.add(receipt);
        }
        Receipt receipt = arrayList.get(0);
        return receipt;
    }
    public static void deleteReceipt(String id_receipt) throws SQLException {
        statmt.execute("DELETE FROM public.\"receipt_service\"\n" +
                "\tWHERE id_receipt ='"+id_receipt+"';");
        statmt.execute("DELETE FROM public.\"Receipt\"\n" +
                "\tWHERE id_receipt = '"+id_receipt+"';");
    }
    public static void updateReceipt(String id_receipt, String date,String summ, ArrayList<ReceiptServ> arrayList)
            throws SQLException {
        statmt.execute("UPDATE public.\"Receipt\"\n" +
                "\tSET summ_receipt = '"+summ+"', date_receipt ='"+date+"'\n" +
                "\tWHERE id_receipt ='"+id_receipt+"'");
        statmt.execute("DELETE FROM public.\"receipt_service\"\n" +
                "\tWHERE id_receipt = '"+id_receipt+"';");
        for(int i=0;i<arrayList.size();i++){
            statmt.execute("INSERT INTO public.\"receipt_service\"(\n" +
                    "\tid_receipt, id_serv)\n" +
                    "\tVALUES ('"+id_receipt+"', '"+arrayList.get(i).id_serv+"');");
        }
    }
    public static void addService (String type, String price, String desc, String cpu, String ram, String extra) throws SQLException {
        statmt.execute("INSERT INTO public.\"Service\"(\n" +
                "\ttype_serv, price_serv, desc_serv, cpu_serv, ram_serv, extra_param_serv)\n" +
                "\tVALUES ('"+type+"', '"+price+"', '"+desc+"', '"+cpu+"', '"+ram+"', '"+extra+"');");
    }
    public static Service getServicebyID(String id) throws SQLException {
        data_resSet = statmt.executeQuery("SELECT id_serv, type_serv, price_serv, desc_serv, cpu_serv, ram_serv, extra_param_serv\n" +
                "\tFROM public.\"Service\" WHERE id_serv ='"+id+"';");
        Service service = null;
        while (data_resSet.next()){
            String type = data_resSet.getString("type_serv");
            String price = data_resSet.getString("price_serv");
            String desc = data_resSet.getString("desc_serv");
            String cpu = data_resSet.getString("cpu_serv");
            String ram = data_resSet.getString("ram_serv");
            String extra = data_resSet.getString("extra_param_serv");
            service = new Service(id,type,price,desc,cpu,ram,extra);
        }
        return service;
    }
    public static void deleteService (String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"Service\"\n" +
                "\tWHERE id_serv ='"+id+"';");
    }
    public static void updateService (String id, String type, String price, String desc, String cpu,
                                     String ram, String extra) throws SQLException {
        statmt.execute("UPDATE public.\"Service\"\n" +
                "\tSET type_serv='"+type+"', price_serv='"+price+"', desc_serv='"+desc+"', cpu_serv='"+cpu+"', " +
                "ram_serv='"+ram+"', extra_param_serv='"+extra+"'\n" +
                "\tWHERE id_serv ='"+id+"';");
    }
}
