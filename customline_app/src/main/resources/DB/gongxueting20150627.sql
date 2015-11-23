
-- 司机端表变动

ALTER TABLE  `driver_info`  ADD COLUMN `displayId` VARCHAR(35) NULL COMMENT '司机回显编号' AFTER `updateOn`;
ALTER TABLE  `driver_info`  ADD COLUMN `headerPicUrl` VARCHAR(256) NULL COMMENT '司机头像URL' AFTER `displayId`;
ALTER TABLE  `driver_info`  ADD COLUMN `praisedNum` INT(11) NULL DEFAULT 0 COMMENT '司机被点赞总次数' AFTER `headerPicUrl`;
ALTER TABLE  `driver_info`  ADD COLUMN `dispatchNum` INT(11) NULL DEFAULT 0 COMMENT '司机服务总次数' AFTER `praisedNum`;
ALTER TABLE  `driver_info`  ADD COLUMN `awardedNum` INT(11) NULL DEFAULT 0 COMMENT '司机获奖励总次数' AFTER `dispatchNum`;
ALTER TABLE  `driver_info`  ADD COLUMN `totalAmount` DECIMAL(6,2) NULL DEFAULT 0.00 COMMENT '司机账户总金额' AFTER `awardedNum`;
ALTER TABLE  `driver_info`  ADD COLUMN `IMEI` VARCHAR(100) NULL COMMENT '设备号' AFTER `totalAmount`;
ALTER TABLE  `driver_info`  ADD COLUMN `terminal` VARCHAR(5) NULL COMMENT '登录设备类型（1-android 2-ios 3-微信）' AFTER `IMEI`;
ALTER TABLE  `driver_info`  ADD COLUMN `comments` VARCHAR(256) NULL COMMENT '开发人员备用字段' AFTER `terminal`;
ALTER TABLE  `driver_info`  ADD COLUMN `alipayNo` VARCHAR(256) NULL COMMENT '支付宝账号' AFTER `comments`;
ALTER TABLE  `driver_info`  ADD COLUMN `wechatNo` VARCHAR(256) NULL COMMENT '微信账号' AFTER `alipayNo`;
ALTER TABLE  `driver_info`  ADD COLUMN `chinapayNo` VARCHAR(256) NULL COMMENT '银联账号' AFTER `wechatNo`;

ALTER TABLE  `line_class_info`  ADD COLUMN `dispatchStatus` TINYINT(4) NULL DEFAULT 0 COMMENT '发车状态（0.未发车 1.已发车 2.行程结束）' AFTER `delFlag`;
ALTER TABLE  `line_class_info`  ADD COLUMN `currentStationName` VARCHAR(64) NULL COMMENT '当前站名' AFTER `dispatchStatus`;
ALTER TABLE  `line_class_info`  ADD COLUMN `totalTime` VARCHAR(64) NULL COMMENT '实际行程总时长(单位分钟)' AFTER `currentStationName`;
ALTER TABLE  `line_class_info`  ADD COLUMN `isDelay` TINYINT(4) NULL DEFAULT '0' COMMENT '是否延误（0.未延误 1.延误）' AFTER `totalTime`;
ALTER TABLE  `line_class_info`  ADD COLUMN `delayMsgId` VARCHAR(35) NULL COMMENT '延误消息id' AFTER `isDelay`;
ALTER TABLE  `line_class_info`  ADD COLUMN `delayTime` VARCHAR(35) NULL COMMENT '延误时长(单位分钟)' AFTER `delayMsgId`;

ALTER TABLE  `line_business_order`  ADD COLUMN `isAboard` TINYINT(4) NULL DEFAULT 0 COMMENT '是否确认上车（0.否 1.是）' AFTER `cdate`;
ALTER TABLE  `line_business_order`  ADD COLUMN `aboardTime` VARCHAR(64) NULL COMMENT '上车时间' AFTER `isAboard`;

ALTER TABLE  `ios_version`  ADD COLUMN `appType` TINYINT(2) NULL DEFAULT 1 COMMENT '应用类型（1.乘客端 2.司机端）' AFTER `version`;

ALTER TABLE sys_msg_info MODIFY COLUMN msgSubType tinyint(4) NULL COMMENT '消息子类型 --->软件消息:待定 --->系统消息 0:系统消息 1:人工消息 [2.公司通知 3.系统通知 4.奖励通知 5.行程通知]->用于司机端';
ALTER TABLE sys_msg_info MODIFY COLUMN theModule tinyint(4) NOT NULL DEFAULT 1 COMMENT '所属模块(1.小猪巴士 2.包车 3.拼车 4.司机端)';


