//////////////////////////////////////////////
//租赁业务工具脚本
//////////////////////////////////////////////

// 封装get的ajax请求
function leaseGetAjax(url,dataType,successHandle){
	$.ajax({
		url:url,
		type:"get",
		dataType:dataType,
		cache:false,
		success:successHandle,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
            //alert(XMLHttpRequest.status);
            //alert(XMLHttpRequest.readyState);
           // alert(textStatus);
        }
	});
}

// 封装post的ajax请求
function leasePostAjax(url,form,dataType,successHandle){
	$.ajax({
		url:url,
		type:"post",
		data:form.serialize(),
		dataType:dataType,
		cache:false,
		success:successHandle,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
            //alert(XMLHttpRequest.status);
            //alert(XMLHttpRequest.readyState);
            //alert(textStatus);
        }
	});
}

//计算当前系统时间往后推天数或月数的时间
function addDate(val,type){
	var url = '../registAndBook/addDate?val='+val+"&type="+type;
	leaseGetAjax(url,"json",function(data){
		$("#rentDateStr").val(data.field01);
		$("#returnDateStr").val(data.field02);
		$("#lease_qyzq").val(data.field03);
		// 隐藏提示语句
		$("#rentDateStrErrTip").hide();
        $("#returnDateStrErrTip").hide();
        $("#rentDateStrTip").hide();
        $("#returnDateStrTip").hide();
        calPrepaidRentLimit();
        setSubmitDisale(false);
	});
}

//计算两个日期中间间隔天数
function calcDate(beginDate,endDate,target){
	var Bdate = $("#"+beginDate).val();
	var Edate = $("#"+endDate).val();
	if(Bdate!=null && Bdate !="" && Edate!=null && Edate != "" ){
		var url = '../registAndBook/calcDate?Bdate='+Bdate+"&Edate="+Edate;
		leaseGetAjax(url,"html",function(data){
			if(data=="0"){
				$("#"+target).val("1");
			}else{
				$("#"+target).val(data);
			}
		});
	}
}

//计算两个日期中间间隔天数
function calDate(beginDate,endDate,callback){
	var result = 0;
	var url = '../jieSuan/calDate?startDate='+beginDate+"&endDate="+endDate;
	leaseGetAjax(url,"html",function(data){
		callback(data);
	});
}



//计算天的数目
function calDays(dateStr){
	var daysArray = dateStr.split("天");
	if(daysArray.length>1){
		return daysArray[0];
	}
	return 0;
}

// 获取系统的当前时间
function getSystemNowTime(){
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var minutes = myDate.getMinutes();
	var hours = myDate.getHours();
	if(parseInt(minutes) < 10){
		minutes = "0" + minutes;
	}
	if(parseInt(hours) < 10){
		hours = "0" + hours;
	}
	if (myDate.getDay()==0) day="星期日";
	if (myDate.getDay()==1) day="星期一";
	if (myDate.getDay()==2) day="星期二";
	if (myDate.getDay()==3) day="星期三";
	if (myDate.getDay()==4) day="星期四";
	if (myDate.getDay()==5) day="星期五";
	if (myDate.getDay()==6) day="星期六";
	return myDate.getFullYear() + "-" + month + "-" + myDate.getDate() + " " + hours + ":" + minutes;
}


//隐藏遮罩层
function stopShare(){
	$("#share").hide();
	$("#shareChooice").hide();
}

/**
加载loding页面
falg=true表示显示，falg=false表示隐藏
popLodingPage(flag)
**/
function popLodingPage(flag){
	if(flag){
		$("#lodingHide").show();
		$("#lodingShowPage").show();
	}else{
		$("#lodingHide").hide();
		$("#lodingShowPage").hide();
	}
}

/**
 * 页面加载详情时的提示
 * @return
 */
function loadTip(){
	$("#loadDetail").hide();
	$("#load_title").show();
	popLodingPage(true);
}

function hideTip(){
	$("#loadDetail").show();
	$("#load_title").hide();
	popLodingPage(false);
}