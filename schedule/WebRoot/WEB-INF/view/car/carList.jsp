<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车辆管理-车辆列表</title>
<jsp:include page="../resource.jsp"/>
</head>

 <body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;车辆管理&nbsp;>&nbsp;车辆列表<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 black1 mr28">
      <form method="post" action="../dispatchVehicle/vehicleInfoList.action" id="searchform">
      	<p class="fw f14 f-arial">车辆列表</p>
      	<ul class="r-sub-sel mt20 clearfix">
             <li class="fl">
	           	<span class="t-l w65 fl">创建时间：</span>
	            <span class="r-input fl w123 mr10">
	            <input type="text" name="search.field01" value="${search.field01}" class="Wdate75 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
	            <span class="fl">至</span>
	            <span class="r-input fl w123 ml10">
	            <input type="text" name="search.field02" value="${search.field02}" class="Wdate75 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-4,top:5},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
       		</li>
            <li class="fl">
	             <span class="fl">车牌号：</span>
	             <s:textfield name="search.field03" cssClass="r-input w123"></s:textfield>
            </li>
            <li class="fl">
                <span class="fl">品牌车型：</span> 
                <s:if test="%{null==search.field04||''==search.field04}">
                <s:textfield name="search.field04" cssClass="r-input w123 gray1" value="品牌或车型的关键字" onfocus="if(value==defaultValue){value='';$(this).removeClass('gray3').addClass('black1');}" onblur="if(!value){value=defaultValue;$(this).removeClass('black1').addClass('gray3');}"></s:textfield>
                </s:if>
                <s:else>
                <s:textfield name="search.field04" cssClass="r-input w130 gray1 black1" ></s:textfield>
                </s:else>
            </li>
            <li class="fl p-r"><span class="fl w65 t-r">车辆类型：</span>
				<div class="fl r-input w130"><span id="carStyleDiv"></span><span class="fr sel-arrow mt10 mr10"></span></div>
				<s:select list="#{'':'车辆类型','0':'大巴','1':'中巴','2':'小巴'}" name="search.field05" cssClass="p-a sel-demo w134 h29" id="carStyle"></s:select>
			</li>
          	<li class="fl">
          		<input class="search-btn" type="button" onclick="to_do_search()"/>
          		<input type="hidden" name="searchOrNo" value="1"/>
          	</li>
          	<li style="float:right">
          		<a class="yellow-btn" href="javascript:void(0)" onclick="parent.openLeftMenu('../dispatchVehicle/toVehicleEditPage.action');"><em class="fw f20 va3">+</em>&nbsp;添加车辆</a>
          	</li>
         </ul>
      </form>
     <table class="table1 tableCursor mt20" border="0" width="100%" id="tableDiv">
		<tr class="th">
			<th scope="col" width="5%"><input type="checkbox" name='checkAllBox' id="checkAllBox" class="checkbox"/></th>
            <th scope="col" width="8%">车牌号码</th>
            <th scope="col" width="8%">车辆编号</th>
            <th scope="col" width="16%">GPS设备号</th>
            <th scope="col" width="8%">车辆品牌</th>
            <th scope="col" width="10%">车型</th>
            <th scope="col" width="5%">类型</th>
	  		<th scope="col" width="8%">座位数</th>
            <th scope="col" width="5%">操作人</th>
            <th scope="col" width="12%">创建时间</th>
            <th scope="col" align="right" class="pr5p">操作</th>
		</tr>
		<s:if test="(null==list||list.isEmpty())">
			<tr class="noDateList">
				<th colspan="11">				
					<div class="t-c mt115 mb180">
						<s:if test="%{searchOrNo==0}">
							<img src="../images/noDate.png" width="169" height="169" alt="暂无数据" /><!-- 默认没有线路 -->
							<p class="mt15 f18 f-yahei">暂无数据</p>
						</s:if>
						<s:if test="%{searchOrNo==1}">
							<img src="../images/noSearchDate.png" width="169" height="169" alt="暂无数据" /><!-- 没有查询结果 -->
							<p class="mt15 f18 f-yahei">暂无数据</p>
							<!--<p class="gray3 mt15">没有查询结果噢，你可以重新设置条件进行搜索,或者返回列表。 <a href="javascript:void(0)" class="red1" onclick="toBackList();">返回</a> 没有查询结果的提示 </p>	-->				
						</s:if>
					</div>				
				</th>
			</tr>
		</s:if>
		
		<s:iterator value="list" status="l">
         <tr align="center">
            <th>
            <input type="checkbox" name="checkboxchild" id="child${l.index}'/>" class="checkbox" value="<s:property value='vehicleId'/>"/>
            <s:hidden theme="simple" name="vehicleId" id="theId" ></s:hidden>
            </th>
         	<td><em class="blue1"><s:property value="vehicleNumber"/></em></td>
         	<td><s:property value="vehicleNo"/></td>
         	<td><s:property value="GPSNo"/></td>
         	<td><s:property value="vehicleBrand"/></td>
         	<td><s:property value="vehicleModel"/></td>
         	<td>
         	<s:if test="%{vehicleType==0}">大巴</s:if>
         	<s:if test="%{vehicleType==1}">中巴</s:if>
         	<s:if test="%{vehicleType==2}">小巴</s:if>
         	</td>
         	<td><s:property value="vehicleSeats"/></td>
         	<td><s:property value="createByName"/></td>
         	<td><s:property value="createOn"/></td>
         	<th align="right" class="pr3p">
				<a href="../dispatchVehicle/toVehicleEditPage.action?vehicleId=<s:property value='vehicleId'/>" class="yellow3 mr15">编辑</a>
				<span class="vertical-line"></span>
				<a href="javascript:do_del('<s:property value="vehicleId"/>')" class="yellow3 ml20">删除</a>
			</th>
         </tr>
         </s:iterator>

		
	</table>
	<p class="mt20"><a class="gray-btn black1" href="javascript:void(0)" id="deleteBtn">删&nbsp;除</a></p>

     <!--Start page-->
        <div class="page mt20 line24" id="pageDiv">
          <s:if test="%{currentPage!=0}">
         		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
           		<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
          </s:if>
             <ul id="pageNumDiv">
             		<s:iterator value="page.pageList">
			<s:if test="field02==1">
				<li class="on fw"><s:property value="field01" /></li>
			</s:if>
			<s:else>
				<li>
					<a href="javascript:void(0);" onclick="toPage(${field03});"><s:property value="field01" /></a>
				</li>
			</s:else>
		</s:iterator>
             </ul>
             <s:if test="%{page.pageCount!=0 && ((currentPage/page.pageSize)+1)!=page.pageCount}">
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
       
