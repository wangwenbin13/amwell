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
<input type="hidden" name="storeId" value="${setting.storeId}"/>
<table>
    <tr>
        <th>开始日期：</th>
        <td><input type="text" name="startTime" value="${setting.startTime}"/></td>
    </tr>
    <tr>
        <th>结束日期：</th>
        <td><input type="text" name="endTime" value="${setting.endTime}"/></td>
    </tr>
    <tr>
        <th></th>
        <td><input type="button" value="提交" onclick="submitForm()"/></td>
    </tr>
</table>
</form>
</body>
</html>
<script type="text/javascript" src="../js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/common/common_util.js"></script>
<script type="text/javascript">
function submitForm(){
	leasePostAjax("../games/beatboss/setting",$("#settingForm"),"json",function(data){
        if(data=="success"){
            alert("保存成功。");
        }else{
            alert("保存失败。");
        }
    });
}
</script>