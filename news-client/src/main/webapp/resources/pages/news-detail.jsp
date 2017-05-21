<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/news-detail.css"/>"/>
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        <div class="back">
           <a href="/news-list"><spring:message code="label.back"/></a>
        </div>
<div class="table">
        <table  cellpadding="10px"  border="0">

        <tr>
            <td width="80%"><b><c:out value="${news.shortText}"/></b> (By <c:forEach var="author" items="${news.authors}"><c:out value="${author.name}"/> </c:forEach>)
            </td>

            <td align="right"><c:set var="locale" value="${pageContext.response.locale }" />
                <c:if test="${locale == 'ru'}">
                    <h class="date"><fmt:formatDate value="${news.modificationDate}"
                                                    pattern="dd.MM.yyyy" /> </h>

                </c:if>
                <c:if test="${locale == 'en'}">
                    <h class="date"><fmt:formatDate value="${news.modificationDate}"
                                                    pattern="MM/dd/yyyy" /> </h>

                </c:if></td>
        </tr>
        <tr>
            <td><c:out value="${news.fullText}"/></td>
        </tr>

        <c:forEach var="comment" items="${news.comments}">

            <tr>
            <td> <h class="date"><c:out value="${comment.creationDate}"/></h></td>
        </tr>
            <tr >
                <td class="comment" ><p><c:out value="${comment.commentText}"/></p></td>
            </tr>

        </c:forEach>


            <tr >
                <td>
                    <sf:form method="POST" modelAttribute="comment" action="/news-detail/add_comment">
                    <sf:hidden path="news.newsId" value="${news.newsId}" />
                    <p><sf:textarea path="commentText" class="area" name="comment" cols="50" rows="8" required="true"></sf:textarea></p>
                    <p align="center"><input  type="submit" value="<spring:message code="button.postComment"/>"/></p>



                </td>

            </tr>
            <tr>
                <td> <div  class="prev-select">
                    <a href="/news-detail/next_news?newsId=${news.newsId}"><spring:message code="label.prev"/></a>
                    <c:if test="${notNext}">
                        Previous news does not exist
                    </c:if>
                </div>
                </td>

                <td colspan="8" class="settings" align="right">
                    <div  class="next-select">
                        <a href="/news-detail/prev_news?newsId=${news.newsId}"><spring:message code="label.next"/></a>
                        <c:if test="${notPrev}">
                            Next news does not exist
                        </c:if>
                    </div>
                </td>

            </tr>
            </sf:form>
        </table>



</div>



</tiles:putAttribute>
</tiles:insertDefinition>