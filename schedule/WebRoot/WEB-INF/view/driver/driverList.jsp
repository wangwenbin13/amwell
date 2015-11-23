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
<title>司机管理-司机列表</title>
<jsp:include page="../resource.jsp"/>
</head>
  
<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;司机管理&nbsp;>&nbsp;司机列表<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 black1 mr28">
      <form action="../dispatchDriver/driverInfoList.action" method="post">
      	<p class="fw f14 f-arial">司机列表</p>
      	<ul class="r-sub-sel mt20 clearfix">
            <li class="fl">
	             <span class="fl">司机姓名：</span>
	             <s:textfield name="search.field01" cssClass="r-input w130"></s:textfield>
            </li>
            <li class="fl">
                <span class="fl">手机号码：</span> 
                <s:textfield name="search.field02" cssClass="r-input w130"></s:textfield>
            </li>
            <li class="fl p-r"><span class="fl w65 t-r">驾照类型：</span>
				<div class="fl r-input w130"><span id="driverStyleDiv"></span><span class="fr sel-arrow mt10 mr10"></span></div>
				<s:select list="#{'':'驾照类型','0':'A1','1':'A2','2':'A3','3':'B1','4':'B2'}" name="search.field03" cssClass="p-a sel-demo w134 h29" id="driverStyle"></s:select>
			</li>
          	<li class="fl">
          		<input class="search-btn" type="submit" value=""/>
          		<input type="hidden" name="searchOrNo" value="1"/>
          	</li>
          	<li style="float:right">
          		<a class="yellow-btn" href="javascript:void(0)" onclick="parent.openLeftMenu('../dispatchDriver/toDriverEditPage.action');"><em class="fw f20 va3">+</em>&nbsp;添加司机</a>
          	</li>
         </ul>
      </form>
     <table class="table1 tableCursor mt20" border="0" width="100%" id="tableDiv">
		<tr class="th">
			<th scope="col" width="5%"><input type="checkbox" name='checkAllBox' id="checkAllBox" class="checkbox"/></th>
            <th scope="col" width="8%">司机姓名</th>
            <th scope="col" width="8%">手机号码</th>
            <th scope="col" width="8%">驾照类型</th>
            <th scope="col" width="5%">性别</th>
            <th scope="col" width="8%">年龄</th>
	  		<th scope="col" width="12%">驾驶证号</th>
            <th scope="col" width="10%">年检日期</th>
            <th scope="col" width="15%" align="left">状态</th>
            <th scope="col" align="right" class="pr8p">操作</th>
		</tr>
		<!--<tr class="tips gray4">	
			<td colspan="9"><span class="display-ib tips-light ml25"></span>点击“发送密码”可给司机发送6位随机码作为默认密码，司机可用手机号码和默认密码登录司机端APP,你也可以“重置密码”，系统将重新发送新随机码作为登录新密码。</td>
			<th><a class="fr mr60" herf="javascript:void(0)"><em class="display-ib fw gray2 f-arial mr4 vf1">X</em>关闭</a></th>
		</tr>
		-->

        <s:if test="(null==list||list.isEmpty())">
			<tr class="noDateList">
				<th colspan="10">				
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
	        <input type="checkbox" name="checkboxchild" id="child${l.index}" class="checkbox" value="<s:property value='driverId'/>"/>
	        <s:hidden name="driverId" id="theId" theme="simple"></s:hidden>
	        </th>
	    	<td><s:property value="driverName"/></td>
	    	<td><s:property value="telephone"/></td>
	    	<td>
	    	<s:if test="%{driverType==0}">A1</s:if>
	    	<s:if test="%{driverType==1}">A2</s:if>
	    	<s:if test="%{driverType==2}">A3</s:if>
	    	<s:if test="%{driverType==3}">B1</s:if>
	    	<s:if test="%{driverType==4}">B2</s:if>
	    	</td>
	    	<td><s:property value="%{sex==0?'男':'女'}"/></td>
	    	<td>
	    	<s:property value="age"/>
	    	</td>
	    	<td><s:property value="driverNo"/></td>
	    	<td><s:property value="inspectionDate"/></td>
	    	<td align="left">
	    	<s:if test="%{accountStatus==0}"><em class="green1">启用</em></s:if>
	    	<s:if test="%{accountStatus==1}"><em class="red4">禁用</em></s:if>
	    	</td>
	    	<th align="right" class="pr3p">
				<a href="../dispatchDriver/toDriverEditPage.action?driverId=<s:property value='driverId'/>" class="yellow3 mr15">编辑</a>
				<span class="vertical-line"></span>
				<a href="javascript:do_del('<s:property value="driverId"/>')" class="yellow3 ml20 mr15">删除</a>
				<span class="vertical-line"></span>
				<a href="javascript:void(0)" class="yellow3 ml20" onclick="reset_password('<s:property value="driverId"/>')">重置密码</a>
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
       
<form action="../dispatchDriver/driverInfoList.action" method="post" style="display: none;" id="turnPage">
    <input type="text" name="search.field01" value="${search.field01}"/>
   <input type="text" name="search.field02" value="${search.field02}"/>
   <input type="text" name="search.field03" value="${search.field03}"/>
   <input type="text" name="currentPage" id="currentPage" value="${currentPage}"/>
</form> 
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//驾照类型
	$("#driverStyle").change(function(){
		$("#driverStyleDiv").text($("#driverStyle").find("option:selected").text());
	});
	//回显所选条件
	$("#driverStyleDiv").text($("#driverStyle").find("option:selected").text());
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
		parent.showPopCommonPage2("请勾选需要删除的司机","true","remind");
		return false;
	}

	do_del(getCheckIds());

});

function do_del(the_id){
	var the_result='';
	$.ajax({
		url:'../dispatchDriver/searchBeforeDel.action?driverId='+the_id,
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
			url:'../dispatchDriver/driverInfoDelete.action?driverId='+the_id,
			dataType:'html',
			cache:false,
			success:function(data){
			    if(data=='ok'){
			    	parent.showRturnTip("删除成功","true");
				    //window.location.reload();
				    $("#turnPage").submit();
				}
			    else{
				    parent.showRturnTip(data,"false");
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

//进入司机详情
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
		//进入司机详情
		window.location.href = "../dispatchDriver/driverInfoDetail.action?driverId="+$(this).parent().find("#theId").val();
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
	if("" == value){
		return;
	}
	value = parseInt(value);
	if(value < 1){
		parent.parent.showCommonTip("请输入大于0的数!");
		return;
	}else if(value>totalPage){
		parent.parent.showCommonTip("超出最大页数!");
		return;
	}
	var pageSize = "${page.pageSize}";
	toPage((value-1)*parseInt(pageSize));
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

//点击重置密码
function reset_password(theId){
	parent.showPopCommonPage("重置后密码默认为手机号码后六位，确认重置吗？");
	$("#commonSure",parent.window.document).text("确定");
	$("#commonCancel",parent.window.document).text("取消");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.window.document).click(function(){
		parent.closePopCommonPage();
		//ajax调用删除方法
		$.ajax({
			url:'../dispatchDriver/resetPassword.action?driverId='+theId,
			dataType:'html',
			cache:false,
			success:function(data){
			    if(data=='ok'){
			    	parent.showRturnTip("重置成功","true");
				    //window.location.reload();
			    	$("#turnPage").submit();
				}
			    else{
				    parent.showRturnTip(data,"false");
			    }
			},
			error:function(){
				parent.showRturnTip("重置失败","false");
			}
		});
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.window.document).unbind('click');
	});
}

</script>
