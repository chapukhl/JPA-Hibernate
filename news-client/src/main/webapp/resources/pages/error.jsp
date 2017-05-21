
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<tiles:insertDefinition name="adminTemplate">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/error.css"/>"/>
    <tiles:putAttribute name="body">
        <%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <div class="space">

       <table style="width: 300px;height: 300px;margin: 0 auto;border: 2px">
           <tr align="center">
               <td align="center">
        <b>Request from</b> ${pageContext.errorData.requestURI} <b>is failed</b>
               </td>
           </tr>

           <tr align="center">
               <td align="center">
                   <b>Servlet name or type:</b> ${pageContext.errorData.servletName}
           <tr align="center">

               <td align="center">
                   <b>Status code:</b> ${pageContext.errorData.statusCode}
               </td>
       </tr>
           <tr align="center">
               <td align="center">
                   <b>Exception:</b> ${pageContext.errorData.throwable}
               </td>

           </tr>
       </table>


    </tiles:putAttribute>
</tiles:insertDefinition>