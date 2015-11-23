//------------------------------------------  分页显示的js  start currPage和totalPage必不可少-----------------------------------------


var pageSizeEm = 1;//每页显示的条数

var totalCountEm = 0;//总条数

var currPageEm = 1;//当前页码

//总页码
var totalPageEm = 1;


//已经画过div页码框的次数
var hasWriteSizeEm = 0;

//每页最多可画div页码框的个数
var maxSizeEm = 10;

//可画div页码框的次数
var writeSizeEm = 0;

//调用方法时，要看是否需要去画div页码框
checkShowPageEm(currPageEm);

//判断是否展示页码框
function checkShowPageEm(pageNumEm)
{
	
	if(totalCountEm > 0 && totalCountEm != null)
	{
	    totalPageEm = Math.ceil(totalCountEm / pageSizeEm);
		if(pageNumEm <= 0 || totalPageEm <= 0 || pageNumEm > totalPageEm)
		{
			$("#totalPageNumEm").text('');
			checkGoPageNumEm(pageNumEm);
			return false;
		}
		else
		{
		   $("#pageDivEm").show();
		   createDivPageFrameEm(pageNumEm);
		   $("#totalPageNumEm").text(totalPageEm);
		}
	}
	else
	{
	    $("#pageDivEm").hide();
	}
}

//是否展示分页div
function showPageDivEm(pageNumEm)
{
	//有且只有一页
	if(pageNumEm == 1 && totalPageEm == 1)
	{
		$("#pageDivEm").hide();
	}
	//首页，第一页  上一页和首页不显示
	else if(pageNumEm == 1 && totalPageEm != 1)
	{
		$("#homePageEm").hide();
		$("#prePageEm").hide();
		$("#nextPageEm").show();
		$("#endPageEm").show();
	}
	//中间页 全部显示
	else if(pageNumEm != 1 && totalPageEm != 1 && totalPageEm != pageNumEm)
	{
		$("#pageDivEm").show();
		$("#homePageEm").show();
		$("#prePageEm").show();
		$("#nextPageEm").show();
		$("#endPageEm").show();
	}
	//尾页，最后一页  下一页和尾页不显示
	else if(pageNumEm != 1 && totalPageEm == pageNumEm)
	{	
		$("#homePageEm").show();
		$("#prePageEm").show();
		$("#nextPageEm").hide();
		$("#endPageEm").hide();
	}
}

//画div框的个数
function createDivPageFrameEm(pageNumEm)
{

	showPageDivEm(pageNumEm);
	//跳页 从最中间显示
    hasWriteSizeEm = pageNumEm - 5;
	
	//是否在最前面几页
	if(hasWriteSizeEm == 0)
	{
	    hasWriteSizeEm = hasWriteSizeEm - 0;
	}
	//小于10页
	else if(hasWriteSizeEm < 0)
	{
	    hasWriteSizeEm = hasWriteSizeEm - hasWriteSizeEm;
	}
	//是否在最后面几页
	else if((totalPageEm - hasWriteSizeEm) < maxSizeEm)
	{
	    hasWriteSizeEm = hasWriteSizeEm - (maxSizeEm -(totalPageEm - hasWriteSizeEm));
	    if(hasWriteSizeEm < 0)
	    {
	        hasWriteSizeEm = hasWriteSizeEm - hasWriteSizeEm;;
	    }
	}
	//新画页数
	var newWriteSizeEm = 0;
	
	$("#pageNumDivEm").html("");
	
	for(var i = 1; i <= maxSizeEm;i++)
	{
		newWriteSizeEm = hasWriteSizeEm + i;
		
		$("#pageNumDivEm").append("<li><a id=\"goPageEm"+newWriteSizeEm+"\" href=\"javascript:void(0);\" onclick=\"goToPageEm('goPageEm"+newWriteSizeEm+"');\">"+newWriteSizeEm+"</a></li>");	
		//当前页不显示a标签可点的样式
		if(pageNumEm == newWriteSizeEm)
		{
			removeHrefEm("goPageEm"+pageNumEm);
		}
		//已经画过div页码框的次数 超过总页数就不去画了
		if(newWriteSizeEm == totalPageEm)
		{
			return false;
		}
		
	}
}

function goToPageEm(id)
{ 
    if(id == "homePageEm")
    {
        currPageEm = 1;
    }
    else if(id == "endPageEm")
    {
        currPageEm = totalPageEm;
    }
    else if(id == "prePageEm")
    {
        currPageEm = parseInt(currPageEm) - 1;
    }
    else if(id == "nextPageEm")
    {
    	currPageEm = parseInt(currPageEm) + 1;
    }
    else if(id == "goPageNumEm")
    {
        currPageEm = $("#"+id).val();
        //输入为空不跳转
        if(currPageEm == "")
    	{
    		return false;
    	}
    }
    else if(id == "goPageEm"+id.substring(8))
    {
    	currPageEm = parseInt(id.substring(8));
    }
    currPageEm = parseInt(currPageEm);
    $("#currPageEm").val(currPageEm);
    loadDetailDateEm(); 
}

/*分页enter按键处理函数*/
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		var currPageEm = $("#goPageNumEm").val();
        //输入为空不跳转
        if(currPageEm == "" || typeof(currPageEm) == "undefined")
    	{
    		return false;
    	}
	    $("#currPageEm").val(currPageEm);
	    loadDetailDateEm();
		break;
	default:
		break;
	}
}

var ua = window.navigator.userAgent;
//判断浏览器类型
if (ua.indexOf("MSIE") >= 1 || ua.indexOf("Chrome") >= 1){
	//禁止后退键  作用于IE、Chrome  
	document.onkeydown = pressKey;
}else if(ua.indexOf("Firefox") >= 1){
	//禁止后退键 作用于Firefox、Opera  
	document.onkeypress = pressKey;  
}else{
	document.onkeypress = pressKey;  
}

//检查跳页是否大于总页数
function checkGoPageNumEm(pageNumEm)
{
	//验证输入首字段不能是0
	$("#goPageNumEm").attr("value",pageNumEm.substring(0,1) == '0' ? pageNumEm = '' : pageNumEm = pageNumEm);
	if(pageNumEm > totalPageEm)
	{
	    $("#goPageNumEm").attr("value",totalPageEm);
	}
}
//动态添加A标签的href属性
function addHrefEm(id)
{
	$("#"+id).attr("href","javascript:void(0);");
	$("#"+id).attr("onClick",id+"()");
	$("#"+id).css("color","#5C85AD");	
}

//动态去除A标签的href属性
function removeHrefEm(id)
{
	$("#"+id).removeAttr("href","javascript:void(0);");
	$("#"+id).removeAttr("onClick","return false;");
	$("#"+id).addClass("on");
	$("#"+id).css("cursor","default");
}