<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-角色权限</title>
<%@include file="../resource.jsp" %>
<script src="../js/common/Util.js" type="text/javascript"></script>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div id="mainBody1" class="mainBody"></div>
<div id="showPage1" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;角色权限管理</p></div>
  
   <div class="mb20 mt10 ml20 mr20 black1">
     <div class="table-title">
	     <ul>
	        <li class="on"><a href="javascript:void(0)" class="f14 blue2">角色权限管理</a></li>
	     </ul>
	 </div>
   	<form action="../rolePermissionManager/toList.action" method="post" id="searchForm">
   	  <ul class="r-sub-sel mt20">
       	   <li class="w31p">
	           	<span class="t-r w65 fl">创建时间：</span>
	               <span class="r-input fl w34p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
	            <span class="fl">至</span>
	            <span class="r-input fl w34p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
	        </li>
       	   <li class="w18p"><span class="fl">角色名称：</span><s:textfield type="text" name="search.field03" theme="simple" cssClass="r-input w65p" id="roleName"/></li>
           <li class="w18p"><span class="fl">操作人：</span><s:textfield type="text" name="search.field04" theme="simple" cssClass="r-input w65p" id="createBy"/></li>
            <li>
           		 <a href="javascript:void(0);"  class="btn-blue4" onclick="findDataByCondition()">查找</a>
            </li>
     </ul>
   	</form>
   
     <div class="table-outline fl widthfull mt10">     
     <div class="table-title">
          <a href="javascript:void(0);" onclick="deleteRole();" class="btn fr mr4 mt4" >删除</a>
          <a href="javascript:void(0);" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
      </div>
      
      <div class="table2-text sh-ttext clearfix">
      <table width="100%" border="0" class="table3" id="tableDiv">
        <tr align="center"  class="th">
          <th scope="col" width="5%"><input type="checkbox" name='checkAllBox' id="checkAllBox"/></th>
          <th scope="col" width="18%">角色名称</th>
          <th scope="col" width="18%">备注</th>
          <th scope="col" width="18%">操作人</th>
          <th scope="col">创建时间</th>
          <th scope="col" width="18%">操作</th>
        </tr>
       <s:if test="recordList.size == 0">
        	<tr align="center" class="bg1">
              	<td colspan="6"><p class="f13 f-yahei gray4 t-c line26 mt10 mb10"><span class="index-nodate mr10"></span>
              	<span id="noDate">暂无数据</span></p></td>
            </tr>
       </s:if>
	  <s:iterator value="recordList" var="sysRole" status="s">
       		<s:if test="%{#s.count%2 == 0}">
				<tr align="center" class="tr bg1" roleId ="${sysRole.roleId}">
			</s:if>
			<s:if test="%{#s.count%2 == 1}">
				<tr align="center" class="tr bg2" roleId ="${sysRole.roleId}">
			</s:if>
          <th><input type="checkbox" name="checkboxchild" id="child0" value="${sysRole.roleId}"/></th>
          <td>${sysRole.roleName }</td>
          <td><span id="remark${s.index}" title="${sysRole.remark }"><script type="text/javascript">$("#remark"+${s.index}).text(Util.cutstr('${sysRole.remark }',30))</script></span></td>
          <td>${sysRole.userName }</td>
          <td class="f-arial">${sysRole.createOn }</td>
          <th>
          <a href="javascript:void(0);" onclick="updateRoleInfo('${sysRole.roleId }');" class="blue1 ml10 fn">修改</a>
          </th>
        </tr>
        </s:iterator>
      </table>
      </div>
      <%@include file="/WEB-INF/view/pageView.jspf" %>
      
   </div>
</div>
 <form action="../rolePermissionManager/toList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
   	<s:hidden theme="simple" name="currentPage" id="currentPage"/>
  </form> 
