<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    <div style="padding:16px;background-color:darkgray;"/>
        <div style="padding:16px;background-color:white">
            <table>
                <tr>
                    <td><a href="/admin/news-list">><spring:message code="menu.newsList"/></a></td>
                </tr>
                <tr>
                    <td><a href="/admin/news">><spring:message code="menu.addNews"/></a></td>
                </tr>
                <tr>
                    <td><a href="/admin/authors">><spring:message code="menu.author"/></a></td>
                </tr>
                <tr>
                    <td><a href="/admin/tags">><spring:message code="menu.tags"/></a></td>
                </tr>
            </table>
        </div>
    </div>
