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
<title>供应商管理-线路成本设置</title>
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
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;线路成本设置</p></div>
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">线路成本设置</a></li>
           </ul>
       </div>

   <s:form id="searchForm" name="searchForm" action="../lineCostSet/getAllLineAndCost.action" method="post"  theme="simple" > 
   	 <ul class="r-sub-sel black1 mt20 clearfix">  
        <li class="w12p"><span class="t-r w65 fl">供应商：</span><s:textfield id="brefName" name="search.field01" cssClass="r-input w55p gray2"/></li>
        <li class="w14p"><span class="t-r w65 fl">线路名称：</span><s:textfield id="lineName" name="search.field02" cssClass="r-input w55p gray2"/></li>
        <li class="w14p"><span class="t-r w70 fl">成本分摊方式：</span><s:select name="search.field03" list="#{'':'所有方式','fixed base price':'固定底价','commission':'佣金分成'}" listKey="key" listValue="value" cssClass="p3 w58p"/></li>
        <li class="w20p"><span class="t-r w65 fl">有效期：</span>
        <span class="r-input fl w30p mr10"><input type="text" name="search.field04" value="${search.field04 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd'})"/></span>
        <span class="fl">至</span>
        <span class="r-input fl w30p ml10">
        <input type="text" name="search.field05" value="${search.field05 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/>
        </span>
        </li>  
        <li class="w14p"><span class="t-r w68 fl">是否设置成本：</span>
        <s:select name="search.field06" list="#{'':'全部','0':'否','1':'是'}" listKey="key" listValue="value" cssClass="p3 w58p"></s:select>
        </li>
        <li>
        <s:submit value="查找" cssClass="btn-blue4"/>
        <s:reset value="重置" cssClass="btn-blue4" onclick="clearFormValue('searchForm')"/>
        <a href="javascript:doExport('n')">导出成本</a>
        <a href="javascript:doExport('nh')">导出成本(包含历史)</a>
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
                      <th scope="col">起点/终点</th>
                      <th scope="col" width="7%">通票价格</th>
