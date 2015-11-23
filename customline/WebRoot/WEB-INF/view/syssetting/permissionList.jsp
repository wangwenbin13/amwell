<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置- 权限管理</title>
<jsp:include page="../resource.jsp"/>
</head>
<body>

<!-- 系统设置-添加管理员 -->
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;权限管理</p></div>
<div class="mt10 ml20 mr20 black1">
   <div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">权限列表</a></li>
        </ul>
    </div>
    <form action="../permission/toList.action" method="post">
     </form>
    
	<div class="table-outline fl widthfull mt20">
         <div class="table-title">
        	 
         	<a href="javascript:void(0)" onclick="addPermission();" class="btn fr mr8 mt4">增加</a>
         </div>
         <div class="table2-text sh-ttext clearfix">
           <div style="overflow-x:auto;overflow-y:hidden">
                   <table width="100%" border="0" class="table3" id="tableDiv">
                        <tr align="center" class="th">
	                      <th scope="col" width="5%"><input type="checkbox" name='checkAllBox' id="checkAllBox"/></th>
	                      <th scope="col" width="10%">当前菜单编码</th>
	                      <th scope="col" width="10%">上级菜单编码</th>
	                      <th scope="col" width="10%">菜单名称</th>
	                      <th scope="col" width="15%">url路径</th>
	                      <th scope="col" width="10%">创建时间</th>
	                      <th scope="col" width="10%">图片路径</th>
	                      <th scope="col" width="10%">系统类型</th>
	                    </tr>
                      
                      <s:if test="recordList.size == 0">
                     
                           <tr align="center" class="bg1">
              					<td colspan="8"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
             		 
             		<s:iterator value="recordList" var="permission" status="s">
             			<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg1" userId ="${permission.powerId}">
						</s:if>
						<s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg2" userId ="${permission.powerId}">
						</s:if>
                    	<th><input type="checkbox" name="checkboxchild" id="child0" value="${permission.powerId }"/></th>
                    	<td class="f-arial"><em class="blue1">${permission.code }</em></td>
                    	<td class="f-arial">${permission.fid }</td>
                    	<td class="f-arial">${permission.name}</td>
                    	<td class="f-arial">${permission.url }</td>
                    	<td class="f-arial">${permission.createOn }</td>
                    	<td class="f-arial">${permission.iconUrl}</td>
                    	<td class="f-arial">${permission.sysType}</td>
                    	
                    </tr>
                    </s:iterator>
                  </table>
   			</div>
   		</div>
   		<%@include file="/WEB-INF/view/pageView.jspf" %>
   </div>
</div>

<form action="../permission/toList.action" method="post" style="display: none;" id="turnPage">
   	<s:hidden theme="simple" name="currentPage" id="currentPage"/>
  </form>
</body>
</html>
<script type="text/javascript">
$(function(){
	//浏览器可视窗口的的高度
	$(".right").css("height",($(window).height()-175+"px"));
	$(".leftbar_bg,.leftbar").css("height",($(window).height()-170+"px"));
	$(".mainBody").css("height",(parent.document.documentElement.offsetHeight)+"px");
	$(window).resize(function() {
		$(".right").css("height",($(window).height()-175+"px"));
		$(".leftbar_bg,.leftbar").css("height",($(window).height()-170+"px"));
		$(".mainBody").css("height",parent.document.documentElement.offsetHeight+"px");
	});  
});
//查找
function findDataByCondition(){
	textTrim("userName");
	textTrim("createBy");
	$("#searchForm").submit();
}


//系统设置-添加管理员   展示增加弹出层页面
function addPermission()
{
    $("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#mainBody").show();
    $("#showPage").load("../permission/toAdd.action?random="+Math.floor(Math.random()*10000+1)); 
}


//系统设置-修改管理员   展示系统设置-修改管理员弹出层页面
function showPopEditPage(userId)
{
    $("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("../adminManager/toUpdate.action?sysAdminVo.userId="+userId+"&random="+Math.floor(Math.random()*10000+1)); 
    $("#mainBody").show();
}
//详情
function showMgrUser(userid){
	
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("../adminManager/toShow.action?sysAdminVo.userId="+userid+"&random="+Math.floor(Math.random()*10000+1));
    $("#mainBody").show();
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

//删除
$("#deleteBtn").click(function(){
	//判断是否已经打勾
	if($("input[name='checkboxchild']:checked").length <= 0)
	{
		parent.parent.showCommonTip("请至少选择一项");
	}
	else{
		parent.parent.showPopCommonPage("确定要删除吗？");
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
		//动态添加方法
		$("#commonSure",parent.window.document).click(function(){
			parent.parent.closePopCommonPage();
			var userids = getCheckLoginIds();
			var inCount = userids.split(":").length;
			
			$.ajax({
				url:'../adminManager/deleteAdmin.action?userids='+userids,
				type:'post',
				cache:false,	
				dataType:"text",	
				success:function(data){	
					
					if("ok"==data){
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
					if("error" == data)
					{
						parent.parent.showRturnTip("删除失败",false);
					}
				}
						
			}); 
			//解绑定，防止多次执行click事件
			$("#commonSure",parent.window.document).unbind('click');
		});
	}
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
				loginIds = loginIds + checkboxs[i].value + ":";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
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
				loginIds = loginIds + checkboxs[i].value + ":";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}


//管理员详情
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
		var userid = $(this).parent().attr("userId");
		showMgrUser(userid);
	}
});

//==============================================================去除搜索框去除两端空格查询开始=================================================================
function textTrim(trimId) {
	 var trimValue = $("#"+trimId).val();
	$("#"+trimId).val(trimValue.replace(/(^\s*)|(\s*$)/g, ""));
}
//===============================================================去除搜索框去除两端空格查询结束================================================================


//重置密码
function resetPassword(userid,loginname){
	
	parent.parent.showPopCommonPage("确定要重置密码吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../adminManager/resetPassword.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{loginName:loginname,userId:userid},
			cache:false,	
			dataType:"text",	
			success:function(data){	
				
				if("" != data){
					if("yes" ==data){
						parent.parent.showRturnTip("重置密码成功!",true);
						parent.parent.showPopCommonPage2("密码已重置为:888888,请管理员尽快修改密码!");
					}
					if("no" == data)
					{
						parent.parent.showRturnTip("重置密码失败!",false);
					}
				}
			},
			error:function(){
				parent.parent.showRturnTip("重置密码失败!",false);
			}
		});
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
	});
}
</script>