<%-- 
    Document   : editdevice
    Created on : 2020-4-5, 22:18:15
    Author     : BaiYu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                      request.getContextPath() + "/" ;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name=”viewport” content=”width=device-width/>  <!--, initial-scale=1″ -->
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/icon.css?<%=Math.random()%>">    
        <link rel="stylesheet" type="text/css" href="mydesigncss.css">
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="edit.js?<%=Math.random()%>"></script>
        <script type="text/javascript">
            <%
                Integer dslId = (Integer) session.getAttribute("dslId");
                String devData = (String) session.getAttribute("devData");
            %>
            var id = <%=dslId%>;
            var dd =<%=devData%>;
            for (var p in dd)
            {
                if (dd[p] === '"null"') {
                    dd[p] = '';
                }
            }
            if (dd !== null) {
                rowe[0].value = dd["system"] +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseSystem();\">*更改系统代号*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newSystem();\">*新建系统代号*</a>";
//                rowe[1].value = dd["itemid"];
                rowe[1].value = dd["Chinesename"] +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseChinese();\">*更改中文名称*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newChinese();\">*新建中文名称*</a>";
                rowe[2].value = dd["Englishname"] +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseEnglish();\">*更改英文名称*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newEnglish();\">*新建英文名称*</a>";
                rowe[3].value = dd["devicename"] +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseDesign();\">*更改名称代号*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newDesign();\">*新建名称代号*</a>";
                rowe[4].value = dd["subsystem"] +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseSubsystem();\">*更改主体分区*</a>";
                rowe[5].value = dd["location"] + "&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseLocation();\">*更改位置信息*</a>";
                rowe[6].value = dd["judge"] +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseJudge();\">*更改选择Yes/No*</a>"
                rowe[7].value = dd["another"];
                rowe[8].value = dd["remark"];
            }
           window.onload = function (){
               $('#adddevice').propertygrid('loadData', rowe);
           };
            
        </script>
        <base href="<%=basePath %>">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">
            label{
                font-size: 16px
            }
        </style>
        <title>名称录入</title>
    </head>
    <body>
        <h2 style="text-align: center">设备名称录入</h2>
        <div style="margin:20px 0;">
            <div class="easyui-panel" style="padding:5px;">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add"
                    onclick="return inputmenu();">录入</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-help"
                    onclick="return querymenu();">查询</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-back"
                    onclick="return mainpage();">主页</a>
            </div>
            <div class="easyui-panel" style="height: 820px;padding-top:20px">
            <div style="margin:0 auto;width:1000px">
                <form id="ff2" action="UpdateDeviceServlet" method="POST" target="_blank" onsubmit="return submitform();" >
                    <div id="table" >
                        <div style="padding-top:10px">             
                            <input id="query" name="query" class="easyui-textbox" prompt="请输入关键字" label="请输入设备中文名：" labelPosition="before"  labelWidth="160" labelAlign="left" style="width:400px" >                           
                        </div>
                        <div style="padding-top:10px"></div>
                        <table id="adddevice" name="adddevice" class="easyui-propertygrid" style=" width: 1000px;" data-options="
                               method: 'get',
                               showGroup: true,
                               scrollbarSize: 0,                                  
                               columns: mycolumns                           
                               ">
                        </table>  
                    </div> 
                    <div style="position:relative;top:15px;  left:0;right:0;text-align: center">
                        <input id="subm2" name="subm2" style="width:90px; font-size: 14px" class="a-upload" type="submit" value="提交">
                        <input type="hidden" id="hd1" name="hd1"/>
                        <input type="hidden" id="hd2" name="hd2"/>
                    </div>
                </form>
            </div>
                <div id="chooseSystemWindow" class="easyui-dialog" title="系统代号" style="width:300px; height:400px;padding:10px;font-size:14px;text-align: center" data-options="iconCls:'icon-more',closed: true,resizable:true">
                    <table id="tableChooseSys" name="tableChooseSys" class="easyui-datagrid" title="查询结果" align="center"
                       data-options="
                       singleSelect: true,
                       collapsible: true,                           
                       rownumbers: true,                        
                        ">
                        <thead>
                            <tr>
                                <!--<th data-options="field:'systemId',width:60,sortable:true">系统Id</th>-->
                                <th data-options="field:'system',width:220,align:'center',sortable:true">系统代号</th>
                            </tr>
                        </thead>  
                    </table>
                    <div style="margin:5px 0;"></div>
                    <a href="#" class="easyui-linkbutton" onclick="return setSystem();"  data-options="iconCls:'icon-save'">保存</a><!--style="position: absolute;left: 150px"-->
                </div>  
                <div id="chooseChineseWindow" class="easyui-dialog" title="设备中文名称" style="width:800px; height:600px;padding:10px;font-size:14px;" data-options="iconCls:'icon-more',closed: true,resizable:true">                        
                        <table id="tableChooseChin" name="tableChooseChin" class="easyui-datagrid" title="查询结果" align="center"
                            data-options="
                            singleSelect: true,
                            collapsible: true,                           
                            rownumbers: true,
                            toolbar:toolbar,
                             ">
                            <thead>
                                <tr>
                                    <th data-options="field:'Chinesename',width:250,align:'center'">设备/部件中文名称</th>
                                    <th data-options="field:'Englishname',width:270,align:'center'">设备/部件英文名称</th>
                                    <th data-options="field:'devicename',width:200,align:'center'">设备/部件名称代号</th>
                                </tr>
                            </thead>  
                        </table>
                    <div style="margin:5px 0;"></div>
                    <!--<a href="#" class="easyui-linkbutton" onclick="setChinese()" style="position: absolute;left: 400px" data-options="iconCls:'icon-save'">保存</a>-->                     
                </div>
                <div id="chooseEnglishWindow" class="easyui-dialog" title="设备英文名称" style="width:800px; height:400px;padding:10px;font-size:14px;text-align: center" data-options="iconCls:'icon-more',closed: true,resizable:true">                        
                        <table id="tableChooseEng" name="tableChooseEng" class="easyui-datagrid" title="查询结果" align="center"
                            data-options="
                            singleSelect: true,
                            collapsible: true,                           
                            rownumbers: true,                        
                             ">
                            <thead>
                                <tr>
                                    <th data-options="field:'Chinesename',width:250,align:'center'">设备/部件中文名称</th>
                                    <th data-options="field:'Englishname',width:270,align:'center'">设备/部件英文名称</th>
                                    <th data-options="field:'devicename',width:200,align:'center'">设备/部件名称代号</th>
                                </tr>
                            </thead>  
                        </table>
                    <div style="margin:5px 0;"></div>
                    <a href="#" class="easyui-linkbutton" onclick="return setEnglish();" data-options="iconCls:'icon-save'">保存</a><!--style="position: absolute;left: 400px" -->                     
                </div>
                <div id="chooseDeviceWindow" class="easyui-dialog" title="设备名称代号" style="width:800px; height:400px;padding:10px;font-size:14px;text-align: center" data-options="iconCls:'icon-more',closed: true,resizable:true">                        
                        <table id="tableChooseDev" name="tableChooseDev" class="easyui-datagrid" title="查询结果" align="center"
                            data-options="
                            singleSelect: true,
                            collapsible: true,                           
                            rownumbers: true,                        
                             ">
                            <thead>
                                <tr>
                                    <th data-options="field:'Chinesename',width:250,align:'center'">设备/部件中文名称</th>
                                    <th data-options="field:'Englishname',width:270,align:'center'">设备/部件英文名称</th>
                                    <th data-options="field:'devicename',width:200,align:'center'">设备/部件名称代号</th>
                                </tr>
                            </thead>  
                        </table>
                    <div style="margin:5px 0;"></div>
                    <a href="#" class="easyui-linkbutton" onclick="return setDevice();" data-options="iconCls:'icon-save'">保存</a>                     
                </div>
                <div id="chooseSubsystemWindow" class="easyui-dialog" title="设备主体分区" style="width:600px; height:300px;padding:10px;font-size:14px;" data-options="iconCls:'icon-more',closed: true,resizable:true">
                    <div style=""> <label style="color:#800080;font-weight: bolder;">主体分区：</label>
                        <input type="radio" name="mainpart" id="acc" value="acc" onclick="return showaccdiv();" checked="checked" /><label style="color:#800080;font-weight: bolder;" for="acc">加速器</label> 
                        <input type="radio" name="mainpart" id="bl" value="bl" onclick="return showbldiv();" /><label style="color:#800080;font-weight: bolder;" for="bl">线站</label>
                        <input type="radio" name="mainpart" id="other" value="other" onclick="return showotherdiv();" /><label style="color:#800080;font-weight: bolder;" for="other">其他建筑单体</label>
                    </div> 
                    <div name="accdiv" id="accdiv" style="padding-top:10px">                        
                        <input id="accpart" name="accpart" class="easyui-textbox" prompt="R/BS/LA/BR/RB/LB" labelWidth="110" label="分区代号：" labelPosition="before" labelAlign="left" style="width:300px">            
                    </div>
                    <div name="bldiv" id="bldiv" style="padding-top:10px">  
                        <div style="padding-top:5px">
                            <input id="blpart1" name="blpart1" class="easyui-textbox" prompt="可填范围01,02,...,48" labelWidth="190" label="储存环周期单元顺序号：" labelPosition="before" labelAlign="left" style="width:400px">
                        </div>
                        <div style="padding-top:5px">
                            <input id="blpart2" name="blpart2" class="easyui-textbox" prompt="U/W/B/I" labelWidth="170" label="引光原件：" labelPosition="before" labelAlign="left" style="width:400px">
                        </div>
                        <div style="padding-top:5px">
                            <input id="blpart3" name="blpart3" class="easyui-textbox" prompt="可填范围1,2,...,9" labelWidth="170" label="光束线顺序号：" labelPosition="before" labelAlign="left" style="width:400px">
                        </div>
                        <div style="padding-top:5px">
                            <input id="blpart4" name="blpart4" class="easyui-textbox" prompt="可填范围A,B,...,Z" labelWidth="170" label="实验站顺序号：" labelPosition="before" labelAlign="left" style="width:400px">
                        </div>                
                    </div>
                    <div name="otherdiv" id="otherdiv" style="padding-top:10px">
                        <input id="otherpart" name="otherpart" class="easyui-textbox" labelWidth="150" label="建筑单体中文名：" labelPosition="before" labelAlign="left" style="width:400px">
                    </div>
                    <div style="padding-top:5px;padding-left: 205px;">
                    <a href="#" class="easyui-linkbutton" onclick="return setMainPart();" data-options="iconCls:'icon-save'">保存</a> <!--style="position: absolute;left: 200px" -->
                    </div>
                </div>
                <div id="chooseLocationWindow" class="easyui-dialog" title="设备位置信息" style="width:600px; height:300px;padding:10px;font-size:14px;" data-options="iconCls:'icon-more',closed: true,resizable:true">
                    <div style="padding-left: 5px;"> <label style="color:#800080;font-weight: bolder;">位置信息：</label>
                        <input type="radio" name="locationpart" id="acclocation" value="acclocation" onclick="return showacclocation();" checked="checked" /><label style="color:#800080;font-weight: bolder;" for="acclocation">加速器</label> 
                        <input type="radio" name="locationpart" id="bllocation" value="bllocation" onclick="return showbllocation();" /><label style="color:#800080;font-weight: bolder;" for="bllocation">线站</label>
                        <input type="radio" name="locationpart" id="otherlocation" value="otherlocation" onclick="return showotherlocation();" /><label style="color:#800080;font-weight: bolder;" for="otherlocation">其他</label>
                    </div> 
                    <div name="acclocationdiv" id="acclocationdiv" style="padding-top:10px;padding-left: 15px;">                        
                        <div style=""> <label style="color:#0000FF;font-weight: bolder;">主体分区：</label>
                            <input type="radio" name="m1" id="m11" value="m11" onclick="return showacc();" checked="checked" /><label style="color:#0000FF;font-weight: bolder;" for="m11">R</label> 
                            <input type="radio" name="m1" id="m12" value="m12" onclick="return showbl();" /><label style="color:#0000FF;font-weight: bolder;" for="m12">BS</label>
                            <input type="radio" name="m1" id="m13" value="m13" onclick="return showother();" /><label style="color:#0000FF;font-weight: bolder;" for="m13">LA/BR/RB/LB</label>
                        </div> 
                    </div>
                    <div name="acclocation1" id="acclocation1" style="padding-top:5px;padding-left: 15px;">
                        <input id="mm1" name="mm1" class="easyui-textbox" prompt="可填范围01-48（不连续位置请用英文','隔开）" labelWidth="110" label="选择范围：" labelPosition="before" labelAlign="left" style="width:400px">
                    </div>
                    <div name="acclocation2" id="acclocation2" style="padding-top:5px;padding-left: 15px;">
                        <input id="mm2" name="mm2" class="easyui-textbox" prompt="可填范围1-4" labelWidth="110" label="选择范围：" labelPosition="before" labelAlign="left" style="width:400px">
                    </div>
                    <div name="acclocation3" id="acclocation3" style="padding-top:5px;padding-left: 15px;">
                        <input id="mm3" name="mm3" class="easyui-textbox" prompt="缺省" value="缺省" labelWidth="110" label="选择范围：" labelPosition="before" labelAlign="left" style="width:400px">
                    </div>
                    <div name="bllocationdiv" id="bllocationdiv" style="padding-top:10px;padding-left: 15px;">  
                         <input id="bl1" name="bl1" class="easyui-textbox" prompt="FE/BL/ES" labelWidth="110" label="选择范围：" labelPosition="before" labelAlign="left" style="width:400px">          
                    </div>
                    <div name="otherlocationdiv" id="otherlocationdiv" style="padding-top:10px;padding-left: 15px;">
                        <input id="other1" name="other1" class="easyui-textbox" labelWidth="150" label="建筑单体中文名：" labelPosition="before" labelAlign="left" style="width:400px">
                    </div>
                    <div style="padding-top:5px;padding-left: 205px;">
                    <a href="#" class="easyui-linkbutton" onclick="return setLocation();" data-options="iconCls:'icon-save'">保存</a> 
                    </div>
                </div>
                <div id="chooseJudgeWindow" class="easyui-dialog" title="设备顺序信息" style="width:400px; height:150px;padding:10px;font-size:14px;" data-options="iconCls:'icon-more',closed: true,resizable:true">
                    <div style="padding-left: 5px;"> <label style="font-weight: bolder;">一个周期单元内的最大台套数>9：</label>
                        <input type="radio" name="judgepart" id="yes" value="Yes"/><label style="color:#800080;font-weight: bolder;" for="yes">Yes</label> 
                        <input type="radio" name="judgepart" id="no" value="No" /><label style="color:#800080;font-weight: bolder;" for="no">No</label>
                    </div>
                    <div style="margin:5px 0;"></div>
                    <a href="#" class="easyui-linkbutton" onclick="return setJudge();" style="position: absolute;left: 200px" data-options="iconCls:'icon-save'">保存</a> 
                    
                </div>
        </div>
        <script type="text/javascript">
            function inputmenu() {
                window.location.href = "upload.jsp";
                return false;
            }

            function querymenu() {
                window.location.href = "querydevice.jsp";
                return false;
            }

            function mainpage() {
                window.location.href = "index.jsp";
                return false;
            }
            var toolbar = [{
                text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        var row = $('#tableChooseChin').datagrid('getSelected');
                        var choose = row.Chinesename;
                        $('#chooseChineseWindow').dialog('close');
                        $('#adddevice').datagrid('updateRow', {
                            index: 1,
                            row: {
                                value: choose +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseChinese();\">*更改中文名称*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newChinese();\">*新建中文名称*</a>"
                            }
                        });
                    }
                   
            }];

            $('#plinsert').click(function () {
                if ($('#plinsert').is(':checked')) {
                    document.getElementById("shangchuan").style.visibility = "visible";
                    document.getElementById("up1").style.visibility = "visible";
                } else {
                    document.getElementById("shangchuan").style.visibility = "hidden";
                    document.getElementById("up1").style.visibility = "hidden";
                    $("#errorTip1").hide();
                    $("#subm1").hide();
                }
            });

            $("#subm1").click(function (e) {
                if ($("#successTip1").text() !== '') {
                    var delname = document.getElementById("successTip1hide").value;
                    var yon = window.confirm("再次提交会覆盖之前的文件，确认提交？");
                    if (yon) {
                        $.ajax({
                            method: "POST",
                            url: "DeleteFileServlet",
                            data: { delname: delname },
                            fail: function () {
                                alert("上传出错！");
                                e.preventDefault();
                            }
                        });
                    } else {
                        e.preventDefault();
                    }
                }
            });

            function upload1() {
                var file = $("#up1").val();
                if (file !== "") {
                    var names = $("#up1").val().split(".");
                    if ((names[1] !== "xls") && (names[1] !== "xlsx")) {
                        $("#subm1").attr("style", "width:120px;height:33px;padding-left:500px;visibility:hidden");
                        $("#errorTip1").html("文件格式必须为.xls或.xlsx");
                        $("#errorTip1").show();

                        return;
                    } else {
                        $("#subm1").attr("style", "width:120px;height:33px");
                        $("#errorTip1").hide();
                    }
                }
            }

            function submitform() {
                document.getElementById("hd2").value = id;
                var deviceinfo = $('#adddevice').datagrid("getData");
                document.getElementById("hd1").value = JSON.stringify(deviceinfo);
                var yn = window.confirm("确认修改？");
                    if (yn) {
                        alert("已提交");
                    } else {
                        return false;
                    }
            }

            function callback(success, message, url) {
                if (success == false) {
                    $("#errorTip1").html(message);
                    $("#errorTip1").show();
                } else {
                    $("#errorTip1").hide();
                    $("#successTip1").html(message);
                    $("#successTip1").show();
                    document.getElementById("successTip1hide").value = url;
                }
            }
            
            var mycolumns = [[

                    {field: 'name', title: '设备命名结构', width: 100, sortable: true},
                    {field: 'value', title: '设备信息', width: 100, resizable: false, formatter: function (value, arr) {
                            var editor = '';
                            if (typeof arr.editor === 'object') {
                                editor = arr.editor.type;
                            } else {
                                editor = arr.editor;
                            }
                            if (editor === "numberbox" && value !== '') {
                                return Number(value);
                            } else
                                return value;

                        }}
                ]];
            
            function setSystem() {
                var row = $('#tableChooseSys').datagrid('getSelected');
                var choose = row.system;

                $('#chooseSystemWindow').dialog('close');
                $('#adddevice').datagrid('updateRow', {
                    index: 0,
                    row: {
                        value: choose +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseSystem();\">*更改系统代号*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newSystem();\">*新建系统代号*</a>"
                    }
                });
                return false;
            }
             function newSystem()
            {
                var name = window.prompt("新建系统代号", "");
                if (name !== null && name !== "")
                {
                    $('#adddevice').datagrid('updateRow', {
                    index: 0,
                    row: {
                        value: name +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseSystem();\">*更改系统代号*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newSystem();\">*新建系统代号*</a>"
                    }
                });    
                }
                return false;
            }
            function chooseSystem() {
                $.ajax({
                    type: 'POST',
                    url: 'SetSystemServlet',
                    scriptCharset: 'UTF-8',
                    success: function (data) {
                        var str = '{"rows":' + data + '}';
                        var s = $.parseJSON(str);
                        $('#chooseSystemWindow').dialog('open');
                        $('#tableChooseSys').datagrid('loadData', s);                      
                    }
                });
                return false;
            }
            
