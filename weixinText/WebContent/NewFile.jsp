<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<input value="2015-11-09 08:22:30" name="aaa" type="hidden" />
	<span id="ms_0" class="showTime"></span>
	<input value="2015-11-09 08:22:35" name="aaa" type="hidden" />
	<span id="ms_1" class="showTime"></span>
</body>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	
	var values = document.getElementsByName("aaa");
	var className = document.getElementsByClassName("showTime");
	console.info(className[0]);
	for(var i=0;i<values.length;i++){
		$("#ms_"+i).text(values[i].value);
	}
</script>
</html>