<%--                      <th scope="col">成本分摊方式</th>--%>
<%--                      <th scope="col">成本分摊</th>--%>
<%--                      <th scope="col">有效期</th>--%>
<%--                      <th scope="col">操作</th>--%>
                      
                      <th scope="col" width="9%">成本分摊方式</th>
                      <th scope="col" width="8%">成本分摊</th>
                      <th scope="col" width="14%">有效期</th>
                      <th scope="col" width="7%">操作</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="list" var="lineBusiness" status="s">
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
                    	<td class="f-arial">
	                    	<s:if test="%{#lineBusiness.id != null && #lineBusiness.id != ''}">
	                    	<em class="red1 fw">${lineBusiness.costSharing}</em>元/次
	                    	</s:if>
                    	</td>
                    	<s:if test="%{#lineBusiness.id == null || #lineBusiness.id == ''}">
                    		<td class="f-arial">
                    		<select name="theMode" class="p3 w58p">
                    		<option value="fixed base price" selected="selected">固定底价</option>
                    		<option value="commission">佣金分成</option>
                    		</select>
	                    	</td>
	                    	<td class="f-arial"><input type="text" name="costSharing" class="r-input w55p gray2"><span>元/天</span></td>
	                    	<td class="f-arial">
	                    	<span class="r-input fl w40p mr10"><input type="text" name="startTime" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate${lineBusiness.lineBaseId}" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd'})"/></span>
					        <span class="fl">至</span>
					        <span class="r-input fl w40p ml10"><input type="text" name="endTime" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate${lineBusiness.lineBaseId}" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate${lineBusiness.lineBaseId}\');}'})"/></span>
	                    	</td>
	                    	<td class="f-arial">
	                    	<a href="javascript:void(0);" class="blue1" onclick="doAddOrUpdate('${lineBusiness.lineBaseId}')">保存</a>
	                    	</td>
                    	</s:if>
                    	<s:else>
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
	                    	<td class="f-arial">
	                    	<a href="javascript:void(0);" class="blue1" onclick="toDoUpdate('${lineBusiness.lineBaseId}')">修改</a>
	                    	<a href="javascript:window.location.href='../lineCostSet/getLineAndCostHistory.action?lineBaseId=${lineBusiness.lineBaseId}&myPageIndex=${currentPageIndex}'" class="blue1">历史</a>
	                    	</td>
                    	</s:else>
                    	</tr>
					</s:iterator>
                  </table>
                  
                  <s:hidden theme="simple" id="theListSize" value="%{list.size()}"></s:hidden>
                  
          		<!--Start page-->
			<s:if test="%{page.pageCount != 0}"><jsp:include page="../page.jsp"></jsp:include></s:if>
			<!--End page-->
			<form method="post" style="display: none;" id="turnPage">
		   	<input type="text" name="search.field01" value="${search.field01}"/>
		    <input type="text" name="search.field02" value="${search.field02}"/>
		    <input type="text" name="search.field03" value="${search.field03}"/>
		    <input type="text" name="search.field04" value="${search.field04}"/>
		    <input type="text" name="search.field05" value="${search.field05}"/>
		    <input type="text" name="search.field06" value="${search.field06}"/>
	    	<input type="text" name="currentPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
	  		</form>
	  		
	  		<form action="../lineCostSet/exportExcel.action" method="post" style="display: none;" id="export">
		   	<input type="text" name="search.field01" value="${search.field01}"/>
		    <input type="text" name="search.field02" value="${search.field02}"/>
		    <input type="text" name="search.field03" value="${search.field03}"/>
		    <input type="text" name="search.field04" value="${search.field04}"/>
		    <input type="text" name="search.field05" value="${search.field05}"/>
		    <input type="text" name="search.field06" value="${search.field06}"/>
	    	<input type="text" name="currentPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
	    	<input type="text" name="exportType" id="exportType"/>
		    </form> 
          </div>
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

	//选择成本分摊方式
	$("select[name='theMode']").change(function(){
		if($(this).val()=='commission'){
			 $(this).parent().next().find("span").text("%");
		}
		else{
			 $(this).parent().next().find("span").text("元/天");
		}
	});
	
	//避免供应商和线路名称输入%，输入%是，sql中like会查出所有信息，然后用户不能理解
	$("#brefName").keyup(function(){
		if($.trim($(this).val())=='%'){
			$(this).val("");
		}
	});
	
	$("#lineName").keyup(function(){
		if($.trim($(this).val())=='%'){
			$(this).val("");
		}
	});
});

