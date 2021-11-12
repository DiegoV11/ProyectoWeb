<%@ page import="pe.edu.pucp.iweb.trabajo.Beans.BFarmacia" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean type="java.util.ArrayList<pe.edu.pucp.iweb.trabajo.Beans.BFarmacia>" scope="request" id="listaFarmacias"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Registro</title>

    <!-- Font Icon -->
    <link href="Login/fonts/material-icon/css/material-design-iconic-font.min.css" rel="stylesheet">

    <!-- Main css -->
    <link href="Login/css/style.css" rel="stylesheet">
</head>
<body>

    <div class="main">

        <!-- Sign up form -->
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <div class="signup-form"  style="margin-right:10px; margin-left:10px;" >
                        <h2 style="text-align:center" class="form-title">Registrarse</h2>
                        <form method="POST" action="<%=request.getContextPath()%>/PaginaPrincipal?act=reg" class="register-form" id="register-form" >
                            <div class="form-group" style="width:400px; margin:auto; margin-bottom:30px;">
                                <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input style="width:400px;" type="text" name="name" id="name" placeholder="Nombres y Apellidos"/>
                            </div>
                            <div class="form-group" style="width:400px; margin:auto; margin-bottom:30px;">
                                <label for="dni"><i class="zmdi zmdi-assignment-account"></i></label>
                                <input style="width:400px;" type="number" name="dni" id="dni" placeholder="DNI"/>
                            </div>
                            <div class="form-group" style="width:400px; margin:auto; margin-bottom:30px;">
                                <label for="birthday"><i class="zmdi zmdi-calendar"></i></label>
                                <input style="width:400px;" type="date" name="birthday" id="birthday" placeholder="Fecha de nacimiento"/>
                            </div>
                            <div class="form-group" style="width:400px; margin:auto; margin-bottom:30px;">
                                <label for="listadistrito"><i class="zmdi zmdi-home"></i></label>

                                <input style="width:400px;" type="search" name="distrito" list="listadistrito" placeholder="Distrito en el que reside">

                                <datalist id="listadistrito">
                                    <%for(BFarmacia farmacia: listaFarmacias){ %>

                                    <option value="<%=farmacia.getDistrito()%>"><%=farmacia.getDistrito()%></option>
                                    <%}%>
                                </datalist>

                            </div>
                            <div class="form-group" style="width:400px; margin:auto; margin-bottom:30px;">
                                <label for="email"><i class="zmdi zmdi-email"></i></label>
                                <input style="width:400px;" type="email" name="email" id="email" placeholder="Correo electrónico"/>
                            </div>
                            <div class="form-group" style="width:400px; margin:auto; margin-bottom:30px;">
                                <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                <input style="width:400px;" type="password" name="pass" id="pass" placeholder="Contraseña"/>
                            </div>
                            <div class="form-group" style="width:400px; margin:auto; margin-bottom:30px;">
                                <label for="re_pass"><i class="zmdi zmdi-lock-outline"></i></label>
                                <input style="width:400px;" type="password" name="re_pass" id="re_pass" placeholder="Repita su contraseña"/>
                            </div>
                            <!--<div class="form-group">
                                <input type="checkbox" name="agree-term" id="agree-term" class="agree-term" />
                                <label for="agree-term" class="label-agree-term"><span><span></span></span>I agree all statements in  <a href="#" class="term-service">Terms of service</a></label>
                            </div>-->
                            <div class="form-group form-button">
                                <div style="display:flex; align-items:center; justify-content:center;" class="column">
                                    <button class="btn btn-success" style="width:150px" type="submit">Registrar</button>
                                   </div>
                            </div>
                        </form>
                    </div>
                    <div class="signup-image" style="margin-right:0px; margin-left:0px" >
                        <figure style="margin-bottom:40px; margin-top:40px; height:400px; width:400px;"><img src="Login/images/Cyborg-amico.png" alt="sing up image"></figure>
                        <a href="<%= request.getContextPath() %>" class="signup-image-link">Ya soy miembro</a>
                    </div>
                </div>
            </div>
        </section>


    </div>

    <!-- JS -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="js/main.js"></script>
</body><!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>