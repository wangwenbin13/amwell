package com.amwell.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.commons.MyDate;
import com.amwell.dao.ILeaseOrderDao;
import com.amwell.dao.IPublishLineDao;
import com.amwell.entity.LeaseOrderModelVo;
import com.amwell.entity.ReturnTicketVo;
import com.amwell.entity.Search;
import com.amwell.service.ILeaseOrderService;
import com.amwell.vo.ApplyReturn;
import com.amwell.vo.Errlog;
import com.amwell.vo.LeasePay;
import com.amwell.vo.LineBusinessOrderEntity;
import com.amwell.vo.RechargeEntity;
import com.amwell.vo.ReturnTicket;
import com.amwell.vo.StatOutEntity;
import com.amwell.vo.StatOutLeaCou;
import com.amwell.vo.StatPassengerConsumLeaseEntity;
import com.amwell.vo.StatTotalEntity;
import com.amwell.vo.StationInfo;

@Service("leaseOrderService")
public class LeaseOrderServiceImpl implements ILeaseOrderService{

	@Autowired
	private ILeaseOrderDao iLeaseOrderDao;
	
	@Autowired
	private IPublishLineDao publishLineDao;
	
	/**
	 * 查找所有订单列表
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> listByPage(Search search, int pageSizeIndex,int pageSize) {
		Map<String,Object> map = iLeaseOrderDao.listByPage(search, pageSizeIndex, pageSize);
		List<LeaseOrderModelVo> leaseOrderModelVoList = (List<LeaseOrderModelVo>)map.get("list");
		if(leaseOrderModelVoList!=null&&!leaseOrderModelVoList.isEmpty()){
			for(LeaseOrderModelVo leaseOrderModelVo : leaseOrderModelVoList){
				List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(leaseOrderModelVo.getLineBaseId());
				if(stationInfoList!=null&&!stationInfoList.isEmpty()){
					for(StationInfo stationInfo : stationInfoList){
						String stationId = stationInfo.getId();
						if(stationId.equals(leaseOrderModelVo.getUstation())){
							leaseOrderModelVo.setUstation(stationInfo.getName());
						}
						if(stationId.equals(leaseOrderModelVo.getDstation())){
							leaseOrderModelVo.setDstation(stationInfo.getName());
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 充值明细
	 */
	public Map<String, Object> rechargeVoList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.rechargeVoList(search, currentPageIndex, pageSize);
	}


	/**
	 *  添加收入统计数据
	 */
	public int saveStatTotal(StatTotalEntity statTotalEntity) {
		return iLeaseOrderDao.saveStatTotal(statTotalEntity);
	}

	/**
	 * 日收入统计
	 */
	public Map<String, Object> queryDayStatTotalList(Search search,
			Integer currentPageIndex, Integer pageSize,BigDecimal totalMoney) {
		return iLeaseOrderDao.queryDayStatTotalList(search,currentPageIndex,pageSize,totalMoney);
	}

	/**
	 * 日期月度账目统计
	 */
	public Map<String, Object> queryDateStatTotalList(Search search,
			Integer dateCurrentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.queryDateStatTotalList(search,dateCurrentPageIndex,pageSize);
	}

	/**
	 * 按商家月度账目统计报表
	 */
	public Map<String, Object> queryBusinessStatTotalList(Search search,
			Integer businessCurrentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.queryBusinessStatTotalList(search,businessCurrentPageIndex,pageSize);
	}

	/**
	 * 按线路月度账目统计报表
	 */
	public Map<String, Object> queryLineStatTotalList(Search search,
			Integer lineCurrentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.queryLineStatTotalList(search,lineCurrentPageIndex,pageSize);
	}
	
	/**
	 * 记录错误信息
	 */
	public void addErrLog(Errlog err) {
		iLeaseOrderDao.addErrLog(err);
	}

	/**
	 * 获取订单详情
	 */
	public LeaseOrderModelVo getLeaseOrderById(
			LeaseOrderModelVo leaseOrderModelVo) {
		return iLeaseOrderDao.getLeaseOrderById(leaseOrderModelVo);
	}

	/**
	 * 获取车票退票列表
	 */
	public Map<String, Object> returnTicketListByPage(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.returnTicketListByPage(search,currentPageIndex,pageSize);
	}

	/**
	 * 车票退票详情
	 */
	public ReturnTicketVo getReturnTicket(ReturnTicketVo returnTicketVo) {
		return iLeaseOrderDao.getReturnTicket(returnTicketVo);
	}

	/**
	 * 更改lease_base_info表
	 */
	public void updateLeaseBaseInfo(String leaseOrderId, String dateSize,
			ReturnTicket returnTicket) {
		/**
		 * 如果退票的天数等于订票的天数
		 * 只修改支付状态payStatus，改为失效,ispay = 0
		 * 支付状态 0:待支付 1:交易成功 2:已失效 3:已取消 4:已删除
		 */
//		LeaseOrderModelVo leaseOrderModelVo = new LeaseOrderModelVo();
//		leaseOrderModelVo.setLeaseOrderId(leaseOrderId);
//		if(Integer.parseInt(dateSize)==returnTicket.getReturnDateNum()){
//			leaseOrderModelVo.setPayStatus(2);
//			leaseOrderModelVo.setIspay(0);
//		}else{
//		leaseOrderModelVo.setTotalCount(new BigDecimal(returnTicket.getRealPayMoney()));
//		leaseOrderModelVo.setRidesInfo(Integer.parseInt(dateSize)-returnTicket.getReturnDateNum());
//		}
//		iLeaseOrderDao.updateLeaseBaseInfo(leaseOrderModelVo);
	}

	/**
	 * 更改lease_pay表
	 */
	public void updateLeasePay(ReturnTicket returnTicket) {
		LeasePay leasePay = new LeasePay();
		leasePay.setLeaseOrderNo(returnTicket.getLeaseOrderNo());
		/**
		 * 先找出该订单对应的支付记录
		 */
		List<LeasePay> leasePays = iLeaseOrderDao.queryLeasePay(leasePay);
		BigDecimal left = new BigDecimal("0");//扣除余额以后剩下的金额
		BigDecimal payMoney = null;//leasePay表中的支付金额
		BigDecimal diffMoney = new BigDecimal(returnTicket.getOldMoney()).subtract(new BigDecimal(returnTicket.getRealPayMoney()));//实际差额
		if(null!=leasePays && leasePays.size()>0){
			for(LeasePay pay : leasePays){
				/**
				 * 支付方式 0：无 1：余额支付，2：财付通 3：银联 4：支付宝
				 */
//				LeasePay lpay = new LeasePay();
//				lpay.setPayId(pay.getPayId());
//				lpay.setPayMoney(new BigDecimal(realPayMoney));
//				iLeaseOrderDao.updateLeasePay(lpay);
				
				LeasePay lpay = new LeasePay();
				lpay.setPayId(pay.getPayId());
				//查看原leasePay支付了多少钱
				payMoney = pay.getPayMoney();
				if(1==pay.getPayModel()){
					//存在余额支付
					if(payMoney.compareTo(new BigDecimal("0"))==1){
						//余额大于0
						if(payMoney.compareTo(diffMoney)==1){
							//余额大于实际差额 eg:余额5元 实际差额2元
							lpay.setPayMoney(payMoney.subtract(diffMoney));
						}else{
							//余额小于实际差额 eg:余额2元 实际差额5元
							left = diffMoney.subtract(payMoney);
							lpay.setPayMoney(new BigDecimal("0"));//余额支付的金额为0
						}
					}
				}else{
					if(leasePays.size()==1){
						//不存在余额支付 --直接修改金额为实际支付金额
						lpay.setPayMoney(new BigDecimal(returnTicket.getRealPayMoney()));
					}else{
						//存在余额支付
						lpay.setPayMoney(payMoney.subtract(left));
					}
				}
				iLeaseOrderDao.updateLeasePay(lpay);
			}
		}
	}

	/**
	 * 更改line_business_order表
	 */
	public void updateLineBusinessOrder(String dateSize,
			ReturnTicket returnTicket,String localIds) {
		/**
		 * (更新)对应的line_business_order
		 */
		String[] localId_str = localIds.split(",");
		for(int i = 0;i<localId_str.length;i++){
			LineBusinessOrderEntity businessOrderEntity = new LineBusinessOrderEntity();
			businessOrderEntity.setLocalId(localId_str[i]);
			iLeaseOrderDao.updateLineBusinessOrder(businessOrderEntity);
		}
	}

	/**
	 * 更改stat_passenger_recharge表
	 */
	public void updateStatPassengerRecharge(ReturnTicket returnTicket) {
		RechargeEntity rechargeEntity = new RechargeEntity();
		rechargeEntity.setRerunNo(returnTicket.getLeaseOrderNo());
		List<RechargeEntity> rechargeEntities = iLeaseOrderDao.queryRecharge(rechargeEntity);
		if(null!=rechargeEntities && rechargeEntities.size()>0){
			for(RechargeEntity entity : rechargeEntities){
				RechargeEntity entity2 = new RechargeEntity();
				entity2.setRechargeId(entity.getRechargeId());
				entity2.setMoneyLimit(new BigDecimal(returnTicket.getRealPayMoney()));
				iLeaseOrderDao.updateStatPassengerRecharge(entity2);
			}
		}
	}

	/**
	 * 更改stat_passenger_consum_lease表
	 */
	public void updateStatPassengerConsumLease(ReturnTicket returnTicket) {
		StatPassengerConsumLeaseEntity consumLeaseEntity = new StatPassengerConsumLeaseEntity();
		consumLeaseEntity.setLeaseOrderNo(returnTicket.getLeaseOrderNo());
		List<StatPassengerConsumLeaseEntity> consumLeaseEntities = iLeaseOrderDao.queryConsumLease(consumLeaseEntity);
		if(null!=consumLeaseEntities && consumLeaseEntities.size()>0){
			for(StatPassengerConsumLeaseEntity leaseEntity : consumLeaseEntities){
				StatPassengerConsumLeaseEntity entity = new StatPassengerConsumLeaseEntity();
				entity.setOrderConsumId(leaseEntity.getOrderConsumId());
				entity.setLeaseLimit(new BigDecimal(returnTicket.getRealPayMoney()));
				iLeaseOrderDao.updateStatPassengerConsumLease(entity);
			}
		}
	}

	/**
	 * 添加退票记录
	 */
	public int addReturnTicket(ReturnTicket returnTicket,String userId,String leaseOrderId,String lineClassIds) {
		return iLeaseOrderDao.addReturnTicket(returnTicket,userId,leaseOrderId,lineClassIds);
	}

	/**
	 * 已退票列表
	 */
	public Map<String, Object> hasReturnTicketListByPage(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.hasReturnTicketListByPage(search,currentPageIndex,pageSize);
	}

	/**
	 * 车票退票详情
	 */
	public ReturnTicket getReturnTicketDetail(ReturnTicket returnTicket) {
		return iLeaseOrderDao.getReturnTicketDetail(returnTicket);
	}

	/**
	 * 支出列表
	 */
	public Map<String, Object> getStatOutListByPage(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.getStatOutListByPage(search,currentPageIndex,pageSize);
	}

	/**
	 * 添加收入统计记录
	 */
	public int addStatTotal(String leaseOrderNo) {
		return iLeaseOrderDao.addStatTotal(leaseOrderNo);
	}

	/**
	 * 取得所有已支付的订单号
	 */
	public List<String> getLeaseOrderNo(Integer currentPageIndex,Integer pageSize) {
		return iLeaseOrderDao.getLeaseOrderNo(currentPageIndex,pageSize);
	}

	/**
	 * 检查支付方式
	 * 包含余额支付的都不允许退票
	 */
	public int getLeasePayCount(String leaseOrderId) {
		return iLeaseOrderDao.getLeasePayCount(leaseOrderId);
	}

	/**
	 * 判断该订单是否已经添加过收入记录
     * 如果已经存在了，则不再添加
	 */
	public int getStatTotalCountByLeaseOrderNo(String leaseOrderNo) {
		return iLeaseOrderDao.getStatTotalCountByLeaseOrderNo(leaseOrderNo);
	}

	/**
	 * 从支出表里面还原数据到收入表
	 */
	public void addStatTotalByStatOut(List<StatOutEntity> list) {
		iLeaseOrderDao.addStatTotalByStatOut(list);
	}

	/**
	 * 清空收入统计表(小猪巴士)
	 */
	public int truncateTable() {
		return iLeaseOrderDao.truncateTable();
		
	}

	/**找出退票的订单 以及该订单退票的张数(张数大于1张)**/
	public List<StatOutLeaCou> queryStatOutLeaCou(Integer page,Integer pageSize) {
		return iLeaseOrderDao.queryStatOutLeaCou(page,pageSize);
	}

	/**比较该订单号支出的价格是否等于该订单号对应的退票记录中的退票金额，如果是，则需要修改**/
	public boolean compareLeaMon(StatOutLeaCou leaCou) {
		return iLeaseOrderDao.compareLeaMon(leaCou);
	}

	/***更改对应的支出记录**/
	public int updateStatOut(StatOutLeaCou leaCou) {
		return iLeaseOrderDao.updateStatOut(leaCou);
	}

	
	/**找出总共需要修改的退票订单记录数**/
	public int queryStatOutLeaCouAllCount() {
		return iLeaseOrderDao.queryStatOutLeaCouAllCount();
	}

	/**找出总共需要恢复的订单记录数**/
	public int queryLeaseOrderCount() {
		return iLeaseOrderDao.queryLeaseOrderCount();
	}

	/**找出总共需要恢复的支出记录数**/
	public int queryStatOutListCount() {
		return iLeaseOrderDao.queryStatOutListCount();
	}

	/**事务处理恢复订单**/
	public boolean recordBack() {
		return iLeaseOrderDao.recordBack();
	}

	/**上下班优惠券列表**/
	public Map<String, Object> queryBusCouponStatList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.queryBusCouponStatList(search,currentPageIndex,pageSize);
	}

	/**乘客申请退票列表**/
	public Map<String, Object> applyReturnTicketList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.applyReturnTicketList(search,currentPageIndex,pageSize);
	}

	/**修改退票申请状态**/
	public int updateApplyReturnTicketType(ApplyReturn applyReturn) {
		applyReturn.setOperTime(MyDate.getMyDateLong());
		applyReturn.setType("1");
		return iLeaseOrderDao.updateApplyReturnTicketType(applyReturn);
	}

	/**计算总计**/
	public String countTotalValue(Search search) {
		return iLeaseOrderDao.countTotalValue(search);
	}

	/**(APP)支付宝退票列表**/
	public Map<String, Object> appZfbReturnTicketList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.appZfbReturnTicketList(search,currentPageIndex,pageSize);
	}

	/**将该次需要退款的数据加上批次号**/
	public int addBatchNoToReturnTicket(String returnIds, String batchNo) {
		return iLeaseOrderDao.addBatchNoToReturnTicket(returnIds,batchNo);
	}

	/**根据退款批次号修改return_ticket的退款状态**/
	public int updateReturnOrNot(String batchNo) {
		return iLeaseOrderDao.updateReturnOrNot(batchNo);
	}

	/**添加退款记录**/
	public void addReturnStatu(int statu, int type, String batchNo) {
		iLeaseOrderDao.addReturnStatu(statu,type,batchNo);
	}

	/**根据条件获取总记录数**/
	public int queryTotal(Search search) {
		return iLeaseOrderDao.queryTotal(search);
	}

	/**根据条件获得对应的订单记录数**/
	@Override
	public int queryLeaseNuByCon(Search search) {
		return iLeaseOrderDao.queryLeaseNuByCon(search);
	}
}
