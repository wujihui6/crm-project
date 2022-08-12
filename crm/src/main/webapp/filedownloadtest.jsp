<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#filedownloadbtn").click(function () {
                window.location.href='workbench/activity/filedownload.do';
            })
        })
    </script>

</head>
<body>
    <input type="button" id="filedownloadbtn" value="下载">
</body>
</html>
