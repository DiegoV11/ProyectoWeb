package pe.edu.pucp.iweb.trabajo.Daos;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredencialesDao {
    String user = "root";
    String password = "root";
    String url = "jdbc:mysql://localhost:3306/mydb";

    public boolean nombreyApellidoValid(String nombre) {
        String regex = "^[\\w'\\-,.][^0-9_!¡?÷?¿/\\\\+=@#$%ˆ&*(){}|~<>;:[\\]]]{1,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.find();
    }

    public boolean emailisValid(String email) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
    public boolean contrasenaisValid(String contrasenia) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contrasenia);
        return matcher.find();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //FUNCION PARA REGISTRAR FARMACIA EN CREDENCIALES
    public String registrarFarmacia(){
        CredencialesDao c = new CredencialesDao();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese su correo electronico: ");
        String correoElectronico = sc.nextLine();
        String sqlBusqueda = "SELECT correo FROM credenciales";
        boolean bandera = false;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBusqueda)) {
            while (rs.next()) {
                String correo = rs.getString(1);
                if (correo.equalsIgnoreCase(correoElectronico)) {
                    bandera = true;
                    break;
                }
            }
            if (!bandera) {
                String sql = "INSERT INTO credenciales(correo,contrasena,rol) VALUES(?,?,'farmacia')";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    if (c.emailisValid(correoElectronico)){
                        while (true){
                            System.out.println("Ingrese la contraseña de la farmacia: ");
                            String contrasenia = sc.nextLine();
                            if (c.contrasenaisValid(contrasenia)){
                                pstmt.setString(1, correoElectronico);
                                pstmt.setString(2, contrasenia);
                                pstmt.executeUpdate();
                                ResultSet rs2 = pstmt.getGeneratedKeys();
                                if (rs2.next()) {
                                    System.out.println("El ultimo es: " + rs2.getString("correo"));
                                }
                                System.out.println("Se ha registrado correctamente");
                                return correoElectronico;
                            }else{
                                System.out.println("La contraseña debe contener al menos: ");
                                System.out.println("1. Letra minuscula ");
                                System.out.println("2. Letra mayuscula ");
                                System.out.println("3. Un simbolo #/?@-");
                                System.out.println("4. Al menos 8 letras\n");
                                System.out.println("5. Ingrese al menos 1 numero");
                            }
                        }
                    } else {
                        System.out.println("El correo ingresado es incorrecto debe ser de la forma por ejemplo: farmacia@gmail.com");
                        return null;
                    }
                }
            } else {
                System.out.println("Este correo de farmacia ya se encuentra registrada\n");
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //FUNCION PARA REGISTRAR ADMIN EN CREDENCIALES
    public String registrarAdmin(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese su correo electronico: ");
        String correoElectronico = sc.nextLine();
        String sqlBusqueda = "SELECT correo FROM credenciales";
        boolean bandera = false;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBusqueda)) {
            while (rs.next()) {
                String correo = rs.getString(1);
                if (correo.equalsIgnoreCase(correoElectronico)) {
                    bandera = true;
                    break;
                }
            }
            if (!bandera) {
                String sql = "INSERT INTO credenciales(correo,contrasena,rol) VALUES(?,?,'administrador')";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)){
                    if (emailisValid(correoElectronico)){
                        while (true){
                            System.out.print("Ingrese la contraseña de la farmacia: ");
                            String contrasena = sc.nextLine();
                            if (contrasenaisValid(contrasena)){
                                pstmt.setString(1, correoElectronico);
                                pstmt.setString(2, contrasena);
                                pstmt.executeUpdate();
                                ResultSet rs2 = pstmt.getGeneratedKeys();
                                if (rs2.next()) {
                                    System.out.println("El ultimo es: " + rs2.getString("correo"));
                                }
                                System.out.println("Se ha registrado al administrador correctamente");
                                return correoElectronico;
                            }else{
                                System.out.println("La contraseña debe contener al menos: ");
                                System.out.println("1. Letra minuscula ");
                                System.out.println("2. Letra mayuscula ");
                                System.out.println("3. Un simbolo #/?@-");
                                System.out.println("4. Al menos 8 letras\n");
                                System.out.println("5. Ingrese al menos 1 numero");
                            }
                        }
                    } else {
                        System.out.println("El correo ingresado es incorrecto debe ser de la forma por ejemplo: farmacia@gmail.com");
                        return null;
                    }
                }
            } else {
                System.out.println("Este correo de administrador ya se encuentra registrado");
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //FUNCION PARA OBTENER ROL
    public String rolCredenciales(String correo){
        String rol = null;
        String sql="SELECT rol FROM credenciales c WHERE c.correo = ? ";
        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,correo);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                rol=rs.getString(1);
            }
            return rol;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rol;
    }

    //FUNCION PARA ELIMINAR CREDENCIALES
    public void eliminarCredenciales(String correoElectronico) {
        String sql="DELETE c FROM credenciales c WHERE ? = c.correo;";
        boolean bandera= false;
        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correoElectronico);
            String sqlBusqueda = "SELECT correo FROM credenciales";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBusqueda)) {
                while (rs.next()) {
                    String correo = rs.getString(1);
                    if (correo.equalsIgnoreCase(correoElectronico)) {
                        bandera = true;
                        break;
                    }
                }
                if (!bandera) {
                    System.out.println("El correo no existe");
                }else {
                    pstmt.executeUpdate();
                    System.out.println("Se han eliminado correctamente las credenciales");
                }
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }


    //FUNCION PARA REALIZAR INICIO DE SESION
    public  String inicioSesion(String correo, String contrasena){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String rol=null;
        String error ="invalid";
        String sql="SELECT * FROM mydb.credenciales where correo = ? and contrasena = ?";
        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,correo);
            pstmt.setString(2,contrasena);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                    rol = rs.getString(3);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        if (rol !=null){
            return rol;
        }else{
            return error;
        }
    }



}
