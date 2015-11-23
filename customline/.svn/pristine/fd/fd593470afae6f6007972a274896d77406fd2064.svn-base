<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../resource.jsp"/>
<!-- 系统设置-添加权限管理 -->
<form id="addForm" action="" method="post">
<div class="pop black1" style="width:800px" id="popAddPage">
	<div class="pop-t">
    	<div class="pop-t-l fl fw white">添加权限</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" class="pop-close fr mt4 mr4" onclick="closePopAddPage();"></a></div>
    </div>
    <div class="pop-main p10">
    	
        <ul class="pop-lilist mt15 clearfix">	
        	<li class="w49p"><span class="t-r w25p fl line26">权限名称：<em class="red1 mr4">*</em></span>
        		<s:textfield cssClass="r-input w67p fl gray3" name="sysPermissionVo.name" theme="simple" id="name" value="请输入权限名称" maxlength="50" check="11"/>
        	</li>
            <li class="w49p"><span class="t-r w25p fl line26">路径url：<em class="red1 mr4">*</em></span>
          	  <s:textfield cssClass="r-input w67p fl gray3" name="sysPermissionVo.url" theme="simple" value="请输入路径url" id="url" maxlength="150" check="11"/>
            </li>           
            <li class="w49p"><span class="t-r w25p fl line26">图片url：<em class="red1 mr4">*</em></span>
           		<s:textfield cssClass="r-input w67p fl gray3" name="sysPermissionVo.iconUrl" theme="simple" id="iconUrl" maxlength="120" value="请输入图片路径url" check="11"/>
            </li>
            <li class="w49p"><span class="t-r w25p fl line26">选择父权限：<em class="red1 mr4">*</em></span>
           		<s:select list="sysPermissions" listKey="code" listValue="name" cssClass="w35p p3 fl" name="sysPermissionVo.fid" id="fid"></s:select>
           </li>  
            <li class="w49p"><span class="t-r w25p fl line26">系统名称：<em class="red1 mr4">*</em></span>
            	 <s:select list="#{-1:'请选择系统',1:'调度平台',0:'运营平台'}" listKey="key" listValue="value"  cssClass="w35p p3 fl" name="sysPermissionVo.sysType" id="sysType"></s:select>
            </li>
            <li class="w49p"><span class="t-r w25p fl line26">权限编码：<em class="red1 mr4">*</em></span>
            	<s:textfield cssClass="r-input w67p fl gray3" name="sysPermissionVo.code" theme="simple" value=""  id="code" maxlength="50" check="11"/>
            	</li>         
        </ul>
        <p class="t-c mt10 mb20"><input type="button" class="display-ib btn1 white mr40 f14" id="toAdd" value="添&nbsp;加"/>
        <input type="reset" class="display-ib btn1 white f14" value="取&nbsp;消" onclick="closePopAddPage()"></p>
    </div>
</div>
</form>

<script type="text/javascript">
//系统设置-添加管理员-查看角色   展示查看角色弹出层页面

function showPopViewPage()
{
    $("#popAddPage").hide();
    $("#mainBody").hide();
    $("#showPage1").load("../permission/toParentPermissionPage?random="+Math.floor(Math.random()*10000+1)); 
    $("#mainBody1").show();
}


function closePopAddPage()
{
    $("#popAddPage").hide();
    $("#mainBody").hide();
    $("#topHide", window.parent.parent.document).hide();
    $("#leftHide", window.parent.parent.document).hide();
}



$(function(){
	//清除input框默认值  
	clearInputDefaultVal();
	//验证方法
	validateFunction();
	$("#sysId").change(function(){
	    validateUserDefined();
	});
});
//清除input框默认值 
function clearInputDefaultVal()
{
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			$(this).removeClass("gray3").addClass("gray2");
			if(txt == $(this).val()){
				$(this).val(""); 
			} 
			setSubmitDisale(false);
		}).blur(function(){  
			if($(this).val() == ""){
			    $(this).val(txt);  
			    $(this).removeClass("gray2").addClass("gray3");
			}
			
		});  
	});
}
//验证权限名称是否已存在
$("#name").focus(function(){
	validateUserDefineClear("name");
	setSubmitDisale(false);
}).blur(function(){
	//调用验证重复的方法
	validatePowerIsExist();
});

