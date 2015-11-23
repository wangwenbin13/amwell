package com.amwell.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.commons.StringUtil;
import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.IPermissionService;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysPermissionVo;

/**
 * 权限_Service
 * @author 胡双
 *
 */
@Service(value="permissionService")
public class PermissionServiceImpl extends SysDaoImpl<SysPermissionVo> implements IPermissionService {
	
	//@Autowired
	//private IPermissionDao permissionDao;
	
	
	
	/**
	 * 获得系统所有的权限
	 * @param sysType 系统类型
	 * @return 
	 */
	public List<SysPermissionVo> queryAllSysPermission(int sysType) {
		String sql = "SELECT powerId,code,name,fid,sysType,url,iconUrl,createBy,createOn,updateBy,updateOn FROM sys_power where sysType = ?";
		Object[] params = new Object[]{sysType};
		
		return queryList(sql, params);
	}
	
	/**
	 * 获得左侧的菜单
	 * @param sysType 系统类型
	 * @param fid 父Id
	 * @param userId 用户ID
	 * @return
	 */
	public List<SysPermissionVo> queryLeftMenu(int sysType,String fid,String userId) {
		String sql = "SELECT p.* FROM sys_power p WHERE p.fid = ? AND p.sysType = ? AND p.powerId IN ("
			+"SELECT rp.powerId FROM  sys_roletopower rp " +
					"WHERE rp.roleId = (SELECT ur.roleId FROM sys_userrole ur WHERE  ur.userId = ?)) ORDER BY p.sortFlag ASC";
		Object[] params = new Object[]{fid,sysType,userId};
		return queryList(sql, params);
	}
	
	/**
	 * 根据角色ID查询对应的权限
	 * @param roleId
	 * @param sysType
	 * @return
	 */
	public List<SysPermissionVo> queryPermissionInfoByRoleId(String roleId,
			int sysType) {
		
		Object[] params = new Object[]{sysType,roleId};
		String preSql = "SELECT p.powerId,p.code,p.url FROM sys_power p,sys_roletopower rp WHERE p.powerId = rp.powerId AND p.sysType = ? AND rp.roleId = ?";
		
		return queryList(preSql, params);
	}
	
	/**
	 * 批量插入权限信息
	 * @param permissionList
	 * @param sysType
	 * @return
	 */
	public boolean insertPermissionData(
			List<SysPermissionEntity> permissionList, int sysType) {
		
		String sql = "INSERT INTO sys_power (powerId,code,name,fid,sysType,url,iconUrl,sortFlag,createBy,createOn) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Object[][] paramList = new Object[permissionList.size()][10];// >> 行 列
		for(int i=0;i<permissionList.size();i++){
			SysPermissionEntity sysPermissionEntity = permissionList.get(i);
			Object[] obj = new Object[]{
				StringUtil.generateSequenceNo(),// >> 主键
				sysPermissionEntity.getCode(),// >>  权限编码
				sysPermissionEntity.getName(),// >> 权限名称
				sysPermissionEntity.getFid(),// >> 权限的上级编码
				sysType,// >> 系统类型
				sysPermissionEntity.getUrl(),// >> 访问路径
				sysPermissionEntity.getIconUrl(),// >> 图片路径
				sysPermissionEntity.getSortFlag(),// >> 排序
				sysPermissionEntity.getCreateBy(),// >> 创建人
				MyDate.getMyDateLong()// >> 创建时间
			};
			
			// >> 
			paramList[i] =  obj;
		}
		
		
		return batchOperator(sql, paramList);
	}
	
