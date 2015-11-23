CREATE TABLE `line_cost_set` (
`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
`lineBaseId` VARCHAR(35) DEFAULT NULL COMMENT '线路id',
`theMode` VARCHAR(16) DEFAULT NULL COMMENT '成本分摊方式（fixed base price:固定底价 commission:佣金分成）',
`costSharing` DECIMAL(10,2) DEFAULT NULL COMMENT '成本分摊（固定底价时输入数字，佣金分成是输入百分数）',
`startTime` VARCHAR(10) DEFAULT NULL COMMENT '有效期开始时间（日期）',
`endTime` VARCHAR(10) DEFAULT NULL COMMENT '有效期结束时间（日期）',
`createBy` VARCHAR(35) DEFAULT NULL COMMENT '创建人',
`createOn` VARCHAR(19) DEFAULT NULL COMMENT '创建时间',
`updateBy` VARCHAR(35) DEFAULT NULL COMMENT '修改人',
`updateOn` VARCHAR(19) DEFAULT NULL COMMENT '修改时间',
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='线路成本设置表';


CREATE TABLE `line_cost_set_history` (
`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
`lineBaseId` VARCHAR(35) DEFAULT NULL COMMENT '线路id',
`theMode` VARCHAR(16) DEFAULT NULL COMMENT '成本分摊方式（fixed base price:固定底价 commission:佣金分成）',
`costSharing` DECIMAL(10,2) DEFAULT NULL COMMENT '成本分摊（固定底价时输入数字，佣金分成是输入百分数）',
`startTime` VARCHAR(10) DEFAULT NULL COMMENT '有效期开始时间（日期）',
`endTime` VARCHAR(10) DEFAULT NULL COMMENT '有效期结束时间（日期）',
`createBy` VARCHAR(35) DEFAULT NULL COMMENT '创建人',
`createOn` VARCHAR(19) DEFAULT NULL COMMENT '创建时间',
`updateBy` VARCHAR(35) DEFAULT NULL COMMENT '修改人',
`updateOn` VARCHAR(19) DEFAULT NULL COMMENT '修改时间',
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='线路成本设置历史表';


UPDATE sys_power SET NAME = '供应商管理' WHERE NAME = '用户管理';
UPDATE sys_power SET NAME = '用户、订单管理' WHERE NAME = '订单管理';
UPDATE sys_power SET NAME = '用户管理' WHERE NAME = '乘客管理';
UPDATE sys_power SET NAME = '改签' WHERE NAME = '车票改签';
UPDATE sys_power SET NAME = '退票' WHERE NAME = '车票退票';
UPDATE sys_power SET NAME = '站内信' WHERE NAME = 'app站内信管理';
UPDATE sys_power SET NAME = 'app消息' WHERE NAME = 'app推送消息';
UPDATE sys_power SET NAME = '优惠券统计' WHERE NAME = '优惠券发放统计';
UPDATE sys_power SET NAME = '财务管理' WHERE NAME = '财务报表';
UPDATE sys_power SET NAME = '供应商结算表' WHERE NAME = '供应商结算明细';
UPDATE sys_power SET NAME = '客户投诉' WHERE NAME = '帮助&反馈';
UPDATE sys_power SET NAME = '操作日志' WHERE NAME = '操作日志管理';

DELETE FROM sys_roletopower WHERE powerId = (SELECT powerId FROM sys_power WHERE NAME = '系统运营功能');

ALTER TABLE `apply_return`
ADD COLUMN `payModel`  tinyint(4) NULL DEFAULT 0 COMMENT '支付方式 0：无 1：余额支付，2：财付通 3：银联 4：支付宝 5:微信 6:(APP)微信' AFTER `operatior`;

UPDATE apply_return AS a SET payModel = ( SELECT b.payModel FROM lease_pay AS b WHERE b.leaseOrderNo = a.leaseOrderNo );

update sys_init_data set isExecute='Y' where dataId='powerdataone';

#站点的到站时刻
ALTER TABLE `pb_station`
ADD COLUMN `arriveTime`  varchar(128) NOT NULL AFTER `name`;

#线路是否自动放票
ALTER TABLE `line_base_info`
ADD COLUMN `autoPutTicket`  varchar(255) NULL COMMENT '自动放票  true-是 false-否' AFTER `fullStationInfoesMask`;

#线路操作统计
CREATE TABLE `amwellcustomline`.`line_operation_log`(  
  `operationId` VARCHAR(32) NOT NULL COMMENT 'ID',
  `lineBaseId` varchar(32) DEFAULT NULL COMMENT '线路ID',
  `content` VARCHAR(128) COMMENT '操作内容',
  `updateBy` VARCHAR(32) COMMENT '操作人',
  `updateOn` VARCHAR(32) COMMENT '操作时间',
  PRIMARY KEY (`operationId`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE `amwellcustomline`.`line_base_info`   
  CHANGE `passengerTel` `lineManager` VARCHAR(32) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '线路运营人员';


ALTER TABLE `line_user_application_new`
ADD COLUMN `isHandle` varchar(255) NULL COMMENT '是否已处理 1是 0不是' AFTER `applicationTime`;

ALTER TABLE `line_enroll_user_new`
ADD INDEX `applicationKey` (`applicationId`) USING BTREE ;

ALTER TABLE `line_user_application_new`
ADD INDEX `applicationTimeKey` (`applicationTime`) USING BTREE ;

ALTER TABLE `line_user_application_station_new`
ADD INDEX `appliStationIdKey` (`appliStationId`) USING BTREE;

ALTER TABLE `passenger_search_line`
ADD INDEX `startnameKey` (`startName`) USING BTREE ,
ADD INDEX `endnameKey` (`endName`) USING BTREE ;

ALTER TABLE `line_user_application_new`
ADD INDEX `isFlagKey` (`isHandle`) USING BTREE ;


ALTER TABLE coupon_group_passenger_detail MODIFY COLUMN useState VARCHAR(7) DEFAULT 'unused' COMMENT '使用状态 unused:未使用 used:已使用 expired:已过期 hidden:隐藏(过期超过七天app页面不可见)';


-- /////////////////////////////////////////////////////

DELIMITER $$

USE `amwellcustomline`$$

DROP PROCEDURE IF EXISTS `p_coupon_expire`$$

CREATE DEFINER=`admin`@`%` PROCEDURE `p_coupon_expire`()
BEGIN


DECLARE v_detail_id VARCHAR(50);


DECLARE STOP INT DEFAULT 0;

-- 查询未使用但已过期的优惠券信息
DECLARE cur CURSOR FOR (SELECT id FROM coupon_group_passenger_detail WHERE useState = 'unused' AND expirationTime < CURDATE());


DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET STOP = 1; 


OPEN cur;

FETCH cur INTO v_detail_id;

WHILE STOP <> 1 DO


UPDATE coupon_group_passenger_detail SET useState = 'expired' WHERE id = v_detail_id;


FETCH cur INTO v_detail_id;

END WHILE;


CLOSE cur; 


END$$

DELIMITER ;


-- /////////////////////////////////////////////////////


DELIMITER $$

USE `amwellcustomline`$$

DROP PROCEDURE IF EXISTS `p_coupon_hidden`$$

CREATE DEFINER=`admin`@`%` PROCEDURE `p_coupon_hidden`()
BEGIN
DECLARE v_detail_id VARCHAR(50);

DECLARE v_expirationTime VARCHAR(50);

DECLARE STOP INT DEFAULT 0;

-- 查询已过期的优惠券信息
DECLARE cur CURSOR FOR (SELECT id,expirationTime FROM coupon_group_passenger_detail WHERE useState = 'expired');

DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET STOP = 1; 

OPEN cur;

FETCH cur INTO v_detail_id,v_expirationTime;

WHILE STOP <> 1 DO

-- 将已过期超过七天的优惠券状态改为hidden，让起在app端不可见
IF (UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(v_expirationTime)) > 7*24*60*60 THEN 

UPDATE coupon_group_passenger_detail SET useState = 'hidden' WHERE id = v_detail_id;

END IF; 

FETCH cur INTO v_detail_id,v_expirationTime;

END WHILE;

CLOSE cur; 

END$$

DELIMITER ;


-- /////////////////////////////////////////////////////

DELIMITER $$

-- 每天凌晨一点半将已到过期时间的优惠券状态改为已过期，并将已过期超过七天的优惠券状态改为隐藏（让其在app端不可见）
CREATE DEFINER=`root`@`localhost` EVENT `e_coupon_changeStatus` ON SCHEDULE EVERY 1 DAY STARTS '2015-08-28 01:30:00' ON COMPLETION PRESERVE ENABLE 
DO 
BEGIN
-- 改为已过期的存储过程
CALL p_coupon_expire();
-- 改为隐藏的存储过程
CALL p_coupon_hidden();
END
$$

DELIMITER ;
