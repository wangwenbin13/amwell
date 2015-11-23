<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理-乘客列表</title>
<jsp:include page="../resource.jsp"/>
</head>
  
  <s:url id="preurl" value="/operationPassenger/passengerList.action">
	<s:param name="p">
		<s:property value="page.previousIndex" />
	</s:param>
</s:url>


<s:url id="nexturl" value="/operationPassenger/passengerList.action">
	<s:param name="p">
		<s:property value="page.nextIndex" />
	</s:param>
</s:url>
<s:url id="firsturl" value="/operationPassenger/passengerList.action">
	<s:param name="p">
		<s:property value="0" />
	</s:param>
</s:url>
<s:url id="lasturl" value="/operationPassenger/passengerList.action">
	<s:param name="p">
		<s:property value="page.lastIndex" />
	</s:param>
</s:url>
<s:url id="tourl" value="/operationPassenger/passengerList.action">
	<s:param name="p">
		<s:property value="page.currentPage" />
	</s:param>
</s:url>
  
  <body>
  <div class="mt10 black1">
   <s:if test="#attr.passengerType==-1">
   <form name="" action="../operationPassenger/passengerList.action" method="post" id="searchform">
   </s:if>
   <s:elseif test="#attr.passengerType==1">
   <form name="" action="../operationPassenger/appPassengerList.action" method="post" id="searchform">
   </s:elseif>
   <s:elseif test="#attr.passengerType==3">
   <form name="" action="../operationPassenger/weiXinPassengerList.action" method="post" id="searchform">
   </s:elseif>
   <s:elseif test="#attr.passengerType==4">
   <form name="" action="../operationPassenger/disanfangPassengerList.action" method="post" id="searchform">
   </s:elseif>
   
   <!-- 存排序方式信息 -->
   <s:hidden theme="simple" name="search.field07" id="orderByWay"></s:hidden>
   <s:hidden theme="simple" name="search.field08" id="orderByColumnName"></s:hidden>
   <s:hidden theme="simple" name="search.field10" value="%{search.field10}"></s:hidden>
   
     
   	 <ul class="r-sub-sel black1 mt20 clearfix">
       	<li class="w23p">
           	<span class="fl">时间：</span>
            <span class="r-input fl w34p mr10">
            <input type="text" name="search.field01" value="${search.field01}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/>
            </span>
            <span class="fl">至</span>
            <span class="r-input fl w34p ml10">
            <input type="text" name="search.field02" value="${search.field02}" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/>
            </span>
        </li>
        <li class="w14p"><span class="fl">乘客：</span>
        <s:textfield name="search.field03" cssClass="r-input w65p gray2" id="nikeNameOrId"></s:textfield>
        </li>
        <li class="w14p"><span class="fl">手机号码：</span>
        <s:textfield name="search.field04" cssClass="r-input w57p gray2"></s:textfield>
        </li>
        <li class="w45p"><span class="fl">账号状态：</span>
        <s:checkboxlist name="search.field05" list="#{'0':'正常','1':'已拉黑'}" cssClass="checkbox mr5"></s:checkboxlist>
        <input type="hidden" id="theStatus" value="${search.field05}"/>
        
        <s:if test="#attr.passengerType==4">
         <span class="ml10">用户类型：</span>
        <s:select name="search.field06" list="#{'':'全部','1':'蛇口','2':'彩生活'}" cssClass="mr5 r-input" ></s:select>
        </s:if>
        
         <input type="submit" class="btn-blue4 ml10" value="查找"/>
         <s:reset value="重置" cssClass="btn-blue4" theme="simple" onclick="clearFormValue('searchform')"/>
        </li>
  	</ul>   
  </form> 
<s:if test="%{regsit==1}">
		<div class="table-title mt10">
		    		<a class="btn fr mr8 mt4" onclick="addPassengerRegsitPage();" href="javascript:void(0)">批量注册</a>
		      	<a class="btn fr mr8 mt4" onclick="addPassengerPage();" href="javascript:void(0)">添加用户</a>
		 </div>
