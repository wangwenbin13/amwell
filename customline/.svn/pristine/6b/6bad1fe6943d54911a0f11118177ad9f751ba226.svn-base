<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../js/common/Util.js" type="text/javascript"></script>
<title>用户、订单管理-所有订单查询</title>
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
.orderDate:hover{cursor:default;position:relative;}
.stations{width:100%;display:none;position:absolute;top:34px;border:1px solid gray;border-top:none;background-color:#fff;}
.stations .up{color:green;}
.stations .down{color:red;}
</style>
</head>

<body>

<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;用户、订单管理&nbsp;>&nbsp;所有订单查询</p></div>
  <div class="mt10 ml20 mr20 black1">
    <div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">所有订单查询</a></li>
        </ul>
    </div>
    
   <form name="form1" action="" method="post" id="searchForm">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w30p">
           	<span class="t-r w65 fl">时间：</span>
               <span class="r-input fl w33p mr10"><input type="text" name="search.field01" value="${search.field01 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w33p ml10"><input type="text" name="search.field02" value="${search.field02 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
        <li class="w20p"><span class="t-r w65 fl">订单号：</span><s:textfield name="search.field03" theme="simple" cssClass="r-input w67p gray2"/></li>
        <li class="w20p"><span class="t-r w65 fl">线路类型：</span>
        	<s:select cssClass="w68p p3" list="#{3:'全部',0:'上下班',1:'自由行'}" theme="simple" name="search.field04">
	        </s:select>
        </li>
        <li class="w20p"><span class="t-r w65 fl">线路名称：</span><s:textfield name="search.field05" theme="simple" cssClass="r-input w67p gray2"/></li>
        <li class="w30p">
           	<span class="t-r w65 fl">乘客：</span>
           	<s:textfield name="search.field06" theme="simple" cssClass="r-input w32p gray2 fl mr10"/>
            <span class="fl">商家：</span>
            <s:textfield name="search.field07" theme="simple" cssClass="r-input w31p gray2 fl"/>
        </li>
      
        <li class="w20p"><span class="t-r w65 fl">支付方式：</span>
        	<s:select cssClass="w68p p3" list="#{7:'所有方式',0:'无',1:'余额支付',2:'财付通',3:'银联',4:'支付宝',5:'微信',6:'(APP)微信'}" theme="simple" name="search.field09">
	        </s:select>
        </li>
        <li class="w20p"><span class="t-r w65 fl">支付状态：</span>
        	<s:select cssClass="w68p p3" list="#{5:'所有状态',0:'未支付',1:'已支付'}" theme="simple" name="search.field10">
	        </s:select>
        </li>
        <li class="w20p"><span class="t-r w65 fl">订单来源：</span>
        	<s:select cssClass="w67p p3 fl" list="#{3:'所有方式',0:'APP',1:'微信',2:'pc'}" theme="simple" name="search.field11">
	        </s:select>
        </li>       
        <li class="w30p"><span class="t-r w65 fl">省份城市：</span>
            <c:if test="${provinceCode!=null&&provinceCode!=''&&cityCode!=null&&cityCode!=''}">
               <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="0" headerValue="--选择省份--" name="search.field13" cssClass="fl r-input mr10 w36p" disabled="true" onchange="loadCity(this.value);"/>
	            <select name="search.field14" id="cityCode" class="fl r-input w36p" disabled="true"><option value="">--选择城市--</option></select>
            </c:if>
            <c:if test="${provinceCode==null||provinceCode==''||cityCode==null||cityCode==''}">
                <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="0" headerValue="--选择省份--" name="search.field13" cssClass="fl r-input mr10 w36p" onchange="loadCity(this.value);"/>
	            <select name="search.field14" id="cityCode" class="fl r-input w36p"><option value="">--选择城市--</option></select>
            </c:if>
        </li>
        <li class="w20p">
        	<span class="t-r w65 fl">联系电话：</span><s:textfield name="search.field12" theme="simple" cssClass="r-input w67p gray2"/>
        </li>
        <li class="w21p ml30">
        <input type="submit" class="btn-blue4" value="查找" />
        <s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchForm')"/>
        <a href="javascript:void(0);" class="blue1 ml10" onclick="toExport();">下载搜索结果</a>
        <a href="javascript:void(0);" class="blue1 ml10" onclick="toCompare();">第三方财务核对</a>
        </li>
  	</ul>   
  </form> 
   
   
      <div class="table2-text sh-ttext mt20">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv" style="margin-bottom:100px;">
                    <tr align="center" class="th">
                      <th scope="col" width="3%">序号</th>
                      <th scope="col" width="8%">时间</th>
                      <th scope="col" width="10%">订单号</th>
                      <th scope="col" width="5%">乘客</th>
                      <th scope="col" width="5%">线路类型</th>
                      <th scope="col" width="5%">服务商家</th>
                      <th scope="col" width="10%">线路名称</th>
                      <th scope="col">上车点</th>
                      <th scope="col">下车点</th>
                       
                      <th scope="col" width="5%">购买数量</th>
                       
                      <th scope="col" width="5%">省份城市</th>
                      <th scope="col" width="6%">金额（元）</th>
                      <th scope="col" width="5%">订单来源</th>
                      <th scope="col" width="5%">支付方式</th>
                      <th scope="col" width="7%">支付状态</th>
                      <th scope="col" width="8%">联系电话</th>
                    </tr>
                    
                   
                  <s:if test="%{page.pageCount == 0}">
                     
                           <tr align="center" class="bg1">
              					<td colspan="13"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                  	<s:iterator value="leaseOrderModelVos" var="leaseOrderVo" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" leaseOrderId ="${leaseOrderVo.leaseOrderId}">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" leaseOrderId ="${leaseOrderVo.leaseOrderId}">
						</s:if>
							<td class="f-arial">${currentPageIndex+s.count}</td>
                    		<td class="f-arial">${leaseOrderVo.leaseOrderTime}</td>
                    		<td class="f-arial">${leaseOrderVo.leaseOrderNo}</td>
                    		<td class="f-arial">${leaseOrderVo.displayId}/${leaseOrderVo.nickName }</td>
                    		<td class="f-arial">
                    			<s:if test="%{#leaseOrderVo.lineSubType==0}"><em>上下班  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.lineSubType==1}"><em>自由行  </em></s:if>
                    		</td>
                    		<td class="f-arial">${leaseOrderVo.brefName}</td>
                    		<td class="f-arial">${leaseOrderVo.lineName}</td>
                    		<td class="f-arial">
                    			<span id="context${s.index }" title="${leaseOrderVo.ustation } "><script type="text/javascript">$("#context"+${s.index }).text(Util.cutstr('${leaseOrderVo.ustation }',10))</script></span>
                    		</td>
                    		<td class="f-arial">
                    			<span id="context_${s.index }" title="${leaseOrderVo.dstation } "><script type="text/javascript">$("#context_"+${s.index }).text(Util.cutstr('${leaseOrderVo.dstation }',10))</script></span>
                    		</td>
                    		
                    		
                    		<td class="f-arial orderDate">${leaseOrderVo.ridesInfo }
                    		<ul class="stations" style="max-height:150px;overflow-y:auto;">
                    		<li class="red1">乘车日期:</li>
                    			<c:forEach items="${leaseOrderVo.lineClassEntities }" var="lineClass">
	                    			<li class="red1">${lineClass.orderDate }</li>
                    			</c:forEach>
                    			
                    		</ul>
                    		
                    		</td>
                    		<td class="f-arial">
                    			${leaseOrderVo.provinceCode}/${leaseOrderVo.cityCode }
                    		</td>
                    		<td class="f-arial">${leaseOrderVo.payMoney}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#leaseOrderVo.sourceFrom==0}"><em>APP  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.sourceFrom==1}"><em>微信  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.sourceFrom==2}"><em>pc  </em></s:if>
                    		</td>
                    		<td class="f-arial">
                    			<s:if test="%{#leaseOrderVo.payModel==0}"><em>无  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.payModel==1}"><em>余额支付  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.payModel==2}"><em>财付通  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.payModel==3}"><em>银联  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.payModel==4}"><em>支付宝  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.payModel==5}"><em>微信  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.payModel==6}"><em>(APP)微信  </em></s:if>
                    		</td>
                    		<td class="f-arial">
                    			<s:if test="%{#leaseOrderVo.ispay==0}"><em class="red1">未支付  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.ispay==1}"><em class="green1">已支付 </em></s:if>
                    			<!-- 
                    			<s:if test="%{#leaseOrderVo.payStatus==2}"><em>已失效  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.payStatus==3}"><em>已取消  </em></s:if>
                    			<s:if test="%{#leaseOrderVo.payStatus==4}"><em>已删除  </em></s:if>
                    			 -->
                    		</td>
                    		<td class="f-arial">${leaseOrderVo.telephone}</td>
                    	</tr>
                   	</s:iterator>
                   	<s:if test="%{page.pageCount != 0}">
		              <tr align="center" class="tr fw">
		                <th  colspan="15" align="right"><input type="button" class="btn-blue4" value="总计" onclick="countTotalValue();"/>（元）：</th>
		                <th class="f-arial"><em class="yellow1 fw" id="totalMoney"></em></th>
		              </tr>
	              </s:if>
                  </table>                 
          	</div>
          	 <!--Start page-->
                  <div class="page t-c mt20  fl widthfull" id="pageDiv">
	                   <s:if test="%{currentPageIndex!=0}">
	                  		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
                     		<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
	                   </s:if>
                       <ul id="pageNumDiv">
                       		<s:iterator value="page.pageList">
							<s:if test="field02==1">
								<b><span class="current"><s:property value="field01" />
								</span>
								</b>
							</s:if>
							<s:else>
								<li>
									<a href="javascript:void(0);" onclick="toPage(${field03});"><s:property value="field01" /></a>
								</li>
							</s:else>
						</s:iterator>
                       </ul>
                       <s:if test="%{page.pageCount!=0 && ((currentPageIndex/pageSize)+1)!=page.pageCount}">
                       		<a href="javascript:void(0);" onclick="toPage(${page.nextIndex});">下一页</a>
                       		<a href="javascript:void(0);" onclick="toPage(${page.lastIndex});">末页</a>
                       </s:if>
                       <s:if test="%{page.pageCount!=1 && page.pageCount!=0}">
                       		<span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onblur="toJudgePage(this.value);"/>页</span>
                       </s:if>
                       <s:if test="%{page.pageCount!=0}">
                       		<span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页]</span>
                       </s:if>
                 </div>
                  <!--End page-->
          </div>
          <input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
