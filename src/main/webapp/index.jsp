<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
            <html>

            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="description" content="Login and Registration Form with HTML5 and CSS3" />
                <meta name="keywords" content="html5, css3, form, switch, animation, :target, pseudo-class" />
                <meta name="author" content="Codrops" />
                <link rel="shortcut icon" href="../favicon.ico">
                <link rel="stylesheet" type="text/css" href="demo.css" />
                <link rel="stylesheet" type="text/css" href="style.css" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

                <link rel="stylesheet" type="text/css" href="animate-custom.css" />


            </head>



            <body class="bodyy">

                <section>
                    <div id="container_demo">

                        <a class="hiddenanchor" id="toregister"></a>
                        <a class="hiddenanchor" id="tologin"></a>
                        <a class="hiddenanchor" id="topin"></a>
                        <div id="wrapper">

                            <div id="login" class="animate form">
                                <form action="AdminController" method="post">
                                    <h1>Login</h1>
                                    <p>
                                        <label for="username" class="uname">Username or Email </label>
                                        <input id="username" name="username" maxlength="35" required="required" type="text" placeholder="Enter the mail" />
                                    </p>
                                    <p>
                                        <label for="password" class="youpasswd">Password </label>
                                        <input class="password" name="password" maxlength="15" pattern="^(?=.*[a-z])(?=.*\d)(?=.*[#$^+=!*()@%&]).{8,15}$" required="required" type="password" placeholder="Enter a valid password" />
                                    </p>

                                    <table>
                                        <tr>
                                            <th>

                                                <p class="login button">
                                                    <input type="hidden" name="choice" value="Login">
                                                    <input class="loginbutton" type="submit" value="Login" />

                                                </p>
                                            </th>
                                            <th>
                                                <p>

                                                    <a class="Quickpin" href="access.jsp">Quick access </a>
                                                </p>

                                            </th>
                                    </table>
                                    <p class="change_link">
                                        Not a member yet ?
                                        <a href="#toregister" class="to_register">Join us</a>
                                    </p>

                                </form>
                            </div>

                            <div id="register" class="animate form">
                                <form action="AdminController" method="post">
                                    <h1> Sign up </h1>
                                    <p>
                                        <label for="usernamesignup" class="uname">Name</label>
                                        <input id="usernamesignup" name="usernamesignup" required="required" type="text" placeholder="Enter the name" value="${name}" />
                                    </p>
                                    <p>
                                        <label for="emailsignup" class="youmail">Email</label>
                                        <input id="emailsignup" name="emailsignup" value="${mail}" required="required" type="email" placeholder="Enter the mail" />
                                    </p>
                                    <p>
                                        <label for="emailsignup" class="youmail">Mobile Number</label>
                                        <input id="mobilesignup" name="mobilesignup" value="${phone}" required="required" type="text" placeholder="Enter the mobile number" pattern="[0-9]{10}" value="${phone}" maxlength="10" />
                                    </p>
                                    <p>
                                        <label for="passwordsignup" class="youpasswd">Password </label>
                                        <input class="password" name="passwordsignup" required="required" type="password" maxlength="15" pattern="^(?=.*[a-z])(?=.*\d)(?=.*[#$^+=!*()@%&]).{8,15}$" value="${password}" placeholder="Enter the password" />
                                    </p>
                                    <c:choose>
                                        <c:when test="${otp != null }">
                                            <p>
                                                <label for="passwordsignup_confirm" class="youpasswd">Otp </label>
                                                <input id="passwordsignup_confirm" name="otp" type="text" placeholder="Enter the otp" maxlength="6" />
                                            </p>

                                        </c:when>
                                    </c:choose>
                                    <table>
                                        <tr>
                                            <c:choose>
                                                <c:when test="${otp != null }">
                                                    <th>

                                                        <p class="signin button">
                                                            <input type=hidden name=mailotp value=${otp}>
                                                            <input type="submit" name="choice" value="Register" />
                                                        </p>
                                                    </th>
                                                </c:when>
                                            </c:choose>
                                            <th>
                                                <p class="signin button">
                                                    <input type="submit" name="choice" value="Get Otp" />
                                                </p>
                                            </th>
                                        </tr>
                                    </table>
                                    <p class="change_link">
                                        Already a member ?
                                        <a href="#tologin" class="to_register"> Go and log in </a>
                                    </p>
                                </form>
                            </div>

                        </div>
                    </div>
                </section>
                </div>
            </body>

            <c:choose>
                <c:when test="${errormessage != null }">
                    <script>
                        alert("${errormessage}");
                    </script>
                </c:when>
            </c:choose>

            </html>
