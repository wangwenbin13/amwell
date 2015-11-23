<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销管理-站内信-发送站内信</title>
<%@include file="../resource.jsp" %>
<!-- 文字编辑器的js -->
<script charset="utf-8" src="../js/kindeditor-4.1.1/kindeditor-min.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.1/lang/zh_CN.js"></script>
<script type="text/javascript" src="../js/jquery/ajaxfileupload.js"></script>

</head>
<body>

<s:form id="form2" action="../insideMessage/addInsideMsg.action" method="post" theme="simple">

<div class="table2-text table2-text-height">
	<div class="xinxi-box">
	    <table width="100%" border="0" class="line24">
	        <tr>
	            <th scope="row" class="f14 fw xinxi-box-t">发送站内信</th>
	            <td valign="top">
	                <ul class="mt15 ml10">
	                	<li class="w58p mb10 clearfix"><span class="w75 t-r fl">站内信标题：</span> 
	                		<input type="text" class="r-input w29p fl"  name="search.field03" id="title" check="11"/> 
	                	</li>
	                	<li class="w58p mb10 clearfix"><span class="w75 t-r fl">模板名称：</span>                		
	                    	<select class="w30p p3" name="" id="template_name" onchange="queryTemplateContent()">   
	                			<option value="-1" >即时编辑</option>
	                			<s:iterator value="list" var="msgTemplateVo">
	                			  	<option value="${msgTemplateVo.id }" >${msgTemplateVo.template_name }</option>
	                			</s:iterator>           		
	                    	</select> 
	                	</li>
	                    <li class="w58p mb10 clearfix"><span class="w75 t-r fl">内容：<em class="red1 t-r mr4">* </em></span>
	                    	<textarea class="r-input more-show fl"  id="content" style="width:500px;height:200px;" name="search.field02" check="11" onkeydown="textcounts()"  
	                    	onKeyUp="textcounts();"></textarea>
	                    	<p class="fl w80p gray2"><span class="w75 t-r fl"></span>字数限定在600字节以内（2字节=1个中文字）您还可以输入
		                	<input id="remlen" name="remlen" type="text" value="600" size="5" readonly="readonly"/>个字符
		                	<input type="hidden" name="content" value=""/>
		                	</p>
	                    </li>
	                   
	                	<li class="w58p mb10 clearfix"><span class="w75 t-r fl">站内信图片：</span>
	                		<!-- 展示图片的地方 --> 
	                		<div class="fl appSendImg"  id="driving_licence_img">
	                			<img src="../images/msg_img_bg.png" id="driving_licence"/>
	                		</div>
	                		
	                		<span class="p-r file-box fl" style="width: 70px;margin-top:113px;margin-left:10px">			        		    
				        		<input type="file" class="file p-a fl" name="photo" style="top: 2px;" id="driving_licence_photo" onchange="doUpload2(this);"/>
				                <a href="javascript:void(0)" class="blue1 ml4">上传图片</a>
			                </span>
	                	</li>
	                	<li class="w58p mb10 clearfix">
	                     	 <span class="fl w75 t-r">发送时间：</span>
	                     	 <span class="fl"><span onclick="" class="black1 ml10"><input type="radio" class="checkbox mr4" name="search.field05"   value="0" checked="checked" id="lijifasong" /></span>立即发送</span>
				              <span class="fl"><span onclick="" class="black1 ml10"><input type="radio"  class="checkbox mr4" name="search.field05" value="1" id="dingshifasong"/></span>定时发送</span>
				             <span class="r-input fl w20p ml10" style="display: none;" id="selectTime"><input type="text" name="search.field06" value="" class="Wdate75 gray2 Wdate fl" readonly="readonly" id="txtStartDate" onclick="WdatePicker({position:{left:-9,top:4},dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"/></span>	
	                     </li>
	                	<li class="w99p h37 mb10">
	                        <div class="sb-top p-r" id="sb-top">
				            	<span class="w75 t-r">发送人群：</span>
				            	<span onclick="changePage(0)" class="black1"><input type="radio" checked="checked"  class="checkbox" name="search.field04" value="3"/></span><em class="ml4">全部用户</em>		
				                <span class="black1 ml10" onclick="changePage(1)"><input type="radio"   class="checkbox " name="search.field04" value="1"/></span><em class="ml4">选择发送</em><a href="javascript:void(0);" onclick="changePage(0)" class="blue1 ml10"></a>
				                <span onclick="changePage(2)" class="black1 ml10"><input type="radio" class="checkbox " name="search.field04"  value="2"/></span><em class="ml4">批量导入号码</em>
				                	               
				            </div>
				            
	                        
	                     </li>
	                     
	                </ul>
	                <%@include file="/WEB-INF/view/marketing/selectPassenger.jspf" %>
						   		 	 
	                <!-- 乘客列表Div end -->
	                
	                <!-- 导入信息Div start -->
	                <div class="ml20 mr20 mb10 hide p-r" id="leadDiv">
	                	<div id="leadDivErrTip" class="tipTable" style="display: none; margin-top: 0px; margin-left: 73px;">没有选中号码</div>
	                	<div class="dj-box p-r" style="margin-left: 72px;width:540px;background:#fff">
			            <span style="left:173px;top:-10px" class="sb-arrow p-a"></span>	   
		               
		                	
					    	<div class="mt10 mb15">
					      	  	<span class="t-r w75 fl">导入文件：</span>
					      	  	<input type="text" class="r-input w222 fl" id="showImg" readonly="readonly"/>
					      	  	<input type="hidden" id="choocePassengerList" name="search.field01"/>
					       		 <span class="p-r yc-file fl ml10">
						       		 <input id="excelFile" name="excelFile" type="file" class="file2 p-a" onchange="uploadFile();"/>
						       		 <a class="display-ib btn-gray black1" href="javascript:void(0)">浏览</a>
					       		 </span>

					       		 <a href="../marketing/downloadTemplate.action" class="blue1 ml10">下载Excel模板</a>
						    </div>
					    
				    </div>
				    </div>
	                <!-- 导入信息Div end -->
	            </td>
	        </tr>
	    </table>
	</div>
	<p class="t-c mt20"><input type="button" class="display-ib btn1 white mr40" id="submitButton" value="发送"/>
	<input type="button" onclick="javascript:history.go(-1);" class="display-ib btn1 white" value="取消"/></p>