<form action="../dispatchVehicle/vehicleInfoList.action" method="post" style="display: none;" id="turnPage">
  <input type="text" name="search.field01" value="${search.field01}"/>
  <input type="text" name="search.field02" value="${search.field02}"/>
  <input type="text" name="search.field03" value="${search.field03}"/>
  <input type="text" name="search.field04" value="${search.field04}"/>
  <input type="text" name="search.field05" value="${search.field05}"/>
  <input type="text" name="currentPage" id="currentPage" value="${currentPage}"/>
</form> 
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//线路类型
	$("#carStyle").change(function(){
		$("#carStyleDiv").text($("#carStyle").find("option:selected").text());
	});
	$("#carStyleDiv").text($("#carStyle").find("option:selected").text());
});
//全部选中
$("#checkAllBox").click(function(){
	//判断是否已经打勾
	$('input[name="checkboxchild"]').attr("checked",this.checked);
});

//选中某一项
$('input[name="checkboxchild"]').click(function(){
	var $checkBoxChild = $("input[name='checkboxchild']");
	$("#checkAllBox").attr("checked",$checkBoxChild.length == $("input[name='checkboxchild']:checked").length ? true : false);
});

//删除
$("#deleteBtn").click(function(){
	//判断是否已经打勾
	if($("input[name='checkboxchild']:checked").length <= 0)
	{
		parent.showPopCommonPage2("请勾选需要删除的车辆","true","remind");
		return false;
	}

	do_del(getCheckIds());

});

function do_del(the_id){
	var the_result='';
	$.ajax({
		url:'../dispatchVehicle/searchBeforeDel.action?vehicleId='+the_id,
		dataType:'html',
		cache:false,
		async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
		success:function(data){
		   the_result=data;
		},
		error:function(){
			parent.showRturnTip("删除失败","false");
		}
	});
	if(the_result=="-1"){
		parent.showPopCommonPage("确定要删除吗？");
	}
	else{
		parent.showPopCommonPage(the_result);
	}

	$("#commonSure",parent.window.document).text("确定");
	$("#commonCancel",parent.window.document).text("取消");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.window.document).click(function(){
		parent.closePopCommonPage();
		//ajax调用删除方法
		$.ajax({
			url:'../dispatchVehicle/vehicleInfoDelete.action?vehicleId='+the_id,
			dataType:'html',
			cache:false,
			success:function(data){
			    if(data=='ok'){
			    	parent.showRturnTip("删除成功","true");
				    //window.location.reload();
				    $("#turnPage").submit();
				}
			    else{
				    parent.showRturnTip("删除失败","false");
			    }
			},
			error:function(){
				parent.showRturnTip("删除失败","false");
			}
		});
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
	});
}

//获取选中的所有id
function getCheckIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var checkIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				checkIds = checkIds + checkboxs[i].value + ",";
			}
		}
	}
	
	if ("" != checkIds)
	{
		checkIds = checkIds.substring(0, checkIds.length - 1);
	}
	
	return checkIds;
}

//车辆详情
$("#tableDiv tr td").click(function(){
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
		//进入车辆详情
		window.location.href = "../dispatchVehicle/vehicleInfoDetail.action?vehicleId="+$(this).parent().find("#theId").val();
	}
});

//翻页方法
function toPage(value){
	$("#currentPage").val(value);
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
	var pageSize = "${page.pageSize}";
	toPage((value-1)*parseInt(pageSize));
}

//点击搜索
function to_do_search(){
	if($("input[name='search.field04']").val()=='品牌或车型的关键字'){
		$("input[name='search.field04']").val('');
	}
	$("#searchform").submit();
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
</script>
