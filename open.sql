/*
Navicat MySQL Data Transfer

Source Server         : openConfig_dev
Source Server Version : 50635
Source Host           : 10.1.135.155:3307
Source Database       : open_dev

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-08-14 10:30:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_company
-- ----------------------------
DROP TABLE IF EXISTS `app_company`;
CREATE TABLE `app_company` (
  `COMPANY_ID` varchar(32) NOT NULL COMMENT '机构ID',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `COMPANY_CNAME` varchar(200) DEFAULT NULL COMMENT '机构简体名称',
  `COMPANY_ENAME` varchar(200) DEFAULT NULL COMMENT '机构英文名称',
  `COMPANY_TNAME` varchar(200) DEFAULT NULL COMMENT '机构繁体名称',
  `COMPANY_LEVEL` int(10) DEFAULT NULL COMMENT '菜单层级',
  `PARENT_COMPANY_ID` varchar(32) DEFAULT NULL COMMENT '上级菜单ID',
  `VALID_FLAG` char(1) DEFAULT NULL COMMENT '是否有效：0无效，1 有效',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `FLAG` varchar(10) DEFAULT NULL COMMENT '其他标志',
  PRIMARY KEY (`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统机构表';

-- ----------------------------
-- Table structure for app_job
-- ----------------------------
DROP TABLE IF EXISTS `app_job`;
CREATE TABLE `app_job` (
  `JOB_ID` varchar(32) NOT NULL COMMENT '任务ID',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `JOB_NAME` varchar(600) NOT NULL COMMENT '任务名称',
  `JOB_GROUP_NAME` varchar(600) NOT NULL COMMENT '任务组',
  `JOB_STATUS` varchar(3) NOT NULL COMMENT '任务状态 0 未运行 1 运行中 2 暂停 3 作废',
  `CRON_EXPRESSION` varchar(100) NOT NULL COMMENT '任务表达式',
  `JOB_CLASS` varchar(150) DEFAULT NULL COMMENT '任务执行类全名',
  `SPRING_ID` varchar(100) DEFAULT NULL COMMENT '任务执行类在SPRING配置中的ID',
  `METHOD_NAME` varchar(100) DEFAULT NULL COMMENT '任务执行方法，无参。【注：选择JOB_CLASS或SPRING_ID时必录】',
  `RESTFUL_URL` varchar(400) DEFAULT NULL COMMENT '任务执行RESTFUL服务URL，无参。【注：JOB_CLASS、SPRING_ID和RESTFUL_URL方式三选一】',
  `REMARK` varchar(600) DEFAULT NULL COMMENT '任务说明',
  `FLAG` varchar(10) DEFAULT NULL COMMENT '其他标志',
  PRIMARY KEY (`JOB_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='定时任务表';

-- ----------------------------
-- Table structure for app_parameter
-- ----------------------------
DROP TABLE IF EXISTS `app_parameter`;
CREATE TABLE `app_parameter` (
  `PARAMETER_ID` varchar(64) NOT NULL COMMENT '参数ID',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `PARAMETER_TYPE` varchar(100) NOT NULL COMMENT '参数类型',
  `PARAMETER_CODE` varchar(100) NOT NULL COMMENT '参数代码',
  `PARAMETER_CNAME` varchar(50) DEFAULT NULL COMMENT '参数简体名称',
  `PARAMETER_ENAME` varchar(50) DEFAULT NULL COMMENT '参数英文名称',
  `PARAMETER_TNAME` varchar(50) DEFAULT NULL COMMENT '参数繁体名称',
  `VALID_FLAG` int(10) DEFAULT NULL COMMENT '是否有效：0无效，1 有效',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `FLAG` varchar(10) DEFAULT NULL COMMENT '其他标志',
  PRIMARY KEY (`PARAMETER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';

-- ----------------------------
-- Table structure for app_parameter_type
-- ----------------------------
DROP TABLE IF EXISTS `app_parameter_type`;
CREATE TABLE `app_parameter_type` (
  `PARAMETER_TYPE_ID` varchar(64) NOT NULL COMMENT '参数类型ID',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `PARAMETER_TYPE` varchar(100) NOT NULL COMMENT '参数类型类型',
  `PARAMETER_TYPE_CNAME` varchar(50) DEFAULT NULL COMMENT '参数类型简体名称',
  `PARAMETER_TYPE_ENAME` varchar(50) DEFAULT NULL COMMENT '参数类型英文名称',
  `PARAMETER_TYPE_TNAME` varchar(50) DEFAULT NULL COMMENT '参数类型繁体名称',
  `VALID_FLAG` char(1) DEFAULT NULL COMMENT '是否有效：0无效，1 有效',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `FLAG` varchar(10) DEFAULT NULL COMMENT '其他标志',
  PRIMARY KEY (`PARAMETER_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数类型表';

-- ----------------------------
-- Table structure for app_resource
-- ----------------------------
DROP TABLE IF EXISTS `app_resource`;
CREATE TABLE `app_resource` (
  `RESOURCE_ID` varchar(32) NOT NULL COMMENT '资源ID',
  `CREATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `RESOURCE_NAME` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `RESOURCE_TYPE` varchar(10) DEFAULT NULL COMMENT '资源类型',
  `RESOURCE_LEVEL` decimal(2,0) DEFAULT NULL COMMENT '资源层级',
  `PARENT_RESOURCE_ID` varchar(32) DEFAULT NULL COMMENT '上级资源ID',
  `RESOURCE_ICON_CLASS` varchar(100) DEFAULT NULL COMMENT '资源图标CLASS',
  `ACTION_URL` varchar(100) DEFAULT NULL COMMENT '提交URL',
  `END_FLAG` char(1) DEFAULT '1' COMMENT '节点标志,默认新添加的都是叶子节点',
  `DISPLAY_ORDER` int(10) DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源表';

-- ----------------------------
-- Table structure for app_resource_copy
-- ----------------------------
DROP TABLE IF EXISTS `app_resource_copy`;
CREATE TABLE `app_resource_copy` (
  `RESOURCE_ID` varchar(32) NOT NULL COMMENT '资源ID',
  `CREATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `RESOURCE_NAME` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `RESOURCE_TYPE` varchar(10) DEFAULT NULL COMMENT '资源类型',
  `RESOURCE_LEVEL` decimal(2,0) DEFAULT NULL COMMENT '资源层级',
  `PARENT_RESOURCE_ID` varchar(32) DEFAULT NULL COMMENT '上级资源ID',
  `RESOURCE_ICON_CLASS` varchar(100) DEFAULT NULL COMMENT '资源图标CLASS',
  `ACTION_URL` varchar(100) DEFAULT NULL COMMENT '提交URL',
  `END_FLAG` char(1) DEFAULT NULL COMMENT '节点标志',
  `DISPLAY_ORDER` int(10) DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源表';

-- ----------------------------
-- Table structure for app_role
-- ----------------------------
DROP TABLE IF EXISTS `app_role`;
CREATE TABLE `app_role` (
  `ROLE_ID` varchar(32) NOT NULL COMMENT '角色ID',
  `CREATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `ROLE_NAME` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `VALID_FLAG` int(10) DEFAULT NULL COMMENT '是否有效：0无效，1 有效',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源表';

-- ----------------------------
-- Table structure for app_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `app_role_resource`;
CREATE TABLE `app_role_resource` (
  `ROLE_RESOURCE_ID` int(32) NOT NULL AUTO_INCREMENT COMMENT '角色资源关联ID',
  `CREATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `RESOURCE_ID` varchar(32) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`ROLE_RESOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2532 DEFAULT CHARSET=utf8 COMMENT='系统资源表';

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `USER_ID` varchar(32) NOT NULL COMMENT '员工ID',
  `CREATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `USER_CODE` varchar(50) NOT NULL COMMENT '员工代码',
  `USER_CNAME` varchar(50) DEFAULT NULL COMMENT '员工简体中文名称',
  `USER_TNAME` varchar(50) DEFAULT NULL COMMENT '员工繁体中文名称',
  `USER_ENAME` varchar(50) DEFAULT NULL COMMENT '员工英文名称',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '密码',
  `SALT` varchar(100) DEFAULT NULL COMMENT '加密密码的盐',
  `SEAL` varchar(100) DEFAULT NULL COMMENT '印鉴',
  `PASSWORD_SET_DATE` datetime DEFAULT NULL COMMENT '密码设置日期',
  `PASSWORD_EXPIRE_DATE` datetime NOT NULL COMMENT '密码过期日期',
  `COMPANY_CODE` varchar(10) NOT NULL COMMENT '归属机构代码',
  `PHONE` varchar(18) DEFAULT NULL COMMENT '电话号码',
  `MOBILE` varchar(18) DEFAULT NULL COMMENT '手机号码',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '通信地址',
  `POST_CODE` varchar(6) DEFAULT NULL COMMENT '邮政编码',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `VALID_FLAG` char(1) NOT NULL COMMENT '1-有效；0-无效',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '备注',
  `FLAG` varchar(255) DEFAULT NULL COMMENT '标志字段',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工代码表';

-- ----------------------------
-- Table structure for app_user_exclude_resource
-- ----------------------------
DROP TABLE IF EXISTS `app_user_exclude_resource`;
CREATE TABLE `app_user_exclude_resource` (
  `USER_EXCLUDE_RESOURCE_ID` int(32) NOT NULL AUTO_INCREMENT COMMENT '用户资源排除关联ID',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `RESOURCE_ID` varchar(32) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`USER_EXCLUDE_RESOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='用户资源排除关联表';

-- ----------------------------
-- Table structure for app_user_role
-- ----------------------------
DROP TABLE IF EXISTS `app_user_role`;
CREATE TABLE `app_user_role` (
  `USER_ROLE_ID` varchar(32) NOT NULL COMMENT '用户角色关联ID',
  `CREATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATED_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`USER_ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Table structure for qz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_blob_triggers`;
CREATE TABLE `qz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qz_calendars`;
CREATE TABLE `qz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_cron_triggers`;
CREATE TABLE `qz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_fired_triggers`;
CREATE TABLE `qz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qz_job_details`;
CREATE TABLE `qz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qz_locks`;
CREATE TABLE `qz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qz_paused_trigger_grps`;
CREATE TABLE `qz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qz_scheduler_state`;
CREATE TABLE `qz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_simple_triggers`;
CREATE TABLE `qz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_simprop_triggers`;
CREATE TABLE `qz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_triggers`;
CREATE TABLE `qz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_platform_account
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_account`;
CREATE TABLE `t_platform_account` (
  `account_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account_name` varchar(80) DEFAULT NULL COMMENT '用户名称',
  `account_password` varchar(80) DEFAULT NULL COMMENT '用户密码',
  `mobile` varchar(45) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱地址',
  `auth_flag` char(1) NOT NULL COMMENT '有效标识,1已经认证,0无',
  `valid_flag` char(1) NOT NULL COMMENT '有效标识,1有效,0无效',
  `created_date` datetime NOT NULL COMMENT '创建时间,默认插入时间',
  `created_user` varchar(50) NOT NULL COMMENT '创建用户,插入用户',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间,默认插入更新时间',
  `updated_user` varchar(50) NOT NULL COMMENT '更新用户',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COMMENT='第三方用户管理表';

-- ----------------------------
-- Table structure for t_platform_partner
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_partner`;
CREATE TABLE `t_platform_partner` (
  `partner_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `partner_name` varchar(100) DEFAULT NULL COMMENT '合作方名称',
  `partner_img` varchar(255) DEFAULT NULL COMMENT '合作方图片资源',
  `partner_invalid` char(1) DEFAULT '1' COMMENT '有效标识,1有效,0无效, -1是删除',
  `partner_remark` varchar(255) DEFAULT NULL COMMENT '备注或者描述',
  `partner_field_aa` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间,默认插入时间',
  `created_user` varchar(50) DEFAULT NULL COMMENT '创建用户,插入用户',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updated_user` varchar(50) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`partner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='合作方管理表';

-- ----------------------------
-- Table structure for t_platform_product
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_product`;
CREATE TABLE `t_platform_product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_name` varchar(100) DEFAULT NULL COMMENT '产品名称',
  `product_img` varchar(255) DEFAULT NULL COMMENT '产品图片资源',
  `product_invalid` char(1) DEFAULT NULL COMMENT '有效标识,1有效,0无效',
  `product_remark` varchar(255) DEFAULT NULL COMMENT '备注或者描述',
  `product_div_html` varchar(255) DEFAULT NULL COMMENT '自定义的html代码，比如遮挡层的效果展示',
  `product_filed_aa` varchar(255) DEFAULT NULL COMMENT '备用字段',
  `created_date` datetime NOT NULL COMMENT '创建时间,默认插入时间',
  `created_user` varchar(50) NOT NULL COMMENT '创建用户,插入用户',
  `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updated_user` varchar(50) DEFAULT NULL COMMENT '更新用户',
  `product_txt` text COMMENT '产品内容描述html代码或者txt描述',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='产品管理表';
