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
        <div class="pop-t-r fr"><a class="pop-close fr mt4 mr4" href="javascript:void(0)" onclick="loadPage.window.closePopAddPage();"></a></div>
    </div>
    <div class="pop-main p10">
    	<p class="gray2">（图片大小不超过500k；支持JPG、JPEG、PNG；分辨率为800*600）</p>
        <ul class="pop-lilist mt15 clearfix">	
           
          	<li class="mr120 line24"><span class="t-r w95 fl">图片1：</span>
        		<span id="pic1" class="fl gray2"><a href='javascript:void(0)' onclick='showImage(filePath1);'>${picUrlList[0]}</a></span>
        		<span class="p-r file-box fl">
        		    <input id="filePath1" type="hidden" name="filePath1" value="${picUrlList[0]}"> 
        		    <input id="absoluteFilePath1" type="hidden" name="absoluteFilePath1"> 
	        		<input id="file1" type="file" name="photo" class="file p-a fl"  onchange="uploadFile('file1','pic1','filePath1','absoluteFilePath1');">
	                <a class="blue1 ml4" href="javascript:void(0)">替换图片</a>
                </span>
                <a href="javascript:void(0)" class="blue1 ml20" onclick="loadPage.window.deleteFile('file1','pic1','filePath1','absoluteFilePath1');">删除</a>
          	</li>
          	<li class="mr120 line24"><span class="t-r w95 fl">图片2：</span>
        		<span id="pic2" class="fl gray2"><a href='javascript:void(0)' onclick='showImage(filePath2);'>${picUrlList[1]}</a></span>
        		<span class="p-r file-box fl">
        		    <input id="filePath2" type="hidden" name="filePath2" value="${picUrlList[1]}"> 
        		    <input id="absoluteFilePath2" type="hidden" name="absoluteFilePath2"> 
	        		<input id="file2" type="file" name="photo" class="file p-a fl"  onchange="uploadFile('file2','pic2','filePath2','absoluteFilePath2');">
	                <a class="blue1 ml4" href="javascript:void(0)">替换图片</a>
                </span>
                <a href="javascript:void(0)" class="blue1 ml20" onclick="loadPage.window.deleteFile('file2','pic2','filePath2','absoluteFilePath2');">删除</a>
          	</li>
          	<li class="mr120 line24"><span class="t-r w95 fl">图片3：</span>
          	    <span id="pic3" class="fl gray2"><a href='javascript:void(0)' onclick='showImage(filePath3);'>${picUrlList[2]}</a></span>
        		<span class="p-r file-box fl">
        		    <input id="filePath3" type="hidden" name="filePath3" value="${picUrlList[2]}"> 
        		    <input id="absoluteFilePath3" type="hidden" name="absoluteFilePath3"> 
	        		<input id="file3" type="file" name="photo" class="file p-a fl" onchange="uploadFile('file3','pic3','filePath3','absoluteFilePath3');">
	                <a class="blue1 ml4" href="javascript:void(0)">替换图片</a>
                </span>
                <a href="javascript:void(0)" class="blue1 ml20" onclick="loadPage.window.deleteFile('file3','pic3','filePath3','absoluteFilePath3');">删除</a>
          	</li>      
          	<li class="mr120 line24"><span class="t-r w95 fl">图片4：</span>
          	    <span id="pic4" class="fl gray2"><a href='javascript:void(0)' onclick='showImage(filePath4);'>${picUrlList[3]}</a></span>
        		<span class="p-r file-box fl">
        		    <input id="filePath4" type="hidden" name="filePath4" value="${picUrlList[3]}"> 
        		    <input id="absoluteFilePath4" type="hidden" name="absoluteFilePath4"> 
	        		<input id="file4" type="file" name="photo" class="file p-a fl" onchange="uploadFile('file4','pic4','filePath4','absoluteFilePath4');">
	                <a class="blue1 ml4" href="javascript:void(0)">替换图片</a>
              </span>
              <a href="javascript:void(0)" class="blue1 ml20" onclick="loadPage.window.deleteFile('file4','pic4','filePath4','absoluteFilePath4');">删除</a>
          	</li>          
        </ul>   
        <p class="t-c mb20 mt20">
	        <input type="button" value="确&nbsp;&nbsp;定" class="display-ib btn1 white f14 mr20" onclick="loadPage.window.confirm();"/>
	        <input type="button" value="取&nbsp;&nbsp;消" class="display-ib btn1 white f14" onclick="loadPage.window.closePopAddPage();"/>
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

function deleteFile(fileId,picId,filePathId,absoluteFilePath){
  //清除字段值
  $("#"+fileId,parent.window.document).val("");
  $("#"+picId,parent.window.document).html("");
  $("#"+picId,parent.window.document).text("");
  $("#"+filePathId,parent.window.document).val("");
  $("#"+absoluteFilePath,parent.window.document).val("");
  //显示添加图片，隐藏删除
  
}

function confirm(){
    
    var filePath1 = $("#filePath1",parent.window.document).val();
    var filePath2 = $("#filePath2",parent.window.document).val();
    var filePath3 = $("#filePath3",parent.window.document).val();
    var filePath4 = $("#filePath4",parent.window.document).val();
    var filePath = filePath1+","+filePath2+","+filePath3+","+filePath4;
    $("#"+id).val(filePath);
    closePopAddPage();
    
}
//关闭增加弹出层页面
function closePopAddPage()
{
    $("#popAddPage",parent.window.document).hide();
    $("#mainBody",parent.window.document).hide();
    $("#topHide", parent.parent.window.document).hide();
    $("#leftHide", parent.parent.window.document).hide();
}
//判断是否是IE8浏览器
if(navigator.userAgent.indexOf("MSIE")>0)  
{   
    //ie8
    if(navigator.userAgent.indexOf("MSIE 8.0")>0)  
    {  
		$(".file-box",parent.window.document).css({"width":70+"px"});
		$(".file",parent.window.document).css({"top":2+"px"});
    }   
    else  
	{  
    	$(".file-box",parent.window.document).css({"width":70+"px"});
		$(".file",parent.window.document).css({"top":2+"px"});
	}
}
else  
{  
	$(".file-box",parent.window.document).css({"width":70+"px"});
	$(".file",parent.window.document).css({"top":2+"px"});
} 

</script>
