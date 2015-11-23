<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>运营平台－创建线路-完成</title>   
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit" /> 
	<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
	<jsp:include page="../resource.jsp"/>

  </head>
  
  <body>
    <div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页 &gt; 发布专线 <span class="blue1 ml25"></span></p></div>
    <div class="steps">
    	<ol class="clearfix">
			<li><i>1</i><span class="tsl">填写基本信息</span></li>
			<li><i>2</i><span class="tsl">填写班次</span></li>
			<li class="active"><i>3</i><span class="tsl">创建完成</span></li>
		</ol>		
    </div>
    <div class="ml30 gray2 mt10 clearfix">
    	<p class="line24"><span class="fw f14 w65 fl t-l">价格信息：</span>单次价格<em class="green1 fw f14">5</em>元/次</p>
   			<div class="table2-text sh-ttext table2-text-noshadow fl w920 mt10">
                <table width="100%" border="0" class="table4 f12 gray2">
                  <tbody><tr align="center" class="trbg1">
                    <td width="115"  class="fw">年</td>
                    <td width="765" height="30" class="fw" colspan="3">2014</td>
                  </tr>
                  <tr align="center" class="trbg2">
                    <td>月份</td>
                    <td width="29%">9</td>
                    <td width="29%">10</td>
                    <td width="29%">11</td>                  
                  </tr>
                  <tr align="center" height="32">
                    <td>工作日(天)</td>
                    <td class="green1">9</td>
                    <td class="green1">10</td>
                    <td class="green1">11</td>                  
                  </tr>
                  <tr align="center" height="32">
                    <td>价格(元)</td>
                    <td class="red1">9</td>
                    <td class="red1">10</td>
                    <td class="red1">11</td>                  
                  </tr>
                  <tr align="center" height="32">
                    <td>折扣</td>
                    <td align="left" colspan="3"><em class="red1 fw ml10"></em>折</td>                                 
                  </tr>
                  <tr align="center" height="32">
                    <td>折后价(元)</td>
                    <td class="green1">9</td>
                    <td class="green1">10</td>
                    <td class="green1">11</td>                  
                  </tr>
             </tbody>
            </table>
          </div>
   		</div>
    <div class="mt80 w1015">
    	<p class="t-c"><span class="display-ib systips-ico systips-ico-ok mr5 vf6"></span><span class="f24">创建线路完成！</span><a href="javascript:void(0)" onclick='parent.openTowMenu(0,1)' class="blue1 ml20 mr20">返回线路列表</a><a class="display-ib btn1 white f14" href="line/forwardAddLinePage.action">继续创建</a></p>
    </div>
  </body>
</html>


