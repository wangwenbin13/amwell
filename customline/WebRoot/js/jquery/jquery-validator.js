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
			$("#"+obj.id+"Tip").remove();
			$("#"+getthis.id+"ErrTip").remove();
		});
		
        this.each(function(){
			 if($(this).attr('check') != '')
			 {
				 //var txt = $(this).val();
				 $(this).focus(function()
				{
					var obj = this;
					//if(txt == $(obj).val()){
						//$(obj).val(""); 
					//} 
					$("#"+obj.id+"Tip").remove();
					$("#"+obj.id+"ErrTip").remove();
					setSubmitDisale(false);
					 
				}).blur(function(){
					//失去焦点开始验证
					var obj = this;
					checkByType(obj);
					//if($(obj).val() == ""){
					   // $(obj).val(txt);  
					//}
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
    	 if(flag)
		 {
			$(this).removeClass("btn1").addClass("btn1-gray");
		 }
		 else
		 {
			 $(this).removeClass("btn1-gray").addClass("btn1");
		 } 
    	 $(this).attr("disabled", flag);
	});
    $('input:text').each(function(){  
    	$(this).removeClass("gray3").addClass("gray2");
	});
}

//根据类型来验证
function checkByType(obj)
{
	var check = $(obj).attr('check');
	//提交时清空默认值
	var substr = $(obj).val().substring(0,3);
	if(substr == "请输入" || substr == "请选择" || substr == "设置起" || substr == "设置终")
	{
		$(obj).val("");
	}
	//展示正确图标 普通input框提示
	if($(obj).parent().is("li"))
	{
		$(obj).parent().find(".onCorrect").remove();
		$(obj).parent().append("<span id='"+obj.id+"Tip'></span>");
	}
	//展示正确图标  复杂input框提示 多层标签嵌套里面的input
	else if($(obj).parent().parent().is("li"))
	{
		$(obj).parent().parent().find(".onCorrect").remove();
		$(obj).parent().parent().append("<span id='"+obj.id+"Tip'></span>");
	}
	//展示正确图标 普通input框提示
	else if($(obj).parents("td").is("td"))
	{
		$(obj).parents("td").find(".onCorrect").remove();
		$(obj).parents("td").append("<span id='"+obj.id+"Tip'></span>");
	}
	else if($(obj).parents("p").is("p"))
	{
		$(obj).parents("p").find(".onCorrect").remove();
		$(obj).parents("p").append("<span id='"+obj.id+"Tip'></span>");
	}
	else if($(obj).parents("div").is("div"))
	{
		if($(obj).parents("span").next().is("span"))
		{
			
		}
		else
		{
			$(obj).parents("div").find(".onCorrect").remove();
			$(obj).parents("div").append("<span id='"+obj.id+"Tip'></span>");
		}
	}
	else
	{
		$("#"+obj.id+"Tip").remove();
		$(obj).after("<span id='"+obj.id+"Tip'></span>");
	}
	//展示错误提示语句
	if($(obj).attr('tip') != '')
	{
		$("#"+obj.id+"ErrTip").remove();

		//复杂input框 创建提示框
		if($(obj).parent().parent().is("li"))
		{
			$(obj).parent().parent().append('<div id="'+obj.id+'ErrTip" class="tipTable" style="margin-top:20px;"></div>');
			//根据父级标签下span文字说明，增加左边距
			if($(obj).parent().parent().find("span").is("span"))
			{
				
				$("#"+obj.id+"ErrTip").css("margin-top",$(obj).height()+"px");	
				//目前租赁业务登记/预定 先这样写，后期优化，客户名称和联系电话，担保人电话，企业邮编这4个字段的margin-left值
				if($(obj).prev().prev().is("input"))
				{
					var width = $(obj).parents("li").children("span").eq(0).width() + $(obj).parents("span").width() - $(obj).width();
					$("#"+obj.id+"ErrTip").css("margin-left",width-33+"px");
				}
				else if($(obj).prev().is("input"))
				{
					var width = $(obj).parents("li").children("span").eq(0).width() + $(obj).parents("span").width() - $(obj).width();
					$("#"+obj.id+"ErrTip").css("margin-left",width-5+"px");
				}
				else{
					if($(obj).parents("li").children("span").eq(0).width() == null)
					{
						$("#"+obj.id+"ErrTip").css("margin-left",$(obj).parent().parent().find("span").width()+"px");
					}
					else if($(obj).parents("li").children("p").find("span").eq(0).width() != null && $(obj).parents("li").children("p").find("span").eq(1).width() != null)
					{
						$("#"+obj.id+"ErrTip").css("margin-left",$(obj).parents("li").children("p").find("span").eq(0).width()+$(obj).parents("li").children("p").find("span").eq(1).width()+"px");
						if($(obj).parent("p").index() == 0)
						{
							$("#"+obj.id+"ErrTip").css("margin-top","-30px");	
						}
						else
						{
							$("#"+obj.id+"ErrTip").css("margin-top","8px");	
						}
					}
					else
					{
						$("#"+obj.id+"ErrTip").css("margin-left",$(obj).parents("li").children("span").eq(0).width()+"px");
					}
				}	
			}
		}
		else if($(obj).parent().is("p"))
		{
			$(obj).after('<div id="'+obj.id+'ErrTip" class="tipTable"></div>');
			$("#"+obj.id+"ErrTip").css("margin-top",$(obj).height()+"px");	
			if($(obj).parents("p").children("span").eq(1).width() != 0)
			{
				$("#"+obj.id+"ErrTip").css("margin-left",$(obj).parents("p").children("span").eq(0).width()+$(obj).parents("p").children("span").eq(1).width()+"px");
			}
			else
			{
				$("#"+obj.id+"ErrTip").css("margin-left",$(obj).parents("p").children("span").eq(0).width()+"px");
			}
		}
		else if($(obj).parents("div").is("div") && $(obj).parent("span").is("span"))
		{
			$(obj).after('<div id="'+obj.id+'ErrTip" class="tipTable"></div>');
			$("#"+obj.id+"ErrTip").css("margin-top",$(obj).height()+"px");	
			$("#"+obj.id+"ErrTip").css("margin-left",0);
		}
		else
		{
			$(obj).after('<div id="'+obj.id+'ErrTip" class="tipTable"></div>');
			//根据父级标签下span文字说明，增加左边距
			if($(obj).parent().find("span").is("span"))
			{
				if($(obj).parent().is("span"))
				{
					$("#"+obj.id+"ErrTip").css("margin-left","0px");
					$("#"+obj.id+"ErrTip").css("margin-top",$(obj).height()+"px");	
				}
				else
				{
					//下拉框验证
					if(check == "6"){
						$("#"+obj.id+"ErrTip").css("margin-left",$(obj).parent().find("span").width()+"px");
						$("#"+obj.id+"ErrTip").css("margin-top",$(obj).height()+10+"px");	
					}
					else{
						if($(obj).parents("td").children().eq(0).is("input") || $(obj).parent("div").children().eq(0).is("input"))
						{
							$("#"+obj.id+"ErrTip").css("margin-left","0px");
						}
						else
						{
							$("#"+obj.id+"ErrTip").css("margin-left",$(obj).parent().find("span").width()+"px");
						}
						$("#"+obj.id+"ErrTip").css("margin-top",$(obj).height()+"px");	
					}
				}
			}
		}
		$("#"+obj.id+"ErrTip").css({left:$.getLeft(this)+'px',top:$.getTop(this)+'px'});
		//下拉框、单选框、复选框
		if(check == "6" || check == "7" || check == "8")
		{
			$("#"+obj.id+"ErrTip").html(validateJson.Select.tip);
		}
		else
		{
			$("#"+obj.id+"ErrTip").html($(obj).attr('tip'));	
		}
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
				//$("#"+obj.id+"Tip").addClass('onCorrect');
				$("#"+obj.id+"ErrTip").hide();
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
		
		$("#"+obj.id+"ErrTip").text(validateJson.isNotNull.tip);
		$("#"+obj.id+"ErrTip").show();
	}
	else
	{
		/*if(obj.value.length == $(obj).attr('maxlength'))
		{
			$("#"+obj.id+"ErrTip").text("文字最多可输入"+$(obj).attr('maxlength'));
			$("#"+obj.id+"ErrTip").show();
		}
		else
		{
			$("#"+obj.id+"Tip").addClass('onCorrect');
			$("#"+obj.id+"ErrTip").hide();
		}*/
		$("#"+obj.id+"Tip").addClass('onCorrect');
		$("#"+obj.id+"ErrTip").hide();
	}
}

//身份证严格校验
function idCardStrengthVal(obj)
{
	if(isIdCard(obj.value))
	{
		$("#"+obj.id+"Tip").addClass('onCorrect');
		$("#"+obj.id+"ErrTip").hide();
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
			$("#"+obj.id+"Tip").addClass('onCorrect');
			$("#"+obj.id+"ErrTip").hide();
		}
		else
		{
			$("#"+obj.id+"Tip").removeClass('onCorrect');
			$("#"+obj.id+"ErrTip").show();
		}
	}
}

//手机和座机校验方法,不可以为空
function isPhoneOrTellVal(obj)
{
	if(isPhoneOrTell(obj.value))
	{
		$("#"+obj.id+"Tip").addClass('onCorrect');
		$("#"+obj.id+"ErrTip").hide();
	}
	else
	{
		$("#"+obj.id+"Tip").removeClass('onCorrect');
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
			$("#"+obj.id+"Tip").addClass('onCorrect');
			$("#"+obj.id+"ErrTip").hide();
		}
		else
		{
			$("#"+obj.id+"Tip").removeClass('onCorrect');
			$("#"+obj.id+"ErrTip").show();
		}	
	}
	else
	{
		$("#"+obj.id+"Tip").removeClass('onCorrect');
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
				$("#"+obj.id+"Tip").addClass('onCorrect');
				$("#"+obj.id+"ErrTip").hide();
			}
			else
			{
				$("#"+obj.id+"Tip").removeClass('onCorrect');
				$("#"+obj.id+"ErrTip").show();
			}
		}
		else
		{
			$("#"+obj.id+"Tip").removeClass('onCorrect');
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
				$("#"+obj.id+"Tip").addClass('onCorrect');	
				$("#"+obj.id+"ErrTip").hide();
			}
			else
			{
				$("#"+obj.id+"Tip").removeClass('onCorrect');
				$("#"+obj.id+"ErrTip").show();
			}	
		}
		else
		{
			$("#"+obj.id+"Tip").removeClass('onCorrect');
			$("#"+obj.id+"ErrTip").show();
		}	
	}
	//单选框、复选框
	if(type == "radio" || type == "checkbox")
	{
		if($("input[type='"+type+"']:checked").size()!=0)
		{
			$("#"+obj.id+"Tip").addClass('onCorrect');	
			$("#"+obj.id+"ErrTip").hide();
		}
		else
		{
			$("#"+obj.id+"Tip").remove();
			$("#"+obj.id+"Tip").removeClass('onCorrect');
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
			 $("#"+obj.id+"ErrTip").text(validateJson.DataCompare.tip);
			 $("#"+obj.id+"ErrTip").show();
			 return false;
		}
		else
		{	
			$("#"+obj.id+"Tip").addClass('onCorrect');	
			$("#"+obj.id+"ErrTip").hide();
		}	
		/* 输入的时间校验
		date0 = new Date($("#"+id0).val().replace(/\-/g, "\/"));
		date1 = new Date($("#"+id1).val().replace(/\-/g, "\/"));
		if(isDate(obj.value))
		{
			if ($("#"+id0).val() != "" && $("#"+id1).val() != "") {
				 if(date0 > date1)
				 {
					 $("#"+obj.id+"ErrTip").text(validateJson.DataCompare.tip);
					 $("#"+obj.id+"ErrTip").show();
					 return false;
				 }
				 else
				 {	
					$("#"+obj.id+"Tip").addClass('onCorrect');	
					$("#"+obj.id+"ErrTip").hide();
				}	
			}
		}
		else
		{
			$("#"+obj.id+"ErrTip").text(validateJson.DateTime.tip);
			$("#"+obj.id+"ErrTip").show();
		}*/
		
		
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
				if ($("#"+id0).val() != "" && $("#"+id1).val() != "") {
					 if ($("#"+id0).val() != $("#"+id1).val())
					 {
						 $("#"+obj.id+"ErrTip").text(validateJson.PassCompare.tip);
					 	 $("#"+obj.id+"ErrTip").show();
					 }
					 else
					 {
						$("#"+id0+"ErrTip").text('');
						$("#"+id1+"ErrTip").text('');
						$("#"+id0+"Tip").addClass('onCorrect');	
						$("#"+id0+"ErrTip").hide();
						$("#"+id1+"Tip").addClass('onCorrect');	
						$("#"+id1+"ErrTip").hide();
					}	
				}
			}
			else
			{
				$("#"+obj.id+"Tip").removeClass('onCorrect');
				$("#"+obj.id+"ErrTip").show();
			}	
		}
		else
		{
			$("#"+obj.id+"ErrTip").text(validateJson.Password.tip);
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
/*
function isIdCard(number){
	var area = {11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
	var idcard, Y, JYM;
	var S, M;
	if(typeof(number) == "undefined")
	{
		return false;	
	}
	var idcard_array = new Array();
	idcard_array = number.split("");
	//地区检验
	if (area[parseInt(number.substr(0, 2))] == null)
	{
		return false;	
	}
	//身份号码位数及格式检验
	switch (number.length) {
	case 15:
		if ((parseInt(number.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(number.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(number.substr(6, 2)) + 1900) % 4 == 0)) {
			//测试出生日期的合法性
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;				
		} else {
			//测试出生日期的合法性
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; 
		}
		if (ereg.test(number)) {
			return true;
		} else{
			return false;
		}
		break;
	case 18:
		/*18位身份号码检测
		出生日期的合法性检查
		闰年月:
		((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
		平年月日:
		((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))*/
		/*if (parseInt(number.substr(6, 4)) % 4 == 0 || (parseInt(number.substr(6, 4)) % 100 == 0 && parseInt(number.substr(6, 4)) % 4 == 0)) {
			//闰年出生日期的合法性正则表达式
			ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; 
		} else {
			//平年出生日期的合法性正则表达式
			ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; 
		}
		//测试出生日期的合法性
		if (ereg.test(number)) { 
			//计算校验位
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1])
			  + parseInt(  idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 
			  + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) 
			  + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 
			  + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 
			  + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y, 1); //判断校验位
			if (M == idcard_array[17]){
				return true; //检测ID的校验位	
			}
			else
			{
				return false;		
			}
		}else{
			return false;	
		}
		break;
		default:
			return false;
		break;
	 }
}*/
//end isIdCard
	
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