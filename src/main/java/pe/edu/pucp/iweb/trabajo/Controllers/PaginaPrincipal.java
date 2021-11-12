package pe.edu.pucp.iweb.trabajo.Controllers;

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

            System.out.println(nombreCompleto);
            System.out.println(dni);
            System.out.println(distrito);
            System.out.println(birthday);
            System.out.println(email);
            System.out.println(contrasenia);
            System.out.println(recontrasenia);
            ClienteDao clienteDao = new ClienteDao();
            clienteDao.registrarCliente(email,contrasenia);
            clienteDao.registrarDatosUsuario(email,dni,nombreCompleto,nombreCompleto,birthday,distrito);
            response.sendRedirect(request.getContextPath());





        } else if (act.equalsIgnoreCase("login")) {

            String constrasenia = request.getParameter("constrasenia");
            String correo = request.getParameter("correo");
            System.out.println(correo);
            System.out.println(constrasenia);
            CredencialesDao credencialesDao = new CredencialesDao();
            String rol = credencialesDao.inicioSesion(correo, constrasenia);

            if (rol.equalsIgnoreCase("administrador")) {
                response.sendRedirect(request.getContextPath() + "/AdminPrincipal");
            } else if (rol.equalsIgnoreCase("cliente")) {
                response.sendRedirect(request.getContextPath() + "/ClientePrincipal");
            } else if (rol.equalsIgnoreCase("farmacia")) {
                response.sendRedirect(request.getContextPath() + "/FarmaciaPrincipal");
            }

        }
        }
}
