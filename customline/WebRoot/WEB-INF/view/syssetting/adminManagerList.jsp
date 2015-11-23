<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-管理员管理</title>
<jsp:include page="../resource.jsp"/>
</head>
<body>

<!-- 系统设置-添加管理员 -->
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;系统设置&nbsp;>&nbsp;管理员管理</p></div>
<div class="mt10 ml20 mr20 black1">
   <div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">管理员列表</a></li>
        </ul>
    </div>
    <form action="../adminManager/toList.action" method="post" id="searchForm">
       	<ul class="r-sub-sel mt20">
               <li class="w31p">
	           	<span class="t-r w65 fl">创建时间：</span>
	               <span class="r-input fl w34p mr10">
	               <input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
	               
	            <span class="fl">至</span>
	            <span class="r-input fl w34p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
	        </li>
               <li class="w20p"><span class="fl">用户名：</span>
               <s:textfield cssClass="r-input w58p" name="search.field03" theme="simple" id="userName"></s:textfield>
               </li>
               <li class="w20p"><span class="fl">操作人：</span> 
               <s:textfield cssClass="r-input w67p" theme="simple" id="createBy" name="search.field04"></s:textfield>
               </li>
           	<li>
           		<a href="javascript:void(0);"  class="btn-blue4" onclick="findDataByCondition()">查找</a>
           	</li>
           </ul>
         </form>
           
	<div class="table-outline fl widthfull mt20">
         <div class="table-title">
            <a href="javascript:void(0)" id="deleteBtn" class="btn fr mr4 mt4">删除</a>
         	<a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
         </div>
         <div class="table2-text sh-ttext clearfix">
           <div style="overflow-x:auto;overflow-y:hidden">
                   <table width="100%" border="0" class="table3" id="tableDiv">
                        <tr align="center" class="th">
	                      <th scope="col" width="5%"><input type="checkbox" name='checkAllBox' id="checkAllBox"/></th>
	                      <th scope="col" width="10%">登录名</th>
	                      <th scope="col" width="10%">用户姓名</th>
	                      <th scope="col" width="15%">联系电话</th>
	                      <th scope="col" width="10%">角色</th>
	                      <th scope="col" width="10%">状态</th>
	                      <th scope="col" width="10%">操作人</th>
	                      <th scope="col" width="15%">创建时间</th>
	                      <th scope="col" width="15%">操作</th>
	                    </tr>
                      
                      <s:if test="recordList.size == 0">
                     
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
             		 
             		<s:iterator value="recordList" var="admin" status="s">
             			<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg1" userId ="${admin.userId}">
						</s:if>
						<s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg2" userId ="${admin.userId}">
						</s:if>
                    	<th><input type="checkbox" name="checkboxchild" id="child${s.index }" value="${admin.userId }"/></th>
                    	<td class="f-arial"><em class="blue1">${admin.loginName }</em></td>
                    	<td class="f-arial">${admin.userName }</td>
                    	<td class="f-arial">${admin.telephone }</td>
                    	<td class="f-arial">${admin.roleName }</td>
                    	<td class="f-arial">${admin.status==0?'无效':'有效'}</td>
                    	<td class="f-arial">${admin.createBy }</td>
                    	<td class="f-arial">${admin.createOn }</td>
                    	<th><a href="javascript:void(0);" class="fn blue1 mr10" onclick="resetPassword('${admin.userId}','${admin.loginName }')">重置密码</a>
                    	<a href="javascript:void(0);" class="fn blue1 mr10" onclick="showPopEditPage('${admin.userId}');">编辑</a></th>
                    </tr>
                    </s:iterator>
                  </table>
   			</div>
   		</div>
   		<%@include file="/WEB-INF/view/pageView.jspf" %>
   </div>
</div>
<input type="hidden" id="myCurrentPageIndex" value="${currentPage}"/>
<form action="../adminManager/toList.action" method="post" style="display: none;" id="turnPage">
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
function showAddPage()
{
    $("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#mainBody").show();
    $("#showPage").load("../adminManager/toAdd.action?random="+Math.floor(Math.random()*10000+1)); 
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
    $("#showPage").load("../adminManager/toShow.action?sysAdminVo.userId="+userid+"&random="+Math.floor(Math.random()*10000+1)+"&currentPage="+$("#myCurrentPageIndex").val());
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