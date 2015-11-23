<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<script src="../js/jquery/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="../js/line/common_util.js" type="text/javascript"></script>
</head>
<body>
开始批量拆分线路<br/>
正在拆分...请等待...
<div id="count"></div>
<div id="result"></div>
</body>
</html>
<script type="text/javascript">
var count = 0;
var tmid;
$(document).ready(function(){
	tmid = window.setInterval(countHandle,1000);
	leaseGetAjax("../batch/splitAllLineStation.action","text",function(data){
		if(data=="success"){
			$("#result").html("处理成功！");
		}else{
			$("#result").html(data);
		}
		window.clearInterval(tmid);
	});
});

function countHandle(){
	count = count + 1;
	$("#count").html(count);
}
</script>