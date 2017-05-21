<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
<%@page session="true"%>
<html>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login-form.css"/>"/>
<body onload='document.loginForm.username.focus();'>


<div id="login-box">


    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <form name='loginForm'
          action="<c:url value='/j_spring_security_check'/>" method='POST'>
        <div class="form">
        <table>
            <tr>
                <td>Username: <input type='text' name='username'></td>

            </tr>
            <tr>
                <td>Password: <input type='password' name='password' /></td>

            </tr>
            <tr>
                <td colspan="2" align="center"><input class="submit" name="submit" type="submit"
                                       value="Submit" /></td>
            </tr>
        </table>
        </div>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
    </form>
</div>

</body>
</html>
    </tiles:putAttribute>
</tiles:insertDefinition>