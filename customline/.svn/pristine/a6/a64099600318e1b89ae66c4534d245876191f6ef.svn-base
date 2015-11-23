<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改上下车点</title>
<jsp:include page="../resource.jsp"/>
<script>
function changeVal(id){
	 var temp = document.getElementsByName("stype");
	 var result = "";
     for(i=0;i<temp.length;i++){
     	result = result + temp[i].value +",";
     }
	
	result = result.substring(0,result.length-1);
	
	var strs= new Array(); 
	strs=result.split(",");
	var sort = bubbleSort(strs);
	
	if(sort!=result || result.indexOf("0") < 0){
		alert("编辑错误！");
	}else{
		window.location.href = "../line/updateLine_Station.action?lid="+id+"&flag="+result;
	}
}
	
function bubbleSort(arr){
	var arr_temp = arr;
	var temp = "";
    for(var i=0;i<arr_temp.length;i++){
        for(var j=i;j<arr_temp.length;j++){
            if(arr_temp[i]<arr_temp[j]){
                var temp1=arr_temp[i];
                arr_temp[i]=arr[j];
                arr_temp[j]=temp1;
            }
        }
    }
    
    for(var i=0;i<arr.length;i++){
    	temp = temp + arr[i]+",";
	}
	temp = temp.substring(0,temp.length-1);
	return temp;
}
	
	
</script>
</head>

<body>
              <table width="50%" border="0" class="table3" id="tableDiv">
                    <tr align="center" class="th">
                      <th scope="col" width="15%">站点ID</th>
                      <th scope="col" width="15%">站点名字</th>
                      <th scope="col" width="20%">站点类型</th>
                    </tr>
                    <s:iterator value="#request.list" status="s">
			            <s:if test="%{#s.count%2 == 1}">
							<tr align="center" class="tr bg1">
						</s:if>
						<s:if test="%{#s.count%2 == 0}">
							<tr align="center" class="tr bg2">
						</s:if>
                    	<td class="f-arial"><s:property value="a2"/></td>
						<td class="f-arial"><s:property value="a3"/></td>
						<td class="f-arial">
							<select name="stype">
								<option value="1" <s:if test="a4==1">selected="selected"</s:if>>上车点</option>
								<option value="0" <s:if test="a4==0">selected="selected"</s:if>>下车点</option>
								<option value="0" <s:if test="a4==2">selected="selected"</s:if>>待选择</option>
							</select>
						</td>
                    	</tr>
                    	
                    	
					</s:iterator>
                    	<tr>
                    		<td colspan="3" align="center">
                    			<input onclick="changeVal('<s:property value="#request.lid"/>');" type="button" value="确定"/>
                    		</td>
                    	</tr>
                  </table>
</body>
</html>
