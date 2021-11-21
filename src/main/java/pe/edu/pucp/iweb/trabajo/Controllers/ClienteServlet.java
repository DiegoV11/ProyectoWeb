/*package pe.edu.pucp.iweb.trabajo.Controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ClienteServlet", value = "/ClientePrincipal")
public class ClienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String DNI = request.getParameter("action");
        request.setAttribute("dni",DNI);
        RequestDispatcher view = request.getRequestDispatcher("FlujoUsuario/homepage.jsp");
        view.forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}*/
package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BCliente;
import pe.edu.pucp.iweb.trabajo.Beans.BPedido;
import pe.edu.pucp.iweb.trabajo.Beans.BPedidoCliente;
import pe.edu.pucp.iweb.trabajo.Daos.ClienteDao;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ClienteServlet", value = "/Usuario")
public class ClienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String opcion = request.getParameter("opcion") != null ? request.getParameter("opcion") : "salir";
        String correo = request.getParameter("correo") != null ? request.getParameter("correo") : "salir";
        request.setAttribute("correo",correo);
        ClienteDao clienteDao = new ClienteDao();
        FarmaciaDao farmaciaDao= new FarmaciaDao();
        request.setAttribute("listafarmacias",farmaciaDao.mostrarListaFarmacias());
        switch (opcion) {
            case "mostrarPerfil":
                BCliente bCliente = clienteDao.mostrarPerfil(correo);
                request.setAttribute("Perfil",bCliente);
                RequestDispatcher view = request.getRequestDispatcher("/FlujoUsuario/profile.jsp");
                view.forward(request, response);
                break;
            case "historialPedidos":
                String dni =  clienteDao.DNI(correo);
                ArrayList<BPedidoCliente> pedidos = clienteDao.mostrarHistorial(dni);
                request.setAttribute("listaPedidos",pedidos);
                RequestDispatcher view2 = request.getRequestDispatcher("/FlujoUsuario/historial.jsp");
                view2.forward(request, response);
                break;
            case "mostrarProducto":
                RequestDispatcher view7 = request.getRequestDispatcher("/FlujoUsuario/paracetamol.jsp");
                view7.forward(request, response);
                break;
            case "carrito":
                break;
            case "salir":
                RequestDispatcher view1 = request.getRequestDispatcher("/FlujoUsuario/homepage.jsp");
                view1.forward(request, response);
                break;
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String opcion = request.getParameter("opcion") != null ? request.getParameter("opcion") : "salir";
        String correo = request.getParameter("correo") != null ? request.getParameter("correo") : "salir";
        request.setAttribute("correo",correo);
        ClienteDao clienteDao = new ClienteDao();
        FarmaciaDao farmaciaDao= new FarmaciaDao();
        request.setAttribute("listafarmacias",farmaciaDao.mostrarListaFarmacias());
        switch (opcion) {
            case "Update":
                String nombre = request.getParameter("Nombres") != null ? request.getParameter("Nombres") : "";
                String apellidos = request.getParameter("Apellidos") != null ? request.getParameter("Apellidos") : "";
                String distrito = request.getParameter("Distrito") != null ? request.getParameter("Distrito") : "";

                clienteDao.updatePerfil(nombre, apellidos, distrito, correo);
                response.sendRedirect(request.getContextPath() + "/Usuario?correo=" + correo + "&opcion=mostrarPerfil");
                break;
            case "Buscar":

                String texto = request.getParameter("search");
                if (texto.equalsIgnoreCase("")) {
                    response.sendRedirect(request.getContextPath() + "/Usuario?correo=" + correo + "&opcion=salir");
                } else {
                    request.setAttribute("productos", clienteDao.buscarProducto(texto));
                    request.setAttribute("BuscarProducto", texto);
                    RequestDispatcher view3 = request.getRequestDispatcher("/FlujoUsuario/jabon.jsp");
                    view3.forward(request, response);
                }
                break;
            case "carrito":
                String numero = request.getParameter("numero") != null ? request.getParameter("numero") : "0";
                int numeroInt= Integer.parseInt(numero);
                RequestDispatcher view4 = request.getRequestDispatcher("/FlujoUsuario/shopping_cart.jsp");
                view4.forward(request, response);
                break;
            case "mostrarFarmacia":
                String farmaciaRuc= request.getParameter("ruc") != null ? request.getParameter("ruc") : "";
                RequestDispatcher view5 = request.getRequestDispatcher("/FlujoUsuario/homepage.jsp");
                view5.forward(request,response);
                break;

        }

    }
}

