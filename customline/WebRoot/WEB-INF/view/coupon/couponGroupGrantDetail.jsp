<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.amwell.vo.coupon.CouponGroupGrant"%>
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
<title>优惠券管理-优惠券发放详情页面</title>
<jsp:include page="../resource.jsp"/>
<style>
    .errorTip{
        display:inline-block;
        color:#d1261e;
        background:#f7e7e9;
        margin-left:10px;
        padding:0 5px;
    }
</style>
</head>

<body>
<div id="mainBody" class="mainBody"></div>
<div id="showPage" class="showPage"></div>
<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;优惠券管理&nbsp;>&nbsp;优惠券发放页面详情</p></div>
<s:form id="addForm" theme="simple">
  <div class="mt10 ml20 mr20 black1">
       <div class="table-title">
           <ul>
           	<li class="on"><a href="">优惠券发放详情</a></li>
           </ul>
       </div>
       <div class="sh-add-new sh-add-new-coupon">  
	       <ul class="clearfix mt20 f12 ruleListBox">
<%--			   	<li class="clearfix"><span class="w120 t-r fl">优惠券批次号：</span>${couponGroup.couponGroupID}</li>--%>
<%--			   	<li class="clearfix"><span class="w120 t-r fl">总发行量：</span>${couponGroup.couponGroupCount}</li>--%>
<%--			   	<li class="clearfix"><span class="w120 t-r fl">人均领取量：</span>${couponGroup.averageNum}</li>--%>
			   	
			   	<li class="clearfix">
			   	<span class="w120 t-r fl">发放信息：</span>
			   	<table class="table1" id="target" width="100%" style="margin-left: 120px; border:1px solid #cddfec"> 
			   	    <tr align="center"><th>优惠券批次号</th><th>任务名称</th><th>总发行量</th><th>人均限领份额</th><th>发放方式</th><th>可领优惠券时间范围</th></tr>
			   	    <tr align="center">
			   			<td>${couponGroup.couponGroupID}</td>
			   			<td>${couponGroupGrant.taskName}</td>
			   			<td>${couponGroup.couponGroupCount}</td>
			   			<td>${couponGroup.averageNum}</td>
			   			<td>
			   			<s:if test="#attr.couponGroupGrant.sendCouponType == 'user get'">用户领取</s:if><s:else>系统发放</s:else>
			   			</td>
			   			<td>${couponGroupGrant.startTime} 至 ${couponGroupGrant.endTime}</td>
			   		</tr>
			   	</table>
			   	</li>
			   	
			   	<li class="clearfix">
			   	<span class="w120 t-r fl">优惠券：</span>
			   	<table class="table1" id="target" width="100%" style="margin-left: 120px; border:1px solid #cddfec"> 
			   	    <tr align="center"><th>优惠券名称</th><th>面值</th><th>门槛</th><th>数量</th><th>有效期</th>
<%--			   	    <th>发行量</th>--%>
			   	    </tr>
			   	    <s:iterator value="#attr.couponGroup.couponList" var="v" status="l">
			   	    <tr align="center">
			   			<td>${v.couponName}</td>
			   			<td>${v.couponValue}</td>
			   			<td>${v.couponCon}</td>
			   			<td>${v.num}</td>
			   			<td>
			   			<s:if test="%{null == #v.delayDays || '' == #v.delayDays}">
		            	<s:property value="#v.effectiveTime"/> 至 <s:property value="#v.expirationTime"/>
		            	</s:if>
		            	<s:else>
		            	券到账后有效期<s:property value="#v.delayDays"/>天
		            	</s:else>
			   			</td>
