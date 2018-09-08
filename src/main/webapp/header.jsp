<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="format.css"/>
   <body>
      <form id = "employeeform" method = "post" action = "employee">
      </form>
 <form id = "projectform" method = "post" action = "project">
      </form>
<form id = "clientform" method = "post" action = "client">
      </form>
         <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href="home.jsp">Home</a>
            <input type="hidden" name="choice" value="Employee">
            <a onclick="document.getElementById('employeeform').submit();">Employee</a>
            <a onclick="document.getElementById('projectform').submit();">Project</a>
            <a onclick="document.getElementById('clientform').submit();">Client</a>
         </div>

      <form id = "logout" method = "post" action = "AdminController">
         <input type="hidden" name="choice" value="Logout">
      </form>
      <span id="id01" class="modal">
         <form class="modal-content animate" method = "post" action = "${param.attribute}">
            <input type=hidden name = search value= search>
            <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close ">&times;</span>
            <div class="container">
               <label for="uname"><b>Please enter the id </b></label>
               <input type="text" placeholder="Enter your id" name="id" required pattern =[0-9]*>
               <label><b>Please enter the status</b></label>
               <input type="radio" name="status" value="active" required> Active
               <input type="radio" name="status"  value="inactive" required> Inactive
               <input class = "submit" type="submit"   name = "Search" value = "Search"/>
            </div>
         </form>
      </span>
      <span id="id02" class="modal">
 <form class="modal-content animate" method = "post" action = "${param.attribute}">

            <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close ">&times;</span>
            <div class="container">
               <label><b>Please enter the status</b></label>
               <input type="radio" name="status" value="active" required> Active
               <input type="radio" name="status"  value="inactive" required> Inactive
            <input type=hidden name = display value= display>
               <input class = "submit" type="submit"   name = "choice" value = "Display"/>
            </div>
         </form>
      </span>
     
      <form id = "myform" method = "post" action = "${param.attribute}">
         <div class="topnav" id="myTopnav">
            <a class="active">
            <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;</span>
            </a> 
            <input type="hidden" name="choice" value="Create">
            <a onclick="document.getElementById('myform').submit();">Create</a>
            <input type="hidden" name="choice" value="Display">
            <a onclick="document.getElementById('id02').style.display='block'" title="Display all" style="width:auto;" >Display all</a>
            <a onclick="document.getElementById('id01').style.display='block'" title="Search" style="width:auto;" >Search</a>
            <a class = "logout"onclick="document.getElementById('logout').submit();">Logout</a>
      </form>
      </div>
      </form>
      <%@ include file="/js/Common.js" %>
   </body>
</html>
