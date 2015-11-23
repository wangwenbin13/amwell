<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代理商管理-编辑代理商</title>
<%@include file="../resource.jsp" %>

</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;代理商管理&nbsp;>&nbsp;添加代理商<span class="blue1 ml25"></span></p></div>
 <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">添加代理商</a></li>
                </ul>
            </div>
<form id="addForm" action="" method="post">
<div class="table2-text">
  	<p class="bt-bot-line"><span class="red1">*字段为必须填写，不填写无法完成创建</span></p>
 	  <div class="sh-add clearfix">
  		<ul>
  			<li class="widthfull"><span class="w100 fl t-r">代理商名称：<em class="red1 mr4">*</em></span><s:textfield  cssClass="r-input w35p gray2 fl" theme="simple" maxlength="30"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">登录名：<em class="red1 mr4">*</em></span><s:textfield  cssClass="r-input w35p gray2 fl" maxlength="15"/><span class="red1">（登录密码默认为888888）</span></li>
  			<li class="widthfull"><span class="w100 fl t-r">联系人：<em class="red1 mr4">*</em></span><input  class="r-input w35p gray2 fl" maxlength="10"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">联系电话：<em class="red1 mr4">*</em></span><s:textfield  cssClass="r-input w35p gray2 fl" theme="simple"/></li>
  			<li class="widthfull"><span class="w100 fl t-r">地址：<em class="red1 mr4">*</em></span>
	  			<select class="p3 fl">
	             		<option value="0">请选择</option>
	             </select>
	             <select class="p3 fl ml10">
	             		<option value="0">请选择</option>
	             </select>
            </li>
            <li class="widthfull"><span class="w100 fl t-r"></span>
            	<input type="text" class="r-input w35p gray2" />
            </li>
           <li style="width:100%;" class="treeli"><span class="t-r w95 fl">权限：<em class="red1 mr4">*</em></span>
               <div class="fl w85p">
                    <p class="gray2 line26">勾选后，赋予当前代理商权限</p>
                    <div class="r-input more-show" style="width:95%;max-height:300px;overflow-y:auto;">
                    	<ul id="treeDemo_1" class="ztree"></ul>
                    </div>
                </div>
           </li>
           <li class="widthfull"><span class="w100 fl t-r"></span></li>
  	  	</ul>
  	  </div>
  	<p class="t-c mt20"><input type="button" class="display-ib btn1 white mr40" value="保存" /><input type="reset" onclick="javascript:history.go(-1);" class="display-ib btn1 white" value="取消"/></p>
</div>
</form>
</div>
</div>
<script type="text/javascript">
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
		
	}else{
		
	}
}
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo_1"), settings,zNodesAdd);
});
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
</script>
</body>
</html>