<form action="../orderAction/getAllOrderList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
    <input type="text" name="search.field07" value="${search.field07 }"/>
    <input type="text" name="search.field08" value="${search.field08 }"/>
    <input type="text" name="search.field09" value="${search.field09 }"/>
    <input type="text" name="search.field10" value="${search.field10 }"/>
    <input type="text" name="search.field11" value="${search.field11 }"/>
    <input type="text" name="search.field12" value="${search.field12 }"/>
    <input type="text" name="search.field13" value="${search.field13 }"/>
    <input type="text" name="search.field14" value="${search.field14 }"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>       
<form action="../orderAction/exportExcel.action" method="post" style="display: none;" id="export">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
    <input type="text" name="search.field07" value="${search.field07 }"/>
    <input type="text" name="search.field08" value="${search.field08 }"/>
    <input type="text" name="search.field09" value="${search.field09 }"/>
    <input type="text" name="search.field10" value="${search.field10 }"/>
    <input type="text" name="search.field11" value="${search.field11 }"/><!-- 订单来源 -->
    <input type="text" name="search.field12" value="${search.field12 }"/><!-- 联系电话 -->
    <input type="text" name="search.field13" value="${search.field13 }"/>
    <input type="text" name="search.field14" value="${search.field14 }"/>
 </form>       
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();

	// 鼠标移入显示路线详情
	loadOriginalCity();
	$(".table3 .orderDate").mouseover(function(){
		$(this).children('.stations').css("z-index","1000").show();
		return false;
	});
	// 鼠标移出显示路线详情
	$(".table3 .orderDate").mouseout(function(){
		$(this).children('.stations').hide();
		return false;
	});
	
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

