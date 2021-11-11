package pe.edu.pucp.iweb.trabajo.Controllers;

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
}
