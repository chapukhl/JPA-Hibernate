<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="bean" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/news-list.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/pagination.css"/>"/>
<link rel="stylesheet" type="text/css"
      href="<c:url value="/resources/css/dropdown.css" />">
<script
        src="<c:url value="/resources/js/dropdown.js" />"
        type="text/javascript"></script>
<script src="<c:url value="/resources/jquery-ui-1.11.4.custom/external/jquery/jquery.js"/>"></script>
<script src="<c:url value="/resources/jquery-ui-1.11.4.custom/jquery-ui.min.js"/>"></script>
<script>

    $(document).click(function(event) {

        if ($(event.target).closest(".multiselect").length)
            return;
        $("#checkboxes").hide();
        event.stopPropagation();
    });
</script>
<tiles:insertDefinition name="defaultTemplate">

    <tiles:putAttribute name="body">

        <div class="body">

            <table cellpadding="10px" height="100%" width="100%" border="0">

                <tr>
                    <sf:form method="POST" action="filter">
                        <td colspan="20" align="center">

                            <div style="float: left; margin-left: 310px;padding-bottom: 10px" class="multiselect">
                                <select id="author-select" name="author">
                                <option value="${null}" selected><spring:message code="dropdown.author"/></option>
                                <c:forEach var="author" items="${authorList}">
                                    <c:choose>
                                        <c:when test="${selectedAuthor eq author.authorId}">

                                                <option value="${author.authorId}" selected>${author.name}</option>

                                        </c:when>
                                        <c:otherwise>
                                            <option value="${author.authorId}">${author.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>

                            </div>

                                <div class="multiselect" style="float: right; margin-right: 310px; padding-bottom: 10px;">
                                    <div class="selectBox" onclick="showCheckboxes()">
                                        <select>
                                            <option><spring:message code="dropdown.tags"/></option>
                                        </select>
                                        <div class="overSelect"></div>
                                    </div>
                                    <div id="checkboxes">
                                        <c:set var="i" value="0"/>
                                        <c:forEach var="tag" items="${tagList}">
                                            <c:choose>
                                            <c:when test="${selectedTags[i] eq tag.tagId}">
                                            <c:set var="i" value="${i+1}"/>
                                                <label for="${tag.tagId}"><input type="checkbox"  value="${tag.tagId}" name="tag" checked/>${tag.tagName}</label>
                                            </c:when>
                                            <c:otherwise>
                                                <label for="${tag.tagId}"><input type="checkbox"  value="${tag.tagId}" name="tag"/>${tag.tagName}</label>
                                            </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                    </div>
                                </div>


                            <p>
                                <input type="submit" value="<spring:message code="button.filter"/>">
                                <input type="button" onclick="location.href = '/news-list';
                                " value="<spring:message code="button.reset"/>">
                            </p>


                        </td>
                    </sf:form>
                </tr>

                <c:forEach var="news" items="${newsList}">

                    <tr>
                        <td><b><c:out value="${news.title}"/></b> (By <c:forEach var="author" items="${news.authors}"><c:out value="${author.name}"/> </c:forEach>)
                            <div class="label">

                            </div>
                        </td>

                        <td colspan="8" class="settings" align="right">
                            <c:set var="locale" value="${pageContext.response.locale }" />
                            <c:if test="${locale == 'ru'}">
                                <h class="date"><fmt:formatDate value="${news.modificationDate}"
                                                                pattern="dd.MM.yyyy" /> </h>

                            </c:if>
                            <c:if test="${locale == 'en'}">
                                <h class="date"><fmt:formatDate value="${news.modificationDate}"
                                                                pattern="MM/dd/yyyy" /> </h>

                            </c:if>
                        </td>

                    </tr>

                    <tr>
                        <td><c:out value="${news.shortText}"/></td>
                    </tr>

                    <tr>
                        <td colspan="8" class="tag" align="right">
                            <c:set value="${fn:length(news.comments)}" var="commentSize"/>
                            <c:forEach var="tag" items="${news.tags}" varStatus="status">
                                <c:out value="${tag.tagName}"/>
                                <c:if test="${status.count<commentSize}">,</c:if>
                            </c:forEach>
                            <h class="comment">Comments(${commentSize})</h>

                            <s:url value="/news-detail?id=${news.newsId}"
                                   var="newsDetail_url" >
                            </s:url>
                            <a href="${newsDetail_url}"><spring:message code="label.view"/>
                        </td>
                    </tr>
                </c:forEach>

                <tr>
                    <td align="center" colspan="20">

                        <div class="pagination">
                            <c:forEach  begin="1" end="${pagesNumber}" var="i">
                                <c:choose>
                                    <c:when test="${filter}">
                                        <a href="/filter?pageNumber=${i-1}" class="page gradient">${i}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/news-list?pageNumber=${i-1}" class="page gradient">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>


                        </div>
                    </td>
                </tr>
            </table>


        </div>


    </tiles:putAttribute>
</tiles:insertDefinition>