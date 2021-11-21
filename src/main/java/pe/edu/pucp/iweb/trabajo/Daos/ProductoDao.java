package pe.edu.pucp.iweb.trabajo.Daos;

import pe.edu.pucp.iweb.trabajo.Beans.BProducto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductoDao {
    String user = "root";
    String password = "root";
    String url = "jdbc:mysql://localhost:3306/mydb";

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------

    // FUNCION INSERTAR PRODUCTO|
    public void inserta_producto(String nombre, double precio, int stock, String descripcion,boolean requiereReceta,String farmacia_ruc) {

        Scanner sc = new Scanner(System.in);


        String sqlInsert = "INSERT\tINTO mydb.producto (nombre,precio,stock,descripcion, requiereReceta,farmacia_ruc)\n" +
                "VALUES(?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert);) {


            pstmt.setString(1, nombre);
            pstmt.setDouble(2, precio);
            pstmt.setInt(3, stock);
            pstmt.setString(4, descripcion);
            if (requiereReceta = true) {

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
    /*public void homePageProductoCaracteristicas(String idProducto){
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

    }*/

    //FUNCION QUE MUESTRA CARACTERISTICAS DEL PRODUCTO
    public void ProductoCaracteristicas(String idProducto){
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

    public ArrayList<BProducto> ProductosPorFarmacia(String rucFarmacia){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<BProducto> listaProdxFarm = new ArrayList<>();


        String sql = "select producto.foto,producto.idproducto,producto.nombre,producto.descripcion,producto.stock,producto.precio,producto.farmacia_ruc from producto " +
                "join farmacia on producto.farmacia_ruc = farmacia.ruc " +
                "where farmacia_ruc = ?";

        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,rucFarmacia);

            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    Blob fotoProducto = rs.getBlob(1);
                    int idProducto = rs.getInt(2);
                    String nombreProducto = rs.getString(3);
                    String descProducto = rs.getString(4);
                    int stockProducto = rs.getInt(5);
                    String precioProductoStr = rs.getString(6);
                    double precioProducto = Double.parseDouble(precioProductoStr);

                    System.out.println(nombreProducto);
                    System.out.println(precioProducto);


                    listaProdxFarm.add(new BProducto(idProducto, nombreProducto, descProducto, null, stockProducto, precioProducto, rucFarmacia));
                }



            }
            catch (SQLException e){
                e.getSQLState();
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            throwables.getSQLState();
            System.out.println("No existe el producto!!!!");

        }

        return listaProdxFarm;

    }

    public BProducto obtenerProducto(String idProducto) {
        BProducto producto = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb";
        String sql = "select * from producto where nombre = ? LIKE ?";

        try (Connection connection = DriverManager.getConnection(url, "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, idProducto);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    producto = new BProducto();
                    producto.setIdProducto(rs.getInt(1));
                    producto.setNombre(rs.getString(2));
                    producto.setPrecio(rs.getDouble(3));
                    producto.setStock(rs.getInt(4));
                    producto.setDescripcion(rs.getString(5));
                    producto.setRequiereReceta(rs.getBoolean(6));
                    producto.setFarmaciaRUC(rs.getString(7));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    public void borrarproducto(String idProducto) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb";
        String sql = "DELETE FROM jobs WHERE job_id = ?";

        try (Connection connection = DriverManager.getConnection(url, "root", "root");
             PreparedStatement pstmt = connection.prepareStatement(sql);) {
            pstmt.setString(1, idProducto);
            pstmt.executeUpdate();
        }
    }

    public ArrayList<BProducto> buscarProductoPorNombre(String nombre) {
        ArrayList<BProducto> listaProductos = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        nombre = nombre.toLowerCase();

        String url = "jdbc:mysql://localhost:3306/mydb";
        String sql = "select * from jobs where lower(job_title) like ?";

        try (Connection connection = DriverManager.getConnection(url, "root", "root");
             PreparedStatement pstmt = connection.prepareStatement(sql);) {
            pstmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BProducto producto = new BProducto();
                    producto.setIdProducto(rs.getInt(1));
                    producto.setNombre(rs.getString(2));
                    producto.setPrecio(rs.getDouble(3));
                    producto.setStock(rs.getInt(4));
                    producto.setDescripcion(rs.getString(5));
                    producto.setRequiereReceta(rs.getBoolean(6));
                    producto.setFarmaciaRUC(rs.getString(7));
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProductos;
    }



}
