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
	
  </head>
  	 <div class="tableGO mt10">
	    	<table width="100%" border="0" class="table1 tableG">
		    	<tr>
		    		<td width="15%">优惠券发行总量：${couponStatVo.couponTotal}</td>
		    		<td width="15%">已使用：${couponStatVo.couponUser }</td>
		    		<td width="15%">未使用：${couponStatVo.couponUnUser }</td>
		    	</tr>
		    	<tr>
		    		<td>优惠券发行总金额：${couponStatVo.couponTotalMon }</td>
		    		<td>已使用：${couponStatVo.couponUserMon }</td>
		    		<td>未使用：${couponStatVo.couponUnUserMon }</td>
		    	</tr>
		    	<tr>
		    		<td width="15%">优惠券已领总量：${couponStatVo.couponGetTotal}</td>
		    		<td width="15%">已使用：${couponStatVo.couponGetUser }</td>
		    		<td width="15%">未使用：${couponStatVo.couponGetUnUser }</td>
		    	</tr>
		    	<tr>
		    		<td>优惠券已领总金额：${couponStatVo.couponGetMon }</td>
		    		<td>已使用：${couponStatVo.couponGetUserMon }</td>
		    		<td>未使用：${couponStatVo.couponGetUnUserMon }</td>
		    	</tr>
		    </table>
	    </div>
  <body>
  </body>
  </html>