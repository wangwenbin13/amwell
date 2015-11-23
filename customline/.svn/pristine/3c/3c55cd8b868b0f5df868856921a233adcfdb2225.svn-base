//validateFunction();
function init(){
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				$(this).removeClass("gray3").addClass("gray2");
			} 
			if(this.id == "lineName"){
				validateUserDefineClear(this.id)
			}
		}).blur(function(){  
			if($(this).val() == ""){
			    $(this).val(txt);  
			   $(this).removeClass("gray2").addClass("gray3");
			}
			if(this.id == "lineName")
			{
				isExitLineName(this.id);
				$(this).removeClass("gray3").addClass("gray2");
			}
		});  
	});
	//切换线路类型
	$("input[name=lineType]").click(function(){
	   $(this).removeClass("gray3").addClass("gray2");
	});

	//途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='lati'],[name='loni'],[name='stationName'],[name='startArea'],[name='endArea']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
		}).blur(function(){  
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val().indexOf("经度") == 0 || $(this).val().indexOf("纬度") == 0 || $(this).val() == "")
			{
				//屏蔽空值校验
				if(false){
					if($(this).val() == ""){
					    $(this).val(txt);  
					   $(this).removeClass("gray2").addClass("gray3");
					}
					createErrorTip(this.id,validateJson.isNotNull.tip);
				}
			}
			if($(this).val()!=""){
				if($(this).attr("name")=="lati"){
		               var lati= $(this).val();
		               if(lati<3||lati>53){
		            	   createErrorTip(this.id,"纬度不在中国范围内（3~53）");
		               }
					}
					if($(this).attr("name")=="loni"){
					   var loni= $(this).val();
		               if(loni<73||loni>135){
		            	   createErrorTip(this.id,"经度不在中国范围内（73~135）");
		               } 
					}
		    }
			
		}); 
	});
	$("#provinceCode").focus(function(){   //省份
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	}); 
	
	$("#cityCode").focus(function(){   //城市
		validateUserDefineClear(this.id);
		setSubmitDisale(false);
	}).blur(function(){  
		 selectValidate(this.id);
	});
}

//清除自定义验证提示语句
function validateUserDefineClear(id){
    $("#"+id+"ErrTip").hide();
    $("#"+id).parent().find(".onCorrect").remove();
}

//验证所有途经点
function validateAccessPoint()
{
	//途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='stationName']").each(function()
	{
		//屏蔽空值校验
		if(false){
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val() == "")
			{
				createErrorTip(this.id,validateJson.isNotNull.tip);
			}
		}
	});
}
function judgeForm(){
	 getthis.each(function(){
		var obj = this;
		checkByType(obj);
	});
	validateAccessPoint();
	selectValidate("provinceCode"); //省份
	selectValidate("cityCode");  //城市
	//验证未通过的计数
	var failValidateCount = $(document).find(".tipTable:visible").length;
	if(failValidateCount == 0 && flag == "success"){
		//验证提交 防止多次提交
		setSubmitDisale(false);
		return true;
	}else{
		//验证提交 防止多次提交
		setSubmitDisale(true);
	    return false;
	}
}
//跳转到不同的页面
function goDiffPage(){
      var flag = judgeForm();
	  if(!flag){
		return;
	  }
	  var stationNames = "";
	  $("input[name='stationName']").each(function(){
	        if($(this).val().length>0){
	          stationNames+=$(this).val()+",";
	        }
	  });
	  if(stationNames.length>0){
	     	stationNames = stationNames.substring(0,stationNames.length-1);
	     	if(checkRepetition(stationNames)){
			     parent.parent.showRturnTip("站点名称重复，请修改！",false); 
			     return;
	     	}
	  }
	  $("#addForm").ajaxSubmit({
		type : 'post',
		success : function(data) {
			if (data == "success") {
				    var lineType = $("input[name='lineType']:checked").val();
					if(lineType == "0"){
						//上下班
						$("#iframe_right", parent.parent.window.document).attr("src","../line/addClass.action");
					}else if(lineType == "1"){
						//旅游
						$("#iframe_right", parent.parent.window.document).attr("src","../line/addClassFreeLine.action");
					}
			}else if(data=="repetition"){
			       parent.parent.showRturnTip("线路名称重复,请修改线路名称",false); 	
			}else{
			       parent.parent.showRturnTip("保存失败,请填写相关字段数据",false); 
			}
		}
	});
}

//验证方法
function validateFunction(){
	var validateArr = [
	  	{"validateName":"stationNameStart:stationNameEnd:lineTime:lineKm","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip},//必填字段不为空判断
		{"validateName":"lineTime:lineKm","validateReg":validateJson.Intege.reg,"validateTip":validateJson.Intege.tip}//行程、里程、通用票价检验
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++){
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
}

//下拉框验证
function selectValidate(id){ 
	var selectIndex = $("#"+id).find("option:selected").index();
    if(selectIndex == 0){
        createErrorTip(id,validateJson.Select.tip);
    }else{
         validateUserDefinedSucc(id);
    }
}

//创建验证错误提示语句
function createErrorTip(id,tip){ 
    $("#"+id).parents("li").find(".onCorrect").remove();
    $("#"+id).parent().append("<span id='"+id+"Tip'></span>");
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
	if($("#"+id).prevAll("input").length > 0){
		if(id == "endArea"){
			$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+($("#"+id).prevAll("input").length*$("#"+id).prevAll("input").width()+$("#"+id).prevAll("input").length*15+12)+"px");
		}else{
			$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+($("#"+id).prevAll("input").length*$("#"+id).prevAll("input").width()+$("#"+id).prevAll("input").length*15)+"px");
		}
	}else if($("#"+id).prevAll("select").length > 0){
		$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+($("#"+id).prevAll("select").length*$("#"+id).prevAll("select").width()+$("#"+id).prevAll("select").length*15)+"px");
	}else{
		$("#"+id+"ErrTip").css("margin-left",$("#"+id).parents("li").children("span").eq(0).width()+"px");
	}
	$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");
}
//自定义验证通过
function validateUserDefinedSucc(id){
    $("#"+id).parents("li").find(".onCorrect").remove();
	$("#"+id).parent().append("<span id='"+id+"Tip' class='mt4'></span>");
    $("#"+id+"Tip").addClass('onCorrect');
	$("#"+id+"ErrTip").hide();
}

