SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 自增id表，用于程序启动时加载
-- ----------------------------
DROP TABLE IF EXISTS `increment`;
CREATE TABLE `increment` (
  `field` varchar(10) NOT NULL       COMMENT '列名' ,
  `start` bigint NOT NULL                 COMMENT '开始',
  PRIMARY KEY  (`field`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `increment` VALUES ('userid',10000);           -- 用户id
INSERT INTO `increment` VALUES ('movieid',300);            -- 电影/电视id
INSERT INTO `increment` VALUES ('modmvid',300);          -- 模块_电影/电视关联id 
INSERT INTO `increment` VALUES ('attachid',300);            -- 电影/电视附件id
INSERT INTO `increment` VALUES ('commentid',300);       -- 留言id
INSERT INTO `increment` VALUES ('replyid',300);              -- 回复id
INSERT INTO `increment` VALUES ('favoriteid',300);          -- 收藏id

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` bigint NOT NULL                       COMMENT '用户id',
  `username` varchar(35) NOT NULL       COMMENT '用户名' ,
  `password` varchar(35) NOT NULL        COMMENT '密码',
  `nickname` varchar(35) NOT NULL        COMMENT '昵称',
  `faceurl` varchar(90) NOT NULL            COMMENT '头像url',
  `cookie_id` varchar(10) default NULL    COMMENT '随机的cookie_id',
  `role` varchar(5) NOT NULL                  COMMENT '角色：user、admin',
  `lastlogtime` datetime NOT NULL           COMMENT '上次登录时间',
  `updatetime` datetime NOT NULL          COMMENT '更新时间',
  `createip` varchar(15) NOT NULL          COMMENT '创建ip',
  `createtime` datetime NOT NULL           COMMENT '创建时间',
  `createarea` varchar(15) default NULL  COMMENT '创建地区：北京、上海、广州等',
  `nextnick` varchar(35) default NULL     COMMENT '修改后的昵称',
  `nextface` varchar(90) default NULL     COMMENT '修改后的头像',
  `isForbit` enum('false','true') default 'false'   COMMENT 'false-不禁用 true禁用',
  PRIMARY KEY  (`userid`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', 'ahone', '123456', '末日黎明', '/img/92x71/1.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('2', 'admin', '123456', '影集站主', '/img/92x71/2.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('3', 'ahone550', '123456', '黑山老妖', '/img/92x71/3.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('4', 'ahone220', '123456', '高清王子', '/img/92x71/4.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('5', 'ahone110', '123456', '影视公主', '/img/92x71/5.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('6', 'root', '123456', '东邪西毒', '/img/92x71/6.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('7', '49163653', '123456', '和你一样的坚强', '/img/92x71/7.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('8', '49163653@qq.com', '123456', '那片红海', '/img/92x71/8.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('9', 'fengqiongliang', '123456', '精灵旅社', '/img/92x71/9.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('10', 'doudou', '123456', '天空翱翔', '/img/92x71/10.gif', null, 'admin', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '海口',null,null,'false');
INSERT INTO `user` VALUES ('11', 'user1', '123456', '堕落天使', '/img/92x71/10.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '北京','天安门','/img/92x71/20.gif','false');
INSERT INTO `user` VALUES ('12', 'user2', '123456', '吹不散的思念', '/img/92x71/11.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '北京',null,null,'false');
INSERT INTO `user` VALUES ('13', 'user3', '123456', '思念是一种病', '/img/92x71/12.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '上海','失败是成功的妈妈','/img/92x71/15.gif','false');
INSERT INTO `user` VALUES ('14', 'user4', '123456', '小苹果', '/img/92x71/13.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '天津',null,null,'false');
INSERT INTO `user` VALUES ('15', 'user5', '123456', '算命先生', '/img/92x71/14.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '广州','忠心护主','/img/92x71/16.gif','false');
INSERT INTO `user` VALUES ('16', 'user6', '123456', '走在相间路上', '/img/92x71/15.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '黑龙江',null,null,'false');
INSERT INTO `user` VALUES ('17', 'user7', '123456', '一米阳光', '/img/92x71/16.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '沈阳','走在寒风中','/img/92x71/29.gif','false');
INSERT INTO `user` VALUES ('18', 'user8', '123456', '牙齿好痛', '/img/92x71/17.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '吉林','习近平去死',null,'false');
INSERT INTO `user` VALUES ('19', 'user9', '123456', '驯龙小子', '/img/92x71/18.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '新疆',null,'/img/92x71/20.gif','false');
INSERT INTO `user` VALUES ('20', 'user10', '123456', '人鱼公主', '/img/92x71/19.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '西藏','干到你逼烂','/img/92x71/2.gif','false');
INSERT INTO `user` VALUES ('21', 'user11', '123456', '你在远方', '/img/92x71/20.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '东方','我是小小新','/img/92x71/1.gif','false');
INSERT INTO `user` VALUES ('22', 'user12', '123456', '看穿你的心', '/img/92x71/21.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '南京','我爱操逼','/img/92x71/3.gif','false');
INSERT INTO `user` VALUES ('23', 'user13', '123456', '找不到男朋友', '/img/92x71/21.gif', null, 'user', '2012-10-21 22:18:56', '2012-10-21 22:18:56', '127.0.0.1','2012-10-21 22:18:56', '广西','Serena','/img/92x71/4.gif','false');

-- ----------------------------
-- 电影表
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
  `movieid` bigint NOT NULL                  COMMENT '电影/电视id',
  `name` varchar(30) NOT NULL                 COMMENT '名称' ,
  `type`  varchar(2) NOT NULL                    COMMENT '类型：mv、tv',
  `shortdesc` varchar(50) NOT NULL            COMMENT '精短简介',
  `longdesc` varchar(1000) NOT NULL           COMMENT '详细简介',
  `face650x500` varchar(90) NOT NULL    COMMENT '电影/电视头像650x500',
  `face400x308` varchar(90) NOT NULL    COMMENT '电影/电视头像400x308',
  `face220x169` varchar(90) NOT NULL    COMMENT '电影/电视头像220x169',
  `face150x220` varchar(90) NOT NULL    COMMENT '电影/电视头像150x220',
  `face80x80` varchar(90) NOT NULL       COMMENT '电影/电视头像80x80',
  `picture` varchar(200) NOT NULL             COMMENT '电影/电视截图url，json数组格式如["/upload/abcdddqfghijk.jpg","/upload/abcddddjok.jpg"]',
  `score` float default 0.0                           COMMENT '评分如8.0',
  `approve` bigint default  0                           COMMENT '赞数',
  `download` bigint default  0                       COMMENT '下载',
  `broswer` bigint default  0                          COMMENT '浏览',
  `createtime` datetime NOT NULL          COMMENT '创建时间',
  `publishtime` datetime NOT NULL         COMMENT '发布时间',
  PRIMARY KEY  (`movieid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `movie` VALUES ('1', '驯龙高手3D', 'mv', '梦工厂全新3D冒险喜剧力作', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。', '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]', '8.1',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('2', '精灵旅社', 'mv', '吸血鬼与人情未了', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。', '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]', '9.0',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('3', '大药纺', 'tv', '钟嘉欣变身药厂千金', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '1.1',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('4', '后会无期', 'mv', '韩寒导演处女之作', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '5.2',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('5', '催眠大师', 'mv', '知名心理治疗师徐瑞宁和棘手的女病人任小妍之间发生的故事', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '7.3',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('6', '反贪风暴', 'mv', '廉政公署打击贪污', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '3.5',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('7', '分手大师', 'mv', '邓超首次自导自演的“超氏喜剧”电影', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '2.1',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('8', '小时代3', 'mv', '80后作家郭敬明执导处女作', '林萧、顾里、南湘、唐宛如，四个从小一起长大的好姐妹大学毕业进入职场，开始了新的旅程。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '8.3',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('9', '使徒行者', 'tv', '铁三角演绎剧版无间道', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '6.8',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('10', '对我而言，可爱的她', 'tv', 'Rain变身韩版霸道总裁', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '7.9',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('11', '七人魔法使', 'tv', '魔道士的校园后宫', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '5.5',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('12', '最终幻想', 'mv', '魔道士的校园后宫', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '5.5',0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');

-- ----------------------------
-- 模块_电影关联表
-- ----------------------------
DROP TABLE IF EXISTS `module_movie`;
CREATE TABLE `module_movie` (
  `modmvid` bigint NOT NULL                  COMMENT '主键id',
  `modname` varchar(30) NOT NULL       COMMENT '模块名称' ,
  `movieid` bigint NOT NULL                     COMMENT '电影/电视id(外)' ,
  `orderseq` bigint default  0                     COMMENT '排列顺序',
  `createtime` datetime NOT NULL            COMMENT '创建时间',
  PRIMARY KEY  (`modmvid`),
  FOREIGN KEY   (`movieid`) REFERENCES `movie` (`movieid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `module_movie` VALUES ('1', '首页-展示区', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('2', '首页-展示区', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('3', '首页-展示区', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('4', '首页-展示区', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('5', '首页-展示区', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('6', '首页-展示区', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('7', '首页-展示区', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('8', '首页-展示区', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('9', '首页-展示区', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('10', '首页-展示区', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('11', '首页-论播区', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('12', '首页-论播区', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('13', '首页-论播区', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('14', '首页-论播区', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('15', '首页-论播区', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('16', '首页-论播区', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('17', '首页-论播区', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('18', '首页-论播区', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('19', '首页-论播区', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('20', '首页-论播区', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('21', '首页-站长区', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('22', '首页-站长区', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('23', '首页-站长区', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('24', '首页-站长区', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('25', '首页-站长区', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('26', '首页-站长区', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('27', '首页-站长区', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('28', '首页-站长区', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('29', '首页-站长区', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('30', '首页-站长区', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('31', '首页-电影-480p', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('32', '首页-电影-480p', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('33', '首页-电影-480p', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('34', '首页-电影-480p', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('35', '首页-电影-480p', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('36', '首页-电影-480p', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('37', '首页-电影-480p', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('38', '首页-电影-480p', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('39', '首页-电影-480p', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('40', '首页-电影-480p', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('41', '首页-电影-720p', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('42', '首页-电影-720p', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('43', '首页-电影-720p', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('44', '首页-电影-720p', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('45', '首页-电影-720p', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('46', '首页-电影-720p', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('47', '首页-电影-720p', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('48', '首页-电影-720p', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('49', '首页-电影-720p', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('50', '首页-电影-720p', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('51', '首页-电影-1080p', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('52', '首页-电影-1080p', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('53', '首页-电影-1080p', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('54', '首页-电影-1080p', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('55', '首页-电影-1080p', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('56', '首页-电影-1080p', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('57', '首页-电影-1080p', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('58', '首页-电影-1080p', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('59', '首页-电影-1080p', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('60', '首页-电影-1080p', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('61', '首页-电影-其它', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('62', '首页-电影-其它', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('63', '首页-电影-其它', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('64', '首页-电影-其它', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('65', '首页-电影-其它', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('66', '首页-电影-其它', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('67', '首页-电影-其它', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('68', '首页-电影-其它', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('69', '首页-电影-其它', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('70', '首页-电影-其它', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('71', '首页-电影-排行榜', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('72', '首页-电影-排行榜', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('73', '首页-电影-排行榜', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('74', '首页-电影-排行榜', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('75', '首页-电影-排行榜', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('76', '首页-电影-排行榜', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('77', '首页-电影-排行榜', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('78', '首页-电影-排行榜', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('79', '首页-电影-排行榜', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('80', '首页-电影-排行榜', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('81', '首页-电视剧-英美', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('82', '首页-电视剧-英美', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('83', '首页-电视剧-英美', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('84', '首页-电视剧-英美', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('85', '首页-电视剧-英美', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('86', '首页-电视剧-英美', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('87', '首页-电视剧-英美', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('88', '首页-电视剧-英美', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('89', '首页-电视剧-英美', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('90', '首页-电视剧-英美', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('91', '首页-电视剧-日韩', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('92', '首页-电视剧-日韩', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('93', '首页-电视剧-日韩', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('94', '首页-电视剧-日韩', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('95', '首页-电视剧-日韩', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('96', '首页-电视剧-日韩', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('97', '首页-电视剧-日韩', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('98', '首页-电视剧-日韩', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('99', '首页-电视剧-日韩', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('100', '首页-电视剧-日韩', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('101', '首页-电视剧-港台', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('102', '首页-电视剧-港台', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('103', '首页-电视剧-港台', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('104', '首页-电视剧-港台', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('105', '首页-电视剧-港台', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('106', '首页-电视剧-港台', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('107', '首页-电视剧-港台', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('108', '首页-电视剧-港台', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('109', '首页-电视剧-港台', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('110', '首页-电视剧-港台', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('111', '首页-电视剧-内地', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('112', '首页-电视剧-内地', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('113', '首页-电视剧-内地', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('114', '首页-电视剧-内地', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('115', '首页-电视剧-内地', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('116', '首页-电视剧-内地', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('117', '首页-电视剧-内地', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('118', '首页-电视剧-内地', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('119', '首页-电视剧-内地', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('120', '首页-电视剧-内地', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('121', '首页-电视剧-排行榜', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('122', '首页-电视剧-排行榜', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('123', '首页-电视剧-排行榜', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('124', '首页-电视剧-排行榜', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('125', '首页-电视剧-排行榜', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('126', '首页-电视剧-排行榜', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('127', '首页-电视剧-排行榜', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('128', '首页-电视剧-排行榜', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('129', '首页-电视剧-排行榜', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('130', '首页-电视剧-排行榜', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('131', '电影-展示区', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('132', '电影-展示区', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('133', '电影-展示区', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('134', '电影-展示区', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('135', '电影-展示区', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('136', '电影-展示区', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('137', '电影-展示区', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('138', '电影-展示区', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('139', '电影-展示区', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('140', '电影-展示区', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('141', '电影-展示区', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('142', '电影-480p', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('143', '电影-480p', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('144', '电影-480p', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('145', '电影-480p', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('146', '电影-480p', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('147', '电影-480p', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('148', '电影-480p', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('149', '电影-480p', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('150', '电影-480p', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('151', '电影-480p', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('152', '电影-720p', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('153', '电影-720p', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('154', '电影-720p', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('155', '电影-720p', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('156', '电影-720p', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('157', '电影-720p', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('158', '电影-720p', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('159', '电影-720p', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('160', '电影-720p', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('161', '电影-720p', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('162', '电影-1080p', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('163', '电影-1080p', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('164', '电影-1080p', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('165', '电影-1080p', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('166', '电影-1080p', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('167', '电影-1080p', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('168', '电影-1080p', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('169', '电影-1080p', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('170', '电影-1080p', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('171', '电影-1080p', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('172', '电影-其它', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('173', '电影-其它', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('174', '电影-其它', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('175', '电影-其它', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('176', '电影-其它', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('177', '电影-其它', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('178', '电影-其它', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('179', '电影-其它', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('180', '电影-其它', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('181', '电影-其它', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('182', '电视剧-展示区', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('183', '电视剧-展示区', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('184', '电视剧-展示区', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('185', '电视剧-展示区', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('186', '电视剧-展示区', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('187', '电视剧-展示区', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('188', '电视剧-展示区', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('189', '电视剧-展示区', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('190', '电视剧-展示区', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('191', '电视剧-展示区', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('192', '电视剧-英美', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('193', '电视剧-英美', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('194', '电视剧-英美', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('195', '电视剧-英美', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('196', '电视剧-英美', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('197', '电视剧-英美', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('198', '电视剧-英美', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('199', '电视剧-英美', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('200', '电视剧-英美', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('201', '电视剧-英美', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('202', '电视剧-日韩', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('203', '电视剧-日韩', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('204', '电视剧-日韩', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('205', '电视剧-日韩', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('206', '电视剧-日韩', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('207', '电视剧-日韩', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('208', '电视剧-日韩', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('209', '电视剧-日韩', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('210', '电视剧-日韩', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('211', '电视剧-日韩', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('212', '电视剧-港台', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('213', '电视剧-港台', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('214', '电视剧-港台', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('215', '电视剧-港台', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('216', '电视剧-港台', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('217', '电视剧-港台', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('218', '电视剧-港台', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('219', '电视剧-港台', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('220', '电视剧-港台', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('221', '电视剧-港台', '10', '10','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('222', '电视剧-内地', '1', '1','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('223', '电视剧-内地', '2', '2','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('224', '电视剧-内地', '3', '3','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('225', '电视剧-内地', '4', '4','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('226', '电视剧-内地', '5', '5','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('227', '电视剧-内地', '6', '6','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('228', '电视剧-内地', '7', '7','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('229', '电视剧-内地', '8', '8','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('230', '电视剧-内地', '9', '9','2012-10-21 22:18:56');
INSERT INTO `module_movie` VALUES ('231', '电视剧-内地', '10', '10','2012-10-21 22:18:56');



-- ----------------------------
-- 电影/电视附件表
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `attachid` bigint NOT NULL                  COMMENT '附件id',
  `movieid` bigint NOT NULL                  COMMENT '电影/电视id(外)' ,
  `new_name`  varchar(200) NOT NULL     COMMENT '新名称',
  `old_name` varchar(200) default NULL      COMMENT '旧名称',
  `attach_url` varchar(380) NOT NULL   COMMENT '附件下载地址',
  `createtime` datetime NOT NULL          COMMENT '创建时间',
  PRIMARY KEY  (`attachid`),
  FOREIGN KEY   (`movieid`) REFERENCES `movie` (`movieid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `attachment` VALUES ('1', '1', '[驯龙高手].How.To.Train.Your.Dragon.720p.Bluray.x264-CBGB.mkv', '[驯龙高手].How.To.Train.Your.Dragon.720p.Bluray.x264-CBGB.mkv', '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('2', '1', '百度网盘', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('3', '1', '迅雷快传', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('4', '1', '华为网盘', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('5', '1', 'Uploader', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('6', '2', '腾讯网盘', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('7', '2', '[天才眼镜狗].Mr.Peabody.And.Sherman.2014.BluRay.720p.x264.AC3.4Audios-CMCT.mkv', '[天才眼镜狗].Mr.Peabody.And.Sherman.2014.BluRay.720p.x264.AC3.4Audios-CMCT.mkv', '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('8', '3', '百度网盘', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('9', '4', '迅雷快传', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('10', '5', '华为网盘', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('11', '5', 'Uploader', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('12', '6', '腾讯网盘', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('13', '7', 'X战警：逆转未来', 'X-Men.Days.of.Future.Past.2014.KORSUB.720p.HDRip.x264.AC3-SeeHD.mkv', '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('14', '7', '百度网盘', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('15', '8', '迅雷快传', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');
INSERT INTO `attachment` VALUES ('16', '8', '华为网盘', null, '/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg','2012-10-21 22:18:56');

-- ----------------------------
-- 留言表
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `commentid` bigint NOT NULL          COMMENT '留言id' ,
  `userid` bigint NOT NULL                  COMMENT '用户id(外)',
  `movieid` bigint NOT NULL               COMMENT '电影/电视id(外)',
  `content` varchar(300) NOT NULL   COMMENT '留言内容',
  `orderseq` bigint default '-1'             COMMENT '推荐留言排序位置，小于0表示不推荐',
  `createarea` varchar(15) default NULL  COMMENT '创建地区：北京、上海、广州等',
  `createtime` datetime NOT NULL      COMMENT '创建时间',
  PRIMARY KEY  (`commentid`),
  FOREIGN KEY   (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE,
  FOREIGN KEY   (`movieid`) REFERENCES `movie` (`movieid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `comment` VALUES ('1', '1','1','男主角我很喜欢，喜欢楼主多发发这类动画影片','1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('2', '1','1','很好很强大','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('3', '1','1','感谢楼主的分享','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('4', '2','2','终于等到高清版本的了，不容易啊','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('5', '3','3','张三到此一游','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('6', '3','3','Thank you for your sharing ~~','2','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('7', '5','5','有没有别的片源啊~~','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('8', '5','5','跪求更加高清的影片','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('9', '7','7','期待变形金刚4的上映','3','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('10', '7','7','今夜你会不会来','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('11', '8','8','影集网搞得不错，我挺喜欢的','4','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('12', '1','3','突然有一种很冲动想打人的感觉','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('13', '2','2','哈哈，不知道该写什么好','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('14', '3','3','什么时候外星人攻打地球，地球才能和平统一','5','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('15', '4','4','扫地的阿姨又来了','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('16', '8','1','你在哪里高就啊，先生','-1','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('17', '8','1','我不要再继续抱怨了~~','6','海口','2012-10-21 22:18:56');

-- ----------------------------
-- 回复表
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `replyid` bigint NOT NULL                 COMMENT '回复id' ,
  `commentid` bigint NOT NULL          COMMENT '留言id' ,
  `userid` bigint NOT NULL                  COMMENT '用户id(外)',
  `content` varchar(300) NOT NULL   COMMENT '留言内容',
  `createarea` varchar(15) default NULL  COMMENT '创建地区：北京、上海、广州等',
  `createtime` datetime NOT NULL      COMMENT '创建时间',
  PRIMARY KEY  (`replyid`),
  FOREIGN KEY   (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE,
  FOREIGN KEY   (`commentid`) REFERENCES `comment` (`commentid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `reply` VALUES ('1', '1','1','你这是毛评论啊','海口','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('2', '1','2','我并不这么认为','湖南','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('3', '1','3','老实说我觉得电影还行吧，你觉得昵','海口','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('4', '1','4','不要这么客气','海口','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('5', '1','5','这是什么跟什么啊','海口','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('6', '1','6','快来我这边啊','海口','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('7', '1','7','记得那一夜吗','北京','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('8', '1','8','你这是在干什么啊','湖北','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('9', '1','2','那一夜你没有拒绝我','海口','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('10', '1','3','顺序逻辑思维','文昌','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('11', '1','8','我在家看着你哦','沈阳','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('12', '1','2','你别想给我跑啊','海口','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('13', '1','3','Where am I ?','北京','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('14', '2','4','去死 ?','北京','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('15', '3','5','回家干什么 ?','北京','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('16', '2','1','你来了吗 ?','北京','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('17', '3','8','我在马路边 ?','北京','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('18', '2','3','捡到钱了吗 ?','北京','2012-10-21 22:18:56');
INSERT INTO `reply` VALUES ('19', '2','2','Make money ?','北京','2012-10-21 22:18:56');

-- ----------------------------
-- 收藏表
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `favoriteid` bigint NOT NULL             COMMENT '收藏id' ,
  `userid` bigint NOT NULL                  COMMENT '用户id(外)',
  `movieid` bigint NOT NULL               COMMENT '电影/电视id(外)',
  `createtime` datetime NOT NULL      COMMENT '创建时间',
  PRIMARY KEY  (`favoriteid`),
  FOREIGN KEY   (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE,
  FOREIGN KEY   (`movieid`) REFERENCES `movie` (`movieid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `favorite` VALUES ('1', '1','1','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('2', '2','2','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('3', '2','3','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('4', '1','4','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('5', '2','5','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('6', '1','6','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('7', '1','7','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('8', '2','8','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('9', '8','9','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('10', '2','10','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('11', '1','1','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('12', '1','2','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('13', '3','3','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('14', '1','3','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('15', '4','4','2012-10-21 22:18:56');
INSERT INTO `favorite` VALUES ('16', '1','5','2012-10-21 22:18:56');

-- ----------------------------
-- 系统禁用用户表（ip)表
-- ----------------------------
DROP TABLE IF EXISTS `forbitUser`;
CREATE TABLE `forbitUser` (
  `userid` bigint NOT NULL                       COMMENT '用户id',
  `nickname` varchar(35) NOT NULL        COMMENT '昵称',
  `createip` varchar(15) NOT NULL          COMMENT '创建ip',
  `createarea` varchar(15) default NULL  COMMENT '创建地区：北京、上海、广州等',
  PRIMARY KEY  (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `forbitUser` VALUES ('55', '系统禁用ip', '192.168.1.55', '海口');
INSERT INTO `forbitUser` VALUES ('56', '系统禁用ip', '192.168.1.56', '海口');
INSERT INTO `forbitUser` VALUES ('57', '系统禁用ip', '192.168.1.57', '海口');
INSERT INTO `forbitUser` VALUES ('58', '系统禁用ip', '192.168.1.58', '海口');
INSERT INTO `forbitUser` VALUES ('59', '系统禁用ip', '192.168.1.59', '海口');
INSERT INTO `forbitUser` VALUES ('60', '系统禁用ip', '192.168.1.60', '海口');

-- ----------------------------
-- 模块统计表
-- ----------------------------
DROP TABLE IF EXISTS `moduleStat`;
CREATE TABLE `moduleStat` (
  `stat_id` varchar(15) NOT NULL               COMMENT '统计id模块英文名',
  `mod_name` varchar(35) NOT NULL        COMMENT '昵称',
  `statCount` bigint NOT NULL                    COMMENT '统计数',
  PRIMARY KEY  (`stat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
			
INSERT INTO `moduleStat` VALUES ('indexShow', '首页-展示区', 0);
INSERT INTO `moduleStat` VALUES ('indexCarrousel', '首页-论播区', 0);
INSERT INTO `moduleStat` VALUES ('indexSiter', '首页-站长区', 0);
INSERT INTO `moduleStat` VALUES ('indexMovie480p', '首页-电影-480p', 0);
INSERT INTO `moduleStat` VALUES ('indexMovie720p', '首页-电影-720p', 0);
INSERT INTO `moduleStat` VALUES ('indexMovie1080p', '首页-电影-1080p', 0);
INSERT INTO `moduleStat` VALUES ('indexMovieOther', '首页-电影-其它', 0);
INSERT INTO `moduleStat` VALUES ('indexMovieRank', '首页-电影-排行榜', 0);
INSERT INTO `moduleStat` VALUES ('indexTvAmerica', '首页-电视剧-英美', 0);
INSERT INTO `moduleStat` VALUES ('indexTvJapan', '首页-电视剧-日韩', 0);
INSERT INTO `moduleStat` VALUES ('indexTvHongkong', '首页-电视剧-港台', 0);
INSERT INTO `moduleStat` VALUES ('indexTvChina', '首页-电视剧-内地', 0);
INSERT INTO `moduleStat` VALUES ('indexTvRank', '首页-电视剧-排行榜', 0);
INSERT INTO `moduleStat` VALUES ('movieShow', '电影-展示区', 0);
INSERT INTO `moduleStat` VALUES ('movie480p', '电影-480p', 0);
INSERT INTO `moduleStat` VALUES ('movie720p', '电影-720p', 0);
INSERT INTO `moduleStat` VALUES ('movie1080p', '电影-1080p', 0);
INSERT INTO `moduleStat` VALUES ('movieOther', '电影-其它', 0);
INSERT INTO `moduleStat` VALUES ('tvShow', '电视剧-展示区', 0);
INSERT INTO `moduleStat` VALUES ('tvAmerica', '电视剧-英美', 0);
INSERT INTO `moduleStat` VALUES ('tvJapan', '电视剧-日韩', 0);
INSERT INTO `moduleStat` VALUES ('tvHongkong', '电视剧-港台', 0);
INSERT INTO `moduleStat` VALUES ('tvChina', '电视剧-内地', 0);


-- ----------------------------
-- 模块统计表
-- ----------------------------
DROP TABLE IF EXISTS `moduleStat`;
CREATE TABLE `moduleStat` (
  `stat_id` varchar(15) NOT NULL               COMMENT '统计id模块英文名',
  `mod_name` varchar(35) NOT NULL        COMMENT '昵称',
  `statCount` bigint NOT NULL                    COMMENT '统计数',
  PRIMARY KEY  (`stat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
			
INSERT INTO `moduleStat` VALUES ('indexShow', '首页-展示区', 0);
INSERT INTO `moduleStat` VALUES ('indexCarrousel', '首页-论播区', 0);
INSERT INTO `moduleStat` VALUES ('indexSiter', '首页-站长区', 0);
INSERT INTO `moduleStat` VALUES ('indexMovie480p', '首页-电影-480p', 0);
INSERT INTO `moduleStat` VALUES ('indexMovie720p', '首页-电影-720p', 0);
INSERT INTO `moduleStat` VALUES ('indexMovie1080p', '首页-电影-1080p', 0);
INSERT INTO `moduleStat` VALUES ('indexMovieOther', '首页-电影-其它', 0);
INSERT INTO `moduleStat` VALUES ('indexMovieRank', '首页-电影-排行榜', 0);
INSERT INTO `moduleStat` VALUES ('indexTvAmerica', '首页-电视剧-英美', 0);
INSERT INTO `moduleStat` VALUES ('indexTvJapan', '首页-电视剧-日韩', 0);
INSERT INTO `moduleStat` VALUES ('indexTvHongkong', '首页-电视剧-港台', 0);
INSERT INTO `moduleStat` VALUES ('indexTvChina', '首页-电视剧-内地', 0);
INSERT INTO `moduleStat` VALUES ('indexTvRank', '首页-电视剧-排行榜', 0);
INSERT INTO `moduleStat` VALUES ('movieShow', '电影-展示区', 0);
INSERT INTO `moduleStat` VALUES ('movie480p', '电影-480p', 0);
INSERT INTO `moduleStat` VALUES ('movie720p', '电影-720p', 0);
INSERT INTO `moduleStat` VALUES ('movie1080p', '电影-1080p', 0);
INSERT INTO `moduleStat` VALUES ('movieOther', '电影-其它', 0);
INSERT INTO `moduleStat` VALUES ('tvShow', '电视剧-展示区', 0);
INSERT INTO `moduleStat` VALUES ('tvAmerica', '电视剧-英美', 0);
INSERT INTO `moduleStat` VALUES ('tvJapan', '电视剧-日韩', 0);
INSERT INTO `moduleStat` VALUES ('tvHongkong', '电视剧-港台', 0);
INSERT INTO `moduleStat` VALUES ('tvChina', '电视剧-内地', 0);


-- ----------------------------
-- 搜索关键词统计表
-- ----------------------------
DROP TABLE IF EXISTS `keywordStat`;
CREATE TABLE `keywordStat` (
  `id` bigint  NOT NULL  PRIMARY KEY AUTO_INCREMENT COMMENT 'id' ,
  `keyword` varchar(35) NOT NULL                     				COMMENT '一个词作为一个字处理',
  `createtime` datetime NOT NULL     									COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
			
INSERT INTO `keywordStat` VALUES (null, '武林圣手', sysdate());
INSERT INTO `keywordStat` VALUES (null, '你好吗', sysdate());
INSERT INTO `keywordStat` VALUES (null, '你在哪里', sysdate());
INSERT INTO `keywordStat` VALUES (null, '苹果', sysdate());
INSERT INTO `keywordStat` VALUES (null, '影集网', sysdate());
INSERT INTO `keywordStat` VALUES (null, '指环王', sysdate());
INSERT INTO `keywordStat` VALUES (null, '你好吗', sysdate());
INSERT INTO `keywordStat` VALUES (null, '苹果', sysdate());
INSERT INTO `keywordStat` VALUES (null, '哥是只是个传说', sysdate());
INSERT INTO `keywordStat` VALUES (null, '筷子兄弟', sysdate());
INSERT INTO `keywordStat` VALUES (null, '心花怒放', sysdate());
INSERT INTO `keywordStat` VALUES (null, '星际穿越', sysdate());
INSERT INTO `keywordStat` VALUES (null, '筷子兄弟', sysdate());

-- ----------------------------
-- 搜索热门关键词
-- ----------------------------
DROP TABLE IF EXISTS `search_hot`;
CREATE TABLE `search_hot` (
  `id` bigint  NOT NULL  PRIMARY KEY AUTO_INCREMENT COMMENT 'id' ,
  `keyword` varchar(35) NOT NULL  UNIQUE KEY  				COMMENT '一个词作为一个字处理',
  `createtime` datetime NOT NULL     									COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
			
INSERT INTO `search_hot` VALUES (null, '驯龙高手', sysdate());
INSERT INTO `search_hot` VALUES (null, '精灵旅社', sysdate());
INSERT INTO `search_hot` VALUES (null, '心花怒放', sysdate());
INSERT INTO `search_hot` VALUES (null, '飞虎', sysdate());


-- ----------------------------
-- 搜索整词字典（一个词作为一个字处理）
-- ----------------------------
DROP TABLE IF EXISTS `search_word`;
CREATE TABLE `search_word` (
  `id` bigint  NOT NULL  PRIMARY KEY AUTO_INCREMENT COMMENT 'id' ,
  `keyword` varchar(35) NOT NULL   UNIQUE KEY				COMMENT '一个词作为一个字处理',
  `createtime` datetime NOT NULL     									COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `search_word` VALUES (null, 'QQ',sysdate());
INSERT INTO `search_word` VALUES (null, '习近平',sysdate());

-- ----------------------------
-- 搜索禁词字典（一个词作为一个字处理）
-- ----------------------------
DROP TABLE IF EXISTS `search_unword`;
CREATE TABLE `search_unword` (
  `id` bigint  NOT NULL  PRIMARY KEY AUTO_INCREMENT COMMENT 'id' ,
  `keyword` varchar(35) NOT NULL UNIQUE KEY 				COMMENT '一个词作为一个字处理',
  `createtime` datetime NOT NULL     									COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `search_unword` VALUES (null, '啊',sysdate());
INSERT INTO `search_unword` VALUES (null, '阿',sysdate());
INSERT INTO `search_unword` VALUES (null, '哦',sysdate());
INSERT INTO `search_unword` VALUES (null, '吗',sysdate());
INSERT INTO `search_unword` VALUES (null, 'fuck',sysdate());



-- ----------------------------
-- 用户昵称表
-- ----------------------------
DROP TABLE IF EXISTS `usernick`;
CREATE TABLE `usernick` (
  `nickname` varchar(35) NOT NULL       COMMENT '昵称' ,
  PRIMARY KEY  (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `usernick` VALUES ('一页楷书');
INSERT INTO `usernick` VALUES ('你似在笑');
INSERT INTO `usernick` VALUES ('花落满地');
INSERT INTO `usernick` VALUES ('能撑多久');
INSERT INTO `usernick` VALUES ('久橙词桐');
INSERT INTO `usernick` VALUES ('意外笑场');
INSERT INTO `usernick` VALUES ('拥你为帝');
INSERT INTO `usernick` VALUES ('毕竟话少');
INSERT INTO `usernick` VALUES ('很久很旧');
INSERT INTO `usernick` VALUES ('遥不可及');
INSERT INTO `usernick` VALUES ('情深不瘦');
INSERT INTO `usernick` VALUES ('怪我念旧');
INSERT INTO `usernick` VALUES ('有多难忘');
INSERT INTO `usernick` VALUES ('独你暖光');
INSERT INTO `usernick` VALUES ('泪般清澈');
INSERT INTO `usernick` VALUES ('想听情话');
INSERT INTO `usernick` VALUES ('再后来');
INSERT INTO `usernick` VALUES ('主动热情');
INSERT INTO `usernick` VALUES ('满楼风絮');
INSERT INTO `usernick` VALUES ('几分心情');
INSERT INTO `usernick` VALUES ('灯半昏时');
INSERT INTO `usernick` VALUES ('像是施舍');
INSERT INTO `usernick` VALUES ('拱手让位');
INSERT INTO `usernick` VALUES ('散光年华');
INSERT INTO `usernick` VALUES ('人心善变');
INSERT INTO `usernick` VALUES ('我不够好');
INSERT INTO `usernick` VALUES ('离人愁');
INSERT INTO `usernick` VALUES ('无所贪爱');
INSERT INTO `usernick` VALUES ('孤傲王者');
INSERT INTO `usernick` VALUES ('空扰寡人');
INSERT INTO `usernick` VALUES ('情癌晚期');
INSERT INTO `usernick` VALUES ('陪你漫步');
INSERT INTO `usernick` VALUES ('只剩余温');
INSERT INTO `usernick` VALUES ('不负于你');
INSERT INTO `usernick` VALUES ('醉花林中');
INSERT INTO `usernick` VALUES ('单人旅途');
INSERT INTO `usernick` VALUES ('相拥入怀');
INSERT INTO `usernick` VALUES ('稳拿江山');
INSERT INTO `usernick` VALUES ('怎有终了');
INSERT INTO `usernick` VALUES ('流年忘却');
INSERT INTO `usernick` VALUES ('以取茴香');
INSERT INTO `usernick` VALUES ('乔木自燃');
INSERT INTO `usernick` VALUES ('初夕');
INSERT INTO `usernick` VALUES ('失忆旅人');
INSERT INTO `usernick` VALUES ('存忆路人');
INSERT INTO `usernick` VALUES ('十三离婚');
INSERT INTO `usernick` VALUES ('周八结婚');
INSERT INTO `usernick` VALUES ('命里良人');
INSERT INTO `usernick` VALUES ('薄荷菇凉');
INSERT INTO `usernick` VALUES ('柠檬暖男');
INSERT INTO `usernick` VALUES ('梦里爱人');
INSERT INTO `usernick` VALUES ('一挽青丝');
INSERT INTO `usernick` VALUES ('一纸荒年');
INSERT INTO `usernick` VALUES ('南巷长歌');
INSERT INTO `usernick` VALUES ('岛城少女');
INSERT INTO `usernick` VALUES ('独一无二');
INSERT INTO `usernick` VALUES ('像是亲吻');
INSERT INTO `usernick` VALUES ('深知我好');
INSERT INTO `usernick` VALUES ('等不及');
INSERT INTO `usernick` VALUES ('从未走远');
INSERT INTO `usernick` VALUES ('最初模样');
INSERT INTO `usernick` VALUES ('别来无恙');
INSERT INTO `usernick` VALUES ('狼与美女');
INSERT INTO `usernick` VALUES ('爱太烫嘴');
INSERT INTO `usernick` VALUES ('故事很长');
INSERT INTO `usernick` VALUES ('离鱼弥巷');
INSERT INTO `usernick` VALUES ('绅士尽头');
INSERT INTO `usernick` VALUES ('相拥未来');
INSERT INTO `usernick` VALUES ('萌面超人');
INSERT INTO `usernick` VALUES ('你别摆脸');
INSERT INTO `usernick` VALUES ('蓝莓不美');
INSERT INTO `usernick` VALUES ('败于未来');
INSERT INTO `usernick` VALUES ('终成过往');
INSERT INTO `usernick` VALUES ('愚人娱己');
INSERT INTO `usernick` VALUES ('与你习惯');
INSERT INTO `usernick` VALUES ('我是笑话');
INSERT INTO `usernick` VALUES ('九夏忧伤');
INSERT INTO `usernick` VALUES ('看透人心');
INSERT INTO `usernick` VALUES ('病娇萝莉');
INSERT INTO `usernick` VALUES ('温柔待狗');
INSERT INTO `usernick` VALUES ('难免心酸');
INSERT INTO `usernick` VALUES ('腿短任性');
INSERT INTO `usernick` VALUES ('资格与否');
INSERT INTO `usernick` VALUES ('抢眼人物');
INSERT INTO `usernick` VALUES ('三秒时光');
INSERT INTO `usernick` VALUES ('真想放弃');
INSERT INTO `usernick` VALUES ('微笑忍耐');
INSERT INTO `usernick` VALUES ('搪塞自己');
INSERT INTO `usernick` VALUES ('强颜欢笑');
INSERT INTO `usernick` VALUES ('悔不及初');
INSERT INTO `usernick` VALUES ('话不多');
INSERT INTO `usernick` VALUES ('啤酒损友');
INSERT INTO `usernick` VALUES ('假好人');
INSERT INTO `usernick` VALUES ('追逐故人');
INSERT INTO `usernick` VALUES ('愿你好心');
INSERT INTO `usernick` VALUES ('无爱无碍');
INSERT INTO `usernick` VALUES ('第三人称');
INSERT INTO `usernick` VALUES ('不及她美');
INSERT INTO `usernick` VALUES ('无人陪伴');
INSERT INTO `usernick` VALUES ('无力逃跑');
INSERT INTO `usernick` VALUES ('十年同窗');
INSERT INTO `usernick` VALUES ('六月初夏');
INSERT INTO `usernick` VALUES ('五月忘怀');
INSERT INTO `usernick` VALUES ('低八度');
INSERT INTO `usernick` VALUES ('配角碍人');
INSERT INTO `usernick` VALUES ('深海归墟');
INSERT INTO `usernick` VALUES ('违心');
INSERT INTO `usernick` VALUES ('小温暖');
INSERT INTO `usernick` VALUES ('華燈初上');
INSERT INTO `usernick` VALUES ('醒著做夢');
INSERT INTO `usernick` VALUES ('光芒萬丈');
INSERT INTO `usernick` VALUES ('簡簡單單');
INSERT INTO `usernick` VALUES ('喜歡你');
INSERT INTO `usernick` VALUES ('颇然心动');
INSERT INTO `usernick` VALUES ('孤独如影');
INSERT INTO `usernick` VALUES ('不言放弃');
INSERT INTO `usernick` VALUES ('雾里看花');
INSERT INTO `usernick` VALUES ('也曾期许');
INSERT INTO `usernick` VALUES ('思之如狂');
INSERT INTO `usernick` VALUES ('深歌浅醉');
INSERT INTO `usernick` VALUES ('独自走来');
INSERT INTO `usernick` VALUES ('乱世人');
INSERT INTO `usernick` VALUES ('离别以后');
INSERT INTO `usernick` VALUES ('薄凉之人');
INSERT INTO `usernick` VALUES ('难安');
INSERT INTO `usernick` VALUES ('鱼走海哭');
INSERT INTO `usernick` VALUES ('旧时光');
INSERT INTO `usernick` VALUES ('久失你心');
INSERT INTO `usernick` VALUES ('心凉怎暖');
INSERT INTO `usernick` VALUES ('早知是梦');
INSERT INTO `usernick` VALUES ('梦一场');
INSERT INTO `usernick` VALUES ('你好再见');
INSERT INTO `usernick` VALUES ('溶解孤单');
INSERT INTO `usernick` VALUES ('只当梦醒');
INSERT INTO `usernick` VALUES ('孤僻碍人');
INSERT INTO `usernick` VALUES ('一幽青竹');
INSERT INTO `usernick` VALUES ('知我心痛');
INSERT INTO `usernick` VALUES ('笑我自己');
INSERT INTO `usernick` VALUES ('与爱无关');
INSERT INTO `usernick` VALUES ('抢救失败');
INSERT INTO `usernick` VALUES ('笑话如人');
INSERT INTO `usernick` VALUES ('大大大怪');
INSERT INTO `usernick` VALUES ('高冷逗霸');
INSERT INTO `usernick` VALUES ('风在抽我');
INSERT INTO `usernick` VALUES ('一路向北');
INSERT INTO `usernick` VALUES ('白玫瑰');
INSERT INTO `usernick` VALUES ('明年今日');
INSERT INTO `usernick` VALUES ('一吻天荒');
INSERT INTO `usernick` VALUES ('藏在眼里');
INSERT INTO `usernick` VALUES ('渴望收藏');
INSERT INTO `usernick` VALUES ('你还年轻');
INSERT INTO `usernick` VALUES ('阴天快乐');
INSERT INTO `usernick` VALUES ('他不懂我');
INSERT INTO `usernick` VALUES ('泡沫');
INSERT INTO `usernick` VALUES ('情深似海');
INSERT INTO `usernick` VALUES ('爱过你');
INSERT INTO `usernick` VALUES ('花开那年');
INSERT INTO `usernick` VALUES ('最爱是我');
INSERT INTO `usernick` VALUES ('不再心酸');
INSERT INTO `usernick` VALUES ('眼泪有毒');
INSERT INTO `usernick` VALUES ('散与留');
INSERT INTO `usernick` VALUES ('不相往来');
INSERT INTO `usernick` VALUES ('心无所依');
INSERT INTO `usernick` VALUES ('吾谁与归');
INSERT INTO `usernick` VALUES ('等晚安');
INSERT INTO `usernick` VALUES ('此情可待');
INSERT INTO `usernick` VALUES ('怎敢言爱');
INSERT INTO `usernick` VALUES ('葬花离别');
INSERT INTO `usernick` VALUES ('独角戏');
INSERT INTO `usernick` VALUES ('告别昨天');
INSERT INTO `usernick` VALUES ('深得他心');
INSERT INTO `usernick` VALUES ('流水千行');
INSERT INTO `usernick` VALUES ('多说无腻');
INSERT INTO `usernick` VALUES ('眉目如画');
INSERT INTO `usernick` VALUES ('今昔何昔');
INSERT INTO `usernick` VALUES ('灯华如初');
INSERT INTO `usernick` VALUES ('哑涩柠檬');
INSERT INTO `usernick` VALUES ('却上心头');
INSERT INTO `usernick` VALUES ('如梦初令');
INSERT INTO `usernick` VALUES ('故人入梦');
INSERT INTO `usernick` VALUES ('双生城');
INSERT INTO `usernick` VALUES ('侍者倦睡');
INSERT INTO `usernick` VALUES ('眷恋昨天');
INSERT INTO `usernick` VALUES ('檀木瑶琴');
INSERT INTO `usernick` VALUES ('梦与长安');
INSERT INTO `usernick` VALUES ('黻衣绣裳');
INSERT INTO `usernick` VALUES ('黛尽青丝');
INSERT INTO `usernick` VALUES ('心有余悸');
INSERT INTO `usernick` VALUES ('哑然失笑');
INSERT INTO `usernick` VALUES ('待你好');
INSERT INTO `usernick` VALUES ('梦断影碎');
INSERT INTO `usernick` VALUES ('七寸青衫');
INSERT INTO `usernick` VALUES ('不算难忘');
INSERT INTO `usernick` VALUES ('十年九梦');
INSERT INTO `usernick` VALUES ('错清九安');
INSERT INTO `usernick` VALUES ('好久未见');
INSERT INTO `usernick` VALUES ('几度徘徊');
INSERT INTO `usernick` VALUES ('决非假象');
INSERT INTO `usernick` VALUES ('我一个人');
INSERT INTO `usernick` VALUES ('别不快乐');
INSERT INTO `usernick` VALUES ('花开半夏');
INSERT INTO `usernick` VALUES ('撕碎岁月');
INSERT INTO `usernick` VALUES ('寄你笑颜');
INSERT INTO `usernick` VALUES ('顽固姿态');
INSERT INTO `usernick` VALUES ('霜林醉');
INSERT INTO `usernick` VALUES ('再无岁月');
INSERT INTO `usernick` VALUES ('说爱我');
INSERT INTO `usernick` VALUES ('不再疼');
INSERT INTO `usernick` VALUES ('经久不愈');
INSERT INTO `usernick` VALUES ('与你长情');
INSERT INTO `usernick` VALUES ('越碍越深');
INSERT INTO `usernick` VALUES ('伤城');
INSERT INTO `usernick` VALUES ('凉情凉心');
INSERT INTO `usernick` VALUES ('七里笙');
INSERT INTO `usernick` VALUES ('不忘初衷');
INSERT INTO `usernick` VALUES ('久溺深海');
INSERT INTO `usernick` VALUES ('怪我放手');
INSERT INTO `usernick` VALUES ('久别的心');
INSERT INTO `usernick` VALUES ('玖九相离');
INSERT INTO `usernick` VALUES ('已过期');
INSERT INTO `usernick` VALUES ('几次追逐');
INSERT INTO `usernick` VALUES ('重来不提');
INSERT INTO `usernick` VALUES ('一分一秒');
INSERT INTO `usernick` VALUES ('不哭不闹');
INSERT INTO `usernick` VALUES ('万人敬仰');
INSERT INTO `usernick` VALUES ('相视而笑');
INSERT INTO `usernick` VALUES ('绝代疯');
INSERT INTO `usernick` VALUES ('西街姑娘');
INSERT INTO `usernick` VALUES ('事外观众');
INSERT INTO `usernick` VALUES ('空白记忆');
INSERT INTO `usernick` VALUES ('岁月余阴');
INSERT INTO `usernick` VALUES ('多久会走');
INSERT INTO `usernick` VALUES ('酒暖花深');
INSERT INTO `usernick` VALUES ('自欺而已');
INSERT INTO `usernick` VALUES ('念所不及');
INSERT INTO `usernick` VALUES ('还喜欢你');
INSERT INTO `usernick` VALUES ('人心太假');
INSERT INTO `usernick` VALUES ('只是曾经');
INSERT INTO `usernick` VALUES ('几时拥你');
INSERT INTO `usernick` VALUES ('一场雨');
INSERT INTO `usernick` VALUES ('关于今昔');
INSERT INTO `usernick` VALUES ('棹动晨钟');
INSERT INTO `usernick` VALUES ('必能久伴');
INSERT INTO `usernick` VALUES ('一个夏天');
INSERT INTO `usernick` VALUES ('一点安慰');
INSERT INTO `usernick` VALUES ('甜酸哽喉');
INSERT INTO `usernick` VALUES ('眼角的泪');
INSERT INTO `usernick` VALUES ('无病呻吟');
INSERT INTO `usernick` VALUES ('握紧双手');
INSERT INTO `usernick` VALUES ('离人怎扰');
INSERT INTO `usernick` VALUES ('各自远扬');
INSERT INTO `usernick` VALUES ('他很明媚');
INSERT INTO `usernick` VALUES ('爱你没错');
INSERT INTO `usernick` VALUES ('你是信仰');
INSERT INTO `usernick` VALUES ('搀扶到老');
INSERT INTO `usernick` VALUES ('终遇良人');
INSERT INTO `usernick` VALUES ('陪我白头');
INSERT INTO `usernick` VALUES ('暖手季');
INSERT INTO `usernick` VALUES ('幸福干杯');
INSERT INTO `usernick` VALUES ('不及久伴');
INSERT INTO `usernick` VALUES ('我们不散');
INSERT INTO `usernick` VALUES ('为爱奔跑');
INSERT INTO `usernick` VALUES ('秀眉浅淡');
INSERT INTO `usernick` VALUES ('深驻我心');
INSERT INTO `usernick` VALUES ('再见好吗');
INSERT INTO `usernick` VALUES ('却深爱你');
INSERT INTO `usernick` VALUES ('还会回来');
INSERT INTO `usernick` VALUES ('给的在乎');
INSERT INTO `usernick` VALUES ('不过银海');
INSERT INTO `usernick` VALUES ('我只爱你');
INSERT INTO `usernick` VALUES ('给你深爱');
INSERT INTO `usernick` VALUES ('时光静好');
INSERT INTO `usernick` VALUES ('入戏太深');
INSERT INTO `usernick` VALUES ('心里做客');
INSERT INTO `usernick` VALUES ('没有如果');
INSERT INTO `usernick` VALUES ('到我怀里');
INSERT INTO `usernick` VALUES ('阳光三寸');
INSERT INTO `usernick` VALUES ('为你而扬');
INSERT INTO `usernick` VALUES ('男宠三千');
INSERT INTO `usernick` VALUES ('坟场蹦迪');
INSERT INTO `usernick` VALUES ('孤独为伍');
INSERT INTO `usernick` VALUES ('下喊加油');
INSERT INTO `usernick` VALUES ('路过人间');
INSERT INTO `usernick` VALUES ('该减肥了');
INSERT INTO `usernick` VALUES ('一世安逸');
INSERT INTO `usernick` VALUES ('初雪未霁');
INSERT INTO `usernick` VALUES ('爱已停机');
INSERT INTO `usernick` VALUES ('必承其重');
INSERT INTO `usernick` VALUES ('心在梦在');
INSERT INTO `usernick` VALUES ('西瓜的甜');
INSERT INTO `usernick` VALUES ('失之我命');
INSERT INTO `usernick` VALUES ('就别趴下');
INSERT INTO `usernick` VALUES ('苦了父母');
INSERT INTO `usernick` VALUES ('不如争气');
INSERT INTO `usernick` VALUES ('何来畏惧');
INSERT INTO `usernick` VALUES ('信命认命');
INSERT INTO `usernick` VALUES ('赢别喊停');
INSERT INTO `usernick` VALUES ('相信眼泪');
INSERT INTO `usernick` VALUES ('赋墨吟殇');
INSERT INTO `usernick` VALUES ('一詀垨候');
INSERT INTO `usernick` VALUES ('傲做自己');
INSERT INTO `usernick` VALUES ('没有失败');
INSERT INTO `usernick` VALUES ('步于欺骗');
INSERT INTO `usernick` VALUES ('在你身后');
INSERT INTO `usernick` VALUES ('不跟我走');
INSERT INTO `usernick` VALUES ('心软是病');
INSERT INTO `usernick` VALUES ('命的珍惜');
INSERT INTO `usernick` VALUES ('上善若水');
INSERT INTO `usernick` VALUES ('给我未来');
INSERT INTO `usernick` VALUES ('天才知道');
INSERT INTO `usernick` VALUES ('弥猫深巷');
INSERT INTO `usernick` VALUES ('听风吟');
INSERT INTO `usernick` VALUES ('败给现实');
INSERT INTO `usernick` VALUES ('卟忘初心');
INSERT INTO `usernick` VALUES ('不如不见');
INSERT INTO `usernick` VALUES ('放手为空');
INSERT INTO `usernick` VALUES ('伴我以歌');
INSERT INTO `usernick` VALUES ('人去心空');
INSERT INTO `usernick` VALUES ('我不幸福');
INSERT INTO `usernick` VALUES ('盼到你爱');
INSERT INTO `usernick` VALUES ('美丽容颜');
INSERT INTO `usernick` VALUES ('我的阳光');
INSERT INTO `usernick` VALUES ('了却你心');
INSERT INTO `usernick` VALUES ('最佳损友');
INSERT INTO `usernick` VALUES ('无声拥抱');
INSERT INTO `usernick` VALUES ('而是昨天');
INSERT INTO `usernick` VALUES ('绝不会哭');
INSERT INTO `usernick` VALUES ('世界背离');
INSERT INTO `usernick` VALUES ('最亮的星');
INSERT INTO `usernick` VALUES ('几次晚安');
INSERT INTO `usernick` VALUES ('旧日淹没');
INSERT INTO `usernick` VALUES ('觉得安心');
INSERT INTO `usernick` VALUES ('抓摸不定');
INSERT INTO `usernick` VALUES ('说我输了');
INSERT INTO `usernick` VALUES ('怎么挽留');
INSERT INTO `usernick` VALUES ('黑白宣张');
INSERT INTO `usernick` VALUES ('我不负你');
INSERT INTO `usernick` VALUES ('永垂不朽');
INSERT INTO `usernick` VALUES ('细水长流');
INSERT INTO `usernick` VALUES ('爱叫放手');
INSERT INTO `usernick` VALUES ('情非得已');
INSERT INTO `usernick` VALUES ('没有也许');
INSERT INTO `usernick` VALUES ('永远骚动');
INSERT INTO `usernick` VALUES ('半醉半醒');
INSERT INTO `usernick` VALUES ('倾城之恋');
INSERT INTO `usernick` VALUES ('保家卫国');
INSERT INTO `usernick` VALUES ('一丝回忆');
INSERT INTO `usernick` VALUES ('纯属扯淡');
INSERT INTO `usernick` VALUES ('姐不稀罕');
INSERT INTO `usernick` VALUES ('多囍从前');
INSERT INTO `usernick` VALUES ('至死不渝');
INSERT INTO `usernick` VALUES ('花开花落');
INSERT INTO `usernick` VALUES ('时光不弃');
INSERT INTO `usernick` VALUES ('必须性感');
INSERT INTO `usernick` VALUES ('帅得明显');
INSERT INTO `usernick` VALUES ('半世阳光');
INSERT INTO `usernick` VALUES ('人别装神');
INSERT INTO `usernick` VALUES ('好多年了');
INSERT INTO `usernick` VALUES ('哎呀卧槽');
INSERT INTO `usernick` VALUES ('大众逗比');
INSERT INTO `usernick` VALUES ('如此多娇');
INSERT INTO `usernick` VALUES ('一个人闹');
INSERT INTO `usernick` VALUES ('哥是国宝');
INSERT INTO `usernick` VALUES ('贱人过敏');
INSERT INTO `usernick` VALUES ('淺色年華');
INSERT INTO `usernick` VALUES ('属于太阳');
INSERT INTO `usernick` VALUES ('浅巷长歌');
INSERT INTO `usernick` VALUES ('笑很倾城');
INSERT INTO `usernick` VALUES ('年少时光');
INSERT INTO `usernick` VALUES ('注定不远');
INSERT INTO `usernick` VALUES ('孤城凉梦');
INSERT INTO `usernick` VALUES ('年葬空城');
INSERT INTO `usernick` VALUES ('壹個過客');
INSERT INTO `usernick` VALUES ('撞我心上');
INSERT INTO `usernick` VALUES ('会被辜负');
INSERT INTO `usernick` VALUES ('血染蔷薇');
INSERT INTO `usernick` VALUES ('终究是戏');
INSERT INTO `usernick` VALUES ('输给备注');
INSERT INTO `usernick` VALUES ('花落花瓣');
INSERT INTO `usernick` VALUES ('葱葱年華');
INSERT INTO `usernick` VALUES ('我好多余');
INSERT INTO `usernick` VALUES ('谁的年华');
INSERT INTO `usernick` VALUES ('时空思念');
INSERT INTO `usernick` VALUES ('一场戏而已');
INSERT INTO `usernick` VALUES ('偏得我心');
INSERT INTO `usernick` VALUES ('过期不候');
INSERT INTO `usernick` VALUES ('谁敢放肆');
INSERT INTO `usernick` VALUES ('巴黎快乐');
INSERT INTO `usernick` VALUES ('浮生若夢');
INSERT INTO `usernick` VALUES ('柠檬之夏');
INSERT INTO `usernick` VALUES ('柠檬少年');
INSERT INTO `usernick` VALUES ('柠萌先森');
INSERT INTO `usernick` VALUES ('柠檬奶茶');
INSERT INTO `usernick` VALUES ('酸心柠檬');
INSERT INTO `usernick` VALUES ('树下少年');
INSERT INTO `usernick` VALUES ('思密达');
INSERT INTO `usernick` VALUES ('一米阳光');
INSERT INTO `usernick` VALUES ('就不要凶');
INSERT INTO `usernick` VALUES ('我太多情');
INSERT INTO `usernick` VALUES ('痴心易碎');
INSERT INTO `usernick` VALUES ('你就是光');
INSERT INTO `usernick` VALUES ('海不会蓝');
INSERT INTO `usernick` VALUES ('零夜孤寒');
INSERT INTO `usernick` VALUES ('几分在意');
INSERT INTO `usernick` VALUES ('岁月静好');
INSERT INTO `usernick` VALUES ('天荒地老');
INSERT INTO `usernick` VALUES ('轻舞浅唱');
INSERT INTO `usernick` VALUES ('离你更近');
INSERT INTO `usernick` VALUES ('祸害四方');
INSERT INTO `usernick` VALUES ('只有背影');
INSERT INTO `usernick` VALUES ('唯有一心');
INSERT INTO `usernick` VALUES ('遗传我爸');
INSERT INTO `usernick` VALUES ('替我坚强');
INSERT INTO `usernick` VALUES ('莫忘初心');
INSERT INTO `usernick` VALUES ('缓缓流淌');
INSERT INTO `usernick` VALUES ('无药可救');
INSERT INTO `usernick` VALUES ('有多坚强');
INSERT INTO `usernick` VALUES ('软变淑女');
INSERT INTO `usernick` VALUES ('萌被开除');
INSERT INTO `usernick` VALUES ('搁浅的心');
INSERT INTO `usernick` VALUES ('微笑温暖');
INSERT INTO `usernick` VALUES ('萌系小妞');
INSERT INTO `usernick` VALUES ('情仅一心');
INSERT INTO `usernick` VALUES ('五行缺你');
INSERT INTO `usernick` VALUES ('我的红线');
INSERT INTO `usernick` VALUES ('一场空喜');
INSERT INTO `usernick` VALUES ('相爱更长');
INSERT INTO `usernick` VALUES ('像个女王');
INSERT INTO `usernick` VALUES ('独孤漂亮');
INSERT INTO `usernick` VALUES ('寸步不离');
INSERT INTO `usernick` VALUES ('只能装逼');
INSERT INTO `usernick` VALUES ('我太懦弱');
INSERT INTO `usernick` VALUES ('一千根针');
INSERT INTO `usernick` VALUES ('有我和他');
INSERT INTO `usernick` VALUES ('说我有病');
INSERT INTO `usernick` VALUES ('依旧女王');
INSERT INTO `usernick` VALUES ('再不发誓');
INSERT INTO `usernick` VALUES ('许仙何事');
INSERT INTO `usernick` VALUES ('走寻常路');
INSERT INTO `usernick` VALUES ('忘了微笑');
INSERT INTO `usernick` VALUES ('有宛如心');
INSERT INTO `usernick` VALUES ('做好自己');
INSERT INTO `usernick` VALUES ('长情告白');
INSERT INTO `usernick` VALUES ('不如沉默');
INSERT INTO `usernick` VALUES ('囚我终老');
INSERT INTO `usernick` VALUES ('爱我别走');
INSERT INTO `usernick` VALUES ('代表开心');
INSERT INTO `usernick` VALUES ('方知情伤');
INSERT INTO `usernick` VALUES ('浅默悲殇');
INSERT INTO `usernick` VALUES ('荒岛晴空');
INSERT INTO `usernick` VALUES ('我会反光');
INSERT INTO `usernick` VALUES ('安然入梦');
INSERT INTO `usernick` VALUES ('有害健康');
INSERT INTO `usernick` VALUES ('犯二少年');
INSERT INTO `usernick` VALUES ('似水年华');
INSERT INTO `usernick` VALUES ('全靠死撑');
INSERT INTO `usernick` VALUES ('祖坟发光');
INSERT INTO `usernick` VALUES ('分手恋爱');
INSERT INTO `usernick` VALUES ('懂得放棄');
INSERT INTO `usernick` VALUES ('惊艳时光');
INSERT INTO `usernick` VALUES ('唇齿相依');
INSERT INTO `usernick` VALUES ('怎敢去碰');
INSERT INTO `usernick` VALUES ('影子陪我');
INSERT INTO `usernick` VALUES ('就是傻子');
INSERT INTO `usernick` VALUES ('无能为力');
INSERT INTO `usernick` VALUES ('心已荒芜');
INSERT INTO `usernick` VALUES ('做了老班');
INSERT INTO `usernick` VALUES ('巫山云雨');
INSERT INTO `usernick` VALUES ('只是观众');
INSERT INTO `usernick` VALUES ('隐身守候');
INSERT INTO `usernick` VALUES ('好久不见');
INSERT INTO `usernick` VALUES ('一声轻唤');
INSERT INTO `usernick` VALUES ('义无反顾');
INSERT INTO `usernick` VALUES ('骚会闪腰');
INSERT INTO `usernick` VALUES ('脉喝脉动');
INSERT INTO `usernick` VALUES ('面条上吊');
INSERT INTO `usernick` VALUES ('素颜浅笑');
INSERT INTO `usernick` VALUES ('老少年郎');
INSERT INTO `usernick` VALUES ('落满尘埃');
INSERT INTO `usernick` VALUES ('脏了别捡');
INSERT INTO `usernick` VALUES ('不贱不散');
INSERT INTO `usernick` VALUES ('未完待续');
INSERT INTO `usernick` VALUES ('放弃治疗');
INSERT INTO `usernick` VALUES ('童年无忌');
INSERT INTO `usernick` VALUES ('情必相守');
INSERT INTO `usernick` VALUES ('以爱为径');
INSERT INTO `usernick` VALUES ('學絵紾惜');
INSERT INTO `usernick` VALUES ('以命守护');
INSERT INTO `usernick` VALUES ('一片天下');
INSERT INTO `usernick` VALUES ('注定被甩');
INSERT INTO `usernick` VALUES ('安分守己');
INSERT INTO `usernick` VALUES ('由我赞助');
INSERT INTO `usernick` VALUES ('封你为后');
INSERT INTO `usernick` VALUES ('越來越近');
INSERT INTO `usernick` VALUES ('你还是你');
INSERT INTO `usernick` VALUES ('失之淡然');
INSERT INTO `usernick` VALUES ('削足为鱼');
INSERT INTO `usernick` VALUES ('爱为半径');
INSERT INTO `usernick` VALUES ('经过心脏');
INSERT INTO `usernick` VALUES ('别无他爱');
INSERT INTO `usernick` VALUES ('沉睡千年');
INSERT INTO `usernick` VALUES ('生老病死');
INSERT INTO `usernick` VALUES ('厌惡人心');
INSERT INTO `usernick` VALUES ('街角幸福');
INSERT INTO `usernick` VALUES ('一抹浅笑');
INSERT INTO `usernick` VALUES ('一口吃掉你');
INSERT INTO `usernick` VALUES ('且爱且珍惜');
INSERT INTO `usernick` VALUES ('心冷血冷');
INSERT INTO `usernick` VALUES ('落羽成霜');
INSERT INTO `usernick` VALUES ('货比三家');
INSERT INTO `usernick` VALUES ('变成陌生');
INSERT INTO `usernick` VALUES ('笑着原谅');
INSERT INTO `usernick` VALUES ('纯属礼貌');
INSERT INTO `usernick` VALUES ('绝不生离');
INSERT INTO `usernick` VALUES ('五行缺德');
INSERT INTO `usernick` VALUES ('没让你爱');
INSERT INTO `usernick` VALUES ('没事抽风');
INSERT INTO `usernick` VALUES ('结婚对象');
INSERT INTO `usernick` VALUES ('女人温柔');
INSERT INTO `usernick` VALUES ('把你删了');
INSERT INTO `usernick` VALUES ('夏末凉城');
INSERT INTO `usernick` VALUES ('发光少年');
INSERT INTO `usernick` VALUES ('真心喂狗');
INSERT INTO `usernick` VALUES ('提醒有病');
INSERT INTO `usernick` VALUES ('不愿醒来');
INSERT INTO `usernick` VALUES ('骚扰本宫');
INSERT INTO `usernick` VALUES ('人心寂寞');
INSERT INTO `usernick` VALUES ('大胸之兆');
INSERT INTO `usernick` VALUES ('帅哥标准');
INSERT INTO `usernick` VALUES ('萌量不足');
INSERT INTO `usernick` VALUES ('旧城无她');
INSERT INTO `usernick` VALUES ('心软成患');
INSERT INTO `usernick` VALUES ('一世温柔');
INSERT INTO `usernick` VALUES ('後會無期');
INSERT INTO `usernick` VALUES ('欠吻的嘴');
INSERT INTO `usernick` VALUES ('且听风吟');
INSERT INTO `usernick` VALUES ('认识你了');
INSERT INTO `usernick` VALUES ('爱你多久');
INSERT INTO `usernick` VALUES ('在睡懒觉');
INSERT INTO `usernick` VALUES ('久爱不腻');
INSERT INTO `usernick` VALUES ('鸡飞狗跳');
INSERT INTO `usernick` VALUES ('樱花雨');
INSERT INTO `usernick` VALUES ('風情萬種');
INSERT INTO `usernick` VALUES ('嘿皮先森');
INSERT INTO `usernick` VALUES ('毕业不哭');
INSERT INTO `usernick` VALUES ('各奔东西');
INSERT INTO `usernick` VALUES ('我们ト哭');
INSERT INTO `usernick` VALUES ('一无所有');
INSERT INTO `usernick` VALUES ('最后留念');
INSERT INTO `usernick` VALUES ('笑着滚蛋');
INSERT INTO `usernick` VALUES ('拥抱可好');
INSERT INTO `usernick` VALUES ('遥遥无期');
INSERT INTO `usernick` VALUES ('分手典礼');
INSERT INTO `usernick` VALUES ('开始怀念');
INSERT INTO `usernick` VALUES ('爱上学校');
INSERT INTO `usernick` VALUES ('曲终人散');
INSERT INTO `usernick` VALUES ('樱花落');
INSERT INTO `usernick` VALUES ('轮回千世');
INSERT INTO `usernick` VALUES ('不懂我伤');
INSERT INTO `usernick` VALUES ('日出日落');
INSERT INTO `usernick` VALUES ('冷月光');
INSERT INTO `usernick` VALUES ('替莪愛祢');
INSERT INTO `usernick` VALUES ('呼吸的痛');
INSERT INTO `usernick` VALUES ('伊人红妆');
INSERT INTO `usernick` VALUES ('深浅自知');
INSERT INTO `usernick` VALUES ('月下独酌');
INSERT INTO `usernick` VALUES ('人世繁華');
INSERT INTO `usernick` VALUES ('蒲公英');
INSERT INTO `usernick` VALUES ('一生心疼');
INSERT INTO `usernick` VALUES ('心软气硬');
INSERT INTO `usernick` VALUES ('浮生半夏');
INSERT INTO `usernick` VALUES ('相顾无言');
INSERT INTO `usernick` VALUES ('泪湿青衫');
INSERT INTO `usernick` VALUES ('木槿花开');
INSERT INTO `usernick` VALUES ('千年老妖');
INSERT INTO `usernick` VALUES ('溫暖他人');
INSERT INTO `usernick` VALUES ('痛恋上你');
INSERT INTO `usernick` VALUES ('送她回家');
INSERT INTO `usernick` VALUES ('学习失恋');
INSERT INTO `usernick` VALUES ('不交房租');
INSERT INTO `usernick` VALUES ('小丑伤悲');
INSERT INTO `usernick` VALUES ('俯视全球');
INSERT INTO `usernick` VALUES ('无人像你');
INSERT INTO `usernick` VALUES ('还是恶梦');
INSERT INTO `usernick` VALUES ('一脚踩地');
INSERT INTO `usernick` VALUES ('这样就好');
INSERT INTO `usernick` VALUES ('自來水');
INSERT INTO `usernick` VALUES ('薄涼之人');
INSERT INTO `usernick` VALUES ('绿色逗逼');
INSERT INTO `usernick` VALUES ('大众萌比');
INSERT INTO `usernick` VALUES ('面具才懂');
INSERT INTO `usernick` VALUES ('女皇霸气');
INSERT INTO `usernick` VALUES ('失去主动');
INSERT INTO `usernick` VALUES ('让你毁容');
INSERT INTO `usernick` VALUES ('高傲死去');
INSERT INTO `usernick` VALUES ('西服相配');
INSERT INTO `usernick` VALUES ('撕心裂肺');
INSERT INTO `usernick` VALUES ('肝肠寸断');
INSERT INTO `usernick` VALUES ('百病庸医');
INSERT INTO `usernick` VALUES ('解释多余');
INSERT INTO `usernick` VALUES ('边走边忘');
INSERT INTO `usernick` VALUES ('东京天空');
INSERT INTO `usernick` VALUES ('背影惊艳');
INSERT INTO `usernick` VALUES ('孤岛旧情');
INSERT INTO `usernick` VALUES ('你最珍贵');
INSERT INTO `usernick` VALUES ('陌上花开');
INSERT INTO `usernick` VALUES ('沒心沒肺');
INSERT INTO `usernick` VALUES ('一念成空');
INSERT INTO `usernick` VALUES ('发育正常');
INSERT INTO `usernick` VALUES ('細雨輕愁');
INSERT INTO `usernick` VALUES ('抚今忆昔');
INSERT INTO `usernick` VALUES ('一斤幸福');
INSERT INTO `usernick` VALUES ('调戏红杏');
INSERT INTO `usernick` VALUES ('一人一半');
INSERT INTO `usernick` VALUES ('一地真心');
INSERT INTO `usernick` VALUES ('瑾色残年');
INSERT INTO `usernick` VALUES ('不见不念');
INSERT INTO `usernick` VALUES ('谁都不爱');
INSERT INTO `usernick` VALUES ('深情似海');
INSERT INTO `usernick` VALUES ('拔掉插头');
INSERT INTO `usernick` VALUES ('长裙落地');
INSERT INTO `usernick` VALUES ('害怕孤独');
INSERT INTO `usernick` VALUES ('巴拉巴拉');
INSERT INTO `usernick` VALUES ('你真我真');
INSERT INTO `usernick` VALUES ('思考人生');
INSERT INTO `usernick` VALUES ('禁止訪問');
INSERT INTO `usernick` VALUES ('堇色安年');
INSERT INTO `usernick` VALUES ('久伴不倦');
INSERT INTO `usernick` VALUES ('爱你不朽');
INSERT INTO `usernick` VALUES ('幸福聲音');
INSERT INTO `usernick` VALUES ('谁都不娶');
INSERT INTO `usernick` VALUES ('谁是小狗');
INSERT INTO `usernick` VALUES ('袖手天下');
INSERT INTO `usernick` VALUES ('冇妳滿足');
INSERT INTO `usernick` VALUES ('执笔画红');
INSERT INTO `usernick` VALUES ('不说分手');
INSERT INTO `usernick` VALUES ('纯白礼服');
INSERT INTO `usernick` VALUES ('也甜入心');
INSERT INTO `usernick` VALUES ('为您推荐');
INSERT INTO `usernick` VALUES ('早恋健康');
INSERT INTO `usernick` VALUES ('踮起脚尖');
INSERT INTO `usernick` VALUES ('不离不弃');
INSERT INTO `usernick` VALUES ('一种心态');
INSERT INTO `usernick` VALUES ('心已远');
INSERT INTO `usernick` VALUES ('豆蔻年华');
INSERT INTO `usernick` VALUES ('渐渐融化');
INSERT INTO `usernick` VALUES ('不归路');
INSERT INTO `usernick` VALUES ('月色如华');
INSERT INTO `usernick` VALUES ('仰望晴天');
INSERT INTO `usernick` VALUES ('鹹蛋超亼');
INSERT INTO `usernick` VALUES ('一字之差');
INSERT INTO `usernick` VALUES ('浮华一世');
INSERT INTO `usernick` VALUES ('淺眉粉黛');
INSERT INTO `usernick` VALUES ('花落忆年');
INSERT INTO `usernick` VALUES ('东京街角');
INSERT INTO `usernick` VALUES ('半面红妆');
INSERT INTO `usernick` VALUES ('那抹忧傷');
INSERT INTO `usernick` VALUES ('江南烟雨');
INSERT INTO `usernick` VALUES ('情深缘浅');
INSERT INTO `usernick` VALUES ('转身一世');
INSERT INTO `usernick` VALUES ('梨涡浅笑');
INSERT INTO `usernick` VALUES ('过往如烟');
INSERT INTO `usernick` VALUES ('锦瑟安年');
INSERT INTO `usernick` VALUES ('繁华若素');
INSERT INTO `usernick` VALUES ('再加个蛋');
INSERT INTO `usernick` VALUES ('因爲在乎');
INSERT INTO `usernick` VALUES ('酒缺幸福');
INSERT INTO `usernick` VALUES ('成熟未满');
INSERT INTO `usernick` VALUES ('街角樱花');
INSERT INTO `usernick` VALUES ('哇凉哇凉');
INSERT INTO `usernick` VALUES ('幻化成雨');
INSERT INTO `usernick` VALUES ('装疯卖傻');
INSERT INTO `usernick` VALUES ('大众男神');
INSERT INTO `usernick` VALUES ('思念打扰');
INSERT INTO `usernick` VALUES ('自作多情');
INSERT INTO `usernick` VALUES ('念念不忘');
INSERT INTO `usernick` VALUES ('心住一人');
INSERT INTO `usernick` VALUES ('巷尾樱花');
INSERT INTO `usernick` VALUES ('仇家太多');
INSERT INTO `usernick` VALUES ('只有一心');
INSERT INTO `usernick` VALUES ('如此多焦');
INSERT INTO `usernick` VALUES ('不能爱你');
INSERT INTO `usernick` VALUES ('火化了你');
INSERT INTO `usernick` VALUES ('最真的人');
INSERT INTO `usernick` VALUES ('像個笑話');
INSERT INTO `usernick` VALUES ('老子没心');
INSERT INTO `usernick` VALUES ('冷面桃花');
INSERT INTO `usernick` VALUES ('要多堅強');
INSERT INTO `usernick` VALUES ('船到桥头');
INSERT INTO `usernick` VALUES ('男人不毒');
INSERT INTO `usernick` VALUES ('花開時間');
INSERT INTO `usernick` VALUES ('走路太扭');
INSERT INTO `usernick` VALUES ('吸血鬼');
INSERT INTO `usernick` VALUES ('心若荒岛');
INSERT INTO `usernick` VALUES ('一季樱花');
INSERT INTO `usernick` VALUES ('一曲流年');
INSERT INTO `usernick` VALUES ('深爱犯贱');
INSERT INTO `usernick` VALUES ('叙写青春');
INSERT INTO `usernick` VALUES ('一笔一画');
INSERT INTO `usernick` VALUES ('回忆幸福');
INSERT INTO `usernick` VALUES ('有个二逼');
INSERT INTO `usernick` VALUES ('單身上瘾');
INSERT INTO `usernick` VALUES ('各自珍重');
INSERT INTO `usernick` VALUES ('左手年华');
INSERT INTO `usernick` VALUES ('偷抹眼泪');
INSERT INTO `usernick` VALUES ('守候某人');
INSERT INTO `usernick` VALUES ('零碎记忆');
INSERT INTO `usernick` VALUES ('陪你削发');
INSERT INTO `usernick` VALUES ('深海未眠');
INSERT INTO `usernick` VALUES ('不愈心病');
INSERT INTO `usernick` VALUES ('情兽先森');
INSERT INTO `usernick` VALUES ('夏热冬凉');
INSERT INTO `usernick` VALUES ('思念猖狂');
INSERT INTO `usernick` VALUES ('牵强的笑');
INSERT INTO `usernick` VALUES ('铁石心肠');
INSERT INTO `usernick` VALUES ('爱你好深');
INSERT INTO `usernick` VALUES ('恍然如夢');
INSERT INTO `usernick` VALUES ('哄骗小孩');
INSERT INTO `usernick` VALUES ('醉紅顏');
INSERT INTO `usernick` VALUES ('二手烟');
INSERT INTO `usernick` VALUES ('夜里哭泣');
INSERT INTO `usernick` VALUES ('巴山夜雨');
INSERT INTO `usernick` VALUES ('半城烟');
INSERT INTO `usernick` VALUES ('情人节');
INSERT INTO `usernick` VALUES ('一触即破');
INSERT INTO `usernick` VALUES ('各安天涯');
INSERT INTO `usernick` VALUES ('需要你懂');
INSERT INTO `usernick` VALUES ('櫻花滿天');
INSERT INTO `usernick` VALUES ('凉心少年');
INSERT INTO `usernick` VALUES ('三分真心');
INSERT INTO `usernick` VALUES ('南城旧梦');
INSERT INTO `usernick` VALUES ('和祢終老');
INSERT INTO `usernick` VALUES ('看到未来');
INSERT INTO `usernick` VALUES ('互不打扰');
INSERT INTO `usernick` VALUES ('北岛初晴');
INSERT INTO `usernick` VALUES ('巴掌扇去');
INSERT INTO `usernick` VALUES ('直接恋爱');
INSERT INTO `usernick` VALUES ('只能回忆');
INSERT INTO `usernick` VALUES ('陪我久了');
INSERT INTO `usernick` VALUES ('回到原点');
INSERT INTO `usernick` VALUES ('心灰意冷');
INSERT INTO `usernick` VALUES ('用心交友');
INSERT INTO `usernick` VALUES ('磨磨叽叽');
INSERT INTO `usernick` VALUES ('粉黛浅妆');
INSERT INTO `usernick` VALUES ('新闻结束');
INSERT INTO `usernick` VALUES ('长发及地');
INSERT INTO `usernick` VALUES ('贪生怕死');
INSERT INTO `usernick` VALUES ('让人心疼');
INSERT INTO `usernick` VALUES ('ー座城池');
INSERT INTO `usernick` VALUES ('繁华落尽');
INSERT INTO `usernick` VALUES ('百毒不侵');
INSERT INTO `usernick` VALUES ('心疼么');
INSERT INTO `usernick` VALUES ('巴黎铁塔');
INSERT INTO `usernick` VALUES ('资深禽兽');
INSERT INTO `usernick` VALUES ('不败季节');
INSERT INTO `usernick` VALUES ('何去何从');
INSERT INTO `usernick` VALUES ('享受未来');
INSERT INTO `usernick` VALUES ('有你便够');
INSERT INTO `usernick` VALUES ('放过自己');
INSERT INTO `usernick` VALUES ('高攀不起');
INSERT INTO `usernick` VALUES ('永不过期');
INSERT INTO `usernick` VALUES ('欢迎调戏');
INSERT INTO `usernick` VALUES ('霸道给我');
INSERT INTO `usernick` VALUES ('棒棒糖');
INSERT INTO `usernick` VALUES ('幸福敲门');
INSERT INTO `usernick` VALUES ('暖了夏天');
INSERT INTO `usernick` VALUES ('看尽孤独');
INSERT INTO `usernick` VALUES ('为牛奋斗');
INSERT INTO `usernick` VALUES ('繈顏歡笑');
INSERT INTO `usernick` VALUES ('姐的素颜');
INSERT INTO `usernick` VALUES ('北城不夏');
INSERT INTO `usernick` VALUES ('女人装纯');
INSERT INTO `usernick` VALUES ('男人装逼');
INSERT INTO `usernick` VALUES ('情若相守');
INSERT INTO `usernick` VALUES ('十年之后');
INSERT INTO `usernick` VALUES ('最初梦想');
INSERT INTO `usernick` VALUES ('保护色');
INSERT INTO `usernick` VALUES ('淑女的心');
INSERT INTO `usernick` VALUES ('昨晚私奔');
INSERT INTO `usernick` VALUES ('调戏蚊子');
INSERT INTO `usernick` VALUES ('倾城倾国');
INSERT INTO `usernick` VALUES ('为沵停留');
INSERT INTO `usernick` VALUES ('闪耀灯泡');
INSERT INTO `usernick` VALUES ('跌跌撞撞');
INSERT INTO `usernick` VALUES ('你不够好');
INSERT INTO `usernick` VALUES ('夏若琉璃');
INSERT INTO `usernick` VALUES ('柠檬初恋');
INSERT INTO `usernick` VALUES ('古巷老街');
INSERT INTO `usernick` VALUES ('高贵气质');
INSERT INTO `usernick` VALUES ('今夕何夕');
INSERT INTO `usernick` VALUES ('吊儿郎当');
INSERT INTO `usernick` VALUES ('闹够了爱');
INSERT INTO `usernick` VALUES ('一缕微光');
INSERT INTO `usernick` VALUES ('毛毛虫');
INSERT INTO `usernick` VALUES ('專治牛逼');
INSERT INTO `usernick` VALUES ('左手幸福');
INSERT INTO `usernick` VALUES ('死心塌地');
INSERT INTO `usernick` VALUES ('若即若离');
INSERT INTO `usernick` VALUES ('躲在角落');
INSERT INTO `usernick` VALUES ('屁股开花');
INSERT INTO `usernick` VALUES ('私定终生');
INSERT INTO `usernick` VALUES ('呼啦啦');
INSERT INTO `usernick` VALUES ('心动心痛');
INSERT INTO `usernick` VALUES ('好聚好散');
INSERT INTO `usernick` VALUES ('谈情说爱');
INSERT INTO `usernick` VALUES ('三生三世');
INSERT INTO `usernick` VALUES ('夜小妖');
INSERT INTO `usernick` VALUES ('全新幸福');
INSERT INTO `usernick` VALUES ('宠坏幸福');
INSERT INTO `usernick` VALUES ('心软致命');
INSERT INTO `usernick` VALUES ('七年痒了');
INSERT INTO `usernick` VALUES ('心为守侯');
INSERT INTO `usernick` VALUES ('梦的翅膀');
INSERT INTO `usernick` VALUES ('平平淡淡');
INSERT INTO `usernick` VALUES ('薄情少年');
INSERT INTO `usernick` VALUES ('别致伤口');
INSERT INTO `usernick` VALUES ('梦入他心');
INSERT INTO `usernick` VALUES ('偷心大盗');
INSERT INTO `usernick` VALUES ('上课铃声');
INSERT INTO `usernick` VALUES ('流年碎影');
INSERT INTO `usernick` VALUES ('天使旳汨');
INSERT INTO `usernick` VALUES ('夏天蓝海');
INSERT INTO `usernick` VALUES ('亡岛孤心');
INSERT INTO `usernick` VALUES ('童话偶像');
INSERT INTO `usernick` VALUES ('捅了一刀');



SET FOREIGN_KEY_CHECKS=1;
