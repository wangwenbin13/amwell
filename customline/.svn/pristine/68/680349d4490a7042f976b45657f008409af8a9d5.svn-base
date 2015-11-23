<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线路管理-线路列表-所有定制线路</title>
<jsp:include page="../resource.jsp"/>
</head>

<body>
      <div class="table2-text sh-ttext mt10">       
              <table width="100%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="4%">序号</th>
                      <th scope="col" width="18%">线路名称</th>
                      <th scope="col" width="20%">起点/终点</th>
                      <th scope="col" width="10%">发布状态</th>
                      <th scope="col" width="10%">是否编辑</th>
                      <th scope="col" width="10">修改时间</th>
                      <th scope="col" width="8%">修改人</th>
                      <th scope="col" width="8%">编辑</th>
                    </tr>
                    <s:iterator value="list" var="lineBusiness" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
						<th>${s.index+1}</th>
                    	<td class="f-arial"><s:property value="lineName"/></td>
						<td class="f-arial"><s:property value="fromStation"/>—<s:property value="toStation"/></td>
                    	
                    	<td class="f-arial">
                    	   <!--0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除-->
                    	   <s:if test="lineStatus==0"><em class="red1">创建中</em></s:if>
                    	   <s:if test="lineStatus==1"><em class="green1">调度中</em></s:if>
                    	   <s:if test="lineStatus==2"><em class="red1">待发布</em></s:if>
                    	   <s:if test="lineStatus==3"><em class="green1">已发布</em></s:if>
                    	   <s:if test="lineStatus==4"><em class="gray1">已下线</em></s:if>
                    	   <s:if test="lineStatus==5"><em class="gray1">已删除</em></s:if>
                    	</td>
                    	<td class="f-arial"><s:if test="remark==null"><font color="red">未修改</font></s:if><s:else><font color="green">已修改</font></s:else></td>
                    	<td class="f-arial"><s:property value="updateOn"/></td>
                    	<td class="f-arial"><s:property value="updateBy"/></td>
                    	<td class="f-arial"><a href="../line/updateLineInfo.action?id=<s:property value="lineBaseId"/>">编辑</a></td>
                    	</tr>
					</s:iterator>
                  </table>
          </div>
</body>
</html>
