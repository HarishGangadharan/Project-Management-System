<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <style>
   </style>
   <head>
      <meta charset="utf-8">
      <link rel="icon" href="resources/images/employeetab.png" type="image/gif" sizes="16x16">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      <link rel="stylesheet" type="text/css" href="format.css"/>
   </head>
   <body>
      <jsp:include page="header.jsp" >
         <jsp:param name="attribute" value="client" />
      </jsp:include>
      <center> All Clients Information </center>

      <form  action = "createClientFile" method ="post">
         <div>
            <input type="text" style = "width:50%;" placeholder="Enter your file name and file will be located in files folder" name="filename" maxlength="10" title="Enter a valid name" value= "" required>
            <input type=hidden name = status value= ${client.status}>
            <input class = "submit" style = "width:10%; "  type="submit"  name = "choice" value = "CreateFile"/>
            <c:choose>
               <c:when test = "${isFilePresent == true}">
                  <a href="${file}" download>
                  <img src="resources/images/file.png">
                  <img src="resources/images/pointer.gif" title ="download file">
                  </a> 
               </c:when>
            </c:choose>

&nbsp Number of Clients
<input  type = "text" value = ""  id = "length" readonly style = "    margin-left: 1%;
    width: 5%;"> 


         </div>
      </form>

      <table id="employees">
         <thead>
            <tr>
               <th>Id</th>
               <th>Name</th>
               <th>Requirements</th>
               <th>Email</th>
               <th>Mobilenumber</th>
               <th>Status</th>
               <th>Action</th>
            </tr>
         </thead>
         <c:forEach items= "${clients}" var = "client">
            <tr>
               </form>
               <td>${client.id}</td>
               <td>${client.name}</td>
               <td>${client.requirements}</td>
               <td>${client.mailId}</td>
               <td>${client.mobileNumber}</td>
               <td>${client.status}</td>
               <td>
                  <c:choose>
                     <c:when test = "${client.status == 'active'}">
                        <input type = "button"  class ="pop" value = "action"title = "action" data-html="true" data-toggle="popover" data-content="
                           <form  id = deleteClient action = deleteClient method =post>   
	<input  name=id type=hidden value=${client.id}> 
                           <a onclick=document.getElementById('deleteClient').submit();> <img src = resources/images/d /></a>
                           </form>
                           <form  id = myform4 action = searchClient method =post>
                           <input type=hidden name = id value= ${client.id}>
                           <input type=hidden name = status value= ${client.status}>
                           <a onclick=document.getElementById('myform4').submit();> <img src = resources/images/edit.png /></a>
                           </form>
                           </input>
                           "></input>
                     </c:when>
                     <c:when test = "${client.status == 'inactive'}">
                        <input type = "button"  class ="pop" value = "action"title = "action" data-html="true" data-toggle="popover" data-content="
                           <form  id = myform1 action = restoreClient method =post>   
                           <input type=hidden name = id value= ${client.id}>
                           <a onclick=document.getElementById('myform1').submit();> <img src = resources/images/restore.png onclick = restoreClient() /></a>
                           </form>
                           <form  id = myform2 action = searchClient method =post>
                           <input type=hidden name = id value= ${client.id}>
                           <input type=hidden name = status value= ${client.status}>

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
         </form>
      </table>
      </form>
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
      <%@ include file="/js/Common.js" %>
   </body>
<script>
    var x = document.getElementById("employees").rows.length;
    document.getElementById("length").value = x-1;

</script>
</html>
