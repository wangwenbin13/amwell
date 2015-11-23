<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车辆管理-添加车辆</title>
<jsp:include page="../resource.jsp"/>
<script type="text/javascript" src="<%=basePath %>js/jquery/ajaxfileupload.js"></script>
</head>

<body>
<p class="subNav">当前位置：首页&nbsp;>&nbsp;车辆管理&nbsp;>&nbsp;${(null==vehicleInfoEntity.vehicleId||""==vehicleInfoEntity.vehicleId)?"添加车辆":"编辑车辆"}<a href="javascript:void(0)" class="red1 ml30" onclick="javascript:history.go(-1);">返回</a></p>
<div class="mt20 ml30 mr28">
        <form action="../dispatchVehicle/vehicleInfoSave.action" method="post" id="the_add_form">
        <s:hidden theme="simple" name="theAddParam" value="0" id="theAddParam"></s:hidden>
        <s:hidden theme="simple" name="vehicleInfoEntity.vehicleId"></s:hidden>
        <p class="fw f14">${(null==vehicleInfoEntity.vehicleId||""==vehicleInfoEntity.vehicleId)?"添加车辆":"编辑车辆"}</p>
        <p class="mt20 libg"><span class="fl ml20 lh28">以下信息为必填</span></p>
        <ul class="clearfix">
            <li class="clearfix mt20"><span class="w75 t-r fl"><em class="red3 mr4 fl">*</em>车牌号码：</span>
            <s:textfield name="vehicleInfoEntity.vehicleNumber" maxlength="12" cssClass="r-input w400 fl" id="vehicleNumber" check="2"></s:textfield>
            </li>
            <li class="clearfix mt20">
	            <span class="w75 t-r fl"><em class="red3 mr4 fl">*</em>车辆品牌：</span>
	            <s:textfield name="vehicleInfoEntity.vehicleBrand" maxlength="32" cssClass="r-input w167 fl mr8" id="vehicleBrand" check="11"></s:textfield>
	            <span class="w52 t-r fl"><em class="red3 mr4 fl">*</em>车型：</span>
	            <s:textfield name="vehicleInfoEntity.vehicleModel" maxlength="32" cssClass="r-input w167 fl" id="vehicleModel" check="11"></s:textfield>
            </li>
             <li class="clearfix mt20 p-r">
	            <span class="w75 t-r fl"><em class="red3 mr4 fl">*</em>座位数量：</span>
	            <s:textfield name="vehicleInfoEntity.vehicleSeats" maxlength="2" cssClass="r-input w167 fl mr8" check="2" id="vehicleSeats"></s:textfield>
	            <span class="w52 t-r fl"><em class="red3 mr4 fl">*</em>类型：</span>
	            <div class="fl r-input w167"><span id="carStyleDiv"></span><span class="fr sel-arrow mt10 mr10"></span></div>
				<s:select name="vehicleInfoEntity.vehicleType" list="#{'0':'大巴','1':'中巴','2':'小巴'}" cssClass="p-a sel-demo w176 h29" id="carStyle" style="left:308px;"></s:select>
            </li>
        </ul>
        <p class="mt20 libg"><span class="fl ml20 lh28">以下信息为选填</span></p>
        <ul class="fl mt20 w600">
            <li class="clearfix">
	            <span class="w75 t-r fl">车牌颜色：</span>
	            <s:textfield name="vehicleInfoEntity.vehicleColor" maxlength="8" cssClass="r-input w400 fl mr8"></s:textfield>
            </li>
             <li class="clearfix mt20"><span class="w75 t-r fl">车辆编号：</span>
             <s:textfield name="vehicleInfoEntity.vehicleNo" maxlength="12" cssClass="r-input w400 fl"></s:textfield>
             </li>
             <li class="clearfix mt20"><span class="w75 t-r fl">GPS设备号：</span>
             <s:textfield name="vehicleInfoEntity.GPSNo" maxlength="100" cssClass="r-input w400 fl"></s:textfield>
             </li>
             <li class="clearfix mt20"><span class="w75 t-r fl">购买日期：</span>
             	<span class="r-input w400 fl"><input name="vehicleInfoEntity.vehicleBuyTime" type="text" class="Wdate75 Wdate" value="${vehicleInfoEntity.vehicleBuyTime}" readonly="readonly" onclick="WdatePicker({position:{left:-1,top:0},dateFmt:'yyyy-MM-dd'})"/></span>
             </li>
             <li class="clearfix mt20"><span class="w75 t-r fl">车&nbsp;&nbsp;系：</span>
             <s:textfield name="vehicleInfoEntity.vehicleDepart" maxlength="32" cssClass="r-input w400 fl"></s:textfield>
             </li>
             <li class="clearfix mt20"><span class="w75 t-r fl">备注信息：</span>
             	<s:textarea name="vehicleInfoEntity.remark" cssClass="r-input more-show w400" onKeyUp="if(this.value.length > 225) this.value=this.value.substr(0,225)"></s:textarea>
             </li>
             <li class="clearfix mt20"><span class="w75 t-r fl"></span>
             	 <input type="button" class="display-ib btn1 white1 mr30 fw" value="保&nbsp;&nbsp;存" id="submitBtn"/>
		         <s:if test="%{null==vehicleInfoEntity||null==vehicleInfoEntity.vehicleId||''==vehicleInfoEntity.vehicleId}">
		         <a class="display-ib btn1-no" href="javascript:void(0);" onclick="clearValue();">取&nbsp;消</a>
		         </s:if>
		         <s:else>
		         <a class="display-ib btn1-no" href="javascript:window.location.href='<%=basePath %>dispatchVehicle/vehicleInfoList.action'">取&nbsp;消</a>
		         </s:else>
             </li>
        </ul>
        <div class="fl w400 mt20">
        	<div class="pic-show w396">
        		<img src="../images/noCar.png" class="hide" width="396" height="235" id="driving_licence" />
        		<div class="t-c mt30" id="the_p">
        			<img src="../images/noCar.png" width="120" height="118"/>
        			<div class="t-c p-r mt25 fileCar">
	        			<input type="file" class="file p-a" style="height:28px;" name="photo" id="driving_licence_photo" onchange="doUpload2(this);" />
	        			<a class="display-ib red1" href="javascript:void(0)"><em class="uploadIco1 mr4"></em>上传车辆照片</a>
        			</div>
        		</div>
        	</div>
        	<p class="w396 mt11">
        		<span class="fl gray2">可上传jpg、png、jpeg、bmp或gif格式图片，图片大小不超过500K</span>
        		<span class="p-r fr w70" id="upload_again" style="display:none;overflow:hidden">
	       		 <input type="file" class="file p-a" name="photo" id="driving_licence_photo1" onchange="doUpload2(this);" />
	       		 <a class="display-ib red1" href="javascript:void(0)">重新上传<em class="uploadIco2"></em></a>
       		   </span>
        	</p>
        	<s:hidden theme="simple" name="vehicleInfoEntity.vehicleUrl" id="vehicleUrl"></s:hidden>
        	<input type="hidden" value="${fullImgUrl}" id="fullImgUrl"/>
        </div>
        
         </form>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	//类型
	$("#carStyle").change(function(){
		$("#carStyleDiv").text($("#carStyle").find("option:selected").text());
	});
	//回显被选项
	$("#carStyleDiv").text($("#carStyle").find("option:selected").text());
	validateFunction();
	
	if($("#fullImgUrl").val()!=""){
		$("#the_p").hide();
		$("#upload_again").show();
		document.getElementById("driving_licence").src = $("#fullImgUrl").val();
		$("#driving_licence").show();
	}
});

