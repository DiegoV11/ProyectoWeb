package pe.edu.pucp.iweb.trabajo.Controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AgregarFarmacia", value = "/AgregarFarmacia")
public class AgregarFarmacia extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Estoy en get");
        RequestDispatcher view = request.getRequestDispatcher("/FlujoAdministrador/AgregarFarmacias/AgregarFarmacias.jsp");
        view.forward(request,response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Estoy en post");
        String ruc = request.getParameter("ruc");
        String direccion = request.getParameter("direccion");
        String distrito = request.getParameter("distrito");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");

        System.out.println(ruc);
        System.out.println(direccion);
        System.out.println(distrito);
        System.out.println(nombre);
        System.out.println(correo);

        response.sendRedirect(request.getContextPath() + "/AgregarFarmacia");


    }
}
