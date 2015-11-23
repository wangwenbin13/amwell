<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拼车管理-敏感词过滤</title>
<%@include file="../resource.jsp" %>
</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;拼车管理&nbsp;>&nbsp;敏感词过滤<span class="blue1 ml25"></span></p></div>
<form id="updateForm" action="../sensitiveWord/updateSensitiveWord.action" method="post">
<input type="hidden" name="wordId" value="${sysSensitiveWordVo.wordId}"/>
 <div class="mt10 ml20 mr20 black1">
          <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">敏感词过滤</a></li>
                </ul>
            </div>
         <div class="sys-text sh-add-new ">
			 <ul class="clearfix">
			   <li class="clearfix f12" style="height:auto">
			 	<span class="fl w75 t-r">敏感词：</span>
			 	<textarea style="width:700px;min-height:350px;" class="r-input more-show fl" id="sensitiveWord" name="sensitiveWord" onkeyup="if(this.value!=null&&this.value!=''&&this.value.length>2000) this.value=this.value.substr(0,2000)">${sysSensitiveWordVo.sensitiveWord}</textarea>
			  </li>
			 	<li class="clearfix gray1 f12"><span class="fl w65 h24"></span>&nbsp;敏感词最大长度为2000,多个敏感词以空格隔开。敏感词在前端，以“**”显示</li>
			 	<li class="clearfix f12"><span class="fl w75 t-r">更新时间：</span>${sysSensitiveWordVo.operateOn}</li>
			 	<li class="clearfix f12"><span class="fl w75 t-r">操作人：</span>${sysSensitiveWordVo.userName}</li>
			 </ul>  
			 <p class="t-c mt20"><input type="button" value="保存" id="submitButton" class="display-ib btn1 white mr40" onclick="updateSensitiveWord();"/>
		</div>
</div>
</form>
</body>
<script>
//sys-text高度
$(function(){	
	$(".sys-text").css("min-height",$(window).height()-120+"px");
	$(window).resize(function(){
		$(".sys-text").css("min-height",$(window).height()-120+"px");	
	});
});
function updateSensitiveWord(){
   $("#updateForm").ajaxSubmit({
		type : 'post',
		success : function(data) {
			if (data == "success") {
				parent.showRturnTip("修改成功",true);
				$("#iframe_right", parent.parent.window.document).attr("src","../sensitiveWord/toSensitiveWord.action?level=two");
			}else if("error"==data){
				parent.popLodeShowPage(false);
				parent.showRturnTip("修改失败",false);
			}
		}
  });
}
</script>
</html>
