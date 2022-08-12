<%--
  Created by IntelliJ IDEA.
  User: Wujihui
  Date: 2022/8/10
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--        文件上传表单的三个条件
    1、表单组件标签必须用<input type="file">
        <input type="text|password|radio|checkbox|hidden|button|submit|reset|file">
                <select></select> <textarea></textarea> 等
    2、请求方式只能用post
        get：参数通过请求头提交到后台，参数放到url后边：只能向后台提交文本数据；对参数长度有限；数据不安全；效率高
        post:参数通过请求体提交到后台；既能提交文件数据，又能提交二进制数据；理论上对参数长度没有限制；相对安全：效率相对较低
    3、表单的编码格式只能用multipart/form-data
        根据http协议的规定，浏览器每次向后台提交参数，都会对参数进行统一编码:默认采用编码格式urlencode，这种编码格式只能对文本数
        浏览器每次向后台提交参数，都会首先把所有的参数转化成字符串，然后对这些数据统一用urlencode编码
        文本上传的表单编码格式只能用multpart/form-data：enctype="multpart/form-data"--%>

    <form action="workbench/activity/fileupload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="multipartFile"><br>
        <input type="text" name="username"><br>
        <input type="submit" value="提交">
    </form>

</body>
</html>
