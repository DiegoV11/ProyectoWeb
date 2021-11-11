package pe.edu.pucp.iweb.trabajo.Daos;


import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdministradorDao {
    String user = "root";
    String password = "root";
    String url = "jdbc:mysql://localhost:3306/mydb";

    //FUNCION PARA VALIDAR NOMBRE Y APELLIDOS
    public boolean nombreyApellidoValid(String nombre) {
        String regex = "^[\\w'\\-,.][^0-9_!¡?÷?¿/\\\\+=@#$%ˆ&*(){}|~<>;:[\\]]]{1,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.find();
    }

    //FUNCION PARA VALIDAR DNI
    public boolean dniValid(String dni) {
        String regex = "^[0-9]{8,8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dni);
        return matcher.find();
    }

    //FUNCION PARA VALIDAR FECHA
    public boolean fechaIsValid(String fecha){
        String regex = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fecha);
        return matcher.find();
    }
    public boolean rucValid(String ruc) {
        String regex = "^[0-9]{11,11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ruc);
        return matcher.find();
    }
    public boolean pedidosPendientes(String dni) {
        String regex = "^[0-1]{1,1}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dni);
        return matcher.find();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------//





}