<%--			   			<td>${v.couponCount}</td>--%>
			   		</tr>
			   	    </s:iterator>
			   	</table>
			   	</li>
			   	<li class="clearfix">
			   	<span class="w120 t-r fl">发放规则：</span>
			   	<table class="table1" id="target" width="100%" style="margin-left: 120px; border:1px solid #cddfec"> 
			   	    <tr align="center"><th>规则名称</th><th>条件</th><th>规则内容</th></tr>
			   	    <s:if test="null==#attr.couponRuleList || #attr.couponRuleList.size == 0">
                    <tr align="center" class="bg1">
      					<td colspan="6"><p class="f13 f-yahei gray4 t-c line26 mt10">
      					<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span></p></td>
      				</tr>
     		        </s:if>
			   	    <s:iterator value="#attr.couponRuleList" var="v" status="l">
			   	    <tr align="center">
			   			<s:if test="#v.ruleName == 'cityCode'"><td>城市</s:if>
			   			<s:elseif test="#v.ruleName == 'lineBaseId'"><td>线路</s:elseif>
			   			<s:elseif test="#v.ruleName == 'telephone'"><td class="telephone">手机号</s:elseif>
			   			<s:elseif test="#v.ruleName == 'registTime'"><td>注册时间</s:elseif>
			   			<s:elseif test="#v.ruleName == 'ticketNumber'"><td>购票次数</s:elseif>
			   			<s:elseif test="#v.ruleName == 'terminal'"><td>设备类型</s:elseif>
			   			<s:elseif test="#v.ruleName == 'sourcefrom'"><td>用户来源</s:elseif>
			   			<s:elseif test="#v.ruleName == 'sex'"><td>性别</s:elseif>
			   			<s:elseif test="#v.ruleName == 'recommendNumber'"><td>推荐人数</s:elseif>
			   			<s:elseif test="#v.ruleName == 'isRecommended'"><td>是否被推荐</s:elseif>
			   			<s:elseif test="#v.ruleName == 'isDoRecommend'"><td>是否推荐</s:elseif>
			   			<s:elseif test="#v.ruleName == 'recommendTime'"><td>推荐时间</s:elseif>
			   			<s:elseif test="#v.ruleName == 'recommendedTime'"><td>被推荐时间</s:elseif>
			   			<s:elseif test="#v.ruleName == 'ticketTime'"><td>购票时间</s:elseif>
			   			<s:elseif test="#v.ruleName == 'singleHandle'"><td>第三方操作</s:elseif>
			   			<s:elseif test="#v.ruleName == 'timingHandle'"><td>第三方定时</s:elseif>
			   			</td>
			   			<td>
			   			<s:if test="#v.ruleName == 'registTime' || #v.ruleName == 'recommendTime' || #v.ruleName == 'recommendedTime'">
			   			<s:if test="#v.theCondition == 'gt'">后于</s:if>
			   			<s:elseif test="#v.theCondition == 'equal'">等于</s:elseif>
			   			<s:elseif test="#v.theCondition == 'lt'">前于</s:elseif>
			   			</s:if>
			   			<s:else>
			   			<s:if test="#v.theCondition == 'gt'">大于</s:if>
			   			<s:elseif test="#v.theCondition == 'equal'">等于</s:elseif>
			   			<s:elseif test="#v.theCondition == 'notEq'">不等于</s:elseif>
			   			<s:elseif test="#v.theCondition == 'lt'">小于</s:elseif>
			   			</s:else>
			   			</td>
			   			<td>${v.theValue}</td>
			   		</tr>
			   	    </s:iterator>
			   	</table>
			   	</li>
<%--			   	<li class="clearfix"><span class="w120 t-r fl">发放方式：</span>--%>
<%--			   	<s:if test="#attr.couponGroupGrant.sendCouponType == 'user get'">用户领取</s:if>--%>
<%--			   	<s:else>系统发放</s:else>--%>
<%--			   	</li>--%>
<%--			   	<li class="clearfix"><span class="w120 t-r fl">可领优惠券时间范围：</span>${couponGroupGrant.startTime} 至 ${couponGroupGrant.endTime}</li>--%>
                
			   	<li class="clearfix">
			   	<span class="w120 t-r fl">用户优惠券详情：</span>
			   	<table class="table1" id="target" width="100%" style="margin-left: 120px; border:1px solid #cddfec"> 
			   	    <tr><td colspan="6">
			   	    <s:form id="searchform" action="../couponGroupGrant/toCouponGroupGrantDetailPage.action" theme="simple">
			   	  <span class="fl">&nbsp;&nbsp;手机号码：</span>
			   	    <s:textfield id="telephone" name="search.field01" cssClass="r-input fl w16p" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"></s:textfield>
			   	  <span class="fl">&nbsp;&nbsp;发放时段：</span>
			   		<span class="r-input fl w16p mr10"><input type="text" name="search.field03" value="${search.field03 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd'})"/></span>
		            <span class="fl">至</span>
		            <span class="r-input fl w16p ml10"><input type="text" name="search.field04" value="${search.field04 }" class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
		            &nbsp;&nbsp;
		            <input type="submit" class="btn-blue4" value="查找" />
<!-- 		            <input type="button" class="btn-blue4" value="查找" onclick="searchCouponDetail()"/> -->
			   	    </s:form>
			   	    </td></tr>
			   	    <tr align="center"><th>用户帐号</th>
<%--			   	    <th>批次号</th>--%>
			   	    <th>有效期</th><th>获取时间</th><th>使用状态</th><th>订单ID</th><th>使用时间</th></tr>
			   	    <s:if test="null==list || list.size == 0">
                    <tr align="center" class="bg1">
      					<td colspan="6"><p class="f13 f-yahei gray4 t-c line26 mt10">
      					<span class="index-nodate mr10"></span><span id="noDate">
      					<s:if test="#attr.couponGroupGrant.modeOfExecution == 'unsend'">
      					暂未发放
      					</s:if>
      					<s:else>
      					暂无数据
      					</s:else>
      					</span></p></td>
      				</tr>
     		        </s:if>
			   	    <s:iterator value="list" var="v" status="l">
			   	    <tr align="center">
			   			<td>${v.telephone}</td>
