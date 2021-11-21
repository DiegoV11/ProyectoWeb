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
            String nombreCompleto = request.getParameter("name");
            String dni = request.getParameter("dni");
            String birthday = request.getParameter("birthday");
            String distrito = request.getParameter("distrito");
            String email = request.getParameter("email");
            String contrasenia = request.getParameter("pass");
            String recontrasenia = request.getParameter("re_pass");

            ClienteDao clienteDao = new ClienteDao();
            boolean registro=clienteDao.registrarCliente(email,contrasenia);
            if(registro) {
                clienteDao.registrarDatosUsuario(email, dni, nombreCompleto, nombreCompleto, birthday, distrito);
                response.sendRedirect(request.getContextPath());
            }else {
                response.sendRedirect(request.getContextPath() + "/Registro");
            }



        } else if (act.equalsIgnoreCase("login")) {

            String constrasenia = request.getParameter("constrasenia");
            String correo = request.getParameter("correo");
            CredencialesDao credencialesDao = new CredencialesDao();
            String rol = credencialesDao.inicioSesion(correo, constrasenia);
            BAdministrador bAdministrador=new BAdministrador();
            bAdministrador.setContrasenia(constrasenia);
            bAdministrador.setLogueoCorreo(correo);

            if (rol.equalsIgnoreCase("administrador")) {
                response.sendRedirect(request.getContextPath() + "/AdminPrincipal");
            } else if (rol.equalsIgnoreCase("cliente")) {
                response.sendRedirect(request.getContextPath() + "/Usuario?correo=" + correo);
            } else if (rol.equalsIgnoreCase("farmacia")) {
                response.sendRedirect(request.getContextPath() + "/FarmaciaPrincipal");
            }
        }
    }
}


