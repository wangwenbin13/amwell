package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.dao.IVehicleInfoDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassVo;
import com.amwell.vo.VehicleInfoEntity;

/**
 * 车辆dao实现类
 * 
 * @author 龚雪婷
 *
 */
@Repository
public class VehicleInfoDaoImpl extends DaoSupport implements IVehicleInfoDao {

	/**
	 * 分页查询车辆列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> listByPage(Search search, int p, int pageSize) {
		super.finit("vehicle_info v LEFT JOIN mgr_user m ON v.createBy = m.userId");
		sql = "select v.*,m.loginName as createByName from vehicle_info v LEFT JOIN mgr_user m ON v.createBy = m.userId where v.delFlag = 0 ";
		List<String> conditions = new ArrayList<String>();
		if (search != null) {
			String cond = "";
			// 开始日期
			if (search.getField01() != null && !search.getField01().equals("")) {
				cond += " and left(v.createOn,10) >= ?";
				conditions.add(search.getField01().trim());
			}
			// 结束日期
			if (search.getField02() != null && !search.getField02().equals("")) {
				cond += " and left(v.createOn,10) <= ?";
				conditions.add(search.getField02().trim());
			}
			// 车牌号
			if (search.getField03() != null && !search.getField03().equals("")) {
				cond += " and v.vehicleNumber like ?";
				conditions.add("%" + search.getField03().trim() + "%");
			}
			// 品牌车型
			if (search.getField04() != null && !search.getField04().equals("")) {
				cond += " and (v.vehicleBrand like ? or v.vehicleModel like ? )";
				conditions.add("%" + search.getField04().trim() + "%");
				conditions.add("%" + search.getField04().trim() + "%");
			}
			// 车辆类型
			if (search.getField05() != null && !search.getField05().equals("")) {
				cond += " and v.vehicleType = ? ";
				conditions.add(search.getField05().trim());
			}
			// 商户id
			if (search.getField06() != null && !search.getField06().equals("")) {
				cond += " and v.businessId = ?";
				conditions.add(search.getField06().trim());
			}
			sql = sql + cond;
		}
		// 默认根据createOn字段排序
		sql = sql + " order by v.createOn desc";
		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(VehicleInfoEntity.class, sql, p, conditions.toArray(new Object[] {}));
		page = new Page(list, sql, p, pageSize, conditions.toArray(new Object[] {}));
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * 根据id查询车辆对象
	 * 
	 * @param id
	 * @return
	 */
	public VehicleInfoEntity getVehicleById(String id) {
		super.finit("vehicle_info");
		VehicleInfoEntity vehicle = tableDao.queryBeanById(VehicleInfoEntity.class, id, "vehicleId");
		return vehicle;
	}

	/**
	 * 保存或者修改车辆信息
	 * 
	 * @param vehicleInfo
	 * @return
	 */
	public int saveOrUpdateVehicle(VehicleInfoEntity vehicleInfo, String userId) {
		super.finit("vehicle_info");
		String str = null;
		if (StringUtils.hasText(vehicleInfo.getVehicleId())) {
			str = "车辆修改";
		} else {
			str = "车辆添加";
		}
		int flag = tableDao.updateData(vehicleInfo, "vehicleId");
		if (flag > 0) {
			AppendLog(userId, "车辆管理", str + "成功");
		} else {
			AppendLog(userId, "车辆管理", str + "失败");
		}
		return flag;
	}

	/**
	 * 根据id删除车辆信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * 
	 * @param id
	 * @return
	 */
	public int deleteVehicleById(String id, String userId) {
		super.finit("vehicle_info");
		String[] ids = id.split(",");
		args = new Object[ids.length];
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			args[i] = ids[i];
			sb.append("?,");
		}
		String sql = "update vehicle_info set delFlag = 1 where vehicleId in(" + sb.substring(0, sb.length() - 1) + ")";
		int flag = tableDao.executeSQL(sql, args);
		if (flag > 0) {
			AppendLog(userId, "车辆管理", "车辆删除成功");
		} else {
			AppendLog(userId, "车辆管理", "车辆删除失败");
		}
		return flag;
	}

	/**
	 * 根据车辆id查询当前排班信息
	 * 
	 * @param driverId
	 * @return
	 */
	public Map<String, Object> lineClass(Search search, String vehicleId) {
		super.finit("line_class_info");
		sql = "SELECT DISTINCT orderDate FROM line_class_info WHERE vehicleId = ? AND orderDate >= ? AND orderDate <= ? and delFlag = 0 order by orderDate";
		List<String> conditions = new ArrayList<String>();
		conditions.add(vehicleId);
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
			conditions.add(vehicleId);
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
					+ "WHERE lci.delFlag = 0 and lci.vehicleId = ? and lci.orderDate in("
					+ s.substring(0, s.length() - 1) + ") ORDER BY lci.orderDate,lci.orderStartTime";
			list = tableDao.queryList(LineClassVo.class, sql, conditions.toArray(new Object[] {}));
		} else {
			list = null;
		}
		map.put("list", list);
		return map;
	}

	/**
	 * 根据车辆id查询历史排班信息
	 * 
	 * @param driverId
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> lineClassPage(Search search, String vehicleId, int p, int pageSize) {
		super.finit("line_class_info");
		sql = "SELECT DISTINCT orderDate FROM line_class_info WHERE vehicleId = ? AND orderDate >= ? AND orderDate <= ? and delFlag = 0 order by orderDate";
		List<String> conditions = new ArrayList<String>();
		conditions.add(vehicleId);
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
			conditions.add(vehicleId);
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
					+ "WHERE lci.delFlag = 0 and lci.vehicleId = ? and lci.orderDate in("
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
	 * 根据车牌号查询车辆信息
	 * 
	 * @param vehicleNumber
	 * @return
	 */
	public List<VehicleInfoEntity> getVehicleByNumber(String vehicleNumber) {
		super.finit("vehicle_info");
		args = new Object[1];
		args[0] = vehicleNumber;
		String sql = "select vi.vehicleId from vehicle_info vi where vi.vehicleNumber = ? and vi.delFlag<>1 ";
		return tableDao.queryList(VehicleInfoEntity.class, sql, args);
	}

	/**
	 * 根据车辆id查询班次信息
	 * 
	 * @param driverId
	 * @return
	 */
	public List<LineClassEntity> getLineClassByVehicleId(String vehicleId) {
		super.finit("line_class_info");
		StringBuffer sub = new StringBuffer("");
		String[] strs = vehicleId.split(",");
		args = new Object[strs.length];
		for (int i = 0; i < strs.length; i++) {
			args[i] = strs[i];
			sub.append("?,");
		}
		String sql = "SELECT distinct lc.vehicleId,v.vehicleNumber as orderStartTime FROM line_class_info lc,vehicle_info v WHERE lc.delFlag = 0 and lc.vehicleId = v.vehicleId and lc.vehicleId in("
				+ sub.substring(0, sub.length() - 1) + ")";
		return tableDao.queryList(LineClassEntity.class, sql, args);
	}
}
