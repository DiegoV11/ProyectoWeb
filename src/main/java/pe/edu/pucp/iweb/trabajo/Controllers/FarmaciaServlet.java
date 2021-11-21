/*package pe.edu.pucp.iweb.trabajo.Controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FarmaciaServlet", value = "/FarmaciaPrincipal")
public class FarmaciaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/FlujoFarmacia/RegistroProductos.jsp");
        view.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}*/
package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BProducto;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;
import pe.edu.pucp.iweb.trabajo.Daos.ProductoDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FarmaciaServlet", value = "/FarmaciaPrincipal")
public class FarmaciaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String action = request.getParameter("action") != null ? request.getParameter("action") : "listar";

        ProductoDao productoDao = new ProductoDao();
        FarmaciaDao farmaciaDao = new FarmaciaDao();

        switch (action) {
            case "listar":

               /* request.setAttribute("listaProductos", farmaciaDao.listaProductos());
                RequestDispatcher view = request.getRequestDispatcher("/RegistroProductos.jsp");
                view.forward(request,response);
                break;*/
            case "formCrear":
                RequestDispatcher view2 = request.getRequestDispatcher("/FlujoFarmacia/AgregarProducto.jsp");
                view2.forward(request,response);
                break;
            case "formEditar":
                String idE = request.getParameter("id") != null ? request.getParameter("id") : "";

                BProducto productoE = productoDao.obtenerProducto(idE);

                if (productoE != null) {
                    request.setAttribute("id", productoE);
                    RequestDispatcher view3 = request.getRequestDispatcher("/FlujoFarmacia/EditarProducto.jsp");
                    view3.forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/FarmaciaPrincipal");
                }
                break;
            case "borrar":
                String id = request.getParameter("id") != null ? request.getParameter("id") : "";
                BProducto producto = productoDao.obtenerProducto(id);
                if (producto != null) { //borramos
                    try {
                        productoDao.borrarproducto(id);
                        response.sendRedirect(request.getContextPath() + "/FlujoFarmacia?msg=be");
                    } catch (SQLException e) {
                        response.sendRedirect(request.getContextPath() + "/FlujoFarmacia?err=ber");
                        e.printStackTrace();
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/FlujoFarmacia?err=ber");
                }

                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "crear";

        ProductoDao productoDao = new ProductoDao();
        FarmaciaDao farmaciaDao = new FarmaciaDao();

        switch (action) {
            case "crear":

                String idProducto = request.getParameter("idProducto") != null ? request.getParameter("idProducto") : "";
                String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
                String precioStr = request.getParameter("precio") != null ? request.getParameter("precio") : "";
                String stockStr = request.getParameter("stock") != null ? request.getParameter("stock") : "";
                String descripcion = request.getParameter("descripcion") != null ? request.getParameter("descripcion") : "";
                String requiere = request.getParameter("requiere") != null ? request.getParameter("requiere") : "";
                String farmacia_ruc = request.getParameter("farmacia_ruc") != null ? request.getParameter("farmacia_ruc") : "";
                double precio = Double.parseDouble(precioStr);
                int stock = Integer.parseInt(stockStr);
                boolean requiereReceta = Boolean.parseBoolean(requiere);


                productoDao.inserta_producto(nombre, precio, stock, descripcion, requiereReceta, farmacia_ruc);
                //CORREGIR
                response.sendRedirect(request.getContextPath() + "/FlujoFarmacia?msg=be");
                break;
            case "update":
                String nombre2 = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
                String precioStr2 = request.getParameter("precio") != null ? request.getParameter("precio") : "";
                String stockStr2 = request.getParameter("stock") != null ? request.getParameter("stock") : "";
                String descripcion2 = request.getParameter("descripcion") != null ? request.getParameter("descripcion") : "";
                String requiere2 = request.getParameter("requiere") != null ? request.getParameter("requiere") : "";
                String farmacia_ruc2 = request.getParameter("farmacia_ruc") != null ? request.getParameter("farmacia_ruc") : "";
                double precio2 = Double.parseDouble(precioStr2);
                int stock2 = Integer.parseInt(stockStr2);
                boolean requiereReceta2 = Boolean.parseBoolean(requiere2);


                productoDao.inserta_producto(nombre2, precio2, stock2, descripcion2, requiereReceta2, farmacia_ruc2);
                //CORREGIR
                response.sendRedirect(request.getContextPath() + "/FlujoFarmacia?msg=be");
                break;
            case "buscar":
                String texto = request.getParameter("textoBuscar");
                if (texto == null) {
                    response.sendRedirect(request.getContextPath() + "/JobServlet");
                } else {
                    request.setAttribute("listaProductos", productoDao.buscarProductoPorNombre(texto));
                    request.setAttribute("textoBusqueda", texto);
                    RequestDispatcher view = request.getRequestDispatcher("/jobs/lista.jsp");
                    view.forward(request, response);
                }
        }

    }
}
