<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta name="viewport" content="width=device-width;initial-scale=1.0;scalable=0;">
</head>
<body>
<form id="settingForm">
<input type="hidden" name="storeId"/>
<table>
    <tr>
        <th>开始日期：</th>
        <td><input type="text" name="startTime"/></td>
    </tr>
    <tr>
        <th>结束日期：</th>
        <td><input type="text" name="endTime"/></td>
    </tr>
    <tr>
        <th></th>
        <td><input type="button" value="提交" id="submitButton"/></td>
    </tr>
</table>
</form>
</body>
</html>
<script type="text/javascript" src="../js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/common/common_util.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	loadSetting();
	$("#submitButton").bind("click",submitForm);
});

function loadSetting(){
	leaseGetAjax("../fczSetting/getFczSetting","json",function(data){
         if(data.a1=="100"){
             var fczSetting = data.a2;
             if(fczSetting!=null){
                 $("[name='startTime']").val(fczSetting.startTime);
                 $("[name='endTime']").val(fczSetting.endTime);
             }
         }else{
             alert("加载数据失败。");
         }
    });
}

function submitForm(){
	leasePostAjax("../fczSetting/saveFczSetting",$("#settingForm"),"json",function(data){
        if(data.a1=="100"){
            alert("保存成功。");
        }else{
            alert("保存失败。");
        }
    });
}
</script>