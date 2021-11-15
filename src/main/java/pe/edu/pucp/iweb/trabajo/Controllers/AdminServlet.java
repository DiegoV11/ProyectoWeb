package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BFarmacia;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Admin", value = "/AdminPrincipal")
public class AdminServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String correo = request.getParameter("action");
        System.out.println("admin");
        System.out.println(correo);
        FarmaciaDao farmaciaDao = new FarmaciaDao() ;
        ArrayList<BFarmacia> listaFarmacias = farmaciaDao.mostrarListaFarmacias();
        request.setAttribute("listaFarmacias",listaFarmacias);
        request.setAttribute("correo",correo);
        RequestDispatcher view = request.getRequestDispatcher("FlujoAdministrador/Listafarmacias/Listafarmacias.jsp");
        view.forward(request,response);
    }


}