</s:if>
	<div class="table2-text sh-ttext mt10">       
       	<div style="overflow-x:auto;overflow-y:hidden">
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="5%">序号</th>
                      <th scope="col" width="11%" class="cursor-d" onclick="orderByClick('registerTime');" style="cursor: pointer;">注册时间<span class="arrow arrow-desc ml4" id="registerTime"><!--arrow为公用样式，升序在后面追加arrow-asc,降序追加arrow-desc--></span></th>
                      <th scope="col" width="10%">头像</th>
                      <th scope="col" width="10%">ID</th>
                      <th scope="col" width="5%">账户余额</th>
                      <th scope="col" width="10%">昵称</th>
                      <th scope="col" width="10%">性别</th>
                      <th scope="col" width="10%">手机号码</th>
                      <th scope="col" width="5%">订票信息</th>
                      <th scope="col" width="5%">退票记录</th>
                      <th scope="col" width="5%">改签记录</th>
                      <th scope="col" width="5%">账号状态</th>
                      <th scope="col" width="10%">操作</th>
                    </tr>
                    <s:if test="null==list || list.size == 0">
                           <tr align="center" class="bg1">
              					<td colspan="10"><p class="f13 f-yahei gray4 t-c line26 mt10">
              					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
              				</tr>
             		 </s:if>
                    <s:iterator value="list" var="v" status="l">
                    <tr class="bg1 tr" align="center">
                    	<td class="f-arial">
                        	${(page.currentPage-1)*page.pageSize+l.index+1}
                    		<s:hidden theme="simple" id="theId" name="passengerId" ></s:hidden>
                    	</td>	
                    	<td class="f-arial"><s:property value="registerTime"/></td>
                    	<td class="f-arial">
                    	  <s:if test="sex==0">
                    	  	<img width="60" height="60" class="p10" src="<s:property value='headerPicUrl'/>" onerror="javascript:this.src='../images/logo@2x.png'"/>
                    	  </s:if>
                    	  <s:else>
                    	  	<img width="60" height="60" class="p10" src="<s:property value='headerPicUrl'/>" onerror="javascript:this.src='../images/logo_female@2x.png'"/>
                    	  </s:else>
                    	</td>
                    	<td class="f-arial"><s:property value="displayId" /></td>
                    	<td class="f-arial"><s:property value="theBalance" />元</td>
                    	<td class="f-arial"><em class="blue1"><s:property value="nickName"/></em></td>
                    	<td class="f-arial"><s:property value="%{sex==0?'男':'女'}"/></td>
                    	<td class="f-arial"><s:property value="telephone"/></td>
                    	<td class="f-arial">
                    		<s:if test="%{orderCount==0}">无</s:if>
                    		<s:else><em class="red1">有</em></s:else>
                    	</td>
                    	<td class="f-arial">
                    		<s:if test="%{returnedOrderCount==0}">无</s:if>
                    		<s:else><em class="red1">有</em></s:else>
                    	</td>
                    	<td class="f-arial">
                    		<s:if test="%{changeOrderCount==0}">无</s:if>
                    		<s:else><em class="red1">有</em></s:else>
                    	</td>
                    	<td class="f-arial">
	                    	<s:if test="%{blackFlag==0}">
	                    		<em class="green1">正常</em>
	                    	</s:if>
	                    	<s:else>
	                    		<em class="red1">黑户</em>
	                    	</s:else>
                    	</td>
                    	<th><a href="javascript:void(0);" class="fn blue1" onclick="toDoLaHei('<s:property value="passengerId"/>','<s:property value="blackFlag"/>')"><s:property value="%{blackFlag==0?'拉黑':'恢复'}"/></a>
                    		<a href="javascript:void(0);" class="fn blue1 mr10" onclick="rePassword('${v.passengerId}')">重设密码</a>
                    	</th>
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
                       		<span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页&nbsp;&nbsp;<s:property value="page.totalCount" />条数据]</span>
                       </s:if>
                 </div>
                 
                 <input type="hidden" id="passengerType" value="${passengerType}"/>
                 <input type="hidden" id="myCurrentPageIndex" value="${currentPageIndex}"/>
                  <!--End page-->
    <form action="../operationPassenger/passengerList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field02" value="${search.field02}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
    <input type="text" name="search.field05" value="${search.field05}"/>
    <input type="text" name="search.field06" value="${search.field06}"/>
    <input type="text" name="search.field07" value="${search.field07}"/>
    <input type="text" name="search.field08" value="${search.field08}"/>
   
    <input type="text" name="currentPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
  </form> 
          </div>
<%--   </s:form>--%>
 
</div>
  </body>
</html>
<script type="text/javascript">
$(function(){
	
	//清除input框默认值  
	clearInputDefaultVal();

	//修改排序样式
	changeClass();

	//改变checkbox显示值的颜色
	$("input[name='search.field05']").each(function(){
       if($(this).val()=='0'){
          $(this).next("label").addClass('green1');
       }
       if($(this).val()=='1'){
           $(this).next("label").addClass('red1');
        }
	});
	if($("#theStatus").val()!=''){
		var $the_str=$("#theStatus").val().split(',');
		if($the_str.length==2){
			$("input[name='search.field05']").attr("checked","checked");
		}
	}
});

//添加用户
function addPassengerPage()
{ 
     $("#topHide", parent.parent.window.document).show();
     $("#leftHide", parent.parent.window.document).show();
     $("#mainBody",parent.window.document).show();
     $("#showPage",parent.window.document).load("../operationPassenger/popPassenger.action"); 
}

//批量注册用户
function addPassengerRegsitPage()
{ 
     $("#topHide", parent.parent.window.document).show();
     $("#leftHide", parent.parent.window.document).show();
     $("#mainBody",parent.window.document).show();
     $("#showPage",parent.window.document).load("../operationPassenger/popPassengerRegsit.action"); 
}
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

