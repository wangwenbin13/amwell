DELIMITER $$

USE `amwellcustomline`$$

DROP TABLE IF EXISTS `coupon_info`;
CREATE TABLE `coupon_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `couponName` varchar(32) NOT NULL COMMENT '优惠券名称',
  `couponValue` DECIMAL(6,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券面额',
  `couponCon` DECIMAL(6,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券门槛',
  `effectiveTime` varchar(19) DEFAULT NULL COMMENT '使用的有效开始时间(预留)',
  `expirationTime` varchar(19) DEFAULT NULL COMMENT '使用的有效结束时间(预留)',
  `delayDays` int(10) NULL COMMENT '后延天数（单位：天）',
  `isDel` tinyint(1) DEFAULT '0' COMMENT '使用状态  0:正常 1:已删除',
  `couponGroupId` int(10) NULL COMMENT '组合券的批次号',
  `couponGroupName` varchar(35) NULL COMMENT '组合券名称',
  `num` int(10) NULL DEFAULT '0' COMMENT '组合券内该优惠券的个数',
  `couponCount` int(10) NULL DEFAULT '0' COMMENT '优惠券发行总数',
  `createBy` varchar(35) NOT NULL COMMENT '创建人',
  `createOn` varchar(19) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券信息表';

DROP TABLE IF EXISTS `coupon_group`;
CREATE TABLE `coupon_group` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `couponGroupID` int(10) NOT NULL DEFAULT '0' COMMENT '组合券批次号',
  `couponGroupName` varchar(35) NOT NULL COMMENT '组合券名称',
  `couponGroupCount` int(10) NOT NULL DEFAULT '0' COMMENT '组合券总数',
  `averageNum` int(10) DEFAULT '1' COMMENT '组合券人均领取量',
  `createBy` varchar(35) NOT NULL COMMENT '创建人',
  `createOn` varchar(19) NOT NULL COMMENT '创建时间',
  `updateBy` varchar(35) NULL COMMENT '修改人',
  `updateOn` varchar(19) NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组合券信息表';

DROP TABLE IF EXISTS `coupon_rule`;
CREATE TABLE `coupon_rule` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ruleName` varchar(35) NOT NULL COMMENT '规则名称',
  `theCondition` varchar(5) NOT NULL DEFAULT 'equal' COMMENT '条件：gt:大于 equal:等于 lt:小于',
  `theValue` text NOT NULL COMMENT '规则内容',
  `couponGroupGrantId` int(10) NOT NULL COMMENT '组合券发放ID',
  `createBy` varchar(35) NOT NULL COMMENT '创建人',  
  `createOn` varchar(19) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券发放规则表';

