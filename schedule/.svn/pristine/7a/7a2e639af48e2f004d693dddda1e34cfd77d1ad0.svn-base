//根据原有验证框架进行改进
//使用时候需要给要增加验证的标签增加check属性
//当check="2"的时候,直接按照reg绑定的正则表达式进行验证.
//当check="11"的时候,判断不为空验证,没有验证格式的校验，例如姓名等
//当check="1"的时候,允许输入为空,如果输入数据了就按reg属性绑定的正则表达进行验证.
//当check="3,6,7,8"的时候,上传、下拉框、单选框、复选框，图片和文件上传
//当check="4,5"的时候,2个日期校验、2个密码校验
//当check="9"的时候,身份证严格校验
//当check="10"的时候,手机和电话号码一起校验，不可以为空
//当check="11"的时候,不为空的方法校验,没有任何验证规则
//当check="12"的时候,手机和电话号码一起校验，可以为空

var getthis;
//获得所有需要验证的标签
(function($){
	$(document).ready(function(){
		$('select[tip],select[check],input[tip],input[check],textarea[tip],textarea[check]').tooltip();
	});
})(jQuery);

(function($) {
    $.fn.tooltip = function(options){
		getthis = this;
        var opts = $.extend({}, $.fn.tooltip.defaults, options);
		
		//移动鼠标隐藏刚创建的提示框
        $(document).mouseout(function(){
			var obj = this;
			$("#"+getthis.id+"ErrTip").remove();
		});
		
        this.each(function(){
			 if($(this).attr('check') != '')
			 {
				 $(this).focus(function()
				{
					var obj = this;
					$("#"+obj.id+"ErrTip").remove();
					//还原错误框input框颜色
					$(obj).css("background","#f6f5f5");
					setSubmitDisale(false);
					 
				}).blur(function(){
					//失去焦点开始验证
					var obj = this;
					checkByType(obj);
				});
			} 
        });
		if(opts.onsubmit)
        {
           
		}

    };

    $.extend({
        getWidth : function(object) {
            return object.offsetWidth;
        },
        getLeft : function(object) {
            var go = object;
            var oParent,oLeft = go.offsetLeft;
            while(go.offsetParent!=null) {
                oParent = go.offsetParent;
                oLeft += oParent.offsetLeft;
                go = oParent;
            }
            return oLeft;
        },
        getTop : function(object) {
            var go = object;
            var oParent,oTop = go.offsetTop;
            while(go.offsetParent!=null) {
                oParent = go.offsetParent;
                oTop += oParent.offsetTop;
                go = oParent;
            }
            return oTop + $(object).height()+ 5;
        },
        onsubmit : true
    });
    $.fn.tooltip.defaults = { onsubmit: true };
})(jQuery);


//验证提交 防止多次提交  flag 为true表示按钮不可点击，false表示按钮可点击
function setSubmitDisale(flag)
{
    $('input[type=submit], input[type=button]').each(function() {
    	 $(this).attr("disabled", flag);
	});
}

//根据类型来验证
function checkByType(obj)
{
	var check = $(obj).attr('check');
	//提交时清空默认值
	var substr = $(obj).val().substring(0,3);
	if(substr == "请输入" || substr == "请选择")
	{
		$(obj).val("");
	}
	//展示错误提示语句
	if($(obj).attr('tip') != '')
	{
		$("#"+obj.id+"ErrTip").remove();
		$(obj).parent().append('<div class="tipTable fl" id="'+obj.id+'ErrTip"><span class="error mt5 ml4"></span><span class="red2" id="'+obj.id+'ErrTipText"></span></div>');
		//下拉框、单选框、复选框
		if(check == "6" || check == "7" || check == "8")
		{
			$("#"+obj.id+"ErrTip").show();	
			$("#"+obj.id+"ErrTipText").html(validateJson.Select.tip);
		}
		else
		{
			$("#"+obj.id+"ErrTip").show();	
			$("#"+obj.id+"ErrTipText").text($(obj).attr('tip'));	
		}
		//增加错误框input框颜色
		$(obj).css("background","#fcf0ee");
	}

	switch(check)
	{
		//可以为空
		case "1":
			byCheckValidate("1",obj);
			break;
		//必填字段
		case "2":
			byCheckValidate("2",obj);
			break;
		//图片上传、图片和文件上传
		case "3":
			byCheckValidate("3",obj,"file");
			break;
		//两个日期比较 起始时间必须小等于终止时间
		case "4":
			byCheckValidate("4",obj,"dateCompare");
			break;
		//密码比较是否相同
		case "5":
			byCheckValidate("5",obj,"passCompare");
			break;
		//下拉框
		case "6":
			byCheckValidate("6",obj,"select");
			break;
		//单选框
		case "7":
			byCheckValidate("7",obj,"radio");
			break;
		//复选框
		case "8":
			byCheckValidate("8",obj,"checkbox");
			break;
		//身份证严格校验
		case "9":
			byCheckValidate("9",obj,"idCardStrength");
			break;
		//手机和电话号码一起校验，不可以为空
		case "10":
			byCheckValidate("10",obj,"isPhoneOrTell");
			break;
		//不为空的方法校验
		case "11":
			isNotNull(obj);
			break;
		//手机和电话号码一起校验,可以为空
		case "12":
			byCheckValidate("12",obj,"isPhoneOrTellNull");
			break;
		default:
			byCheckValidate("1",obj);
			break;
	}
}

