<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<script src="../js/common/Util.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销管理-APP消息-APP消息模板</title>
<jsp:include page="../resource.jsp"/>
</head>

<body> 
<div class="table-title mt20"><a class="btn fr mr8 mt4" id="showAddPushPage" href="javascript:void(0)">增加</a></div>
      <div class="table2-text sh-ttext">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table1" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
					  <th scope="col">模板名称</th>
                      <th scope="col" width="8%">创建人</th>
                      <th scope="col" width="12%">创建时间 </th>                    
                      <th scope="col" width="8%">操作</th>
                    </tr>
                    <s:if test="list==null || list.isEmpty">
	                    <tr align="center" class="tr bg1">
							<td colspan="11">
	                   			<p class="f13 f-yahei gray4 t-c line26 mt10">
	             					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p>
	                   		</td>
	                   	</tr>
                   	 </s:if>
                   	<s:iterator value="list" var="msgTemplateVo" status="s">
	               		<tr align="center">
	               			<td class="f-arial">${currentPageIndex+s.count}</td>
	               			<td>${msgTemplateVo.template_name }</td>
	               			<td>${msgTemplateVo.createBy}</td>
	               			<td class="f-arial">${msgTemplateVo.createOn}</td>
	               			<td class="f-arial">
	               			&nbsp;
	               			<a href="javascript:void(0);" class="blue1"  onclick="showEditMarketPage('${msgTemplateVo.id}')">编辑</a>
	               			&nbsp;
	               			<a href="javascript:void(0);" class="blue1 ml10" onclick="deleteMarketPage('${msgTemplateVo.id}')">删除</a></td>
	               		</tr>
	               </s:iterator>
                  </table>
                  
          	</div>
          	
          	   	
          </div>
    <form action="../marketing/getMarketingList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
  </form> 
</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();
});

function clearInputDefaultVal()
{
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				
			} 
		})  
	});
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

//增加模板
$("#showAddPushPage").click(function(){	
		$("#iframe_right",parent.parent.window.document).attr("src","../pushMessage/pushAPPMsgModelAdd.action?feedbackId");
});

//修改短信模板
function showEditMarketPage(id){
	$("#iframe_right",parent.parent.window.document).attr("src","../pushMessage/marketingModelEdit.action?msgTemplateVo.id="+id);
}

function deleteMarketPage(id){

	parent.parent.showPopCommonPage("确定要删除吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
	
		parent.parent.closePopCommonPage();

		$.ajax({
			url:'../pushMessage/marketingModelDel.action?msgTemplateVo.id='+id,
			type:'post',
			cache:false,	
			dataType:"text",	
			success:function(data){	
				if(data != null){
					if(data == 'yes'){
						parent.parent.showRturnTip("删除短信模板成功",true);
						$("#iframe_right",parent.parent.window.document).attr("src","../pushMessage/pushAPPManager.action?level=two&toAdd=2");
					}
					if(data == 'no'){
						parent.parent.showRturnTip("删除短信模板失败",false);
					}
				}
				
			}
		});
	});
}
</script>