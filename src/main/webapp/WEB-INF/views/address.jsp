<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="format.css"/>
   </head>
   <body>
      <jsp:include page="header.jsp" >
         <jsp:param name="attribute" value="ProjectController" />
      </jsp:include>
 <br>
      <center> Address Information </center> <br> <br>

<c:choose>
                        <c:when test = "${employee.name != null}">

      <form:form action = "submitEmployee" commandName = "employee" method ="post">
<c:forEach items="${employee.addresses}"  var = "address" varStatus="vs">






<c:if test = "${address.type == 'permanent'}">
                  <table class = "permanentTable">


                     <tr>
                        <td align='center'>
<b>Address LINE</b>
                       <form:input path="addresses[${vs.index}].type" type="hidden" />
   
                        </td>
                        <td> 
<input  name="name" type="hidden" value="${employee.name}"/> 
	<input  name="designation" type="hidden" value="${employee.designation}"/> 
	<input  name="mailId" type="hidden" value="${employee.mailId}"/> 
	<input  name="mobileNumber" type="hidden" value="${employee.mobileNumber}"/> 
	<input  name="dob" type="hidden" value="<fmt:formatDate pattern = "yyyy-MM-dd" 
         value = "${employee.dob}" />"/> 
<form:textarea path="addresses[${vs.index}].firstAddressline" maxlength = "60" 
                          name = "perAddressLine1"  placeholder="Address" required="required"  rows="5" cols="30" pattern="[A-Za-z]*"
  id="perAddressLine1"/>

                        </td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>city </h4>
                        </td>
                        <td> <form:input path="addresses[${vs.index}].city" maxlength = "15"
                              placeholder="City" required="required" name = "perCity" pattern="[A-Za-z]*" id="perCity" /><br/></td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>state </h4>
                        </td>
                        <td><form:input path="addresses[${vs.index}].state" maxlength = "15"
                              placeholder="State" required="required" name = "perState" pattern="[A-Za-z]*" id="perState" /><br /></td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>pincode </h4>
                        </td>
                        <td><form:input path="addresses[${vs.index}].pincode"
                              placeholder="Pincode" required="required"  name = "perPincode"  id="perPincode" pattern="[0-9]{6}" 
maxlength = "6"/><br /></td>

                     </tr>
                     <tr>
                        <td>
                        </td>
                        <td>
                           <input type="checkbox" class = "copyAddress" id ="lifecheck" name="vehicle" onclick = "exefunction()" value="Bike"> Copy address to paste
                        </td>
                     </tr>

                  </table>
</c:if> 




<c:if test = "${address.type == 'temporary'}">
                  <table class = "temporaryTable">

                       <form:input path="addresses[${vs.index}].type" type="hidden" />

                     <tr>
                        <td align='center'>
<b>Address LINE1</b>
                          
                        </td>
                        <td> 
<form:textarea path="addresses[${vs.index}].firstAddressline" maxlength = "60"
         id="tempAddressLine1"             name = "tempAddressLine1"   placeholder="Address" required="required" pattern="[A-Za-z]*" rows="5" cols="30"/><br />

                        </td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>city </h4>
                        </td>
                        <td> <form:input path="addresses[${vs.index}].city" maxlength = "16"
                id="tempCity"           name = "tempCity"   pattern="[A-Za-z]*"  placeholder="City" required="required"/><br /></td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>state </h4>
                        </td>
                        <td><form:input path="addresses[${vs.index}].state" maxlength = "16"
                     id="tempState"        name = "tempState"   pattern="[A-Za-z]*"    placeholder="State" required="required"/><br /></td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>pincode </h4>
                        </td>
                        <td><form:input path="addresses[${vs.index}].pincode" maxlength = "6"
                 id="tempPincode"         name = "tempPincode" pattern="[0-9]{6}" placeholder="Pincode" required="required"/><br /></td>
                     </tr>
                  

                  </table>
</c:if> 






</c:forEach>
         <table>
            <tr>
               <th>



