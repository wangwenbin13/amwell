<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商管理-商户列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;供应商管理&nbsp;>&nbsp;商户列表<span class="blue1 ml25"></span></p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">商户列表</a></li>
                </ul>
            </div>
   <s:form name="" action="../merchantAction/getMerchantList.action" method="post" id="searchForm" theme="simple">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w30p">
           	<span class="t-r w65 fl">创建时间：</span>
               <span class="r-input fl w34p mr5"><input type="text" name="search.field01"  value="${search.field01 }"class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w34p ml5"><input type="text" name="search.field02" value="${search.field02}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
        </li>
        <li class="w20p"><span class="t-r w65 fl">登录名：</span><s:textfield name="search.field03" theme="simple" cssClass="r-input w67p gray2"/></li>
        <li class="w20p"><span class="t-r w65 fl">商户简称：</span><s:textfield  name="search.field04" cssClass="r-input w67p gray2"/></li>
        <li class="w20p"><span class="t-r w65 fl">业务类型：</span>
        	<s:select cssClass="w67p p3 fl" list="#{0:'所有方式',1:'上下班',2:'包车',3:'两者都支持'}" theme="simple" name="search.field06">
	        </s:select>
        </li>
        <li class="w30p"><span class="t-r w65 fl">省份城市：</span>
	        <s:select list="%{proSysAreas}" listKey="arearCode" listValue="areaName" headerKey="0" headerValue="--选择省份--" name="search.field07" cssClass="fl r-input mr10 w36p" onchange="loadCity(this.value);"/>
	        <s:select list="#{'0':'--选择城市--'}" name="search.field08" id="cityCode" cssClass="fl r-input w36p"></s:select>
	        <input type="hidden" id="theSelectedCityCode" value="${search.field08}"/>
<%--	        <select name="search.field08" id="cityCode" class="fl r-input w36p"><option value="">--选择城市--</option></select>--%>
        </li>
        <li class="w31p"><span class="t-r w65 fl">账号状态：</span><input name="search.field05" value="1" type="checkbox" class="checkbox mr5" <s:if test="%{search.field05=='all' || search.field05==1}">checked="checked"</s:if>/><em class="green1">启用</em><input value="0" name="search.field05" type="checkbox" class="checkbox mr5 ml18" <s:if test="%{search.field05=='all' || search.field05==0}">checked="checked"</s:if>/><em class="red1">禁用</em>
        	<input type="submit" class="btn-blue4 ml20" value="查找" />
        	<s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchForm')"/>
        </li>
        
  	</ul>   
  </s:form> 
   <div class="table-outline fl widthfull mt20">
    <div class="table-title">
    		<a href="javascript:void(0)" onclick="toExport();" class="btn fr mr8 mt4">导出</a>
         	<a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
         </div>
      <div class="table2-text sh-ttext">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号<!-- <input type="checkbox" name='checkAllBox' id="checkAllBox"/> --></th>
                      <th scope="col" width="14%">商户简称</th>
                      <th scope="col" width="10%">登录名</th>
                      <th scope="col" width="10%">联系人</th>
                      <th scope="col" width="8%">联系电话</th>
                      <th scope="col" width="10%">操作人</th>
                      <th scope="col" width="11%">创建时间</th>
                      <th scope="col" width="5%">账号状态</th>
                      <th scope="col" width="8%">省份城市</th>
                      <th scope="col" width="7%">业务类型</th>
                      <th scope="col">操作</th>
                    </tr>
                    
                    <s:if test="%{page.pageCount == 0}">
                     
                           <tr align="center" class="bg1">
              					<td colspan="11"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    
                    <s:iterator value="mgrBusinessEntities" var="mgrBussiness" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1" businessId ="${mgrBussiness.businessId}">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2" businessId ="${mgrBussiness.businessId}">
						</s:if>
						<td class="f-arial">${s.index+1}<!-- <input type="checkbox" name="checkboxchild" id="child${s.index}" value="${carBasicResultListModel.carBasicInfoId}"/> --></td>
                    	<td class="f-arial">${mgrBussiness.brefName}</td>
                    		<td class="f-arial">${mgrBussiness.loginName}</td>
                    		<td class="f-arial">${mgrBussiness.contacts}</td>
                    		<td class="f-arial">${mgrBussiness.contactsPhone}</td>
                    		<td class="f-arial">${mgrBussiness.address}</td><!-- 临时使用：操作人姓名 -->
                    		<td class="f-arial">${mgrBussiness.createOn}</td>
                    		<td class="f-arial">
                    			<s:if test="%{#mgrBussiness.accountStatus==1}"><em class="green1">启用</em></s:if>
                    			<s:if test="%{#mgrBussiness.accountStatus==0}"><em class="red1">禁用</em></s:if>
                    		</td>
                    		<td class="f-arial">
                    			${mgrBussiness.provinceCode}/${mgrBussiness.areaCode }
                    		</td>
                    		<td class="f-arial">
                    			<s:if test="%{#mgrBussiness.businessType==1}">上下班</s:if>
                    			<s:if test="%{#mgrBussiness.businessType==2}">包车</s:if>
                    			<s:if test="%{#mgrBussiness.businessType==3}">两者都支持</s:if>
                    		</td>
                    		<th><a href="javascript:void(0);" class="fn blue1 mr10" onclick="showEditPage('${mgrBussiness.businessId}');">编辑</a><s:if test="%{#mgrBussiness.accountStatus==1}"><a href="javascript:void(0);" class="fn blue1 mr10" onclick="updateAccountStatu('${mgrBussiness.businessId}','0');">禁用</a></s:if><s:if test="%{#mgrBussiness.accountStatus==0}"><a href="javascript:void(0);" class="fn blue1 mr10" onclick="updateAccountStatu('${mgrBussiness.businessId}','1');">启用</a></s:if><a href="javascript:void(0);" class="fn blue1 mr10" onclick="resetPassword('${mgrBussiness.businessId}','${mgrBussiness.createBy}','${mgrBussiness.loginName }');">重置密码</a></th>
                    	</tr>
                   	</s:iterator>
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
		</div>
	</div>
