-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.27 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 sean_disk 的数据库结构
CREATE DATABASE IF NOT EXISTS `sean_disk` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sean_disk`;

-- 导出  表 sean_disk.sean_file 结构
CREATE TABLE IF NOT EXISTS `sean_file` (
  `file_id` varchar(36) NOT NULL COMMENT '逻辑文件唯一标识',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `filename` varchar(512) NOT NULL COMMENT '文件名',
  `length` bigint NOT NULL COMMENT '文件大小',
  `type` varchar(128) DEFAULT NULL COMMENT '文件类型',
  `physical_hash` varchar(64) DEFAULT NULL COMMENT '物理文件哈希',
  `version` bigint DEFAULT NULL COMMENT '文件版本',
  `create_time` datetime DEFAULT NULL COMMENT '文件创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '文件更新时间',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='云盘文件表';

-- 导出  表 sean_disk.sean_file_op_log 结构
CREATE TABLE IF NOT EXISTS `sean_file_op_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `file_id` varchar(36) DEFAULT NULL COMMENT '逻辑文件唯一标识',
  `filename` varchar(512) DEFAULT NULL COMMENT '逻辑文件名',
  `user_id` bigint DEFAULT NULL COMMENT '文件操作者标识',
  `username` varchar(50) DEFAULT NULL COMMENT '文件操作者名称',
  `operation` varchar(32) DEFAULT NULL COMMENT '文件操作',
  `log_time` datetime DEFAULT NULL COMMENT '日志发生时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件操作日志表';

-- 导出  表 sean_disk.sean_label 结构
CREATE TABLE IF NOT EXISTS `sean_label` (
  `label_id` varchar(36) NOT NULL COMMENT '文件标签唯一标识',
  `user_id` bigint NOT NULL COMMENT '标签创建者ID',
  `label_name` varchar(256) NOT NULL COMMENT '文件标签名',
  `is_system` tinyint NOT NULL COMMENT '是否属于系统标签',
  `create_time` datetime DEFAULT NULL COMMENT '标签创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '标签更新时间',
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='云盘标签表';

-- 导出  表 sean_disk.sean_label_file 结构
CREATE TABLE IF NOT EXISTS `sean_label_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `file_id` varchar(36) NOT NULL COMMENT '逻辑文件唯一标识',
  `label_id` varchar(36) NOT NULL COMMENT '文件标签唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件标签关系表';

-- 导出  表 sean_disk.sys_captcha 结构
CREATE TABLE IF NOT EXISTS `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统验证码';

-- 导出  表 sean_disk.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单管理';

-- 正在导出表  sean_disk.sys_menu 的数据：~21 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES
	(1, 0, '系统管理', NULL, NULL, 0, 'system', 0),
	(2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1),
	(3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2),
	(4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3),
	(15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0),
	(16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0),
	(17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0),
	(18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0),
	(19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0),
	(20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0),
	(21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0),
	(22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0),
	(23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0),
	(24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0),
	(25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0),
	(26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0),
	(27, 0, '小恩云盘', '', '', 0, 'zonghe', 1),
	(28, 27, '文件浏览', 'disk/file', 'disk:file:info,disk:file:save,disk:file:update,disk:file:delete', 1, 'zhedie', 0),
	(29, 28, '查看', '', 'disk:file:list', 2, '', 0),
	(30, 28, '标签权限', '', 'disk:label:list,disk:label:update,disk:label:add,disk:label:delete', 2, '', 0),
	(31, 28, '下载权限', '', 'disk:file:download', 2, '', 0),
	(32, 27, '文件操作日志', 'disk/fileLog', 'disk:fileoplog:list', 1, 'log', 0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- 导出  表 sean_disk.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色';

-- 正在导出表  sean_disk.sys_role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`role_id`, `role_name`, `remark`, `create_user_id`, `create_time`) VALUES
	(1, '管理员', '', 1, '2022-01-12 10:43:11'),
	(2, '云盘用户', '', 1, '2022-01-12 10:43:37');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- 导出  表 sean_disk.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色与菜单对应关系';

-- 正在导出表  sean_disk.sys_role_menu 的数据：~28 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(21, 2, 27),
	(22, 2, 28),
	(23, 2, 29),
	(26, 2, 30),
	(49, 1, 1),
	(50, 1, 2),
	(51, 1, 15),
	(52, 1, 16),
	(53, 1, 17),
	(54, 1, 18),
	(55, 1, 3),
	(56, 1, 19),
	(57, 1, 20),
	(58, 1, 21),
	(59, 1, 22),
	(60, 1, 4),
	(61, 1, 23),
	(62, 1, 24),
	(63, 1, 25),
	(64, 1, 26),
	(65, 1, 27),
	(66, 1, 28),
	(67, 1, 29),
	(68, 1, 30),
	(69, 1, 31),
	(70, 1, 32);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- 导出  表 sean_disk.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `uid` bigint NOT NULL COMMENT '工号',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(32) DEFAULT NULL COMMENT 'QQ号',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uid` (`uid`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `uid_2` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2019006411 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';

-- 正在导出表  sean_disk.sys_user 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`user_id`, `uid`, `username`, `name`, `password`, `salt`, `email`, `qq`, `mobile`, `status`, `create_user_id`, `create_time`) VALUES
	(1, 0, 'admin', NULL, '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@sea2.org', '10001', '13612345678', 1, 1, '2016-11-11 11:11:11');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- 导出  表 sean_disk.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户与角色对应关系';

-- 导出  表 sean_disk.sys_user_token 结构
CREATE TABLE IF NOT EXISTS `sys_user_token` (
  `user_id` bigint NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户Token';

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
