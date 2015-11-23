<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠券列表</title>
<%@include file="../resource.jsp" %>
</head>
  
 <body>
 <input type="hidden" id="nowTime" value="${nowTime }"/>
<input type="hidden" id="pageSizeEm" />
<input type="hidden" id="currPageEm" />
<input type="hidden" id="totalCountEm" />
    <div id="mainBody" class="mainBody"></div>
	<div id="showPage" class="showPage"></div>
	<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;优惠券列表</p></div>  
	<div class="mt10 ml20 mr20 black1">
	   <div class="table-title">
	        <ul>
	        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">优惠券列表</a></li>
	        </ul>
	        <a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
	    </div>
	    <s:form method="post" action="../coupon/toList.action" theme="simple">
		    <ul class="r-sub-sel black1 mt20 clearfix">   
		    	<li class="w21p">优惠券名称：<s:textfield name="search.field01" theme="simple" cssClass="r-input w67p gray2" id="couponName"/></li> 
		    	<li>
	    			<span class="t-r w65 fl">有效时间：</span>
		               <span class="r-input fl w33p mr10"><input type="text" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})" id="txtStartDate" readonly="readonly" class="Wdate75 gray2 Wdate" value="${search.field02 }" name="search.field02"/></span>
		            <span class="fl">至</span>
		            <span class="r-input fl w33p ml10"><input type="text" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})" id="txtEndDate" readonly="readonly" class="Wdate75 gray2 Wdate" value="${search.field03 }" name="search.field03"/></span>
		    	</li>  
		    	<li><input type="submit" value="查找" class="btn-blue4" /></li>
		    </ul>
		</s:form>
	   <div id="loadCouponCount"></div>
	    <div class="table-outline mt20">
	         <div class="table2-text sh-ttext clearfix">
                 <table width="100%" border="0" class="table1 tableGf" id="tableDivSearch">
                      <tr align="center">
                     <th scope="col">名称</th>
                     <th scope="col" width="5%">推广位置</th>
                     <th scope="col" width="5%">发放方式</th>
                     <th scope="col" width="7%">用户类型</th>
                     <th scope="col" width="10%">礼品类型</th>
                     <th scope="col" width="9%">上线时间</th>
                     <th scope="col" width="13%">有效期</th>
                     <th scope="col" width="8%">发行量</th>
                     <th scope="col" width="7%">限领数量</th>
                     <th scope="col" width="8%">配置人</th>
                     <th scope="col" width="7%">数据统计</th>
                     <th scope="col" width="11%">操作</th>
                   </tr>
                </table>
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
    </div>
</body>
</html>
<script type="text/javascript" src="../js/common/page3.js"></script>
<script>
var jsonDate = {
		"pageSize":"1",
		"totalCount":"1",
		"currPage":"1",
		"resultData":[{
			"couponId":"10001",//主健id
			"cityName":"深圳",//使用城市
			"couponName":"新用户10元礼包", //优惠券名称
			"couponTypeStr":"通用", //推广位置
			"sendCouponTypeStr":"10", //发放方式
			"selectPassStr":"无", //用户类型
			"giftTypeStr":"",//礼品类型
			"upLineTime":"2015-03-10 12:00",//上线时间
			"downLineTime":"2015-03-20 12:00",//下线时间
			"validity":"",//有效期
			"issueNum":"10000",//发行量
			"limitNum":"10",//限领数量
			"userName":"zhangsan"//配置人
		}]
}
//增加优惠券
function showAddPage()
{
	$("#iframe_right", parent.parent.window.document).attr("src","../coupon/setCoupon.action");
}
//修改优惠券
function toUpdateCoupon(couponId)
{   
    $.ajax({
			url:'../coupon/checkCouponGetState.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{couponId:couponId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="true"){
			       parent.parent.showRturnTip("优惠券已有用户领取，不可修改!",false);
			    }else{
			       $("#iframe_right", parent.parent.window.document).attr("src","../coupon/toUpdate.action?couponId="+couponId);
			    }
			}
	}); 
	
}
//查看数据
function viewCouponDate(couponId,couponName)
{
	$("#iframe_right", parent.parent.window.document).attr("src","../couponStat/toCouponDate.action?couponId="+couponId+"&couponName="+encodeURI(encodeURI(couponName)));
}
//关闭优惠券
function closeCoupon(couponId)
{
// 	alert(couponId);
	parent.parent.showPopCommonPage("确定要关闭此优惠券吗？");         
 	$("#commonSure", parent.window.document).unbind('click')   
	$("#commonSure",parent.window.document).click(function(){
		parent.parent.closePopCommonPage(); 
			$.ajax({
					url:'../coupon/closeCoupon.action?&random='+Math.floor(Math.random()*10000+1),
					type:'post',
					data:{couponId:couponId},
					cache:false,	
					dataType:"text",	
					success:function(data){	
					    if(data=="success"){
					       parent.parent.showRturnTip("优惠券关闭成功!",true);
					       $("#iframe_right",parent.parent.window.document).attr("src","../coupon/toList.action");
					    }else{
					       parent.parent.showRturnTip("优惠券关闭失败!",false);
					    }
						closePopDateDetailPage();
					},
					error:function(){
						parent.parent.showRturnTip("优惠券关闭失败!",false);
						closePopDateDetailPage();
					}
			}); 
	
	    
			$("#commonSure",parent.window .document).unbind("click")
	  }); 
	  
	  $("#commonCancel",parent.parent.window.document).click(function(){
  	   		parent.parent.closePopCommonPage();
  	  		//解绑定，防止多次执行click事件
	 		$("#commonSure",parent.parent.window.document).unbind('click');
     }); 
                         
}

