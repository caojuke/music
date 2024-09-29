<%--
  Created by IntelliJ IDEA.
  User: XIAcaoju
  Date: 2022/3/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="false" %>
<html>
<head>
    <title>Title</title>
    <style>
        body {
            background-color: #85FFBD;
            background-image: linear-gradient(45deg, #85FFBD 0%, #FFFB7D 100%);
        }
    </style>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico" type="image/x-icon" />
    <link href="${pageContext.request.contextPath}/css/sty.css" rel="stylesheet"/>
    <script src = ${pageContext.request.contextPath}/js/jquery.min.js></script>
</head>
<body>


${pageContext.setAttribute("param01","value is this")}<br>
<img src="${pageContext.request.contextPath}/assets/logo.PNG">
<form id="form" action="/music/registerUser" method="get">

    <hr>
    <table>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="username" value="caojuke"></td>
        </tr>
        <tr>
            <td>密 码</td>
            <td><input type="password" name="password" value="778899"></td>
        </tr>
        <tr>
            <td>出生日期</td>
            <td><input type="text" name="birthdate" value="19861016"></td>
        </tr>
        <tr>
            <td>爱好</td>
            <td><p>
                <input type="checkbox" name="hobby" value="1" checked="checked">篮球
                <input type="checkbox" name="hobby" value="2">足球
                <input type="checkbox" name="hobby" value="3" checked="checked">乒乓球
            </p>
            </td>
        </tr>
        <tr>
            <td>性别</td>
            <td>
                <p>
                    <input type="radio" name="gender" value="0" checked="checked">男
                    <input type="radio" name="gender" value="1">女
                </p>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
<button id="loadDataBtn">提交</button>
<div id="content"></div>
<script>
    document.getElementById('loadDataBtn').addEventListener('click', function() {
        let data = JSON.stringify({ name: "JohnDoe"}); // 发送json格式数据
        $.ajax({
            type:"post",
            url:'http://localhost:8080/music/xhr',
            async:true,//默认为true，不用显式配置
            timeout: 0,//要求为Number类型的参数，设置请求超时时间（毫秒）。此设置将覆盖$.ajaxSetup()方法的全局设置
            //dataType:"json",
            /*data:'{"name":"zs","password":"123"}',*/
            data:data,
            contentType:"application/json",
            success: function(backendData) {
                // Update the content with the response
                alert("成功！")
                console.log("Response:", backendData);
                document.getElementById('content').innerHTML = JSON.stringify(backendData);
            },
            error:function(XMLHttpRequest,textStatus,errorThrown){
                /*
                * 请求失败时调用的函数,可选参数有
                * XMLHttpRequest对象
                * 错误信息
                * 捕获的异常对象
                * */
                alert("失败！")
                console.log(textStatus);
            }

        })
    });
</script>
</body>
</html>
