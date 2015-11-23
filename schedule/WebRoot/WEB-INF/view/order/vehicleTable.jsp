<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  </head>
  
  <body>
  	<p class="fw black1">车辆月度收入统计表</p>
     	<table class="table1 mt20" border="0" width="100%">
			<tr class="th">
				<th scope="col" width="40%">车辆</th>
	            <th scope="col" width="60%" align="left">收入（元）</th>
	         </tr>
			<s:if test="%{businessListSize==0}">
				 <!-- 没有数据 start -->	
				<tr class="noDateList">
					<th colspan="2">				
						<div class="t-c mt35 mb40">
							<s:if test="%{searchOrNo==0}">
								<img src="../images/noDate.png" width="169" height="169" alt="暂无数据" /><!-- 默认没有线路 -->
								<p class="mt15 f18 f-yahei">暂无数据</p>
							</s:if>
							<s:if test="%{searchOrNo==1}">
								<img src="../images/noSearchDate.png" width="169" height="169" alt="暂无数据" /><!-- 没有查询结果 -->
								<p class="mt15 f18 f-yahei">暂无数据</p>	
							</s:if>
						</div>				
					</th>
				</tr>
				<!-- 没有数据 end -->
			</s:if>
			<s:iterator value="businessStatTotalEntities" var="businessStatTotalEntity" status="s">
				<tr>
					<!-- 车辆类型 0:大巴 1:中巴 2:小巴 -->
					<td align="center">${businessStatTotalEntity.vehicleNumber}/${businessStatTotalEntity.vehicleBrand}</td>
					<td align="left"><em class="fw yellow4">${businessStatTotalEntity.consumeLimit}</em></td>
				</tr>
			</s:iterator>
			 <s:if test="%{businessPage.pageCount!=0}">
				<tr>
		         	<td colspan="2">
		         		 <!--Start page-->
	                  	<div class="page ml15 pb9 clearfix">
		                   <s:if test="%{businessCurrentPageIndex!=0}">
	                     		<a href="javascript:void(0);" class="mt5" onclick="toBusinessPage(${businessPage.previousIndex});">上一页</a>
		                   </s:if>
	                       <s:if test="%{businessPage.pageCount!=0 && ((businessCurrentPageIndex/pageSize)+1)!=businessPage.pageCount}">
	                       		<a href="javascript:void(0);" class="mt5" onclick="toBusinessPage(${businessPage.nextIndex});">下一页</a>
	                       </s:if>
	                       	<span class="ml25 fl mt7">[共<span class="fw"><s:property value="businessPage.pageCount" /></span>页]</span>
	                 	</div>
	                  	<!--End page-->
		         	</td>
		         </tr>
	         </s:if>
	     </table>
<form action="../statMonthIncomeAction/exportBusinessTable.action" method="post" style="display: none;" id="exportBusiness">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
   	<input type="text" name="search.field02" value="${search.field02 }"/>
</form>
  </body>
</html>
<script type="text/javascript">
	//翻页方法
function toBusinessPage(value){
	loadStatBusiness(value);
}

//判断输入的参数是否符合规定
function toJudgeBusinessPage(value){
	var totalPage = "${businessPage.pageCount}";
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
	toBusinessPage((value-1)*pageSize);
}

//导出
function toExportBusiness(){
	var listSize = "${businessListSize}";
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#exportBusiness").submit();
}
</script>