//翻页方法
function toPage(value){
	$("#currentPageIndex").val(value);
	$("#turnPage").submit();
}

//判断输入的参数是否符合规定
function toJudgePage(value){
	var totalPage = "${page.pageCount}";
	totalPage = parseInt(totalPage);
	if(""==value){
		return;
	}
	value = parseInt(value);
	if(value<1){
		parent.parent.showCommonTip("请输入大于0的数!");
		return;
	}else if(value>totalPage){
		parent.parent.showCommonTip("超出最大页数!");
		return;
	}
	var pageSize = "${pageSize}";
	toPage((value-1)*pageSize);
}

//导出
function toExport(){
	var listSize = "${listSize}";
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#export").submit();
}


/*分页enter按键处理函数*/
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		var currPage = $("#goPageNum").val();
        //输入为空不跳转
        if(currPage == "" || typeof(currPage) == "undefined")
    	{
    		return false;
    	}
	    toJudgePage(currPage);
		break;
	default:
		break;
	}
}

var ua = window.navigator.userAgent;
//判断浏览器类型
if (ua.indexOf("MSIE") >= 1 || ua.indexOf("Chrome") >= 1){
	//禁止后退键  作用于IE、Chrome  
	document.onkeydown = pressKey;
}else if(ua.indexOf("Firefox") >= 1){
	//禁止后退键 作用于Firefox、Opera  
	document.onkeypress = pressKey;  
}else{
	document.onkeypress = pressKey;  
}


