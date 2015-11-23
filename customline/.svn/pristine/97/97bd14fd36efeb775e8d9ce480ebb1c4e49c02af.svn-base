<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商管理-线路成本设置历史</title>
<jsp:include page="../resource.jsp"/>

<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
/* box */
.showbox{width:0px;height:0px;display:none;position:absolute;right:0;top:0;z-index:100;background:#fff;}
.showbox h2{height:30px;font-size:14px;background-color:#0093dd;position:relative;line-height:30px;color:#fff;border:1px #8FA4F5 solid;border-bottom:0;padding:1px 0 0 10px;}
.showbox h2 a{position:absolute;right:5px;top:0;font-size:12px;color:#fff;}
.showbox .mainlist{padding:10px 10px 20px;}
.showbox .mainlist li{height:24px;line-height:24px;}
.showbox .mainlist li span{margin:0 5px 0 0;font-family:"宋体";font-size:12px;font-weight:400;color:#ddd;}
.showbox .mainlist li a{font-size:12px;}
.mask{background:#666;position:absolute;z-index:99;left:0;top:0;display:none;width:100%;height:100%;opacity:0.5;filter:alpha(opacity=50);-moz-opacity:0.5;}
.showboxContent{height:auto;background:#fff;border:1px #8FA4F5 solid;border-top:0;padding:1px;}
.textarea{resize:none;width:100%;height:80px;text-index:5px;padding-top:5px;}
</style>


</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;线路成本设置历史</p></div>
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">线路成本设置历史</a></li>
           </ul>
       </div>
      <div class="table2-text sh-ttext mt10">       
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="4%">序号<span class="arrowes acs"><!--升序追加样式acs，降序追加样式desc --></span></th>
                      <th scope="col" width="5%">所属地区</th>
                      <th scope="col" width="6%">供应商</th>
                      <th scope="col" width="6%">线路类型</th>
                      <th scope="col" width="6%">线路名称</th>
                      <th scope="col" width="4%">发车时间</th>
                      <th scope="col">起点/终点</th>
                      <th scope="col" width="7%">通票价格</th>
                      <th scope="col" width="9%">成本分摊方式</th>
                      <th scope="col" width="8%">成本分摊</th>
                      <th scope="col" width="14%">有效期</th>
                    </tr>
                    <s:if test="null==lineCostSetList || lineCostSetList.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="11"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无历史信息</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="lineCostSetList" var="lineBusiness" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" id="${lineBusiness.lineBaseId}">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" id="${lineBusiness.lineBaseId}">
						</s:if>
						<th>
						${s.index+1}
						<input name="lineBaseId" value="${lineBusiness.lineBaseId}" type="hidden">
	                    <input name="lineCostSetId" value="${lineBusiness.id}" type="hidden">
						</th>
                    	<td class="f-arial">${lineBusiness.cityName}</td>
						<td class="f-arial"><s:if test="%{#lineBusiness.brefName==null}">—</s:if><s:else>${lineBusiness.brefName}</s:else></td>
                    	<td class="f-arial"><s:if test="%{#lineBusiness.lineSubType==0}">上下班</s:if><s:if test="%{#lineBusiness.lineSubType==1}">自由行</s:if></td>
                    	<td class="f-arial">${lineBusiness.lineName}</td>
                    	<td class="f-arial">${lineBusiness.orderStartTime}</td>
                    	<td class="f-arial">${lineBusiness.startStationName}<span class="display-ib lineArrow mr5 ml5 vf6"></span>${lineBusiness.endStationName}</td>
                    	<td class="f-arial"><em class="red1 fw">${lineBusiness.orderPrice}</em>元/次</td>
	                    	<td class="f-arial">
	                    	<s:if test="%{#lineBusiness.theMode == 'fixed base price'}">
								固定底价
							</s:if>
							<s:if test="%{#lineBusiness.theMode == 'commission'}">
								佣金分成
							</s:if>
	                    	</td>
	                    	<td class="f-arial"><span>${lineBusiness.costSharing}</span>
	                    	<s:if test="%{#lineBusiness.theMode == 'fixed base price'}">
								元/天
							</s:if>
							<s:else>
							    %
							</s:else>
	                    	</td>
	                    	<td class="f-arial"><s:if test="%{#lineBusiness.startTime != null && #lineBusiness.startTime != ''}">${lineBusiness.startTime}至${lineBusiness.endTime}</s:if></td>
                    	</tr>
					</s:iterator>
                  </table>
          </div>
          <s:reset value="返回" cssClass="btn-blue4" onclick="javascript:window.location.href='../lineCostSet/getAllLineAndCost.action'" cssStyle="margin-top:20px;"/>
       </div>
</body>
<script type="text/javascript">
$(function(){
	//表格标题点击排序样式更改
	$(".table3 th").toggle(function(){
		$(this).find("span").removeClass("acs").addClass("desc");
	},function(){
		$(this).find("span").removeClass("desc").addClass("acs");
	});
});
</script>
</html>