//根据不同类型进行验证
function byCheckValidate(check,obj,type)
{
	switch(check)
	{
		//可以为空
		case "1":
			if($(obj).attr('value') == "")
			{
				$("#"+obj.id+"ErrTip").hide();
				//还原错误框input框颜色
				$(obj).css("background","#f6f5f5");
			}
			else
			{	
				mustValidate(obj);
			}	
			break;
		//必填字段
		case "2":
			mustValidate(obj);	
			break;
		//图片上传、下拉框、单选框、复选框、图片和文件上传
		case "3":
		case "6":
		case "7":
		case "8":
			mustValidateByType(obj,type,check);	
			break;
		//日期比较、密码确认
		case "4":
		case "5":
			mustValidateCompare(obj,type);
			break;
		//身份证严格校验
		case "9":
			idCardStrengthVal(obj);
			break;
		//手机和电话号码一起校验，不可以为空
		case "10":
			isPhoneOrTellVal(obj);
			break;
		//手机和电话号码一起校验，可以为空
		case "12":
			isPhoneOrTellNull(obj);
			break;
		default:
			break;
	}
}
//ie8 trim方法不能获取，只能重写
String.prototype.trim = function() {
  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}
//不为空校验
function isNotNull(obj)
{
	//如果输入全部都是空格则表示空字符串
	if($(obj).attr('value').trim() == "")
	{
		
		$("#"+obj.id+"ErrTipText").text(validateJson.isNotNull.tip);
		$("#"+obj.id+"ErrTip").show();
	}
	else
	{
		if(obj.id == "drivername")
		{
			if($(obj).attr('value').length > 12 || $(obj).attr('value').length < 2)
			{
				$("#"+obj.id+"ErrTipText").text("司机姓名最少2个字");
				$("#"+obj.id+"ErrTip").show();
			}
			else
			{
				$("#"+obj.id+"ErrTip").hide();
				//还原错误框input框颜色
				$(obj).css("background","#f6f5f5");
			}
		}
		else
		{
			$("#"+obj.id+"ErrTip").hide();
			//还原错误框input框颜色
			$(obj).css("background","#f6f5f5");
		}
	}
}

//身份证严格校验
function idCardStrengthVal(obj)
{
	if(isIdCard(obj.value))
	{
		$("#"+obj.id+"ErrTip").hide();
		//还原错误框input框颜色
		$(obj).css("background","#f6f5f5");
	}
	else
	{
		
		$("#"+obj.id+"ErrTip").show();
	}
}

//手机和座机校验方法，可以为空
function isPhoneOrTellNull(obj)
{
	//如果输入全部都是空格则表示空字符串
	if($(obj).attr('value').trim() != "")
	{
		if(isPhoneOrTell(obj.value))
		{
			$("#"+obj.id+"ErrTip").hide();
			//还原错误框input框颜色
			$(obj).css("background","#f6f5f5");
		}
		else
		{
			$("#"+obj.id+"ErrTip").show();
		}
	}
}

//手机和座机校验方法,不可以为空
function isPhoneOrTellVal(obj)
{
	if(isPhoneOrTell(obj.value))
	{
		$("#"+obj.id+"ErrTip").hide();
		//还原错误框input框颜色
		$(obj).css("background","#f6f5f5");
	}
	else
	{
		$("#"+obj.id+"ErrTip").show();
	}
}

