<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pop black1 w520" id="popViewPage">
	<div class="pop-t pop-t1">
    	<div class="pop-t-l fl fw white">查看权限</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" class="pop-close fr mt4 mr4" onclick="closePopViewPage();"></a></div>
    </div>
    <div class="pop-main p10">
        <ul class="pop-lilist mt15 clearfix">	
            <li style="width:100%;" class="treeli"><span class="t-r w95 fl">权限列表：<em class="mr4">&nbsp;</em></span>
            	<div class="fl">
                    <p class="gray2 line26">勾选后，赋予当前用户权限</p>
                    <div class="r-input more-show" style="width:375px;max-height:300px;overflow-y:auto;">
                    	<ul id="treeDemo" class="ztree"></ul>
                    </div>
                </div>
            </li>
        </ul>
        <p class="t-c mt10 mb20"><input type="submit" class="display-ib btn1 white mr40 f14" onclick="closePopViewPage();" value="确&nbsp;定" />
        <a class="display-ib btn1 white f14" href="javascript:void(0);" onclick="closePopViewPage();">取&nbsp;消</a></p>
    </div>
</div>
<script language="Javascript">
<!--
var settings = {
	open:true,
	data: {
		simpleData: {
			enable: true
		}
	},
	//表示是否显示单选框
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	//回调函数
	callback: {
		onCheck: onCheck
	}
};
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = zTree.getCheckedNodes(true);
	
	$("#powerName",window.parent.documnet).val(nodes[nodes.length-1].name);
	$("#powerName",window.parent.documnet).removeClass("gray3").addClass("gray2");
	$("#fid",window.parent.documnet).val(nodes[nodes.length-1].id);
	$("#powerNameErrTip",window.parent.documnet).hide();
	//验证提交 防止多次提交
	setSubmitDisale(false);
}		


$(document).ready(function(){
	//验证提交 防止多次提交
	setSubmitDisale(true);
	$.fn.zTree.init($("#treeDemo"), settings, ${ztreeJson});
});


//展示增加弹出层页面
function showPopAddPage()
{
	$("#popAddPage").show();
    $("#mainBody").show();
    $("#topHide", window.parent.document).show();
    $("#leftHide", window.parent.document).show();
}

//关闭查看角色弹出层页面
function closePopViewPage()
{
    showPopAddPage();
    $("#popViewPage").hide();
    $("#mainBody1").hide();
}
</script>
</html>