</body>
</html>
<script type="text/javascript">
$(function(){
	//浏览器可视窗口的的高度
	$(".right").css("height",($(window).height()-175+"px"));
	$(".leftbar_bg,.leftbar").css("height",($(window).height()-170+"px"));
	$(".right-tree-list").css("height",($(window).height()-43+"px"));	
	$(".mainBody").css("height",(parent.document.documentElement.offsetHeight)+"px");
	$(window).resize(function() {
		$(".right").css("height",($(window).height()-175+"px"));
		$(".leftbar_bg,.leftbar").css("height",($(window).height()-170+"px"));
		$(".right-tree-list").css("height",($(window).height()-43+"px"));	
		$(".mainBody").css("height",parent.document.documentElement.offsetHeight+"px");
	});    
});

//提交表单的时候去除空格
function findDataByCondition(){
	textTrim("roleName");
	textTrim("createBy");
	$("#searchForm").submit();
}
//添加角色
function showAddPage()
{ 
     $("#topHide", parent.window.document).show();
     $("#leftHide", parent.window.document).show();
     $("#mainBody").show();
     $("#showPage").load("../rolePermissionManager/toAdd.action"); 
}
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
				loginIds = loginIds + checkboxs[i].value + ",";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

function deleteRole(){
	//判断是否已经打勾
	if($("input[name='checkboxchild']:checked").length <= 0)
	{
		parent.parent.showCommonTip("请至少选择一项");
	}
	else
	{
		parent.parent.showPopCommonPage("确定要删除吗？");
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
		//动态添加方法
		$("#commonSure",parent.window.document).click(function(){
			parent.parent.closePopCommonPage();
			var ids = getCheckLoginIds();
			var inCount = ids.split(",").length;
			parent.parent.popLodeShowPage(true);
			
			$.ajax({
				url:'../rolePermissionManager/deleteRole.action?ids='+ids,		
				cache:false,	
				async : true,
				dataType:"text",	
				success:function(data){	
					parent.parent.popLodeShowPage(false);
					if(null != data){
						if("" != data){
							if("isUse" == data){
								parent.parent.showCommonTip("该角色已绑定用户");
							}
							if("ok" == data){
								
								//$("#searchform").submit();
								parent.parent.showRturnTip("删除成功",true);
								//判断当前页是否只有一条记录
								var currPage = $("#currentPage").val();
								var pageSize = $("#pageSize").val();
								var totalCount = $("#recordCount").val();
								//说明当前页只有一条记录 ,需要跳转到上一页
								var npageNum = ((totalCount-inCount)+pageSize-1)/pageSize;
								if(npageNum<currPage){
									$("#currentPage").val(npageNum);
								}
								$("#turnPage").submit();
							}
							if("error" ==data){				
								parent.parent.showRturnTip("删除失败",false);
							}
							if("noID" == data){
								parent.parent.showRturnTip("没有选择角色ID",false);
							}
						}
					}
					
				}
						
			});
			
			//解绑定，防止多次执行click事件
			$("#commonSure",parent.window.document).unbind('click');
		});
		
	}
	
}
//详情
function roleInfo(rid){

     $("#topHide", parent.window.document).show();
     $("#leftHide", parent.window.document).show();
     $("#mainBody").show();
     $("#showPage").load("../rolePermissionManager/toShow.action?sysRoleEntity.roleId="+rid+"&random="+Math.floor(Math.random()*10000+1));
}

//修改角色功能
function updateRoleInfo(rid){
	 $("#topHide", parent.window.document).show();
     $("#leftHide", parent.window.document).show();
     $("#mainBody1").show();
     $("#showPage1").load("../rolePermissionManager/toUpdate.action?sysRoleEntity.roleId="+rid+"&random="+Math.floor(Math.random()*10000+1)); 
}

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
		var roleId = $(this).parent().attr("roleId");
		roleInfo(roleId);
	}
});

//==============================================================去除搜索框去除两端空格查询开始=================================================================
function textTrim(trimId) {
	 var trimValue = $("#"+trimId).val();
	$("#"+trimId).val(trimValue.replace(/(^\s*)|(\s*$)/g, ""));
}
//===============================================================去除搜索框去除两端空格查询结束================================================================

</script>
