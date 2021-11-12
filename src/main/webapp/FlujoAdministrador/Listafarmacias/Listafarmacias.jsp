<%@ page import="pe.edu.pucp.iweb.trabajo.Daos.FarmaciaDao" %>
<%@ page import="pe.edu.pucp.iweb.trabajo.Beans.BFarmacia" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean type="java.util.ArrayList<pe.edu.pucp.iweb.trabajo.Beans.BFarmacia>" scope="request" id="listaFarmacias"/>
<jsp:useBean type="java.lang.String" scope="request" id="correo"/>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Producto</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="FlujoAdministrador/css/styles.css" rel="stylesheet" />
		
    </head>
    <body>
                <!-- Navigation-->
              <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-4 px-lg-5">
                <a class="navbar-brand" href="#!">Teledrugs</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    	
					
					
					</ul>
						<div class="dropdown">
						  <a class="btn btn-outline-dark dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
							 <i class='bi bi-person-circle' style='font-size:15px'></i>
							 Usuario	
						  </a>

						  <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
							<li><a class="dropdown-item" href="Login/iniciar.jsp">Cerrar sesión</a></li>
						  </ul>
						</div>                
                </div>
            </div>
        </nav>
		<header class="bg-dark py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="text-center text-white">
                    <h1 class="display-4 fw-bolder">Lista de Farmacias</h1>
                    <p class="lead fw-normal text-white-50 mb-0"><%=correo%></p>
                </div>
            </div>
			<div style="margin-top:30px;"class = "box">
					<input type="text" name="search" placeholder="Buscar farmacia" class="src" autocomplete = "off">
			</div>
			<div style="display:flex; align-items:center; justify-content:center; margin-top: 50px; margin-bottom: 15px;">
				<a class="btn btn-success" href="FlujoAdministrador/AgregarFarmacias/AgregarFarmacias.jsp" data-toast="" data-toast-type="success" data-toast-position="topRight" data-toast-icon="icon-circle-check" data-toast-title="Your cart" data-toast-message="is updated successfully!" style="width:250px;">Agregar Farmacia</a>
			</div>
		</header>
		

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container padding-bottom-3x mb-1", style=" margin-top: 25px;">
    <!-- Shopping Cart-->
    <div class="table-responsive shopping-cart">
        <table class="table">
            <thead>
                <tr>
                    <th class="text-center">Nombre de Farmacia</th>
					<th class="text-center">Correo</th>
					<th class="text-center">Dirección</th>
					<th class="text-center">Distrito</th>
                    <th class="text-center">Pedidos pendientes</th>
                    <th class="text-center">Bloquear</th>
                </tr>
            </thead>
            <%for(BFarmacia farmacia: listaFarmacias){ %>

            <tbody>
                <tr>
                    <td>
                        <div class="product-item">
                            <div class="product-info">
                                <h4 class="product-title"><a href="#"><%=farmacia.getNombre()%></a></h4><span><em>RUC:</em> <%=farmacia.getRuc()%></span>
                            </div>
                        </div>
                    </td>
					<td class="text-center text-lg text-medium"><%=farmacia.getCorreo()%></td>
					<td class="text-center text-lg text-medium"><%=farmacia.getDireccion()%></td>
					<td class="text-center text-lg text-medium"><%=farmacia.getDistrito()%></td>
                    <td class="text-center text-lg text-medium"><%=farmacia.getBloqueado()%></td>
                    <td class="text-center">
						<div class="form-check" style="display:flex; align-items:center; justify-content:center">
							<input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
						</div></td>
                </tr>
                <% }%>

            </tbody>
        </table>
		<div class="shopping-cart-footer">

			<!-- Modal -->
			<div class="modal" id="exampleModalToggle" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
			  <div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
				  <div class="modal-header">
					<h5 class="modal-title" id="exampleModalToggleLabel">Confirmación</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				  </div>
				  <div class="modal-body">
					¿Estas seguro de que quiere Bloquear la(s) farmacias seleccionadas?.
				  </div>
				  <div class="modal-footer">
					<button style="margin-right:10px" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
					<button class="btn btn-success" onclick="alert('Se han bloqueado las farmacias seleccionadas')" data-bs-dismiss="modal">Aceptar</button>
				  </div>
				</div>
			  </div>
			</div>
			<div class="column">
			<nav aria-label="Page navigation example">
			  <ul class="pagination justify-content-center">
				<li class="page-item disabled">
				  <a class="page-link">Previous</a>
				</li>
				<li class="page-item"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item">
				  <a class="page-link" href="#">Next</a>
				</li>
			  </ul>
			  <a class="btn btn-danger justify-content-right" style="float:right;" data-bs-toggle="modal" href="#exampleModalToggle">Bloquear</a>
			</nav>
			</div>
		</div>
    </div>
   
    
</div>
		
		
		
        <!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container"><p class="m-0 text-center text-white">Copyright &copy; Your Website 2021</p></div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>
</html>
