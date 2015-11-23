<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-添加角色</title>
<%@include file="../resource.jsp" %>
</head>

<body style="background:#fff;">
<form action="" method="post" id="roleForm">
<s:hidden theme="simple" name="sysRoleEntity.roleId"></s:hidden>
<s:hidden theme="simple" name="permissionCodes" id="permissionCodes"></s:hidden>
	<div class="pop black1" id="popAddPage" style="width:800px">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white">添加角色</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopAddPage();" class="pop-close fr mt4 mr4"></a></div>
	    </div>
	    <div class="pop-main p10">
	        <ul class="pop-lilist mt15 clearfix line26">	
	        	<li class="w49p"><span class="t-r w95 fl">角色名称：<em class="red1 mr4">*</em></span>
	        	<s:textfield cssClass="r-input w64p fl" name="sysRoleVo.roleName" theme="simple" id="roleName" maxlength="32"/>
	        	</li>
	            <li class="w49p"><span class="t-r w95 fl">备注：</span><s:textfield cssClass="r-input w64p fl" name="sysRoleVo.remark" theme="simple" id="remark" maxlength="225"/></li>
	            <li style="width:100%;" class="treeli"><span class="t-r w95 fl">角色权限：<em class="red1 mr4">*</em></span>
	            	<div class="fl w85p">
	                    <p class="gray2 line26">勾选后，赋予当前角色的权限</p>
	                    <div class="r-input more-show" style="width:95%;max-height:300px;overflow-y:auto;">
	                    	<ul id="treeDemo_1" class="ztree"></ul>
	                    </div>
	                </div>
	            </li>
	        </ul>
	        <p class="t-c mt10 mb20"><input type="button" class="display-ib btn1 white mr40 f14" value="添&nbsp;加" onclick="validateFunction('roleForm');" id="submitButton"/>
	        <a onclick="closePopAddPage();" href="javascript:void(0);" class="display-ib btn1 white f14">取&nbsp;消</a></p>
	    </div>
	</div>
</form>
</body>
</html>
<script language="Javascript">
//关闭弹出层
function closePopAddPage()
{
    $("#popAddPage").hide();
    $("#topHide", parent.window.document).hide();
    $("#leftHide", parent.window.document).hide();
    $("#mainBody").hide();
   
}
var settings = {
	data: {
		simpleData: {
			enable: true
		}
	},
	//表示是否显示复选框
	check: {
		enable: true
	},
	
	
	//回调函数
	callback: {
		onCheck: onCheck
	}
};
var zNodesAdd =[
	{id:1, name:"此类节点不响应右键菜单", open:true, noR:true,
		children:[
			   {id:11, name:"节点1", noR:true},
			   {id:12, name:"节点2", noR:true}

		]},
	{id:2, name:"右键操作 1", open:true,
		children:[
			   {id:21, name:"节点11"},
			   {id:22, name:"节点12"},
			   {id:23, name:"节点13"},
			   {id:24, name:"节点14"}
		]},
	{id:3, name:"右键操作 1", open:true,
		children:[
			   {id:31, name:"节点21"},
			   {id:32, name:"节点22"},
			   {id:33, name:"节点23"},
			   {id:34, name:"节点24"}
		]}
];
function onCheck(e, treeId, treeNode) {

	var permissIds = checkNode();
	//
	if("" == permissIds){
		createErrorTip("treeDemo_1","权限不能为空");
		setSubmitDisales(true);
	}else{
		validateUserDefinedSucc("treeDemo_1");	
		//将提交按钮恢复
		setSubmitDisales(false);
	}
}
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo_1"), settings,${ztreeJson});
});

//验证开始===============================================================================
function validateFunction(submitForm){
	
	//验证权限树是否有选
	validateUserDefined();
	//验证未通过的计数
    var failValidateCount = $(document).find(".tipTable:visible").length;
	
    if(failValidateCount == 0)
	{
    	//设置权限code
	    $("#permissionCodes").val(checkNode());
		//添加/修改方法
	    roleFormSubmit(submitForm);
	    //验证提交 防止多次提交,true表示将按钮高亮
		setSubmitDisales(true);
	}
	else
	{
		//验证提交 防止多次提交
		setSubmitDisales(true);
	    return false;
	}
}

