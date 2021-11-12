package pe.edu.pucp.iweb.trabajo.Daos;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClienteDao {
    String user = "root";
    String password = "root";
    String url = "jdbc:mysql://localhost:3306/mydb";
    HashMap<String, String> carrito = new HashMap<>();
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
    public boolean fechaIsValid(String fecha) {
        String regex = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fecha);
        return matcher.find();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    //FUNCION PARA REGISTRAR CLIENTE
    public boolean registrarCliente(String correoElectronico,String contrasenia){
            String sqlBusqueda = "SELECT correo FROM credenciales";
            CredencialesDao c = new CredencialesDao();
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
                    String sql = "INSERT INTO credenciales(correo,contrasena,rol) VALUES(?,?,'cliente')";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        if (c.emailisValid(correoElectronico)) {
                                if (c.contrasenaisValid(contrasenia)) {
                                    pstmt.setString(1, correoElectronico);
                                    pstmt.setString(2, contrasenia);
                                    pstmt.executeUpdate();
                                    System.out.println("Se ha registrado correctamente");
                                    return true;
                                } else {
                                    System.out.println("La contraseña debe contener al menos: ");
                                    System.out.println("1. Letra minuscula ");
                                    System.out.println("2. Letra mayuscula ");
                                    System.out.println("3. Un simbolo #/?@-");
                                    System.out.println("4. Al menos 8 letras");
                                    System.out.println("5. Ingrese al menos 1 numero");
                                    return false;
                                }

                        } else {
                            System.out.println("El correo ingresado es incorrecto debe ser de la forma por ejemplo: ejemplo@gmail.com");
                            return false;
                        }
                    }
                } else {
                    System.out.println("Este correo ya se encuentra registrado");
                    return false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
    }
    //FUNCION QUE REGISTRA A UN USUARIO YA HAY CORREO
    public void registrarDatosUsuario(String logueo_correo, String dni, String nombre, String apellidos,String fecha,String distrito) {
        String sqlInsert = "INSERT INTO cliente(dni,nombre,apellidos,fecha_nac,distrito,logueo_correo)\n" +
                "VALUES(?,?,?,?,?,?)";
        if (dniValid(dni) && nombreyApellidoValid(nombre) && nombreyApellidoValid(apellidos) && fechaIsValid(fecha)) {
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                pstmt.setString(2, nombre);
                pstmt.setString(3, apellidos);
                pstmt.setString(4, fecha);
                pstmt.setString(5, distrito);
                pstmt.setString(6, logueo_correo);
                pstmt.setString(1, dni);
                pstmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    //FUNCION QUE ELIMINA A UN CLIENTE CON SU CORREO
    public void eliminarCliente(String correo) {
        String sql = "DELETE c FROM cliente c  WHERE ? = c.logueo_correo;";
        boolean bandera = false;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, correo);
            String sqlBusqueda = "SELECT logueo_correo FROM cliente";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBusqueda);) {
                while (rs.next()) {
                    String logueo_correo = rs.getString(1);
                    if (logueo_correo.equalsIgnoreCase(correo)) {
                        bandera = true;
                        break;
                    }
                }
                if (!bandera) {
                    System.out.println("El correo no existe");
                } else {
                    pstmt.executeUpdate();
                    System.out.println("Se ha eliminado correctamente el cliente");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    //FUNCION QUE MUESTRA PERFIL DE CLIENTE
    public void mostrarPerfil(String correo) {
        String dni = null;
        String nombre = null;
        String apellidos = null;
        String fecha_nac = null;
        String distrito = null;
        String email = null;
        String sql = "SELECT c FROM cliente c  WHERE ? = c.logueo_correo";
        boolean bandera = false;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, correo);
            String sqlBusqueda = "SELECT dni,nombre,apellidos,fecha_nac,distrito,logueo_correo FROM cliente";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBusqueda);) {
                while (rs.next()) {
                    email = rs.getString(6);
                    if (email.equalsIgnoreCase(correo)) {
                        dni = rs.getString(1);
                        nombre = rs.getString(2);
                        apellidos = rs.getString(3);
                        fecha_nac = rs.getString(4);
                        distrito = rs.getString(5);
                        break;
                    }

                }
                String DNI = dni;
                System.out.println("DNI     |   Nombre  |   Apellidos   |   FechaDeNacimiento   |   Distrito    |   CorreoDeLogueo");
                System.out.println(dni + "  |   " + nombre + "   |   " + apellidos + "    |   " + fecha_nac + "    |   " + distrito + "     |   " + email);
                System.out.println("-----------------------------------------------------------------------------");
                Scanner sc = new Scanner(System.in);
                while (true) {
                    System.out.println("1. Mostrar historial de pedidos");
                    System.out.println("2. Ver el carrito de compras");
                    System.out.println("3. Buscar un producto");
                    System.out.print("Ingrese la opción --> ");
                    String option = sc.nextLine();
                    switch (option) {
                        case "1":
                            mostrarHistorial(DNI);
                            break;
                        case "2":
                            verCarrito();
                            break;
                        case "3":
                            buscarProducto();
                            break;

                    }


                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    //FUNCION QUE MUESTAR HISTORIAL DE PEDIDOS
    public void mostrarHistorial(String DNI) {

        String sql = "SELECT precio,cantidad ,p.numeroOrden as \"Numero de Orden\", p.estado as \"Estado de pedido\",pr.nombre as \"Nombre de productos\" , f.nombre as \"Farmacia\"FROM pedidos p\n" +
                "INNER JOIN producto_tiene_pedidos pt ON pt.pedidos_numeroOrden=p.numeroOrden\n" +
                "INNER JOIN producto pr ON pr.idProducto=pt.producto_idProducto\n" +
                "INNER JOIN farmacia f ON pr.farmacia_ruc = f.ruc\n" +
                "WHERE p.usuarioDni = ? ;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Numero de Orden     |   Estado de Pedido   |  Cantidad    |   Resumen de Pago    |  Producto    |    Farmacia");
            while (rs.next()) {
                double precio = rs.getDouble(1);
                int cantidad = rs.getInt(2);
                String numeroOrden = rs.getString(3);
                String estado = rs.getString(4);
                String nombreProducto = rs.getString(5);
                String farmacia = rs.getString(6);

                double resumenPago = (precio * cantidad);
                System.out.println(numeroOrden + "||" + estado + "||" + cantidad + "||" + resumenPago + "||" + nombreProducto + "||" + farmacia);
            }


            System.out.println("-------------------------------------------------------------------------");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    //FUNCION VER EL CARRITO DE COMPRAS
    public void verCarrito() {
        String sql = "SELECT idProducto,nombre,requiereReceta,foto,stock,precio FROM producto p\n" +
                     "WHERE idProducto=?;";
        Iterator<String> it = carrito.keySet().iterator();
        System.out.println("IdProducto   |   Nombre del Producto  | Requiere Receta  |  Foto  |   Stock    |  Precio  | Cantidad");
        while (it.hasNext()) {
            String key = it.next();
            String value = carrito.get(key);
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, key);
                ResultSet rs = pstmt.executeQuery();
                rs.next();
                    String idProducto = rs.getString(1);
                    String nombre = rs.getString(2);
                    boolean requiereReceta = rs.getBoolean(3);
                    String foto = rs.getString(4);
                    String stock = rs.getString(5);
                    double precio = rs.getDouble(6);
                    System.out.println(idProducto + "||" + nombre + "||" + requiereReceta + "||" + foto + "||" + stock + "||" + precio + "||" + value);
                    System.out.println("------------------------------------------------------------------------");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //FUNCION PARA AÑADIR UN PRODUCOT AL CARRITO

   public void agregarAlCarrito( String agregarProducto){
        String idProduct=agregarProducto;
        Scanner sc = new Scanner(System.in);
        System.out.print("Cantidad que desea Agregar al carrito: ");
        String cantidad =sc.nextLine();
        carrito.put(idProduct,cantidad);
       System.out.println(carrito);

   }


    //FUNCION PARA BUSCAR UN PRODUCTO

    public void buscarProducto() {
        Scanner sc = new Scanner(System.in);
        String sql = "SELECT idProducto,nombre,descripcion,requiereReceta,foto,stock,precio FROM producto p WHERE p.nombre LIKE ?";
        String agregarProducto = "";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            System.out.print("Ingrese el nombre del producto a buscar: ");
            String palabra = sc.nextLine();
            palabra = palabra + "%";
            pstmt.setString(1,palabra);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("IdProducto   |   Nombre del Producto  | Descripcion | Requiere Receta  |  Foto  |   Stock    |  Precio ");
            while (rs.next()) {
                String nombre = rs.getString(2);
                String idProducto = rs.getString(1);
                String descripcion = rs.getString(3);
                boolean requiereReceta = rs.getBoolean(4);
                String foto = rs.getString(5);
                int stock = rs.getInt(6);
                double precio = rs.getDouble(7);
                System.out.println(idProducto + "||" + nombre + "||" + descripcion + "||" + requiereReceta + "||" + foto + "||" + stock + "||" + precio);
                agregarProducto = idProducto;

            }
            System.out.println("------------------------------------------------------------------------");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        salir:
        while (true) {

            System.out.println("1. Agregar al carrito de compras");
            System.out.println("2. Buscar Producto");
            System.out.print("Ingrese la opción -->");
            String option2 = sc.nextLine();
            switch (option2) {
                case "1":
                    agregarAlCarrito(agregarProducto);
                    break salir;
                case "2":
                    buscarProducto();
                    break salir;
            }
        }
    }

    //FUNCION PARA AGREGAR CLIENTE
    /*public void agregarCliente(String logueo_correo){
        Scanner sc = new Scanner(System.in);
        String sqlBusqueda = "SELECT * FROM cliente";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBusqueda);) {
            while(true){
                System.out.println("Ingrese DNI: ");
                String dni = sc.nextLine();
                boolean bandera = false;
                while (rs.next()) {
                    String DNI = rs.getString(1);
                    if (dni.equalsIgnoreCase(DNI)){
                        bandera = true;
                    }
                }
                if (!bandera && dniValid(dni)) {
                    String sqlInsert = "INSERT INTO cliente(dni,nombre,apellidos,fecha_nac,distrito,logueo_correo)\n" +
                            "VALUES(?,?,?,?,?,?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert);){
                        while (true){
                            System.out.print("Ingresar nombre: ");
                            String nombre = sc.nextLine();
                            System.out.print("Ingresar apellidos: ");
                            String apellidos = sc.nextLine();
                            if (nombreyApellidoValid(nombre)) {
                                pstmt.setString(2, nombre);
                                pstmt.setString(3, apellidos);
                                while(true){
                                    System.out.print("Ingresar fecha de nacimiento con el siguiente formato YYYY/MM/DD: ");
                                    String fecha_nac = sc.nextLine();
                                    SimpleDateFormat formatter = new SimpleDateFormat();
                                    Date date = formatter.parse(fecha_nac);
                                    pstmt.setString(4, fecha_nac);
                                    System.out.print("Ingrese su distrito: ");
                                    String distrito = sc.nextLine();
                                    pstmt.setString(5, distrito);
                                    pstmt.setString(1, dni);
                                    pstmt.setString(6, logueo_correo);
                                    pstmt.executeUpdate();
                                        break;
                                }

                            }else{
                                System.out.println("Ha ingresado nombre y apellidos invalidos");
                            }
                        }
                    }

                }else {
                    System.out.println("dni incorrecto");
                }
            }
        }catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }*/


}


