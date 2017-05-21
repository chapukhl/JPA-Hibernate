<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tiles:insertDefinition name="adminTemplate">
<tiles:putAttribute name="body">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tag-edit.css"/>"/>
    <script
            src="<c:url value="/resources/js/author_tag_edit.js" />"></script>

<table>
    <c:forEach items="${tags}" var="currentTag">
        <tr>
            <td>
                <div class="editTag">


                    <sf:form method="POST" id="${-currentTag.tagId}" modelAttribute="tag" action="update_tag">

                   <label for="${currentTag.tagName}"> <b><spring:message code="label.tag"/>:</b></label>

                    <sf:hidden path="tagId" value="${currentTag.tagId}" />
                        <jsp:setProperty name="tag" property="tagName" value="${currentTag.tagName}"/>
                    <sf:input path="tagName"  name="tagName" id="${currentTag.tagId}"  required="true" disabled="true"/>

                        <a href="#" id="edit${currentTag.tagId}" onclick="changeAccess(${currentTag.tagId})"><spring:message code="label.edit"/></a>
                        <%--<c:set var="formId" value="${-currentTag.id}"/>--%>

                        <a href="javascript:document.getElementById(${-currentTag.tagId}).submit();" id="update${currentTag.tagId}" style="display: none"><spring:message code="label.update"/></a>
                        <s:url value="delete_tag?id=${currentTag.tagId}"
                               var="tagDelete_url" >
                        </s:url>
                        <a href="${tagDelete_url}" id="delete${currentTag.tagId}" style="display: none" ><spring:message code="label.delete"/>
                            <a href="#" id="cancel${currentTag.tagId}" style="display: none"  onclick="changeAccess(${currentTag.tagId})"><spring:message code="label.cancel"/>
                    </sf:form>



                    </div>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td>

            <sf:form method="POST" id="SaveTagForm" modelAttribute="newTag" action="save_tag">
                <div class="saveTag">
                    <label for="newTag"> <b><spring:message code="label.addTag"/>:</b></label>
                    <sf:input path="tagName"  name="saveTag" id="newTag"/>
                    <a href="javascript:document.getElementById('SaveTagForm').submit();"><spring:message code="label.save"/></a>

                </div>
            </sf:form>
        </td>
    </tr>
</table>



    </tiles:putAttribute>
</tiles:insertDefinition>