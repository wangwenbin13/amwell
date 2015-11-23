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
<title>线路管理-线路列表-所有定制线路</title>
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
.route-details:hover{cursor:default;position:relative;}
.stations{width:100%;display:none;position:absolute;top:34px;border:1px solid gray;border-top:none;background-color:#fff;}
.stations .up{color:green;}
.stations .down{color:red;}
</style>


</head>

<body>


   <s:form id="searchForm" name="searchForm" action="../line/getAllLines.action" method="post"  theme="simple" > 
   <input type="hidden" name="search.field10" id="myCurrentPageIndex" value="${currentPageIndex}"/>
   	 <ul class="r-sub-sel black1 mt20 clearfix">  
   	 	   	
        <li class="w14p"><span class="t-r w65 fl">线路名称：</span><s:textfield name="search.field01" cssClass="r-input w55p gray2"/></li>
        <li class="w14p"><span class="t-r w65 fl">车点Ａ：</span><s:textfield name="search.field02"  cssClass="r-input w55p gray2"/></li>
        <li class="w14p"><span class="t-r w65 fl">车点Ｂ：</span><s:textfield name="search.field08"  cssClass="r-input w55p gray2"/></li>
        <li class="w14p"><span class="t-r w65 fl">商家：</span><s:textfield name="search.field03" cssClass="r-input w55p gray2"/></li>
        <li class="w14p"><span class="t-r w65 fl">线路类型：</span><s:select name="search.field04" list="#{'':'所有类型',0:'上下班',1:'自由行'}"  listKey="key" listValue="value" cssClass="p3 w55p"/></li>
       	<li class="w14p"><span class="t-r w65 fl">车牌号：</span><s:textfield name="search.field13" cssClass="r-input w55p gray2"/></li>
        <li class="w29p"><span class="t-r w65 fl">省份城市：</span>
	        <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="" headerValue="--选择省份--" name="search.field11" cssClass="fl r-input mr10 w38p" onchange="loadCity(this.value);"/>
	        <s:select list="#{'':'--选择城市--'}" name="search.field12" id="cityCode" cssClass="fl r-input w38p"></s:select>
	        <input type="hidden" id="theSelectedCityCode" value="${search.field12}"/>
        </li>
        <li class="w14p"><span class="t-r w65 fl">线路状态：</span><s:select name="search.field05" list="#{3:'已发布','':'所有状态',0:'待调度',1:'调度中 ',2:'待发布',4:'已下线'}" listKey="key" listValue="value" cssClass="p3 w58p"/></li>
        <li class="w14p"><span class="t-r w65 fl">排序规则：</span><s:select name="search.field06" list="#{'':'所有排序',0:'按发布时间',1:'按票价高低',2:'按更新时间',3:'按发布状态'}" listKey="key" listValue="value" cssClass="p3 w58p" /></li>  
	   <li class="w14p"><span class="t-r w65 fl">每页展示：</span><s:select name="search.field07" list="#{'':'默认',10:'10条',20:'20条',30:'30条 ',50:'50条'}" listKey="key" listValue="value" cssClass="p3 w58p" /></li>
        <li><s:submit value="查找" onclick="query()" cssClass="btn-blue4"/>
        	<s:reset value="重置" cssClass="btn-blue4" onclick="clearFormValue('searchForm')"/>
        	<a class="blue1 ml10" href="javascript:void(0);" onclick="toExport();">下载搜索结果</a>
        </li>
  	</ul >  
  	</s:form>
   
   
      <div class="table2-text sh-ttext mt10">       
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="4%">序号<span class="arrowes acs"><!--升序追加样式acs，降序追加样式desc --></span></th>
                      <th scope="col" width="5%">所属地区</th>
                      <th scope="col" width="6%">供应商</th>
                      <th scope="col" width="6%">线路类型</th>
                      <th scope="col" width="6%">线路名称</th>
                      <th scope="col" width="4%">发车时间</th>
                      <th scope="col" width="6%">剩余购票天数</th>
                      <th scope="col">起点/终点</th>
                      <th scope="col" width="7%">通票价格</th>
                    <!--   <th scope="col" width="15%">更新时间</th> -->
                      <th scope="col" width="5%">归属人</th>
                      <th scope="col" width="5%">发布状态</th>
                      <th scope="col" width="20%">操作</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              					<input type="hidden" id="listSize" value="0" />
              				</tr>
             		 </s:if>
                    <s:iterator value="list" var="lineBusiness" status="s">
                    	<input type="hidden" id="listSize" value="1" />
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" lineBaseId="${lineBusiness.lineBaseId}" lineStatus="${lineBusiness.lineStatus}">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" lineBaseId="${lineBusiness.lineBaseId}" lineStatus="${lineBusiness.lineStatus}">
						</s:if>
						<th>${s.index+1}</th>
                    	<td class="f-arial">${lineBusiness.cityName}</td>
						<td class="f-arial"><s:if test="%{#lineBusiness.brefName==null}">—</s:if><s:else>${lineBusiness.brefName}</s:else></td>
                    	<td class="f-arial"><s:if test="%{#lineBusiness.lineSubType==0}">上下班</s:if><s:if test="%{#lineBusiness.lineSubType==1}">自由行</s:if></td>
                    	<td class="f-arial">${lineBusiness.lineName}</td>
                    	<td class="f-arial"><s:if test="%{#lineBusiness.ischange==1}"><font color='red'>${lineBusiness.orderStartTime}(预约修改中)</font></s:if><s:if test="%{#lineBusiness.ischange==0}">${lineBusiness.orderStartTime}</s:if></td>
                    	<td class="f-arial">
                    	    <s:if test="%{#lineBusiness.availableBuyDays<=1}">
                    	        <span style="color:red;">${lineBusiness.availableBuyDays}</span>
                    	    </s:if>
                    	    <s:if test="%{#lineBusiness.availableBuyDays>1}">
                    	        ${lineBusiness.availableBuyDays}
                    	    </s:if>
                    	    
                    	</td>
                    	
                    	<td class="f-arial route-details">${lineBusiness.startStationName}<span class="display-ib lineArrow mr5 ml5 vf6"></span>${lineBusiness.endStationName}
                      <ul class="stations">
                      <c:forEach  items="${lineBusiness.upStation }" var="up">
                      	<li class="fl red1">上车点:&nbsp;&nbsp;&nbsp;${up }</li><br>
                      </c:forEach>
                      <c:forEach items="${lineBusiness.downStation }" var="down">
                      	<li class="fl green1">下车点:&nbsp;&nbsp;&nbsp;${down }</li><br>
                      </c:forEach>
                       <!--  <li class="up">待修改接口</li> -->
                      <!--   <li class="down">待修改接口</li> -->
                      </ul>
                      </td>
                    	<td class="f-arial"><em class="red1 fw">${lineBusiness.orderPrice}</em>元/次</td>
                    	<%-- <td class="f-arial">${lineBusiness.updateOn}</td> --%>
                    	<td class="f-arial">${lineBusiness.userName}</td>
                    	<td class="f-arial">
                    	   <!--0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除-->
                    	   <s:if test="%{#lineBusiness.lineStatus==0}"><em class="red1">待调度</em></s:if>
                    	   <s:if test="%{#lineBusiness.lineStatus==1}"><em class="green1">调度中</em></s:if>
                    	   <s:if test="%{#lineBusiness.lineStatus==2}"><em class="red1">待发布</em></s:if>
                    	   <s:if test="%{#lineBusiness.lineStatus==3}"><em class="green1">已发布</em></s:if>
                    	   <s:if test="%{#lineBusiness.lineStatus==4}"><em class="gray1">已下线</em></s:if>
                    	</td>
                    	<th>
                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="allowance('${lineBusiness.lineBaseId}',1)">放票</a>
                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="allowance('${lineBusiness.lineBaseId}',2)">改价</a>
                    	   <s:if test="%{#lineBusiness.lineStatus==0}">
                    	     <a href="javascript:void(0);" class="fn blue1 mr10" onclick="deleteLine('${lineBusiness.lineBaseId}')">删除</a>
                    	   </s:if>
                    	   <s:if test="%{#lineBusiness.lineStatus==1}">
                    	     <a href="javascript:void(0);" class="fn blue1 mr10" onclick="deleteLine('${lineBusiness.lineBaseId}')">删除</a>
                    	   </s:if>
                    	   <s:if test="%{#lineBusiness.lineStatus==2}">
                    	     <a href="javascript:void(0);" class="fn blue1 mr10" onclick="publishLine('${lineBusiness.lineBaseId}')">发布</a>
                    	     <a href="javascript:void(0);" class="fn blue1 mr10" onclick="deleteLine('${lineBusiness.lineBaseId}')">删除</a>
                    	   </s:if>
                    	   <s:if test="%{#lineBusiness.lineStatus==3}">
                    	     <a href="javascript:void(0);" class="fn blue1 mr10" onclick="offLine('${lineBusiness.lineBaseId}')">下线</a>
                    	     <a href="javascript:void(0);" onclick="getOrderNum('${lineBusiness.lineBaseId}')" class="fn blue1 mr10" >购票人数</a>
                    	     <a href="javascript:void(0);" onclick="copyLine('${lineBusiness.lineBaseId}')" class="fn blue1 mr10">复制</a>
                    	   </s:if> 
                    	   <s:if test="%{#lineBusiness.lineStatus==4}">
                    	     <a href="javascript:void(0);" class="fn blue1 mr10" onclick="publishLine('${lineBusiness.lineBaseId}')">上线</a><a href="javascript:void(0);" class="fn blue1 mr10" onclick="deleteLine('${lineBusiness.lineBaseId}')">删除</a>
                    	   </s:if>
                    	    <a href="javascript:void(0);" class="fn blue1 mr10" onclick="logOperation('${lineBusiness.lineBaseId}')">操作记录</a>
                    	</th>
                    	</tr>
					</s:iterator>
                  </table>
          		<!--Start page-->
			<s:if test="%{page.pageCount != 0}"><jsp:include page="../page.jsp"></jsp:include></s:if>
			<!--End page-->
			
          </div>
          


<!-- 
<div class="showbox">
	<h2>温馨提醒：您正在修改发车时间，请谨慎操作！<a class="close" href="javascript:void(0);">关闭</a></h2>
	<div class="showboxContent">
		<div class="mainlist">
	   		<input type="hidden" id="lineId_change"/>
	   		<p class="f14" id="changeInfo"></p>
			<div class="pop-main-tips mt10" style="height:auto;line-height:25px;padding-top:5px;padding-bottom:5px;font-size:16px;box-shadow:inset 1px -1px 0 #f0edd5,inset -1px 1px 0 #f0edd5">
			我要把线路为<span class="red1 ml4 mr4 fw" id="lineName_change">TK102</span>
			的发车时间由原来的<span class="red1 ml4 mr4 fw" id="lineTime_old">8:15</span>
			改为<input class="r-input ml4" readonly="readonly" type="text" id="lineTime_change" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'HH:mm'})" size="8" value=""/>,
			生效时间为<span class="r-input w140 display-ib" style="vertical-align:-7px;"><input class="Wdate75 gray2 Wdate" type="text" name="workTime" readonly="readonly" id="workTime" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d}'})"/></span>
			<p>并且<select id="changeMSG" name="changeMSG" onchange="changeMSG(this.value)" class="p3 w15p"><option selected="selected" value="1">不发送</<option><option value="0">发送</option></select>短信。</p>
			
			</div>
			<textarea name="msgContent" id="msgContent" disabled="disabled" class="mt10 textarea"></textarea>
	   	</div>
	   	<p class="t-c mt10 mb20">
		    <a class="display-ib btn1 white mr40 f14 updateTime" href="javascript:void(0);">确&nbsp;定</a>
		   	<a class="display-ib btn1 white f14 close" href="javascript:void(0);">取&nbsp;消</a>
	   	</p>
   	</div>
