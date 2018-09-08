<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
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
    <body class = "bodyy">
        
      
            <section>				
                <div id="container_demo" >
                 
                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
 <a class="hiddenanchor" id="topin"></a>
                    <div id="wrapper">



           <div id="login" class="animate form">

         <form action = "AdminController" method ="post">
<center>
                                <h1>Login via Pin</h1> 
                                <p> 
                                    <label for="username" class="uname">Quick Access Pin </label><br>
                                   <input class="pin" name="password0"   required="required" type="password" data-max=1 oninput="skipIfMax(this)" /> 
   <input class="pin" name="password1" required="required" type="password"  data-max=1 oninput="skipIfMax(this)"   /> 
   <input class="pin" name="password2" required="required" type="password"  data-max=1 oninput="skipIfMax(this)"  /> 
   <input class="pin" name="password3" required="required" type="password" data-max=1 oninput="skipIfMax(this)"  /> 
   <input class="pin" name="password4" required="required" type="password"  data-max=1 oninput="skipIfMax(this)"  /> 
   <input class="pin" name="password5" required="required" type="password"  data-max=1 oninput="skipIfMax(this)"  /> 
   <input class="pin" name="password6" required="required" type="password" maxlength = "1"   /> 
                                </p>
                               
						

<table>
<tr>
<th>

			
                                <p class="login button"> 
            <input type="hidden" name="choice" value="Login via Pin">
                                    <input class = "loginbutton" type="submit"  value="Login" /> 
 
								</p>
</th>
<th> 
<p> 
								
									<a class= "Quickpin" href="index.jsp" >Login via Email</a>
								</p>
    
</th>
</table>     
                              
 <p class="change_link">  
									
									<a href="#toregister" class="to_register">Create PIN</a>
								</p>
 
				</center>		
                            </form>
                        </div>


                     <div id="register" class="animate form">
         <form action = "AdminController" method ="post">
                                <h1>Create PIN </h1> 
                                <p> 
                                    <label for="usernamesignup" class="uname">Admin Id</label>
                                    <input id="idsignup" name="id" maxlength = "3" required="required" type="text" placeholder="Enter the name" value = "${name}"/>
                                </p>
                                <p> 
                                    <label for="accesspinsignup" class="youmail">Access PIN</label>
                                   <input class="password" name="accesspin" maxlength = "4"  pattern="[0-9]{4}" required="required" type="password" placeholder="Enter a valid password" /> 
                                </p>
 
    <table>
<tr>

<th>


                                <p class="signin button"> 
      <input type=hidden name = mailotp value= ${otp}>
									<input type="submit" name = "choice" value="Submit"/> 
								</p>
</th>

<th>
 <p class="signin button"> 
									<input type="reset"  name="choice"  value="Reset"/> 
								</p>
</th>
</tr>
                        </table>
                                <p class="change_link">  
									Already having a pin ?
									<a href="#tologin" class="to_register"> login via pin </a>
								</p>
                            </form>
                        </div>
				



                    </div>
                </div>  
            </section>
        </div>
    </body>

<script>
function skipIfMax(element) {
  max = parseInt(element.dataset.max)
  
  
  if (element.value.length >= max && element.nextElementSibling) {
    element.nextElementSibling.focus();  
  }
}
       </script>
${otp}
  <c:choose>
         <c:when test = "${errormessage != null }">
            <script>
               alert("${errormessage}");
            </script>
         </c:when>
      </c:choose>
</html>