//            function chooseItem() {
//                var a = $('#adddevice').datagrid('getRows')[0];
//                $.ajax({
//                    type: 'POST',
//                    url: "SetItemServlet",
//                    scriptCharset: 'UTF-8',
//                    data: "system=" + a.value,
//                    success: function (data) {
//                        var str = data;
//                        $('#adddevice').datagrid('updateRow',{
//                            index: 1,
//                            row:{
//                                value: str +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseItem();\">*点击选择此系统新增序号*</a>"
//                            }
//                        });
//                    }
//                });
//                return false;
//            }
            
            function chooseChinese(){
                var queryname = document.getElementById("query").value;              
                $.ajax({
                    type: 'POST',
                    url: 'SetChineseServlet',
                    scriptCharset: 'UTF-8',
                    data: "queryName=" + queryname,//若返回数据只有中文名则将table中其他两列删掉
                    success: function (data) {
                        //alert(data);
                        var str = '{"rows":' + data + '}';
                        var s = $.parseJSON(str);
                        $('#chooseChineseWindow').dialog('open');
                        $('#tableChooseChin').datagrid('loadData', s);                       
                    }
                });
                return false;
            }    
            function newChinese()
            {
                var name = window.prompt("新建设备中文名", "");
                if (name !== null && name !== "")
                {
                    $('#adddevice').datagrid('updateRow', {
                    index: 1,
                    row: {
                        value: name +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseChinese();\">*更改中文名称*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newChinese();\">*新建中文名称*</a>"
                    }
                });    
                }
                return false;
            }            
            function setChinese(){
                var row = $('#tableChooseChin').datagrid('getSelected');
                var choose = row.Chinesename;
                $('#chooseChineseWindow').dialog('close');
                $('#adddevice').datagrid('updateRow', {
                    index: 1,
                    row: {
                        value: choose +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseChinese();\">*更改中文名称*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newChinese();\">*新建中文名称*</a>"
                    }
                });
                return false;
            }
            
            function chooseEnglish(){
                var engname = $('#adddevice').datagrid('getRows')[1];          
                $.ajax({
                    type: 'POST',
                    url: 'SetEnglishServlet',
                    scriptCharset: 'UTF-8',
                    data: "engName=" + engname.value,
                    success: function (data) {
                        //alert(data);
                        var str = '{"rows":' + data + '}';
                        var s = $.parseJSON(str);
                        $('#chooseEnglishWindow').dialog('open');
                        $('#tableChooseEng').datagrid('loadData', s);                       
                    }
                });
                return false;
            }
            function newEnglish(){
                var name = window.prompt("新建设备英文名", "");
                if (name !== null && name !== "")
                {
                    $('#adddevice').datagrid('updateRow', {
                    index: 2,
                    row: {
                        value: name +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseEnglish();\">*更改英文名称*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newEnglish();\">*新建英文名称*</a>"
                    }
                });    
                }
                return false;
            }
            function setEnglish(){
                var row = $('#tableChooseEng').datagrid('getSelected');
                var choose = row.Englishname;
                $('#chooseEnglishWindow').dialog('close');
                $('#adddevice').datagrid('updateRow', {
                    index: 2,
                    row: {
                        value: choose +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseEnglish();\">*更改英文名称*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newEnglish();\">*新建英文名称*</a>"
                    }
                });
                return false;
            }
            
            function chooseDesign(){
                var devname = $('#adddevice').datagrid('getRows')[2];          
                $.ajax({
                    type: 'POST',
                    url: 'SetDeviceServlet',
                    scriptCharset: 'UTF-8',
                    data: "devName=" + devname.value,
                    success: function (data) {
                        //alert(data);
                        var str = '{"rows":' + data + '}';
                        var s = $.parseJSON(str);
                        $('#chooseDeviceWindow').dialog('open');
                        $('#tableChooseDev').datagrid('loadData', s);                       
                    }
                });
                return false;
            }
            function newDesign(){
                var name = window.prompt("新建设备代号（只允许由英文大写字母组成）", "");
                if (name !== null && name !== "")
                {
                    $('#adddevice').datagrid('updateRow', {
                    index: 3,
                    row: {
                        value: name +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseDesign();\">*更改名称代号*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newDesign();\">*新建名称代号*</a>"
                    }
                });    
                }
                return false;
            }
            function setDevice(){
                var row = $('#tableChooseDev').datagrid('getSelected');
                var choose = row.devicename;
                $('#chooseDeviceWindow').dialog('close');
                $('#adddevice').datagrid('updateRow', {
                    index: 3,
                    row: {
                        value: choose +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" onclick=\"return chooseDesign();\">*更改名称代号*</a>" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return newDesign();\">*新建名称代号*</a>"
                    }
                });
                return false;
            }
            
            function showaccdiv(){
                $('#accdiv').show();
                $('#bldiv').hide();
                $('#otherdiv').hide();
                
            }
            function showbldiv(){
                $('#accdiv').hide();
                $('#bldiv').show();
                $('#otherdiv').hide();
               
            }
            function showotherdiv(){
                $('#accdiv').hide();
                $('#bldiv').hide();
                $('#otherdiv').show();
                
            }
            
            function chooseSubsystem(){
                var redios = document.getElementsByName("mainpart");
                if(redios[0].checked){
                   showaccdiv();
                }
                if(redios[1].checked){
                   showbldiv();
                }
                if(redios[2].checked){
                   showotherdiv();
                }
                $('#chooseSubsystemWindow').dialog('open');
                return false;
            }
            function setMainPart(){
                var redios = document.getElementsByName("mainpart");
                if(redios[0].checked){
                    var r1 = document.getElementById("accpart").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 4,
                        row:{
                            value: r1 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseSubsystem();\">*更改主体分区*</a>"
                        }
                    });
                    $('#chooseSubsystemWindow').dialog('close');
                }
                if(redios[1].checked){
                   var r21 = document.getElementById("blpart1").value;
                   var r22 = document.getElementById("blpart2").value;
                   var r23 = document.getElementById("blpart3").value;
                   var r24 = document.getElementById("blpart4").value;
                   var r2 = r21+r22+r23+r24;
                    $('#adddevice').datagrid('updateRow',{
                        index: 4,
                        row:{
                            value: r2 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseSubsystem();\">*更改主体分区*</a>"
                        }
                    });
                    $('#chooseSubsystemWindow').dialog('close');
                }
                if(redios[2].checked){
                   var r3 = document.getElementById("otherpart").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 4,
                        row:{
                            value: r3 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseSubsystem();\">*更改主体分区*</a>"
                        }
                    });
                    $('#chooseSubsystemWindow').dialog('close');
                }
                return false;
            }
            
            function showacclocation(){
                $('#acclocationdiv').show();
                $('#bllocationdiv').hide();
                $('#otherlocationdiv').hide();
            }
            function showbllocation(){
                $('#acclocationdiv').hide();
                $('#bllocationdiv').show();
                $('#otherlocationdiv').hide();
                $('#acclocation1').hide();
                $('#acclocation2').hide();
                $('#acclocation3').hide();
            }
            function showotherlocation(){
                $('#acclocationdiv').hide();
                $('#bllocationdiv').hide();
                $('#otherlocationdiv').show();
                $('#acclocation1').hide();
                $('#acclocation2').hide();
                $('#acclocation3').hide();
            }
            function showacc(){
                $('#acclocation1').show();
                $('#acclocation2').hide();
                $('#acclocation3').hide();
            }
            function showbl(){
                $('#acclocation2').show();
                $('#acclocation1').hide();
                $('#acclocation3').hide();
            }
            function showother(){
                $('#acclocation3').show();
                $('#acclocation2').hide();
                $('#acclocation1').hide();
            }
            function chooseLocation(){
                var redios1 = document.getElementsByName("locationpart");
                var redios2 = document.getElementsByName("m1");
                if(redios1[0].checked){
                    showacclocation();
                    if(redios2[0].checked){
                        showacc();
                    }
                    if(redios2[1].checked){
                        showbl();
                    }
                    if(redios2[2].checked){
                        showother();
                    }
                }
                if(redios1[1].checked){
                   showbllocation();
                }
                if(redios1[2].checked){
                   showotherlocation();
                }
                $('#chooseLocationWindow').dialog('open');
                return false;
            }
            function setLocation(){
                var redios1 = document.getElementsByName("locationpart");
                var redios2 = document.getElementsByName("m1");
                if(redios2[0].checked){
                    var r1 = document.getElementById("mm1").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 5,
                        row:{
                            value: r1 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseLocation();\">*更改位置信息*</a>"
                        }
                    });
                    $('#chooseLocationWindow').dialog('close');
                }
                if(redios2[1].checked){
                    var r2 = document.getElementById("mm2").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 5,
                        row:{
                            value: r2 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseLocation();\">*更改位置信息*</a>"
                        }
                    });
                    $('#chooseLocationWindow').dialog('close');
                }
                if(redios2[2].checked){
                    var r3 = document.getElementById("mm3").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 5,
                        row:{
                            value: r3 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseLocation();\">*更改位置信息*</a>"
                        }
                    });
                    $('#chooseLocationWindow').dialog('close');
                }
                if(redios1[1].checked){
                    var r4 = document.getElementById("bl1").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 5,
                        row:{
                            value: r4 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseLocation();\">*更改位置信息*</a>"
                        }
                    });
                    $('#chooseLocationWindow').dialog('close');
                }
                if(redios1[2].checked){
                    var r5 = document.getElementById("other1").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 5,
                        row:{
                            value: r5 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseLocation();\">*更改位置信息*</a>"
                        }
                    });
                    $('#chooseLocationWindow').dialog('close');
                }
                return false;
            }
            
            function chooseJudge(){
                $('#chooseJudgeWindow').dialog('open');
                return false;
            }
            function setJudge(){
                var jud = document.getElementsByName("judgepart");
                if(jud[0].checked){
                    var jud1 = document.getElementById("yes").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 6,
                        row:{
                            value: jud1 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseJudge();\">*更改选择Yes/No*</a>"
                        }
                    });
                    $('#chooseJudgeWindow').dialog('close');
                }
                if(jud[1].checked){
                    var jud2 = document.getElementById("no").value;
                    $('#adddevice').datagrid('updateRow',{
                        index: 6,
                        row:{
                            value: jud2 +"&nbsp;&nbsp;&nbsp;&nbsp;" + "<a href=\"#\" style=\"display:block;float:right\" onclick=\"return chooseJudge();\">*更改选择Yes/No*</a>"
                        }
                    });
                    $('#chooseJudgeWindow').dialog('close');
                }
                return false;
            }
            
            </script>
    </body>
</html>

