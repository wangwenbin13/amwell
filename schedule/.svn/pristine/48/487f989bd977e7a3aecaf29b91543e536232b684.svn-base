package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.dao.IDriverInfoDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassVo;

/**
 * 司机dao实现类
 * 
 * @author 龚雪婷
 *
 */
@Repository
public class DriverInfoDaoImpl extends DaoSupport implements IDriverInfoDao {

	/**
	 * 分页查询司机列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> listByPage(Search search, int p, int pageSize) {
		super.finit("driver_info");
		// 将出生日期转换为年龄
		sql = "select di.*,ROUND(DATEDIFF(CURRENT_DATE(),birthday)/365) as age from driver_info di where accountStatus <> 2 ";
		List<Object> paramList = new ArrayList<Object>();
		if (search != null) {
			String cond = "";
			// 司机姓名
			if (search.getField01() != null && !search.getField01().equals("")) {
				cond += " and di.driverName like ?";
				paramList.add("%" + search.getField01().trim() + "%");
			}
			// 手机号码
			if (search.getField02() != null && !search.getField02().equals("")) {
				cond += " and di.telephone like ?";
				paramList.add("%" + search.getField02().trim() + "%");
			}
			// 驾照类型
			if (search.getField03() != null && !search.getField03().equals("")) {
				cond += " and di.driverType = ?";
				paramList.add(search.getField03().trim());
			}
			// 商户id
			if (search.getField04() != null && !search.getField04().equals("")) {
				cond += " and di.businessId = ?";
				paramList.add(search.getField04().trim());
			}
			sql = sql + cond;
		}
		sql = sql + " order by di.createOn desc ";
		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(DriverInfoEntity.class, sql, p, paramList.toArray());
		page = new Page(list, sql, p, pageSize, paramList.toArray());
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * 根据id查询司机对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverInfoEntity getDriverById(String id) {
		super.finit("driver_info");
		args = new Object[1];
		args[0] = id;
		String sql = "select di.*,ROUND(DATEDIFF(CURRENT_DATE(),birthday)/365) as age from driver_info di where di.driverId = ?";
		return tableDao.queryBean(DriverInfoEntity.class, sql, args);
	}

	/**
	 * 保存或者修改司机信息
	 * 
	 * @param driverInfo
	 * @return
	 */
	public int saveOrUpdateDriver(DriverInfoEntity driverInfo, String userId) {
		super.finit("driver_info");
		String str = null;
		if (StringUtils.hasText(driverInfo.getDriverId())) {
			str = "司机修改";
		} else {
			str = "司机添加";
		}
		int flag = tableDao.updateData(driverInfo, "driverId");
		if (flag > 0) {
			AppendLog(userId, "司机管理", str + "成功");

			// 将司机加入im用户表里
			addImUser(driverInfo.getDriverId(), "2");
		} else {
			AppendLog(userId, "司机管理", str + "失败");
		}
		return flag;
	}

	/** 添加圈子用户 **/
	private int addImUser(String userId, String userMark) {
		super.finit("im_user_info");
		String sql = "select userId as a1 from im_user_info where passengerId = ? and userMark = ?";
		args = new Object[2];
		args[0] = userId;
		args[1] = userMark;
		int count = tableDao.queryCount(sql, args);
		if (count == 0) {// 如果该im用户不存在，则添加
			super.finit("im_user_info");
			sql = "INSERT INTO `im_user_info` (`passengerId`,`userMark`) VALUES(?,?)";
			args = new Object[2];
			args[0] = userId;
			args[1] = userMark;
			return tableDao.executeSQL(sql, args);
		}
		return 0;
	}

	/**
	 * 根据id删除司机信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * 
	 * @param id
	 * @return
	 */
	public int deleteDriverById(String id, String userId) {
		super.finit("driver_info");
		StringBuffer sub = new StringBuffer("");
		String[] strs = id.split(",");
		args = new Object[strs.length];
		for (int i = 0; i < strs.length; i++) {
			args[i] = strs[i];
			sub.append("?,");
		}
		String sql = "update driver_info set accountStatus = 2 where driverId in(" + sub.substring(0, sub.length() - 1)
				+ ")";
		int flag = tableDao.executeSQL(sql, args);
		if (flag > 0) {
			AppendLog(userId, "司机管理", "司机删除成功");
		} else {
			AppendLog(userId, "司机管理", "司机删除失败");
		}
		return flag;
	}

