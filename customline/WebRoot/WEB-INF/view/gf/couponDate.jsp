<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>使用统计</title>
<%@include file="../resource.jsp" %>
</head>
  
 <body>
 	<input type="hidden" id="pageSizeEm" />
	<input type="hidden" id="currPageEm" />
	<input type="hidden" id="totalCountEm" />
    <div id="mainBody" class="mainBody"></div>
	<div id="showPage" class="showPage"></div>
	<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;使用统计</p></div>  
	<div class="mt10 ml20 mr20 black1">
	   <div class="table-title">
	        <ul>
	        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">使用统计</a></li>
	        </ul>
	    </div>
	    <s:form action="../couponStat/toCouponDate.action" method="post" theme="simple">
	    <input type="hidden" name="couponId" value="${couponId}" id="field01"/>
	    <input type="hidden" name="couponName" value="${couponName}"/>
	    <ul class="r-sub-sel black1 mt20 clearfix">
	    	<li class="w17p">线路名称：<input type="text" class="r-input w65p gray2" value="${search.field02 }" name="search.field02" id="field02"/></li>
	    	<li class="w17p">用户账号：<input type="text" class="r-input w65p gray2" value="${search.field03 }" name="search.field03" id="field03"/></li>
	    	<li class="w17p">使用状态：
				 <s:select list="#{2:'全部',1:'已使用',0:'未使用' }" listKey="key" listValue="value" cssClass="w65p p3" name="search.field04" id="field04"></s:select>
			</li>
	    	<li>
    			<span class="t-r w65 fl">有效时间：</span>
	               <span class="r-input fl w33p mr10"><input type="text" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd ',maxDate:'#F{$dp.$D(\'txtEndDate\');}',minDate:'${couponStatVo.effectiveTime}'})" id="txtStartDate" readonly="readonly" class="Wdate75 gray2 Wdate" value="${search.field05 }" name="search.field05"/></span>
	            <span class="fl">至</span>
	            <span class="r-input fl w33p ml10"><input type="text" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd ',minDate:'#F{$dp.$D(\'txtStartDate\');}',maxDate:'${couponStatVo.expirationTime}'})" id="txtEndDate" readonly="readonly" class="Wdate75 gray2 Wdate" value="${search.field06 }" name="search.field06"/></span>
	    	</li>  
	    	<li><input type="submit" value="查找" class="btn-blue4" /></li>
	    </ul>
	    </s:form>
	    <div class="tableGO mt10">
	    	<table width="100%" border="0" class="table1 tableG">
		    	<tr>
		    		<td width="15%">${couponName}发行总量：${couponStatVo.couponTotal}</td>
		    		<td width="15%">已使用：${couponStatVo.couponUser}</td>
		    		<td width="15%">未使用：${couponStatVo.couponUnUser}</td>
		    	</tr>
		    	<tr>
		    		<td width="15%">${couponName}发行总金额：${couponStatVo.couponTotalMon}</td>
		    		<td width="15%">已使用：${couponStatVo.couponUserMon}</td>
		    		<td width="15%">未使用：${couponStatVo.couponUnUserMon}</td>
		    	</tr>
		    	<tr>
		    		<td width="15%">${couponName}领取总量：${couponStatVo.couponGetTotal}</td>
		    		<td width="15%">已使用：${couponStatVo.couponGetUser}</td>
		    		<td width="15%">未使用：${couponStatVo.couponGetUnUser}</td>
		    	</tr>
		    	<tr>
		    		<td width="15%">${couponName}领取总金额：${couponStatVo.couponGetMon}</td>
		    		<td width="15%">已使用：${couponStatVo.couponGetUserMon}</td>
		    		<td width="15%">未使用：${couponStatVo.couponGetUnUserMon}</td>
		    	</tr>
		    </table>
	    </div>
	    <p class="mt20">使用明细：遇到多张优惠券的发放时,使用明细中用户使用了几张就保留几条记录</p>
	    <!--  
	    <div class="table-outline mt10">
	         <div class="table2-text sh-ttext clearfix">
           	 	<div style="overflow-x:auto;overflow-y:hidden">
                   <table width="100%" border="0" class="table1 tableGf" id="tableDiv">
                        <tr align="center">
	                      <th scope="col" width="10%">用户ID</th>
	                      <th scope="col" width="10%">用户账号</th>
	                      <th scope="col" width="10%">使用优惠券</th>
	                      <th scope="col" width="10%">使用时间</th>
	                      <th scope="col" width="10%">使用状态</th>
	                      <th scope="col">使用线路</th>
	                    </tr>
                    	<tr align="center">
	                    	<td class="f-arial">12444</td>
	                    	<td class="f-arial">13724327232</td>
	                    	<td>10元班车优惠券</td>
	                    	<td class="f-arial">2015-02-08 18:30</td>
	                    	<td>已使用</td>
	                    	<td>桃源居-科技园</td>
                    	</tr>
                    	<tr align="center">
	                    	<td class="f-arial">12444</td>
	                    	<td class="f-arial">13724327232</td>
	                    	<td>10元班车优惠券</td>
	                    	<td class="f-arial">2015-02-08 18:30</td>
	                    	<td>已使用</td>
	                    	<td>桃源居-科技园</td>
                    	</tr>
                  </table>
   				</div>
   			</div>
         </div>
         -->
        <div id="loadCouponCount"></div>
	    <div class="table-outline mt20">
	         <div class="table2-text sh-ttext clearfix">
           	 	<div style="overflow-x:auto;overflow-y:hidden">
                   <table width="100%" border="0" class="table1 tableGf" id="tableDivSearch">
                        <tr align="center">
	                      <th scope="col" width="10%">用户ID</th>
	                      <th scope="col" width="10%">用户账号</th>
	                      <th scope="col" width="10%">使用优惠券</th>
	                      <th scope="col" width="10%">使用时间</th>
	                      <th scope="col" width="10%">使用状态</th>
	                      <th scope="col">使用线路</th>
	                    </tr>
                  </table>
   				</div>
   			</div>
   			<div class="page t-c mt20" id="pageDivEm">
			       <a href="javascript:void(0);" id="homePageEm" onclick="goToPageEm('homePageEm');">首页</a>
			       <a href="javascript:void(0);" id="prePageEm" onclick="goToPageEm('prePageEm');">上一页</a>
			       <ul id="pageNumDivEm"></ul>
			       <a href="javascript:void(0);" id="nextPageEm" onclick="goToPageEm('nextPageEm');">下一页</a>
			       <a href="javascript:void(0);" id="endPageEm" onclick="goToPageEm('endPageEm');">末页</a>
			       <span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNumEm" onblur="goToPageEm('goPageNumEm');"  onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNumEm(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNumEm(this.value);"/>页</span>
			       <span class="ml25">[共<span id="totalPageNumEm" class="fw"></span>页]</span>
			   </div>
         </div>
        <p class="t-c mt20"><input type="button" value="返回" onclick="history.back();"  class="display-ib btn1 white"/></p>
         
    </div>
