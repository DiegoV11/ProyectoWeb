package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BPedidoHistorial;
import pe.edu.pucp.iweb.trabajo.Beans.BProducto;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;
import pe.edu.pucp.iweb.trabajo.Daos.ProductoDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ListaProductos", value = "/ListaProductos")
public class ListaProductos extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        ProductoDao productoDao = new ProductoDao() ;
        String rucFarmacia = "27207510101";
        ArrayList<BProducto> listaProductos = productoDao.ProductosPorFarmacia(rucFarmacia);
        System.out.println("pirulin pim pon");
        request.setAttribute("listaProductos",listaProductos);
        RequestDispatcher view = request.getRequestDispatcher("/FlujoFarmacia/ListaProductos.jsp");
        view.forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}