</div>
<s:hidden theme="simple" name="search.field08" id="vehicleUrl"></s:hidden>
</s:form>
</body>
</html>
<script type="text/javascript">

//获取模板内容，根据ID
function queryTemplateContent(){
	var v = $("#template_name").val();
	if(typeof(v)=="undefined" && v == "" ){
		parent.parent.showRturnTip("没有可用模板",false);
		return;
	}
	 $.ajax({
			url:'../insideMessage/queryMarketingModel.action?random='+Math.floor(Math.random()*10000+1),
			cache:false,
			data:{"msgTemplateVo.id":v},
			type : 'post',
			dataType:"json",
			success:function(data){	
// 				alert(data.template_content);
				if(data != null && data != ''){
					$("#content").val(data.template_content);
				}
	 		}
	 });
}
//创建添加问题的文字编辑器
var editor;
$(function(){
	$("#phoneList").hide();

	//显示定时发送时间控件
	$("#dingshifasong").click(function(){
		$("#selectTime").css("display","block");
	});

	//隐藏定时发送时间控件
	$("#lijifasong").click(function(){
		$("#selectTime").css("display","none");
	});
})
KindEditor.ready(function(K) {
	var options = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image', 'link']
	}
	//editor = K.create("#content",options);
});


//限制text里的字符数量
function textcounts(){
 
	var maxlimit = 600; 
	var content = $("#content").val();
	var remlen = $("#remlen").val();//显示字数
	var count=0;	//计算字符数
	var char=0;		//计算剩余字符数				
	for(var i=0;i<content.length;i++){
		var ask11=content[i].charCodeAt();  //ascii码
		if(0<=ask11 && ask11<161){
			count++;
			if(count<maxlimit){
			char++;
			}
		}else{
			count+=2;
			if(count<maxlimit){
			char++;
			}
		}
	}
	if(count>maxlimit){
		content = content.substring(0, char);
		$("#content").val(content);
	}else{
		 $("#remlen").val(maxlimit-count);
	}
	
}   




//------------------------- 列表选中和未选中end------------------------------
//table2-text高度
$(function(){	
	$(".table2-text-height").css("min-height",$(window).height()-36+"px");
	$(window).resize(function(){
		$(".table2-text-height").css("min-height",$(window).height()-36+"px");	
	});
	
	//
	validateFunction();
});




 var telephoneAdd=new Array();



