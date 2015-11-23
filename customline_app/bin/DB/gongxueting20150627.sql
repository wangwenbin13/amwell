
-- ˾���˱�䶯

ALTER TABLE  `driver_info`  ADD COLUMN `displayId` VARCHAR(35) NULL COMMENT '˾�����Ա��' AFTER `updateOn`;
ALTER TABLE  `driver_info`  ADD COLUMN `headerPicUrl` VARCHAR(256) NULL COMMENT '˾��ͷ��URL' AFTER `displayId`;
ALTER TABLE  `driver_info`  ADD COLUMN `praisedNum` INT(11) NULL DEFAULT 0 COMMENT '˾���������ܴ���' AFTER `headerPicUrl`;
ALTER TABLE  `driver_info`  ADD COLUMN `dispatchNum` INT(11) NULL DEFAULT 0 COMMENT '˾�������ܴ���' AFTER `praisedNum`;
ALTER TABLE  `driver_info`  ADD COLUMN `awardedNum` INT(11) NULL DEFAULT 0 COMMENT '˾�������ܴ���' AFTER `dispatchNum`;
ALTER TABLE  `driver_info`  ADD COLUMN `totalAmount` DECIMAL(6,2) NULL DEFAULT 0.00 COMMENT '˾���˻��ܽ��' AFTER `awardedNum`;
ALTER TABLE  `driver_info`  ADD COLUMN `IMEI` VARCHAR(100) NULL COMMENT '�豸��' AFTER `totalAmount`;
ALTER TABLE  `driver_info`  ADD COLUMN `terminal` VARCHAR(5) NULL COMMENT '��¼�豸���ͣ�1-android 2-ios 3-΢�ţ�' AFTER `IMEI`;
ALTER TABLE  `driver_info`  ADD COLUMN `comments` VARCHAR(256) NULL COMMENT '������Ա�����ֶ�' AFTER `terminal`;
ALTER TABLE  `driver_info`  ADD COLUMN `alipayNo` VARCHAR(256) NULL COMMENT '֧�����˺�' AFTER `comments`;
ALTER TABLE  `driver_info`  ADD COLUMN `wechatNo` VARCHAR(256) NULL COMMENT '΢���˺�' AFTER `alipayNo`;
ALTER TABLE  `driver_info`  ADD COLUMN `chinapayNo` VARCHAR(256) NULL COMMENT '�����˺�' AFTER `wechatNo`;

ALTER TABLE  `line_class_info`  ADD COLUMN `dispatchStatus` TINYINT(4) NULL DEFAULT 0 COMMENT '����״̬��0.δ���� 1.�ѷ��� 2.�г̽�����' AFTER `delFlag`;
ALTER TABLE  `line_class_info`  ADD COLUMN `currentStationName` VARCHAR(64) NULL COMMENT '��ǰվ��' AFTER `dispatchStatus`;
ALTER TABLE  `line_class_info`  ADD COLUMN `totalTime` VARCHAR(64) NULL COMMENT 'ʵ���г���ʱ��(��λ����)' AFTER `currentStationName`;
ALTER TABLE  `line_class_info`  ADD COLUMN `isDelay` TINYINT(4) NULL DEFAULT '0' COMMENT '�Ƿ�����0.δ���� 1.����' AFTER `totalTime`;
ALTER TABLE  `line_class_info`  ADD COLUMN `delayMsgId` VARCHAR(35) NULL COMMENT '������Ϣid' AFTER `isDelay`;
ALTER TABLE  `line_class_info`  ADD COLUMN `delayTime` VARCHAR(35) NULL COMMENT '����ʱ��(��λ����)' AFTER `delayMsgId`;

ALTER TABLE  `line_business_order`  ADD COLUMN `isAboard` TINYINT(4) NULL DEFAULT 0 COMMENT '�Ƿ�ȷ���ϳ���0.�� 1.�ǣ�' AFTER `cdate`;
ALTER TABLE  `line_business_order`  ADD COLUMN `aboardTime` VARCHAR(64) NULL COMMENT '�ϳ�ʱ��' AFTER `isAboard`;

