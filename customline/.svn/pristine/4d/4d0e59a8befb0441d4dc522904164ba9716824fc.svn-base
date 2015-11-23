<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-招募线路-招募线路列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
   <s:form action="../line/getRecruitLines.action" method="post" theme="simple" id="searchForm">
   	 <ul class="r-sub-sel black1 mt20 clearfix">       	
        <li class="w17p">线路名称：<s:textfield name="search.field01" cssClass="r-input w63p gray2"/></li>
        <li class="w17p">站点：<s:textfield name="search.field02" cssClass="r-input w67p gray2"/></li>        
        <li class="w17p">状态：
        	<s:select name="search.field03" list="#{'':'所有状态',2:'关闭',1:'招募中'}"  listKey="key" listValue="value" cssClass="p3 w67p"/>
        </li>
        <li class="w30p"><span class="t-r w65 fl">省份城市：</span>
	        <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" name="search.field11" cssClass="fl r-input mr10 w36p" onchange="loadCity(this.value);"/>
	        <s:select list="#{'':'--选择城市--'}" name="search.field12" id="cityCode" cssClass="fl r-input w36p"></s:select>
	        <input type="hidden" id="theSelectedCityCode" value="${search.field12}"/>
        </li>
        <li><s:submit value="查找" cssClass="btn-blue4"/><s:reset value="重置" cssClass="btn-blue4" onclick="clearFormValue('searchForm')"/></li>
  	</ul>   
  </s:form> 
      <div class="table2-text sh-ttext mt10">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="7%">线路类型</th>
                      <th scope="col" width="10%">线路名称</th>
                      <th scope="col" width="8%">城市名称</th>
                      <th scope="col">起点/终点</th>
                      <th scope="col" width="6%">价格</th>
                      <th scope="col" width="15%">更新时间</th>
                      <th scope="col" width="6%">报名人数</th>
                      <th scope="col" width="6%">操作人</th>
                      <th scope="col" width="6%">招募状态</th>
                      <th scope="col" width="13%">操作</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="list" var="recruitLine" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" lineBaseId="${recruitLine.lineBaseId}">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" lineBaseId="${recruitLine.lineBaseId}">
						</s:if>
						<th>${s.index+1}</th>
						<td class="f-arial"><s:if test="%{#recruitLine.lineSubType==0}">上下班</s:if><s:if test="%{#recruitLine.lineSubType==1}">自由行</s:if></td>
                    	<td class="f-arial">${recruitLine.lineName}</td>
                    	<td class="f-arial">${recruitLine.cityName}</td>
                    	<td class="f-arial">${recruitLine.startStationName}<span class="display-ib lineArrow mr5 ml5 vf6"></span>${recruitLine.endStationName}</td>
                    	<td class="f-arial"><em class="red1 fw">${recruitLine.orderPrice}</em>元/次</td>
                    	<td class="f-arial">${recruitLine.updateOn}</td>
                    	<td class="f-arial">${recruitLine.applyTotal}人</td>
                    	<td class="f-arial">${recruitLine.userName}</td>
                    	<td class="f-arial">
                    	   <s:if test="%{#recruitLine.lineStatus==1}"><em class="green1">招募中</em></s:if>
                    	   <s:if test="%{#recruitLine.lineStatus==2}"><em class="gray1">关闭</em></s:if>
                    	</td>
                    	<th>
	                       <s:if test="%{#recruitLine.lineStatus==1}">
	                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="closeRecruitLine('${recruitLine.lineBaseId}')">关闭</a>
	                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="topRecruitLine('${recruitLine.lineBaseId}')">置顶</a>
	                            <a href="javascript:void(0);" class="fn blue1 mr10" onclick="kaiTongBus('${recruitLine.lineBaseId}')">开通班车</a>
	                       </s:if>
                    	   <s:if test="%{#recruitLine.lineStatus==2}">
	                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="openRecruitLine('${recruitLine.lineBaseId}')">开启</a>
	                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="deleteRecruitLine('${recruitLine.lineBaseId}')">删除</a>
	                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="kaiTongBus('${recruitLine.lineBaseId}')">开通班车</a>
	                       </s:if>
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
          <input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
