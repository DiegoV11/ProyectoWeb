package pe.edu.pucp.iweb.trabajo.Daos;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PedidosDao {
    String user = "root";
    String password = "root";
    String url = "jdbc:mysql://localhost:3306/mydb";


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------


    //FUNCION QUE BLOQUEA EL PEDIDO FARMACIA
    public boolean bloquearPedidoFarmacia(String codigoPedidoStr){
        String sqlUpdate = "UPDATE pedidos p SET p.bloqueado ='Verdadero' WHERE p.numeroOrden = ?";
        String sqlBusqueda ="SELECT p.fechaRecojo FROM pedidos p WHERE p.numeroOrden = ?";
        try (Connection conn = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = conn.prepareStatement(sqlBusqueda)){
            pstmt.setString(1,codigoPedidoStr);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Date fecha_a_recoger =  rs.getDate(1);
            Calendar c= Calendar.getInstance();
            c.setTime(fecha_a_recoger);
            c.add(Calendar.DATE,1);
            Date fecha_a_recoger_mas_uno = c.getTime();
            Date hoy = new Date();
            if(fecha_a_recoger_mas_uno.after(hoy)){
                try(PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdate);){
                    pstmt2.setString(1,codigoPedidoStr);
                    pstmt2.executeUpdate();
                    return true;
                }
            }else{
                System.out.println("No se bloquear, porque todavia no pasa la fecha de recojo");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //FUNCION PARA CLIENTE REALICE PEDIDOS
    public void realizaPedidos(String norden, String fechaRecojo, String estado){
        /*fecha de recojo con el siguiente formato YYYY/MM/DD */
        String sqlBusqueda2 = "SELECT * FROM pedidos";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBusqueda2);) {
            String sqlInsert = "INSERT INTO pedidos(numeroOrden,fechaRecojo,estado,usuarioDNI)\n" +
                    "VALUES(?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert);){
                SimpleDateFormat formatter = new SimpleDateFormat();
                Date date = formatter.parse(fechaRecojo);

                pstmt.setString(1, norden);
                pstmt.setDate(2, java.sql.Date.valueOf(fechaRecojo));
                pstmt.setString(3, estado);
                pstmt.executeUpdate();


            }

        }catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }



    }






}