ALTER TABLE  `ios_version`  ADD COLUMN `appType` TINYINT(2) NULL DEFAULT 1 COMMENT 'Ӧ�����ͣ�1.�˿Ͷ� 2.˾���ˣ�' AFTER `version`;

ALTER TABLE sys_msg_info MODIFY COLUMN msgSubType tinyint(4) NULL COMMENT '��Ϣ������ --->�����Ϣ:���� --->ϵͳ��Ϣ 0:ϵͳ��Ϣ 1:�˹���Ϣ [2.��˾֪ͨ 3.ϵͳ֪ͨ 4.����֪ͨ 5.�г�֪ͨ]->����˾����';
ALTER TABLE sys_msg_info MODIFY COLUMN theModule tinyint(4) NOT NULL DEFAULT 1 COMMENT '����ģ��(1.С���ʿ 2.���� 3.ƴ�� 4.˾����)';


UPDATE sys_power SET url = '../operationDriver/driverManager' WHERE powerId = '140925144711606006';
INSERT INTO `amwellcustomline`.`sys_power` 
	(`powerId`, `code`, `name`, `fid`, `sysType`, `url`, `createBy`, `createOn`, `sortFlag`)
	VALUES
	('150826200439927011', 'driver1', '˾���б�', 'user2', '0', '../operationDriver/driverList', 'ϵͳ����', '2015-06-19 10:30:00', 1);

INSERT INTO `amwellcustomline`.`sys_power` 
	(`powerId`, `code`, `name`, `fid`, `sysType`, `url`, `createBy`, `createOn`, `sortFlag`)
	VALUES
	('150826200439927012', 'driver2', '��������', 'user2', '0', '../operationDriver/driverApply', 'ϵͳ����', '2015-06-19 10:30:00', 2);

INSERT INTO `amwellcustomline`.`sys_power` 
	(`powerId`, `code`, `name`, `fid`, `sysType`, `url`, `createBy`, `createOn`, `sortFlag`)
	VALUES
	('150826200439927013', 'driver3', '���Ž���', 'user2', '0', '../operationDriver/driverPayment', 'ϵͳ����', '2015-06-19 10:30:00', 3);

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
  `id` VARCHAR(35) NOT NULL COMMENT '�˻���ϸID',
  `driverId` VARCHAR(35) NOT NULL COMMENT '˾��ID',
  `handleType` TINYINT(4) NULL COMMENT '�������ͣ�1.���� 2.���֣�',
  `incomeType` TINYINT(4) NULL COMMENT '������Դ��1.����� 2.���޽�����',
  `withdrawType` TINYINT(4) NULL COMMENT '���ַ�ʽ��1.֧���� 2.΢�� 3.������',
  `amount` DECIMAL(6,2) NULL COMMENT '���',
  `handleTime` VARCHAR(64) NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='�˻���ϸ��';


