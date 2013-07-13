/*
Navicat MySQL Data Transfer

Source Server         : dream
Source Server Version : 50137
Source Host           : localhost:3306
Source Database       : dream

Target Server Type    : MYSQL
Target Server Version : 50137
File Encoding         : 65001

Date: 2012-10-22 02:26:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `nickname` varchar(50) default NULL,
  `email` varchar(50) default NULL,
  `mobile` bigint(20) default NULL,
  `password` varchar(50) NOT NULL,
  `plainpwd` varchar(50) NOT NULL,
  `realname` varchar(50) default NULL,
  `lastlogtime` datetime NOT NULL,
  `createip` varchar(50) NOT NULL,
  `createtime` datetime NOT NULL,
  `areacode` varchar(50) default NULL,
  `updatetime` datetime default NULL,
  `emailcode` varchar(45) default NULL COMMENT '激活码',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `nickname_UNIQUE` (`nickname`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `mobile_UNIQUE` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ahone', '49163653@qq.com', '18210456549', 'md5', 'q36014038', '公孙鹏', '2012-10-21 22:18:56', '192.168.0.100', '2012-10-21 21:01:51', null, null);
INSERT INTO `user` VALUES ('2', 'flygirl', 'fly@qq.com', '1287410768', 'md5', '123456', '陈小飞', '2012-10-21 22:19:00', '192.168.0.100', '2012-10-21 21:03:16', null, null);
INSERT INTO `user` VALUES ('3', 'dreamboy', 'dream@dream.com', '129468', 'md5', '123456', null, '2012-10-21 22:19:01', '192.168.0.100', '2012-10-21 21:04:19', null, null);
INSERT INTO `user` VALUES ('4', 'dreamgirl', 'girl@dream.com', '1234324', 'md5', '123456', null, '2012-10-21 22:19:03', '192.168.0.100', '2012-10-21 21:04:59', null, null);
INSERT INTO `user` VALUES ('5', 'admin', 'admin@dream.com', '123456', 'md5', '123456', null, '2012-10-21 22:19:06', '192.168.0.100', '2012-10-21 21:05:50', null, null);