$(function(){
	//清除IE缓存
	$.ajaxSetup ({ 
		cache: false 
	});
	//加载优惠券统计数量
	loadCouponCount();
});

//加载优惠券统计数量
function loadCouponCount(){
	var couponName = $("#couponName").val();
	var txtStartDate = $("#txtStartDate").val();
	var txtEndDate = $("#txtEndDate").val();
	var url = "../couponStat/stat.action?search.field01="+encodeURI(encodeURI(couponName))+"&search.field02="+txtStartDate+"&search.field03="+txtEndDate;
	$("#loadCouponCount").load(url);
}

$(function(){	
	loadDetailDateEm();
});

//查找
function searchData()
{
	$("#currPageEm").val("1");
	 //清掉勾选的值
    $("#selectData").val("");
    loadDetailDateEm();
}

//调用后台   加载数据
function loadDetailDateEm()
{
	var currPage = $("#currPageEm").val();
	if(currPage == "" || typeof(currPage) == "undefined")
	{
		currPage = 1;
	}
	var couponName=encodeURI($("#couponName").val());
	$.ajax({
		url : "../coupon/getCouponList.action",
		type : "post",
		dataType : "json",
		data : {
			pageSize : $("#pageSizeEm").val(),
			currPage : currPage,
			totalCount : $("#totalCountEm").val(),
			couponName:couponName,
			effectiveTime:$("#txtStartDate").val(),
			expirationTime:$("#txtEndDate").val()
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
	checkShowPage(currPageEm);
}
//无数据
function createNoDate(id)
{
	$("#"+id+" tr").eq(0).nextAll().remove(); //除第一行外删除所有的tr
	$("#"+id).append("<tr align='center' class='tr bg1'><td colspan='12'><p class='f13 f-yahei gray4 t-c line26 mt10'><span class='index-nodate mr10'></span><span id='noDate'>暂无数据</span></p></td></tr>");
}
//有数据
function createDate(id,jsonDate)
{
	var nowTime=$("#nowTime").val();
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
		var $trs = $tr + "<td class='f-arial'>"+jsonDate.resultData[i].couponName+"</td>"+
			"<td>"+jsonDate.resultData[i].couponTypeStr+"</td>"+
			"<td>"+jsonDate.resultData[i].sendCouponTypeStr+"</td>"+
			"<td>"+jsonDate.resultData[i].selectPassStr+"</td>"+
			"<td>"+
				"<p>"+jsonDate.resultData[i].giftTypeStr+"</p>"+
			"</td>"+
			"<td class='f-arial'>"+jsonDate.resultData[i].upLineTime+"</td>"+
			"<td class='f-arial'>"+
				"<p>"+jsonDate.resultData[i].validity+"</p>"+
			"</td>"+
			"<td class='f-arial'>"+jsonDate.resultData[i].issueNum+"</td>"+   
			"<td class='f-arial'>"+jsonDate.resultData[i].limitNum+"</td>"+
			"<td class='f-arial'>"+
				"<p>"+jsonDate.resultData[i].userName+"</p>"+
			"</td>"+
			"<td><a href='javascript:void(0);' class='blue1'  onclick='viewCouponDate(\""+jsonDate.resultData[i].couponId+"\",\""+jsonDate.resultData[i].couponName+"\");'>数据查看</a></td>"+
			"<td><a href='javascript:void(0);' class='blue1'  onclick='toUpdateCoupon(\""+jsonDate.resultData[i].couponId+"\");'>修改</a>";
			if(jsonDate.resultData[i].downLineTime>nowTime){
				$trs+="<a href='javascript:void(0);' class='blue1 ml10'  onclick='closeCoupon(\""+jsonDate.resultData[i].couponId+"\");'>关闭</a>"
			}else{
				$trs+="<a  class=' ml10' >已关</a>"
			}
			$trs+="</td></tr>";
		$("#"+id).append($trs);
	}                	
}

</script>