//获取选中的乘客电话列表
function getTelephone(){
	$("#phoneList").show();
	var passengerPhones="";
	for(var i=0;i<telephoneAdd.length;i++){
		passengerPhones+=telephoneAdd[i]+" ";

	}
	$("#choocePassengerList").val(passengerPhones);

}

//发送短信
function sendMessage(){
	
	// 获得手机号
	var phoneNumber = getNotCheckLoginIds();
	setSubmitDisales(true);
	$("#form2").ajaxSubmit({
	type:'post',
	data:{"phoneNumber":phoneNumber},
	success : function(data){
		data = eval("(" + data + ")");


		if(data.result=="success"){
			parent.parent.showRturnTip("发送成功",true);

			$("#iframe_right",parent.parent.window.document).attr("src","../insideMessage/insideAPPManager.action?level=two&toAdd=0");
			setSubmitDisales(false);
		
		}else if(data.result=="error"){
			var phone=data.telephone;

			if(phone!=null){

				parent.parent.showRturnTip("第"+phone+"组号码错误！",false);
			}else{
				parent.parent.showRturnTip("发送失败,短信内容或号码没数据",false);
			}
			setSubmitDisales(false);
		}else if(data.sendStatus!=0 && data.sendStatus!=1 && data.sendStatus!=3 ){
			parent.parent.showRturnTip("短信发送失败！",false);
			setSubmitDisales(false);
		}else if(data.result=="timeError"){
			parent.parent.showRturnTip("定时发送时间不能为空",false);
			setSubmitDisales(false);
		}	
	}	
	});
	

}

function uploadFile(){
	var value = $("#excelFile").val();
	
	$("#showImg").val(value);
	if(""==value||value.indexOf(".")==-1){
		return;
	}
	var reg = validateJson.excelFile.reg;
	var thisReg = new RegExp(reg);
	if(!thisReg.test(value))
	{
		parent.parent.showRturnTip(validateJson.excelFile.tip,false); 
		return;
	}
   $.ajaxFileUpload({
			fileElementId:'excelFile', 
			secureuri:false,
			dataType: 'text',
			url : '../marketing/upload.action',
			success : function(data) {
				data = eval("(" + data + ")");
				var samePhones=data.theSameTelephone;
				var wrongPhones = data.wrongTelephone;
// 				alert(wrongPhones);
				if (wrongPhones != null) {
					parent.parent.showRturnTip("错误的手机号码：" + wrongPhones,false);
				}
				if(samePhones!=null){
					parent.parent.showRturnTip("相同的电话号码：" + samePhones,false);
				}
				if (data.result == "error") {
				} else {
					parent.parent.showRturnTip("导入成功"+data.count+"条数据！",true);
					var telephones = data.importTelephones;
					$("#choocePassengerList").val(telephones);
				}
			},
		});
	}
	
	//获取未选中的所有id
function getNotCheckLoginIds()
{
	var checkboxs = $("input[name='checkboxchild']");
	var loginIds = "";
	
	for (var i = 0; i < checkboxs.length; i++)
	{
		
		if (checkboxs[i].type == "checkbox" && checkboxs[i].id != "checkAllBox")
		{
			
			if (checkboxs[i].checked)
			{
				loginIds = loginIds + checkboxs[i].value + " ";
			}
		}
	}
	
	
	return loginIds;
}

	//图片预览
	function goViewPicPage()
	{
	    
		$("#topHide", parent.parent.window.document).show();
	    $("#leftHide", parent.parent.window.document).show();
	    $("#showPage",parent.window.document).load("../marketing/getViewPic.action?random="+Math.floor(Math.random()*10000+1)); 
	    $("#mainBody",parent.window.document).show();
	}

//异步上传图片
function doUpload2(obj)
{
	var value = $(obj).val();
	if("" == value){
		return;
	}
	value = value.split(".");
	value = value[value.length-1];
	var reg = validateJson.UploadFile5K.reg;

	
	var thisReg = new RegExp(reg);
	if(thisReg.test(value))
	{
		
		parent.parent.popLodingPage(true);
		$.ajaxFileUpload({
				
				fileElementId:$(obj).attr("id"), 
				secureuri:false,
				dataType: 'text',
				url : '../ftpUploadAction/upLoad.action?uploadFileType=5',
				success : function(data) {
			    parent.parent.popLodingPage(false);
					if (data == "error") {
						parent.parent.showRturnTip("上传失败！","false");
					}else if(data=="overSize"){
						//图片过大，请上传不大于500K的图片
						parent.parent.showRturnTip("图片过大，请上传不大于500K的图片！","false");
					}else{
						
						data = eval("(" + data + ")");
						document.getElementById("driving_licence").src = data.downFileUrl;
						$("#driving_licence_photo").next("a").html("重新上传");
						$("#vehicleUrl").val(data.dbFileUrl);
// 						
					}
					
				},
				error:function(){
					parent.showRturnTip("上传失败！","false");
				}
	     });
	}
	
}