	/**
	 * 根据司机id查询当前排班信息
	 * 
	 * @param driverId
	 * @return
	 */
	public Map<String, Object> lineClass(Search search, String driverId) {
		super.finit("line_class_info");
		sql = "SELECT DISTINCT orderDate FROM line_class_info WHERE driverId = ? AND orderDate >= ? AND orderDate <= ? and delFlag = 0 order by orderDate";
		List<String> conditions = new ArrayList<String>();
		conditions.add(driverId);
		if (search != null) {
			// 开始日期
			if (search.getField01() != null && !search.getField01().equals("")) {
				conditions.add(search.getField01());
			}
			// 结束日期
			if (search.getField02() != null && !search.getField02().equals("")) {
				conditions.add(search.getField02());
			}
		}
		List<LineClassEntity> l = tableDao.queryList(LineClassEntity.class, sql, conditions.toArray(new Object[] {}));
		if (!(null == l || l.size() == 0)) {
			conditions = new ArrayList<String>();
			conditions.add(driverId);
			StringBuffer s = new StringBuffer();
			for (LineClassEntity lineClassEntity : l) {
				s.append("?,");
				conditions.add(lineClassEntity.getOrderDate());
			}
			super.finit(
					"line_class_info lci LEFT JOIN vehicle_info vi ON lci.vehicleId = vi.vehicleId LEFT JOIN line_base_info lbi ON lci.lineBaseId = lbi.lineBaseId");
			sql = "SELECT lci.orderDate as orderDate,lci.orderStartTime as orderStartTime,vi.vehicleNumber as vehicleNumber,lbi.lineSubType as lineSubType,lbi.lineName as lineName,"
					+ "lbi.lineTime as lineTime,lbi.remark as remark,lbi.lineBaseId as lineBaseId FROM "
					+ "line_class_info lci LEFT JOIN vehicle_info vi ON lci.vehicleId = vi.vehicleId LEFT JOIN line_base_info lbi ON lci.lineBaseId = lbi.lineBaseId "
					+ "WHERE lci.delFlag = 0 and lci.driverId = ? and lci.orderDate in("
					+ s.substring(0, s.length() - 1) + ") ORDER BY lci.orderDate,lci.orderStartTime";
			list = tableDao.queryList(LineClassVo.class, sql, conditions.toArray(new Object[] {}));
		} else {
			list = null;
		}
		map.put("list", list);
		return map;
	}

	/**
	 * 根据司机id查询历史排班信息
	 * 
	 * @param driverId
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> lineClassPage(Search search, String driverId, int p, int pageSize) {
		super.finit("line_class_info");
		sql = "SELECT DISTINCT orderDate FROM line_class_info WHERE driverId = ? AND orderDate >= ? AND orderDate <= ? and delFlag = 0 order by orderDate";
		List<String> conditions = new ArrayList<String>();
		conditions.add(driverId);
		if (search != null) {
			// 开始日期
			if (search.getField01() != null && !search.getField01().equals("")) {
				conditions.add(search.getField01());
			}
			// 结束日期
			if (search.getField02() != null && !search.getField02().equals("")) {
				conditions.add(search.getField02());
			}
		}
		tableDao.setPageSize(pageSize);
		List<LineClassEntity> l = tableDao.queryByPage(LineClassEntity.class, sql, p * pageSize,
				conditions.toArray(new Object[] {}));
		page = new Page(l, sql, p, pageSize, conditions.toArray(new Object[] {}));
		if (!(null == l || l.size() == 0)) {
			conditions = new ArrayList<String>();
			conditions.add(driverId);
			StringBuffer s = new StringBuffer();
			for (LineClassEntity lineClassEntity : l) {
				s.append("?,");
				conditions.add(lineClassEntity.getOrderDate());
			}
			super.finit(
					"line_class_info lci LEFT JOIN vehicle_info vi ON lci.vehicleId = vi.vehicleId LEFT JOIN line_base_info lbi ON lci.lineBaseId = lbi.lineBaseId");
			sql = "SELECT lci.orderDate as orderDate,lci.orderStartTime as orderStartTime,vi.vehicleNumber as vehicleNumber,lbi.lineSubType as lineSubType,lbi.lineName as lineName,"
					+ "lbi.lineTime as lineTime,lbi.remark as remark,lbi.lineBaseId as lineBaseId FROM "
					+ "line_class_info lci LEFT JOIN vehicle_info vi ON lci.vehicleId = vi.vehicleId LEFT JOIN line_base_info lbi ON lci.lineBaseId = lbi.lineBaseId "
					+ "WHERE lci.delFlag = 0 and lci.driverId = ? and lci.orderDate in("
					+ s.substring(0, s.length() - 1) + ") ORDER BY lci.orderDate,lci.orderStartTime";
			list = tableDao.queryList(LineClassVo.class, sql, conditions.toArray(new Object[] {}));
		} else {
			list = null;
		}
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * 根据电话号码查询司机信息
	 * 
	 * @param telPhone
	 * @return
	 */
	public List<DriverInfoEntity> getDriverByTel(String telPhone) {
		super.finit("driver_info");
		args = new Object[1];
		args[0] = telPhone;
		String sql = "select di.* from driver_info di where di.telephone = ? AND accountStatus<>2 ";
		return tableDao.queryList(DriverInfoEntity.class, sql, args);
	}

	/**
	 * 根据司机id查询班次信息
	 * 
	 * @param driverId
	 * @return
	 */
	public List<LineClassEntity> getLineClassByDriverId(String driverId) {
		super.finit("line_class_info");
		StringBuffer sub = new StringBuffer("");
		String[] strs = driverId.split(",");
		args = new Object[strs.length];
		for (int i = 0; i < strs.length; i++) {
			args[i] = strs[i];
			sub.append("?,");
		}
		String sql = "SELECT distinct lc.driverId,d.driverName as orderStartTime FROM line_class_info lc,driver_info d WHERE lc.delFlag = 0 and lc.driverId = d.driverId and lc.driverId in("
				+ sub.substring(0, sub.length() - 1) + ")";
		return tableDao.queryList(LineClassEntity.class, sql, args);
	}
}