DROP TABLE IF EXISTS `coupon_group_grant`;
CREATE TABLE `coupon_group_grant` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `taskName` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `couponGroupId` int(10) NOT NULL COMMENT '组合券批次号',
  `couponGroupName` varchar(35) NULL COMMENT '组合券名称',
  `selectPass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '选择用户  0:全部用户 1:老用户 2:新用户',
  `sendCouponType` varchar(10) DEFAULT NULL COMMENT '发放方式  user get:用户领取  sys send:系统发放',
  `startTime` varchar(19) DEFAULT NULL COMMENT '可领取组合券开始时间',
  `endTime` varchar(19) DEFAULT NULL COMMENT '可领取组合券结束时间',
  `modeOfExecution` varchar(7) DEFAULT 'unsend' NULL COMMENT '执行方式 unsend:未发放  send:发放  closed:关闭',
  `createBy` varchar(35) NOT NULL COMMENT '创建人',
  `createOn` varchar(19) NOT NULL COMMENT '创建时间',
  `updateBy` varchar(35) NULL COMMENT '修改人',
  `updateOn` varchar(19) NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组合券发放表';

DROP TABLE IF EXISTS `coupon_group_passenger_detail`;
CREATE TABLE `coupon_group_passenger_detail` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `passengerId` varchar(35) NOT NULL COMMENT '用户Id',
  `telephone` varchar(16) NOT NULL COMMENT '手机号码',
  `grantId` int(10) NOT NULL COMMENT '发放Id',
  `couponGroupId` int(10) NOT NULL COMMENT '组合券批次号',
  `couponId` int(10) NOT NULL COMMENT '优惠券Id',
  `getTime` varchar(19) DEFAULT NULL COMMENT '领取时间',
  `effectiveTime` varchar(19) DEFAULT NULL COMMENT '使用的有效时间(开始)',
  `expirationTime` varchar(19) DEFAULT NULL COMMENT '使用的有效时间(结束)',
  `useState` varchar(7) DEFAULT 'unused' COMMENT '使用状态 unused:未使用 used:已使用  expired:已过期',
  `orderId` varchar(35) DEFAULT NULL COMMENT '订单ID',
  `useTime` varchar(19) DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户优惠券详情表';

DROP TABLE IF EXISTS `pb_station`;
CREATE TABLE `pb_station` (
  `id` varchar(255) NOT NULL DEFAULT '' COMMENT '主键',
  `lineId` varchar(255) DEFAULT NULL COMMENT '线路id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `baiduLng` double DEFAULT NULL COMMENT '百度经度',
  `baiduLat` double DEFAULT NULL COMMENT '百度纬度',
  `gpsLng` double DEFAULT NULL COMMENT 'gps经度',
  `gpsLat` double DEFAULT NULL COMMENT 'gps纬度',
  `type` int(11) DEFAULT NULL COMMENT '站点类型 1-上车点 0-下车点 2-途径点',
  `sortNum` int(11) DEFAULT NULL COMMENT '排序序号',
  `available` int(11) DEFAULT NULL COMMENT '可用状态 0-可用 1-不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新版站点表（站点拆分）';

ALTER TABLE `line_base_info`
MODIFY COLUMN `stationInfoes`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '线路站点ID集合 以，号隔开' AFTER `lineBaseId`;

DROP TABLE IF EXISTS `stat_financial`;
CREATE TABLE `stat_financial` (
  `finacialId` varchar(35) NOT NULL DEFAULT '' COMMENT '主键ID',
  `lineBaseId` varchar(35) DEFAULT NULL COMMENT '线路信息ID',
  `lineName` varchar(35) DEFAULT NULL COMMENT '线路名称',
  `businessId` varchar(35) DEFAULT NULL COMMENT '商户ID',
  `brefName` varchar(35) DEFAULT NULL COMMENT '商户名称',
  `classPrice` decimal(16,2) DEFAULT '0.00' COMMENT '班次价格',
  `price` decimal(16,2) DEFAULT '0.00' COMMENT '实际支付价格',
  `todayGifMoney` decimal(16,2) DEFAULT '0.00' COMMENT '当天班次优惠金额',
  `bstation` varchar(35) DEFAULT NULL COMMENT '起点',
  `estation` varchar(35) DEFAULT NULL COMMENT '终点',
  `orderStartTime` varchar(16) DEFAULT NULL COMMENT '发车时间',
  `orderDate` varchar(16) DEFAULT NULL COMMENT '排班日期',
  `orderSeats` int(4) DEFAULT NULL COMMENT '座位数',
  `driverId` varchar(35) DEFAULT NULL COMMENT '司机ID',
  `vehicleId` varchar(35) DEFAULT NULL COMMENT '车辆ID',
  `vehicleNumber` varchar(16) DEFAULT NULL COMMENT '车牌',
  `driverName` varchar(16) DEFAULT NULL COMMENT '司机名称',
  `lineClassId` varchar(35) DEFAULT NULL COMMENT '班次ID',
  `byBusNum` int(4) DEFAULT '0' COMMENT '班次的实际上座人数',
  `changeNum` int(4) DEFAULT '0' COMMENT '班次对应的实际改签数',
  `returnNum` int(4) DEFAULT '0' COMMENT '班次对应的实际退票数',
  `statTotalNum` int(4) DEFAULT '0' COMMENT '从收入统计中获取的总购票数',
  `buyNum` int(4) DEFAULT '0' COMMENT '(相加得到的)班次总购票数',
  `attendance` varchar(10) DEFAULT '0.00%' COMMENT '上座率',
  `score` varchar(10) DEFAULT NULL COMMENT '平均分',
  `advOne` varchar(40) DEFAULT NULL COMMENT '建议一',
  `advTwo` varchar(40) DEFAULT NULL COMMENT '建议二',
  `advThree` varchar(40) DEFAULT NULL COMMENT '建议三',
  PRIMARY KEY (`finacialId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新版财务实体(V2.2)';