// 点击保存或修改
function doAddOrUpdate(lineBaseId){

	var $lineCostSetId=$("#"+lineBaseId).find("input[name='lineCostSetId']").val();
	var $theMode=$("#"+lineBaseId).find("select[name='theMode']").val();
	var $costSharing=$.trim($("#"+lineBaseId).find("input[name='costSharing']").val());
	if($costSharing==''){
		parent.parent.showRturnTip("成本分摊不能为空",false);
		return false;
	}
	//var reg=/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;//包括0
	var reg=/^(([1-9]{1}\d*)(\.(\d){1,2})?)|(([0]{1})(\.(\d){1,2}))$/;//不包括0
	if(!reg.test($costSharing)){
		parent.parent.showRturnTip("成本分摊只能是大于0的数字，可输入两位小数",false);
		return false;
	}
	
	var $startTime=$("#"+lineBaseId).find("input[name='startTime']").val();
	if($startTime==''){
		parent.parent.showRturnTip("有效期开始时间不能为空",false);
		return false;
	}
	var $endTime=$("#"+lineBaseId).find("input[name='endTime']").val();
	if($endTime==''){
		parent.parent.showRturnTip("有效期结束时间不能为空",false);
		return false;
	}
	/*if($startTime==$endTime){
		parent.parent.showRturnTip("有效期开始和结束时间不能相同",false);
		return false;
	}*/
	
	if($lineCostSetId!=''){
		 var d1 = new Date($startTime.replace(/\-/g, "\/"));  
		 var d2 = new Date($endTime.replace(/\-/g, "\/"));  
		 if(d1 >=d2){
			 parent.parent.showRturnTip("有效期结束时间不能小于开始时间",false);
			return false;
		 }
	}

	$.ajax({
        url:'../lineCostSet/lineCostSetUpdate.action?lineCostSetId='+$lineCostSetId+'&lineBaseId='+lineBaseId+'&theMode='+$theMode+'&costSharing='+$costSharing+'&startTime='+$startTime+'&endTime='+$endTime+'&myPageIndex='+$("#currentPageIndex").val()+'&temp='+new Date(),
        dataType:'text',
        cache:false,//不从浏览器缓存中取
        async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
        success:function(data){
       	  if(data==1){
       		 parent.parent.showRturnTip("编辑成功",true);
               //刷新本页
         	 $("#turnPage").attr("action","../lineCostSet/getAllLineAndCost.action");
         	 //刷新当前页信息
	             $("#turnPage").submit(); 
           }
       	  if(data==-1){
       		parent.parent.showRturnTip("编辑失败",false);
           }
        }
    });
}

function toDoUpdate(lineBaseId){
	
	var $theMode=$("#"+lineBaseId).find("td:eq(7)").html().trim();
	var $costSharing=$("#"+lineBaseId).find("td:eq(8)").find("span").html();
	if($theMode=='固定底价'){
		$("#"+lineBaseId).find("td:eq(7)").html('<select name="theMode" class="p3 w58p"><option value="fixed base price" selected="selected">固定底价</option><option value="commission">佣金分成</option></select>');
		$("#"+lineBaseId).find("td:eq(8)").html('<input type="text" name="costSharing" class="r-input w55p gray2" value="'+$costSharing+'"><span>元/天</span>');
	}
	else{
		$("#"+lineBaseId).find("td:eq(7)").html('<select name="theMode" class="p3 w58p"><option value="fixed base price">固定底价</option><option value="commission" selected="selected">佣金分成</option></select>');
		$("#"+lineBaseId).find("td:eq(8)").html('<input type="text" name="costSharing" class="r-input w55p gray2" value="'+$costSharing+'"><span>%</span>');
	}
	
	//选择成本分摊方式
	$("select[name='theMode']").change(function(){
		if($(this).val()=='commission'){
			 $(this).parent().next().find("span").text("%");
		}
		else{
			 $(this).parent().next().find("span").text("元/天");
		}
	});
	
	var $theHtml=$("#"+lineBaseId).find("td:eq(9)").html().split("至");
	var $startTime=$theHtml[0];
	var $endTime=$theHtml[1];
	$("#"+lineBaseId).find("td:eq(9)").html(
			'<span class="r-input fl w40p mr10">'+
			'<input type="text" name="startTime" value="'+$startTime+'" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate'+lineBaseId+'" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:\'yyyy-MM-dd\'})"/>'+
			'</span>'+
		    '<span class="fl">至</span>'+
		    '<span class="r-input fl w40p ml10">'+
		    '<input type="text" name="endTime" value="'+$endTime+'" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate'+lineBaseId+'" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:\'yyyy-MM-dd\'})"/>'+
		    '</span>');                                                                                                                           

	$("#"+lineBaseId).find("td:eq(10)").html('<a href="javascript:void(0);" class="blue1" onclick="doAddOrUpdate(\''+lineBaseId+'\')">保存</a>');
}

//点击导出按钮
function doExport(type){
	var listSize = $("#theListSize").val();
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	//导出类型（n：当前成本  nh：当前和历史成本）
	$("#exportType").val(type);
	$("#export").submit();
}

</script>
</html>