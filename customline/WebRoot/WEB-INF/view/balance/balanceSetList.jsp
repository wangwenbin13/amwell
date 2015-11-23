<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>财务结算-结算设置</title>
<jsp:include page="../resource.jsp"/>
</head>
<body>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;财务结算&nbsp;>&nbsp;结算设置</p></div>
<div class="mt10 ml20 mr20 black1">
   <div class="table-title">
        <ul>
        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">结算设置</a></li>
        </ul>
    </div>
    <s:form action="../adminManager/toList.action" method="post" id="searchForm" theme="simple">
       	<ul class="r-sub-sel mt20">
       		<li class="w20p"><span class="fl">承运商：</span>
               <s:select cssClass="w68p p3" list="#{2:'所有代理商',0:'侨城',1:'租车在线'}" >
	        	</s:select>
            </li>
            <li class="w20p"><span class="fl">包车类型：</span> 
               <s:select cssClass="w68p p3" list="#{2:'所有代理商',0:'侨城',1:'租车在线'}" >
	        	</s:select>
            </li>
            <li class="w20p"><span class="fl">包车代理商：</span> 
               <s:select cssClass="w68p p3" list="#{2:'所有代理商',0:'侨城',1:'租车在线'}" >
	        	</s:select>
            </li>
            
           	<li>
           		<a href="javascript:void(0);"  class="btn-blue4" onclick="">查找</a>
           	</li>
           </ul>
         </s:form>
           
	<div class="table-outline fl widthfull mt20">
         <div class="table-title">
         	<a href="javascript:void(0)" onclick="goAddModle()" class="btn fr mr8 mt4">增加模板</a>
         	<a href="javascript:void(0)" class="btn fr mr8 mt4">应用模板</a>
         </div>
         <div class="table2-text sh-ttext clearfix">
           <div style="overflow-x:auto;overflow-y:hidden">
                   <table width="100%" border="0" class="table1" id="tableDiv">
                        <tr align="center">
	                      <th scope="col" width="5%"><input type="checkbox" name='checkAllBox' id="checkAllBox"/></th>
	                      <th scope="col" width="15%">承运商</th>
	                      <th scope="col" width="10%">包车类型</th>
	                      <th scope="col" width="15%">代理商</th>
	                      <th scope="col">结算模板</th>
	                      <th scope="col" width="8%">操作</th>	                      
	                    </tr>
                      	<tr align="center">
                      		<td><input type="checkbox" name="checkboxchild" id="child${s.index }" value="${admin.userId }"/></td>
                      		<td>侨城</td>
                      		<td>侨城</td>
                      		<td>侨城</td>
                      		<td>侨城</td>
                      		<td><a href="javascript:void(0);" class="blue1">应用模板</a></td>
                      	</tr>
                      <s:if test="recordList.size == 0">
                     
                           <tr align="center" class="bg1">
              					<td colspan="9"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
             		             		
                  </table>
   			</div>
   		</div>
   		
   </div>
</div>

<form action="../adminManager/toList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
   	<s:hidden theme="simple" name="currentPage" id="currentPage"/>
  </form>
</body>
</html>
<script type="text/javascript">

//查找
function findDataByCondition(){
	textTrim("userName");
	textTrim("createBy");
	$("#searchForm").submit();
}
function goAddModle(){
	$("#iframe_right",parent.parent.window.document).attr("src","../balanceSet/addBalance.action");  
}


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
		parent.parent.showCommonTip("请至少选择一项");
	}
	else{
		parent.parent.showPopCommonPage("确定要删除吗？");
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
		//动态添加方法
		$("#commonSure",parent.window.document).click(function(){
			parent.parent.closePopCommonPage();
			var userids = getCheckLoginIds();
			var inCount = userids.split(":").length;
			
			$.ajax({
				url:'../adminManager/deleteAdmin.action?userids='+userids,
				type:'post',
				cache:false,	
				dataType:"text",	
				success:function(data){	
					
					if("ok"==data){
						//$("#searchform").submit();
						parent.parent.showRturnTip("删除成功",true);
						//判断当前页是否只有一条记录
						var currPage = $("#currentPage").val();
						var pageSize = $("#pageSize").val();
						var totalCount = $("#recordCount").val();
						//说明当前页只有一条记录 ,需要跳转到上一页
						var npageNum = ((totalCount-inCount)+pageSize-1)/pageSize;
						if(npageNum<currPage){
							$("#currentPage").val(npageNum);
						}
						$("#turnPage").submit();
					}
					if("error" == data)
					{
						parent.parent.showRturnTip("删除失败",false);
					}
				}
						
			}); 
			//解绑定，防止多次执行click事件
			$("#commonSure",parent.window.document).unbind('click');
		});
	}
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
				loginIds = loginIds + checkboxs[i].value + ":";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
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
				loginIds = loginIds + checkboxs[i].value + ":";
			}
		}
	}
	
	if ("" != loginIds)
	{
		loginIds = loginIds.substring(0, loginIds.length - 1);
	}
	
	return loginIds;
}


//==============================================================去除搜索框去除两端空格查询开始=================================================================
function textTrim(trimId) {
	 var trimValue = $("#"+trimId).val();
	$("#"+trimId).val(trimValue.replace(/(^\s*)|(\s*$)/g, ""));
}
//===============================================================去除搜索框去除两端空格查询结束================================================================

</script>