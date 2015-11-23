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
		success:successHandle
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
		success:successHandle
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

/** 日期转换 */
function toLocalWeekDay(date1, date2) {
	var offset = date2.getTime() - date1.getTime(), day = date2.getDay();
	if (offset > 0) {
		if (offset < 86400000)
			day = -2;
		else if (offset < 172800000)
			day = -3;
	} else
		day = -1
	switch(day) {
		case -3: return '后天';
		case -2: return '明天';
		case -1: return '今天';
		case 1: return '周一';
		case 2: return '周二';
		case 3: return '周三';
		case 4: return '周四';
		case 5: return '周五';
		case 6: return '周六';
		default: return '周日';
	}
}

/** 字符串的处理. */
String.prototype.cnLen = function () {
	return this.replace(/[^u4E00-u9FA5]/g, 'nn').length;
}
String.prototype.replaceAll = function(s1,s2) {
    return this.replace(new RegExp(s1,"gm"), s2); 
}
String.prototype.cnSubstring = function (size, width) {
    var len = this.cnLen(), scale = this.length / this.cnLen(),
        maxSize = width / size >> 0;
    if (len > maxSize)
        return this.substring(0, this.length * (maxSize / len) >> 0) + "...";
    else
        return this.toString();
}

/** 获取位置授权； */
var geolocationTimeout = null;
function getPosition(callback) {
	var isTimeout = false;
	// 百度地图API功能
	geolocationTimeout = setTimeout(function () {
		isTimeout = true;
		callback && callback();
	}, 5000);
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r) {
		if (isTimeout)
			return;
		clearTimeout(geolocationTimeout);
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			var latitude = r.point.lat.toFixed(6), longitude = r.point.lng.toFixed(6);
			callback && callback({latitude: latitude, longitude: longitude});
		}
		else {
			callback && callback();
			alert('failed' + this.getStatus());
		}        
	}, {enableHighAccuracy: true});
}

/** 获取用户； */
function getLoginUser() {
	var user = localStorage.getItem("MyUserInfo");
	if (user) {
		try {
		user = eval('(' + user + ')');
		if (user['a2'])
			return user;
		} catch(exc) {}
	}
	return null;
}

/** 锚点跳转； */
function forwardHash(mao) {
	// history.pushState({}, null, mao);
	setTimeout(function () {
		var curUrl = location.href, idx = curUrl.indexOf('#');
		if (idx != -1)
			curUrl = curUrl.substring(0, idx);
		location.href = curUrl + mao;
	}, 200);
}

/** 终止事件派发； */
function stopEvent (evt) {
	evt.returnValue = false;
	evt.cancelBubble = true;
	evt.preventDefault && evt.preventDefault();
	evt.stopPropagatio && evt.stopPropagatio();
	return false;
}

(function () {
	var vw = $(window).width();
	if (document.body.className != 'none' && !document.body.getAttribute('center'))
		document.body.style.padding = ['0px ', (vw - 314 >> 1), 'px'].join('');
})();
