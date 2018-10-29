<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 
   <head>
      <meta charset="utf-8">
      <link rel="icon" href="resources/images/employeetab.png" type="image/gif" sizes="16x16">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      <link rel="stylesheet" type="text/css" href="format.css"/>
   </head>
   <style>
            .width-50{
      
                  width:50%;
            }
            .width-10{
      
      width:10%;
}
         </style>
   <body>
      <jsp:include page="header.jsp" >
         <jsp:param name="attribute" value="employee" />
      </jsp:include>
      <center> All Employees Information </center>
      <form  action = "createFile" method ="post">
         <div>
            <input type="text" class = "width-50" placeholder="Enter your file name and file will be located in files folder" name="filename" maxlength="10" title="Enter a valid name" value= "" required>
            <input type=hidden name = status value= ${employee.status}>
            <input class = "submit" class = "width-10" type="submit"  name = "choice" value = "CreateFile"/>
            <c:choose>
               <c:when test = "${isFilePresent == true}">
                  <a href="${file}" download>
                  <img src="resources/images/file.png">
                  <img src="resources/images/pointer.gif" title ="download file">
                  </a> 
               </c:when>
            </c:choose>

&nbsp Number of Employees
<input  type = "text" value = ""  id = "length" readonly> 


         </div>
      </form>

      <table id="employees" >
         <thead>
            <tr>
               <th>Id</th>
               <th>Name</th>
               <th>Designation</th>
               <th>Email</th>
               <th>Dob</th>
               <th>Mobilenumber</th>
               <th>Status</th>
               <th>Action</th>
            </tr>
         </thead>
         <c:forEach items= "${employees}" var = "employee">
            <tr>

               <td>${employee.id}</td>
               <td>${employee.name}</td>
               <td>${employee.designation}</td>
               <td>${employee.mailId}</td>
               <td>${employee.dob}</td>
               <td>${employee.mobileNumber}</td>
               <td>${employee.status}</td>
               <td>
                  <c:choose>
                     <c:when test = "${employee.status == 'active'}">
                        <input type = "button"  class ="pop" value = "action"title = "action" data-html="true" data-toggle="popover" data-content="
                           <form  id = myform3 action = deleteEmployee method =post>   
	<input  name=id type=hidden value=${employee.id}> 
                           <a onclick= document.getElementById('myform3').submit();> <img src = resources/images/d /></a>
                           </form>
                           <form  id = myform4 action = searchEmployee method =post>
                           <input type=hidden name = id value= ${employee.id}>
                           <input type=hidden name = status value= ${employee.status}>
                           <a onclick=document.getElementById('myform4').submit();> <img src = resources/images/edit.png /></a>
                           </form>
                           </input>
                           "></input>
                     </c:when>
                     <c:when test = "${employee.status == 'inactive'}">
                        <input type = "button"  class ="pop" value = "action"title = "action" data-html="true" data-toggle="popover" data-content="
                           <form  id = myform1 action = restoreEmployee method =post>   
                           <input type=hidden name = id value= ${employee.id}>

                           <a onclick=document.getElementById('myform1').submit();> <img src = resources/images/restore.png /></a>
                           </form>
                           <form  id = myform2 action = searchEmployee method =post>
                           <input type=hidden name = id value= ${employee.id}>
                           <input type=hidden name = status value= ${employee.status}>
                           <a onclick=document.getElementById('myform2').submit();> <img src = resources/images/view.png /></a>
                           </form>
                           </input>
                           "></input>
                        </input>
                     </c:when>
                  </c:choose>
               </td>
            </tr>
         </c:forEach>

      </table>
      <c:choose>
         <c:when test = "${Deletedid != null }">
            <script>
               alert("${Deletedid} is successfully deleted");
            </script>
         </c:when>
      </c:choose>
 <c:choose>
         <c:when test = "${responseMessage != null }">
            <script>
               alert("${responseMessage}");
            </script>
         </c:when>
      </c:choose>

      <c:choose>
         <c:when test = "${restoredId!= null}">
            <script>
               alert("${restoredId} is successfully restored");
            </script>
         </c:when>
         <c:when test = "${updatedId != null }">
            <script>
               alert("${updatedId} is successfully  updated");
            </script>
         </c:when>
      </c:choose>

<script>
    var x = document.getElementById("employees").rows.length;
    document.getElementById("length").value = x-1;

</script>


      <%@ include file="/js/Common.js" %>
   </body>
</html>
