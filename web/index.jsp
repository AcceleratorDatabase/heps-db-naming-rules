<%-- 
    Document   : query
    Created on : Sep 9, 2019, 9:57:35 AM
    Author     : BaiYu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>高能同步辐射光源-设备命名规则数据库</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/icon.css">
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.min.js"></script>
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
        <script type="text/javascript">
            function jump1() {
                url = "upload.jsp";
                location.href = url;
            }
            function jump2() {
                url = "querydevice.jsp";
                location.href = url;
            }


        </script>
        <style>
            .button_giant{
                border-radius: 4px;
                background-color:  #E0ECFF;
                border-color: #95B8E7;
                color: #000; }
        </style>
    </head>

    <body>
        <div style="margin-top:60px; font-size: 50px; text-align:center" >
            <h4 align="center" style="color:teal">高能同步辐射光源-设备命名规则数据库</h4>
            <div style="margin:100px 0;"></div>
            <button type="button" class="button_giant" style="margin-right: 50px; font-size: 24px;width: 150px;height: 100px" onclick="jump1()">名称录入</button>
            <button type="button" class="button_giant" style="font-size: 24px;width: 150px;height: 100px" onclick="jump2()">名称查询</button>     
        </div>
    </body>
</html>