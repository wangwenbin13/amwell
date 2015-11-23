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
<title>线路管理-客户专线</title>
<jsp:include page="../resource.jsp"/>
</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;线路管理&nbsp;>&nbsp;客户专线<span class="blue1 ml25"></span></p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">客户专线</a></li>
                </ul>
            </div>
   <s:form name="" action="../specialLineLine/getCompanyLineList.action" method="post" theme="simple">
   	 <ul class="r-sub-sel black1 mt10 clearfix">
        <li class="w20p"><span class="t-r w65 fl">公司：</span><s:textfield name="search.field01" theme="simple" cssClass="r-input w67p gray2"/></li>
        <li class="w25p">
        	<input type="submit" class="btn-blue4 ml20" value="查找" />
        </li>
  	</ul>   
  </s:form> 
  	
   <div class="table-outline fl widthfull mt10">
    <div class="table-title">
    		<a href="javascript:void(0)" class="btn fr mr8 mt4" onclick="deleteCompany()">删除</a>
         	<a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">新增公司</a>
         </div>
      <div class="table2-text sh-ttext">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                   	  <th scope="col" width="5%"><input type="checkbox" name="checkAllBox" id="checkAllBox" /></th>
                      <th scope="col" width="15%">公司名称</th>
                      <th scope="col" width="20%">城市</th>
                      <th scope="col" width="20%">专线</th>
                      <th scope="col" width="30%">员工</th>
                      <th scope="col">操作</th>
                    </tr>
                    
                    <s:if test="recordList.size == 0">
                     
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
             		 <s:iterator value="recordList" var="company" status="s">
							<tr align="center" companyId ="${company.companyId}" <s:if test="%{#s.count%2 == 0}">class="tr bg1"</s:if><s:else>class="tr bg2"</s:else>>
	                    	<th class="f-arial"><input type="checkbox" name="checkboxchild" id="child0" value="${company.companyId}"/></th>
	                    	<td class="f-arial">${company.companyName }</td>
	                    	<td class="f-arial">${company.companyProvince}/${company.companyCity }</td>
	                    	<td class="f-arial">${company.lineCount }</td>
	                    	<td class="f-arial">${company.passengerCount }</td>
	                    	<th class="f-arial">
	                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="showEditPage('${company.companyId}');">编辑</a>
	                    	</th>
	                    </tr> 
                    </s:iterator>
                  </table>
                   
          	</div>
			<%@include file="/WEB-INF/view/pageView.jspf" %>
          </div>   
		</div>
	</div>
</div>
 <form action="../specialLineLine/getCompanyLineList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="currentPage" id="currentPage"/>
  </form> 
</body>
</html>
<script type="text/javascript">
$(function(){
	//清除input框默认值  
	clearInputDefaultVal();

	
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

//增加公司
function showAddPage()
{
	$("#iframe_right", parent.parent.window.document).attr("src","../specialLineLine/addCompany.action");
}

//编辑公司
function showEditPage(companyId)
{
	$("#iframe_right", parent.parent.window.document).attr("src","../specialLineLine/specialLineDetail.action?companyId="+companyId);
}

//公司详情
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
		var companyId = $(this).parent().attr("companyId");
		//跳转到公司详情
		$("#iframe_right", parent.parent.window.document).attr("src","../specialLineLine/specialLineDetail.action?companyId="+companyId);
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
//------------------------- 列表选中和未选中end------------------------------


//删除公司
function deleteCompany(){
	if($("input[name='checkboxchild']:checked").length <= 0)
	{
		parent.parent.showCommonTip("请至少选择一项");
	}else{
		parent.parent.showPopCommonPage("确定要删除吗？");
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
		//动态添加方法
		$("#commonSure",parent.window.document).click(function(){
			parent.parent.closePopCommonPage();
			
			var companyIds = getCheckLoginIds();
		
			$.ajax({
				url:'../specialLineLine/deleteCompany.action?companyIds='+companyIds,
				type:'get',
				cache:false,	
				dataType:"text",	
				success:function(data){	
					
					if(data == "yes"){
						parent.parent.showRturnTip("删除成功",true);
						window.location.href="../specialLineLine/getCompanyLineList.action?level=two";
					}
					if(data == "no"){
						parent.parent.showRturnTip("删除失败",false);
					}
		
					if(data == "error"){
						parent.parent.showRturnTip("删除失败",false);	
					}
				}
			});
			//解绑定，防止多次执行click事件
			$("#commonSure",parent.window.document).unbind('click');
		});
	}

	
}
</script>