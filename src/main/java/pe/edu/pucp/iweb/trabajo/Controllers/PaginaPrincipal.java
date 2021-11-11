package pe.edu.pucp.iweb.trabajo.Controllers;

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

}
