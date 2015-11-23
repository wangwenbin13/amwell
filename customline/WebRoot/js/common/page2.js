//------------------------------------------  分页显示的js  start currPage和totalPage必不可少-----------------------------------------


var pageSize = 1;//每页显示的条数

var totalCount = 1;//总条数

var currPage = 1;//当前页码

//总页码
var totalPage = 1;


//已经画过div页码框的次数
var hasWriteSize = 0;

//每页最多可画div页码框的个数
var maxSize = 10;

//可画div页码框的次数
var writeSize = 0;

//调用方法时，要看是否需要去画div页码框
checkShowPage(currPage);

//判断是否展示页码框
function checkShowPage(pageNum)
{
	if(totalCount > 0 && totalCount != null)
	{
	    totalPage = Math.ceil(totalCount / pageSize);
		if(pageNum <= 0 || totalPage <= 0 || pageNum > totalPage)
		{
			$("#totalPageNum").text('');
			checkGoPageNum(pageNum);
			return false;
		}
		else
		{
		   $("#pageDiv").show();
		   createDivPageFrame(pageNum);
		   $("#totalPageNum").text(totalPage);
		}
	}
	else
	{
	    $("#pageDiv").hide();
	}
}

//是否展示分页div
function showPageDiv(pageNum)
{
	//有且只有一页
	if(pageNum == 1 && totalPage == 1)
	{
		$("#pageDiv").hide();
	}
	//首页，第一页  上一页和首页不显示
	else if(pageNum == 1 && totalPage != 1)
	{
		$("#homePage").hide();
		$("#prePage").hide();
		$("#nextPage").show();
		$("#endPage").show();
	}
	//中间页 全部显示
	else if(pageNum != 1 && totalPage != 1 && totalPage != pageNum)
	{
		$("#pageDiv").show();
		$("#homePage").show();
		$("#prePage").show();
		$("#nextPage").show();
		$("#endPage").show();
	}
	//尾页，最后一页  下一页和尾页不显示
	else if(pageNum != 1 && totalPage == pageNum)
	{	
		$("#homePage").show();
		$("#prePage").show();
		$("#nextPage").hide();
		$("#endPage").hide();
	}
}

//画div框的个数
function createDivPageFrame(pageNum)
{

	showPageDiv(pageNum);
	//跳页 从最中间显示
    hasWriteSize = pageNum - 5;
	
	//是否在最前面几页
	if(hasWriteSize == 0)
	{
	    hasWriteSize = hasWriteSize - 0;
	}
	//小于10页
	else if(hasWriteSize < 0)
	{
	    hasWriteSize = hasWriteSize - hasWriteSize;
	}
	//是否在最后面几页
	else if((totalPage - hasWriteSize) < maxSize)
	{
	    hasWriteSize = hasWriteSize - (maxSize -(totalPage - hasWriteSize));
	    if(hasWriteSize < 0)
	    {
	        hasWriteSize = hasWriteSize - hasWriteSize;;
	    }
	}
	//新画页数
	var newWriteSize = 0;
	
	$("#pageNumDiv").html("");
	
	for(var i = 1; i <= maxSize;i++)
	{
		newWriteSize = hasWriteSize + i;
		$("#pageNumDiv").append("<li><a id=\"goPage"+newWriteSize+"\" href=\"javascript:void(0);\" onclick=\"goToPage('goPage"+newWriteSize+"');\">"+newWriteSize+"</a></li>");	
		//当前页不显示a标签可点的样式
		if(pageNum == newWriteSize)
		{
			removeHref("goPage"+pageNum);
		}
		//已经画过div页码框的次数 超过总页数就不去画了
		if(newWriteSize == totalPage)
		{
			return false;
		}
		
	}
}

function goToPage(id)
{ 
	if(id == "homePage")
    {
        currPage = 1;
    }
    else if(id == "endPage")
    {
        currPage = totalPage;
    }
    else if(id == "prePage")
    {
        currPage = parseInt(currPage) - 1;
    }
    else if(id == "nextPage")
    {
        currPage = parseInt(currPage) + 1;
    }
    else if(id == "goPageNum")
    {
        currPage = parseInt($("#"+id).val());
        //输入为空不跳转
        if(currPage == "")
    	{
    		return false;
    	}
    }
    else if(id == "goPage"+id.substring(6))
    {
        currPage = parseInt(id.substring(6));
    }
    currPage = parseInt(currPage);
    $("#currPage").val(currPage);
    $("#checkAllBox").attr("checked",false)
    loadDetailDate(); 
}

/*分页enter按键处理函数*/
function pressKey(evt) {
	evt = evt ? evt : window.event;
	var keyCode = evt.which ? evt.which : evt.keyCode;
	switch (keyCode) {
	/*ok*/
	case 13:
		var currPage = parseInt($("#goPageNum").val());
        //输入为空不跳转
        if(currPage == "" || typeof(currPage) == "undefined")
    	{
    		return false;
    	}
	    $("#currPage").val(currPage);
	    loadDetailDate();
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
function checkGoPageNum(pageNum)
{
	//验证输入首字段不能是0
	$("#goPageNum").attr("value",pageNum.substring(0,1) == '0' ? pageNum = '' : pageNum = pageNum);
	if(pageNum > totalPage)
	{
	    $("#goPageNum").attr("value",totalPage);
	}
}
//动态添加A标签的href属性
function addHref(id)
{
	$("#"+id).attr("href","javascript:void(0);");
	$("#"+id).attr("onClick",id+"()");
	$("#"+id).css("color","#5C85AD");	
}

//动态去除A标签的href属性
function removeHref(id)
{
	$("#"+id).removeAttr("href","javascript:void(0);");
	$("#"+id).removeAttr("onClick","return false;");
	$("#"+id).addClass("on");
	$("#"+id).css("cursor","default");
}