//验证开始
//验证方法
function validateFunction()
{
	
    //validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [{"validateName":"title:content","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip}//必填字段不为空判断
		
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}

	//一个form表单提交验证
	$('#submitButton').click( function () {
		 
		 getthis.each(function(){
			var obj = this;
			
			checkByType(obj);
		});
	   
	   //调用自定义验证
	   validateSendTime();
	   //
	   sendObject();
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;

		if(failValidateCount == 0)
		{
			//验证提交 防止多次提交
			setSubmitDisales(false);

			sendMessage();
			return false;
		}
		else
		{
			//验证提交 防止多次提交
			setSubmitDisales(true);
		    return false;
		}
	});
}

function setSubmitDisales(flag)
{
     if(flag)
	 {
		$('#submitButton').removeClass("btn1").addClass("btn1-gray");
	 }
	 else
	 {
		 $('#submitButton').removeClass("btn1-gray").addClass("btn1");
	 } 
	 $('#submitButton').attr("disabled", flag);
}


/**
自定义验证，发送时间，当选择立即发送，就不做处理，当选择定时发送，发送时间不能为空
 dingshifasong
**/
function validateSendTime(){
	
	var val=$('input:radio[name="search.field05"]:checked').val();
	
	//立即发送
	if(val == 0){
	 	return;
	}
	
	//定时发送，验证时间是否为null;
	var sendTime = $("#txtStartDate").val();
	
	if(sendTime == ""){
		createErrorTip("txtStartDate","定时发送时间不能为空");
		
	}else{
		// 验证时间通过
		validateUserDefinedSucc("selectTime");
	}
}

//发送人群验证
function sendObject(){
	
	var val=$('input:radio[name="search.field04"]:checked').val();
	//选择用户
	if(val == 1){
		var number = getCheckLoginIds();
		if(number == ""){
			createErrorTip("marketingPassengerList","没有选中号码");
		}
	}
	
	if(val == 2){
		
		var telephones = $("#choocePassengerList").val();
		if(telephones == ""){
			$("#leadDivErrTip").show();
		}
		else
		{
			$("#leadDivErrTip").hide();
		}
	}
}


//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
    $("#"+id).parents("li").find(".onCorrect").remove();
    $("#"+id).parent().append("<span id='"+id+"Tip'></span>");
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
	if(id == "txtStartDate")
	{
		$("#"+id+"ErrTip").css("margin-left","-4px");
	}
	else
	{
		$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
	}
	if(id == "marketingPassengerList")
	{
		$("#"+id+"ErrTip").css({"margin-top":"0","margin-left":"81px"});
	}
	else
	{
		$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
	}
}

//自定义验证通过
function validateUserDefinedSucc(id)
{
    $("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id).parent().append("<span id='"+id+"Tip'></span>");
    $("#"+id+"Tip").addClass('onCorrect');
	$("#"+id+"ErrTip").hide();
}

//清除自定义验证提示语句
function validateUserDefineClear(id)
{
    $("#"+id+"ErrTip").hide();
    $("#"+id).parent().find(".onCorrect").remove();
}

$("#marketingPassengerList").click(function(){
	validateUserDefineClear("marketingPassengerList");
	setSubmitDisales(false);
});

$("#leadDiv").click(function(){
	$("#leadDivErrTip").hide();
	setSubmitDisales(false);
});


//绑定事件
$("#content,#title").focus(function(){
	 //验证提交 防止多次提交
	setSubmitDisales(false);
}).blur(function(){
	
	
});

// 绑定事件
$("#txtStartDate").focus(function(){
	//清除自定义错误提示语句
	validateUserDefineClear("txtStartDate");
	 //验证提交 防止多次提交
	setSubmitDisales(false);
}).blur(function(){
	validateSendTime();
});
$("#leadDiv").click(function(){
	$("#leadDivErrTip").hide();
	setSubmitDisales(false);
});

$("[name='search.field05'],[name='search.field04']").click(function(){
	
	setSubmitDisales(false);
})
//验证结束
</script>