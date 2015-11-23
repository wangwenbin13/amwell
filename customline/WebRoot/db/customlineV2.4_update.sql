ALTER TABLE `sys_admin`
ADD COLUMN `cityCode`  varchar(255) NULL COMMENT '城市编码' AFTER `deleteFlag`,
ADD COLUMN `cityName`  varchar(255) NULL COMMENT '城市名称' AFTER `cityCode`,
ADD COLUMN `provinceCode`  varchar(255) NULL COMMENT '省份编码' AFTER `cityName`;