//清除自定义验证提示语句
function validateUserDefineClear(id){
    $("#"+id+"ErrTip").hide();
    $("#"+id).parent().find(".onCorrect").remove();
}

//标注站点 地图查询页面
function goAddMapPage(id){
	$("#topHide", parent.window.document).show();
    $("#leftHide", parent.window.document).show();
    $("#showPage").load("line/addLineMap.action?random="+Math.floor(Math.random()*10000+1),{id:id}); 
    $("#mainBody").show();
}

//中间随意添加途经点
function addAccessPointPreOrNext(index){
	//ul li的个数
	var count = parseInt(parseInt(index)+parseInt(1));
	$("#stationNameAccess"+parseInt(index)).parent("li").after("<li class='clearfix mt15 r-input32 line28'>"+
			"<span class='fl w106 t-r'></span><input type='text' name='stationName' disabled='true' maxLength='24' id='stationNameAccess"+count+"' class='fl r-input w222' placeholder='设置途径点"+count+"' onfocus='if (value==defaultValue){$(this).removeClass(\"gray3\").addClass(\"gray2\");}' onblur='if(!value){$(this).removeClass(\"gray2\").addClass(\"gray3\");}' onkeyup='if(/-/.test(this.value)){this.value=this.value.replace(/-/,\"\");}'/>"+
			"<span class='map-ico ml10 mr20' id='map"+count+"' title='添加站点位置' onclick='goAddMapPage(\"Access"+count+"\")'></span>"+
			"<select class='p3 mr10'><option>上车点</option><option>引导点</option><option>下车点</option></select>"+
			"<span id='linePoint"+count+"' count='"+count+"'>途径点"+count+"</span>"+
			"<a href='javascript:void(0)' class='f12 blue1 ml10 mr10' onclick='deleteAccessPoint(this)'>删除</a><a href='javascript:void(0)' class='f12 blue1' id='btnA"+count+"' onclick='addAccessPointPreOrNext("+count+")'>添加途径点</a></li>");
	//途经点的验证
	$("[name='stationNameStart'],[name='stationNameEnd'],[name='stationName']").each(function()
	{
		var txt = $(this).val();  
		$(this).focus(function(){  	
			if(txt == $(this).val()){
				//$(this).val(""); 
				$(this).removeClass("gray3").addClass("gray2");
			} 
			validateUserDefineClear(this.id);
		}).blur(function(){ 
			if($(this).val().indexOf("设置途径点") == 0 || $(this).val() == "")
			{
				//屏蔽空值校验
				if(false){
					if($(this).val() == ""){
					    $(this).val(txt);  
					   $(this).removeClass("gray2").addClass("gray3");
					}
					createErrorTip(this.id,validateJson.isNotNull.tip);
				}
			}
		}); 
	});
	setOtherAccessPointNotEqOne();
}

//删除途径点:
function deleteAccessPoint(obj){
	try{
		//本身的count数值
		var selfCount = $(obj).prev().attr("count");
		//要删除的li后面还剩几个li
		var surplusCount = $(obj).parent().nextAll("li").length - 5;
		for(var i = 0;i<surplusCount;i++){
			$("#linePoint"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"linePoint"+parseInt(parseInt(i)+parseInt(selfCount)),"count":parseInt(parseInt(i)+parseInt(selfCount))}).text("途径点"+parseInt(parseInt(i)+parseInt(selfCount)));
			$("#map"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"map"+parseInt(parseInt(i)+parseInt(selfCount)),"onclick":"goAddMapPage('picUrlAccess"+parseInt(parseInt(i)+parseInt(selfCount))+"')"});
			$("#btnA"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"btnA"+parseInt(parseInt(i)+parseInt(selfCount)),"onclick":"addAccessPointPreOrNext('"+parseInt(parseInt(i)+parseInt(selfCount))+"')"});
			if($("#stationNameAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).val().indexOf("设置途径点") == 0){
				$("#stationNameAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"stationNameAccess"+parseInt(parseInt(i)+parseInt(selfCount)),"value":"设置途径点"+parseInt(parseInt(i)+parseInt(selfCount))});
			}else{
				$("#stationNameAccess"+(parseInt(parseInt(i)+parseInt(selfCount)+1))).attr({"id":"stationNameAccess"+parseInt(parseInt(i)+parseInt(selfCount))});
			}
		}
		$(obj).parent().remove();
		setOtherAccessPointNotEqOne();
	}catch(e){
       alert(e);
	}
}
