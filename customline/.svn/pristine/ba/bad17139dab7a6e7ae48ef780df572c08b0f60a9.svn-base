<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- js下拉选框 -->
<script src="../js/select/liquidmetal.js" type="text/javascript"></script>
<script src="../js/select/jquery.flexselect.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
//-------------------------------------------下拉选框
$.fn.example = function(select, type) {
var div = $("<div></div>").addClass(type).append(select.clone().attr({ id: select.attr("id") + "_" + type, "class": type }));
//this.append(div);  //如果是div，则是append
this.after(div);  //如果是input，则是after
};

//创建下拉搜索
function createSelectSearch(inputId,selectId)
{
	//搜索内容
    var selectSearchData = $("#"+selectId); 
	//创建搜索下拉框,将搜索内容追加进去
    $("#"+inputId).example(selectSearchData, "flexselect");
	$("select.flexselect").flexselect();
	
	//搜索下拉框默认值为空
    var searchByInputName = $("#"+selectId+"_flexselect_flexselect");
	searchByInputName.attr("value","");
	searchByInputName.hide();
	
    
    $("select").attr("tabindex", -1);
    $("input.flexselect").each(function(index) { $(this).attr("tabindex", index+1) });

 		 
	 $("#"+inputId).focus(function(){
	 	  $("#"+inputId).blur();			  
	      searchByInputName.show();
	      searchByInputName.focus();
  	 });	
	
}
//-------------------------------------------------------------------------------------
</script>