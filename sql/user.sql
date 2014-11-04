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
  `longdesc` varchar(380) NOT NULL           COMMENT '详细简介',
  `face650x500` varchar(90) NOT NULL    COMMENT '电影/电视头像650x500',
  `face400x308` varchar(90) NOT NULL    COMMENT '电影/电视头像400x308',
  `face220x169` varchar(90) NOT NULL    COMMENT '电影/电视头像220x169',
  `face150x220` varchar(90) NOT NULL    COMMENT '电影/电视头像150x220',
  `face80x80` varchar(90) NOT NULL       COMMENT '电影/电视头像80x80',
  `picture` varchar(200) NOT NULL             COMMENT '电影/电视截图url，json数组格式如["/upload/abcdddqfghijk.jpg","/upload/abcddddjok.jpg"]',
  `score` float default 0.0                           COMMENT '评分如8.0',
  `approve` int default  0                           COMMENT '赞数',
  `favorite` int default  0                          COMMENT '收藏',
  `download` int default  0                       COMMENT '下载',
  `broswer` int default  0                          COMMENT '浏览',
  `createtime` datetime NOT NULL          COMMENT '创建时间',
  `publishtime` datetime NOT NULL         COMMENT '发布时间',
  PRIMARY KEY  (`movieid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `movie` VALUES ('1', '驯龙高手3D', 'mv', '梦工厂全新3D冒险喜剧力作', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。', '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]', '8.1',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('2', '精灵旅社', 'mv', '吸血鬼与人情未了', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。', '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]', '9.0',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('3', '大药纺', 'tv', '钟嘉欣变身药厂千金', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '1.1',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('4', '后会无期', 'mv', '韩寒导演处女之作', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '5.2',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('5', '催眠大师', 'mv', '知名心理治疗师徐瑞宁和棘手的女病人任小妍之间发生的故事', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '7.3',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('6', '反贪风暴', 'mv', '廉政公署打击贪污', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '3.5',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('7', '分手大师', 'mv', '邓超首次自导自演的“超氏喜剧”电影', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '2.1',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('8', '小时代3', 'mv', '80后作家郭敬明执导处女作', '林萧、顾里、南湘、唐宛如，四个从小一起长大的好姐妹大学毕业进入职场，开始了新的旅程。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '8.3',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('9', '使徒行者', 'tv', '铁三角演绎剧版无间道', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '6.8',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('10', '对我而言，可爱的她', 'tv', 'Rain变身韩版霸道总裁', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '7.9',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('11', '七人魔法使', 'tv', '魔道士的校园后宫', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '5.5',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');
INSERT INTO `movie` VALUES ('12', '最终幻想', 'mv', '魔道士的校园后宫', '讲述一个住在博克岛的维京少年希卡普，他必须通过驯龙测验，才能正式成为维京勇士。驯龙测验即将到来，希卡普必须把握这唯一的机会，向族人和他爸爸证明他存在的价值。但是，当希卡普遇见一只受伤的龙，并且和这只龙成为朋友之后，希卡普的世界将从此彻底改变。',  '/upload/7ac74d80c52ad4a5f3962865c87024ed.jpg','/upload/f14c5acc55f57af95a3ec474f267c54e.jpg','/upload/e11529703a18ebcdd4f58420833b7b59.jpg','/upload/f2c6b443a1006b7e0831e2be1c62c67a.jpg','/upload/6b574f17418b125ffc04282dc9369aad.jpg','["/upload/25c66b33312a5e68b1d09c0d138e7c68.jpg","/upload/102eaeb97bbad64bcc53666d3cefe26a.jpg","/upload/f14c5acc55f57af95a3ec474f267c54e.jpg","/upload/c1d48636e7acc8bd13d3c8fdd81da55c.jpg"]',  '5.5',0,0,0,0, '2012-10-21 22:18:56','2012-10-21 22:18:56');

-- ----------------------------
-- 模块_电影关联表
-- ----------------------------
DROP TABLE IF EXISTS `module_movie`;
CREATE TABLE `module_movie` (
  `modmvid` bigint NOT NULL                  COMMENT '主键id',
  `modname` varchar(30) NOT NULL       COMMENT '模块名称' ,
  `movieid` bigint NOT NULL                     COMMENT '电影/电视id(外)' ,
  `orderseq` int default  0                         COMMENT '排列顺序',
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
  `createarea` varchar(15) default NULL  COMMENT '创建地区：北京、上海、广州等',
  `createtime` datetime NOT NULL      COMMENT '创建时间',
  PRIMARY KEY  (`commentid`),
  FOREIGN KEY   (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE,
  FOREIGN KEY   (`movieid`) REFERENCES `movie` (`movieid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `comment` VALUES ('1', '1','1','男主角我很喜欢，喜欢楼主多发发这类动画影片','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('2', '1','1','很好很强大','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('3', '1','1','感谢楼主的分享','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('4', '2','2','终于等到高清版本的了，不容易啊','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('5', '3','3','张三到此一游','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('6', '3','3','Thank you for your sharing ~~','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('7', '5','5','有没有别的片源啊~~','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('8', '5','5','跪求更加高清的影片','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('9', '7','7','期待变形金刚4的上映','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('10', '7','7','今夜你会不会来','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('11', '8','8','影集网搞得不错，我挺喜欢的','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('12', '1','3','突然有一种很冲动想打人的感觉','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('13', '2','2','哈哈，不知道该写什么好','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('14', '3','3','什么时候外星人攻打地球，地球才能和平统一','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('15', '4','4','扫地的阿姨又来了','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('16', '8','1','你在哪里高就啊，先生','海口','2012-10-21 22:18:56');
INSERT INTO `comment` VALUES ('17', '8','1','我不要再继续抱怨了~~','海口','2012-10-21 22:18:56');

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

SET FOREIGN_KEY_CHECKS=1;