</div>
 -->

</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();

	//通过默认省份加载城市信息
	loadOriginalCity();

	//表格标题点击排序样式更改
	$(".table3 th").toggle(function(){
		$(this).find("span").removeClass("acs").addClass("desc");
	},function(){
		$(this).find("span").removeClass("desc").addClass("acs");
	});

  // 鼠标移入显示路线详情
  $(".table3 .route-details").mouseover(function(){
    $(this).children('.stations').show();
    return false;
  });
  // 鼠标移出显示路线详情
  $(".table3 .route-details").mouseout(function(){
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
				//$(this).val(""); 
			} 
		})  
	});
}

//线路详情
$("#tableDiv tr td").click(function(){
    //如果是没有数据就不调用后面的方法
	if($("#noDate").html()!= undefined){	
		return false;
	}
	//获取选中文字，如果选中文字则不响应点击事件
	//ie8写法
	if(typeof(document.selection) != "undefined"){
		var selecter = document.selection.createRange().text;
	}else if(typeof(window.getSelection()) != "undefined"){
		var selecter = window.getSelection();
	}else{
		var selecter = "";
	}
	if(selecter != ""){
	 	return false;
	}else{
	    var lineBaseId = $(this).parent().attr("lineBaseId");
	    var lineStatus = $(this).parent().attr("lineStatus");
		//跳转到线路详情总页面
		//$("#iframe_right", parent.parent.window.document).attr("src","../line/getLinesDetailManager.action?lineBaseId="+lineBaseId+"&currentPageIndex="+$("#myCurrentPageIndex").val());
		if(lineStatus!=4){
			$("#iframe_right", parent.parent.window.document).attr("src","../publishLine/loadLineBaseInfo.action?lineBaseId="+lineBaseId+"&currentPageIndex="+$("#myCurrentPageIndex").val());
		}
	}
});