<script type="text/javascript" src="../js/common/page3.js?a=1"></script>
 <script type="text/javascript">

 $(function(){	
		loadDetailDateEm();
	});
 
 //调用后台   加载数据
function loadDetailDateEm()
{
	var currPageEm = $("#currPageEm").val();
	if(currPageEm == "" || typeof(currPageEm) == "undefined")
	{
		currPageEm = 1;
	}
	$.ajax({
		url : "../couponStat/toCouponUserRecord.action",
		type : "post",
		dataType : "json",
		data : {
			pageSize : $("#pageSizeEm").val(),
			currPage : currPageEm,
			totalCount : $("#totalCountEm").val(),
			"search.field01":$("#field01").val(),
			"search.field02":encodeURI(encodeURI($("#field02").val())),
			"search.field03":$("#field03").val(),
			"search.field04":$("#field04").val(),
			"search.field05":$("#txtStartDate").val(),
			"search.field06":$("#txtEndDate").val()
		},
		cache: false,//不从浏览器缓存中取
		async: false, //async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
		error : function() {
			createNoDate("tableDivSearch");
			//无数据时重置页码相关数据
			resetPage();
		},
		success : function(data) {
			jsonDate = data;
			//无数据时提示暂无数据
			if(jsonDate.resultData.length == 0)
			{
				createNoDate("tableDivSearch");
				//无数据时重置页码相关数据
				resetPage();
			}
			else
			{
				pageSizeEm = jsonDate.pageSize;//每页显示的条数
				totalCountEm = jsonDate.totalCount;//总条数
				currPageEm = jsonDate.currPage;//当前页码
				$("#pageSizeEm").val(pageSizeEm);
				$("#currPageEm").val(currPageEm);
				$("#totalCountEm").val(totalCountEm);
				createDate("tableDivSearch",jsonDate);
				checkShowPageEm(currPageEm);
			}
		}
	});
}

//无数据时重置页码相关数据
function resetPage()
{
	pageSizeEm = 0;//每页显示的条数
	totalCountEm = 0;//总条数
	currPageEm = 1;//当前页码
	$("#pageSizeEm").val(pageSizeEm);
	$("#currPageEm").val(currPageEm);
	$("#totalCountEm").val(totalCountEm);
	checkShowPageEm(currPageEm);
}
//无数据
function createNoDate(id)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	$("#"+id).append("<tr align='center' class='tr bg1'><td colspan='6'><p class='f13 f-yahei gray4 t-c line26 mt10'><span class='index-nodate mr10'></span><span id='noDate'>暂无数据</span></p></td></tr>");
}
//有数据
function createDate(id,jsonDate)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	for(var i = 0;i < jsonDate.resultData.length;i++)
	{
		var $tr = "";
		if(i % 2 == 0)
		{
			$tr = "<tr align='center' class='tr bg1'>";
		}
		else
		{
			$tr = "<tr align='center' class='tr bg2'>";
		}
		var $trs = $tr + "<td class='f-arial'>"+jsonDate.resultData[i].displayId+"</td>"+
			"<td>"+jsonDate.resultData[i].telephone+"</td>"+
			"<td>"+jsonDate.resultData[i].couponName+"</td>"+
			"<td>"+jsonDate.resultData[i].userTime+"</td>";
			if(1==jsonDate.resultData[i].useState){
				$trs += 
					"<td>"+
						"<p>已使用</p>"+
					"</td>";
			}else{
				$trs += 
					"<td>"+
						"<p>未使用</p>"+
					"</td>";
			}
			$trs += 
			"<td>"+
				"<p>"+jsonDate.resultData[i].lineName+"</p>"+
			"</td>"
			"</tr>";
		$("#"+id).append($trs);
	}                	
}
 </script>
</body>
</html>