UPDATE sys_power SET url = '../operationDriver/driverManager' WHERE powerId = '140925144711606006';
INSERT INTO `amwellcustomline`.`sys_power` 
	(`powerId`, `code`, `name`, `fid`, `sysType`, `url`, `createBy`, `createOn`, `sortFlag`)
	VALUES
	('150826200439927011', 'driver1', '司机列表', 'user2', '0', '../operationDriver/driverList', '系统生成', '2015-06-19 10:30:00', 1);

INSERT INTO `amwellcustomline`.`sys_power` 
	(`powerId`, `code`, `name`, `fid`, `sysType`, `url`, `createBy`, `createOn`, `sortFlag`)
	VALUES
	('150826200439927012', 'driver2', '提现申请', 'user2', '0', '../operationDriver/driverApply', '系统生成', '2015-06-19 10:30:00', 2);

INSERT INTO `amwellcustomline`.`sys_power` 
	(`powerId`, `code`, `name`, `fid`, `sysType`, `url`, `createBy`, `createOn`, `sortFlag`)
	VALUES
	('150826200439927013', 'driver3', '发放奖励', 'user2', '0', '../operationDriver/driverPayment', '系统生成', '2015-06-19 10:30:00', 3);

INSERT INTO `amwellcustomline`.`sys_roletopower` 
	(`roleId`, `powerId`, `sysType`)
	VALUES
	('140925144712826023', '150826200439927011', '0');

INSERT INTO `amwellcustomline`.`sys_roletopower` 
	(`roleId`, `powerId`, `sysType`)
	VALUES
	('140925144712826023', '150826200439927012', '0');
	
INSERT INTO `amwellcustomline`.`sys_roletopower` 
	(`roleId`, `powerId`, `sysType`)
	VALUES
	('140925144712826023', '150826200439927013', '0');


