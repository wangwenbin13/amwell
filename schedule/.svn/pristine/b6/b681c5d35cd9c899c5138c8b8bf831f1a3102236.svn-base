<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调度平台－线路详情-查看站点图片</title>

</head>
<body>
<div class="popMain black1" id="popMonthPage" style="width:500px;">
    <div class="popMain-t fw">查看站点图片<a href="javascript:void(0);" onclick="closeViewPrice();" class="fr white1 f-arial" title="关闭">X</a></div>
    <div class="p-r popMain-main">
    	<span class="p-a arrowPic prevPic hide" id="prevPic"></span>
    	<div class="viewPicListDiv">
	    	<!-- 没有数据 start -->	
			<div class="t-c hide" id="noPicListDate">
				<img src="../images/noDate.png" class="mt35" width="169" height="169" alt="暂无数据" />
				<p class="mt15 mb20"><span class="f18 f-yahei">暂无图片</span></p>
			</div>
			<!-- 没有数据 end -->
	    	<ul class="viewPicList clearfix" id="viewPicList"></ul>
    	</div>
    	<span class="p-a arrowPic nextPic" id="nextPic"></span>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
//站点图片切换
$(function(){
	//线路名称
	var name = "<%=request.getParameter("name")%>";
	loadDate(unescape(name));
});

//加载数据
function loadDate(name)
{
	var url = "../line/getStationPics.action?name="+encodeURI(encodeURI(name));
  	$.ajax({
		url:url,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			if(data.length == 0)
			{
				$("#noPicListDate",parent.window.document).show();
				$("#viewPicList,#prevPic,#nextPic",parent.window.document).hide();
			}
			else
			{
				$("#noPicListDate,#prevPic",parent.window.document).hide();
				$("#viewPicList,#nextPic",parent.window.document).show();
				initLi("viewPicList",data);
			}
		},
		error:function()
		{
			$("#noPicListDate",parent.window.document).show();
			$$("#viewPicList,#prevPic,#nextPic",parent.window.document).hide();
		}
	});
}

//创建li数据
function initLi(id,jsonDate)
{
	$("#"+id,parent.window.document).children("li").remove(); //除第一行外删除所有的tr
	for(var i = 0;i < jsonDate.length;i++)
	{
		$("#"+id,parent.window.document).append("<li class='p10'><img src='"+jsonDate[i]+"'/></li>");
	}
	
	$(".viewPicList li",parent.window.document).hover(function(){
		var $showDiv = $(".viewPicList",parent.window.document);
		var liW = $(".viewPicList li",parent.window.document).width()+20;
		var len = $(".viewPicList li",parent.window.document).length;

		if(len == 1){
			$(".prevPic",parent.window.document).hide();
			$(".nextPic",parent.window.document).hide();
		}
		else{
			var n=1;
			$(".nextPic",parent.window.document).click(function(){
				$(".prevPic",parent.window.document).show();
				if( !$showDiv.is(":animated") ){
					if(n == (len-1)){
						$(".nextPic",parent.window.document).hide();
						$showDiv.animate({ marginLeft : -liW*n });
					}else{
						$showDiv.animate({ marginLeft : '-='+liW }, 100);
						n++;
					 }
				}
			})
			$(".prevPic",parent.window.document).click(function(){
				$(".nextPic",parent.window.document).show();
				if( !$showDiv.is(":animated") ){
					if(n == 1){
						$(".prevPic",parent.window.document).hide();
						$showDiv.animate({ marginLeft : 0 });
					}else{
						$showDiv.animate({ marginLeft : '+='+liW }, 100);
						n--;
					}
				}
			})
		}
	});
}
</script>