<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户投诉-帮助中心</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;客户投诉&nbsp;>&nbsp;帮助中心</p></div>
<s:form action="" method="post" id="helpCenterForm" theme="simple">
<s:hidden theme="simple" name="helpCentryEntity.helpId" id="helpId"/>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">帮助中心</a></li>
        </ul>
    </div>
    <div class="sh-add-new feedbackLi" >
    	<ul class="mt20 clearfix gray2 f12">
            <li>类型：
            <s:select name="helpCentryEntity.helpType" list="#{'1':'预订常见问题','2':'支付常见问题','3':'乘车常见问题','4':'退款常见问题'}" cssClass="p3" id="typeSelect"></s:select>

			</li>
            <li style="width:90%"><span class="fl">内容：</span>
                <s:textarea cssClass="r-input more-show w87p" name="helpCentryEntity.content" id="content" cssStyle="height:350px;"></s:textarea>

            </li>
            <li class="t-c mt20"><input type="button" id="addBtn" value="确&nbsp;定" class="display-ib btn1 white mr40 f14" /></li>
       </ul>
    </div>
</div>
</s:form>
</body>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	$(".sh-add-new").css("height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("height",$(window).height()-130+"px");	
	})
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
			'insertunorderedlist', '|', 'emoticons','link']
	}
	editor = K.create("#content",options);
});

//取得编辑器文字的内容
$("#addBtn").click(function(){
	var $theText=editor.html();//带换行、空格等标签的  .text()取的是纯文本
	if(''==$.trim(editor.text())){
		parent.showRturnTip("内容不能为空！",false);
		return false;
	}
	$("#content").text($theText);
	$("#helpCenterForm").ajaxSubmit({
		type : 'post',
		data:{},
		url : "../helpCenter/addUpdateHelpCentry.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"html",
		success : function(data) {
			if('success' == data){
				if($("#helpId").val()==''){
					parent.showRturnTip("添加成功",true);
				}
				else{
					parent.showRturnTip("修改成功",true);
				}
				$("#helpCenterForm").attr('action','../helpCenter/list.action');
				$("#helpCenterForm").submit();
			}
		}
	});
	//验证提交 防止多次提交
	setSubmitDisale(false);
});

//改变下拉框的值
$("#typeSelect").change(function(){
	$("#helpCenterForm").attr('action','../helpCenter/list.action');
	$("#helpCenterForm").submit();
});
</script>
</html>