DROP TABLE IF EXISTS `user_id_map`;
CREATE TABLE `user_id_map` (
  `localId` varchar(255) NOT NULL COMMENT '存储主键',
  `passengerId` varchar(255) NOT NULL COMMENT '乘客id',
  `clientId` varchar(255) NOT NULL COMMENT '第三方id',
  `channelId` varchar(255) DEFAULT NULL COMMENT '渠道号',
  PRIMARY KEY (`localId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP PROCEDURE IF EXISTS `p_order_changeStatus`$$

CREATE DEFINER=`admin`@`%` PROCEDURE `p_order_changeStatus`()
BEGIN


DECLARE v_leaseOrderid VARCHAR(50);


DECLARE v_leaseOrderTime 

VARCHAR(50);


DECLARE v_leaseOrderNo VARCHAR(50);


DECLARE STOP INT DEFAULT 0;

DECLARE cur CURSOR FOR (SELECT leaseOrderid,leaseOrderTime,leaseOrderNo FROM `lease_base_info` WHERE payStatus = '0');


DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET STOP = 1; 


OPEN cur;

FETCH cur INTO v_leaseOrderid,v_leaseOrderTime,v_leaseOrderNo;

         WHILE STOP <> 1 DO


IF (ROUND((UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(v_leaseOrderTime))/60)) > (SELECT MIN(orderValiteTime)  FROM sys_app_setting) THEN 

UPDATE `lease_base_info` SET payStatus = '3' WHERE leaseOrderid = v_leaseOrderid AND payStatus <> 1;
UPDATE `passenger_discount_info` SET disstatus = '0',orderno = '' WHERE orderno = v_leaseOrderNo;
UPDATE amwellcustomline.`coupon_group_passenger_detail` SET useState = 'unused',orderId = NULL,useTime = NULL WHERE orderId = (SELECT leaseOrderNo FROM amwellcustomline.`lease_base_info` WHERE leaseOrderid = v_leaseOrderid AND payStatus <> 1);

END IF;


     FETCH cur INTO v_leaseOrderid,v_leaseOrderTime,v_leaseOrderNo;
     
        END WHILE;
        
        
        CLOSE cur;             


    END$$

DELIMITER ;

DELIMITER $$

USE `amwellcustomline`$$

DROP FUNCTION IF EXISTS `f_getFreeSeatCount`$$

CREATE DEFINER=`admin`@`%` FUNCTION `f_getFreeSeatCount`(Ctime VARCHAR(50),Cdate VARCHAR(50), Lid VARCHAR(50)) RETURNS INT(11)
    DETERMINISTIC
BEGIN  
DECLARE cid VARCHAR(50) DEFAULT '';  
DECLARE buyCount INT DEFAULT 0; 
DECLARE totalCount INT DEFAULT 0;  
DECLARE totalCount_temp INT DEFAULT -1;  
DECLARE result INT DEFAULT -1; 
SET cid = (SELECT lineClassId FROM line_class_info WHERE orderStartTime = Ctime AND orderDate = Cdate AND linebaseid = Lid  AND delflag = '0');  
IF cid = '' || cid IS NULL THEN  
SET result := result;  
ELSE 
SET totalCount_temp = (SELECT orderSeats FROM line_class_info WHERE lineClassId = cid AND delflag = '0');
IF totalCount_temp <> '' && totalCount_temp IS NOT NULL THEN  
SET totalCount := totalCount_temp; 
ELSE
RETURN result;
END IF; 
SET buyCount = (SELECT COUNT(tickets) AS useredSeat FROM line_business_order WHERE lineClassId = cid AND leaseOrderId IN (SELECT leaseOrderNo FROM lease_base_info WHERE payStatus IN(0,1) OR ispay = '1') AND STATUS <> '4');  
SET result = totalCount - buyCount;
END IF;  
RETURN result;  
END$$

DELIMITER ;


ALTER TABLE `amwellcustomline`.`app_version`   
    ADD COLUMN `state` INT(4) DEFAULT 0  NULL  COMMENT '使用状态:0,使用  1,删除' AFTER `appType`;

ALTER TABLE `stat_financial`
ADD COLUMN `provinceCode`  varchar(10) NULL COMMENT '省份编码' AFTER `advThree`,
ADD COLUMN `cityCode`  varchar(10) NULL COMMENT '城市编码' AFTER `provinceCode`,
ADD COLUMN `cityName`  varchar(40) NULL COMMENT '城市名称' AFTER `cityCode`;

ALTER TABLE `stat_financial`
ADD COLUMN `telephone`  varchar(20) NULL COMMENT '司机电话' AFTER `cityName`;

ALTER TABLE `amwellcustomline`.`passenger_info`   
ADD COLUMN `sessionId` VARCHAR(150) NULL  COMMENT 'sessionId' AFTER `IMEI`;

ALTER TABLE `amwellcustomline`.`driver_info`   
ADD COLUMN `sessionid` VARCHAR(150) NULL  COMMENT 'sessionid' AFTER `chinapayNo`;

ALTER TABLE `lease_base_info`
ADD COLUMN `thirdparty`  varchar(100) NULL COMMENT '第三方交易订单号' AFTER `ispay`;

INSERT INTO sys_init_data VALUES('xinbancaiwu','新版财务数据恢复','N','0');
UPDATE `sys_power` SET NAME = '我的定制线路',sortflag = 1,fid='line2' WHERE url='../line/getPublishedLines';
UPDATE `sys_power` SET sortflag='2' WHERE url='../line/getAllLines';
UPDATE sys_init_data SET isExecute = 'Y' WHERE dataId IN ('powerdataone','xinbancaiwu');




