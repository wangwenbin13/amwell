<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销管理-广告配置</title>
<jsp:include page="../resource.jsp"/>
</head>
<script >
 function cutUrl(id){
 	var text = $("#"+id).attr("title");
 	if(text.length>30){
 		var sub = text.substring(0,30);
 		sub+="...";
 	}else{
 		sub=text;
 	}
 	$("#"+id).text(sub);
 }
</script>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;广告配置<span class="blue1 ml25"></span></p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">广告配置</a></li>
                </ul>
            </div>
   <div class="table-outline fl widthfull mt10">
    <div class="table-title">
         	<a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
         </div>
      <div class="table2-text sh-ttext">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="5%">客户端</th>
                      <th scope="col" width="8%">版本</th>
                      <th scope="col" width="10%">生效时间</th>
                      <th scope="col" width="10%">失效时间</th>
                      <th scope="col" width="10%">图片</th>
                      <th scope="col">链接地址</th>
                      <th scope="col" width="5%">广告状态</th>
                      <th scope="col" width="7%">处理人</th>
                      <th scope="col" width="11%">处理时间</th>
                      <th scope="col" width="7%">操作</th>
                    </tr>
                    
                    <!-- 没有数据 -->
                    <s:if test="list==null || list.isEmpty">
                    	<tr align="center" class="tr bg1">
							<td colspan="11">
								<p	class="f13 f-yahei gray4 t-c line26 mt10">
									<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span>
								</p>
							</td>
						</tr>
                    </s:if>
                    
                    <s:iterator value="list" var="ad" status="s">
	                    <tr align="center" class="tr bg1" adId="${ad.ad_id }">
							<td class="f-arial">${s.index+1+currentPageIndex}</td>
		                   	<td class="f-arial">		
		                   	    <s:if test="%{#ad.clientType==1}">Android</s:if>
		                   	    <s:if test="%{#ad.clientType==2}">IOS</s:if>
		                   	</td>
	                   		<td class="f-arial">${ad.versionNO }</td>
	                   		<td class="f-arial">${ad.effectiveTime }</td>
	                   		<td class="f-arial">${ad.expirationTime }</td>
	                   		<td class="f-arial"><p class="mt5 mb5"><img src="${ad.fullThumbnail}" width="200" height="100"/></p></td>
	                   		<td class="f-arial"><span id="url${s.index }" title="${ad.urlLink }">
	                   		<script type="text/javaScript">
	                   			cutUrl("url"+${s.index})
	                   		</script>
	                   		</span></td>
	                   		<td class="f-arial">
		                   		<s:if test="%{#ad.adStatus==0}"><em class="yellow1">待使用</em></s:if>
		                   		<s:if test="%{#ad.adStatus==1}"><em class="green1">使用中</em></s:if>
		                   		<s:if test="%{#ad.adStatus==2}"><em class="red1">已过期</s:if>
		                   		<s:if test="%{#ad.adStatus==4}"><em class="gray1">已关闭</s:if>
	                   		</td>
	                   		<td class="f-arial">${ad.userName }</td>
	                   		<td class="f-arial">${ad.operateOn}</td>
	                   		<th><s:if test="%{#ad.adStatus==1 || #ad.adStatus==0}">
	                   		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="openLine('${ad.ad_id }')">关闭</a>
	                   		</s:if>
	                   		<s:if test="%{#ad.adStatus==4 }">
	                   		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="closeLine('${ad.ad_id }')">开启</a>
	                   		</s:if>
	                   		
	                   		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="deleteAd('${ad.ad_id }')">删除</a>
	                   		</th>
	                   	</tr>
                   	</s:iterator>
                  </table>
                   
          	</div>
          	<!--Start page-->
                 <div class="page t-c mt20  fl widthfull" id="pageDiv">
	                   <s:if test="%{currentPageIndex!=0}">
	                  		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
                     		<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
	                   </s:if>
                       <ul id="pageNumDiv">
                       		<s:iterator value="page.pageList">
							<s:if test="field02==1">
								<b><span class="current"><s:property value="field01" />
								</span>
								</b>
							</s:if>
							<s:else>
								<li>
									<a href="javascript:void(0);" onclick="toPage(${field03});"><s:property value="field01" /></a>
								</li>
							</s:else>
						</s:iterator>
                       </ul>
                       <s:if test="%{page.pageCount!=0 && ((currentPageIndex/page.pageSize)+1)!=page.pageCount}">
		             		<a href="javascript:void(0);" onclick="toPage(${page.nextIndex});">下一页</a>
		             		<a href="javascript:void(0);" onclick="toPage(${page.lastIndex});">末页</a>
		             </s:if>
		             <s:if test="%{page.pageCount!=1 && page.pageCount!=0}">
		             		<span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onblur="toJudgePage(this.value);"/>页</span>
		             </s:if>
		             <s:if test="%{page.pageCount!=0}">
		                 <span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页]</span>
		             </s:if>
                 </div>
                  <!--End page-->
          </div>   
		</div>
	</div>
