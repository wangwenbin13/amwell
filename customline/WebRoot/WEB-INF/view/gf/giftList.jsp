<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder" %>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>礼品配置</title>
<%@include file="../resource.jsp" %>
</head>
  
 <body>
    <div id="mainBody" class="mainBody"></div>
	<div id="showPage" class="showPage"></div>
	<div class="r-sub-nav-out"><p class="r-sub-nav gray2">当前位置：首页&nbsp;>&nbsp;营销管理&nbsp;>&nbsp;礼品配置</p></div>  
	<div class="mt10 ml20 mr20 black1">
	   <div class="table-title">
	        <ul>
	        	<li class="on"><a href="javascript:void(0)" class="f14 blue2">礼品配置</a></li>
	        </ul>
	    </div>
	    <div class="table-outline mt10">
	         <div class="table-title">	        	 
	         	<a href="javascript:void(0)" onclick="showAddPage();" class="btn fr mr8 mt4">增加</a>
	         </div>
	         <div class="table2-text sh-ttext clearfix">
           	 	<div style="overflow-x:auto;overflow-y:hidden">
                   <table width="100%" border="0" class="table1" id="tableDiv">
                        <tr align="center">
	                      <th scope="col" width="10%">礼品ID</th>
	                      <th scope="col">名称</th>
	                      <th scope="col" width="10%">类型</th>
	                      <th scope="col" width="10%">面值</th>
	                      <th scope="col" width="15%">约束条件</th>
	                      <th scope="col" width="10%">配置时间</th>
	                      <th scope="col" width="10%">配置人</th>
	                      <th scope="col" width="10%">使用状态</th>
	                      <th scope="col" width="10%">操作</th>
	                    </tr>
	                    <s:if test="null==list || list.size==0">
							<tr align="center" class="bg1">
								<td colspan="12">
									<p	class="f13 f-yahei gray4 t-c line26 mt10">
										<span class="index-nodate mr10"></span><span id="noDate">暂无数据</span>
									</p>
								</td>
							</tr>
						</s:if>
	                    <s:iterator value="list" var="gift" status="s" >
                    	<tr align="center" giftPriId="${gift.giftPriId }">
	                    	<td class="f-arial">${gift.giftId }</td>
	                    	<td>${gift.giftName }</td>
	                    	<td>${gift.giftTypeStr }</td>
	                    	<td class="f-arial"> ${gift.giftValueStr }</td>
	                    	<td class="f-arial"> ${gift.giftConStr }</td>
	                    	<td class="f-arial">${gift.operateTime }</td>
	                    	<td>${gift.loginName }</td>   
	                    	<td>${gift.stateStr }</td> 
	                    	<td><a href="javascript:void(0);" class="blue1"  onclick="delGift(${gift.state},${gift.giftId });">删除</a></td>                 	
                       </tr>
	                  </s:iterator>
                   
                  </table>
   				</div>
   			</div>
   			<!--Start page-->
                 <div class="page t-c mt20  fl widthfull" id="pageDiv">
	                   <s:if test="%{currentPageIndex!=0}">
	                  		<a href="javascript:void(0);" onclick="toPage(0);">首页</a>
                     		<a href="javascript:void(0);" onclick="toPage(${page.previousIndex});">上一页</a>
	                   </s:if>
                       <ul id="pageNumDiv">
                       		<s:iterator value="page.pageList">
							<s:if test="field02==1">
								<b><span class="current"><s:property value="field01" />
								</span>
								</b>
							</s:if>
							<s:else>
								<li>
									<a href="javascript:void(0);" onclick="toPage(${field03});"><s:property value="field01" /></a>
								</li>
							</s:else>
						</s:iterator>
                       </ul>
                       <s:if test="%{page.pageCount!=0 && ((currentPageIndex/page.pageSize)+1)!=page.pageCount}">
		             		<a href="javascript:void(0);" onclick="toPage(${page.nextIndex});">下一页</a>
		             		<a href="javascript:void(0);" onclick="toPage(${page.lastIndex});">末页</a>
		             </s:if>
		             <s:if test="%{page.pageCount!=1 && page.pageCount!=0}">
		             		<span class="ml10">跳转到第<input type="text" class="page-input" id="goPageNum" onkeyup="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onafterpaste="this.value=this.value.replace(/\D/g,'');checkGoPageNum(this.value);" onblur="toJudgePage(this.value);"/>页</span>
		             </s:if>
		             <s:if test="%{page.pageCount!=0}">
		                 <span class="ml25">[共<span id="totalPageNum" class="fw"><s:property value="page.pageCount" /></span>页]</span>
		             </s:if>
                 </div>
             <!--End page-->
         </div>
    </div>
    <form action="../gift/forwardGiftListPage.action" method="post" style="display: none;" id="turnPage">
    <input type="text" name="currentPageIndex" id="currentPageIndex"/>
 </form>
</body>
</html>
<script>
//增加礼品
function showAddPage()
{
	$("#iframe_right", parent.parent.window.document).attr("src","../gift/forwardAddGiftPage.action");
}
//删除礼品
function delGift(value,giftPriId)
{
// 	var giftPriId=$(this).parent().parent().attr("giftPriId");
	parent.parent.showPopCommonPage("确定要删除此礼品吗？");
	//解除防止多次执行click事件
	$("#commonSure",parent.parent.window.document).unbind('click');
	//动态添加方法
 	 $("#commonSure",parent.parent.window.document).click(function(){
 	 parent.parent.closePopCommonPage();
		var id=giftPriId
		if(value==1){
	    $("#topHide", parent.window.document).show();
		$("#leftHide", parent.window.document).show();
	    $("#mainBody").show();
	    $("#showPage").load("../gift/forwardDelGiftPage.action");
		}else {
			$.ajax({
				url:'../gift/deleteGiftPage.action',
				type:'post',
				data:{id:id},
				cache:false,
				dataType:"json",
				success:function(data){
	// 				alert(data+"==success");
					if(data=="success"){
						 parent.parent.showRturnTip("礼品删除成功!",true);
						 $("#iframe_right",parent.parent.window.document).attr("src","../gift/forwardGiftListPage.action");
					}else if (data=="error"){
						 parent.parent.showRturnTip("礼品删除失败!",false);
					}
				}
				
			});
		
		}
	  //解绑定，防止多次执行click事件
	  $("#commonSure",parent.parent.window.document).unbind('click');
	})
  $("#commonCancel",parent.parent.window.document).click(function(){
  	   		parent.parent.closePopCommonPage();
  	  		//解绑定，防止多次执行click事件
	 		$("#commonSure",parent.parent.window.document).unbind('click');
     });
 
}

//翻页方法
function toPage(value){
	$("#currentPageIndex").val(value);
	$("#turnPage").submit();
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

/*分页enter按键处理函数*/
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		var currPage = $("#goPageNum").val();
        //输入为空不跳转
        if(currPage == "" || typeof(currPage) == "undefined")
    	{
    		return false;
    	}
	    toJudgePage(currPage);
		break;
	default:
		break;
	}
}

</script>
