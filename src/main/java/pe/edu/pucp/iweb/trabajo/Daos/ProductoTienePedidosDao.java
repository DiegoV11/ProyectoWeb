package pe.edu.pucp.iweb.trabajo.Daos;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ProductoTienePedidosDao {
    String user = "root";
    String password = "root";
    String url = "jdbc:mysql://localhost:3306/mydb";

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    //FUNCION QUE MUESTRA DETALLES DEL PEDIDO   -> FALTA

    //FUNCION PRODUCTO TIENE PEDIDO
    public void producto_tiene_pedido(int numeroOrden, int idProducto){

        Scanner sc = new Scanner(System.in);
        String sqlBusqueda2 = "SELECT * FROM mydb.producto_tiene_pedidos;";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBusqueda2);) {
            String sqlInsert = "INSERT INTO producto_tiene_pedidos(producto_idProducto,pedidos_numeroOrden,cantidad)\n" +
                    "VALUES (?,?,?)" ;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert);){

                pstmt.setInt(1, idProducto);
                pstmt.setInt(2, numeroOrden);


                System.out.print("Ingrese la cantidad: ");
                int cantidad = Integer.parseInt(sc.nextLine());
                pstmt.setInt(3, cantidad);
                pstmt.executeUpdate();
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


}
