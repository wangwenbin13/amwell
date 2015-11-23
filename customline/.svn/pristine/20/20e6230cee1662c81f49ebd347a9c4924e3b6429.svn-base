$(document).ready(function(){
	$("[name='stationName']").unbind();
	$("[name='stationName']").bind("keyup",loadAvailableStationList);
});
function loadAvailableStationList(){
	var _this = $(this);
	$(".stationPicker").remove();
	leaseGetAjax("line/loadStationListByKeyword.action?keyword="+_this.val(),"json",function(data){
		if(data.a1=="100"){
			var stationList = data.a2;
			var stationPicker = "<div class=\"stationPicker\">";
			for(var i=0;i<stationList.length;i++){
				var station = stationList[i];
				stationPicker += "<div loni=\""+station.loni+"\" lati=\""+station.lati+"\">"+station.stationName+"</div>"
			}
			stationPicker += "</div>";
			if(stationList.length>0){
				_this.after(stationPicker);
				$(".stationPicker div").unbind();
				$(".stationPicker div").bind("click",loadStationInfo);
			}
		}
	});
}

function loadStationInfo(){
	var _this = $(this);
	var _parent = _this.parent();
	_parent.prev().val(_this.html()).css("color","rgb(102, 102, 102)");
	_parent.next().val(_this.attr("loni")).css("color","rgb(102, 102, 102)");
	_parent.next().next().val(_this.attr("lati")).css("color","rgb(102, 102, 102)");
	$(".stationPicker").remove();
}