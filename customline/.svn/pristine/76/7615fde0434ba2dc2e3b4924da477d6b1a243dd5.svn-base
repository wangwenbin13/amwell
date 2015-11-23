<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠券管理-优惠券发放列表</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;优惠券发放列表</p></div>
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">优惠券发放列表</a></li>
           </ul>
       </div>
       <s:form id="searchform" action="../couponGroupGrant/couponGroupGrantList.action" theme="simple">
       <ul class="r-sub-sel black1 mt20 clearfix">
		   	<li class="w13p"><span class="fl">批次号：</span><s:textfield name="search.field01" cssClass="r-input w55p" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"></s:textfield></li>
		   	<li class="w13p"><span class="fl">发放方式：</span><s:select name="search.field02" list="#{'sys send':'系统发放','user get':'用户领取'}" cssClass="p3 w55p" headerKey="" headerValue="请选择"></s:select></li>
		   	<li class="w28p"><span class="fl">发放时段：</span>
		   		<span class="r-input fl w30p mr10"><input type="text" name="search.field03" value="${search.field03 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"/></span>
	            <span class="fl">至</span>
	            <span class="r-input fl w30p ml10"><input type="text" name="search.field04" value="${search.field04 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
			</li>
		   	<li class="w13p"><span class="fl">执行状态：</span>
		   	<s:select name="search.field05" list="#{'send':'发放中','closed':'已结束'}" cssClass="p3 w55p" headerKey="" headerValue="请选择"></s:select>
			</li>
		   	<li class="w13p"><span class="fl">配置人名：</span><s:textfield name="search.field06" cssClass="r-input w55p" onkeyup="this.value=this.value.replace(/[%]/g,'')"></s:textfield></li>
		   	<li>
		   	<input type="submit" class="btn-blue4" value="查找" />
        	<input type="reset" value="重置" class="btn-blue4" onclick="clearFormValue('searchform')"/>
        	</li>
	   </ul>
	   </s:form>
	   <div class="table-title mt10">
        	<a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
        </div>
      <div class="table2-text sh-ttext">  
      	<table width="100%" border="0" class="table1" id="tableDiv">
      		<tr align="center" class="th">
               <th scope="col" width="3%">序号</th>
               <th scope="col" width="8%">批次号</th>
               <th scope="col">任务名称</th>
               <th scope="col" width="4%">发放方式</th>
               <th scope="col" width="4%">发放用户</th>
               <th scope="col" width="13%">发放时段</th>
               <th scope="col" width="8%">执行状态</th>
               <th scope="col" width="10%">配置时间</th>
               <th scope="col" width="8%">配置人</th>
               <th scope="col" width="10%">修改时间</th>
               <th scope="col" width="8%">修改人</th>
               <th scope="col" width="10%">操作</th>
            </tr>
            <s:if test="null==list || list.size == 0">
                   <tr align="center" class="bg1">
      					<td colspan="12"><p class="f13 f-yahei gray4 t-c line26 mt10">
      					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
      				</tr>
     		 </s:if>
            <s:iterator value="list" var="v" status="l">
            <tr class="bg1 tr" align="center"><!--隔行换色样式名为：bg1和bg2区分  -->
            	<td class="f-arial">${(page.currentPage-1)*page.pageSize+l.index+1}</td>	
            	<td class="f-arial"><s:property value="couponGroupId"/></td>
            	<td class="f-arial"><s:property value="taskName"/></td>
            	<td class="f-arial">
            	<s:if test="%{sendCouponType == 'user get'}">用户领取</s:if>
            	<s:else>系统发放</s:else>
            	</td>
            	<td class="f-arial">
            	<s:if test="%{selectPass == 'oldUser'}">现有用户</s:if>
            	<s:else>新进用户</s:else>
            	</td>
            	<td class="f-arial"><s:property value="startTime"/> 至 <s:property value="endTime" /></td>
            	<td class="f-arial">
            	<s:if test="%{modeOfExecution == 'send'}"><font color="red">发放中</font></s:if>
            	<s:else>已结束</s:else></td>
            	<td class="f-arial"><s:property value="createOn"/></td>
            	<td class="f-arial"><s:property value="userName"/></td>
            	<td class="f-arial"><s:property value="updateOn"/></td>
            	<td class="f-arial"><s:property value="updateUserName"/></td>
            	<td>
            	<s:if test="%{selectPass == 'oldUser'}">
	            	<a href="javascript:void(0);" class="blue1" onclick="showDetailPage(${v.id})">详情</a>
	            	<a href="javascript:void(0);" class="blue1" onclick="doCopyGrant(${v.id});">复制发放</a>
            	</s:if>
            	<s:else>
            	    <s:if test="%{modeOfExecution == 'send'}">
	            	<a href="javascript:void(0);" class="blue1" onclick="showDetailPage(${v.id})">详情</a>
	            	<a href="javascript:void(0);" class="blue1" onclick="closeCoupon(${v.id});">停止发放</a>
	            	<a href="javascript:void(0);" class="blue1" onclick="showEditPage(${v.id})">修改</a>
	            	</s:if>
	            	<s:else>
	            	<a href="javascript:void(0);" class="blue1" onclick="showDetailPage(${v.id})">详情</a>
	            	<a href="javascript:void(0);" class="blue1" onclick="doCouponGrant(${v.id});">开始发放</a>
	            	<a href="javascript:void(0);" class="blue1" onclick="showEditPage(${v.id})">修改</a>
	            	</s:else>
            	</s:else>
            	</td>
            </tr>
            </s:iterator>
      	</table>
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
      </div>
   </div>
