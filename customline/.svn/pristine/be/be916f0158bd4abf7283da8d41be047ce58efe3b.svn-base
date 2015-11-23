<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统设置-角色详情</title>
</head>

<body style="background:#fff;">
<form action="" method="post">
	<div class="pop black1" id="popDetaiPage" style="width:800px">
		<div class="pop-t">
	    	<div class="pop-t-l fl fw white">角色详情</div>
	        <div class="pop-t-r fr"><a href="javascript:void(0);" onclick="closePopDetailPage();" class="pop-close fr mt4 mr4"></a></div>
	    </div>
	    <div class="pop-main p10">
	        <ul class="pop-lilist mt15 clearfix line26">	
	        	<li class="w49p graybg"><span class="t-r w95 fl">角色名称：<em class="red1 mr4">*</em></span>
	        	<s:textfield cssClass="r-input w64p fl" name="sysRoleVo.roleName" theme="simple" id="roleName" maxlength="32" readonly="true"/></li>
	            <li class="w49p graybg"><span class="t-r w95 fl">备注：</span><s:textfield cssClass="r-input w64p fl" name="sysRoleVo.remark" theme="simple" id="remark" maxlength="500" readonly="true"/></li>
	            <li style="width:100%;" class="treeli"><span class="t-r w95 fl">角色权限：<em class="red1 mr4">*</em></span>
	            	<div class="fl w85p">
	                    <p class="gray2 line26">勾选后，赋予当前角色的权限</p>
	                    <div class="r-input more-show" style="width:95%;max-height:300px;overflow-y:auto;">
	                    	<ul id="treeDemo_3" class="ztree"></ul>
	                    </div>
	                </div>
	            </li>
	        </ul>
			<p class="t-c mt10 mb20"><input type="button" onclick="closePopDetailPage();" class="display-ib btn1 white mr40 f14"  value="返&nbsp;回"/>
	    </div>
	</div>
</form>
</body>
</html>
<script language="Javascript">
//关闭弹出层
function closePopDetailPage()
{
    $("#popDetaiPage").hide();
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
		enable: true,
		chkDisabled: true
	},
	
	//回调函数
	callback: {
		onCheck: onCheck
	}
};
var zNodesDetail =[
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
	
}
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo_3"), settings, ${ztreeJson});
});

</script>
