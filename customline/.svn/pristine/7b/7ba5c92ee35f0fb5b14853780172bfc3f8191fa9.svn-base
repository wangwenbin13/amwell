<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
</head>
<body>
<!--Start page-->
 					<s:url id="preurl" value=""><s:param name="p"><s:property value="page.previousIndex" /></s:param></s:url>
					<s:url id="nexturl" value=""><s:param name="p"><s:property value="page.nextIndex" /></s:param></s:url>
					<s:url id="firsturl" value=""><s:param name="p"><s:property value="0" /></s:param></s:url>
					<s:url id="lasturl" value=""><s:param name="p"><s:property value="page.lastIndex" /></s:param></s:url>
					<s:url id="tourl" value=""><s:param name="p"><s:property value="page.currentPage" /></s:param></s:url>         	 
                  <div class="page t-c mt20  fl widthfull" id="pageDiv">
	           		<s:a href="%{firsturl}">首页</s:a>
	           		<s:if test="page.currentPage gt 1">
						<s:a href="%{preurl}">上一页</s:a>
	           		</s:if>
					<s:iterator value="page.pageList">
						<s:if test="field02==1">
							<b><span class="current"><s:property value="field01" />
							</span>
							</b>
						</s:if>
						<s:else>
							<a href="?p=<s:property value="field03"/>"><s:property value="field01" /></a>
						</s:else>
					</s:iterator>
					<s:if test="page.currentPage lt page.pageCount">
						<s:a href="%{nexturl}">下一页</s:a>
					</s:if>
					<s:a href="%{lasturl}">末页</s:a>
					&nbsp;[&nbsp;
					<font color="red"><s:property value="page.currentPage" /></font>/<font color="red"><s:property value="page.pageCount" /></font>
					&nbsp;共&nbsp;
					<font color="red"><s:property value="page.totalCount" />
					</font>&nbsp;条记录]
                 </div>
<!--End page-->
</body>
</html>
