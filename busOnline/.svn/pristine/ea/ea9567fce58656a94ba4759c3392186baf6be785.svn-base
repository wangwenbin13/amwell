-- 进入库，建立谍中谍活动的奖品数据表；
USE `amwellcustomline`;

DROP TABLE IF EXISTS `ImpossibleInfo`;
CREATE TABLE `ImpossibleInfo` (
	`beginTime` bigint unsigned NOT NULL COMMENT '活动开始时间',
	`endTime` bigint unsigned NOT NULL COMMENT '活动结束时间',
	`prize1` int unsigned DEFAULT 0 COMMENT '1等奖数量(剩余数量)',
	`prize2` int unsigned DEFAULT 0 COMMENT '2等奖数量(剩余数量)',
	`prize3` int unsigned DEFAULT 0 COMMENT '3等奖数量(剩余数量)',
	`prize4` int unsigned DEFAULT 0 COMMENT '4等奖数量(剩余数量)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `Impossible`
-- ----------------------------
DROP TABLE IF EXISTS `Impossible`;
CREATE TABLE `Impossible` (
	`tel` varchar(32) NOT NULL COMMENT '用户手机号',
	`uid` varchar(32) NOT NULL COMMENT '用户ID',
	`prize` int unsigned NOT NULL DEFAULT 0 COMMENT '奖品，0为未中奖，各个数字对应奖品',
	`createTime` bigint unsigned NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ImpossibleInfo` (`beginTime`, `endTime`, `prize1`, `prize2`, `prize3`, `prize4`) VALUES (1442160000000, 1442419200000, 5, 10, 10, 50);
