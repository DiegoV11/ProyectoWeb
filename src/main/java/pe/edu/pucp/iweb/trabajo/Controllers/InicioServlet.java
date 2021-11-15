package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Daos.ClienteDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "InicioServlet", value = "")
public class InicioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher view = request.getRequestDispatcher("Login/iniciar.jsp");
        view.forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* //ESTO ES PARA RECUPERAR CONTRASEÑA , PARA REGISTRARSE SE DIRIGE A LA PAGINA PRINCIPAL Y DE UNA VEZ SE VA A LA PAGINA PRINCIPAL
        String correo = request.getParameter("Correo");
        String DNI = request.getParameter("DNI");

        //SE VERIFICA QUE EXISTA ESAS CREDENCIALES , LO QUE SE INGRESO AL CAMPO ESTE BIEN , TODAVIA NO QUE EXISTA EN LA BASE DE DATOS


        //SI ESTAN BIEN SE VERIFICA AHORA SI EN LA BASE DE DATOS CON SU DNI Y CORREO INGRESADOS
        //SE IMPLEMENTO UNA FUNCION EN CLIENTE DAO QUE VERIFIQUE QUE EXISTA EL CLIENTE
        ClienteDao clienteDao = new ClienteDao();
        boolean existeCliente = clienteDao.existeCliente(correo,DNI);

        //SI NO EXISTE TENDRA QUE ENVIAR UN FEEDABACK AL USUARIO DICIENDO QUE NO EXISTE ESA COSA
        if(!existeCliente){




        }
        else{
            //SI EN CASO EXISTE SE TENDRA QUE MANDAR UN FEEDBACK AL USUARIO DICIENDO QUE LE VA A LLEGAR A SU CORREO PARA CONFIRMAR SU CAMBIO
            //DE CONTRA




        }

            */


    }
}