//乘客详情
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
		//跳转到乘客详情
		$("#iframe_right",parent.parent.window.document).attr("src","../operationPassenger/passengerDetail.action?passengerId="+$(this).parent().find("#theId").val()+"&currentPageIndex="+$("#myCurrentPageIndex").val()+"&theTab="+$("#passengerType").val());  
	}
});

//点击拉黑
function toDoLaHei(theId,blackFlag){
	
	var str='';
	if(blackFlag=="0"){
		str='确定将该用户拉黑？';
	}
	else if(blackFlag=="1"){
		str='确定将该用户恢复？';
	}

		parent.parent.showPopCommonPage(str);
		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
		//动态添加方法
		$("#commonSure",parent.parent.window.document).click(function(){
			parent.parent.closePopCommonPage();

			$.ajax({
		           url:'../operationPassenger/passengerChangeStatus.action?passengerId='+theId+'&blackFlag='+blackFlag+'&temp='+new Date(),
		           dataType:'text',
		           cache:false,//不从浏览器缓存中取
		           async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
		           success:function(data){
	              	  if(data=='ok'){
		                  //刷新本页
		            	 // window.location.reload();
		            	 var $passengerType=$("#passengerType").val();
						if($passengerType=='-1'){
							$("#turnPage").attr("action","../operationPassenger/passengerList.action");
						}
						if($passengerType=='1'){
							$("#turnPage").attr("action","../operationPassenger/appPassengerList.action");
						}
						if($passengerType=='3'){
							$("#turnPage").attr("action","../operationPassenger/weiXinPassengerList.action");
						}
						if($passengerType=='4'){
							$("#turnPage").attr("action","../operationPassenger/disanfangPassengerList.action");
						}
		            	 //刷新当前页信息
			              $("#turnPage").submit(); 
		              }
		              else{
		            	  parent.parent.showRturnTip(data,false);
		              }
		           }
				});

			//解绑定，防止多次执行click事件
			$("#commonSure",parent.parent.window.document).unbind('click');
		});
}

//----------------------------排序模式start-------------------------------------
//排序
function orderByClick(orderByParam)
{
	//初始化按降序排列
	var orderTypeParam = "ASC";

	var $orderByWay=$("#orderByWay").val();
	if($orderByWay!=''){
		if($orderByWay.toUpperCase()=='DESC'){
			orderTypeParam='ASC';
		}
		else{
			orderTypeParam='DESC';
		}
	}
	
	$("#orderByWay").val(orderTypeParam);
 	$("#orderByColumnName").val(orderByParam);
	//排序后去请求数据
	$("#searchform").submit();
}

function changeClass(){
	//升序添加样式
	if($("#orderByWay").val()!=''){
		if ($("#orderByWay").val().toUpperCase() == "ASC")
		{
			addAndRemoveClass('registerTime',"arrow-desc","arrow-asc");
		}
		
		else
		{
			addAndRemoveClass('registerTime',"arrow-asc","arrow-desc");
	   	}
	}
}

//排序时添加或是删除样式
function addAndRemoveClass(orderByParam,class1,class2)
{
  $("#"+orderByParam).removeClass(class1).addClass(class2);
}
//----------------------------排序模式end-------------------------------------

//翻页方法
function toPage(value){
	$("#currentPageIndex").val(value);
	var $passengerType=$("#passengerType").val();
	if($passengerType=='-1'){
		$("#turnPage").attr("action","../operationPassenger/passengerList.action");
	}
	if($passengerType=='1'){
		$("#turnPage").attr("action","../operationPassenger/appPassengerList.action");
	}
	if($passengerType=='3'){
		$("#turnPage").attr("action","../operationPassenger/weiXinPassengerList.action");
	}
	if($passengerType=='4'){
		$("#turnPage").attr("action","../operationPassenger/disanfangPassengerList.action");
	}
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

//点击查询按钮
function to_do_search(){
	if($("#nikeNameOrId").val()=='输入ID或昵称'){
		$("#nikeNameOrId").val('');
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


//显示错误信息页面
function showErrorPage()
{ 
     $("#topHide", parent.parent.window.document).show();
     $("#leftHide", parent.parent.window.document).show();
     $("#mainBody",parent.window.document).show();
     $("#showPage",parent.window.document).load("../passengerRegist/popfailAdd.action"); 
}

//重设用户密码
function rePassword(passengerId){
	parent.parent.showPopCommonPage("是否确定重置用户密码");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();
		$.ajax({
				url:'../operationPassenger/resetPsPassword.action',
				type:'post',
				data:{"passengerId":passengerId},
				cache: false,
				async:false,
				dataType:"json",
				success: function(data){
					if(data=="idNull"){
						parent.parent.showRturnTip("号码为空",false);
					}else if(data=="success"){
						parent.parent.showRturnTip("重置成功",true);
					}else{
						parent.parent.showRturnTip("重置失败",false);
					}
				}
		});
	//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
	});
}

</script>