<form:button class = "submitButton" > submit</form:button>
               </th>
               <th>
                  <input class = "submitButton" type="reset" value="Reset"  onclick = "return  confirm('Do you want to reset this address detail ?');">
               </th>
            </tr>
         </table>

</form:form>


 </c:when> 







                        <c:when test = "${client.name != null}">

      <form:form action = "submitClient" commandName = "client" method ="post">
<c:forEach items="${client.addresses}"  var = "address" varStatus="vs">






<c:if test = "${address.type == 'permanent'}">
                  <table class = "permanentTable">


                     <tr>
                        <td align='center'>
<b>Address LINE1</b>
                       <form:input path="addresses[${vs.index}].type" type="hidden" />
   
                        </td>
                        <td> 
<input  name="name" type="hidden" value="${client.name}"/> 
	<input  name="requirements" type="hidden" value="${client.requirements}"/> 
	<input  name="mailId" type="hidden" value="${client.mailId}"/> 
	<input  name="mobileNumber" type="hidden" value="${client.mobileNumber}"/> 

<form:textarea path="addresses[${vs.index}].firstAddressline" maxlength = "60"
                          name = "perAddressLine1"  placeholder="Address" required="required"  rows="5" cols="30" pattern="[A-Za-z]*"/>

                        </td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>city </h4>
                        </td>
                        <td> <form:input path="addresses[${vs.index}].city" maxlength = "15"
                              placeholder="City" required="required" name = "perCity" pattern="[A-Za-z]*"/><br/></td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>state </h4>
                        </td>
                        <td><form:input path="addresses[${vs.index}].state" maxlength = "15"
                              placeholder="State" required="required" name = "perState" pattern="[A-Za-z]*"/><br /></td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>pincode </h4>
                        </td>
                        <td><form:input path="addresses[${vs.index}].pincode"
                              placeholder="Pincode" required="required"  name = "perPincode" pattern="[0-9]{6}" 
maxlength = "6"/><br /></td>

                     </tr>
                     <tr>
                        <td>
                        </td>
                        <td>
                           <input type="checkbox" class = "copyAddress" id ="lifecheck" name="vehicle" onclick = "exefunction()" value="Bike"> Copy address to paste
                        </td>
                     </tr>

                  </table>
</c:if> 




<c:if test = "${address.type == 'temporary'}">
                  <table class = "temporaryTable">

                       <form:input path="addresses[${vs.index}].type" type="hidden" />

                     <tr>
                        <td align='center'>
<b>Address LINE1</b>
                          
                        </td>
                        <td> 
<form:textarea path="addresses[${vs.index}].firstAddressline" maxlength = "60"
                     name = "tempAddressLine1"   placeholder="Address" required="required" pattern="[A-Za-z]*" rows="5" cols="30"/><br />

                        </td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>city </h4>
                        </td>
                        <td> <form:input path="addresses[${vs.index}].city" maxlength = "16"
                          name = "tempCity"   pattern="[A-Za-z]*"  placeholder="City" required="required"/><br /></td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>state </h4>
                        </td>
                        <td><form:input path="addresses[${vs.index}].state" maxlength = "16"
                           name = "tempState"   pattern="[A-Za-z]*"    placeholder="State" required="required"/><br /></td>
                     </tr>
                     <tr>
                        <td align='center'>
                           <h4>pincode </h4>
                        </td>
                        <td><form:input path="addresses[${vs.index}].pincode" maxlength = "6"
                         name = "tempPincode" pattern="[0-9]{6}" placeholder="Pincode" required="required"/><br /></td>
                     </tr>
                  

                  </table>
</c:if> 






</c:forEach>
         <table>
            <tr>
               <th>



<form:button class = "submitButton" > submit</form:button>
               </th>
               <th>
                  <input class = "submitButton" type="reset" value="Reset"  onclick = "return  confirm('Do you want to reset this address detail ?');">
               </th>
            </tr>
         </table>

</form:form>


 </c:when>
</c:choose>

   </body>
   <%@ include file="/js/Common.js" %>
   <%@ include file="footer.html" %>
</html>
