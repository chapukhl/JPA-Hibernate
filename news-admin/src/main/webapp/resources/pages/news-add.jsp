<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<tiles:insertDefinition name="adminTemplate">
    <tiles:putAttribute name="body">

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/news-add.css"/>"/>
        <link rel="stylesheet" type="text/css"
              href="<c:url value="/resources/css/dropdown.css" />">

        <link rel="stylesheet" href="<c:url value="/resources/jquery-ui-1.11.4.custom/jquery-ui.min.css" />">
        <script src="<c:url value="/resources/jquery-ui-1.11.4.custom/external/jquery/jquery.js"/>"></script>
        <script src="<c:url value="/resources/jquery-ui-1.11.4.custom/jquery-ui.min.js"/>"></script>
        <script
                src="<c:url value="/resources/js/dropdown.js" />"
                type="text/javascript"></script>
        <script type="text/javascript"
                src="/resources/js/datepicker-ru.js">
        </script>


        <script>

                $(function() {
                    val = $("#pattern-datepicker").val();
                    $("#datepicker").datepicker(
                            $.datepicker.regional[$("#hidden-locale").val()]).attr(
                            'required', 'required').datepicker("option", "dateFormat", val);
                    $("#datepicker").keydown(function(event) {
                        event.preventDefault();
                    });
                });


        </script>
        <sf:form method="POST" id="saveNewsForm" modelAttribute="News" action="save_news">
        <table>

            <tr>

                <td colspan="20" align="center;  padding: 20px">

                    <div style="float: left; padding: 20px">
                        <select id="author-select" name="author" required="required">
                            <option value="${null}" disabled="disabled" selected><spring:message code="dropdown.author"/></option>
                            <c:forEach var="author" items="${authorList}">

                                <option value="${author.authorId}">${author.name}</option>

                            </c:forEach>
                        </select>

                    </div>

                    <div  class="multiselect" style="float: right; padding: 20px">
                        <div class="selectBox" onclick="showCheckboxes()">
                            <select>
                                <option><spring:message code="dropdown.tags"/></option>
                            </select>
                            <div class="overSelect"></div>
                        </div>
                        <div id="checkboxes">
                            <c:forEach var="tag" items="${tagList}">
                                <label for="${tag.tagId}"><input type="checkbox"  value="${tag.tagId}" name="tag"/>${tag.tagName}</label>
                            </c:forEach>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <div class="addNews">
                        <label for="title"> <b><spring:message code="label.title"/>: </b></label>
                    </div>
                </td>
                <td align="left">

                        <div class="addNews">

                            <sf:textarea style="vertical-align: top"  cssClass="news_title"  path="title" id="title" required="required"/>
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
                 <sf:textarea  style="vertical-align: top" cssClass="shortText" path="shortText" id="shortText" required="required"/>
                </div>
            </td>
            </tr>
            <c:set var="locale" value="${pageContext.response.locale}" />

            <tr>
                <td align="right">
                    <div class="addNews">
                <label  for="creationDate"> <b><spring:message code="label.creationDate"/>:</b></label>
                    </div>
                </td>
                <td align="left">
                    <div class="addNews">

                            <c:set var="locale" value="${pageContext.response.locale }" />
                            <c:if test="${locale == 'ru'}">
                                <input type="hidden" value="ru" id="hidden-locale" />
                                <input type="hidden" value="dd.mm.yy" id="pattern-datepicker" />
                                <fmt:formatDate value="${News.creationDate}"
                                                pattern="dd.MM.yyyy" var="newsDate" />
                            </c:if>
                            <c:if test="${locale == 'en'}">
                                <input type="hidden" value="" id="hidden-locale" />
                                <input type="hidden" value="mm/dd/yy" id="pattern-datepicker" />
                                <fmt:formatDate value="${News.creationDate}"
                                                pattern="MM/dd/yyyy" var="newsDate" />
                            </c:if>
                            <jsp:setProperty name="News" property="creationDate" value="${newsDate}"/>
                            <sf:input path="creationDate" style="vertical-align: top" type="text" id="datepicker" required="required"/>
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

                            <sf:textarea style="vertical-align: top" cssClass="fullText" path="fullText" id="fullText" required="required"/>
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