<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <title>My JSP 'dateTable.jsp' starting page</title>
  </head>
  <body>
    <p class="fw black1">线路月度收入统计表</p>
     	<table class="table1 mt20" border="0" width="100%">
			<tr class="th">
				<th scope="col" width="40%">线路</th>
	            <th scope="col" width="60%" align="left">收入（元）</th>
	         </tr>
			<s:if test="%{lineListSize==0}">
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
								<!--<p class="gray3 mt15">没有查询结果噢，你可以重新设置条件进行搜索,或者返回列表。 <a href="javascript:void(0)" class="red1" onclick="toBackList();">返回</a> 没有查询结果的提示 </p>	-->
							</s:if>
						</div>				
					</th>
				</tr>
				<!-- 没有数据 end -->
			</s:if>
			<s:iterator value="lineStatTotalEntities" var="lineStatTotalEntity" status="s">
				<tr>
					<td align="center">${lineStatTotalEntity.lineName}</td>
					<td align="left"><em class="fw yellow4">${lineStatTotalEntity.consumeLimit}</em></td>
				</tr>
			</s:iterator>
			<s:if test="%{linePage.pageCount!=0}">
				<tr>
		         	<td colspan="2">
		         		 <!--Start page-->
	                  	<div class="page ml15 pb9 clearfix">
		                   <s:if test="%{lineCurrentPageIndex!=0}">
	                     		<a href="javascript:void(0);" class="mt5" onclick="toLinePage(${linePage.previousIndex});">上一页</a>
		                   </s:if>
	                       <s:if test="%{linePage.pageCount!=0 && ((lineCurrentPageIndex/pageSize)+1)!=linePage.pageCount}">
	                       		<a href="javascript:void(0);" class="mt5" onclick="toLinePage(${linePage.nextIndex});">下一页</a>
	                       </s:if>
	                       <span class="ml25 fl mt7">[共<span class="fw"><s:property value="linePage.pageCount" /></span>页]</span>
	                 	</div>
	                  	<!--End page-->
		         	</td>
		         </tr>
	         </s:if>
	     </table>
<form action="../statMonthIncomeAction/exportLineTable.action" method="post" style="display: none;" id="exportLine">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
   	<input type="text" name="search.field02" value="${search.field02 }"/>
</form>
  </body>
</html>
<script type="text/javascript">
	//翻页方法
function toLinePage(value){
	loadStatLine(value);
}

//判断输入的参数是否符合规定
function toJudgeLinePage(value){
	var totalPage = "${linePage.pageCount}";
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
	toLinePage((value-1)*pageSize);
}

//导出
function toExportline(){
	var listSize = "${lineListSize}";
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#exportLine").submit();
}
</script>
