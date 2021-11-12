package pe.edu.pucp.iweb.trabajo.Controllers;

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
        String constrasenia = request.getParameter("constrasenia");
        String correo = request.getParameter("correo");
        System.out.println(correo);
        System.out.println(constrasenia);
        CredencialesDao credencialesDao = new CredencialesDao();
        String rol = credencialesDao.inicioSesion(correo,constrasenia);
        System.out.println(rol);

        if (rol.equalsIgnoreCase("administrador")){
            response.sendRedirect(request.getContextPath()+"/AdminPrincipal");
        }else if(rol.equalsIgnoreCase("cliente")){
            response.sendRedirect(request.getContextPath()+"/ClientePrincipal");
        }else if(rol.equalsIgnoreCase("farmacia")){
            response.sendRedirect(request.getContextPath()+"/FarmaciaPrincipal");
        }

    }

}