//验证权限树是否选中
function validateUserDefined(){

	//验证权限
	var permissIds = checkNode();
	if("" == permissIds){
		createErrorTip("treeDemo_1","权限不能为空");
	}else{
		validateUserDefinedSucc("treeDemo_1");	
	}
	//验证角色名称
	var roleName = $("#roleName").val().trim();
	if("" == roleName){
		createErrorTip("roleName","角色名称不能为空");
	}
}


function clearInputDefaultVal(){

	$("#roleName").each(function(){
			
		$(this).focus(function(){
			validateUserDefineClear("roleName");
			 //验证提交 防止多次提交
			setSubmitDisales(false);
		}).blur(function(){
			
			//验证角色名称是否重复
			var verificationValue = $("#roleName").val();
			//第一个参数是验证的Id,第二个是值
			roleNameIsRepetition("roleName",verificationValue);
			//去除左右两端的空格
			textTrim("roleName");
		});


	});
	
}

//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
	
	$("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id+"ErrTip").remove();
	
	if($("#"+id).parent().is("div"))
	{
		$("#"+id).parent().prev().append("<span id='"+id+"ErrTip' class='tipTable'></span>");
		$("#"+id+"ErrTip").css("margin-left","7px");
	}
	else
	{
	    $("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	    $("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
		$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
	}
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
}
//清除自定义验证提示语句
function validateUserDefineClear(id)
{
    $("#"+id+"ErrTip").hide();
    $("#"+id).parent().find(".onCorrect").remove();
}
//自定义验证通过
function validateUserDefinedSucc(id)
{
	$("#"+id).parents("li").find(".onCorrect").remove();
    if($("#"+id).parent().is("div"))
    {
    	$("#"+id).parent().prev().append("<span id='"+id+"Tip'></span>");
    }
    else
    {
    	 $("#"+id).parents("li").append("<span id='"+id+"Tip'></span>");
    }
    $("#"+id+"Tip").addClass("onCorrect");
	$("#"+id+"ErrTip").hide();
}
function setSubmitDisales(flag)
{
     if(flag)
	 {
		$('#submitButton').removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $('#submitButton').removeClass("btn1-gray").addClass("btn1");
	 } 
	 $('#submitButton').attr("disabled", flag);
}
//验证结束===============================================================================
function roleNameIsRepetition(verificationId,verificationValue){
	
	//判断是否为空
	if("" == verificationValue.trim()){
		//创建自定义提示语句
		createErrorTip(verificationId,"角色名称不能为空");
		return;
	}
	$.ajax({
		url:'../rolePermissionManager/roleNameIsRepetition.action',	
		cache:false,
		type: 'POST',	
		async : false,
		data:{roleName:verificationValue},
		
		success:function(data){
			if("yes" == data){
				createErrorTip(verificationId,"角色名称已存在");
			}
			if("no" == data){
				validateUserDefinedSucc(verificationId);
			}
		}
				
	});
}	
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}	
//==============================================================去除搜索框去除两端空格查询开始=================================================================
function textTrim(trimId) {
	 var trimValue = $("#"+trimId).val();
	$("#"+trimId).val(trimValue.replace(/(^\s*)|(\s*$)/g, ""));
}
//===============================================================去除搜索框去除两端空格查询结束================================================================
//获取树选中id
function checkNode() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo_1");
	var nodes = zTree.getCheckedNodes(true);
	var idarr = "";
	for(var i = 0; i<nodes.length;i++){
		idarr+=nodes[i].id+",";
	}
	
	return idarr;
}

function roleFormSubmit(submitForm){

	$("#"+submitForm).ajaxSubmit({
		type : 'post',
		data:{},
		url : "../rolePermissionManager/rolePermissionAddOrUpdate.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"text",
		success : function(data) {
			
			if("yes" == data){
				closePopAddPage();
				parent.parent.showRturnTip("添加成功",true);
				window.location.href='../rolePermissionManager/toList.action';
			}
			if("no" == data){
				parent.parent.showRturnTip("添加失败",false);
			}
			if("noPermission" == data){
				parent.parent.showRturnTip("没有选择权限",false);
			}
		}
	});
}
</script>
