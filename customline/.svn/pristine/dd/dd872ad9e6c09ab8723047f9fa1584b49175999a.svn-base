<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-招募线路-用户线路申请-详情</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div class="black1">
	<ul class="sys-text sh-add-new line24 gray2">
		<li class="clearfix"><span class="fl w100 t-r f12">申请时间:</span>${userLineEntity.applicationTime}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r f12">申请人：</span>${userLineEntity.nickName}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r f12">联系电话：</span>${userLineEntity.telephone}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r f12">线路类型：</span><s:if test="%{userLineEntity.lineType==0}">上下班</s:if><s:if test="%{userLineEntity.lineType==1}">自由行</s:if></li>
		<li class="clearfix mt20"><span class="fl w100 t-r f12">起点：</span>${userLineEntity.startAddress}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r f12">途经点：</span>${userLineEntity.stationNames}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r f12">终点：</span>${userLineEntity.endAddress}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r f12">发车时间：</span>${userLineEntity.startTime}</li>
		<li class="clearfix mt20"><span class="fl w100 t-r f12">备注：</span>
			<textarea style="width:700px;height:100px" class="dateTextarea mt10 gray1" disabled="disabled">${userLineEntity.remark}</textarea>
		</li>
		<li class="mt20 hide" id="bacnLi0"><span class="fl w100 t-r f12">回访人：</span>${userLineEntity.callbackLoginName}</li>
		<li class="mt20 hide" id="bacnLi1"><span class="fl w100 t-r f12">回访时间：</span>${userLineEntity.callbackDateTime}</li>
		<li class="mt20 hide" id="bacnLi2"><span class="fl w100 t-r f12">回访内容：</span>
			<textarea style="width:700px;height:100px" class="dateTextarea mt10 gray1" disabled="disabled" id="callbackContent" name="callbackContent">${userLineEntity.callbackContent}</textarea>
		</li>
		<li class="mt20 hide" id="bacnLi3"><span class="fl w100 t-r f12">回访内容：</span>
			<textarea class="r-input more-show" style="width:700px;" name="content" id="content" style="height:350px;"></textarea>
		</li>
		<li class="mt20 ml100 hide" id="bacnLi4"><input type="button" id="addBtn" value="确&nbsp;定" class="display-ib btn1 white mr40 f14" /><a class="display-ib btn1 white" href="javascript:void(0)" onclick="toBack();">返回</a></li>
	</ul>
</div>
<form action="../line/addUserLineCallBack.action" method="post" style="display: none;" id="callbackForm">
<input type="hidden" id="applicationId" name="applicationId" value="${userLineEntity.applicationId}"/>
<textarea  style="display: none;" name="callbackContent2" id="callbackContent2"></textarea>
</form>
</body>
</html>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">
/*
 * getURLParameter函数是根据参数名获取url中的参数;
 * 1个必传参数
 * @paraName    
 * @return {字符串} 参数的值
 */
function getURLParameter(param){
	var url = window.location.href;
    var params = (url.substr(url.indexOf("?") + 1)).split("&");
    if (params != null) {
        for (var i = 0; i < params.length; i++) {
            var strs = params[i].split("=");
            if (strs[0] == param && strs[1]) {
                return strs[1];
            }
        }
    }
    return "";
}
//回访状态  backstatu:0代表未回访；1代表已回访 
var backstatu = ("" == getURLParameter("backstatu")) ? "" : parseInt(getURLParameter("backstatu"));

$(function(){
	if(backstatu == "0") //未回访
	{
		$("#bacnLi3,#bacnLi4").show();
	}
	else if(backstatu == "1") //已回访 
	{
		$("#bacnLi0,#bacnLi1,#bacnLi2").show();
	}
});

//创建文字编辑器
var editor;
KindEditor.ready(function(K) {
	var options = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist']
	}
	editor = K.create("#content",options);
});

//创建文字编辑器
var editorNew;
KindEditor.ready(function(K) {
	var options = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist'],
		afterChange : function() {
	     }
	}
	editorNew = K.create("#callbackContent",options);
	editorNew.readonly(true);    
});

//取得编辑器文字的内容
$("#addBtn").click(function(){
	//alert(editor.text());
	var content = editor.text();
	if(content==null||content.length==0||content==""){
	   parent.parent.showRturnTip("请填写回访内容",false);
	   return;
	}else{
	    if(content.length>6000){
			parent.parent.showPopCommonPage2("回访内容最多输入5000个字");
			return;
	    }
	}
	$("#callbackContent2").text(editor.html());
	$("#callbackForm").ajaxSubmit({
		
		type : 'post',
		success : function(data) {
			if (data == "success") {
				parent.parent.showRturnTip("回访成功",true);
				$("#iframe_right",parent.parent.window.document).attr("src","../line/recruitLinesManager.action?level=two&toAdd=1");
				
			}else if("error"==data){
				parent.parent.showRturnTip("修改失败",false);
			}
		}
  });
});

//返回上一步
function toBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../recruitLinesManager.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="recruitLinesManager.action?level=two";
	}
	window.parent.location.href=$the_url;
}
</script>
