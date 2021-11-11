<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Iniciar sesión</title>

    <!-- Font Icon -->
    <link  href="Login/fonts/material-icon/css/material-design-iconic-font.min.css"rel="stylesheet">

    <!-- Main css -->
    <link  href="Login/css/style.css" rel="stylesheet">
</head>
<body>

    <div class="main">

        <!-- Sing in  Form -->
        <section class="sign-in">
            <div class="container">
                <div class="signin-content">
                    <div class="signin-image">
                        <br>
                        <figure style="margin-bottom:0px;"><img src="Login/images/login.png" alt="sing up image"></figure>
                        <!--a href="Login/registro.jsp" class="signup-image-link">Crear una cuenta</a-->
                        <a href="<%= request.getContextPath() %>/Registro" class="signup-image-link">Crear una cuenta</a>
                    </div>

                    <div class="signin-form">
                        <h2 style="text-align:center;" class="form-title">Iniciar sesión TeleDrugs</h2>
                        <form method="POST" class="register-form" id="login-form">
                            <div class="form-group">
                                <label for="correo"><i class="zmdi zmdi-email"></i></label>
                                <input type="correo" name="correo" id="correo" placeholder="Correo electrónico"/>
                            </div>
                            <div class="form-group">
                                <label for="your_pass"><i class="zmdi zmdi-lock"></i></label>
                                <input type="password" name="your_pass" id="your_pass" placeholder="Contraseña"/>
                            </div>
                            <div class="form-group">
                                <input type="checkbox" name="remember-me" id="remember-me" class="agree-term" />
                                <label for="remember-me" class="label-agree-term"><span><span></span></span>Recordarme</label>
                                <br>
                                <br>

                                <!--a href="Login/recuperar.jsp" class="signup-image-link">¿Has olvidado tu contraseña?</a-->
                                <a href="<%= request.getContextPath() %>/RecuperarContrasena" class="signup-image-link">¿Has olvidado tu contraseña?</a>
                            </div>
                            <div class="form-group form-button">
                                <div style="display:flex; align-items:center; justify-content:center;" class="column"><a href="<%= request.getContextPath() %>/PaginaPrincipal" style="width:150px" class="btn btn-success" data-bs-toggle="modal" >Ingresar</a></div>
                            </div>

                        </form>
                        <!--<div class="social-login">
                            <span class="social-label">Or login with</span>
                            <ul class="socials">
                                <li><a href="#"><i class="display-flex-center zmdi zmdi-facebook"></i></a></li>
                                <li><a href="#"><i class="display-flex-center zmdi zmdi-twitter"></i></a></li>
                                <li><a href="#"><i class="display-flex-center zmdi zmdi-google"></i></a></li>
                            </ul>
                        </div>-->
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