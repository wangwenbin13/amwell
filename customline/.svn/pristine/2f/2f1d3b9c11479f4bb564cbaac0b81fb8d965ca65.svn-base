<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户、订单管理-改签-订单列表</title>
<jsp:include page="../resource.jsp"/>
<jsp:include page="../public_select.jsp"/>
<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
/* box */
.showbox{width:0px;height:0px;display:none;position:absolute;right:0;top:0;z-index:100;background:#fff;}
.showbox h2{height:30px;font-size:14px;background-color:#0093dd;position:relative;line-height:30px;color:#fff;border:1px #8FA4F5 solid;border-bottom:0;padding:1px 0 0 10px;}
.showbox h2 a{position:absolute;right:5px;top:0;font-size:12px;color:#fff;}
.showbox .mainlist{padding:10px 10px 20px;}
.showbox .mainlist li{height:24px;line-height:24px;}
.showbox .mainlist li span{margin:0 5px 0 0;font-family:"宋体";font-size:12px;font-weight:400;color:#ddd;}
.showbox .mainlist li a{font-size:12px;}
.mask{background:#666;position:absolute;z-index:99;left:0;top:0;display:none;width:100%;height:100%;opacity:0.5;filter:alpha(opacity=50);-moz-opacity:0.5;}
.showboxContent{height:auto;background:#fff;border:1px #8FA4F5 solid;border-top:0;padding:1px;}
.textarea{resize:none;width:100%;height:80px;text-index:5px;padding-top:5px;}
</style>
</head>

<body>
  <div class="mt10 black1">
  	<form action="../changeTicket/userChangeTicket.action" method="post" id="reload_form">
	  <ul class="r-sub-sel black1 mt20 clearfix">
	        <li>
	        	<span class="fl">订单号：</span><input type="text" class="fl r-input gray2 w180 mt2" name="search.field01" value="${search.field01 }"/>   
	        </li>  
	        <li>
	        	<span class="fl">联系方式：</span><input type="text" class="fl r-input gray2 w180 mt2" name="search.field02" value="${search.field02 }"/>   
	        	<input type="submit" class="btn-blue4 ml20" value="查找" />
	        </li> 
	  	</ul>  
	  </form> 
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
            <table width="100%" border="0" class="table1">
              <tr align="center">
              	<th scope="col" width="4%">序号</th>
                <th scope="col" width="12%">订单号</th>
                <th scope="col" width="10%">乘客</th>
                <th scope="col" width="10%">联系方式</th>
                <th scope="col" width="10%">线路名称</th>
                <th scope="col">申请时间</th>
                <th scope="col">改签前&nbsp;->&nbsp;改签后</th>
                <th scope="col" width="10%">操作</th>
              </tr>
              
               <s:if test="%{page.pageCount == 0}">
                  <tr align="center" class="bg1">
              		<td colspan="13"><p class="f13 f-yahei gray4 t-c line26 mt10">
              			<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              	</tr>
             	</s:if>
              
              	<s:iterator value="list" var="vo" status="s">
		            <s:if test="%{#s.count%2 == 1}">
						<tr align="center" class="tr bg1">
					</s:if>
					<s:if test="%{#s.count%2 == 0}">
						<tr align="center" class="tr bg2">
					</s:if>
					<td>${s.index+1}</td>
					<td class="f-arial">${vo.leaseOrderNo }</td>
					<td class="f-arial">${vo.nickName }</td>                    	
	               	<td class="f-arial">${vo.telephone }</td>
	               	<td class="f-arial">${vo.lineName }</td>
	               	<td class="f-arial">${vo.cdate }</td>
	               	<td class="f-arial">
	               	   ${vo.oldTime}<span class="f14 ml4 mr4 fw f-yahei">-></span>${vo.newTime }
	               	</td>
	       	        <td class="f-arial">
	       	        	<a href="javascript:void(0);" class="blue1 mr10" onclick="checkChange('shenhe','${vo.localId}','${vo.telephone }','${vo.lineClassId }','${vo.leaseOrderNo }','${vo.nickName }','${vo.lineName }','${ vo.oldTime}','${vo.newTime }')">通过审核</a><a href="javascript:void(0);" class="blue1" onclick="checkChange('juqian','${vo.localId}','${vo.telephone }')">拒签</a>
	       	        </td>
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
<form action="../changeTicket/userChangeTicket.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="search.field01" value="${search.field01}"/>
  <input type="text" name="search.field02" value="${search.field02}"/>
  <input type="text" name="currentPageIndex" id="currentPageIndex"/>
</form> 

 <!-- 搜索内容 -->
<!-- 班次 -->
<div class="hide">
  <select id="selectClass">
  </select>
</div>

<div class="showbox">
	<h2>审核通过<a class="close" href="#">关闭</a></h2>
	<div class="showboxContent">
		<div class="mainlist">
	   		<input type="hidden" id="localId_input"/>
	   		<input type="hidden" id="type_input"/>
	   		<input type="hidden" id="telephone_input"/>
	   		<input type="hidden" id="lineClassId_input"/>
	   		<input type="hidden" id="leaseOrderNo_input"/>
	   		<p class="f14"></p>
			<div class="mt10" style="height:auto;line-height:25px;font-size:14px;">
			<!-- 审核 start -->
				<div id="shenhe" style="display: none;">
					<p class="pop-main-tips "><span id="nickName_span">乘客：122</span><span class="ml20" id="telephone_span">手机号：13758996895</span><span class="ml20" id="lineName_span">班线：122</span></p>
					<p class="mt5">申请改签的时间为：</p>
					<p class="ml20"><span id="oldTime_span">2015-04-28&nbsp;08:00</span><span class="ml4 mr4 fw f-yahei">-></span><span id="newTime_span">2015-04-28&nbsp;08:00</span></p>
					<p class="mt5 fw">是否通过审核？</p>
				</div>
				<!-- 审核 end -->
				<!-- 拒签 start -->
				<div id="juqian" style="display: none;">
					<p class="pop-main-tips ">请输入拒签理由，该信息会以站内信和消息推送的方式发送给用户(100字以内)</p>
					<textarea class="mt10 textarea" id="juqian_textare" onkeydown="textCounts();" onkeyup="textCounts();"></textarea>
					<!-- 拒签 end -->
				</div>
			</div>
	   	</div>
	   	<p class="t-c mt10 mb20">
		    <a class="display-ib btn1 white mr40 f14" href="javascript:void(0);" onclick="onSuer();">确定</a>
		   	<a class="display-ib btn1 white f14 close" href="javascript:void(0);">取消</a>
	   	</p>
   	</div>
</div>

  
</body>


<script type="text/javascript">

//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}

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


//改签审核弹窗
function checkChange(type,localId,telephone,lineClassId,leaseOrderNo,nickName,lineName,oldTime,newTime){
		var box =300;
		var th= $(window).scrollTop()+$(window).height()/1.6-box;
		var h =document.body.clientHeight;
		var rw =$(window).width()/2-box;
		
		$("#nickName_span").html("乘客："+nickName);
		$("#telephone_span").html("手机号："+telephone);
		$("#lineName_span").html("班线："+lineName);
		$("#oldTime_span").html(oldTime);
		$("#newTime_span").html(newTime);
		$("#localId_input").val(localId);
		$("#type_input").val(type);
		$("#telephone_input").val(telephone);
		$("#lineClassId_input").val(lineClassId);
		$("#leaseOrderNo_input").val(leaseOrderNo);
		if('shenhe'==type){
			$("#shenhe").show();
			$("#juqian").hide();
		}else if('juqian'==type){
			$("#shenhe").hide();
			$("#juqian").show();
		}
		$(".showbox").animate({top:th,opacity:'show',width:580,height:150,right:rw},500);
		$("body").prepend("<div class='mask'></div>");
		$(".mask").css({opacity:"0.5"}).css("height",h);
		return false;
}
$(".showbox .close").click(function(){
	$(this).parents(".showbox").animate({top:0,opacity: 'hide',width:0,height:0,right:0},500);
	$(".mask").fadeOut("fast");
	return false;
});

//关闭弹出层
function closeWindow(){
	$(".showbox").animate({top:0,opacity: 'hide',width:0,height:0,right:0},500);
	$(".mask").fadeOut("fast");
}

//确定按钮
function onSuer(){
	var localId =$("#localId_input").val();
	var type = $("#type_input").val();
	var telephone = $("#telephone_input").val();
	if("juqian"==type){
		//拒绝改签
		var refusemess = $("#juqian_textare").val();
		if(''==refusemess.trim()){
			parent.parent.showPopCommonPage2("请填写拒绝理由");
		}else{
			$.ajax({
				url:'../changeTicket/refuceReturnTicket.action',
				data:{localId:localId,refusemess:refusemess,telephone:telephone},
				type:'post',		
				cache:false,	
				dataType:"json",	
				success:function(data){
					if("1"==data){
						parent.parent.showRturnTip("保存成功",true);
						closeWindow();
					}else{
						parent.parent.showRturnTip("保存失败",false);
					}
					$("#reload_form").submit();
				}
			});
		}
	}else{
		//执行改签
		var lineClassId = $("#lineClassId_input").val();
		var leaseOrderNo = $("#leaseOrderNo_input").val();
		var oldTime = $("#oldTime_span").html();
		var newTime = $("#newTime_span").html();
		$.ajax({
			url:'../changeTicket/doReturnTicket.action',
			data:{
				"changeVo.localId":localId,
				"changeVo.lineClassId":lineClassId,
				"changeVo.telephone":telephone,
				"changeVo.leaseOrderNo":leaseOrderNo,
				"changeVo.oldTime":oldTime,
				"changeVo.newTime":newTime
				},
			type:'post',		
			cache:false,	
			dataType:"json",	
			success:function(data){
				if("1"==data){
					parent.parent.showRturnTip("保存成功",true);
					closeWindow();
					$("#reload_form").submit();
				}else if("-3"==data){
					parent.parent.showRturnTip("没有余座,不能改签",false);
				}else{
					parent.parent.showRturnTip("保存失败",false);
				}
			}
		});
	}
}

//字数控制
function textCounts(){
 	var maxlimit = 100;
 	var count=0;	//计算字符数
 	var content=$("#juqian_textare").val();
	var char=0;		//计算剩余字符数	
 	for(var i=0;i<content.length;i++){
		var ask11=content[i].charCodeAt();
		if(0<=ask11 && ask11<161){
			count++;
			if(count<maxlimit){
			char++;
			}
		}else{
			count+=2;
			if(count<maxlimit){
			char++;
			}
		}
	}
	if(count>maxlimit){
		content = content.substring(0, char);
		
		$("#juqian_textare").val(content);
	}
 	
 	
 	
 }
</script>
</html>