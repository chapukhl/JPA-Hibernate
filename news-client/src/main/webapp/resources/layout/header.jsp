<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/header.css"/>"/>

<div class="title">
    <spring:message code="header.title.client"/>
</div>

<div class="language">
    <c:choose>
        <c:when test="${empty param}">
            <a href="?lang=en">EN</a>
            <a href="?lang=ru">RU</a>
        </c:when>
        <c:otherwise>
            <a href="?id=${param['id']}&lang=en">EN</a>
            <a href="?id=${param['id']}&lang=ru">RU</a>
        </c:otherwise>
    </c:choose>
</div>

