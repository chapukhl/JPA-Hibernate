<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tiles:insertDefinition name="adminTemplate">
    <tiles:putAttribute name="body">

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/news-add.css"/>"/>
        <link rel="stylesheet" type="text/css"
              href="<c:url value="/resources/css/dropdown.css" />">


        <link rel="stylesheet" href="<c:url value="/resources/jquery-ui-1.11.4.custom/jquery-ui.min.css" />">
        <script src="<c:url value="/resources/jquery-ui-1.11.4.custom/external/jquery/jquery.js"/>"></script>
        <script src="<c:url value="/resources/jquery-ui-1.11.4.custom/jquery-ui.min.js"/>"></script>
        <script type="text/javascript"
                src="/resources/js/datepicker-ru.js">
        </script>

        <script
                src="<c:url value="/resources/js/dropdown.js" />"
                type="text/javascript"></script>


        <script>
            $(function() {
                $( "#modificationDate" ).datepicker($.datepicker.regional[$("#hidden-locale").val()]);

            });

                        $(document).click(function(event) {

                            if ($(event.target).closest(".multiselect").length)
                                return;
                            $("#checkboxes").hide();
                            event.stopPropagation();
                        });

        </script>
        <c:if test="${unavailable}">
            News updating  by another transaction.
        </c:if>
        <sf:form method="POST" id="saveNewsForm" modelAttribute="News" action="update_news">
            <table>

                <tr>
                    <td colspan="20" align="center;  padding: 20px">

                        <div style="float: left; padding: 20px" >
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

                        <div class="multiselect"  style="float: right; padding: 20px">
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
                    </td>
                </tr>
                <tr>

                    <sf:hidden path="newsId"/>

                    <td align="right">
                        <div class="addNews">
                            <label for="title"> <b><spring:message code="label.title"/> </b></label>
                        </div>
                    </td>
                    <td align="left">

                        <div class="addNews">

                            <sf:textarea style="vertical-align: top"  cssClass="news_title"  path="title" id="title"/>
                        </div>
                    </td>
                </tr>

                <tr>

                    <td align="right">
                        <div class="addNews">
                            <label for="shortText"> <b><spring:message code="label.shortText"/>:</b></label>
                        </div>
                    </td>
                    <td align="left">
                        <div class="addNews">

                            <sf:textarea  style="vertical-align: top" cssClass="shortText" path="shortText" id="shortText"/>
                        </div>
                    </td>
                </tr>



                <tr>
                    <sf:hidden    id="creationDate" path="creationDate"  required="required" pattern="(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d" />
                    <td align="right">
                        <div class="addNews">
                            <label  for="modificationDate"> <b><spring:message code="label.modificationDate"/>:</b></label>
                        </div>
                    </td>

                    <td align="left">
                        <div class="addNews">
                            <c:set var="locale" value="${pageContext.response.locale }" />
                            <c:if test="${locale == 'ru'}">
                                <input type="hidden" value="ru" id="hidden-locale" />
                                <input type="hidden" value="dd.mm.yy" id="pattern-datepicker" />
                                <fmt:formatDate value="${News.modificationDate}"
                                                pattern="dd.MM.yyyy" var="newsDate" />
                                <fmt:formatDate value="${News.modificationDate}"
                                                pattern="MM/dd/yyyy" var="formatDate" />

                            </c:if>
                            <c:if test="${locale == 'en'}">
                                <input type="hidden" value="" id="hidden-locale" />
                                <input type="hidden" value="mm/dd/yy" id="pattern-datepicker" />
                                <fmt:formatDate value="${News.modificationDate}"
                                                pattern="MM/dd/yyyy" var="newsDate" />
                                <fmt:formatDate value="${News.modificationDate}"
                                                pattern="MM/dd/yyyy" var="formatDate" />
                            </c:if>

                            <%--<jsp:setProperty name="News" property="modificationDate" value="${newsDate}"/>--%>
                            <input style="vertical-align: top" type="text" id="modificationDate"  value="${newsDate}"  required="required"/>
                            <%--onchange="$('#tempDate').val($.datepicker.formatDate('yy-mm-dd',$('#modificationDate').val()))"--%>
                            <%--<input style="vertical-align: top" type="text"  id="tempDate" value="${formatDate}"  required="required" pattern="(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](((19|20)\d\d)|\d\d)"/>--%>


                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <div class="addNews">
                            <label  for="fullText"> <b><spring:message code="label.fulltext"/>:</b></label>
                        </div>
                    </td>
                    <td align="left">
                        <div class="addNews">

                                <sf:textarea style="vertical-align: top" cssClass="fullText" path="fullText" id="fullText"/>
                    </td>


                </tr>

                <tr>
                    <td colspan="2" align="center">
                        <p align="center"><input  type="submit" value="<spring:message code="button.save"/>"/></p>
                    </td>
                </tr>
            </table>

        </sf:form>



    </tiles:putAttribute>
</tiles:insertDefinition>