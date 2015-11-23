<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 系统设置-管理员详情 -->
<form action="" method="post">
<div class="pop black1" id="popDetailPage" style="width:840px">
	<div class="pop-t">
    	<div class="pop-t-l fl fw white">管理员详情</div>
        <div class="pop-t-r fr"><a href="javascript:void(0);" class="pop-close fr mt4 mr4" onclick="closePopDetailPage();"></a></div>
    </div>
    <div class="pop-main p10">
        <ul class="pop-lilist mt15 clearfix line26">	
        	<li class="w49p graybg"><span class="t-r w100 fl">用户姓名：<em class="red1 mr4">*</em></span> 
        		<s:textfield cssClass="fl r-input w50p gray3" name="sysAdminVo.userName" theme="simple" id="userName" maxlength="64" check="11" readonly="true"/>
        	</li>
            <li class="w49p graybg"><span class="t-r w100 fl">登录名：<em class="red1 mr4">*</em></span>
            	<s:textfield cssClass="fl r-input w50p gray3" name="sysAdminVo.loginName" theme="simple" id="loginName" maxlength="64" readonly="true"/>
            </li>
            <li class="w49p graybg"><span class="t-r w100 fl">角色：<em class="red1 mr4">*</em></span>
            	<s:select list="sysAdminVo.sysRoleList" listKey="roleId" listValue="roleName" cssClass="fl r-input w50p gray3" name="sysAdminVo.roleId" id="roleId" disabled="true" ></s:select>
            </li>
	        <li class="w49p graybg"><span class="t-r w100 fl">账号状态：<em class="red1 mr4">*</em></span>
	        	<s:select list="#{1:'有效',0:'无效'}" listKey="key" listValue="value"  cssClass="fl r-input w50p gray3" name="sysAdminVo.status" id="status" disabled="true"></s:select>
	        </li>
	        <li class="w49p graybg"><span class="t-r w100 fl">联系电话：</span>
            	<s:textfield cssClass="fl r-input w50p gray3" name="sysAdminVo.telephone" theme="simple" id="telephone" maxlength="16" check="1" readonly="true"/>
            </li>
            <li style="width:99%;" class=" graybg"><span class="t-r w100 fl">备注：</span>
                 <s:textarea cssClass="r-input more-show" theme="simple" maxlength="200" onKeyUp="if(this.value.length > 200) this.value=this.value.substr(0,200)" name="sysAdminVo.remark" id="remark" readonly="true">
                </s:textarea>
            </li>
             <li style="width:99%;"><span class="t-r w100 fl">省份：</span>
                <s:select list="proSysAreas" listKey="arearCode" listValue="areaName" cssClass="p3 w51p" id="provinceCode" name="sysAdminVo.provinceCode" disabled="true" onchange="loadCity(this.value)"></s:select>
            </li>
            <li style="width:99%;"><span class="t-r w100 fl">城市：</span>
                <select id="cityCode" name="sysAdminVo.cityCode" class="p3 w51p" disabled="true"></select>
            </li>
        </ul>
        <p class="t-c mt10 mb20"><input type="submit" onclick="closePopDetailPage();" class="display-ib btn1 white mr40 f14"  value="返&nbsp;回"/>
    </div>
</div>
</form>
<script type="text/javascript" src="../js/line/common_util.js"></script>
<script type="text/javascript">
//关闭详情弹出层页面
function closePopDetailPage()
{
    $("#popDetailPage").hide();
    $("#mainBody").hide();
    $("#topHide", window.parent.document).hide();
    $("#leftHide", window.parent.document).hide();
}

function loadCityInit(value){
	$("#cityCode").empty();
	$("#cityCode").append("<option value=''>--选择城市--</option>");
	if(value != null && value != ""){
		var url = "../merchantAction/getProvince.action?proId="+value;
		leaseGetAjax(url,"json",function(data){
			$(data).each(function(i){
				$("<option value='"+data[i].arearCode+"'>"+data[i].areaName+"</option>").appendTo($("#cityCode"));
			})
			$("#cityCode").val("${sysAdminVo.cityCode}");
	    });
	}
}

	
$(function(){
	$("#provinceCode").val('${sysAdminVo.provinceCode}');
	loadCityInit('${sysAdminVo.provinceCode}');
});
</script>

