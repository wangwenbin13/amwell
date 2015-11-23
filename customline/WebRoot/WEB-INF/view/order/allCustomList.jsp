<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单管理-用户乘车查询</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;订单管理&nbsp;>&nbsp;用户乘车查询</p></div>
  <div class="mt10 ml20 mr20 black1">
    <div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">用户乘车明细</a></li>
        </ul>
    </div>
    
   <s:form name="" action="" method="post" theme="simple">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w30p">
           	<span class="t-r w65 fl">时间：</span>
               <span class="r-input fl w34p mr10"><input type="text" name="" value="" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w34p ml10"><input type="text" name="" value="" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
        <li class="w20p"><span class="t-r w65 fl">乘客：</span><input type="text" name="" value="" class="r-input w67p gray2"/></li>
        <li class="w20p"><span class="t-r w65 fl">线路类型：</span>
        	<select class="w68p p3">
	        	<option>上下班</option>
	            <option>自由行</option>
	        </select>
        </li>
        <li class="w20p"><span class="t-r w65 fl">商家：</span><input type="text" name="" value="" class="r-input w67p gray2"/></li>
        <li class="w30p">
           	<span class="t-r w65 fl">扣款金额：</span>
            <input type="text" name="" value="" class="r-input w34p gray2 fl mr10" />
            <span class="fl">至</span>
            <input type="text" name="" value="" class="r-input w34p gray2 fl ml10"/>
        </li>
        <li class="w20p"><span class="t-r w65 fl">线路名称：</span><input type="text" name="" value="" class="r-input w67p gray2"/></li>
        <li class="w20p"><span class="t-r w65 fl">付款方式：</span>
        	<select class="w68p p3">
	        	<option>所有扣款方式</option>
	            <option>刷卡上次</option>
	            <option>系统付款</option>
	        </select>
        </li>
        <li class="w20p ml30"><input type="submit" class="btn-blue4" value="查找" /><a href="javascript:void(0);" class="blue1 ml10">下载搜索结果</a></li>
  	</ul>   
  </s:form> 
   
   
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table1" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="15%">扣款时间</th>
                      <th scope="col" width="10%">乘客</th>
                      <th scope="col" width="5%">线路类型</th>
                      <th scope="col" width="10%">服务商家</th>
                      <th scope="col" width="15%">线路名称</th>
                      <th scope="col" width="10%">班次</th>
                      <th scope="col" width="10%">扣款金额（元）</th>
                      <th scope="col" width="10%">付款方式</th>
                      <th scope="col" width="10%">购买方式（剩余）</th>
                    </tr>
                    <tr class="bg1 tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
                    	<td>1</td>
                    	<td class="f-arial">2014-07-30 10:15</td>
                    	<td class="f-arial">10058/飞翔的企鹅</td>
                    	<td class="f-arial">上下班</td>
                    	<td class="f-arial">侨城租赁</td>
                    	<td class="f-arial">15895231/龙华-宝安</td>
                    	<td class="f-arial">09:00</td>
                    	<td class="f-arial"><em class="green1 fw">50.00</em></td>
                    	<td class="f-arial">刷卡上车</td>
                    	<td class="f-arial">按次(10次)</td>
                    </tr>                   
                  </table>
                  
          	</div>
          	 <!--Start page-->
                  <div class="page t-c mt20  fl widthfull" id="pageDiv">
                       <a href="javascript:void(0);" id="homePage">首页</a>
                       <a href="javascript:void(0);" id="prePage">上一页</a>
                       <ul id="pageNumDiv">
                       		<li><a id="goPage1" class="on" style="cursor: default;">1</a></li>
                       		<li><a id="goPage2" style="cursor: point;">2</a></li>
                       		<li><a id="goPage3" style="cursor: point;">3</a></li>
                       </ul>
                       <a href="javascript:void(0);" id="nextPage">下一页</a>
                       <a href="javascript:void(0);" id="endPage">末页</a>
                       <span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');"/>页</span>
                       <span class="ml25">[共<span id="totalPageNum" class="fw">3</span>页]</span>
                 </div>
                  <!--End page-->
          </div>
      
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();
});

function clearInputDefaultVal()
{
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
				
			} 
		})  
	});
}
</script>