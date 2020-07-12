<%-- 
    Document   : query
    Created on : Sep 9, 2019, 9:57:35 AM
    Author     : BaiYu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/icon.css">    
        <link rel="stylesheet" type="text/css" href="mydesigncss.css">
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
        <!--<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.portal.js"></script>-->
        
        <script type="text/javascript">
            <%
                String system = (String) session.getAttribute("system");
                String subsystem = (String) session.getAttribute("subsystem");
                String location = (String) session.getAttribute("location");
                String devicename = (String) session.getAttribute("devicename");
            %>
                var dsys = "<%=system%>";
                var dsub = "<%=subsystem%>";
                var dloc = "<%=location%>";
                var ddev = "<%=devicename%>";
            window.onload = function (){
                $.ajax({
                    type: 'POST',
                    url: "LoadSystemServlet",
                    success: function (data) {
                        var d = data.split(",");
                        var x = document.getElementById("system");
                        for(var i = 0; i < d.length; i++){
                            var option = document.createElement("option");
                            option.text = d[i];
                            option.value = d[i];
                            try{
                                x.add(option, x.options[null]);
                            }catch(e){
                                x.add(option, null);
                            }
                        }
                        document.getElementById("system").value = dsys;
                    }
                });
                $.ajax({
                    type: 'POST',
                    url: "LoadSubsystemServlet",
                    success: function (data) {
                        var d = data.split(",");
                        var x = document.getElementById("subsystem");
                        for(var i = 0; i < d.length; i++){
                            var option = document.createElement("option");
                            option.text = d[i];
                            option.value = d[i];
                            try{
                                x.add(option, x.options[null]);
                            }catch(e){
                                x.add(option, null);
                            }
                        }
                        document.getElementById("subsystem").value = dsub;
                    }
                });
                $.ajax({
                    type: 'POST',
                    url: "LoadLocationServlet",
                    success: function (data) {
                        var d = data.split(",");
                        var x = document.getElementById("location");
                        for(var i = 0; i < d.length; i++){
                            var option = document.createElement("option");
                            option.text = d[i];
                            option.value = d[i];
                            try{
                                x.add(option, x.options[null]);
                            }catch(e){
                                x.add(option, null);
                            }
                        }
                      　document.getElementById("location").value = dloc;
                    }
                });
                $.ajax({
                    type: 'POST',
                    url: "LoadDesignNameServlet",
                    success: function (data) {
                        var d = data.split(",");
                        var x = document.getElementById("devicename");
                        for(var i = 0; i < d.length; i++){
                            var option = document.createElement("option");
                            option.text = d[i];
                            option.value = d[i];
                            try{
                                x.add(option, x.options[null]);
                            }catch(e){
                                x.add(option, null);
                            }
                        }
                        $('#devicename').combobox({
                            filter: function(q, row){
                                    var opts = $(this).combobox('options');
                                    var enterStr = q.toUpperCase();
                                    return row[opts.textField].indexOf(enterStr) !== -1;
                            }
                        });
                        document.getElementById("devicename").value = ddev;
                    }
                });
                var ppp = $('#dg').datagrid('getPager');
                if(ppp){
                    $(ppp).pagination({
                    onBeforeRefresh:function(){   
                            var dsys = document.getElementById("system").value;
                                var dsub = document.getElementById("subsystem").value;
                                var dloc = document.getElementById("location").value;
                                var ddev = document.getElementById("devicename").value;
                                var page = $(".pagination-num").val();
                                var rows = $(".pagination-page-list").val();
                                $.ajax({
                                    type: 'POST',
                                    url: 'DeviceResultServlet?<%=Math.random()%>'+'&page='+page+'&rows='+rows,
                                    scriptCharset: 'UTF-8',
                                    data: "system="+dsys+"&subsystem="+dsub+"&location="+dloc+"&devicename="+ddev,
                                    success: function (data) {
                                        var str = data;
                //                        var str = '{"rows":' + data + '}';
                                        var s = $.parseJSON(str);

                                        $('#dg').datagrid('loadData', s);                      
                                    }
                                });
                        },   

                        onRefresh:function(pageNumber,pageSize){   
                            var dsys = document.getElementById("system").value;
                                var dsub = document.getElementById("subsystem").value;
                                var dloc = document.getElementById("location").value;
                                var ddev = document.getElementById("devicename").value;
                                var page = $(".pagination-num").val();
                                var rows = $(".pagination-page-list").val();
                                $.ajax({
                                    type: 'POST',
                                    url: 'DeviceResultServlet?<%=Math.random()%>'+'&page='+page+'&rows='+rows,
                                    scriptCharset: 'UTF-8',
                                    data: "system="+dsys+"&subsystem="+dsub+"&location="+dloc+"&devicename="+ddev,
                                    success: function (data) {
                                        var str = data;
                //                        var str = '{"rows":' + data + '}';
                                        var s = $.parseJSON(str);

                                        $('#dg').datagrid('loadData', s);                      
                                    }
                                });
                        },   

                        onChangePageSize:function(){   
                            var dsys = document.getElementById("system").value;
                                var dsub = document.getElementById("subsystem").value;
                                var dloc = document.getElementById("location").value;
                                var ddev = document.getElementById("devicename").value;
                                var page = $(".pagination-num").val();
                                var rows = $(".pagination-page-list").val();
                                $.ajax({
                                    type: 'POST',
                                    url: 'DeviceResultServlet?<%=Math.random()%>'+'&page='+page+'&rows='+rows,
                                    scriptCharset: 'UTF-8',
                                    data: "system="+dsys+"&subsystem="+dsub+"&location="+dloc+"&devicename="+ddev,
                                    success: function (data) {
                                        var str = data;
                //                        var str = '{"rows":' + data + '}';
                                        var s = $.parseJSON(str);

                                        $('#dg').datagrid('loadData', s);                      
                                    }
                                });
                        },   

                        onSelectPage:function(pageNumber,pageSize){   
                        var dsys = document.getElementById("system").value;
                                var dsub = document.getElementById("subsystem").value;
                                var dloc = document.getElementById("location").value;
                                var ddev = document.getElementById("devicename").value;
                                var page = $(".pagination-num").val();
                                var rows = $(".pagination-page-list").val();
                                $.ajax({
                                    type: 'POST',
                                    url: 'DeviceResultServlet?<%=Math.random()%>'+'&page='+page+'&rows='+rows,
                                    scriptCharset: 'UTF-8',
                                    data: "system="+dsys+"&subsystem="+dsub+"&location="+dloc+"&devicename="+ddev,
                                    success: function (data) {
                                        var str = data;
                //                        var str = '{"rows":' + data + '}';
                                        var s = $.parseJSON(str);

                                        $('#dg').datagrid('loadData', s);                      
                                    }
                                });
                        }  
                });
                }
                
            }
            
            
            
            function getSelected(){
                var row = $('#dg').datagrid('getSelected');
                if (row){
                    
                    var mainpart = row.subsystem;
                    var zz = /[\u4e00-\u9fa5]/;    ///\p{Unified_Ideograph}/u
                    var yy = /([\d{2}]+[BIWU]+[\d]+[A-Z])/;
                    var xx = /(R|BS|LA|LB|BR|RB)/;
                    if (zz.test(mainpart)){
                        mainpart = " ";
                    }if (yy.test(mainpart)){
                        mainpart = mainpart + "-";
                    }if (xx.test(mainpart)){
                        mainpart = mainpart;
                    }

                    var dename = row.devicename;
                    var senumber = row.judge;
                    var anothername = row.another;
                    var newSenumber = new Array();
                    if (senumber === "Yes" && anothername === ""){    
                        newSenumber[0] = "01";
                        newSenumber[1] = "02";
                        newSenumber[2] = "...";
                        newSenumber[3] = "99";

                    }else if(senumber === "No" && anothername === ""){
                        newSenumber[0] = "1";
                        newSenumber[1] = "2";
                        newSenumber[2] = "...";
                        newSenumber[3] = "9";
                    }else if(senumber === "No" && anothername !== ""){
                        var ww = /([1-9]+[-]+[1-9])|((([0]+[1-9])|([1-9]+[0-9]))+[-]+(([0]+[1-9])|([1-9]+[0-9])))/g;
                        var extractNum = anothername.match(ww);
                        extractNum = extractNum.toString().split('-');
                        var num = extractNum[1]-extractNum[0]+1;
                        for(var j=0; j<num; j++){
                            newSenumber[j] = parseInt(extractNum[0]) + j;
                        }
                    }else if(senumber === "Yes" && anothername !== ""){
                        var ww = /([1-9]+[-]+[1-9])|((([0]+[1-9])|([1-9]+[0-9]))+[-]+(([0]+[1-9])|([1-9]+[0-9])))/g;
                        var extractNum = anothername.match(ww);
                        extractNum = extractNum.toString().split('-');
                        var num = extractNum[1]-extractNum[0]+1;
                        if(extractNum[0]<10){
                            for(var k=0; k<num; k++){
                                newSenumber[k] = parseInt(extractNum[0]) + k;
                                if(newSenumber[k]<10){
                                    newSenumber[k] = "0" + newSenumber[k];
                                }
                            }
                        }else{
                            for(var k=0; k<num; k++){
                                newSenumber[k] = parseInt(extractNum[0]) + k;
                            }
                        }
                        
                    }

                    var locanumber = row.location;
                    if (zz.test(locanumber) || locanumber === "-" || locanumber === ""){
                        locanumber = " "; 
                    }
                    
                }
                var ss = [];
                for(var i=0; i<newSenumber.length; i++){
                    ss.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+'</span>');
                }
                $.messager.alert('设备名称', ss.join('<br/>'));  
                
            }
            
            function getSelections(){
                var sss = [];
                var rows = $('#dg').datagrid('getSelections');
                for(var m=0; m<rows.length; m++){
                    var row = rows[m];
                    var mainpart = row.subsystem;
                    var zz = /[\u4e00-\u9fa5]/;    ///\p{Unified_Ideograph}/u
                    var yy = /([\d{2}]+[BIWU]+[\d]+[A-Z])/;
                    var xx = /(R|BS|LA|LB|BR|RB)/;
                    if (zz.test(mainpart)){
                        mainpart = " ";
                    }if (yy.test(mainpart)){
                        mainpart = mainpart + "-";
                    }if (xx.test(mainpart)){
                        mainpart = mainpart;
                    }

                    var dename = row.devicename;
                    var senumber = row.judge;
                    var anothername = row.another;
                    var newSenumber = new Array();
                    if (senumber === "Yes" && anothername === ""){    
                        newSenumber[0] = "01";
                        newSenumber[1] = "02";
                        newSenumber[2] = "...";
                        newSenumber[3] = "99";

                    }else if(senumber === "No" && anothername === ""){
                        newSenumber[0] = "1";
                        newSenumber[1] = "2";
                        newSenumber[2] = "...";
                        newSenumber[3] = "9";
                    }else if(senumber === "No" && anothername !== ""){
                        var ww = /([1-9]+[-]+[1-9])|((([0]+[1-9])|([1-9]+[0-9]))+[-]+(([0]+[1-9])|([1-9]+[0-9])))/g;
                        var extractNum = anothername.match(ww);
                        extractNum = extractNum.toString().split('-');
                        var num = extractNum[1]-extractNum[0]+1;
                        for(var j=0; j<num; j++){
                            newSenumber[j] = parseInt(extractNum[0]) + j;
                        }
                    }else if(senumber === "Yes" && anothername !==""){
                        var ww = /([1-9]+[-]+[1-9])|((([0]+[1-9])|([1-9]+[0-9]))+[-]+(([0]+[1-9])|([1-9]+[0-9])))/g;
                        var extractNum = anothername.match(ww);
                        extractNum = extractNum.toString().split('-');
                        var num = extractNum[1]-extractNum[0]+1;
                        if(extractNum[0]<10){
                            for(var k=0; k<num; k++){
                                newSenumber[k] = parseInt(extractNum[0]) + k;
                                if(newSenumber[k]<10){
                                    newSenumber[k] = "0" + newSenumber[k];
                                }
                            }
                        }else{
                            for(var k=0; k<num; k++){
                                newSenumber[k] = parseInt(extractNum[0]) + k;
                            }
                        }       
                    }

                    var locanumber = row.location;
                    if (zz.test(locanumber) || locanumber === "-" || locanumber === ""){
                        locanumber = " "; 
                    }

                    var ss = [];
                    for(var i=0; i<newSenumber.length; i++){
                        ss.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+" "+'</span>');
                    }
                    sss.push('<br>'+ss+'</br>');
                }
                $.messager.alert('设备名称', sss.join('<br/>'));     
            }


        </script>
        <style type="text/css">             
            label{
                font-size: 16px
            }
        </style>
        <style type="text/css">  
        body,div{margin:0;padding:0} 
        label{
            font-size: 16px
        }

        </style>

        <title>名称查询</title>
        <style type="text/css">
            span{
                font-size: 14px;
                font-weight: bold;
            }

        </style>
    </head>
    <body>
        <h2 style="text-align: center">设备名称查询</h2>
        <div style="margin:20px 0;"></div>
            <div class="easyui-panel" style="padding:5px;width: 1210px">
                <a href="#" class="easyui-linkbutton" data-options="toggle:true" iconCls="icon-add" onclick="inputmenu()">录入</a>
                <a href="#" class="easyui-linkbutton" data-options="toggle:true" iconCls="icon-help" onclick="querymenu()">查询</a>
                <a href="#" class="easyui-linkbutton" data-options="toggle:true" iconCls="icon-back" onclick="mainpage()">主页</a>
            </div>
                
        
            <div id="tb" style="padding:2px 5px;width: 100%;">
            <!--<form action="QueryDeviceServlet" method="post" target="" onsubmit="return submitform();">-->
                    <div class="con"  >
                        <div style="position:absolute;width: 200px;left: 300px">
                            <label for="system">所属系统：</label> 
                            <select  id="system" name="system" style="width: 100px; height: 25px" >
                                <option value="none" ${system=="none"?'selected':''}>未选择</option>                                
                            </select> 
                        </div>
                        <div style="position:absolute;width: 200px;left:500px" >
                            <label for="subsystem">主体分区：</label>
                            <select  id="subsystem" name="subsystem" style="width: 100px;height: 25px" >
                                <option value="none">未选择</option>                                
                            </select>
                        </div>
                        <div style="position:absolute;width: 200px;left:700px">
                            <label for="location">位置编号：</label>
                            <select  id="location" name="location" style="width: 100px;height: 25px" >
                                <option value="none">未选择</option>                                
                            </select>
                        </div>
                        <div style="position:absolute;width: 200px;left: 900px" >               
                            <label for="devicename">设备代号：</label>                                
                            <select class="easyui-combobox" id="devicename" name="devicename" options="filter: filterCombo" style="width: 100px;height: 25px" >
                                <option value="none">未选择</option>                                
                            </select>                            
                        </div>
                    </div>
                    <div style="position:absolute;top:150px;bottom: 0; left:0;right:0;text-align: center">                    
                        <input style="width:90px; font-size: 14px" class="a-upload" onclick="y()" type="submit" value="查询" >
                    </div>
            <div style="position: absolute;top: 200px;width: 1200px">
                <table id="dg" name="dg"　class="easyui-datagrid"   style="width: auto; padding: 5px"
                       data-options="title:'查询',iconCls:'icon-search', pagination:true,pageSize:20,toolbar:toolbar,rownumbers:true,fitColumns:true,
                       url:'DeviceResultServlet?<%=Math.random()%>',method:'get',toolbar:'#toolbar'">

                    <thead>
                        <tr>
                            <th data-options="field:'ck',checkbox:true"></th>
                            <th data-options="field:'system',width:100,align:'center'">系统代号</th>
                            <th data-options="field:'Chinesename',width:250,align:'center'">设备/部件中文名称</th>
                            <th data-options="field:'Englishname',width:270,align:'center'">设备/部件英文名称</th>
                            <th data-options="field:'devicename',width:170,align:'center'">设备/部件名称代号</th>
                            <th data-options="field:'subsystem',width:140,align:'center'">所在主体分区</th>
                            <th data-options="field:'location',width:130,align:'center'">位置</th>
                            <th data-options="field:'judge',width:160,align:'center'">同一周期内个数>9</th>
                            <th data-options="field:'another',width:150,align:'center'">别名（可选填）</th>
                            <th data-options="field:'remark',width:300,align:'center'">备注</th>
                        </tr>
                    </thead>
                </table>
                <input type="hidden" id="hd1" name="hd1">
                <div id="toolbar"> 

                        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="newdevice()">编辑</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroy()">删除</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="getSelected()">打印单行设备名字</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="getSelections()">打印多行设备名字</a>

                </div>
            </div>
        </div>
                
        
                
        <script type="text/javascript">
            $(function(){
            　　//初始加载，表格宽度自适应
                $(document).ready(function(){
                    fitCoulms();
                });
            　　//浏览器窗口大小变化后，表格宽度自适应
                $(window).resize(function(){
                    fitCoulms();
                });
            });

            //表格宽度自适应，这里的#dg是datagrid表格生成的div标签
            function fitCoulms(){
                $('#dg').datagrid({
                    fitColumns:true
                });
            }
           function inputmenu(){                
              window.location.href='upload.jsp';
           } 
         
           function querymenu(){                
              window.location.href='querydevice.jsp';
           }
           function mainpage(){                
              window.location.href='index.jsp';
           }
            function submitform() {

            }
            function y(){
//                $('#dg').datagrid({url:getUrl()+"/QueryDeviceServlet"+$("#queryCondition").serialize()});
                var dsys = document.getElementById("system").value;
                var dsub = document.getElementById("subsystem").value;
                var dloc = document.getElementById("location").value;
                var ddev = document.getElementById("devicename").value;
                var page = $(".pagination-num").val();
                var rows = $(".pagination-page-list").val();
                $.ajax({
                    type: 'POST',
                    url: 'DeviceResultServlet?<%=Math.random()%>'+'&page='+page+'&rows='+rows,
                    scriptCharset: 'UTF-8',
                    data: "system="+dsys+"&subsystem="+dsub+"&location="+dloc+"&devicename="+ddev,
                    success: function (data) {
                        var str = data;
//                        var str = '{"rows":' + data + '}';
                        var s = $.parseJSON(str);
                        
                        $('#dg').datagrid('loadData', s);                      
                    }
                });
            }
           
            $('#devicename').combobox({
		filter: function(q, row){
			var opts = $(this).combobox('options');
			var enterStr = q.toUpperCase();
			return row[opts.textField].indexOf(enterStr) !== -1;
		}
            });
            
            function newdevice(){
                var row = $('#dg').datagrid('getSelected');
                var index = $('#dg').datagrid('getRowIndex',row);
                var devdata = $('#dg').datagrid('getData').rows[index];
                document.getElementById("hd1").value = JSON.stringify(devdata);
                if(row){
                    var yn = window.confirm("确认修改此设备信息？");
                    if(yn){
                        $.ajax({
                            type: 'POST',
                            url: "EditDeviceServlet",
                            data: "dslId=" + row.dslId + "&devData=" + document.getElementById("hd1").value,
                            success: function (data) {
                                window.location.href = 'editdevice.jsp'; 
                            }
                        });
                    }else
                        return false;
                }else{
                    alert("请选择编辑项！");
                }
            }
            
            function destroy(){
                var row = $('#dg').datagrid('getSelected');
                if(row){
                    var yn = window.confirm("确认删除此设备信息？");
                    if(yn){
                        $.ajax({
                            type: 'POST',
                            url: "DeleteDeviceServlet",
                            data: "dslId=" + row.dslId,
                            success: function (data) {
                                alert(data);
                                window.history.go(0);
                            }
                        });
                    }else
                        return false;
                }else{
                    alert("请选择删除项！");
                }
            }
       </script>

    </body>
</html>