<%--			   			<td>${v.couponGroupId}</td>--%>
			   			<td>${v.effectiveTime}至${v.expirationTime}</td>
			   			<td>${v.getTime}</td>
			   			<td>
			   			<s:if test="#v.useState == 'unused'">未使用</s:if>
			   			<s:elseif test="#v.useState == 'used'">已使用</s:elseif>
			   			<s:elseif test="#v.useState == 'expired'">已过期</s:elseif>
			   			</td>
			   			<td>${v.orderId}</td>
			   			<td>${v.useTime}</td>
			   		</tr>
			   	    </s:iterator>
			   	</table>
			   	<!--Start page-->
				<s:if test="%{page.pageCount != 0}"><jsp:include page="../page.jsp"></jsp:include></s:if>
				<!--End page-->
				<%
				  CouponGroupGrant cgg=(CouponGroupGrant)request.getAttribute("couponGroupGrant");
				  session.setAttribute("detailGrantId",cgg.getId());
				%>
			   	</li>
		   </ul>
		        <%--<s:if test="#attr.couponGroupGrant.modeOfExecution == 'unsend'">
            	<a href="javascript:void(0);" class="blue1" onclick="doCouponGrant(${couponGroupGrant.id})">开始发放</a>
            	<span class="gray1">停止发放</span>
            	</s:if>
            	<s:elseif test="#attr.couponGroupGrant.modeOfExecution == 'send'">
            	<span class="gray1">开始发放</span>
            	<a href="javascript:void(0);" class="blue1" onclick="closeCoupon(${couponGroupGrant.id});">停止发放</a>
            	</s:elseif>
            	<s:else>
            	<span class="gray1">开始发放&nbsp;停止发放</span>
            	</s:else>--%>
	          <input value="返回" class="btn1 white" onclick="javascript:window.location.href='../couponGroupGrant/couponGroupGrantList.action'"/>
<%--	          <input value="返回" class="btn1 white" onclick="javascript:history.go(-1)"/>--%>
	       
	   </div>
   </div>
</s:form>

<form method="post" style="display: none;" id="turnPage">
   	<input type="text" name="grantId" id="grantId" value="${couponGroupGrant.id}"/>
   	<input type="text" name="myPageIndex" id="currentPageIndex" value="${currentPageIndex}"/>
   	<input type="text" name="search.field01" value="${search.field01}"/>
    <input type="text" name="search.field03" value="${search.field03}"/>
    <input type="text" name="search.field04" value="${search.field04}"/>
</form>
</body>
<script>
$(function(){
	$(".sh-add-new").css("min-height",$(window).height()-130+"px");
	$(window).resize(function(){
		$(".sh-add-new").css("min-height",$(window).height()-130+"px");	
	})

	$('.telephone').each(function(){
		var $h=$(this).next().next().html().split(';');
		var $s='';
		for(var i=0;i<$h.length;i++){
			if($s==''){
				$s=$h[i];
			}
			else{
				if($s.substring($s.length-1,$s.length)=='>'){
					$s=$s+$h[i];
				}
				else{
					$s=$s+';'+$h[i];
				}
			}
			if((i+1)%7==0){
				$s=$s+'<br>';
			}
		}
		$(this).next().next().html($s);
	});
})

//优惠券发放
function doCouponGrant(grantId)
{
	parent.parent.showPopCommonPage("确认发放该优惠券吗？");
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
	            	 $("#turnPage").attr("action","../couponGroupGrant/toCouponGroupGrantDetailPage.action");
	            	 //刷新当前页信息
		             $("#turnPage").submit(); 
	              }
              	  if(data==-1){
              		parent.parent.showRturnTip("发放失败",false);
                  }
              	  if(data==-2){
              		parent.parent.showRturnTip("暂无符合过滤条件的用户",false);
              		 //刷新本页
	            	 $("#turnPage").attr("action","../couponGroupGrant/toCouponGroupGrantDetailPage.action");
	            	 //刷新当前页信息
		             $("#turnPage").submit(); 
                  }
              	 if(data==-3){
               		parent.parent.showRturnTip("用户对该优惠券已无领取机会",false);
               		 //刷新本页
 	            	 $("#turnPage").attr("action","../couponGroupGrant/toCouponGroupGrantDetailPage.action");
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
	parent.parent.showPopCommonPage("确定要关闭该优惠券发放吗？");
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
	            	 $("#turnPage").attr("action","../couponGroupGrant/toCouponGroupGrantDetailPage.action");
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

function searchCouponDetail()
{
    $("#iframe_right", parent.parent.window.document).attr("src","../couponGroupGrant/toCouponGroupGrantDetailPage.action?telephone="+$("#telephone").val());
}

</script>
</html>