</div>
<input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
 <form action="../merchantAction/getMerchantList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
    <input type="text" name="search.field06" value="${search.field06 }"/>
    <input type="text" name="search.field07" value="${search.field07 }"/>
    <input type="text" name="search.field08" value="${search.field08 }"/>
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
  </form> 
 <form action="../merchantAction/exportExcel.action" method="post" style="display: none;" id="export">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05 }"/>
  </form> 
</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();

	loadOriginalCity();
		//清除IE缓存
	$.ajaxSetup ({ 
		cache: false 
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
//增加商户
function showAddPage()
{
	$("#iframe_right", parent.parent.window.document).attr("src","../merchantAction/editMerchant.action");
}

//编辑商户
function showEditPage(businessId)
{
	$("#iframe_right", parent.parent.window.document).attr("src","../merchantAction/editMerchant.action?businessId="+businessId);
}

//商户详情
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
		var businessId = $(this).parent().attr("businessId");
		//跳转到商户详情
		$("#iframe_right", parent.parent.window.document).attr("src","../merchantAction/getMerchantDetail.action?businessId="+businessId+"&currentPageIndex="+$("#myCurrentPageIndex").val());
	}
});

//------------------------- 列表选中和未选中start------------------------------
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


//获取选中的所有id
function getCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

//获取count值
function getCount()
{
	var checkboxs = $("input[name='checkboxchild']");
	var counts = 0;
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (checkboxs[i].checked)
			{
				counts += parseInt($("#"+checkboxs[i].id).attr("count"));
			}
		}
	}
	return counts;
}

//获取未选中的所有id
function getNotCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			if (!checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + ";";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}

//------------------------- 列表选中和未选中end------------------------------

//重置密码
function resetPassword(businessId,createBy,loginName){
	var value = "${currentPageIndex}";
	var data = {"mgrBusinessEntity.businessId":businessId,"mgrBusinessEntity.loginPassword":888888,"mgrBusinessEntity.createBy":createBy,"mgrBusinessEntity.loginName":loginName};
	parent.parent.showPopCommonPage("确定要重置密码吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
			url : '../merchantAction/updatePassWord.action',
			type:'post',
			cache:false,	
			dataType:"text",
			data:data,	
			success:function(data){	
				
				if(null != data){
					if("success"==data){
						parent.parent.showRturnTip("重置密码成功!",true);
						parent.parent.showPopCommonPage2("密码已重置为:888888,请管理员尽快修改密码!");
						toPage(value);
					}
					else
					{
						parent.parent.showRturnTip("重置密码失败!",false);
					}
				}
			},
			error:function(){
				parent.parent.showRturnTip("重置密码失败!",false);
			}
		});
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
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

//更改帐号状态
function updateAccountStatu(businessId,accountStatus){
 	var data = {"mgrBusinessEntity.businessId":businessId,"mgrBusinessEntity.accountStatus":accountStatus};
 	var value = "${currentPageIndex}";
	$.ajax({
			url : '../merchantAction/updateStatu.action',
			type:'post',
			data:data,
			cache:false,	
			dataType:"text",	
			success:function(data){	
				if("success" == data){
					toPage(value);
				}
			},
			error:function(){
			}
		});
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
		//document.forms[0].submit();
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

function loadOriginalCity(){
	   var province = "${search.field07}";
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
				$("#cityCode").append("<option value='0'>--选择城市--</option>");
				$(data).each(function(i){ //遍历结果数组
					if($theSelectedCityCode==data[i].arearCode){
						$("<option value='"+data[i].arearCode+"' selected='true'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
					}
					else{
						$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
					}
				})
				loadSelectCity();
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
				$("#cityCode").append("<option value='0'>--选择城市--</option>");
				$(data).each(function(i){ //遍历结果数组
				   $("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
				})
				loadSelectCity();
			}
		});
	}
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

</script>