CREATE TABLE `driver_withdraw_askfor` (
  `id` VARCHAR(35) NOT NULL COMMENT '��������ID',
  `driverId` VARCHAR(35) NOT NULL COMMENT '˾��ID',
  `withdrawType` TINYINT(4) NULL COMMENT '���ַ�ʽ��1.֧���� 2.΢�� 3.������',
  `withdrawAccount` VARCHAR(100) NULL COMMENT '�����˻�',
  `driverName` VARCHAR(64) NULL COMMENT '˾������',
  `amount` DECIMAL(6,2) DEFAULT NULL COMMENT '���ֽ��',
  `askforTime` VARCHAR(64) NULL COMMENT '����ʱ��',
  `handleType` TINYINT(4) NULL DEFAULT 0 COMMENT '���봦��״̬��0.δ���� 1.�Ѵ���',
  `handleBy` VARCHAR(64) NULL COMMENT '������id',
  `handleOn` VARCHAR(64) NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='˾�����������';


CREATE TABLE `driver_comment_info` (
  `id` VARCHAR(35) NOT NULL COMMENT '����ID',
  `driverId` VARCHAR(35) NOT NULL COMMENT '˾��ID',
  `passengerId` VARCHAR(35) NOT NULL COMMENT '�˿�ID',
  `headerPicUrl` VARCHAR(256) NULL COMMENT '�˿�ͷ��URL',
  `commentType` TINYINT(4) NULL COMMENT '�������ͣ�1.���� 2.�������ۣ�',
  `commentContent` VARCHAR(256) NULL COMMENT '��������',
  `commentTime` VARCHAR(64) NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='˾�����۱�';


CREATE TABLE `driver_feedback` (
  `feedbackId` VARCHAR(35) NOT NULL COMMENT '����ID',
  `driverId` VARCHAR(35) NOT NULL COMMENT '˾��ID',
  `feedbackTime` VARCHAR(64) NOT NULL COMMENT '����ʱ��',
  `feedbackContext` TEXT COMMENT '��������',
  `phoneNo` VARCHAR(16) DEFAULT '' COMMENT '�û���ϵ��ʽ',
  PRIMARY KEY (`feedbackId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='˾��������';


CREATE TABLE delay_msg_info(
   delayMsgId int(11) NOT NULL AUTO_INCREMENT COMMENT '��Ϣid',
   msgTitle VARCHAR(256) NOT NULL COMMENT '��Ϣ����',
   msgContent VARCHAR(256) NOT NULL COMMENT '��Ϣ����',
   delayLevel TINYINT(4) NOT NULL COMMENT '�ӳټ���1.����5���� 2.����10���� 3.���󳬹�20���� 4.���ϣ�',
   delayTime INT(11) DEFAULT NULL COMMENT '�ӳ�ʱ�䣨���ӣ�',
   PRIMARY KEY(delayMsgId)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '������Ϣģ���';
INSERT INTO `amwellcustomline`.`delay_msg_info` (`msgTitle`, `msgContent`, `delayLevel`, `delayTime`) VALUES ('����5����','����5����',1,5);
INSERT INTO `amwellcustomline`.`delay_msg_info` (`msgTitle`, `msgContent`, `delayLevel`, `delayTime`) VALUES ('����10����','����10����',2,10);
INSERT INTO `amwellcustomline`.`delay_msg_info` (`msgTitle`, `msgContent`, `delayLevel`, `delayTime`) VALUES ('���󳬹�20����','���󳬹�20����',3,20);
INSERT INTO `amwellcustomline`.`delay_msg_info` (`msgTitle`, `msgContent`, `delayLevel`, `delayTime`) VALUES ('����','����',4,null);

CREATE TABLE `driver_app_version` (
  `appId` VARCHAR(35) NOT NULL COMMENT '����ID',
  `vsn` VARCHAR(64) NOT NULL COMMENT '�汾��',
  `type` TINYINT(4) DEFAULT '0' COMMENT '0:С���ʿ˾����APP  1:I�߿�APP',
  `url` VARCHAR(256) DEFAULT '' COMMENT 'apk����ļ�������·��  IOS��ΪԤ��',
  `vtime` VARCHAR(64) DEFAULT '' COMMENT '�汾����ʱ��',
  `flag` TINYINT(4) DEFAULT NULL COMMENT '�汾����',
  PRIMARY KEY (`appId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='˾���˰汾���Ʊ�';


-- im��ر�

CREATE TABLE `im_user_info` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `passengerId` varchar(35) DEFAULT NULL COMMENT '�˿�ID',
  `userMark` tinyint(2) DEFAULT NULL COMMENT '�û���ʶ��1.�˿� 2.˾����',
  PRIMARY KEY (`userId`),
  KEY `passengerId` (`passengerId`)
) ENGINE=InnoDB AUTO_INCREMENT=1020 DEFAULT CHARSET=utf8 COMMENT='�û���Ϣ��';


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
  `groupId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Ȧ��id',
  `createBy` varchar(35) NOT NULL COMMENT '������id',
  `gName` varchar(32) DEFAULT NULL COMMENT 'Ȧ������',
  `icon` varchar(200) DEFAULT NULL COMMENT 'Ȧ��ͷ��',
  `gSite` varchar(200) DEFAULT NULL COMMENT 'Ȧ��λ��(��γ�ȡ�XX,XX��)',
  `gmaxNum` int(5) DEFAULT NULL COMMENT '�������',
  `remark` varchar(200) DEFAULT NULL COMMENT '��ע����',
  `createTime` int(8) DEFAULT NULL COMMENT '����ʱ��',
  `isVerify` tinyint(1) DEFAULT NULL COMMENT '�Ƿ������֤(0:��1:��)',
  `verifyInfo` varchar(32) DEFAULT NULL COMMENT '��֤��Ϣ',
  `extern` varchar(32) DEFAULT NULL COMMENT '��չ',
  `lineBaseId` varchar(35) DEFAULT NULL COMMENT '��·id',
  PRIMARY KEY (`groupId`),
  KEY `greateBy` (`createBy`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='Ȧ����Ϣ';


CREATE TABLE `im_ug_info` (
  `ugInfoId` int(11) NOT NULL AUTO_INCREMENT COMMENT '�û�Ȧ�ӹ�ϵ��id',
  `groupId` int(11) NOT NULL COMMENT 'Ȧ��id',
  `userId` int(11) DEFAULT NULL COMMENT '�û�id',
  `userLevel` tinyint(2) DEFAULT NULL COMMENT '�û��ȼ�(0:��������Ա��1:��ͨ�û�)',
  `userNickName` varchar(32) DEFAULT NULL COMMENT '�û���Ȧ���ǳ�',
  `isTop` tinyint(2) DEFAULT NULL COMMENT '�Ƿ��ö�(0:��,1:��)',
  `isDisturb` tinyint(2) DEFAULT NULL COMMENT '�Ƿ������(0:��,1:��)',
  `createTime` int(8) DEFAULT NULL COMMENT '���ʱ��',
  `extern` varchar(32) DEFAULT NULL COMMENT '��չ',
  `stationMark` varchar(35) DEFAULT NULL COMMENT '��·վ��id',
  PRIMARY KEY (`ugInfoId`),
  KEY `groupId` (`groupId`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  CONSTRAINT `fk_ug_grop` FOREIGN KEY (`groupId`) REFERENCES `im_group_info` (`groupId`) ON DELETE CASCADE,
  CONSTRAINT `fk_ug_user` FOREIGN KEY (`userId`) REFERENCES `im_user_info` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�û�Ȧ�ӹ�ϵ��';


CREATE TABLE `im_gcomplain_info` (
  `gcomplainId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Ȧ��Ͷ����Ϣid',
  `groupId` int(11) NOT NULL COMMENT 'Ȧ��id',
  `complainBy` varchar(35) NOT NULL COMMENT 'Ͷ����id',
  `complainTime` int(8) DEFAULT NULL COMMENT 'Ͷ��ʱ��',
  `complainType` tinyint(2) DEFAULT NULL COMMENT 'Ͷ������',
  `complainInfo` varchar(1000) DEFAULT NULL COMMENT 'Ͷ����Ϣ',
  `extern` varchar(32) DEFAULT NULL COMMENT '��չ',
  PRIMARY KEY (`gcomplainId`),
  KEY `groupId` (`groupId`) USING BTREE,
  KEY `complainBy` (`complainBy`) USING BTREE,
  CONSTRAINT `fk_gcom_grop` FOREIGN KEY (`groupId`) REFERENCES `im_group_info` (`groupId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ȧ��Ͷ����Ϣ';


CREATE TABLE `im_off_line_info` (
  `offLineId` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `infoType` tinyint(2) DEFAULT NULL COMMENT '��Ϣ����(0:�ı�,1:ͼƬ,2:�����ļ�,3:��Ƶ�ļ���5��Ƭ��6λ�ã�7:��·��8:url)',
  `infoTime` int(8) DEFAULT NULL COMMENT '��Ϣʱ��',
  `infoContext` varchar(1000) DEFAULT NULL COMMENT '��Ϣ����',
  PRIMARY KEY (`offLineId`)
) ENGINE=InnoDB AUTO_INCREMENT=776 DEFAULT CHARSET=utf8 COMMENT='���ߴ�����Ϣ';

CREATE TABLE `im_p2p_off_line_info` (
  `p2pId` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `MsgType` int(11) DEFAULT NULL,
  `cmd` int(11) DEFAULT NULL,
  `offLineInfoId` int(11) DEFAULT NULL COMMENT '��Ϣid',
  `from` int(11) DEFAULT NULL COMMENT '��Ϣ��Դ',
  `destination` int(11) DEFAULT NULL COMMENT '��ϢĿ�ĵ�',
  `extern` varchar(32) NOT NULL DEFAULT '' COMMENT '��չ',
  `insertTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`p2pId`,`extern`),
  KEY `offLineInfoId` (`offLineInfoId`) USING BTREE,
  KEY `from` (`from`) USING BTREE,
  KEY `destination` (`destination`),
  CONSTRAINT `fk_p2p_goff` FOREIGN KEY (`offLineInfoId`) REFERENCES `im_off_line_info` (`offLineId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=767 DEFAULT CHARSET=utf8 COMMENT='��Ե�������Ϣ';

CREATE TABLE `im_goff_line_info` (
  `goffLineInfoId` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `offLineId` int(11) DEFAULT NULL COMMENT '��Ϣid',
  `groupId` int(11) DEFAULT NULL COMMENT 'Ȧ��id',
  `from` int(11) DEFAULT NULL COMMENT '��Ϣ��Դ',
  `destination` int(11) DEFAULT NULL COMMENT '��ϢĿ�ĵ�',
  `extern` varchar(32) DEFAULT NULL COMMENT '��չ',
  PRIMARY KEY (`goffLineInfoId`),
  KEY `offLineId` (`offLineId`),
  KEY `groupId` (`groupId`),
  KEY `from` (`from`),
  KEY `destination` (`destination`),
  CONSTRAINT `fk_goff_grop` FOREIGN KEY (`groupId`) REFERENCES `im_group_info` (`groupId`) ON DELETE CASCADE,
  CONSTRAINT `fk_goff_off` FOREIGN KEY (`offLineId`) REFERENCES `im_off_line_info` (`offLineId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ȧ��������Ϣ';

CREATE TABLE `im_system_info` (
  `systemId` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `systemInfoType` tinyint(2) DEFAULT NULL COMMENT '��Ϣ����(0:���֣�1:ͼƬ)',
  `systemInfo` varchar(200) DEFAULT NULL COMMENT 'ϵͳ����',
  `extern` varchar(32) DEFAULT NULL COMMENT '��չ',
  PRIMARY KEY (`systemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϵͳ��Ϣ';



DELIMITER $$

USE `amwellcustomline`$$

DROP PROCEDURE IF EXISTS `p_change_im_group_user`$$

CREATE DEFINER=`admin`@`%` PROCEDURE `p_change_im_group_user`()
BEGIN
	DECLARE v_groupId VARCHAR(50);-- imȦ��id
	
	DECLARE v_lineBaseId VARCHAR(50);-- ��·id
	
	DECLARE v_nickName VARCHAR(50);-- �˿��ǳ�
	
	DECLARE v_userId VARCHAR(50);-- im�û�id
	
	DECLARE STOP_1,STOP_2 INT DEFAULT 0;
	
	-- ��ѯȦ��id����·id
	DECLARE cur_1 CURSOR FOR (SELECT groupId,lineBaseId FROM im_group_info);
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET STOP_1 = 1; 
	-- ɾ�����е�Ȧ���û���ϵ��Ϣ
        TRUNCATE TABLE im_ug_info;
	
	OPEN cur_1;
	
	FETCH cur_1 INTO v_groupId,v_lineBaseId;
		
         WHILE STOP_1 <> 1 DO-- ����Ȧ��id����·id
             
             -- -------cur_2 start---------------------------------------------------------------- 
             BEGIN
		     -- ������·id��ѯ��һ�����Ʊ���û���Ϣ
		     DECLARE cur_2 CURSOR FOR (SELECT p.nickName,iui.userId FROM lease_base_info lbi,line_business_order lbo,line_class_info lci,passenger_info p,im_user_info iui WHERE lbi.lineBaseId = v_lineBaseId AND lbi.ispay = '1' 
	AND lbi.leaseOrderNo = lbo.leaseOrderId AND lbo.lineClassId = lci.lineClassId AND lci.orderDate = CURDATE() AND lbi.passengerId = p.passengerId AND p.passengerId = iui.passengerId);
		     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET STOP_2 = 1; 
             
                     OPEN cur_2;
                        
                         FETCH cur_2 INTO v_nickName,v_userId;
                         
                         WHILE STOP_2 <> 1 DO-- �����û���Ϣ
			
                           -- ���˿ͼ���Ȧ��
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



-- �Ƽ��н����ݿ�Ķ�

ALTER TABLE  `sys_app_setting`  ADD COLUMN `recommendAwardSwitch` TINYINT(4) NULL DEFAULT 1 COMMENT '�Ƽ��н����أ�0.�� 1.��' AFTER `returnTicketFree`;

ALTER TABLE gf_gifts MODIFY COLUMN giftType TINYINT(4) NOT NULL DEFAULT 1 COMMENT '��������:1:ͨ������Ʒ���೵��������ƴ��ͨ�ã�2:���°�೵��Ʒ 3:������Ʒ 4:ƴ����Ʒ 5.�Ƽ��н���Ʒ';

ALTER TABLE gf_coupon MODIFY COLUMN selectPass TINYINT(4) NOT NULL DEFAULT 0 COMMENT 'ѡ���û�  0:ȫ���û�  1:���û�  2:15��δ��¼�û�   3:�Զ����û� 4.�Ƽ��н��û�';


CREATE TABLE `recommend_award_set` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `newUserGiftId` varchar(35) NOT NULL COMMENT '�����û���ƷID',
  `oldUserGiftId` varchar(35) NOT NULL COMMENT '���û���ƷID',
  `actionRule` text NOT NULL COMMENT '�Ƽ��н������',
  `setBy` varchar(64) DEFAULT NULL COMMENT '������Id',
  `setOn` varchar(64) DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='�Ƽ��н����ñ�'



CREATE TABLE `recommend_award_info` (
  `id` VARCHAR(32) NOT NULL,
  `oldUserPhone` VARCHAR(16) NOT NULL COMMENT '���û��ֻ�����',
  `newUserPhone` VARCHAR(16) NOT NULL COMMENT '���û��ֻ�����',
  `oldUserGiftId` VARCHAR(35) NOT NULL COMMENT '���û���ƷID',
  `newUserGiftId` VARCHAR(35) NOT NULL COMMENT '�����û���ƷID',
  `createOn` VARCHAR(64) NULL COMMENT 'ʱ��',
  `delFlag` tinyint(4) DEFAULT '0' COMMENT '״̬��0.���� 1.����  2.������ע���¼  3.�����״���Ʊ',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='�Ƽ��н���ϸ��';



INSERT INTO `amwellcustomline`.`sys_power` 
	(`powerId`, `code`, `name`, `fid`, `sysType`, `url`,iconUrl, `createBy`, `createOn`, `sortFlag`)
	VALUES
	('150826200439927014', 'help4', '�Ƽ��н�����', 'help', '0', '../recommendAward/forwardSetPage','leftico leftico19 fl', 'ϵͳ����', '2015-06-27 10:30:00', 4);


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
         
                -- ��״̬Ϊ0�ĳ���7�յ��Ƽ���Ϣ״̬��ΪʧЧ
		
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
