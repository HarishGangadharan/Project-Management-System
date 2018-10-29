<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="format.css"/>
   <body>
      <jsp:include page="header.jsp" >
         <jsp:param name="attribute" value="project" />
      </jsp:include>
      <center> Project Information </center>

      <form:form action = "projectOperation" id = "projectDetails" commandName = "project" method ="post">
         <table align = 'center'>
            <tr>
               <td align='center'> <img src="resources/images/account.png"/></td>
               <td> <form:input path="name" pattern="[A-Za-z]*"  placeholder= "Name " maxlength= "20" required = "required"  title="Enter a valid Name "/></td>
            </tr>
            <tr>
               <td align='center'>  <img src="resources/images/hello.svg"></td>
                 <td> <form:input path="description" pattern="[A-Za-z]*"  placeholder= "Description " maxlength= "20" required = "required"  title="Enter a valid description "/></td>
            </tr>
            <tr>
               <td align='center'>  <img src="resources/images/projectDate.png"></td>
                   <td>  
<spring:bind path="project.startDate">
                                <input type="date" class = "date" value="${project.startDate}"  name="startDate" required "/>
                                </spring:bind>
                   </td>  
            </tr>
            <c:choose>
               <c:when test = "${project.name != null}">
                  <tr>
                     <td align='center'>  <img src="resources/images/id.png"></td>
                              <td>  <form:input path="id"  readonly="true"/></td>
               </c:when>
            </c:choose>
         </table>
         <div>
            <c:choose>
               <c:when test = "${project.name == null}">
                  <table>
                     <tr>
                        <th>
<input type="submit" name="submit" class="submitButton" value="Submit" />
                        </th>
                        <th>
                           <input class = "submitButton" type="reset" value="Reset"  onclick = "return  confirm('Do you want to reset this project detail ?');">
                        </th>
                     </tr>
                  </table>
               </c:when>
               <c:when test = "${project.name != null}">
                  <div>
                     <c:if test="${(project.status == 'active')}">
                        <table>
                           <tr>
                              <th>

    <input type="submit" name="Update" class="employeeButton" value="Update" onclick = "return  confirm('Do you want to update this project ?');"  />
      </th>
      <th>

      <input type="submit" name="Delete" class="employeeButton" value="Delete" onclick = "return  confirm('Do you want to delete this project ?');"  />
      </th>
      <th>
                           <input type=hidden name = projectId value= "${project.id}">
            <input type=hidden name = status value= "${project.status}">
      <input class = "addProject" type="submit"  name = "AddEmployee" value = "AddEmployee"/>

      </th>
      <th>
      </th>
      </tr>
      </table>
      </div>
      </c:if>
      <c:if test="${(project.status == 'inactive')}">
      <input type="submit" name="Restore" class="submitRestore" value="Restore" onclick = "return  confirm('Do you want to restore this project ?');"  />
  <input type = "submit" class = "submitRestore"   name = "Cancel"  onclick = "return  confirm('Do you want to cancel?');" value = "Cancel"/>
      </c:if>
      </c:when>
      </c:choose>
      </div>
</form:form>
      </center>
   </body>
</html>
