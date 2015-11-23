<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>第三方财务对比页面</title>
    <jsp:include page="../resource.jsp"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
  </head>
  <body>
  	<div class="clearfix line24" style="margin:20px auto auto 20px;">
  		<span class="fl">时间：</span>
               <span class="r-input fl w120 mr10"><input type="text"  class="Wdate75 gray2 Wdate" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'txtEndDate\');}'})"/></span>
            <span class="fl">至</span>
            <span class="r-input fl w120 ml10"><input type="text"  class="Wdate75 gray2 Wdate" readonly="readonly" id="txtEndDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'txtStartDate\');}'})"/></span>
            <span><a href="javasctipt:void(0)" onclick="cleanDate();">清空数据</a></span>
  	</div>
  	<div style="margin:20px auto auto 20px;">
  			<div class="clearfix" style="line-height:31px;"><span class="fl w100 t-r">上传财付通账单：</span>
  			  	<div class="p-r fl" style="width:101px;height:31px;overflow:hidden;">
  			  		<a href="javascript:void(0)" class="display-ib btn1 white">选择文件</a>
  					<input type="file" name="excel" id="file0" onchange="upLoadExcel('file0',0);" class="p-a" style="left:0;top:0;opacity:0;filter:alpha(opacity=0);width:101px;height:31px;" />
  				</div>
  				<a href="javascript:void(0);" class="blue1 ml10 hide fl" onclick="toExport(0);" id="a_0">下载财付通结果</a>
  			</div>
  			<div class="clearfix mt10" style="line-height:31px;">
  				<span class="fl w100 t-r">上传微信账单：</span>
  				<div class="p-r fl" style="width:101px;height:31px;overflow:hidden;">
  			  		<a href="javascript:void(0)" class="display-ib btn1 white">选择文件</a>
  					<input type="file" name="excel" id="file1" onchange="upLoadExcel('file1',1);" class="p-a" style="left:0;top:0;opacity:0;filter:alpha(opacity=0);width:101px;height:31px;" />
  				</div>
  				<a href="javascript:void(0);" class="blue1 ml10 hide" onclick="toExport(1);" id="a_1">下载微信结果</a>
  			</div>
  			<div class="clearfix mt10" style="line-height:31px;">
  				<span class="fl w100 t-r">上传银联账单：</span>
  				<div class="p-r fl" style="width:101px;height:31px;overflow:hidden;">
  			  		<a href="javascript:void(0)" class="display-ib btn1 white">选择文件</a>
  					<input type="file" name="excel" id="file2" onchange="upLoadExcel('file2',2);"  class="p-a" style="left:0;top:0;opacity:0;filter:alpha(opacity=0);width:101px;height:31px;" />
  				</div>
  				<a href="javascript:void(0);" class="blue1 ml10 hide" onclick="toExport(2);" id="a_2">下载银联结果</a>
  			</div>
  			<div class="clearfix mt10" style="line-height:31px;">
  				<span class="fl w100 t-r">上传支付宝账单：</span>
  				<div class="p-r fl" style="width:101px;height:31px;overflow:hidden;">
  			  		<a href="javascript:void(0)" class="display-ib btn1 white">选择文件</a>
  					<input type="file" name="excel" id="file3" onchange="upLoadExcel('file3',3);"  class="p-a" style="left:0;top:0;opacity:0;filter:alpha(opacity=0);width:101px;height:31px;"/>
  				</div>
  				<a href="javascript:void(0);" class="blue1 ml10 hide" onclick="toExport(3);" id="a_3">下载支付宝结果</a>
  			</div>
  			
  	</div>
  	
  	<form action="../compareOther/exportExcel.action" method="post" style="display: none;" id="export">
   	<input type="text" name="search.field01" id="field01"/>
    <input type="text" name="search.field02" id="field02"/>
    <input type="text" name="search.field03" id="field03"/>
 </form> 
  </body>
  <script type="text/javascript">
  function upLoadExcel(id,type){
	  $.ajaxFileUpload({
			fileElementId:id, 
			secureuri:false,
			dataType: 'text',
			url : '../compareOther/importExcel.action?type='+type,
			success : function(data) {
			   if("success"==data){
				   parent.showRturnTip("上传成功!",true); 
				   $("#a_"+type).show();
				}else if(0==data){
					parent.showRturnTip("格式不正确!",false); 
				}
			},
			error:function(){
				parent.showRturnTip("上传失败!",false); 
			}
   });
}

//导出
  function toExport(type){
	var startTime = $("#txtStartDate").val();
	var endTime = $("#txtEndDate").val();
	if(''==startTime || ''==endTime){
		parent.parent.showCommonTip("请填写时间");
		return;
	}
	$("#field01").val(type);
	$("#field02").val(startTime);
	$("#field03").val(endTime);
	$("#export").submit();
 }

  //清空数据
  function cleanDate(){
	  $.ajax({
			url:'../compareOther/deleteDate.action?&random='+Math.floor(Math.random()*10000+1),
			type:'post',
			cache:false,	
			dataType:"text",	
			success:function(data){
			    if(data=="success"){
			       parent.parent.showRturnTip("数据清空成功!",true);
			    }else{
			       parent.parent.showRturnTip("数据清空失败!",false);
			    }	
			},
			error:function(){
				//parent.parent.showRturnTip("数据清空失败!",false);
			}
		}); 
	}
  
  </script>
</html>
