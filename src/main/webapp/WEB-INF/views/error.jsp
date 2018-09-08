<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <link rel="icon" href="resources/images/account.png" type="image/gif" sizes="16x16">
   </head>
   <body>
      <jsp:include page="header.jsp" />
      <center>
         <img  src="resources/images/warning.png">
         <h4>${error}</h4>
         <img src="resources/images/tenor.gif"/>
      </center>
   </body>
</html>
