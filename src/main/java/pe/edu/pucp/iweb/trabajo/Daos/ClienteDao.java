package pe.edu.pucp.iweb.trabajo.Daos;

import pe.edu.pucp.iweb.trabajo.Beans.BBuscarProductoCliente;
import pe.edu.pucp.iweb.trabajo.Beans.BCliente;
import pe.edu.pucp.iweb.trabajo.Beans.BPedidoCliente;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
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
    public boolean emailisValid(String email) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
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
    public BCliente mostrarPerfil(String correo) {
        String sql = "SELECT c.dni,c.nombre,c.apellidos,c.distrito FROM cliente c  WHERE ? = c.logueo_correo";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            String dni = rs.getString(1);
            String nombre = rs.getString(2);
            String apellidos = rs.getString(3);
            String distrito = rs.getString(4);
            BCliente bCliente = new BCliente(dni,nombre,apellidos,distrito,correo);
            return bCliente;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String DNI(String correo) {
        String sql = "SELECT c.dni FROM cliente c WHERE ? = c.logueo_correo";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            String dni = rs.getString(1);
            return dni;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //FUNCION QUE MUESTAR HISTORIAL DE PEDIDOS
    public ArrayList<BPedidoCliente> mostrarHistorial(String DNI) {
        ArrayList<BPedidoCliente> pedidos = new ArrayList<>();
        String sql = "SELECT truncate(sum(pr.precio*pt.cantidad),1),sum(pt.cantidad),p.numeroOrden , p.estado , f.nombre FROM pedidos p\n" +
                "INNER JOIN producto_tiene_pedidos pt ON pt.pedidos_numeroOrden=p.numeroOrden\n" +
                "INNER JOIN producto pr ON pr.idProducto=pt.producto_idProducto\n" +
                "INNER JOIN farmacia f ON pr.farmacia_ruc = f.ruc\n" +
                "WHERE p.usuarioDni = ? GROUP BY p.numeroOrden;";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                double resumenPago = rs.getDouble(1);
                int cantidad = rs.getInt(2);
                int numeroOrden = rs.getInt(3);
                String estado = rs.getString(4);
                String farmacia = rs.getString(5);
                pedidos.add(new BPedidoCliente(numeroOrden,cantidad, estado,resumenPago,farmacia));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pedidos;
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

    public ArrayList<BBuscarProductoCliente> buscarProducto(String nombre) {
        ArrayList<BBuscarProductoCliente> productos = new ArrayList<>();

        nombre = nombre.toLowerCase();

        String sql = "SELECT nombre,descripcion,foto,precio FROM producto p WHERE lower(p.nombre) LIKE ?;";


        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,"%" + nombre + "%");

            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    BBuscarProductoCliente producto = new BBuscarProductoCliente();
                    producto.setNombre(rs.getString(1));
                    producto.setDescripcion(rs.getString(2));
                    producto.setFoto(rs.getString(3));
                    producto.setPrecio(rs.getDouble(4));
                    productos.add(producto);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        /*salir:
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
        }*/
        return productos;
    }

    public BCliente updatePerfil(String nombre, String Apellido, String distrito, String correo){
        String sql="UPDATE cliente c SET c.nombre = ?, c.apellidos = ?, c.distrito = ? WHERE c.logueo_correo= ?;";
        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombre);
            pstmt.setString(2,Apellido);
            pstmt.setString(3,distrito);
            pstmt.setString(4,correo);
            pstmt.executeUpdate();
            BCliente bCliente = mostrarPerfil(correo);
            return bCliente;
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }




    public String obtenerIDCliente (String correo){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String DNI = null;  //IGUAL SE LE VA ALLENAR EL CAMPO YA QUE SE VALIDO QUE EXISTE UNA PERSONA CON DICHO DNI

        String sentenciaSQL = "SELECT dni,nombre,apellidos ,logueo_correo FROM cliente\n"+
                                "INNER JOIN credenciales ON (cliente.logueo_correo = credenciales.correo)\n"+
                                "WHERE logueo_correo = ?;\n";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sentenciaSQL)) {

            pstmt.setString(1,correo);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    DNI = rs.getString(1);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return DNI;
    }


    public boolean existeCliente(String correo, String dni){
        Boolean existe = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql="SELECT * FROM cliente WHERE dni = ? AND logueo_correo = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,dni);
            pstmt.setString(2,correo);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                existe=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return existe;
    }
}


