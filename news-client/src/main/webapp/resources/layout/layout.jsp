<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/layout.css"/>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>News Managment</title>
</head>
<body>
    <table cellspacing="2" border="2" >
        <tr  height="13%" align="left">
            <td class="contour"  colspan="2">
                <tiles:insertAttribute name="header" />
            </td>
        </tr>
        <tr height="85%">
            <%--<td   width="15%"  style="vertical-align: top;">--%>
                <%--<tiles:insertAttribute name="menu" />--%>
            <%--</td>--%>
            <td  class="inner" width="85%" style="vertical-align: top">
                <div  class="frame"> <tiles:insertAttribute name="body" /></div>
            </td>
        </tr>
        <tr height="2%">
            <td class="contour" colspan="2" align="center">
                <tiles:insertAttribute name="footer" />
            </td>
        </tr>
    </table>
</body>
</html>