CREATE TABLE `driver_account_detail` (
  `id` VARCHAR(35) NOT NULL COMMENT '账户明细ID',
  `driverId` VARCHAR(35) NOT NULL COMMENT '司机ID',
  `handleType` TINYINT(4) NULL COMMENT '操作类型（1.收入 2.提现）',
  `incomeType` TINYINT(4) NULL COMMENT '收入来源（1.活动奖励 2.点赞奖励）',
  `withdrawType` TINYINT(4) NULL COMMENT '提现方式（1.支付宝 2.微信 3.银联）',
  `amount` DECIMAL(6,2) NULL COMMENT '金额',
  `handleTime` VARCHAR(64) NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='账户明细表';


CREATE TABLE `driver_withdraw_askfor` (
  `id` VARCHAR(35) NOT NULL COMMENT '提现申请ID',
  `driverId` VARCHAR(35) NOT NULL COMMENT '司机ID',
  `withdrawType` TINYINT(4) NULL COMMENT '提现方式（1.支付宝 2.微信 3.银联）',
  `withdrawAccount` VARCHAR(100) NULL COMMENT '提现账户',
  `driverName` VARCHAR(64) NULL COMMENT '司机姓名',
  `amount` DECIMAL(6,2) DEFAULT NULL COMMENT '提现金额',
  `askforTime` VARCHAR(64) NULL COMMENT '申请时间',
  `handleType` TINYINT(4) NULL DEFAULT 0 COMMENT '申请处理状态（0.未处理 1.已处理）',
  `handleBy` VARCHAR(64) NULL COMMENT '处理人id',
  `handleOn` VARCHAR(64) NULL COMMENT '处理时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='司机提现申请表';


CREATE TABLE `driver_comment_info` (
  `id` VARCHAR(35) NOT NULL COMMENT '评论ID',
  `driverId` VARCHAR(35) NOT NULL COMMENT '司机ID',
  `passengerId` VARCHAR(35) NOT NULL COMMENT '乘客ID',
  `headerPicUrl` VARCHAR(256) NULL COMMENT '乘客头像URL',
  `commentType` TINYINT(4) NULL COMMENT '评论类型（1.点赞 2.文字评论）',
  `commentContent` VARCHAR(256) NULL COMMENT '评论内容',
  `commentTime` VARCHAR(64) NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='司机评论表';


CREATE TABLE `driver_feedback` (
  `feedbackId` VARCHAR(35) NOT NULL COMMENT '反馈ID',
  `driverId` VARCHAR(35) NOT NULL COMMENT '司机ID',
  `feedbackTime` VARCHAR(64) NOT NULL COMMENT '反馈时间',
  `feedbackContext` TEXT COMMENT '反馈内容',
  `phoneNo` VARCHAR(16) DEFAULT '' COMMENT '用户联系方式',
  PRIMARY KEY (`feedbackId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='司机反馈表';


CREATE TABLE delay_msg_info(
   delayMsgId int(11) NOT NULL AUTO_INCREMENT COMMENT '消息id',
   msgTitle VARCHAR(256) NOT NULL COMMENT '消息标题',
   msgContent VARCHAR(256) NOT NULL COMMENT '消息内容',
   delayLevel TINYINT(4) NOT NULL COMMENT '延迟级别（1.延误5分钟 2.延误10分钟 3.延误超过20分钟 4.故障）',
   delayTime INT(11) DEFAULT NULL COMMENT '延迟时间（分钟）',
   PRIMARY KEY(delayMsgId)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '延误消息模版表';
INSERT INTO `amwellcustomline`.`delay_msg_info` (`msgTitle`, `msgContent`, `delayLevel`, `delayTime`) VALUES ('延误5分钟','延误5分钟',1,5);
INSERT INTO `amwellcustomline`.`delay_msg_info` (`msgTitle`, `msgContent`, `delayLevel`, `delayTime`) VALUES ('延误10分钟','延误10分钟',2,10);
INSERT INTO `amwellcustomline`.`delay_msg_info` (`msgTitle`, `msgContent`, `delayLevel`, `delayTime`) VALUES ('延误超过20分钟','延误超过20分钟',3,20);
INSERT INTO `amwellcustomline`.`delay_msg_info` (`msgTitle`, `msgContent`, `delayLevel`, `delayTime`) VALUES ('故障','故障',4,null);

CREATE TABLE `driver_app_version` (
  `appId` VARCHAR(35) NOT NULL COMMENT '主键ID',
  `vsn` VARCHAR(64) NOT NULL COMMENT '版本号',
  `type` TINYINT(4) DEFAULT '0' COMMENT '0:小猪巴士司机端APP  1:I蛇口APP',
  `url` VARCHAR(256) DEFAULT '' COMMENT 'apk存放文件服务器路径  IOS作为预留',
  `vtime` VARCHAR(64) DEFAULT '' COMMENT '版本发布时间',
  `flag` TINYINT(4) DEFAULT NULL COMMENT '版本编码',
  PRIMARY KEY (`appId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='司机端版本控制表';


-- im相关表

CREATE TABLE `im_user_info` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `passengerId` varchar(35) DEFAULT NULL COMMENT '乘客ID',
  `userMark` tinyint(2) DEFAULT NULL COMMENT '用户标识（1.乘客 2.司机）',
  PRIMARY KEY (`userId`),
  KEY `passengerId` (`passengerId`)
) ENGINE=InnoDB AUTO_INCREMENT=1020 DEFAULT CHARSET=utf8 COMMENT='用户信息表';


CREATE TABLE `im_friend_info` (
  `friendInfoId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `friendId` int(11) NOT NULL,
  `createTime` int(8) DEFAULT NULL,
  `remark` char(200) DEFAULT NULL,
  `verify` int(1) DEFAULT NULL,
  `extern` char(32) DEFAULT NULL,
  PRIMARY KEY (`friendInfoId`),
  KEY `userId` (`userId`),
  KEY `friendId` (`friendId`),
  CONSTRAINT `fk_fri_fri` FOREIGN KEY (`friendId`) REFERENCES `im_user_info` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `fk_fri_user` FOREIGN KEY (`userId`) REFERENCES `im_user_info` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `im_group_info` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT COMMENT '圈子id',
  `createBy` varchar(35) NOT NULL COMMENT '创建人id',
  `gName` varchar(32) DEFAULT NULL COMMENT '圈子名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '圈子头像',
  `gSite` varchar(200) DEFAULT NULL COMMENT '圈子位置(经纬度”XX,XX”)',
  `gmaxNum` int(5) DEFAULT NULL COMMENT '最大人数',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注介绍',
  `createTime` int(8) DEFAULT NULL COMMENT '创建时间',
  `isVerify` tinyint(1) DEFAULT NULL COMMENT '是否身份验证(0:否，1:是)',
  `verifyInfo` varchar(32) DEFAULT NULL COMMENT '验证信息',
  `extern` varchar(32) DEFAULT NULL COMMENT '扩展',
  `lineBaseId` varchar(35) DEFAULT NULL COMMENT '线路id',
  PRIMARY KEY (`groupId`),
  KEY `greateBy` (`createBy`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='圈子信息';


CREATE TABLE `im_ug_info` (
  `ugInfoId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户圈子关系表id',
  `groupId` int(11) NOT NULL COMMENT '圈子id',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `userLevel` tinyint(2) DEFAULT NULL COMMENT '用户等级(0:超级管理员，1:普通用户)',
  `userNickName` varchar(32) DEFAULT NULL COMMENT '用户在圈子昵称',
  `isTop` tinyint(2) DEFAULT NULL COMMENT '是否置顶(0:否,1:是)',
  `isDisturb` tinyint(2) DEFAULT NULL COMMENT '是否免打扰(0:否,1:是)',
  `createTime` int(8) DEFAULT NULL COMMENT '添加时间',
  `extern` varchar(32) DEFAULT NULL COMMENT '扩展',
  `stationMark` varchar(35) DEFAULT NULL COMMENT '线路站点id',
  PRIMARY KEY (`ugInfoId`),
  KEY `groupId` (`groupId`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  CONSTRAINT `fk_ug_grop` FOREIGN KEY (`groupId`) REFERENCES `im_group_info` (`groupId`) ON DELETE CASCADE,
  CONSTRAINT `fk_ug_user` FOREIGN KEY (`userId`) REFERENCES `im_user_info` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户圈子关系表';


CREATE TABLE `im_gcomplain_info` (
  `gcomplainId` int(11) NOT NULL AUTO_INCREMENT COMMENT '圈子投诉信息id',
  `groupId` int(11) NOT NULL COMMENT '圈子id',
  `complainBy` varchar(35) NOT NULL COMMENT '投诉人id',
  `complainTime` int(8) DEFAULT NULL COMMENT '投诉时间',
  `complainType` tinyint(2) DEFAULT NULL COMMENT '投诉类型',
  `complainInfo` varchar(1000) DEFAULT NULL COMMENT '投诉信息',
  `extern` varchar(32) DEFAULT NULL COMMENT '扩展',
  PRIMARY KEY (`gcomplainId`),
  KEY `groupId` (`groupId`) USING BTREE,
  KEY `complainBy` (`complainBy`) USING BTREE,
  CONSTRAINT `fk_gcom_grop` FOREIGN KEY (`groupId`) REFERENCES `im_group_info` (`groupId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='圈子投诉信息';


CREATE TABLE `im_off_line_info` (
  `offLineId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `infoType` tinyint(2) DEFAULT NULL COMMENT '消息类型(0:文本,1:图片,2:语音文件,3:视频文件，5名片，6位置，7:线路，8:url)',
  `infoTime` int(8) DEFAULT NULL COMMENT '消息时间',
  `infoContext` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  PRIMARY KEY (`offLineId`)
) ENGINE=InnoDB AUTO_INCREMENT=776 DEFAULT CHARSET=utf8 COMMENT='离线待发消息';

CREATE TABLE `im_p2p_off_line_info` (
  `p2pId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `MsgType` int(11) DEFAULT NULL,
  `cmd` int(11) DEFAULT NULL,
  `offLineInfoId` int(11) DEFAULT NULL COMMENT '消息id',
  `from` int(11) DEFAULT NULL COMMENT '消息来源',
  `destination` int(11) DEFAULT NULL COMMENT '消息目的地',
  `extern` varchar(32) NOT NULL DEFAULT '' COMMENT '扩展',
  `insertTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  PRIMARY KEY (`p2pId`,`extern`),
  KEY `offLineInfoId` (`offLineInfoId`) USING BTREE,
  KEY `from` (`from`) USING BTREE,
  KEY `destination` (`destination`),
  CONSTRAINT `fk_p2p_goff` FOREIGN KEY (`offLineInfoId`) REFERENCES `im_off_line_info` (`offLineId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=767 DEFAULT CHARSET=utf8 COMMENT='点对点离线消息';

CREATE TABLE `im_goff_line_info` (
  `goffLineInfoId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `offLineId` int(11) DEFAULT NULL COMMENT '消息id',
  `groupId` int(11) DEFAULT NULL COMMENT '圈子id',
  `from` int(11) DEFAULT NULL COMMENT '消息来源',
  `destination` int(11) DEFAULT NULL COMMENT '消息目的地',
  `extern` varchar(32) DEFAULT NULL COMMENT '扩展',
  PRIMARY KEY (`goffLineInfoId`),
  KEY `offLineId` (`offLineId`),
  KEY `groupId` (`groupId`),
  KEY `from` (`from`),
  KEY `destination` (`destination`),
  CONSTRAINT `fk_goff_grop` FOREIGN KEY (`groupId`) REFERENCES `im_group_info` (`groupId`) ON DELETE CASCADE,
  CONSTRAINT `fk_goff_off` FOREIGN KEY (`offLineId`) REFERENCES `im_off_line_info` (`offLineId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='圈子离线消息';

CREATE TABLE `im_system_info` (
  `systemId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `systemInfoType` tinyint(2) DEFAULT NULL COMMENT '消息类型(0:文字，1:图片)',
  `systemInfo` varchar(200) DEFAULT NULL COMMENT '系统内容',
  `extern` varchar(32) DEFAULT NULL COMMENT '扩展',
  PRIMARY KEY (`systemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统消息';



DELIMITER $$

USE `amwellcustomline`$$

DROP PROCEDURE IF EXISTS `p_change_im_group_user`$$

CREATE DEFINER=`admin`@`%` PROCEDURE `p_change_im_group_user`()
BEGIN
	DECLARE v_groupId VARCHAR(50);-- im圈子id
	
	DECLARE v_lineBaseId VARCHAR(50);-- 线路id
	
	DECLARE v_nickName VARCHAR(50);-- 乘客昵称
	
	DECLARE v_userId VARCHAR(50);-- im用户id
	
	DECLARE STOP_1,STOP_2 INT DEFAULT 0;
	
	-- 查询圈子id、线路id
	DECLARE cur_1 CURSOR FOR (SELECT groupId,lineBaseId FROM im_group_info);
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET STOP_1 = 1; 
	-- 删除所有的圈子用户关系信息
        TRUNCATE TABLE im_ug_info;
	
	OPEN cur_1;
	
	FETCH cur_1 INTO v_groupId,v_lineBaseId;
		
         WHILE STOP_1 <> 1 DO-- 遍历圈子id、线路id
             
             -- -------cur_2 start---------------------------------------------------------------- 
             BEGIN
		     -- 根据线路id查询新一天的买票的用户信息
		     DECLARE cur_2 CURSOR FOR (SELECT p.nickName,iui.userId FROM lease_base_info lbi,line_business_order lbo,line_class_info lci,passenger_info p,im_user_info iui WHERE lbi.lineBaseId = v_lineBaseId AND lbi.ispay = '1' 
	AND lbi.leaseOrderNo = lbo.leaseOrderId AND lbo.lineClassId = lci.lineClassId AND lci.orderDate = CURDATE() AND lbi.passengerId = p.passengerId AND p.passengerId = iui.passengerId);
		     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET STOP_2 = 1; 
             
                     OPEN cur_2;
                        
                         FETCH cur_2 INTO v_nickName,v_userId;
                         
                         WHILE STOP_2 <> 1 DO-- 遍历用户信息
			
                           -- 将乘客加入圈子
			    INSERT INTO `im_ug_info` (`groupId`, `userId`, `userLevel`,`userNickName`,`isTop`, `isDisturb`, `createTime`,`stationMark`) VALUES(v_groupId,v_userId,'1',v_nickName,'0','0',UNIX_TIMESTAMP(),'0');
			   
                           FETCH cur_2 INTO v_nickName,v_userId;                         
                         
                         END WHILE;                    
                     
                     CLOSE cur_2;
                     
                     SET STOP_2=0;
                          
             END;
             -- -------cur_2 end---------------------------------------------------------------- 	     
	     
	     FETCH cur_1 INTO v_groupId,v_lineBaseId;
	     
        END WHILE;
        
        CLOSE cur_1;             	
	
    END$$

DELIMITER ;




DELIMITER $$

create EVENT `e_change_im_group_user` ON SCHEDULE EVERY 1 DAY STARTS '2015-06-01 01:00:00' ON COMPLETION PRESERVE ENABLE DO CALL p_change_im_group_user$$

DELIMITER ;



-- 推荐有奖数据库改动

ALTER TABLE  `sys_app_setting`  ADD COLUMN `recommendAwardSwitch` TINYINT(4) NULL DEFAULT 1 COMMENT '推荐有奖开关：0.关 1.开' AFTER `returnTicketFree`;

ALTER TABLE gf_gifts MODIFY COLUMN giftType TINYINT(4) NOT NULL DEFAULT 1 COMMENT '礼类类型:1:通用型礼品（班车、包车、拼车通用）2:上下班班车礼品 3:包车礼品 4:拼车礼品 5.推荐有奖礼品';

ALTER TABLE gf_coupon MODIFY COLUMN selectPass TINYINT(4) NOT NULL DEFAULT 0 COMMENT '选择用户  0:全部用户  1:新用户  2:15日未登录用户   3:自定义用户 4.推荐有奖用户';


CREATE TABLE `recommend_award_set` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `newUserGiftId` varchar(35) NOT NULL COMMENT '拉新用户礼品ID',
  `oldUserGiftId` varchar(35) NOT NULL COMMENT '老用户礼品ID',
  `actionRule` text NOT NULL COMMENT '推荐有奖活动规则',
  `setBy` varchar(64) DEFAULT NULL COMMENT '设置人Id',
  `setOn` varchar(64) DEFAULT NULL COMMENT '设置时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='推荐有奖设置表'



CREATE TABLE `recommend_award_info` (
  `id` VARCHAR(32) NOT NULL,
  `oldUserPhone` VARCHAR(16) NOT NULL COMMENT '老用户手机号码',
  `newUserPhone` VARCHAR(16) NOT NULL COMMENT '新用户手机号码',
  `oldUserGiftId` VARCHAR(35) NOT NULL COMMENT '老用户礼品ID',
  `newUserGiftId` VARCHAR(35) NOT NULL COMMENT '拉新用户礼品ID',
  `createOn` VARCHAR(64) NULL COMMENT '时间',
  `delFlag` tinyint(4) DEFAULT '0' COMMENT '状态：0.正常 1.过期  2.新人已注册登录  3.新人首次买票',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='推荐有奖明细表';



INSERT INTO `amwellcustomline`.`sys_power` 
	(`powerId`, `code`, `name`, `fid`, `sysType`, `url`,iconUrl, `createBy`, `createOn`, `sortFlag`)
	VALUES
	('150826200439927014', 'help4', '推荐有奖设置', 'help', '0', '../recommendAward/forwardSetPage','leftico leftico19 fl', '系统生成', '2015-06-27 10:30:00', 4);


	INSERT INTO `amwellcustomline`.`sys_roletopower` 
	(`roleId`, `powerId`, `sysType`)
	VALUES
	('140925144712826023', '150826200439927014', '0');


DELIMITER $$

USE `amwellcustomline`$$

DROP PROCEDURE IF EXISTS `p_recommend_award_del`$$

CREATE DEFINER=`admin`@`%` PROCEDURE `p_recommend_award_del`()
BEGIN
	
	
	DECLARE v_recommend_award_id VARCHAR(50);
	
	
	DECLARE v_recommend_award_create_on VARCHAR(50);
	
	
	DECLARE STOP INT DEFAULT 0;
	
	DECLARE cur CURSOR FOR (SELECT id,createOn FROM recommend_award_info WHERE delFlag = '0');
	
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET STOP = 1; 
	
	
	OPEN cur;
	
	FETCH cur INTO v_recommend_award_id,v_recommend_award_create_on;
		
         WHILE STOP <> 1 DO
         
                -- 将状态为0的超过7日的推荐信息状态改为失效
		
		IF (UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(v_recommend_award_create_on)) > 7*24*60*60 THEN 
			
			UPDATE recommend_award_info SET delFlag = '1' WHERE id = v_recommend_award_id;
				
		END IF;
             
              
	     FETCH cur INTO v_recommend_award_id,v_recommend_award_create_on;
	     
        END WHILE;
        
        
        CLOSE cur;             
		
	
    END$$

DELIMITER ;




DELIMITER $$

CREATE EVENT `e_recommend_award_del` ON SCHEDULE EVERY 1 DAY STARTS '2015-06-27 02:00:00' ON COMPLETION PRESERVE ENABLE DO CALL p_recommend_award_del$$

DELIMITER ;
