<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'dateTable.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <p class="bt-bot-line f-yahei f13 fw blue2 mt4 h24"><span class="bt-ico fl mr5 ml4"></span>按日期月度账目统计报表<a href="javascript:void(0);" class="blue1 ml10" onclick="toExportDate();">下载搜索结果</a></p>
            <table width="100%" border="0" class="table1 bb-table">
              <tr align="center">
                <th scope="col" width="50%">日期</th>
                <th scope="col" width="50%">收入（元）</th>
              </tr>
              <s:iterator value="dateStatTotalEntities" var="dataStatTotalEntity" status="s">
			             <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
                    		<td class="f-arial">${dataStatTotalEntity.orderDate}</td>
                    		<td class="f-arial">${dataStatTotalEntity.consumeLimit}</td>
                    	</tr>
                   	</s:iterator>
              <tr align="center" class="trbg4 fw">
                <td align="center" colspan="2">合计（元）：<em class="yellow1 fw">￥${dateTotalMoney }</em></td>
              </tr>
            </table>
           <!--Start page-->
                  <div class="page t-c mt20  fl widthfull" id="pageDiv">
	                   <s:if test="%{dateCurrentPageIndex!=0}">
	                  		<a href="javascript:void(0);" onclick="toDatePage(0);">首页</a>
                     		<a href="javascript:void(0);" onclick="toDatePage(${datePage.previousIndex});">上一页</a>
	                   </s:if>
                       <ul id="pageNumDiv">
                       		<s:iterator value="datePage.pageList">
							<s:if test="field02==1">
								<b><span class="current"><s:property value="field01" />
								</span>
								</b>
							</s:if>
							<s:else>
								<li>
									<a href="javascript:void(0);" onclick="toDatePage(${field03});"><s:property value="field01" /></a>
								</li>
							</s:else>
						</s:iterator>
                       </ul>
                       <s:if test="%{datePage.pageCount!=0 && ((dateCurrentPageIndex/pageSize)+1)!=datePage.pageCount}">
                       		<a href="javascript:void(0);" onclick="toDatePage(${datePage.nextIndex});">下一页</a>
                       		<a href="javascript:void(0);" onclick="toDatePage(${datePage.lastIndex});">末页</a>
                       </s:if>
                       <s:if test="%{datePage.pageCount!=1 && datePage.pageCount!=0}">
                       		<span class="ml10">跳转到第<input type="text" class="page-input" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');" onblur="toJudgeDatePage(this.value);"/>页</span>
                       </s:if>
                       <span class="ml25">[共<span class="fw"><s:property value="datePage.pageCount" /></span>页]</span>
                 </div>
                  <!--End page-->
<form action="../monthIncomeAction/exportDateTable.action" method="post" style="display: none;" id="exportDate">
   	<input type="text" name="search.field01" value="${search.field01 }"/>
</form>
  </body>
</html>
<script type="text/javascript">
	//翻页方法
function toDatePage(value){
	loadStatDate(value);
}

//判断输入的参数是否符合规定
function toJudgeDatePage(value){
	var totalPage = "${datePage.pageCount}";
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
	toDatePage((value-1)*pageSize);
}

//导出
function toExportDate(){
	var listSize = "${dateListSize}";
	if(0==listSize){
		parent.parent.showCommonTip("没有需要导出的数据!");
		return;
	}
	$("#exportDate").submit();
}
</script>