//点击继续添加
function add_again(){
	$("#theAddParam").val("1");
	$("#the_add_form").submit();
}

var uploadFlag = "driving_licence_photo";
if(uploadFlag == "driving_licence_photo")
{
	var prePath = $('#driving_licence_photo').val();
}
else
{
	var prePath = $('#driving_licence_photo1').val();
}
//异步上传图片
function doUpload2(obj)
{
	var curPath = $(obj).val();
    if (prePath == curPath) {
        return false;
    }
    prePath = curPath;
    
    if($(obj).attr("id") == "driving_licence_photo")
    {
    	uploadFlag = "driving_licence_photo";
    }
    else
    {
    	uploadFlag = "driving_licence_photo1";
    }
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
		$("#driving_licence").hide();
		$("#driving_licence_img").show();
		parent.popLodingPage(true);
		$.ajaxFileUpload({
				//dataType:'text',
				fileElementId:$(obj).attr("id"), 
				secureuri:false,
				dataType: 'text',
				url : '../ftpUploadAction/upLoad.action?upLoadCarImgType=4',
				success : function(data) {
			    parent.popLodingPage(false);
					if (data == "error") {
						parent.showRturnTip("上传失败！","false");
					}else if(data=="overSize"){
						//图片过大，请上传不大于500K的图片
						parent.showRturnTip("图片过大，请上传不大于500K的图片！","false");
					}else{
						//alert("data==="+data);
						data = eval("(" + data + ")");
						$("#driving_licence").show();
						$("#driving_licence_img").hide();
						$("#the_p").hide();
						$("#upload_again").show();
						document.getElementById("driving_licence").src = data.downFileUrl;
						$("#vehicleUrl").val(data.dbFileUrl);
					}
				},
				error:function(){
					parent.showRturnTip("上传失败！","false");
				}
	     });
	}
	else
	{
		parent.showRturnTip(validateJson.UploadFile5K.tip,"false");
		$("#input_icon_"+i).val("");
	}
}
//验证方法
function validateFunction()
{
    //validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [{"validateName":"vehicleBrand:vehicleModel","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		{"validateName":"vehicleSeats","validateReg":validateJson.Seat.reg,"validateTip":validateJson.Seat.tip},//座位数
		{"validateName":"vehicleNumber","validateReg":validateJson.isCarNum.reg,"validateTip":validateJson.isCarNum.tip}//车牌号
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
	
	//一个form表单提交验证
	//$('#the_add_form').submit( function () {
		$('#submitBtn').click( function () {
			 getthis.each(function(){
				var obj = this;
				checkByType(obj);
			});
			
			//failValidateCount = 0  代表页面全部验证通过
			//验证未通过的计数
		    var failValidateCount = $(document).find(".tipTable:visible").length;
			if(failValidateCount == 0)
			{

				$("#the_add_form").ajaxSubmit({
					type : 'post',
					data:{},
					url : "../dispatchVehicle/vehicleInfoSave.action?requestType=page&random="+Math.floor(Math.random()*10000+1),
					dataType:"html",
					success : function(data) {
						var the_data=data.split("=");
						if(1 == the_data[0]){
							parent.showRturnTip("车牌号码已存在，请重新输入","false");
							//验证提交 防止多次提交
							setSubmitDisale(true);
						}
						if(2 == the_data[0]){
							parent.showPopCommonPage("添加成功","true","true"); 

							$("#commonSure",parent.window.document).text("再添加");
							$("#commonCancel",parent.window.document).text("去查看");

							$("#commonSure",parent.window.document).click(function(){
								parent.closePopCommonPage();
								window.location.href="../dispatchVehicle/toVehicleEditPage.action";
								//解绑定，防止多次执行click事件
								$("#commonSure",parent.window.document).unbind('click');
							});

							$("#commonCancel",parent.window.document).click(function(){
								parent.closePopCommonPage();
								//window.location.href="</=basePath%>dispatchVehicle/vehicleInfoList.action";
								//返回车辆列表
								parent.openLeftMenu("../dispatchVehicle/vehicleInfoList.action");
								//解绑定，防止多次执行click事件
								$("#commonSure",parent.window.document).unbind('click');
							});
							
							
							//验证提交 防止多次提交
							setSubmitDisale(false);
						}
						if(3 == the_data[0]){
							parent.showRturnTip("添加失败","false");
							//验证提交 防止多次提交
							setSubmitDisale(true);
						}
						if(4 == the_data[0]){
							parent.showRturnTip("修改成功","true");
							//验证提交 防止多次提交
							setSubmitDisale(false);
							window.location.href="../dispatchVehicle/vehicleInfoList.action";
						}
						if(5 == the_data[0]){
							parent.showRturnTip("修改失败","false");
							//验证提交 防止多次提交
							setSubmitDisale(true);
						}
					}
				});
			}
			else
			{
				
				//验证提交 防止多次提交
				setSubmitDisale(true);
			   // return false;
			}
		});
}

//取消
function clearValue(){
	              
	setSubmitDisale(false);
	$('input:text,input:password,textarea').each(function(){ 
		$(this).val("");
		$("#"+this.id+"Tip").hide(); 
		$("#"+this.id+"ErrTip").hide();
		$("#"+this.id).css("background","#f6f5f5");
	});
}
</script>
