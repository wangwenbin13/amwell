<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">

//onload方法
$(document).ready(function(){  
	initMenu();
	//showLeftMenu();
	resizeWindow();
	//清除input框默认值  
	clearInputDefaultVal();
});

//清除input框默认值 
function clearInputDefaultVal()
{
	$('input:text').each(function(){  
		var txt = $(this).val();  
		$(this).focus(function(){  
			if(txt == $(this).val()){
//				$(this).val(""); 
//				$(this).removeClass("gray3").addClass("gray2");
			} 
		}).blur(function(){  
			if($(this).val() == ""){
//			    $(this).val(txt);  
//			    $(this).removeClass("gray2").addClass("gray3");
			}
		});  
	});

}

//加载左侧菜单事件
function initMenu() {
	
	//左侧菜单聚焦和失去焦点
	$("#leftmenu li").hover(function(){
	    $(this).addClass("hover");
	},function(){
	    $(this).removeClass("hover");
	});
  //每一个父菜单点击事件
  $("#leftmenu li a").click(function() {
  	   //$(this).next() 指当前节点下一个节点 即ul
	   var checkElement = $(this).next();
	   if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
		   //去除当前a标签的active样式
		   $(this).removeClass("active");
		   //将当前的ul关闭
		   checkElement.slideUp();
	   }
	   if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
		   //当前节点#leftmenu li a的父节点为li，然后其同级节点都是li，li后找到a标签，即去除同级li标签中的a标签已经添加过active样式
		   $(this).parent().siblings().find("a").removeClass("active");
		   //将之前展开的ul关闭
		   $('#leftmenu ul:visible').slideUp('normal');
		   //将当前点击的ul展开
		   checkElement.slideDown();
		   //增加当前a标签的active样式
		   $(this).addClass("active");
		}
	});
}

//绘制窗体大小
function resizeWindow()
{
	//浏览器可视窗口的的高度  窗体整体高度减去头部和尾部相加的高度
	$(".right").css("height",($(window).height()-175+"px"));
	//左侧菜单有一个margin——top为-5px，故减去5px
	$(".leftbar_bg,.leftbar").css("height",($(window).height()-170+"px"));
	$(window).resize(function() {
		$(".right").css("height",($(window).height()-175+"px"));
		$(".leftbar_bg,.leftbar").css("height",($(window).height()-170+"px"));
	}); 
}

//清空表单数据
function clearFormValue(formId){
	$("#"+formId).find("input:text,input:password,input:file,textarea").each(function(){
      $(this).attr("value","");
	});
	$("#"+formId).find('select').each(function(){
	  $("#"+this.id).find("option").attr("selected",false);
      $("#"+this.id).find("option").eq(0).attr("selected",true);
	});

	$("#"+formId).find("input[type='checkbox']").attr("checked",false);
}
</script>