//检查跳页是否大于总页数
function checkGoPageNum(pageNum)
{
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(pageNum > ${page.pageCount})
	{
	    $("#goPageNum").attr("value",${page.pageCount });
	}
}


//订单详情
$("#tableDiv tr td").click(function(){
	//如果是没有数据就不调用后面的方法
	if($("#noDate").html()!= undefined){	
		return false;
	}
	//获取选中文字，如果选中文字则不响应点击事件
	//ie8写法
	if(typeof(document.selection) != "undefined")
	{
		var selecter = document.selection.createRange().text;
	}
	else if(typeof(window.getSelection()) != "undefined")
	{
		var selecter = window.getSelection();
	}
	else
	{
		var selecter = "";
	}
	if(selecter != "")
	{
	 	return false;
	}
	else
	{
		var leaseOrderId = $(this).parent().attr("leaseOrderId");
		//跳转到订单详情
		$("#iframe_right",parent.window.document).attr("src","../orderAction/orderDetail.action?leaseOrderModelVo.leaseOrderId="+leaseOrderId+"&currentPageIndex="+$("#myCurrentPageIndex").val());  
	}
});

function loadOriginalCity(){
	   var province = "${search.field13}";
	   if(null!=province&&province.length>0&&province!="null"){
	      loadCity(province);
	   }
}

//根据省份加载城市
function loadCity(value){
  	$.ajax({
		url:'../merchantAction/getProvince.action?proId='+value,		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#cityCode").empty();
			$("#cityCode").append("<option value='0'>--选择城市--</option>");
			$(data).each(function(i){ //遍历结果数组
			   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
			loadSelectCity();
		}
	});
}

//加载原来的城市
function loadSelectCity(){
	var city = "${search.field14}";
		if(""!=city){
			var ops =  $("#cityCode option");
			for(var i=0;i<ops.length;i++){
				if(city==ops[i].value){
					ops[i].selected = true;
				}
			}
		}
}

//比较第三方的财务统计
function toCompare(){
	window.location.href="../compareOther/toCompareView.action";
}

//计算总计
function countTotalValue(){
	$.ajax({
		url:'../orderAction/countTotalValue.action',		
		cache:false,	
		dataType:"json",
		async : false,
		success:function(data){
			$("#totalMoney").html("￥"+data);
		}
	});
}

</script>