//验证方法
function validateFunction()
{
    //validateArr需要验证的数组，validateName代表input框id,多个input间id用冒号：隔开；validateReg代表验证的规则；validateJson代表验证提示语句
	var validateArr = [{"validateName":"url:iconUrl:fid:code:sysType","validateReg":validateJson.isNotNull.reg,"validateTip":validateJson.isNotNull.tip}//必填字段不为空判断
	];
	//循环调用验证方法
	for(var i = 0;i<validateArr.length;i++)
	{
		//setValidateCheck(input框id,验证的规则,验证提示语句)
		setValidateCheck(validateArr[i].validateName,validateArr[i].validateReg,validateArr[i].validateTip);
	}
	
	//一个form表单提交验证
	$('#toAdd').click( function () {
		 getthis.each(function(){
			var obj = this;
			checkByType(obj);
		});
		//自定义验证 
		validateUserDefined();
		//验证角色名称是否重复
		validatePowerIsExist();
		//failValidateCount = 0  代表页面全部验证通过
		//验证未通过的计数
	    var failValidateCount = $(document).find(".tipTable:visible").length;
		if(failValidateCount == 0 && $("#sysType").val() != "-1")
		{
			//验证提交 防止多次提交
			setSubmitDisale(false);
			//调用ajax添加权限
			ajaxAddPermission();
			
			
		}
		else
		{
			//验证提交 防止多次提交
			setSubmitDisale(true);
			
		    return false;
		}
	});
} 

//提交表单
function ajaxAddPermission(){

	$("#addForm").ajaxSubmit({
		type : 'post',
		data:{},
		url : "../permission/add.action?random="+Math.floor(Math.random()*10000+1),
		dataType:"text",
		success : function(data) {
			
			if("true" == data){
				closePopAddPage();
				alert("添加成功");
				window.location.href='../permission/toList.action';
			}
			if("false" == data){
				alert("添加失败");
			}
		}
	});
}
//页面自定义验证方法
function validateUserDefined()
{
    //请选择系统
    var sysId = $("#sysType").val();
	if(sysId == "-1")
	{
	    createErrorTip("sysType",validateJson.Select.tip);
	}
	else
	{
	    validateUserDefinedSucc("sysType");
	    //验证提交 防止多次提交
		setSubmitDisale(false);
	}
}

//验证权限名称是否重复
function validatePowerIsExist(){
	var name = $("#name").val();
	
	//判断菜单名称是否为空
	if("" == name){
		createErrorTip("name","菜单名称不能为空");
		return;
	}
	
	if("请输入权限名称" == name){
		createErrorTip("name","菜单名称不能为空");
		return;
	}
	
	
	$.ajax({
		url:'../permission/permissionNameIsExist.action?random="+Math.floor(Math.random()*10000+1)',		
		cache:false,
		data:{"permissionName":name},
		type: 'POST',	
		dataType:"text",
		success:function(data){	
			if(null != data){
				if("" != data){
					if("true" == data){
						createErrorTip("name","菜单名称已存在");
					}
					if("false" == data){
						validateUserDefinedSucc("name");
					}
				}
			}
			
		}
	});
	
	//createErrorTip("name","该菜单名称已存在");
}


//创建验证错误提示语句
function createErrorTip(id,tip)
{ 
    $("#"+id).parent().find(".onCorrect").remove();
    $("#"+id).parent().parent().append("<span id='"+id+"Tip'></span>");
	$("#"+id+"ErrTip").remove();
	$("#"+id).after("<div id='"+id+"ErrTip' class='tipTable'></div>");
	$("#"+id+"ErrTip").css("margin-left",$("#"+id).parent().find("span").width()+"px");	
	if(id == "name")
	{
		$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+"px");	
	}
	else
	{
		$("#"+id+"ErrTip").css("margin-top",$("#"+id).height()+10+"px");	
	}
	$("#"+id+"ErrTip").show();
	$("#"+id+"ErrTip").html(tip);
}

//自定义验证通过
function validateUserDefinedSucc(id)
{
    $("#"+id).parent().find(".onCorrect").remove();
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
</script>
