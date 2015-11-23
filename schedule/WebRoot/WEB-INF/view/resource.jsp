<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath() + "/";
%>
<!-- 公共资源CSS,JS  -->
<!--Css -->
<!-- 收藏图标，浏览器图标 -->
<link rel="Shortcut Icon" href="<%=path %>images/favicon.ico"  type="image/x-icon" />
<link href="<%=path %>css/default.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>css/main.css" rel="stylesheet" type="text/css" />

<!-- ** jquery Javascript ** -->
<script src="<%=path %>js/jquery/jquery-1.8.2.min.js" type="text/javascript"></script>
<!-- ** jquery 解决window.onresize在ie上刷新多次，导致浏览器死机问题 ** -->
<script src="<%=path %>js/jquery/jquery.wresize.js" type="text/javascript"></script>
<!-- ** 时间控件  Javascript ** -->
<script src="<%=path %>js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<!-- ** 验证Javascript ** -->
<script type="text/javascript" src="<%=path %>js/jquery/jquery-validator.js"></script>
<script type="text/javascript" src="<%=path %>js/config/validator-config.js"></script>
<script src="<%=path %>js/jquery/jquery.form.js" type="text/javascript"></script>

