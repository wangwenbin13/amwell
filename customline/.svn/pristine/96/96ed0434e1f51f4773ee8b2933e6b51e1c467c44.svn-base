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
<title>用户管理-代理商列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;代理商管理&nbsp;>&nbsp;代理商列表<span class="blue1 ml25"></span></p></div>
  <div class="mt10 ml20 mr20 black1">
          <div class="fl widthfull p-r">
            <div class="table-title">
                <ul>
                	<li class="on"><a href="javascript:void(0)" class="f14 blue2">代理商列表</a></li>
                </ul>
            </div>
   <form name="" action="" method="post">
   	 <ul class="r-sub-sel black1 mt20 clearfix">
          <li class="w20p"><span class="t-r w65 fl">代理商：</span><s:textfield name="search.field03" theme="simple" cssClass="r-input w67p gray2"/></li>
	      <li><span class="fl">地址：</span>			
  			<select class="p3 fl">
             		<option value="0">请选择</option>
             </select>
             <select class="p3 fl ml10">
             		<option value="0">请选择</option>
             </select>
	      	<input type="submit" class="btn-blue4 ml20" value="查找" />
	      </li>
  	</ul>   
  </form> 
   <div class="table-outline fl widthfull mt20">
    <div class="table-title">
         	<a href="javascript:void(0)" onclick="goToAgentPage();" class="btnagent fr mr8 mt4">增加代理商</a>
         </div>
      <div class="table2-text sh-ttext">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号<!-- <input type="checkbox" name='checkAllBox' id="checkAllBox"/> --></th>
                      <th scope="col">代理商名称</th>
                      <th scope="col" width="18%">所在地</th>
                      <th scope="col" width="13%">联系人</th>
                      <th scope="col" width="10%">联系电话</th>
                      <th scope="col" width="10%">账号</th>
                      <th scope="col" width="10%">账号状态</th>
                      <th scope="col" width="12%">操作</th>
                    </tr>
                    
                    <%--<s:if test="%{page.pageCount == 0}">
                     
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    
                    --%>                   			             
						<tr align="center" class="tr bg1" >
					
						<td class="f-arial">${s.index+1}<!-- <input type="checkbox" name="checkboxchild" id="child${s.index}" value="${carBasicResultListModel.carBasicInfoId}"/> --></td>
                    	<td>租车在线</td>
                    		<td>湖北黄石</td>
                    		<td>sss</td>
                    		<td class="f-arial">13258698755</td>
                    		<td class="f-arial">echo</td>
                    		<td>
                    			<s:if test="%{#mgrBussiness.accountStatus==1}"><em class="green1">启用</em></s:if>
                    			<s:if test="%{#mgrBussiness.accountStatus==0}"><em class="red1">禁用</em></s:if>
                    		</td>
                    		<th><a href="javascript:void(0);" class="fn blue1 mr10" onclick="showEditPage();">编辑</a><a href="javascript:void(0);" class="fn blue1 mr10" onclick="updateAccountStatu('${mgrBussiness.businessId}','0');">禁用</a><a href="javascript:void(0);" class="fn blue1 mr10" onclick="resetPassword('${mgrBussiness.businessId}','${mgrBussiness.createBy}','${mgrBussiness.loginName }');">重置密码</a></th>
                    	</tr>
                   	
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

//增加代理商
function goToAgentPage()
{
	$("#iframe_right", parent.parent.window.document).attr("src","../agent/addAgent.action");
}

//编辑代理商
function showEditPage()
{
	$("#iframe_right", parent.parent.window.document).attr("src","../agent/addAgent.action");
}

//代理商详情
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
		//var businessId = $(this).parent().attr("businessId");
		//跳转到代理商详情
		$("#iframe_right", parent.parent.window.document).attr("src","../agent/addAgent.action");
	}
});



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

</script>