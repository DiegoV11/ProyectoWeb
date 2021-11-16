package pe.edu.pucp.iweb.trabajo.Daos;

import java.sql.*;
import java.util.Scanner;

public class ProductoDao {
    String user = "root";
    String password = "root";
    String url = "jdbc:mysql://localhost:3306/mydb";

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    // FUNCION INSERTAR PRODUCTO|
    public void inserta_producto(String nombre, String precioStr, String stockStr,String descripcion,String requiere,String farmacia_ruc) {

        Scanner sc = new Scanner(System.in);
        boolean requiereReceta;

        String sqlInsert = "INSERT\tINTO mydb.producto (nombre,precio,stock,descripcion, requiereReceta,farmacia_ruc)\n" +
                "VALUES(?,?,?,?, ?,?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert);) {


            pstmt.setString(1, nombre);
            double precio = Double.parseDouble(precioStr);
            pstmt.setDouble(2, precio);
            int stock = Integer.parseInt(stockStr);
            pstmt.setInt(3, stock);
            pstmt.setString(4, descripcion);
            if (requiere.equalsIgnoreCase("si")) {
                requiereReceta = true;
            } else {
                requiereReceta = false;
            }
            pstmt.setBoolean(5, requiereReceta);
            pstmt.setString(6, farmacia_ruc);
            pstmt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




    // FUNCION ELIMINAR PRODUCTO
    public void eliminarProducto(String idProducto, String farmaciaRuc) {
        int productoID = Integer.parseInt(idProducto);
        boolean bandera= false;

        String sqlDeleteProductoPedido = "DELETE p FROM producto_tiene_pedidos p WHERE ? = p.producto_idProducto; ";

        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sqlDeleteProductoPedido)) {

            pstmt.setInt(1, productoID);

            String sqlDeleteProducto = "DELETE p FROM producto p WHERE p.farmacia_ruc = ? AND p.idProducto = ? ;";

            try (PreparedStatement pstmt2 = conn.prepareStatement(sqlDeleteProducto);){
                pstmt2.setString(1, farmaciaRuc);
                pstmt2.setInt(2, productoID);

                String sqlBusqueda ="SELECT idProducto FROM producto;";

                try(PreparedStatement pstmt3 = conn.prepareStatement(sqlBusqueda);
                    ResultSet rs = pstmt3.executeQuery(sqlBusqueda)){

                    while (rs.next()) {
                        String idProdu = rs.getString(1);
                        if (idProdu.equals(idProducto)) {
                            bandera = true;
                            break;
                        }
                    }
                    if (!bandera) {
                        System.out.println("El producto no existe");
                    }else {
                        pstmt.executeUpdate();
                        pstmt2.executeUpdate();
                        System.out.println("Se ha eliminado correctamente el producto");
                    }

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //FUNCION EDITAR PRODUCTO
    public void editarProducto(String idProducto,String stockProducto,String precioStr,String requiere,String descripcionProducto){
        Scanner sc = new Scanner(System.in);

        String sqlBusqueda ="SELECT idProducto FROM producto;";
        boolean banderita = false;

        try(Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlBusqueda)){

            while (rs.next()) {
                String id = rs.getString(1);
                if (idProducto.equals(id)) {
                    banderita = true;
                    break;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(banderita){

            Double precioProducto = Double.parseDouble(precioStr);
            Boolean requiereReceta;

            if(requiere.equalsIgnoreCase("si")) {
                requiereReceta = true;
            }
            else {
                requiereReceta = false;
            }


            //COMO HARIA PARA QUE SE MODIFIQUE LA FOTO?? , LE PEDIRIA POR ACA EL TERMINAL LA FOTO O COMO?

            String sqlUpdate = "UPDATE producto SET stock = ? , descripcion = ?, requiereReceta = ?, precio = ? WHERE idProducto = ?;";

            try(Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)){

                pstmt.setString(6,stockProducto);
                pstmt.setString(3,descripcionProducto);
                pstmt.setBoolean(4,requiereReceta);
                pstmt.setDouble(7,precioProducto);
                pstmt.executeUpdate();
                System.out.println("Las caracteristicas del producto se han modificado con exito.");

            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else {
            System.out.println("No existe el producto!!!!");
        }

    }



    //FUNCION QUE MUESTRA CARACTERISTICAS DEL PRODUCTO
    public void homePageProductoCaracteristicas(String idProducto){
        Scanner sc = new Scanner(System.in);

        String sqlBusqueda ="SELECT idProducto FROM producto;";
        boolean banderita = false;

        try(Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlBusqueda)){

            while (rs.next()) {
                String id = rs.getString(1);
                if (idProducto.equals(id)) {
                    banderita = true;
                    break;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(banderita){
            String sql = "SELECT nombre,precio FROM producto WHERE idProducto = ?;";

            try(Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1,idProducto);

                try(ResultSet rs = pstmt.executeQuery()){
                    String nombreProducto = rs.getString(1);
                    String precioProductoStr = rs.getString(2);
                    double precioProducto = Double.parseDouble(precioProductoStr);

                    System.out.println(nombreProducto);
                    System.out.println(precioProducto);

                }

            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else {
            System.out.println("No existe el producto!!!!");
        }

    }

}
