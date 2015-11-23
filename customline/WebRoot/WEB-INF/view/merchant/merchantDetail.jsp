<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商管理-商户详情</title>
<%@include file="../resource.jsp" %>

</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;商户详情<a class="display-ib btn ml10" href="javascript:history.go(-1)">返回</a><span class="blue1 ml25"></span></p></div>
<div class="mt10 ml20 mr20 black1">
 	<div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">商户详情</a></li>
        </ul>
    </div>
    <div class="sys-text sh-add-new map-shebei-li">
        <ul class="mt20 clearfix gray2 f12">
            <li><span class="fl w85 t-r">企业机构名称：</span>${mgrBusinessEntity.name }</li>
            <li><span class="fl w85 t-r">简称：</span>${mgrBusinessEntity.brefName }</li>
            <li><span class="fl w85 t-r">登录名：</span>${mgrBusinessEntity.loginName }</li>
            <li><span class="fl w85 t-r">联系人：</span>${mgrBusinessEntity.contacts }</li>
            <li><span class="fl w85 t-r">联系电话：</span>${mgrBusinessEntity.contactsPhone }</li>
            <li><span class="fl w85 t-r">地址：</span>${mgrBusinessEntity.address }</li> 
            <li><span class="fl w85 t-r">账号状态：</span><s:if test="%{mgrBusinessEntity.accountStatus==1}"><em class="green1">启用</em></s:if><s:if test="%{mgrBusinessEntity.accountStatus==0}"><em class="red1">禁用</em></s:if></li> 
            <li><span class="fl w85 t-r">业务类型：</span><s:if test="%{mgrBusinessEntity.businessType==1}">上下班</s:if><s:if test="%{mgrBusinessEntity.businessType==2}">包车</s:if><s:if test="%{mgrBusinessEntity.businessType==3}">两者都支持</s:if></li> 
            <li style="height:250px;width:100%"><span class="fl w85 t-r">商户介绍：</span><span><textarea class="r-input more-show w87p" id="content"   name="news.newsContext" readonly="readonly">${mgrBusinessEntity.remark }</textarea></span></li> 
        </ul>
    </div>
</div>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	//浏览器可视窗口的的高度
	$(".sys-text").css("min-height",($(window).height()-120+"px"));
	$(window).resize(function() {
		$(".sys-text").css("min-height",($(window).height()-120+"px"));
	}); 
})
	//创建文字编辑器
var editor;
KindEditor.ready(function(K) {
	 editor = K.create('textarea[name="news.newsContext"]', {
		cssPath : '../js/kindeditor-4.1.1/plugins/code/prettify.css',
		uploadJson : '../js/kindeditor-4.1.1/jsp/upload_json.jsp',
		fileManagerJson : '../js/kindeditor-4.1.1/jsp/file_manager_json.jsp',
		allowFileManager : true,
		items : ['source', '|', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
		         'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
		         'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
		// items:[],
		afterCreate : function() {
			var self = this;
			K.ctrl(document, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
			K.ctrl(self.edit.doc, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
			self.readonly(true);//只读
		}
	});
});

//返回上一步
function goBack(){
	var $the_url='';
	//判断是否是IE浏览器
	if(navigator.userAgent.indexOf("MSIE")>0){   
		$the_url="../getMerchantList.action?level=two";
	}
	//谷歌和火狐
	if(navigator.userAgent.indexOf("Chrome") > -1||navigator.userAgent.indexOf("Firefox") !=-1){
		$the_url="getMerchantList.action?level=two";
	}
	
	window.location.href=$the_url;
}
</script>
</body>
</html>
