function Util(){
	/*当前页面的url*/
	this.url = window.location.href;  
}
function getSystemTime(){
	var myDate = new Date();
	var month = myDate.getMonth()+1
	var minutes = myDate.getMinutes();
	var hours = myDate.getHours();
	if(parseInt(minutes) < 10){
		minutes = "0" + minutes;
	}
	if(parseInt(hours) == 0){
		hours = "0" + hours;
	}
	if (myDate.getDay()==0) day="星期日";
	if (myDate.getDay()==1) day="星期一";
	if (myDate.getDay()==2) day="星期二";
	if (myDate.getDay()==3) day="星期三";
	if (myDate.getDay()==4) day="星期四";
	if (myDate.getDay()==5) day="星期五";
	if (myDate.getDay()==6) day="星期六";
	//Util.$("timeTitle").innerHTML = hours + ":" + minutes;
	//Util.$("dataTitle").innerHTML = myDate.getFullYear() + "-" + month + "-" + myDate.getDate()+" "+day;
	return myDate.getFullYear() + "-" + month + "-" + myDate.getDate();
}
/*
 * getDateObject函数是根据字符串获取Date对象;
 * 1个必传参数
 * @str    参数名称
 * @return {Date} 返回date
 */
Util.prototype.getDateObject = function (str) {
	var date;
	str = str.toString();
	/*如果当前传入字符串*/
	if(str && str.length >= 14)
	{
		/*年*/
		var strYear = str.substring(0,4);
		/*月-->月份需要先减去1月*/
		var strMouth = parseInt(str.substring(4,6)) - 1;
		/*日*/
		var strDate = str.substring(6,8);
		/*时*/
		var strHours = str.substring(8,10);
		/*分*/
		var strMinutes = str.substring(10,12);
		/*秒*/
		var strSeconds = str.substring(12,14);
		date = new Date(strYear,strMouth,strDate,strHours,strMinutes,strSeconds);
	}
	else
	{
		date = new Date();
	}
	return date;
}

/*
 * getDays函数是根据字符串获取星期;
 * 1个必传参数
 * @str    参数名称
 * @return {字符串} 返回day
 */
Util.prototype.getDays = function (str){
	var date = this.getDateObject(str);
    var day = date.getDay();
    switch (day) {
        case 0:
            return "星期天";
            break;
        case 1:
            return "星期一";
            break;
        case 2:
            return "星期二";
            break;
        case 3:
            return "星期三";
            break;
        case 4:
            return "星期四";
            break;
        case 5:
            return "星期五";
            break;
        case 6:
            return "星期六";
            break;
    }
}

/*
 * getURLParameter函数是根据格式转换时间;
 * 2个必传参数
 * @str    参数名称
 * @format    格式
 * @return {字符串} 返回date
 */
Util.prototype.dateFormat = function (str,format){
	var date = this.getDateObject(str);
	switch(format)
	{
		/*YYYY年MM月DD日*/
		case "YYYY年MM月DD日":
			date = date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日";
			break;
		/*YYYY/MM/DD*/
		case "YYYY/MM/DD":
		default:
			date = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
			break;
	}
    return date;
}

/*
 * getURLParameter函数是根据参数名获取url中的参数;
 * 2个必传参数
 * @paraName    参数名称
 * @url    所要更改参数的网址
 * @return {字符串} 参数的值
 */
Util.prototype.getURLParameter = function(param, url){
    var params = (url.substr(url.indexOf("?") + 1)).split("&");

    if (params != null) {
        for (var i = 0; i < params.length; i++) {
            var strs = params[i].split("=");
            if (strs[0] == param && strs[1]) {
                return strs[1];
            }
        }
    }
    return "";
}

/*
 * getURLListParameter函数是根据参数名获取url中的参数,主要是解决进入第三方全屏播放，第三方的路径传参不能用&传参，并且从第三方返回后，页面堆栈中的路径全部丢失;
 * 2个必传参数
 * @paraName    参数名称
 * @url    所要更改参数的网址
 * @return {字符串} 参数的值
 */
Util.prototype.getGoURLParameter = function(param, url){
	var params = (url.substr(url.indexOf("?") + 1)).split("&");
	if (params != null) {
        for (var i = 0; i < params.length; i++) {
        	var index = params[i].indexOf("goUrl");
        	if (index != -1 && index >= 0) {
        		var params = (params[i].substring(index + 6));
        		if(params != null)
        		{
        		    return params;	
        		}
        	}
        }
	}
	return "";
}

/*
 * cutstr函数是截取字符串;
 * 2个必传参数
 * @str    需要截取的字符串
 * @len    需要截取的长度（中文占2个）
 * @return {字符串} str新的字符串
 */
Util.prototype.cutstr = function(str,len)
{ 
    var str_length = 0; 
    var str_len = 0; 
    str_cut = new String(); 
    str_len = str.length; 
    for(var i = 0; i < str_len; i++) 
    { 
        a = str.charAt(i); 
        str_length++; 
        if(escape(a).length > 4) 
        { 
            //中文字符的长度经编码之后大于4 
            str_length++; 
        } 
        str_cut = str_cut.concat(a);
        if(str_length > len) 
        { 
            str_cut = str_cut.substring(0,str_cut.length-1).concat("..."); 
            return str_cut; 
        } 
    } 
    //如果给定字符串小于等于指定长度，则返回源字符串； 
    if(str_length <= len){ 
        return  str; 
    } 
} 

/*
 * $函数是获取dom对象;
 * 1个必传参数
 * @id    dom对象的id
 * @return {dom} dom对象
 */
Util.prototype.$ = function (id){
	return document.getElementById(id);
}

/*
 * isActiveDom函数是是否激活dom元素;
 * 2个必传参数
 * @id    dom对象的id
 * @state    true:激活dom对象 否则:去激活dom对象
 */
Util.prototype.isActiveDom = function(id,state){
	var display = "none";
	if(state)
	{
		display = "block"
	}
	this.$(id).style.display = display;
}

/*
 * isShowDom函数是是否显示dom元素;
 * 2个必传参数
 * @id    dom对象的id
 * @state    true:显示dom对象 否则:隐藏dom对象
 */
Util.prototype.isShowDom = function(id,state){
	var visibility = "hidden";
	if(state)
	{
		visibility = "visible"
	}
	this.$(id).style.visibility = visibility;
}

//字符串转日期型
Util.prototype.parseDate = function(str)
{
	var strArr = str.split("-");
	var year = strArr[0];
	var month = strArr[1];
	if(month.length < 2)
	{
	    month = "0" + month;	
	}
	var day = strArr[2];
	if(day.length < 2)
	{
	    day = "0" + day;	
	}
	//兼容ie8 字符串转换成日期格式
	var str = year + "-" + month + "-" + day;
	return str.replace(/-/g,"/");
}

//计算时间差函数
Util.prototype.TimeDiffirence = function(startTime,endTime){
    var diffDetail = {};
    try{
    	var date1 = new Date(this.parseDate(startTime));
	    var date2 = new Date(this.parseDate(endTime));
	    //毫秒差
	    diffDetail.msDiff = date2.getTime()-date1.getTime();
	    //分钟差
	    diffDetail.minutesDiff = Math.floor(diffDetail.msDiff/(60*1000));
	    //小时差
	    diffDetail.hoursDiff = Math.floor(diffDetail.minutesDiff/60);
	    //天数差
	    diffDetail.daysDiff = Math.floor(diffDetail.hoursDiff/24);
	    
	}catch(e){}
		
	return diffDetail;
}

var Util = new Util();