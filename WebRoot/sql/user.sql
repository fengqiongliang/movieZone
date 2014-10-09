-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` bigint(20) NOT NULL               COMMENT '用户id',
  `username` varchar(50) NOT NULL       COMMENT '用户名' ,
  `password` varchar(50) NOT NULL        COMMENT '密码',
  `nickname` varchar(50) NOT NULL       COMMENT '昵称',
  `faceurl` varchar(50) NOT NULL            COMMENT '头像url',
  `cookie_id` varchar(50) default NULL    COMMENT '随机的cookie_id',
  `role` varchar(50) NOT NULL                 COMMENT '角色：user、admin',
  `lastlogtime` datetime NOT NULL           COMMENT '上次登录时间',
  `updatetime` datetime NOT NULL          COMMENT '更新时间',
  `createip` varchar(50) NOT NULL          COMMENT '创建ip',
  `createtime` datetime NOT NULL           COMMENT '创建时间',
  `createarea` varchar(50) default NULL  COMMENT '创建地区：北京、上海、广州等',
  `nextnick` varchar(50) default NULL  COMMENT '修改后的昵称',
  `nextface` varchar(50) default NULL  COMMENT '修改后的头像',
  PRIMARY KEY  (`userid`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', 'ahone', '123456', '中原一点红', 'md5', 'q36014038', '公孙鹏', '2012-10-21 22:18:56', '192.168.0.100', '2012-10-21 21:01:51', null, null);
INSERT INTO `user` VALUES ('2', 'flygirl', 'fly@qq.com', '1287410768', 'md5', '123456', '陈小飞', '2012-10-21 22:19:00', '192.168.0.100', '2012-10-21 21:03:16', null, null);
INSERT INTO `user` VALUES ('3', 'dreamboy', 'dream@dream.com', '129468', 'md5', '123456', null, '2012-10-21 22:19:01', '192.168.0.100', '2012-10-21 21:04:19', null, null);
INSERT INTO `user` VALUES ('4', 'dreamgirl', 'girl@dream.com', '1234324', 'md5', '123456', null, '2012-10-21 22:19:03', '192.168.0.100', '2012-10-21 21:04:59', null, null);
INSERT INTO `user` VALUES ('5', 'admin', 'admin@dream.com', '123456', 'md5', '123456', null, '2012-10-21 22:19:06', '192.168.0.100', '2012-10-21 21:05:50', null, null);
