<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－创建线路-添加站点图片</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
  </head>
  
  <body>
    <div id="showPage" class="showPage"></div>
    <div class="pop black1 w500" id="popAddPage">
	<div class="pop-t">
    	<div class="pop-t-l fl fw white">添加图片</div>
        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onClick="closePopAddPage();"></a></div>
    </div>
    <div class="pop-main p10">
    	<p class="gray2">（图片大小不超过500k；支持GIF、BMP、JPG、JPEG、PNG；）</p>
        <ul class="pop-lilist mt15 clearfix">	
          
          	<li class="mr120 line24"><span class="t-r w95 fl">图片1：</span>
        		<span id="pic1" class="fl gray2"></span>
        		<span class="p-r file-box fl">
        		    <input id="filePath1" type="hidden" name="filePath1"> 
        		    <input id="absoluteFilePath1" type="hidden" name="absoluteFilePath1"> 
	        		<input id="file1" type="file"  name="photo" class="file p-a fl"  onchange="uploadFile('file1','pic1','filePath1','absoluteFilePath1');">
	                <a class="blue1 ml4" href="javascript:void(0)">添加图片</a>
                </span>
                <a href="javascript:void(0)" class="blue1 ml20 hide" onClick="deleteFile('file1','pic1','filePath1','absoluteFilePath1');">删除</a>
          	</li>
          	<li class="mr120 line24"><span class="t-r w95 fl">图片2：</span>
        		<span id="pic2" class="fl gray2"></span>
        		<span class="p-r file-box fl">
        		    <input id="filePath2" type="hidden" name="filePath2"> 
        		    <input id="absoluteFilePath2" type="hidden" name="absoluteFilePath2"> 
	        		<input id="file2" type="file" name="photo" class="file p-a fl"  onchange="uploadFile('file2','pic2','filePath2','absoluteFilePath2');">
	                <a class="blue1 ml4" href="javascript:void(0)">添加图片</a>
                </span>
                <a href="javascript:void(0)" class="blue1 ml20 hide" onClick="deleteFile('file2','pic2','filePath2','absoluteFilePath2');">删除</a>
          	</li>
          	<li class="mr120 line24"><span class="t-r w95 fl">图片3：</span>
          	    <span id="pic3" class="fl gray2"></span>
        		<span class="p-r file-box fl">
        		    <input id="filePath3" type="hidden" name="filePath3"> 
        		    <input id="absoluteFilePath3" type="hidden" name="absoluteFilePath3"> 
	        		<input id="file3" type="file" name="photo" class="file p-a fl" onChange="uploadFile('file3','pic3','filePath3','absoluteFilePath3');">
	                <a class="blue1 ml4" href="javascript:void(0)">添加图片</a>
                </span>
                <a href="javascript:void(0)" class="blue1 ml20 hide" onClick="deleteFile('file3','pic3','filePath3','absoluteFilePath3');">删除</a>
          	</li>      
          	<li class="mr120 line24"><span class="t-r w95 fl">图片4：</span>
          	    <span id="pic4" class="fl gray2"></span>
        		<span class="p-r file-box fl">
        		    <input id="filePath4" type="hidden" name="filePath4"> 
        		    <input id="absoluteFilePath4" type="hidden" name="absoluteFilePath4"> 
	        		<input id="file4" type="file" name="photo" class="file p-a fl" onChange="uploadFile('file4','pic4','filePath4','absoluteFilePath4');">
	                <a class="blue1 ml4" href="javascript:void(0)">添加图片</a>
              </span>
              <a href="javascript:void(0)" class="blue1 ml20 hide" onClick="deleteFile('file4','pic4','filePath4','absoluteFilePath4');">删除</a>
          	</li>          
        </ul>   
        <p class="t-c mb20 mt20">
	        <input type="button" value="确&nbsp;&nbsp;定" class="display-ib btn1 white f14 mr20" onClick="confirm();"/>
	        <input type="button" value="取&nbsp;&nbsp;消" class="display-ib btn1 white f14" onClick="closePopAddPage();"/>
        </p>  
    </div>
