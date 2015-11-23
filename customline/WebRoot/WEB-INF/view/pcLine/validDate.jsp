<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拼车管理-线路有效期设置</title>
<%@include file="../resource.jsp" %>

</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;拼车管理&nbsp;>&nbsp;线路有效期<span class="blue1 ml25"></span></p></div>
 <form id="updateForm" action="../lineValidDate/updateValidDate.action" method="post">
 <input type="hidden" name="id" value="${pcLineValidityVo.id}"/>
 <div class="mt10 ml20 mr20 black1">
          <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">线路有效期</a></li>
                </ul>
            </div>
         <div class="sys-text sh-add-new ">
			 <ul class="clearfix mt20">
			   <li class="clearfix f12">
			 	<span class="fl w100 t-r">线路有效期设置：</span>
			 	<input type="text" name="validityDays" class="r-input w110 mr5" value="${pcLineValidityVo.validityDays}"/>天
			  </li>
			 	<li class="clearfix f12"><span class="fl w100 t-r">更新时间：</span>${pcLineValidityVo.operateOn}</li>
			 	<li class="clearfix f12"><span class="fl w100 t-r">操作人：</span>${pcLineValidityVo.userName}</li>
			 </ul>  
			 <p class="ml20 mt20"><input type="button" value="保存" id="submitButton" class="display-ib btn1 white mr40" onclick="updateValidity();"/>
		</div>
</div>
</form>
</body>
<script>
function updateValidity(){
 $("#updateForm").ajaxSubmit({
		type : 'post',
		success : function(data) {
			if (data == "success") {
				parent.showRturnTip("修改成功",true);
				$("#iframe_right", parent.parent.window.document).attr("src","../lineValidDate/toLineValidDate.action?level=two");
			}else if("error"==data){
				parent.popLodeShowPage(false);
				parent.showRturnTip("修改失败,有效期必须为大于0的整数",false);
			}
		}
  });

}
//sys-text高度
$(function(){	
	$(".sys-text").css("min-height",$(window).height()-120+"px");
	$(window).resize(function(){
		$(".sys-text").css("min-height",$(window).height()-120+"px");	
	});
});
</script>
</html>
