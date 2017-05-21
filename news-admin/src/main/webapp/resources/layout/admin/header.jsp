<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/header.css"/>"/>



<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>

<table>

<tr>

    <td>
<div class="title">
    <spring:message code="header.title"/>
</div>
    </td>

    <td>
<div class="logout">
<c:url value="/j_spring_security_logout" var="logoutUrl" />
    <form action="${logoutUrl}" method="post" id="logoutForm">
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
    </form>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>
        <spring:message code="header.hello"/>  ${pageContext.request.userPrincipal.name}
        <button onclick="formSubmit()"><spring:message code="header.logout"/></button>
    </h2>
</c:if>
</div>
    </td>
</tr>
    <tr>
        <td colspan="2">
            <div class="language">
                <c:choose>
                    <c:when test="${empty param['id']}">
                        <a href="?lang=en">EN</a>
                        <a href="?lang=ru">RU</a>
                    </c:when>
                    <c:otherwise>
                        <a href="?id=${param['id']}&lang=en">EN</a>
                        <a href="?id=${param['id']}&lang=ru">RU</a>
                    </c:otherwise>
                </c:choose>

            </div>
        </td>
    </tr>
</table>

