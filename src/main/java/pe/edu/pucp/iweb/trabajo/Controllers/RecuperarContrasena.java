package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Daos.ClienteDao;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "recuperarcontrasena", value = "/RecuperarContrasena")
public class RecuperarContrasena extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RequestDispatcher view = request.getRequestDispatcher("/Login/recuperar.jsp");
        view.forward(request,response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("Correo");
        String DNI = request.getParameter("DNI");

        //SE VERIFICA QUE EXISTA ESAS CREDENCIALES , LO QUE SE INGRESO AL CAMPO ESTE BIEN , TODAVIA NO QUE EXISTA EN LA BASE DE DATOS

        //SE VERIFICA DNI
        ClienteDao clienteDao = new ClienteDao();
        boolean dniCorrecto = clienteDao.dniValid(DNI);

        //SE VERIFICA EMAIL
        boolean correoCorrecto = clienteDao.emailisValid(correo);

        if(dniCorrecto & correoCorrecto){
            //TOCA HACER LA VALIDACION DE SI EXISTE UN USUARIO CON ESAS CREDENCIALES
            boolean existeCliente = clienteDao.existeCliente(correo,DNI);
            System.out.println(existeCliente);
            if(!existeCliente){
                //SI NO EXISTE EL CLIENTE SE MUESTRA UN MENSAJE DE ERROR FEEDBACK
            }else{

                //FALTA CREAR EL HTML PARA PODER CAMBIAR LA CONTRASEÑA
                //ACA TENDRIA QUE SER UN REDIRECT PARA QUE NOS MANDE A LA VISTA DEL HTML DONDE SE PODRA CAMBIAR LA CONTRASEÑA
                //Y DE AHI NOS MANDARIA A LA VISTA DE LOGIN DENUEVO PARA PODER INICIAR SESION
                response.sendRedirect(request.getContextPath() +  "/RecuperarContrasena");
            }
        }else{
            //SI NO ESTA CORRECTO SE MUESTRA UN MENSAJE DE FEEDBACK
        }


    }
}