</div>
  </body>
</html>
<script type="text/javascript">
var id = null;
$(function(){
	id = "<%=request.getParameter("id")%>";
	
});
function uploadFile(fileId,picId,filePathId,absoluteFilePath){
    var value = $("#"+fileId).val();
	if(""==value||value.indexOf(".")==-1){
		return;
	}
	/*var arr = value.split(".");
	var suffix = arr[arr.length-1];
	if(suffix!=("jpg") && suffix!=("png") && suffix!=("jpeg")){
		parent.showRturnTip(validateJson.ImgFile.tip,false); 
		return;
	}*/
	var reg = validateJson.ImgFile.reg;
	var thisReg = new RegExp(reg);
	if(!thisReg.test(value))
	{
		parent.showRturnTip(validateJson.ImgFile.tip,false); 
		return;
	}
	parent.popLodingPage(true);
	$.ajaxFileUpload({
			
			fileElementId:fileId, 
			secureuri:false,
			dataType: 'text',
			url : 'ftpUploadAction/upLoad.action?uploadFileType=1',
			success : function(data) {
				parent.popLodingPage(false);
				if (data == "error") {
					parent.showRturnTip("上传失败!",false); 
				}else if(data=="overSize"){
					//图片过大，请上传不大于500K的图片
					parent.showRturnTip("图片过大，请上传不大于500K的图片",false); 
				}else{
					data = eval("(" + data + ")");
					//显示原来的图片名称
					var pathArr = value.split("\\");
					var fileName = pathArr[pathArr.length-1];
					//相对路径
					$("#"+filePathId).val(data.dbFileUrl);
					//绝对路径
					$("#"+absoluteFilePath).val(data.downFileUrl);
					
					$("#"+fileId).parent(".file-box").hide().next("a").show();
					
					var imgUrl = "<a href='javascript:void(0)' onclick='showImage("+filePathId+");'>"+fileName+"</a>";
					
					$("#"+picId).html(imgUrl);
				}
			},
			error:function(){
				parent.showRturnTip("上传失败!",false); 
			}
     });
	
}

function deleteFile(fileId,picId,filePathId,absoluteFilePath){
  //清除字段值
  $("#"+fileId).val("");
  $("#"+picId).html("");
  $("#"+filePathId).val("");
  $("#"+absoluteFilePath).val("");
  //显示添加图片，隐藏删除
  $("#"+fileId).parent(".file-box").show().next("a").hide();
}

function showImage(filePath){
   var filePathValue = $(filePath).val();
   
   $("#showPagePic").load("line/showImage.action?random="+Math.floor(Math.random()*10000+1),{filePath:filePathValue}); 
}

function confirm(){
    
    var filePath1 = $("#filePath1").val();
    var filePath2 = $("#filePath2").val();
    var filePath3 = $("#filePath3").val();
    var filePath4 = $("#filePath4").val();
    var filePath = filePath1+","+filePath2+","+filePath3+","+filePath4;
    
    $("#"+id).val(filePath).removeClass("gray3").addClass("gray2");
    closePopAddPage();
    
}
//关闭增加弹出层页面
function closePopAddPage()
{
    $("#popAddPage").hide();
    $("#mainBody").hide();
    $("#topHide", parent.window.document).hide();
    $("#leftHide",parent.window.document).hide();
}

//判断是否是IE8浏览器
if(navigator.userAgent.indexOf("MSIE")>0)  
{   
    //ie8
    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
    {  
		$(".file-box").css({"width":70+"px"});
		$(".file").css({"top":2+"px"});
    }   
    else  
	{  
    	$(".file-box").css({"width":70+"px"});
		$(".file").css({"top":2+"px"});
	}
}
else  
{  
	$(".file-box").css({"width":70+"px"});
	$(".file").css({"top":2+"px"});
} 

</script>