function copyLine(lineBaseId){
	parent.document.location.href = "../publishLine/loadLineBaseInfo.action?lineBaseId="+lineBaseId+"&isCopy=true";
}

function allowance(lineBaseId,type){
	if(type==1){
// 		alert($("#myCurrentPageIndex").val());
		$("#iframe_right", parent.parent.window.document).attr("src","../publishLine/loadClassInfo.action?lineBaseId="+lineBaseId+"&currentPageIndex="+$("#myCurrentPageIndex").val());
	}else if(type==2){
		$("#iframe_right", parent.parent.window.document).attr("src","../publishLine/loadClassPrice.action?lineBaseId="+lineBaseId+"&currentPageIndex="+$("#myCurrentPageIndex").val());
	}
 
//  <a href="../publishLine/loadClassInfo.action?lineBaseId=${lineBaseId}"><li><i>2</i><span class="tsl">设置班次</span></li></a>
// <a href="../publishLine/loadClassPrice.action?lineBaseId=${lineBaseId}"><li class="active"><i>3</i><span class="tsl">定价格</span></li></a>
//     if(""==lineBaseId||null==lineBaseId){
//        return ;
//     }
// 	$("#topHide", parent.parent.window.document).show();
//     $("#leftHide", parent.parent.window.document).show();
//     $("#showPage",parent.window.document).load("../line/forwardLineAllowance.action?random="+Math.floor(Math.random()*10000+1),{lineBaseId:lineBaseId}); 
//     $("#mainBody",parent.window.document).show();  
}



