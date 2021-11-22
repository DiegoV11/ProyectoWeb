/*package pe.edu.pucp.iweb.trabajo.Controllers;

import jdk.swing.interop.SwingInterOpUtils;
import pe.edu.pucp.iweb.trabajo.Beans.BAdministrador;
import pe.edu.pucp.iweb.trabajo.Daos.ClienteDao;
import pe.edu.pucp.iweb.trabajo.Daos.CredencialesDao;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PaginaPrincipal", value = "/PaginaPrincipal")
public class PaginaPrincipal extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher view = request.getRequestDispatcher("/FlujoUsuario/homepage.jsp");
        view.forward(request,response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act") != null ? request.getParameter("act") : "login";

        if (act.equalsIgnoreCase("reg")) {
            String nombre = request.getParameter("Nombres");
            String apellido = request.getParameter("Apellidos");
            String dni = request.getParameter("DNI");
            String birthday = request.getParameter("FechaNacimiento");
            String distrito = request.getParameter("Distrito");
            String email = request.getParameter("Correo");
            String contrasenia = request.getParameter("Contrasena");
            String recontrasenia = request.getParameter("RePass");

            //SE VALIDA QUE LOS DATOS INGRESADOS SON CORRECTOS CON LOS CAMPOS SOLICITADOS
            ClienteDao clienteDao = new ClienteDao();
            boolean nombreCorrecto = clienteDao.nombreyApellidoValid(nombre);
            boolean apellidoCorrecto = clienteDao.nombreyApellidoValid(apellido);
            boolean dniCorrecto = clienteDao.dniValid(dni);
            boolean birthdayCorrecto = clienteDao.fechaIsValid(birthday);
            boolean correoCorrecto = clienteDao.emailisValid(email);

            //SE TIENE QUE VALIDAR TAMBIEN QUE ESTE LA CONTRASENA SEA IGUAL A LA RECONTRASENA
            if(contrasenia.equals(recontrasenia) && nombreCorrecto && apellidoCorrecto && dniCorrecto && birthdayCorrecto && correoCorrecto ){
                //UNA VEZ QUE ESTE BIEN AHORA SE VERIFICA SI NO EXISTE ALGUIEN MAS
                boolean existeCliente = clienteDao.existeCliente(dni,email);
                if(!existeCliente) {
                    //SI NO EXISTE ENTONCES SE INSERTA A LA BASE DE DATOS
                    clienteDao.registrarDatosUsuario(email, dni, nombre, apellido, birthday, distrito);
                    //SI ESTA OK QUEDA
                    response.sendRedirect(request.getContextPath() + "/Registro");

                }else {
                    //SI YA EXISTE EL CLIENTE TIENE QUE SALIR UN MENSAJE DE ERROR
                }
            }
            else{
                //SE MUESTRA MENSAJE DE ERROR FEEDBACK
            }


        } else if (act.equalsIgnoreCase("login")) {
            String constrasenia = request.getParameter("constrasenia");
            String correo = request.getParameter("correo");

            //ANTES DE ENVIARSELO AL DAO , TENDRIA QUE VALIDAR QUE ES UN FORMATO CORRECTO LO QUE SE INGRESO EN DICHO CAMPO?

            //ESTA CORRECTO EN ALL CASO
            CredencialesDao credencialesDao = new CredencialesDao();
            String rol = credencialesDao.inicioSesion(correo, constrasenia);
            System.out.println(rol);
            if(rol == null){
                //SE GENERA ERROR YA QUE NO EXISTE EL USUARIO


            }
            else{
                if(rol.equalsIgnoreCase("cliente")){
                    //TENDRIA QUE OBTENER SU DNI Y PASARSELO A LA VISTA
                    // PARA YA TENER SU INFORMACION
                    ClienteDao clienteDao = new ClienteDao();
                    String DNI = clienteDao.obtenerIDCliente(correo);
                    response.sendRedirect(request.getContextPath() + "/ClientePrincipal?action="+DNI);

                }else if(rol.equalsIgnoreCase("Administrador")){
                    response.sendRedirect(request.getContextPath() + "/AdminPrincipal?action=" + correo);

                }else{
                    //CREO QUE SU ID ES SU RUC xd
                    FarmaciaDao farmaciaDao = new FarmaciaDao();
                    String RUC = farmaciaDao.obtenerRUCFarmacia(correo);
                    response.sendRedirect(request.getContextPath() + "/FarmaciaPrincipal?action=" + RUC);

                }
            }




        }






    }



}*/
package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BAdministrador;
import pe.edu.pucp.iweb.trabajo.Beans.BCliente;
import pe.edu.pucp.iweb.trabajo.Daos.ClienteDao;
import pe.edu.pucp.iweb.trabajo.Daos.CredencialesDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PaginaPrincipal", value = "/PaginaPrincipal")
public class PaginaPrincipal extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher view = request.getRequestDispatcher("/FlujoUsuario/homepage.jsp");
        view.forward(request,response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act") != null ? request.getParameter("act") : "login";

        if (act.equalsIgnoreCase("reg")) {
            String nombre = request.getParameter("Nombres");
            String apellido = request.getParameter("Apellidos");
            String dni = request.getParameter("DNI");
            String birthday = request.getParameter("FechaNacimiento");
            String distrito = request.getParameter("Distrito");
            String email = request.getParameter("Correo");
            String contrasenia = request.getParameter("Contrasena");
            String recontrasenia = request.getParameter("RePass");

            CredencialesDao credencialesDao = new CredencialesDao();
            boolean nombreCorrecto = credencialesDao.nombreValid(nombre);
            boolean apellidoCorrecto = credencialesDao.apellidoValid(apellido);
            boolean dniCorrecto = credencialesDao.dniValid(dni);
            boolean birthdayCorrecto = credencialesDao.fechaIsValid(birthday);
            //EL DISTRITO YA NO SE VALIDA PORQUE VA A ESCOGER UNO DE LA LISTA
            boolean correoCorrecto = credencialesDao.emailisValid(email);
            boolean contrasenaCorrecto = credencialesDao.contrasenaisValid(contrasenia);
            boolean recontrasenaCorrecto = false;

            if(recontrasenia.equals(contrasenia)){
                recontrasenaCorrecto = true;
            }

            ClienteDao clienteDao = new ClienteDao();
            if(nombreCorrecto & apellidoCorrecto & dniCorrecto & birthdayCorrecto & contrasenaCorrecto & recontrasenaCorrecto){
                //VALIDAMOS SI EXISTE EL CLIENTE
                boolean existeCliente = clienteDao.existeCliente(email,contrasenia);
                if(existeCliente == true){
                    //SE IMPRIME UN MENSAJE DE ERRROR UN FEEDBACK
                }
                else{
                    BCliente clientito = new BCliente(dni,nombre,apellido,birthday,distrito,email);
                    // YA TENGO EL CLIENTE AHORA FALTA PASAR LA CONTRASEÑA PARA PODER REGISTRARLO
                    //PRIMERO REGISTRAMOS EN LAS CREDENCIALES
                    credencialesDao.insertCliente(email,contrasenia);

                    //UNA VEZ REGISTRADO LAS CREDENCIALES , REGISTRAMOS EL CLIENTE
                    clienteDao.registrarCliente(clientito);
                    response.sendRedirect(request.getContextPath() + "/Registro");
                }

            }
            else{
                //COMO SON DEMASIADAS VALIDACIONES , HABRIA UN MONTON DE CASOS , ENTONCES LO IDEAL SERIA MOSTRARLE UN SOLO MENSAJE
                //DICIENDOLE QUE LOS CAMPOS ESTAN MAL Y YA , EN FORMA GENERAL PARA NO DECIRLE UNO EN ESPECIFICO

            }

        } else if (act.equalsIgnoreCase("login")) {

            String constrasenia = request.getParameter("constrasenia");
            String correo = request.getParameter("correo");

            CredencialesDao credencialesDao = new CredencialesDao();

            if(credencialesDao.existeCredenciales(correo,constrasenia)){
                String rol = credencialesDao.rolCredenciales(correo);
                System.out.println(rol);
                //String DNI = credencialesDao.obtenerIDCliente(correo);
                //BAdministrador bAdministrador = new BAdministrador();
                //bAdministrador.setContrasenia(constrasenia);
                //bAdministrador.setLogueoCorreo(correo);

                if (rol.equalsIgnoreCase("administrador")) {
                    response.sendRedirect(request.getContextPath() + "/AdminPrincipal?action=" + correo);
                } else if (rol.equalsIgnoreCase("cliente")) {
                    response.sendRedirect(request.getContextPath() + "/Usuario?correo=" + correo);
                } else if (rol.equalsIgnoreCase("farmacia")) {
                    response.sendRedirect(request.getContextPath() + "/FarmaciaPrincipal");
                }

            }
            else{
                //MENSAJE DE ERROR MENSAJE DE FEEDBACK
            }

        }
    }
}



