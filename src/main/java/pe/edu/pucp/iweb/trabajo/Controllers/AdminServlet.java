package pe.edu.pucp.iweb.trabajo.Controllers;

import pe.edu.pucp.iweb.trabajo.Beans.BFarmacia;
import pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Admin", value = "/AdminPrincipal")
public class AdminServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String correo = request.getParameter("action");
        String busqueda = request.getParameter("busqueda") != null ? request.getParameter("busqueda") : "";
        FarmaciaDao farmaciaDao = new FarmaciaDao() ;
        if(busqueda.equals("")){
            ArrayList<BFarmacia> listaFarmacias = farmaciaDao.mostrarListaFarmacias();
            request.setAttribute("listaFarmacias",listaFarmacias);
            request.setAttribute("correo",correo);
            RequestDispatcher view = request.getRequestDispatcher("FlujoAdministrador/Listafarmacias/Listafarmacias.jsp");
            view.forward(request,response);

        }else{
            //FALTA CONFIGURAR LA VISTA DE LISTA DE FARMACIAS PARA QUE SE VEA SI ESTA BLOQUEADO O NO LA FARMACIA
            request.setAttribute("listaFarmacias",farmaciaDao.listaFarmaciasPorBusqueda(busqueda));
            request.setAttribute("correo",correo);
            RequestDispatcher view = request.getRequestDispatcher("FlujoAdministrador/Listafarmacias/Listafarmacias.jsp");
            view.forward(request,response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FarmaciaDao farmaciaDao = new FarmaciaDao();
        String correo = request.getParameter("correo");
        String opcion = request.getParameter("opcion") != null ? request.getParameter("opcion") : "";
        //System.out.println(opcion);
        //FarmaciaDao farmaciaDao = new FarmaciaDao();
        if(opcion.equalsIgnoreCase("Buscar")){
            String search = request.getParameter("search") != null ? request.getParameter("search") : "";
            // System.out.println(search);
            if(opcion.equalsIgnoreCase("")){
                response.sendRedirect(request.getContextPath() + "/AdminPrincipal?action=" + correo );

            }else{
                //System.out.println("ENTRE HASTA ANTES DE ENVIAR LA LISTA");
                //response.sendRedirect(request.getContextPath() + "/AdminPrincipal?action=" + correo + "?lista=" + farmaciaDao.listaFarmaciasPorBusqueda(search));
                response.sendRedirect(request.getContextPath() + "/AdminPrincipal?action=" + correo + "&busqueda=" + search);
            }

        }else if (opcion.equalsIgnoreCase("bloquear")){
            String numero = request.getParameter("num")!= null ? request.getParameter("num") : "1";
            int num = Integer.parseInt(numero);
            String check="check";
            ArrayList<String> lista = new ArrayList<String>();
            for(int i=0; i<num;i++){
                String check_num=check+i;
                String elemento = request.getParameter(check_num) != null ? request.getParameter(check_num) : "no";
                if (!elemento.equals("no")){
                    lista.add(elemento);
                }
            }
            for (String f : lista){
                farmaciaDao.bloquearFarmacia(f);
                System.out.println(f);
            }
            String search ="";
            response.sendRedirect(request.getContextPath() + "/AdminPrincipal?action=" + correo);
        }




    }
}