/*package pe.edu.pucp.iweb.trabajo.Controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HistorialPedido", value = "/HistorialPedido")
public class HistorialPedido extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
*/
package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BFarmacia;
import pe.edu.pucp.iweb.trabajo.Beans.BPedidoHistorial;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "HistorialPedido", value = "/HistorialPedido")
public class HistorialPedido extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        FarmaciaDao farmaciaDao = new FarmaciaDao() ;
        ArrayList<BPedidoHistorial> listaHistorialPedidos = farmaciaDao.mostrarHistorialPedidos();
        request.setAttribute("listaHistorialPedidos",listaHistorialPedidos);
        RequestDispatcher view = request.getRequestDispatcher("/FlujoFarmacia/HistorialPedidos.jsp");
        view.forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
