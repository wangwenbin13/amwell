package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.ISysLogDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.SysLogVo;
import com.amwell.vo.SysMgrAdminVo;

/**
 * @author chenlongfeng 系统操作日志
 *
 */
@Repository("SysLogDao")
public class SysLogDaoImpl extends DaoSupport implements ISysLogDao {

	/**
	 * 查询操作日志数据列表
	 * 
	 * @param search
	 * @return
	 */
	public Map<String, Object> getSysLogList(Search search, int currentPage, int pageSize) {
		super.finit(" sys_log log left join sys_admin admin on log.userId=admin.userId ");
		StringBuffer sql = new StringBuffer(
				"select log.operateTime,log.operateResult,log.userIp,log.action,admin.userName,admin.loginName from sys_log log left join mgr_user admin on log.userId=admin.userId where 1=1  ");
		List<Object> paramList = new ArrayList<Object>();
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysMgrAdminVo msgUser = (SysMgrAdminVo) httpSession.getAttribute("userInfo");// 登录用帐号资料
		String userId = msgUser.getUserId();
		Map<String, Object> map = new HashMap<String, Object>();
		if (search != null) {
			if (search.getField01() != null && !"".equals(search.getField01())) {
				sql.append(" and left(log.operateTime,10) >=? ");
				paramList.add(search.getField01());
			}
			if (search.getField02() != null && !"".equals(search.getField02())) {

				sql.append(" and left(log.operateTime,10) <=? ");
				paramList.add(search.getField02());
			}
			if (search.getField03() != null && !"".equals(search.getField03())) {
				sql.append("  and admin.userName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if (search.getField04() != null && !"".equals(search.getField04())) {
				sql.append("  and log.action like ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if (search.getField05() != null && !"".equals(search.getField05())) {
				sql.append("  and log.operateResult like ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
		}
		sql.append(" and log.userId like ? ");
		paramList.add(userId);
		sql.append("  and log.sysType like 1  order by log.operateTime desc  ");
		Object[] params = paramList.toArray(new Object[] {});
		List<SysLogVo> list = tableDao.queryByPage(SysLogVo.class, sql.toString(), currentPage, pageSize, params);
		Page page = new Page(list, sql.toString(), currentPage, pageSize, params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * 添加操作日志
	 * 
	 * @param sysLogVo
	 * @return
	 */
	public int addSysLog(SysLogVo sysLogVo) throws Exception {
		int count = 0;
		if (sysLogVo != null) {
			super.finit("sys_log");
			count = super.tableDao.updateData(sysLogVo, "logId");
		}
		return count;
	}

}