<form action="../line/getRecruitLines.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="search.field01" value="${search.field01}"/>
  <input type="text" name="search.field02" value="${search.field02}"/>
  <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field11" value="${search.field11}"/>
  <input type="text" name="search.field12" value="${search.field12}"/>
  <input type="text" name="currentPageIndex" id="currentPageIndex"/>
</form>       
</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();

	//通过默认省份加载城市信息
	loadOriginalCity();
});

function clearInputDefaultVal()
{
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				//$(this).val(""); 
			} 
		})  
	});
}

//开通班车
function kaiTongBus(lineBaseId){
	parent.document.location.href="../line/forwardAddLinePageFromRecruidLines.action?lineBaseId="+lineBaseId;
}

//招募详情
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
	    var lineBaseId = $(this).parent().attr("lineBaseId");
		//跳转到招募线路详情总页面
		$("#iframe_right", parent.parent.window.document).attr("src","../line/recruitLinesDetailManeager.action?recLineBaseId="+lineBaseId+"&currentPageIndex="+$("#myCurrentPageIndex").val());
	}
});

//------------------------- 列表选中和未选中start------------------------------
//全部选中
$("#checkAllBox").click(function(){
	//判断是否已经打勾
	$('input[name="checkboxchild"]').attr("checked",this.checked);
});

//选中某一项
$('input[name="checkboxchild"]').click(function(){
	var $checkBoxChild = $("input[name='checkboxchild']");
	$("#checkAllBox").attr("checked",$checkBoxChild.length == $("input[name='checkboxchild']:checked").length ? true : false);
});


//获取选中的所有id
function getCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

//获取count值
function getCount()
{
	var checkboxs = $("input[name='checkboxchild']");
	var counts = 0;
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				counts += parseInt($("#"+checkboxs[i].id).attr("count"));
			}
		}
	}
	return counts;
}

//获取未选中的所有id
function getNotCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (!checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
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
function openRecruitLine(lineBaseId){
  parent.parent.showPopCommonPage("确定要开启此招募线路吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/openRecruitLine.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("招募线路开启成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#turnPage").submit();
			    }else{
			       parent.parent.showRturnTip(data,false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("招募线路开启失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
 
}

function closeRecruitLine(lineBaseId){
  parent.parent.showPopCommonPage("确定要关闭此招募线路吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/closeRecruitLine.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("招募线路关闭成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#turnPage").submit();
			    }else{
			       parent.parent.showRturnTip(data,false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("招募线路关闭失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  
}

function deleteRecruitLine(lineBaseId){
  parent.parent.showPopCommonPage("确定要删除此招募线路吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/deleteRecruitLine.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("招募线路删除成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#turnPage").submit();
			    }else{
			       parent.parent.showRturnTip(data,false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("招募线路删除失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
 
}

function topRecruitLine(lineBaseId){
  parent.parent.showPopCommonPage("确定要置顶此招募线路吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/topRecruitLine.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("招募线路置顶成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#turnPage").submit();
			    }else{
			       parent.parent.showRturnTip(data,false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("招募线路置顶失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
 
}

function loadOriginalCity(){
	   var province = "${search.field11}";
	   if(null!=province&&province.length>0&&province!="null"){
	      loadCity(province);
	   }
}

//根据省份加载城市
function loadCity(value){
	var $theSelectedCityCode=$("#theSelectedCityCode").val();
	if(null!=$theSelectedCityCode&&""!=$theSelectedCityCode){
		$.ajax({
			url:'../merchantAction/getProvince.action?proId='+value,		
			cache:false,	
			dataType:"json",
			async : false,
			success:function(data){
				$("#cityCode").empty();
				$("#cityCode").append("<option value=''>--选择城市--</option>");
				$(data).each(function(i){ //遍历结果数组
					if($theSelectedCityCode==data[i].arearCode){
						$("<option value='"+data[i].arearCode+"' selected='true'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
					}
					else{
						$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
					}
				})
			}
		});
	}
	else{
		$.ajax({
			url:'../merchantAction/getProvince.action?proId='+value,		
			cache:false,	
			dataType:"json",
			async : false,
			success:function(data){
				$("#cityCode").empty();
				$("#cityCode").append("<option value=''>--选择城市--</option>");
				$(data).each(function(i){ //遍历结果数组
				   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
				})
			}
		});
	}
}
</script>