//手机和固定座机电话一起校验
function isPhoneOrTell(number)
{
	var isMobile = new RegExp(validateJson.Phone.reg);
	var isTell = new RegExp(validateJson.Tell.reg);
	if(isMobile.test(number) || isTell.test(number))
	{
		 return true;	
	}
	else
	{
		return false;	
	}
}
//根据校验规则，是否校验成功来展示不同的图片样式
//必填字段校验方法
function mustValidate(obj)
{
	var thisReg = new RegExp($(obj).attr('reg'));
	if(typeof(obj.value) != "undefined")
	{
		if(thisReg.test(obj.value))
		{
			$("#"+obj.id+"ErrTip").hide();
			//还原错误框input框颜色
			$(obj).css("background","#f6f5f5");
		}
		else
		{
			$("#"+obj.id+"ErrTip").show();
		}	
	}
	else
	{
		$("#"+obj.id+"ErrTip").show();
	}
}

//根据校验规则及input框类型，是否校验成功来展示不同的图片样式,下拉框及上传框等报错或正确样式不能放到框中，直接放到框后
//图片上传、下拉框、单选框、复选框校验方法、图片和文件上传
function mustValidateByType(obj,type,check)
{
	var thisReg = new RegExp($(obj).attr('reg'));
	
	//文件上传
	if(type == "file")
	{
		if(thisReg.test(obj.value))
		{
			if(checkFileSize(obj))
			{
				$("#"+obj.id+"ErrTip").hide();
				//还原错误框input框颜色
				$(obj).css("background","#f6f5f5");
			}
			else
			{
				$("#"+obj.id+"ErrTip").show();
			}
		}
		else
		{
			$("#"+obj.id+"ErrTip").show();
		}
	}
	//下拉框
	if(type == "select")
	{
		if(typeof(obj.value) != "undefined")
		{
			if(obj.value != "" && obj.value != "-1")
			{
				$("#"+obj.id+"ErrTip").hide();
				//还原错误框input框颜色
				$(obj).css("background","#f6f5f5");
			}
			else
			{
				$("#"+obj.id+"ErrTip").show();
			}	
		}
		else
		{
			$("#"+obj.id+"ErrTip").show();
		}	
	}
	//单选框、复选框
	if(type == "radio" || type == "checkbox")
	{
		if($("input[type='"+type+"']:checked").size()!=0)
		{
			$("#"+obj.id+"ErrTip").hide();
			//还原错误框input框颜色
			$(obj).css("background","#f6f5f5");
		}
		else
		{
			$("#"+obj.id+"ErrTip").show();
		}
	}	
}
          
//根据校验规则判断输入值是否合理，并且比价下2个值
//日期比较、密码确认校验方法
function mustValidateCompare(obj,type)
{
	var thisReg = new RegExp($(obj).attr('reg'));
	var id0 = "";
	var id1 = "";
	var date0 = "";
	var date1 = "";
	//日期比较
	if(type == "dateCompare")
	{
		id0 = dateIdArr.split(",")[0];
		id1 = dateIdArr.split(",")[1];
	    var sdate = new Date($("#"+id0).val().replace(/-/g,"/"));  //把所有的“-”转成“/”  
	    var edate = new Date($("#"+id1).val().replace(/-/g,"/"));  //把所有的“-”转成“/”  
	    
	    //日期选择时间校验
    	if(Date.parse(edate) < Date.parse(sdate))
		{
			 $("#"+obj.id+"ErrTipText").text(validateJson.DataCompare.tip);
			 $("#"+obj.id+"ErrTip").show();
			 return false;
		}
		else
		{	
			$("#"+obj.id+"ErrTip").hide();
			//还原错误框input框颜色
			$(obj).css("background","#f6f5f5");
		}	
		
	}
	//密码确认校验方法
	else if(type == "passCompare")
	{
		id0 = passIdArr.split(",")[0];
		id1 = passIdArr.split(",")[1];
		if(typeof(obj.value) != "undefined" && obj.value != "")
		{
			if(thisReg.test(obj.value))
			{
				$("#"+obj.id+"ErrTipText").text('');
				$("#"+obj.id+"ErrTip").hide();
				//还原错误框input框颜色
				$("#"+obj.id+"ErrTip").prev().css("background","#f6f5f5");
				
				if ($("#"+id0).val() != "" && $("#"+id1).val() != "") {
					 if ($("#"+id0).val() != $("#"+id1).val())
					 {
						 $("#"+obj.id+"ErrTipText").text(validateJson.PassCompare.tip);
					 	 $("#"+obj.id+"ErrTip").show();
					 }
					 else
					 {
						$("#"+id0+"ErrTipText").text('');
						$("#"+id1+"ErrTipText").text('');	
						$("#"+id0+"ErrTip").hide();
						$("#"+id1+"ErrTip").hide();
						//还原错误框input框颜色
						$("#"+id0+"ErrTip").prev().css("background","#f6f5f5");
						$("#"+id1+"ErrTip").prev().css("background","#f6f5f5");
					 }
				}
			}
			else
			{
				$("#"+obj.id+"ErrTip").show();
			}	
		}
		else
		{
			$("#"+obj.id+"ErrTipText").text(validateJson.Password.tip);
			$("#"+obj.id+"ErrTip").show();
		}	
	}
}


