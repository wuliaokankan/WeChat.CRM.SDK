
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_accountinfo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_accountinfo`;
CREATE TABLE `tbl_accountinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账号id',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称（中文显示名）',
  `type` varchar(255) DEFAULT NULL COMMENT '账号类型（0:管理员，不能登录客户端；1：操作员，可登录客户端，运营后台暂时没有权限）（类型后续可扩展）',
  `cid` int(11) DEFAULT NULL COMMENT '所属的客户id',
  `state` int(11) DEFAULT NULL COMMENT '当前账号状态',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_accountinfo
-- ----------------------------
INSERT INTO `tbl_accountinfo` VALUES ('5', 'yutou', '123456', 'yutou', '1', '7', '1', '2018-10-08 14:20:04');
INSERT INTO `tbl_accountinfo` VALUES ('6', 'admin', '123456', 'admin', '0', '7', '0', '2018-09-21 15:06:59');

-- ----------------------------
-- Table structure for tbl_admininfo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_admininfo`;
CREATE TABLE `tbl_admininfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginname` (`loginname`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_admininfo
-- ----------------------------
INSERT INTO `tbl_admininfo` VALUES ('23', 'admin', 'F6FDFFE48C908DEB0F4C3BD36C032E72', 'admin', '2018-09-17 18:25:47');

-- ----------------------------
-- Table structure for tbl_customerinfo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_customerinfo`;
CREATE TABLE `tbl_customerinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'SupplierId  UnionId,客户id',
  `suppliername` varchar(255) DEFAULT NULL COMMENT '客户名称（中文显示名）',
  `shortname` varchar(255) DEFAULT NULL COMMENT '客户缩写，字母（可包含数字）；如聚播科技->jbkj;使用场景:比如自动生成Admin：jbkj_admin，业务报表之类的',
  `level` int(11) DEFAULT NULL COMMENT '客户等级（待定）',
  `account_num` int(11) DEFAULT NULL COMMENT '最大账号（操作员）数量（依客户等级而定）',
  `device_num` int(11) DEFAULT NULL COMMENT '最大设备数量（依客户等级而定）',
  `validity` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '服务期限',
  `state` int(11) DEFAULT NULL COMMENT '当前客户状态',
  `admin` varchar(255) DEFAULT NULL COMMENT '管理员账号',
  `contact` varchar(255) DEFAULT NULL COMMENT '联系人姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系人电话',
  `description` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_customerinfo
-- ----------------------------
INSERT INTO `tbl_customerinfo` VALUES ('7', '聚播科技', 'jbkj', '1', '100', '1000', '2018-09-20 14:52:47', '1', 'admin', 'admin', '', '', '2018-09-20 14:52:47');

-- ----------------------------
-- Table structure for tbl_phoneinfo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_phoneinfo`;
CREATE TABLE `tbl_phoneinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(255) DEFAULT NULL COMMENT '设备id（一般为imei）',
  `cid` int(11) DEFAULT NULL COMMENT '所属的客户id',
  `accountid` int(11) DEFAULT NULL COMMENT '所属操作员账号id，为空则未分配',
  `nickname` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `brand` varchar(255) DEFAULT NULL COMMENT '手机品牌',
  `module` varchar(255) DEFAULT NULL COMMENT '手机型号',
  `login_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `deviceid` (`deviceid`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_phoneinfo
-- ----------------------------
INSERT INTO `tbl_phoneinfo` VALUES ('52', '861381030532529', '7', null, '861381030532529', '861381030532529', '861381030532529', '2018-11-07 17:29:29', '2018-11-07 17:29:29');
INSERT INTO `tbl_phoneinfo` VALUES ('53', '865203038674192', '7', null, '865203038674192', '865203038674192', '865203038674192', '2018-12-19 09:38:35', '2018-12-19 09:38:35');
INSERT INTO `tbl_phoneinfo` VALUES ('54', '865203038300996', '7', null, '865203038300996', '865203038300996', '865203038300996', '2018-12-19 09:38:36', '2018-12-19 09:38:36');

-- ----------------------------
-- Table structure for tbl_wechat_accountinfo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_wechat_accountinfo`;
CREATE TABLE `tbl_wechat_accountinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechatid` varchar(255) DEFAULT NULL COMMENT '微信id',
  `wechatno` varchar(255) DEFAULT NULL COMMENT '微信账号',
  `wechatnick` varchar(255) DEFAULT NULL COMMENT '微信昵称',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像url',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `isonline` int(11) DEFAULT '1' COMMENT '是否在线   0上线   1下线',
  `islogined` int(11) DEFAULT '1' COMMENT '是否在pc客户端上登录   0上线   1下线',
  `deviceid` varchar(255) DEFAULT NULL COMMENT '微信登陆的设备',
  `cid` int(11) DEFAULT NULL COMMENT '客户id',
  `accountid` int(11) DEFAULT NULL COMMENT 'Pc客户端正在操作该微信的操作员账号id',
  `login_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `deviceid` (`deviceid`,`wechatid`) USING BTREE,
  UNIQUE KEY `deviceid_2` (`deviceid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_wechat_accountinfo
-- ----------------------------
INSERT INTO `tbl_wechat_accountinfo` VALUES ('2', 'wxid_036kg5tnlfoa22', 'dandanenheng0110', '涵涵', '2', 'http://wx.qlogo.cn/mmhead/ver_1/TM6r8cCfoe8t44Vic5smP3rJONS7noKCBWalpWp2dQcPJa9GeKgL1sPicgmkh6BDW66tzm68ySjf5RIDvnJ25HSZ5zglBTYuvKnZQKCYc1qBs/96', '', '奥地利', '施蒂利亚', '0', '0', '865203038674192', '7', '5', '2018-12-19 09:47:48', '2018-12-19 09:47:48');
INSERT INTO `tbl_wechat_accountinfo` VALUES ('3', 'wxid_kg9a5uxz94nl22', 'juboxuyy', '阳光', '2', 'http://wx.qlogo.cn/mmhead/ver_1/Kw5BgnUzWVEiaIfzQB02kXNNLm2a4gQ4VsIfYv3mqQ46QM4R1kObymYs0qCxFIhdeWH77EW2ziaJXjytZ1ujdLEGDyicqL2J0uckLxwxQ8eR3A/96', '', '奥地利', '维也纳', '0', '0', '865203038300996', '7', '5', '2018-12-19 09:47:48', '2018-12-19 09:47:48');
