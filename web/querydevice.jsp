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
            
            window.onload = function (){
                $('#btn_edit').linkbutton('disable');
                $('#btn_remove').linkbutton('disable');
                $('#system').combobox('select','none');
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
//                        document.getElementById("subsystem").value = dsub;
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
//                      　document.getElementById("location").value = dloc;
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
//                        document.getElementById("devicename").value = ddev;
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
            
            function formatOper(val,row,index){
                return '<a href="#" onclick="getSelected('+index+')">查看</a>';               
            }
            
            function getSelected(index){
                $('#dg').datagrid('selectRow', index);
                var ssAll = [];
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
                        locanumber = ""; 
                    }
                    
                }
                var ss = [];
                var ssM = [];
                for(var i=0; i<newSenumber.length; i++){
                    ss.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+" "+'</span>');
                    var reamrkMag = row.remark;
                    if(reamrkMag.toString().search('H')!==-1 && reamrkMag.toString().search('磁铁名')!==-1){
                        ssM.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+'H'+" "+'</span>');
                    }
                    if(reamrkMag.toString().search('V')!==-1 && reamrkMag.toString().search('磁铁名')!==-1){
                        ssM.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+'V'+" "+'</span>');
                    }
                    if(reamrkMag.toString().search('S')!==-1 && reamrkMag.toString().search('磁铁名')!==-1){
                        ssM.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+'S'+" "+'</span>');
                    }
                }
                ssAll.push('<br>'+ss+'</br>');
                ssAll.push('<br>'+ssM+'</br>');
                $.messager.alert('设备名称', ssAll.join('<br/>'));  
                
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
                        locanumber = ""; 
                    }

                    var ss = [];
                    var ssM = [];
                    for(var i=0; i<newSenumber.length; i++){
                        ss.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+" "+'</span>');
                        var reamrkMag = row.remark;
                        if(reamrkMag.toString().search('H')!==-1 && reamrkMag.toString().search('磁铁名')!==-1){
                            ssM.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+'H'+" "+'</span>');
                        }
                        if(reamrkMag.toString().search('V')!==-1 && reamrkMag.toString().search('磁铁名')!==-1){
                            ssM.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+'V'+" "+'</span>');
                        }
                        if(reamrkMag.toString().search('S')!==-1 && reamrkMag.toString().search('磁铁名')!==-1){
                            ssM.push('<span>'+mainpart+locanumber+dename+newSenumber[i]+'S'+" "+'</span>');
                        }
                    }
                    sss.push(ss);
                    sss.push(ssM);
                }
                $.messager.alert('设备名称', sss.join('<br/>'));     
            }
            
            function exportPart(){
//                var rows = $('#dg').datagrid('getSelections');
//                var str = "系统代号,设备/部件中文名称,设备/部件英文名称,设备/部件名称代号,所在主体分区,位置,同一周期内个数>9,别名,备注,生成设备名称\n";
                var rows = $('#dg').datagrid('getSelections');
                if(rows==null||rows==""){
                    return $.messager.alert("提示","请至少选中一行，单击可选定或取消选定");
                }
                for(var i=0; i<rows.length; i++){
                    var row = rows[i];
                    delete row.dslId;
                    delete row.deviceId;
                    delete row.systemId;
                    delete row.subsystemId;
                    delete row.locationId;
                    delete row.judgeId;
                }
                document.getElementById("hidetext").value = JSON.stringify(rows);
                $('#ff').form('submit');            
            }
            
            function exportAll(){
                var dsys = $('#system').combobox("getValues");
//                var dsys = document.getElementById("system").value;
                var dsub = document.getElementById("subsystem").value;
                var dloc = document.getElementById("location").value;
                var ddev = document.getElementById("devicename").value;
                location.href = 'ExportAllServlet?system=' + dsys + '&subsystem=' + dsub + '&location=' + dloc + '&devicename=' + ddev;
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
            <div class="easyui-panel" style="padding:5px;width: 1510px">
                <a href="#" class="easyui-linkbutton" data-options="toggle:true" iconCls="icon-add" onclick="inputmenu()">录入</a>
                <a href="#" class="easyui-linkbutton" data-options="toggle:true" iconCls="icon-help" onclick="querymenu()">查询</a>
                <a href="#" class="easyui-linkbutton" data-options="toggle:true" iconCls="icon-back" onclick="mainpage()">主页</a>
            </div>
                
        
            <div id="tb" style="padding:2px 5px;width: 100%;">
            <!--<form action="QueryDeviceServlet" method="post" target="" onsubmit="return submitform();">-->
                    <div class="con"  >
                        <div style="position:absolute;width: 200px;left: 300px">
                            <label for="system">所属系统：</label>   
                            <input class="easyui-combobox" id="system" name="system" data-options="valueField:'value',textField:'text',url:'LoadSystemServlet',method:'post',multiple:true, panelHeight:'auto'" style="width: 100px; height: 25px" >
                        </div>
                        <div style="position:absolute;width: 200px;left:500px" >
                            <label for="subsystem">主体分区：</label>
                            <select  id="subsystem" name="subsystem" style="width: 100px;height: 25px" >
                                <option value="none">none</option>                                
                            </select>
                        </div>
                        <div style="position:absolute;width: 200px;left:700px">
                            <label for="location">位置编号：</label>
                            <select  id="location" name="location" style="width: 100px;height: 25px" >
                                <option value="none">none</option>                                
                            </select>
                        </div>
                        <div style="position:absolute;width: 200px;left: 900px" >                
                            <label for="devicename">设备代号：</label>                                
                            <select class="easyui-combobox" id="devicename" name="devicename" options="filter: filterCombo" style="width: 100px;height: 25px" >
                                <option value="none">none</option>                                
                            </select>                            
                        </div>
                    </div>
                    <div style="position:absolute;top:150px;bottom: 0; left:0;right:0;text-align: center">                    
                        <input style="width:90px; font-size: 14px" class="a-upload" onclick="y()" type="submit" value="查询" >
                    </div>
            <div style="position: absolute;top: 200px;width: 1500px">
                <table id="dg" name="dg"　class="easyui-datagrid"   style="width: auto; padding: 5px"
                       data-options="title:'查询',iconCls:'icon-search', pagination:true,pageSize:20,toolbar:toolbar,rownumbers:true,fitColumns:true,
                       url:'DeviceResultServlet?<%=Math.random()%>',method:'get',toolbar:'#toolbar'">

                    <thead>
                        <tr>
                            <th data-options="field:'ck',checkbox:true"></th>
                            <th data-options="field:'system',width:100,align:'center'">系统代号</th>
                            <th data-options="field:'Chinesename',width:210,align:'center'">设备/部件中文名称</th>
                            <th data-options="field:'Englishname',width:290,align:'center'">设备/部件英文名称</th>
                            <th data-options="field:'devicename',width:170,align:'center'">设备/部件名称代号</th>
                            <th data-options="field:'subsystem',width:140,align:'center'">所在主体分区</th>
                            <th data-options="field:'location',width:130,align:'center'">位置</th>
                            <th data-options="field:'judge',width:160,align:'center'">同一周期内个数>9</th>
                            <th data-options="field:'another',width:150,align:'center'">别名（可选填）</th>
                            <th data-options="field:'remark',width:200,align:'center'">备注</th>
                            <th data-options="field:'operation',width:130,align:'center',formatter:formatOper">生成设备名称</th>
                        </tr>
                    </thead>
                </table>
                <input type="hidden" id="hd1" name="hd1">      
                <form type="hidden" id="ff" action="ExportPartServlet" method="post">
                    <input type="text" style="display:none" name="hidetext" id="hidetext">
                </form>
                <div id="toolbar"> 

                    <a id="btn_edit" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="newdevice()">编辑</a>
                    <a id="btn_remove" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroy()">删除</a>
                        <!--<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="getSelected()">打印单行设备名字</a>-->
                        <!--<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="getSelections()">打印多行设备名字</a>-->
                        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="exportPart()">导出指定项</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="exportAll()">导出查询项</a>
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
//                $.ajax({
//                    type: 'POST',
//                    url: 'QueryDeviceServlet',
//                    scriptCharset: 'UTF-8',
//                    success: function (data) {
//                        var str = '{"rows":' + data + '}';
//                        var s = $.parseJSON(str);
//                        
//                        $('#dg').datagrid('loadData', s);                      
//                    }
//                });

            }
            function y(){
//                $('#dg').datagrid({url:getUrl()+"/QueryDeviceServlet"+$("#queryCondition").serialize()});
                var dsys = document.getElementById("system").value;
//                var dsys = $('#system').combobox("getValues");
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