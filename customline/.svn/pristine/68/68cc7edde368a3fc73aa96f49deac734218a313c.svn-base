<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>marketing passengerList</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">


  </head>
  
  <body>
  
  
  <div class="dj-box p-r" style="margin-left: 72px;width:540px;background:#fff"  id="marketingPassengerList">
			            <span style="left:6px;top:-10px" class="sb-arrow p-a"></span>	   
			               
						    	<p class="mt10 clearfix">
			                    	<span class="fl" >手机号码：</span>
			                        <span class="fl w25p mr10">
			                            <s:textfield type="text" cssClass="fl r-input w98p mr10" name="search.field01" id="telephone" />
			                        </span>
				                    <a class="search-btn1 fl mt2 ml5" href="javascript:void(0)" onclick="toPage(0)"></a>
				                </p>
					    	
				         <!-- 乘客列表-->
					        <div id="target">
						        <p class="bt-bot-line f14 blue2 mt10"><span class="bt-ico fl mr5"></span>乘客列表</p>
						        <div class="table2-text sh-ttext clearfix" >
							        <table width="100%" border="0" class="table1 t-line30" id="passengerList">
								        <tr>
									        <th scope="col" width="5%"></th>
									        <th scope="col" width="35%">乘客昵称</th>
									        <th scope="col" width="35%">手机号码</th>
									        <th scope="col" width="35%">操作</th>
								        </tr>

								     
							        </table>
						   		 </div>
						   		 	 
				                  <p class="t-c mt20 mb15">
							          <a class="btn mr8 mt4" href="javascript:void(0)">取消</a>
							          <a class="btn mr4 mt4" href="javascript:void(0)" onclick="getTelephone()">确定</a>
					             </p>
					    	</div> 
		        		</div>
		        		
  <form action="../marketing/passengerList.action" method="post" style="display: none;" id="turnPage">
   	<input type="text" name="search.field01" value="${search.field01 }"/>

    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
  </form>
    
  </body>
</html>
<script type="text/javascript">
//翻页方法
function toPage(value){
    var telephone = $("#telephone").val();
    var url = "marketing/passengerList.action?telephone="+telephone+"&currentPageIndex="+value;

	$("#passengerList").append('<tr class="bg1" align="center" ><td>张三</td><td class="f-arial">123456789</td><td><a href="javascript:void(0);" class="display-ib btn-gray">删除</a></td>');
	//追加乘客数据
	//addPassengerInfo();
}

//判断输入的参数是否符合规定
function toJudgePage(value){
	var totalPage = "${page.pageCount}";
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
	toPage((value-1)*pageSize);
}

function queryPassenger(){
	var telephone=$("#telephone").val();
	var value=null;
	loadPassengerList(value,telephone);
	
}


</script>
