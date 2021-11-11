package pe.edu.pucp.iweb.trabajo.Daos;

import pe.edu.pucp.iweb.trabajo.Beans.BFarmacia;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FarmaciaDao {


    String user = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        public boolean nombreyApellidoValid(String nombre) {
            String regex = "^[\\w'\\-,.][^0-9_!¡?÷?¿/\\\\+=@#$%ˆ&*(){}|~<>;:[\\]]]{1,}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(nombre);
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
        Scanner sc = new Scanner(System.in);

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------//FUNCION BLOQUEAR FARMACIA
    //FUNCION PARA AGREGAR FARMACIA
    public void insertarFarmacia(String logueo_correo){
        Scanner sc = new Scanner(System.in);
        String sqlBusqueda = "SELECT * FROM farmacia";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBusqueda);) {
            while(true){
                System.out.println("Ingrese el ruc: ");
                String ruc = sc.nextLine();
                boolean bandera = false;
                while (rs.next()) {
                    String RUC = rs.getString(1);
                    if (ruc.equalsIgnoreCase(RUC)){
                        bandera = true;
                    }
                }
                if (!bandera && rucValid(ruc)) {
                    String sqlInsert = "INSERT INTO farmacia(ruc,nombre,direccion,distrito,bloqueado,pedidosPendientes,logueo_correo)\n" +
                            "VALUES(?,?,?,?,'Falso',?,?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert);){
                        while (true){
                            System.out.print("Ingrese el nombre de la farmacia: ");
                            String nombre = sc.nextLine();
                            System.out.print("Ingrese la direccion de la farmacia: ");
                            String direccion = sc.nextLine();
                            if (nombreyApellidoValid(nombre)) {
                                pstmt.setString(2, nombre);
                                pstmt.setString(3, direccion);
                                while(true){
                                    System.out.print("Ingrese su distrito: ");
                                    String distrito = sc.nextLine();
                                    pstmt.setString(4, distrito);
                                    System.out.print("¿Tiene pedidos pendientes? (1:'Si',0:'No'): ");
                                    String pedidosPendientes = sc.nextLine();
                                    if (pedidosPendientes(pedidosPendientes)) {
                                        if(pedidosPendientes.equalsIgnoreCase("1")){
                                            pstmt.setString(5, "Si");
                                        }else{
                                            pstmt.setString(5, "No");
                                        }
                                        pstmt.setString(1, ruc);
                                        pstmt.setString(6, logueo_correo);
                                        pstmt.executeUpdate();
                                        break;
                                    }else{
                                        System.out.println("Ha ingresado una opcion invalida");
                                    }
                                }
                                break;
                            }else{
                                System.out.println("Ha ingresado un nombre de farmacia sin sentido");
                            }
                        }
                    }
                    break;
                }else {
                    System.out.println("ruc incorrecto");
                }
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    //FUNCION QUE MUESTAR HISTORIAL DE PEDIDOS FARMACIA
    public void mostrarHistorialPedidosSca(){

        Scanner sc= new Scanner(System.in);
        System.out.print("Ingrese el ruc de la farmacia: ");
        String ruc = sc.nextLine();

        String sql ="SELECT f.ruc,pt.pedidos_numeroOrden,CONCAT(cl.nombre,\" \",cl.apellidos),cl.dni,pe.estado,pe.fechaRecojo, pt.recetas FROM farmacia f \n" +
                "INNER JOIN producto p ON p.farmacia_ruc=f.ruc\n" +
                "INNER JOIN producto_tiene_pedidos pt ON p.idProducto = pt.producto_idProducto\n" +
                "INNER JOIN pedidos pe ON pe.numeroOrden=pt.pedidos_numeroOrden\n" +
                "INNER JOIN cliente cl ON cl.dni = pe.usuarioDni\n" +
                "WHERE f.ruc = ?\n" +
                "GROUP BY pedidos_numeroOrden\n" +
                "ORDER BY pedidos_numeroOrden ASC;";

        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql);){
            pstmt.setString(1,ruc);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String ruc1=rs.getString(1);
                int pedidosOrden = rs.getInt(2);
                String nombre_completo = rs.getString(3);
                String dni = rs.getString(4);
                String estado =  rs.getString(5);
                String fecha_recojo = rs.getString(6);
                String recetas= rs.getString(7);
                System.out.println(pedidosOrden + "||" +nombre_completo + "||"+dni+ "||" + estado +"||" + fecha_recojo + "||" + recetas);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //FUNCION QUE LISTA PROD
    public void listaProductos(String ruc){

        String sql ="SELECT p.foto,p.nombre,p.descripcion,p.stock,p.precio FROM producto p \n" +
                "INNER JOIN farmacia f ON f.ruc=p.farmacia_ruc\n" +
                "WHERE f.ruc = ? \n" +
                "ORDER BY p.nombre";


        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql);){
            pstmt.setString(1,ruc);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String foto=rs.getString(1);
                String nombre = rs.getString(2);
                String descripcion = rs.getString(3);
                int stock  = rs.getInt(4);
                double precio =  rs.getDouble(5);
                System.out.println(foto + "||" +nombre + "||"+descripcion+ "||" + stock +"||" + precio);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //FUNCION PARA BUSCAR UN PRODUCTO
    public void buscarProducto(String ruc, String palabra){
        Scanner sc = new Scanner(System.in);
        String sql="SELECT p.foto,p.nombre,p.descripcion,p.stock,p.precio FROM producto p INNER JOIN farmacia f ON f.ruc = p.farmacia_ruc WHERE f.ruc = ? AND p.nombre LIKE ?";
        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,ruc);
            palabra = palabra + "%";
            pstmt.setString(2,palabra);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String foto=rs.getString(1);
                String nombre = rs.getString(2);
                String descripcion = rs.getString(3);
                int stock  = rs.getInt(4);
                double precio =  rs.getDouble(5);
                System.out.println(foto + "||" +nombre + "||"+descripcion+ "||" + stock +"||" + precio);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //FUNCION PARA MOSTRAR UN PEDIDO EN ESPECIFICO
    public void buscarPedido(String ruc, String numero){
        Scanner sc = new Scanner(System.in);
        String sql="SELECT f.ruc,pt.pedidos_numeroOrden,CONCAT(cl.nombre,\" \",cl.apellidos),cl.dni,pe.estado,pe.fechaRecojo, pt.recetas FROM farmacia f \n" +
                "INNER JOIN producto p ON p.farmacia_ruc=f.ruc\n" +
                "INNER JOIN producto_tiene_pedidos pt ON p.idProducto = pt.producto_idProducto\n" +
                "INNER JOIN pedidos pe ON pe.numeroOrden=pt.pedidos_numeroOrden\n" +
                "INNER JOIN cliente cl ON cl.dni = pe.usuarioDni\n" +
                "WHERE f.ruc = ?\n" +
                "GROUP BY pt.pedidos_numeroOrden\n" +
                "HAVING pt.pedidos_numeroOrden = ?\n" +
                "ORDER BY pedidos_numeroOrden ASC;";
        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,ruc);
            numero= numero + "%";
            pstmt.setString(2,numero);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                int pedidosOrden = rs.getInt(2);
                String nombre_completo = rs.getString(3);
                String dni = rs.getString(4);
                String estado =  rs.getString(5);
                String fecha_recojo = rs.getString(6);
                String recetas= rs.getString(7);
                System.out.println(pedidosOrden + "||" +nombre_completo + "||"+dni+ "||" + estado +"||" + fecha_recojo + "||" + recetas);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //FUNCION PARA BLOQUEAR FARMACIA
    public boolean bloquearFarmacia(String rucStr){
        String sqlUpdate = "UPDATE farmacia f SET f.bloqueado ='Verdadero' WHERE f.ruc = ?";
        String sqlBusqueda ="SELECT f.pedidosPendientes FROM farmacia f WHERE f.ruc = ?";
        try (Connection conn = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = conn.prepareStatement(sqlBusqueda)){
            pstmt.setString(1,rucStr);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            String pendientes =  rs.getString(1);
            if(pendientes.equalsIgnoreCase("no")){
                try(PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdate)){
                    pstmt2.setString(1,rucStr);
                    pstmt2.executeUpdate();
                    return true;
                }
            }else{
                System.out.println("No se puede bloquear porque tiene pedidos pendientes");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    //FUNCION PARA VER LA LISTA DE FARMACIAS
    public ArrayList<BFarmacia> mostrarListaFarmacias(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<BFarmacia> listaFarmacias = new ArrayList<>();
        String sql ="SELECT f.ruc, f.nombre, l.correo, f.distrito,f.pedidosPendientes,bloqueado,f.direccion FROM farmacia f\n" +
                "INNER JOIN credenciales l ON l.correo = f.logueo_correo\n" +
                "INNER JOIN producto p ON p.farmacia_ruc=f.ruc\n" +
                "INNER JOIN producto_tiene_pedidos pt ON p.idProducto = pt.producto_idProducto\n" +
                "GROUP BY f.ruc;";

        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement stmt = conn.createStatement();){

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String ruc = rs.getString(1);
                String nombre = rs.getString(2);
                String correo = rs.getString(3);
                String distrito =  rs.getString(4);
                String pedidosPendientes1 = rs.getString(5);
                String bloqueado= rs.getString(6);
                String direccion = rs.getString(7);
                BFarmacia f = new BFarmacia(ruc, nombre,correo, distrito,bloqueado,pedidosPendientes1,direccion);
                listaFarmacias.add(f);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaFarmacias;
    }

    //FUNCION PARA BUSCAR UNA FARMACIA EN ESPECIFICO
    public void mostrarHistorialPedidosSAdmin(String ruc){
        Scanner sc= new Scanner(System.in);
        String sql ="SELECT f.ruc, f.nombre, l.correo, f.distrito,f.pedidosPendientes,bloqueado FROM farmacia f \n" +
                "INNER JOIN credenciales l ON l.correo = f.logueo_correo\n" +
                "INNER JOIN producto p ON p.farmacia_ruc=f.ruc\n" +
                "INNER JOIN producto_tiene_pedidos pt ON p.idProducto = pt.producto_idProducto\n" +
                "GROUP BY f.ruc;";

        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql);){
            pstmt.setString(1,ruc);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String ruc1=rs.getString(1);
                String nombre = rs.getString(2);
                String correo = rs.getString(3);
                String distrito =  rs.getString(4);
                String pedidosPendientes2 = rs.getString(5);
                String bloqueado= rs.getString(6);
                System.out.println(nombre + "||" +correo + "||"+distrito+ "||" + pedidosPendientes2 +"||" + bloqueado);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




    }



