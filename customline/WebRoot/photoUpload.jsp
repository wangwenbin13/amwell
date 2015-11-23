<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'photoUpload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <img id="driving_licence" />
  	<p class="fl mt10">
       <span class="fl xinxi-file p-r"><input class="file p-a" type="file" name="photo" id="driving_licence_photo" onchange="doUpload2(this);"/>
       <a href="javascript:;void(0)" class="btn-blue display-ib white">上传照片</a></span>
       <span class="gray2 fl mt5">支持JPG、GIF、PNG，文件大小&lt;500K</span>
   </p>
      <input type="hidden" name="drivingURL" id="drivingURL"/>
  <body>
    This is my JSP page. <br>
  </body>
  <script type="text/javascript" src="<%=basePath %>js/jquery/jquery-1.8.2.min.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
  <script type="text/javascript">
//异步上传图片
function doUpload2(obj)
{
	var value = $("#driving_licence_photo").val();
	if(""==value){
		return;
	}
	value = value.split(".");
	value = value[value.length-1];
	if(value!=("jpg") && value!=("png") && value!=("jpeg")){
		alert("格式不正确!");
		$("#input_icon_"+i).val("");
		return;
	}
	$("#driving_licence").hide();
	$("#driving_licence_img").show();
	$.ajaxFileUpload({
			//dataType:'text',
			fileElementId:'driving_licence_photo', 
			secureuri:false,
			dataType: 'text',
			url : 'ftpUploadAction/upLoad.action',
			success : function(data) {
				if (data == "error") {
				}else if(data=="overSize"){
					//图片过大，请上传不大于500K的图片
					alert("图片过大，请上传不大于500K的图片");
				}else{
					data = eval("(" + data + ")");
					$("#driving_licence").show();
					$("#driving_licence_img").hide();
					document.getElementById("driving_licence").src = data.downFileUrl;
					$("#drivingURL").val(data.dbFileUrl);
				}
			},
			error:function(){
			}
     });
	
}
  </script>
</html>
