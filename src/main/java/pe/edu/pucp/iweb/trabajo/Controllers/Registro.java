package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BFarmacia;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "registro", value = "/Registro")
public class Registro extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        FarmaciaDao farmaciaDao = new FarmaciaDao();
        ArrayList<BFarmacia> listaFarmacias= farmaciaDao.mostrarListaFarmacias();
        request.setAttribute("listaFarmacias",listaFarmacias);
        RequestDispatcher view = request.getRequestDispatcher("/Login/registro.jsp");
        view.forward(request,response);


    }
}