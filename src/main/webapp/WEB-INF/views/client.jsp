<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <link rel="icon" href="images/employeetab.png" type="image/gif" sizes="16x16">
      <link rel="stylesheet" type="text/css" href="format.css"/>
   <body>
      <jsp:include page="header.jsp" >
         <jsp:param name="attribute" value="client" />
      </jsp:include>
      <center> Client Information </center>
      
      <form:form action = "clientOperation" commandName = "client" method ="post">


        
                  <table align= "center">

                     <c:choose>
                        <c:when test = "${client.name != null}">
                           <tr>
                              <td align='center'>  <img src="resources/images/id.png"></td>
                              <td>  <form:input path="id"  readonly="true"/></td>
                           </tr>
                          
                        </c:when>
                     </c:choose>

                     <tr>
                        <td align='center'> <img src="resources/images/account.png"/></td>
                        <td> <form:input path="name" pattern="[A-Za-z]*"  placeholder= "Name " maxlength= "20" required = "required"  title="Enter a valid Name "/></td>
                     </tr>
                     <tr>
                        <td align='center'>  <img src="resources/images/email.png"></td>
                        <td>  
                           <form:input path="mailId" class ="email"  placeholder="Email " pattern="[a-z0-9._]+@[a-z.]+" title="Enter a valid Email address" maxlength="20" required = "required" />
                        </td>
                     </tr>
                     <tr>
                        <td align='center'>  <img src="resources/images/phone.png"></td>
                        <td>  <form:input path="mobileNumber" placeholder="Mobile number" pattern="[0-9]{10}" title="Enter a valid mobile number" maxlength="10"  required = "required" /></td>
                     </tr>
                   
                     <tr>
                        <td align='center'>  <img src="resources/images/you.svg"></td>
                        <td> <form:input path="requirements" placeholder="Requirements"  pattern="[A-Za-z]*" title="Enter a valid requirements"  maxlength="20" required = "required"  /></td>
                     </tr>

                  </table>
            


<c:forEach items="${client.addresses}"  var = "address" varStatus="vs">

<c:if test = "${address.type == 'temporary'}">
<h2>Temporary Address </h2>
                  <table align= "center">
<tr>
<td>
Address
<td>
<td>
                       <form:input path="addresses[${vs.index}].type" type="hidden" />
<form:textarea path="addresses[${vs.index}].firstAddressline"
                          name = "perAddressLine1"    placeholder="Address Line1" required="required"  rows="5" cols="30"/><br />
<td>
<tr>

<tr>
<td>
city
<td>
<td>
<form:input path="addresses[${vs.index}].city"
                              placeholder="Pincode" required="required"  name = "perPincode"/>
<td>
<tr>

<tr>
<td>
state
<td>
<td>
<form:input path="addresses[${vs.index}].state"
                              placeholder="Pincode" required="required"  name = "perPincode"/>
<td>
<tr>


<tr>
<td>
pincode
<td>
<td>
<form:input path="addresses[${vs.index}].pincode"
                              placeholder="Pincode" required="required"  name = "perPincode"/>
<td>
<tr>

</table>
</c:if>


<c:if test = "${address.type == 'permanent'}">
<h2>Permanent Address</h2>

                  <table align= "center">
<tr>
<td>
Address
<td>
<td>
                       <form:input path="addresses[${vs.index}].type" type="hidden" />
<form:textarea path="addresses[${vs.index}].firstAddressline"
                          name = "perAddressLine1"    placeholder="Address Line1" required="required"  rows="5" cols="30"/><br />
<td>
<tr>

<tr>
<td>
city
<td>
<td>
<form:input path="addresses[${vs.index}].city"
                              placeholder="Pincode" required="required"  name = "perPincode"/>
<td>
<tr>

<tr>
<td>
state
<td>
<td>
<form:input path="addresses[${vs.index}].state"
                              placeholder="Pincode" required="required"  name = "perPincode"/>
<td>
<tr>


<tr>
<td>
pincode
<td>
<td>
<form:input path="addresses[${vs.index}].pincode"
                              placeholder="Pincode" required="required"  name = "perPincode"/>
<td>
<tr>

</table>
</c:if>

</c:forEach>

   <div>
            <c:choose>
               <c:when test = "${client.name == null}">
                  <table>
                     <tr>
                        <th>

<input type="submit" name="addAddress" class="submitButton" value="Add address" />





                        </th>

                        <th>
                             <input type= "reset" class = "submitButton" value="Reset"  onclick = "return  confirm('Do you want to reset this client detail ?');"/>
                        </th>
                     </tr>
                  </table>
               </c:when>
               <c:when test = "${client.name != null}">
                  <c:if test="${(client.status == 'active')}">
                     <table>
                        <tr>
                           <th>

<input type="submit" name="Update" class="employeeButton" value="Update" onclick = "return  confirm('Do you want to update this client ?');"  />


      </th>
      <th>


        <input type= "submit" class = "employeeButton" value="Delete" name = "Delete" onclick = "return  confirm('Do you want to delete this client detail ?');"/>
      </th>
      <th>
     
                           <input type=hidden name = clientId value= "${client.id}">
            <input type=hidden name = status value= "${client.status}">
      <input class = "employeeButton" type="submit"  name = "AddProject" value = "AddProject${client.id}"/>


      </th>
      </tr>
      </table>
      </c:if>
      <c:if test="${(client.status == 'inactive')}">
    <input type="submit" name="Restore" class="submitRestore" value="Restore" onclick = "return  confirm('Do you want to restore this client ?');"  />

 <input type = "submit" class = "submitRestore"   name = "Cancel"  onclick = "return  confirm('Do you want to cancel?');" value = "Cancel"/>
      </c:if>
      </c:when>
      </c:choose>
      </div>
      </form:form>
      </center>
      <%@ include file="footer.html" %>
   </body>
<c:choose>
         <c:when test = "${response != null }">
            <script>
               alert("${response}");
            </script>
         </c:when>
      </c:choose>
</html>
