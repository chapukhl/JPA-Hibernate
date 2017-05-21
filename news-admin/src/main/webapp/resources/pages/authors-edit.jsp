<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tiles:insertDefinition name="adminTemplate">
    <tiles:putAttribute name="body">

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/author-edit.css"/>"/>
        <script
                src="<c:url value="/resources/js/author_tag_edit.js" />"></script>

        <table>
            <c:forEach items="${authors}" var="currentAuthor">
                <tr>
                    <td>
                        <div class="editAuthor">


                            <sf:form method="POST" id="${-currentAuthor.authorId}" modelAttribute="author" action="update_author">

                            <label for="${currentAuthor.authorId}"> <b><spring:message code="label.author"/>:</b></label>
                            <sf:hidden path="authorId" value="${currentAuthor.authorId}" />
                            <jsp:setProperty name="author" property="name" value="${currentAuthor.name}"/>
                            <sf:input path="name"  name="athorName" id="${currentAuthor.authorId}"  required="required" disabled="true" />
                                <a href="#" id="edit${currentAuthor.authorId}" onclick="changeAccess(${currentAuthor.authorId})"><spring:message code="label.edit"/></a>
                            <a href="javascript:document.getElementById(${-currentAuthor.authorId}).submit();" id="update${currentAuthor.authorId}" style="display: none"><spring:message code="label.update"/></a>
                            <s:url value="delete_author?id=${currentAuthor.authorId}"
                                   var="authorDelete_url" >
                            </s:url>
                            <a href="${authorDelete_url}" id="delete${currentAuthor.authorId}" style="display: none"><spring:message code="label.expire"/>
                                <a href="#" id="cancel${currentAuthor.authorId}" style="display: none"  onclick="changeAccess(${currentAuthor.authorId})"><spring:message code="label.cancel"/>
                                </sf:form>




                        </div>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td>
                    <sf:form method="POST" id="saveAuthorForm" modelAttribute="newAuthor" action="save_author">
                        <div class="saveAuthor">
                            <label for="newAuthor"> <b><spring:message code="label.addAuthor"/>:</b></label>
                            <sf:input path="name"  name="saveAuthor" id="newAuthor"/>
                            <a href="javascript:document.getElementById('saveAuthorForm').submit();" ><spring:message code="label.save"/></a>
                        </div>
                    </sf:form>
                </td>
            </tr>
        </table>



    </tiles:putAttribute>
</tiles:insertDefinition>