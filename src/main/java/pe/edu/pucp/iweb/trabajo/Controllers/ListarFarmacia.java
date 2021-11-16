package pe.edu.pucp.iweb.trabajo.Controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ListarFarmacia", value = "/ListarFarmacia")
public class ListarFarmacia extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //SI EN CASO SE QUIERE BLOQUEAR LA FARMACIA , NO SERIA TENER UN ICONO DE CHECK AL COSTADO DE OTRO? PARA ASI PODER SABER SU ID
        //PORQUE SE PODRIA TENER LOS IDS SELECCIONADOS EN UNA LISTA Y DE AHI PASARLOS AL SERVLET DONDE AHI SE BORRAN Y
        //DE AHI SE IMPRIMAN LAS QUE QUEDAN?
        //CHEQUEAR <----




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