//***************************************************************************************************************************************************
//利用JQuery功能对标签属性设置表达式
//传入的标签ID组必须为"name1:name2:name3"中间用':'分隔.


//对所有需要整数验证的标签进行设置正则表达式
function setValidateCheck(validatorString,reg,tip)
{
	var validatorStrings = "";
	if(validatorString != "")
	{
		validatorStrings = validatorString.split(":");
		for(i=0;i<validatorStrings.length;i++)
		{
			$("#"+validatorStrings[i]).attr("reg",reg);
			$("#"+validatorStrings[i]).attr("tip",tip);
		}
	}
}

//判断文件上传大小
function checkFileSize(obj_file)
{
	var maxsize = 2*1024*1024;//2M
	var  browserCfg = {};
	var ua = window.navigator.userAgent;
	var filesize = 0;
	//判断浏览器类型
	if (ua.indexOf("MSIE") >= 1){
		browserCfg.ie = true;
	}else if(ua.indexOf("Firefox") >= 1){
		browserCfg.firefox = true;
	}else if(ua.indexOf("Chrome") >= 1){
		browserCfg.chrome = true;
	}
 	 if(browserCfg.firefox || browserCfg.chrome ){
          filesize = obj_file.files[0].size;
     }else if(browserCfg.ie){
		  var obj_img = $("#tempimg");
		  obj_img.dynsrc = obj_file.value;
		  filesize = obj_img.fileSize;
     }else{
        return false;
     }
     if(filesize == -1){
		  return false;
     }else if(filesize > maxsize){
		  return false;
    }else{
        return true;
    }
}
//身份证号校验
function isIdCard(number){
	if(typeof(number) == "undefined")
	{
		return false;	
	}
	//身份号码位数及格式检验
	//^[0-9]{6}[1-2]{1}[0-9]{3}[0-1]{1}[0-9]{1}[0-3]{1}[0-9]{1}[0-9]{3}[xX0-9]{1}$
	var ereg = new RegExp("^[0-9]{6}[1-9]{1}[0-9]{3}[0-1]{1}[0-9]{1}[0-3]{1}[0-9]{1}[0-9]{3}[xX0-9]{1}$");
	if (ereg.test(number)) {
		return true;
	} else{
		return false;
	}
}
	
//日期校验
function isDate(op){
	if(typeof(op) == "undefined")
	{
		return false;	
	}
	var formatString = "ymd";
	var m, year, month, day;
	switch(formatString){
	case "ymd" :
		m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
		if(m == null ) return false;
		day = m[6];
		month = m[5]*1;
		year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
	break;
	case "dmy" :
		m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
		if(m == null ) return false;
		day = m[1];
		month = m[3]*1;
		year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
	break;
	default :
		break;
	}
	if(!parseInt(month)) return false;
	month = month == 0 ? 12 : month;
	var date = new Date(year, month-1, day);
	return (typeof(date) == "object" && year == date.getFullYear() && month == (date.getMonth()+1) && day == date.getDate());
}//end isDate

function GetFullYear(y){
	return ((y < 30 ? "20" : "19") + y)|0;
}  
//***************************************************************************************************************************************************