</div>
<input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
<form action="../adManageAction/getAdManageList.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="currentPageIndex" id="currentPageIndex"/>
</form> 
</body>
</html>
<script type="text/javascript">

//增加广告
function showAddPage()
{
	$("#iframe_right", parent.parent.window.document).attr("src","../adManageAction/addPageJump.action");
}

//关闭广告
function openLine(adId){
	parent.parent.showPopCommonPage("确定要关闭此广告吗？");
  	//解绑定，防止多次执行click事件
  	$("#commonSure",parent.parent.window.document).unbind('click');
  	//动态添加方法
 	 $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../adManageAction/closeState.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{adId:adId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("广告关闭成功!",true);
			        $("#iframe_right",parent.window.document).attr("src","../adManageAction/getAdManageList.action");
			    }else{
			       parent.parent.showRturnTip("广告关闭失败!",false);
			    }	
			},
			error:function(){
				parent.parent.showRturnTip("广告关闭失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  $("#commonCancel",parent.parent.window.document).click(function(){
  		parent.parent.closePopCommonPage();
  		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
 
}

//开启广告
function closeLine(adId){
	parent.parent.showPopCommonPage("确定要开启此广告吗？");
  	//解绑定，防止多次执行click事件
  	$("#commonSure",parent.parent.window.document).unbind('click');
  	//动态添加方法
 	 $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../adManageAction/openState.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{adId:adId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("广告开启成功!",true);
			       $("#iframe_right",parent.window.document).attr("src","../adManageAction/getAdManageList.action");
			    }else{
			       parent.parent.showRturnTip("广告开启失败!",false);
			    }	
			},
			error:function(){
				parent.parent.showRturnTip("广告开启失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  $("#commonCancel",parent.parent.window.document).click(function(){
  		parent.parent.closePopCommonPage();
  		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
}

//删除广告
function deleteAd(adId){
	parent.parent.showPopCommonPage("确定删除此广告吗？");
  	//解绑定，防止多次执行click事件
  	$("#commonSure",parent.parent.window.document).unbind('click');
  	//动态添加方法
 	 $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../adManageAction/deleteAdvert.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{adId:adId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("广告删除成功!",true);
			       $("#iframe_right",parent.window.document).attr("src","../adManageAction/getAdManageList.action");
			    }else{
			       parent.parent.showRturnTip("广告删除失败!",false);
			    }	
			},
			error:function(){
				parent.parent.showRturnTip("广告删除失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  $("#commonCancel",parent.parent.window.document).click(function(){
  		parent.parent.closePopCommonPage();
  		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
}

//广告详情
$("#tableDiv tr td").click(function(){
	//如果是没有数据就不调用后面的方法
	if($("#noDate").html()!= undefined){	
		return false;
	}
	//获取选中文字，如果选中文字则不响应点击事件
	//ie8写法
	if(typeof(document.selection) != "undefined")
	{
		var selecter = document.selection.createRange().text;
	}
	else if(typeof(window.getSelection()) != "undefined")
	{
		var selecter = window.getSelection();
	}
	else
	{
		var selecter = "";
	}
	if(selecter != "")
	{
	 	return false;
	}
	else
	{
		var adId = $(this).parent().attr("adId");
		//跳转到商户详情
		$("#iframe_right", parent.parent.window.document).attr("src","../adManageAction/getAdManageDetails.action?adId="+adId+"&currentPageIndex="+$("#myCurrentPageIndex").val());
	}
});

//翻页方法
function toPage(value){
	$("#currentPageIndex").val(value);
	$("#turnPage").submit();
}

//判断输入的参数是否符合规定
function toJudgePage(value){
	var totalPage = "${page.pageCount}";
	totalPage = parseInt(totalPage);
	if(""==value){
		return;
	}
	value = parseInt(value);
	if(value<1){
		parent.parent.showCommonTip("请输入大于0的数!");
		return;
	}else if(value>totalPage){
		parent.parent.showCommonTip("超出最大页数!");
		return;
	}
	var pageSize = "${pageSize}";
	toPage((value-1)*pageSize);
}

/*分页enter按键处理函数*/
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		var currPage = $("#goPageNum").val();
        //输入为空不跳转
        if(currPage == "" || typeof(currPage) == "undefined")
    	{
    		return false;
    	}
	    toJudgePage(currPage);
		break;
	default:
		break;
	}
}

var ua = window.navigator.userAgent;
//判断浏览器类型
if (ua.indexOf("MSIE") >= 1 || ua.indexOf("Chrome") >= 1){
	//禁止后退键  作用于IE、Chrome  
	document.onkeydown = pressKey;
}else if(ua.indexOf("Firefox") >= 1){
	//禁止后退键 作用于Firefox、Opera  
	document.onkeypress = pressKey;  
}else{
	document.onkeypress = pressKey;  
}

//检查跳页是否大于总页数
function checkGoPageNum(pageNum)
{
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(parseInt(pageNum) > parseInt(totalPage))
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}
</script>