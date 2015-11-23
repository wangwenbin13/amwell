package com.amwell.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amwell.vo.SysPermissionVo;
import com.google.gson.Gson;


/**
 * 权限树
 * @author 胡双
 *
 */
public class JsonZtreeUtil {
	
	/**
	 * 获得json格式的权限ztree
	 * @param permission 该角色已有的权限
	 * @param falg 设置该树是否禁用
	 * @param sysPermissionAll 所有的权限
	 */
	public static String getJsonZtree(List<SysPermissionVo> permission,boolean falg,List<SysPermissionVo> sysPermissionAll)throws Exception{
		
		List<String> permissionIds = new ArrayList<String>();
		//说明该角色有权限
		if(null != permission && permission.size()>0){
			for(int i=0;i<permission.size();i++){
				permissionIds.add(permission.get(i).getPowerId());
			}
		}
		List<Map<String, Object>> tree = new ArrayList<Map<String,Object>>();
		//初始化根节点
		if(!sysPermissionAll.isEmpty()){
			Map<String, Object> rootObj = new HashMap<String,Object>();
			rootObj.put("id", "-1");
			rootObj.put("name", "运营平台权限");
			rootObj.put("open", "true");
			rootObj.put("pId", "tree");
			if(permissionIds.size()>0){
				rootObj.put("checked", true);
			}
			if(falg){
				rootObj.put("chkDisabled", "true");
			}
			tree.add(rootObj);
		}
		for(int i=0;i<sysPermissionAll.size();i++){
			SysPermissionVo sp = sysPermissionAll.get(i);
			Map<String, Object> obj = new HashMap<String,Object>();
			obj.put("id",sp.getCode());
			obj.put("pId", sp.getFid());
			obj.put("name", sp.getName());
			obj.put("open", "true");
			if(permissionIds.contains(sp.getPowerId())){
				obj.put("checked", true);
			}
			if(falg){
				obj.put("chkDisabled", "true");
			}
			tree.add(obj);
		}
		Gson gson = new Gson();
		return gson.toJson(tree);
	}
	
	
	
}