	/**
	 * 初始化或更新调用的方法
	 */
	public boolean initPermissionData(
		List<SysPermissionEntity> permissionList, int sysType) {
			
			String sql = "INSERT INTO sys_power (powerId,code,name,fid,sysType,url,iconUrl,sortFlag,createBy,createOn) VALUES (?,?,?,?,?,?,?,?,?,?)";
			String powerId = "";
			Object[][] paramList = new Object[permissionList.size()][10];// >> 行 列
			for(int i=0;i<permissionList.size();i++){
				SysPermissionEntity sysPermissionEntity = permissionList.get(i);
				System.out.println("当前的权限名称:"+sysPermissionEntity.getName());
				
				// 查询该权限是否已经在数据库存在
				SysPermissionVo  vo = queryPermissionByNameAndFid(sysPermissionEntity.getName(), sysType,sysPermissionEntity.getFid());
				if(vo != null){
					powerId = vo.getPowerId();
				}else{
					powerId = StringUtil.generateSequenceNo();
				}
				
				
				
				Object[] obj = new Object[]{
					powerId,// >> 主键
					sysPermissionEntity.getCode(),// >>  权限编码
					sysPermissionEntity.getName(),// >> 权限名称
					sysPermissionEntity.getFid(),// >> 权限的上级编码
					sysType,// >> 系统类型
					sysPermissionEntity.getUrl(),// >> 访问路径
					sysPermissionEntity.getIconUrl(),// >> 图片路径
					sysPermissionEntity.getSortFlag(),// >> 排序
					sysPermissionEntity.getCreateBy(),// >> 创建人
					MyDate.getMyDateLong()// >> 创建时间
				};
				
				// >> 
				paramList[i] =  obj;
			}
			deleteObject("DELETE FROM sys_power WHERE sysType = 0", new Object[]{});
			
			return batchOperator(sql, paramList);
	}
	/**
	 * 根据请求路径和系统类型查询权限信息
	 * @param path
	 * @param sysType
	 * @return
	 */
	public SysPermissionVo queryPermissionInfoByUrlAndSysType(String path,
			int sysType) {
		String sql = "SELECT powerId,code,name,fid,sysType,url,iconUrl,sortFlag,createBy,createOn,updateBy,updateOn FROM  sys_power WHERE url = ? AND sysType = ?";
		Object[] params = new Object[]{
				path, // >> 访问路径
				sysType // >> 系统类型
		};
		
		return queryObject(sql, params);
	}
	
	/**
	 * 判断请求的nameSpace是否在权限控制范围内
	 * @param nameSpace 命名空间
 	 * @param sysType 系统类型
	 * @return
	 */
	public int queryPermissionByNameSpace(String nameSpace, int sysType) {
		String sql = "SELECT COUNT(*) FROM  sys_power WHERE url Like ? AND sysType = ?";
		Object[] params = new Object[]{
				nameSpace, // >> 访问路径
				sysType // >> 系统类型
		};
		
		
		return queryCountByCustomSqlAndParams(sql, params);
	}

	public PageBean getPageBean(int parseInt, int pageSize, QueryHelper query) {
		String pageSql = query.getListSql(); // >> 获取分页SQL
		Object[] pageParams = query.getObjectArrayParams(); // >>  对应的参数数组
		List<SysPermissionVo> recordList = queryList(pageSql,pageParams); // >> 得到集合
		String countSql = query.getCountSql(); // >>  查询总数的SQL
		int recordCount = queryCountByCustomSqlAndParams(countSql, pageParams); // >>  得到总数
		PageBean pageBean = new PageBean(parseInt, pageSize, recordCount, recordList);
		return pageBean;
	}
	

	/**
	 * 根据权限名称查找权限
	 * @param permissionName
	 * @param sysType
	 * @return
	 */
	public SysPermissionVo queryPermissionByName(String permissionName,
			int sysType) {
		String sql = "SELECT powerId,code,name,fid,sysType,url,iconUrl,sortFlag,createBy,createOn,updateBy,updateOn FROM  sys_power WHERE name = ? AND sysType = ?";
		Object[] params = new Object[]{
				permissionName,
				sysType
		};
		return queryObject(sql, params);
	}
	
	/**
	 * 根据权限名称，系统类型，父ID
	 * @param permissionName
	 * @param sysType
	 * @param fid
	 * @return
	 */
	public SysPermissionVo queryPermissionByNameAndFid(String permissionName,
			int sysType,String fid) {
		String sql = "SELECT powerId,code,name,fid,sysType,url,iconUrl,sortFlag,createBy,createOn,updateBy,updateOn FROM  sys_power WHERE name = ? AND sysType = ? AND fid = ?";
		Object[] params = new Object[]{
				permissionName,
				sysType,
				fid
		};
		return queryObject(sql, params);
	}
	
	/**
	 * 根据权限编码查询权限
	 */
	public SysPermissionVo queryPermissionByCode(String fid, int sysType) {
		String sql = "SELECT powerId,code,name,fid,sysType,url,iconUrl,sortFlag,createBy,createOn,updateBy,updateOn,sortFlag FROM  sys_power WHERE fid = ? AND sysType = ? ORDER BY sortFlag DESC";
		Object[] params = new Object[]{
				fid,
				sysType
		};
		List<SysPermissionVo> permissions = queryList(sql, params);
		if(permissions.size() == 0){ //说明是第一次添加它的下级菜单
			return null;
		}
		
		return permissions.get(permissions.size()-1);
	}
	
	
	
	
	
	
	
	
	
}