</body>
<script>
function showAddPage()
{
    $("#iframe_right", parent.parent.window.document).attr("src","../couponGroupGrant/toCouponGroupGrantAddPage.action");
}

function showDetailPage(grantId)
{
    $("#iframe_right", parent.parent.window.document).attr("src","../couponGroupGrant/toCouponGroupGrantDetailPage.action?grantId="+grantId+"&myPageIndex="+$("#currentPageIndex").val());
}

function showEditPage(grantId)
{
	parent.parent.showPopCommonPage("确定要修改该优惠券吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();

       $("#iframe_right", parent.parent.window.document).attr("src","../couponGroupGrant/toCouponGroupGrantEditPage.action?grantId="+grantId+"&myPageIndex="+$("#currentPageIndex").val());
    
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
    });
}

//复制发放
function doCopyGrant(grantId)
{
	parent.parent.showPopCommonPage("确定要复制该优惠券发放吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();

		 $("#iframe_right", parent.parent.window.document).attr("src","../couponGroupGrant/toCouponGroupGrantCopyPage.action?grantId="+grantId+"&myPageIndex="+$("#currentPageIndex").val());

		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
	});
}

//优惠券发放
function doCouponGrant(grantId)
{
	parent.parent.showPopCommonPage("确定要开始该优惠券发放吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();

		$.ajax({
	           url:'../couponGroupGrant/couponDetailAdd.action?grantId='+grantId+'&temp='+new Date()+'&myPageIndex='+$("#currentPageIndex").val(),
	           dataType:'text',
	           cache:false,//不从浏览器缓存中取
	           async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
	           success:function(data){
              	  if(data>0){
              		 parent.parent.showRturnTip("发放成功",true);
	                  //刷新本页
	            	 $("#turnPage").attr("action","../couponGroupGrant/couponGroupGrantList.action");
	            	 //刷新当前页信息
		             $("#turnPage").submit(); 
	              }
              	  if(data==-1){
              		parent.parent.showRturnTip("发放失败",false);
                  }
              	  if(data==-2){
              		parent.parent.showRturnTip("暂无符合过滤条件的用户",false);
              		 //刷新本页
	            	 $("#turnPage").attr("action","../couponGroupGrant/couponGroupGrantList.action");
	            	 //刷新当前页信息
		             $("#turnPage").submit(); 
                  }
              	if(data==-3){
               		parent.parent.showRturnTip("用户对该优惠券已无领取机会",false);
               		 //刷新本页
 	            	 $("#turnPage").attr("action","../couponGroupGrant/couponGroupGrantList.action");
 	            	 //刷新当前页信息
 		             $("#turnPage").submit(); 
                   }
	           }
			});

		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
	});
}

//关闭优惠券发放
function closeCoupon(grantId)
{
	parent.parent.showPopCommonPage("确定要停止该优惠券发放吗？");
	//解绑定，防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
	$("#commonSure",parent.parent.window.document).click(function(){
		parent.parent.closePopCommonPage();

		$.ajax({
	           url:'../couponGroupGrant/couponGrantClose.action?grantId='+grantId+'&temp='+new Date()+'&myPageIndex='+$("#currentPageIndex").val(),
	           dataType:'text',
	           cache:false,//不从浏览器缓存中取
	           async:false,//async:false后;就是强制成了同步执行，后面的代码必须等待ajax交互完成了才继续执行
	           success:function(data){
              	  if(data==1){
              		 parent.parent.showRturnTip("关闭成功",true);
	                  //刷新本页
	            	 $("#turnPage").attr("action","../couponGroupGrant/couponGroupGrantList.action");
	            	 //刷新当前页信息
		             $("#turnPage").submit(); 
	              }
              	  if(data==-1){
              		parent.parent.showRturnTip("关闭失败",false);
                  }
	           }
			});

		//解绑定，防止多次执行click事件
		$("#commonSure",parent.parent.window.document).unbind('click');
	});
}
</script>
</html>
