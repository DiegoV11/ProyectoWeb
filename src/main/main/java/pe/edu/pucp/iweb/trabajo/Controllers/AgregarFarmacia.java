package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BFarmacia;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AgregarFarmacia", value = "/AgregarFarmacia")
public class AgregarFarmacia extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        System.out.println("agregar");
        System.out.println(user);
        request.setAttribute("usuario",user);
        RequestDispatcher view = request.getRequestDispatcher("/FlujoAdministrador/AgregarFarmacias/AgregarFarmacias.jsp");
        view.forward(request,response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("usuario");
        System.out.println("agregar post");
        System.out.println(user);

        String imagenFarmacia = request.getParameter("Imagen");
        String RUCFarmacia = request.getParameter("RUC");
        String direccionFarmacia = request.getParameter("Direccion");
        String distritoFarmacia = request.getParameter("Distrito");
        String nombreFarmacia = request.getParameter("Nombre");
        String correoFarmacia = request.getParameter("Correo");
        String contrasenaFarmacia = request.getParameter("Contrasena");

        //VALIDACION DEL RUC
        FarmaciaDao farmaciaDao = new FarmaciaDao();
        boolean correctoRUCFarmacia = farmaciaDao.rucValid(RUCFarmacia);

        //VALIDACION DE CONTRASENA
        boolean correctoContrasena = farmaciaDao.contrasenaisValid(contrasenaFarmacia);

        if(correctoRUCFarmacia && correctoContrasena){
            boolean existeFarmacia = farmaciaDao.existeFarmacia(RUCFarmacia);
            if(existeFarmacia){
                //YA EXISTE UNA IMPRIMIRIA UN FEEDBACK DICIENDO QUE YA EXISTE UNA FARMACIA
            }
            else{
                BFarmacia farmacita = new BFarmacia(RUCFarmacia,nombreFarmacia,direccionFarmacia,distritoFarmacia,imagenFarmacia,correoFarmacia);
                farmaciaDao.insertarFarmacia(farmacita,contrasenaFarmacia);
                response.sendRedirect(request.getContextPath() + "/AgregarFarmacia?user="+user);
            }
        }else{
            //ACA SE TENDRIA QUE IMPRIMIR UN MENSAJE DE FEEDBACK DICIENDO QUE EL CAMPO DE RUC ESTA MAL INGRESADO
            //SI ESTA MAL EL RUC O LA CONTRASENA
        }

    }
}