/**
 * 查看订票情况
 */
function getOrderNum(lineBaseId){
	parent.document.location.href="../line/getOrderNum.action?lineBaseId="+lineBaseId;
}

//选择商家
function selectMerchant(lineBaseId)
{
    if(""==lineBaseId||null==lineBaseId){
       return ;
    }
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("../line/selectMerchant.action?random="+Math.floor(Math.random()*10000+1),{lineBaseId:lineBaseId}); 
    $("#mainBody",parent.window.document).show();
}

function deleteLine(lineBaseId){
  parent.parent.showPopCommonPage("确定要删除吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/deleteLine.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("删除成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#searchForm").submit();
			    }else{
			       parent.parent.showRturnTip(data,false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("删除失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  
  $("#commonCancel",parent.parent.window.document).click(function(){
  		parent.parent.closePopCommonPage();
  		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
}

function publishLine(lineBaseId){
  parent.parent.showPopCommonPage("确定要发布此线路吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/publishLine.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("线路发布成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#searchForm").submit();
			    }else{
			       parent.parent.showRturnTip("线路发布失败!",false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("线路发布失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  $("#commonCancel",parent.parent.window.document).click(function(){
  		parent.parent.closePopCommonPage();
  		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
}

function offLine(lineBaseId){
  parent.parent.showPopCommonPage("确定要将此线路下线吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/offLine.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("线路下线成功!",true);
// 			       $("#currentPageIndex").val("0");
			       $("#searchForm").submit();
			    }else{
			       parent.parent.showRturnTip("线路下线失败!",false);
			    }
			},
			error:function(){
				parent.parent.showRturnTip("线路下线失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  $("#commonCancel",parent.parent.window.document).click(function(){
  		parent.parent.closePopCommonPage();
  		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
}




function topLine(lineBaseId){
  parent.parent.showPopCommonPage("确定要将此线路置顶吗？");
  //解绑定，防止多次执行click事件
  $("#commonSure",parent.parent.window.document).unbind('click');
  //动态添加方法
  $("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url:'../line/topLine.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			data:{lineBaseId:lineBaseId},
			cache:false,	
			dataType:"text",	
			success:function(data){	
			    if(data=="success"){
			       parent.parent.showRturnTip("线路置顶成功!",true);
			       $("#currentPageIndex").val("0");
			       $("#turnPage").submit();
			    }else{
			       parent.parent.showRturnTip("线路置顶失败!",false);
			    }
				
			},
			error:function(){
				parent.parent.showRturnTip("线路置顶失败!",false);
			}
		}); 
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
  });
  $("#commonCancel",parent.parent.window.document).click(function(){
  		parent.parent.closePopCommonPage();
  		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
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
	var totalPage = "${page.pageCount}";
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(parseInt(pageNum) > parseInt(totalPage))
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}

//修改商家
function changeBusiness(lineBaseId,businessId){
	/**判断线路是否有排班,如果有,不允许修改**/
	$.ajax({
		url:'../line/judgeLineClass.action?&random='+Math.floor(Math.random()*10000+1),
		type:'post',
		data:{lineBaseId:lineBaseId},
		cache:false,	
		dataType:"text",	
		success:function(data){	
		    if(data=="0"){
		    	showBusinessList(businessId,lineBaseId);
		    }else{
		       parent.parent.showRturnTip("该线路有排班,不能修改商家!",false);
		    }
			
		},
		error:function(){
			parent.parent.showRturnTip("获取修改商家信息异常!",false);
		}
	}); 
}

//上下班-工作时间
function goDatePage(lineBaseId,orderStartTime,orderSeat,onCorrectNew,isShow)
{
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("../line/popAddLineDateForLine.action?lineBaseId="+lineBaseId+"&orderStartTime="+orderStartTime+"&random="+Math.floor(Math.random()*10000+1),{lineBaseId:lineBaseId,orderStartTime:orderStartTime,orderSeat:orderSeat,isShow:isShow,onCorrectNew:onCorrectNew}); 
    $("#mainBody",parent.window.document).show();
}

/**显示商家列表**/
function showBusinessList(businessId,lineBaseId){
	 
    if(""==businessId||null==businessId){
       return ;
    }
    if(""==lineBaseId||null==lineBaseId){
       return ;
    }
	$("#topHide", parent.parent.window.document).show();
    $("#leftHide", parent.parent.window.document).show();
    $("#showPage",parent.window.document).load("../merchantAction/queryMerchantListJsonManager.action?random="+Math.floor(Math.random()*10000+1),{businessId:businessId,lineBaseId:lineBaseId}); 
    $("#mainBody",parent.window.document).show();  
}

//修改发车时间(弹窗)
function changeTime(id,time,name,sname,ename){
		$("#msgContent").val("【小猪巴士】尊敬的先生/小姐，小猪巴士“"+sname+"→"+ename+"”线路发车时间将于XXXX-XX-XX更改XX:XX发车，请各位预计好时间准时乘坐，谢谢大家的支持！");
		document.getElementById("changeMSG").options[0].setAttribute("selected", true); 
		$("#msgContent").attr("disabled",true); 
		$("#lineName_change").html();
		$("#lineTime_old").html();
		$("#lineId_change").val("");
		$("#lineTime_change").val("");
		$("#changeInfo").html("");
		$("#workTime").val("");
		
		$.ajax({
				url:'../line/getLineChangeInfo.action',
				type:'post',
				async: false,
				data:{cid:id},
				cache:false,	
				dataType:"json",	
				success:function(data){
					if(data!="" && data !=null){
						$("#changeInfo").html("该线路由<em class=\"fw red1 ml4 mr4\">"+data.a3+"</em>预约了在<em class=\"fw red1 ml4 mr4\">"+data.a2+"</em> 后改为<em class=\"fw red1 ml4 mr4\">"+data.a1+"</em>发车！")
					}	
				},
				error:function(){
			    	alert("失败");
				}
		}); 
		
		var box =300;
		var th= $(window).scrollTop()+$(window).height()/1.6-box;
		var h =document.body.clientHeight;
		var rw =$(window).width()/2-box;
		$(".showbox").animate({top:th,opacity:'show',width:800,height:150,right:rw},500);
		$("body").prepend("<div class='mask'></div>");
		$(".mask").css({opacity:"0.5"}).css("height",h);
		$("#lineName_change").html(name);
		$("#lineTime_old").html(time);
		$("#lineId_change").val(id);
		
		return false;
}
$(".showbox .updateTime").click(function(){
	var cid = $("#lineId_change").val();
	var ctime = $("#lineTime_change").val();
	var workTime = $("#workTime").val();
	var changeMSG = $("#changeMSG ").val();
	var content = $("#msgContent").val();

	if(ctime=="" || ctime == null || workTime == "" || workTime == null){
		alert("请将信息填完整！");
		return;
	}
	
	if(confirm("想清楚了要进行此操作吗？")){
		$.ajax({
				url:'../line/updateLineTime.action',
				type:'post',
				async: false,
				data:{cid:cid,ctime:ctime,workTime:workTime,changeMSG:changeMSG,content:content},
				cache:false,	
				dataType:"text",	
				success:function(data){	
				    if(data=="success"){
				    	history.go(0);
				    }else{
				    	alert("失败");
				    }
				},
				error:function(){
			    	alert("失败");
				}
			}); 
		$(this).parents(".showbox").animate({top:0,opacity: 'hide',width:0,height:0,right:0},500);
		$(".mask").fadeOut("fast");
		return false;
	}
});


$(".showbox .close").click(function(){
	$("#lineName_change").html();
	$("#lineTime_old").html();
	$("#lineId_change").val("");
	$("#lineTime_change").val("");
	$(this).parents(".showbox").animate({top:0,opacity: 'hide',width:0,height:0,right:0},500);
	$(".mask").fadeOut("fast");
	return false;
});

function changeMSG(val){
	if(val == 1){
		$("#msgContent").attr("disabled",true); 
	}else{
		$("#msgContent").attr("disabled",false); 
	}
}

function loadOriginalCity(){
	   var province = "${search.field11}";
	   if(null!=province&&province.length>0&&province!="null"){
	      loadCity(province);
	   }
}

//根据省份加载城市
function loadCity(value){
	var $theSelectedCityCode=$("#theSelectedCityCode").val();
	if(null!=$theSelectedCityCode&&""!=$theSelectedCityCode){
		$.ajax({
			url:'../merchantAction/getProvince.action?proId='+value,		
			cache:false,	
			dataType:"json",
			async : false,
			success:function(data){
				$("#cityCode").empty();
				$("#cityCode").append("<option value=''>--选择城市--</option>");
				$(data).each(function(i){ //遍历结果数组
					if($theSelectedCityCode==data[i].arearCode){
						$("<option value='"+data[i].arearCode+"' selected='true'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
					}
					else{
						$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
					}
				})
			}
		});
	}
	else{
		$.ajax({
			url:'../merchantAction/getProvince.action?proId='+value,		
			cache:false,	
			dataType:"json",
			async : false,
			success:function(data){
				$("#cityCode").empty();
				$("#cityCode").append("<option value=''>--选择城市--</option>");
				$(data).each(function(i){ //遍历结果数组
				   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
				})
			}
		});
	}
}

//排序
function query(){
	$("#myCurrentPageIndex").val("0");
	$("#searchForm").submit();
}
//线路操作记录
function logOperation(lineBaseId){
// 	window.location.href=""
	parent.document.location.href="../line/lineLog.action?lineBaseId="+lineBaseId;
}

//线路信息导出
function toExport(){
	var listSize = $("#listSize").val();
	if(listSize==1){
		parent.document.location.href="../line/toExport.action";
	}else{
		parent.parent.showCommonTip("没有需要导出的数据!");
	}
}


</script>