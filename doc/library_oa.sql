/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost
 Source Database       : library_oa

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : utf-8

 Date: 05/22/2020 17:49:02 PM
*/




SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ms_admin`
-- ----------------------------
DROP TABLE IF EXISTS `ms_admin`;
CREATE TABLE `ms_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_number` varchar(11) NOT NULL COMMENT '登录号',
  `admin_pwd` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `admin_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `login_pre_time` date DEFAULT NULL COMMENT '上次登录时间',
  `del_flg` int(1) DEFAULT '1' COMMENT '是否删除(标记用)',
  `identity_id` int(11) NOT NULL DEFAULT '1' COMMENT '用户权限',
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_number` (`admin_number`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ms_admin`
-- ----------------------------
BEGIN;
INSERT INTO `ms_admin` VALUES ('1', '1101', '123456', 'Bob', '2019-04-12', '1', '0'), ('2', '1102', '123456', '大天狗', '2019-04-09', '1', '0'), ('3', '3', '4', '4', null, '1', '1'), ('4', '2011', '123456', 'yzy', '2020-05-18', '1', '1'), ('5', '5', '5', '5', null, '1', '1'), ('6', '6', '6', '6', null, '1', '1'), ('7', '7', '7', '7', null, '1', '1'), ('8', '8', '8', '8', null, '1', '1'), ('9', '9', '9', '9', null, '1', '1'), ('10', '10', '10', '10', null, '1', '1'), ('11', '11', '11', '11', null, '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `ms_book`
-- ----------------------------
DROP TABLE IF EXISTS `ms_book`;
CREATE TABLE `ms_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '书名',
  `ISBN` varchar(255) DEFAULT NULL COMMENT '统一使用07年新颁布的13位数字',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `introduction` varchar(255) DEFAULT NULL COMMENT '简介',
  `price` varchar(255) DEFAULT NULL COMMENT '价格',
  `publish_time` varchar(255) DEFAULT NULL COMMENT '出版时间',
  `category_id` int(10) DEFAULT NULL COMMENT '类别',
  `image` varchar(255) DEFAULT NULL COMMENT '图片url',
  `create_time` date DEFAULT NULL COMMENT '上架时间',
  `create_admin` varchar(255) DEFAULT NULL COMMENT '上架管理员',
  `update_pre_admin` varchar(255) DEFAULT NULL COMMENT '上一次修改信息的管理员',
  `del_flg` int(1) DEFAULT NULL,
  `sum` int(11) NOT NULL,
  `remainder` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ms_book`
-- ----------------------------
BEGIN;
INSERT INTO `ms_book` VALUES ('1', '活着', '9787506365437', '余华', '《活着》是作家余华的代表作之一，讲述了在大时代背景下，随着内战、三反五反，大跃进，文化大革命等社会变革，徐福贵的人生和家庭不断经受着苦难，到了最后所有亲人都先后离他而去，仅剩下年老的他和一头老牛相依为命。', '25.30', '1923年6月', '1', '', '2019-04-13', 'Bob', '大天狗', '1', '10', '7'), ('3', '2', '2', '2', '2', '2', '2020-05-21', '1', '38a77897-124f-403d-96da-942192b3fa15', '2020-05-21', 'Bob', 'Bob', '1', '2', '0'), ('4', '3', '3', '3', '3', '3', '2020-05-21', '2', '38be617c-5aa3-41b3-bf7e-f343d4a17cf7', '2020-05-21', 'Bob', 'Bob', '1', '3', '1'), ('5', '4', '4', '4', '4', '4', '2020-05-22', '3', 'f4bc6a21-64fb-4ce9-a9ec-fec193362c3d', '2020-05-21', 'Bob', 'Bob', '1', '4', '2'), ('6', '5', '5', '5', '5', '5', '2020-05-21', '4', '3be0532c-48a2-4ea8-b6e0-b1053f18e871', '2020-05-21', 'Bob', 'Bob', '1', '5', '4'), ('7', '6', '6', '6', '6', '6', '2020-05-21', '5', '46ed11c5-5819-4d23-b58a-0a96084e3a12', '2020-05-21', 'Bob', 'Bob', '1', '6', '5'), ('8', '7', '7', '7', '7', '7', '2020-05-21', '6', '63dcaf76-0f07-476b-a529-e0680b15e832', '2020-05-21', 'Bob', 'Bob', '1', '7', '7'), ('9', '8', '8', '8', '8', '8', '2020-05-28', '7', '7ef89434-7c77-47d8-8a2a-2f72866f0ddc', '2020-05-21', 'Bob', 'Bob', '1', '8', '8'), ('10', '9', '9', '9', '9', '9', '2020-05-21', '8', '425b7fd7-c1d3-4bc8-9390-c8d3d4baef7a', '2020-05-21', 'Bob', 'Bob', '1', '9', '9'), ('11', '10', '10', '10', '10', '10', '', '9', 'f4d173fd-b194-4a07-8663-0b47b7e8ab6f', '2020-05-21', 'Bob', 'Bob', '1', '10', '10'), ('12', '11', '11', '11', '11', '11', '2020-05-22', '8', '9500c8d2-ce65-4970-9b3d-3220fe9ed159', '2020-05-21', 'Bob', 'Bob', '1', '11', '11');
COMMIT;

-- ----------------------------
--  Table structure for `ms_borrow`
-- ----------------------------
DROP TABLE IF EXISTS `ms_borrow`;
CREATE TABLE `ms_borrow` (
  `borrow_id` int(11) NOT NULL AUTO_INCREMENT,
  `borrow_reader_id` int(11) NOT NULL,
  `borrow_book_id` int(11) NOT NULL,
  `borrow_booking` enum('预约中','可领取') DEFAULT NULL,
  `borrow_renew` enum('已续借') DEFAULT NULL,
  `borrow_time` datetime DEFAULT NULL,
  `borrow_return_time` datetime DEFAULT NULL,
  `borrow_penalty` float DEFAULT '0',
  PRIMARY KEY (`borrow_id`),
  KEY `borrow_reader_id` (`borrow_reader_id`),
  KEY `borrow_book_id` (`borrow_book_id`),
  CONSTRAINT `ms_borrow_ibfk_1` FOREIGN KEY (`borrow_reader_id`) REFERENCES `ms_admin` (`id`),
  CONSTRAINT `ms_borrow_ibfk_2` FOREIGN KEY (`borrow_book_id`) REFERENCES `ms_book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ms_borrow`
-- ----------------------------
BEGIN;
INSERT INTO `ms_borrow` VALUES ('2', '4', '3', null, '已续借', '2020-05-21 16:06:08', '2020-05-21 16:06:08', '0.2'), ('5', '4', '6', null, null, '2020-05-21 16:08:10', '2020-05-21 16:08:10', '0.2'), ('7', '1', '1', null, null, '2020-05-22 16:59:35', '2020-05-22 16:59:35', '0.1'), ('8', '1', '3', null, null, '2020-05-22 16:59:41', '2020-05-20 16:59:41', '0.3'), ('9', '1', '4', null, null, '2020-05-22 16:59:43', '2020-05-14 16:59:43', '0.9'), ('10', '1', '5', null, null, '2020-05-22 16:59:46', '2020-05-14 16:59:46', '0.9'), ('11', '1', '7', null, null, '2020-05-22 16:59:51', '2020-05-20 16:59:51', '0.3'), ('12', '4', '1', null, null, '2020-05-22 17:00:43', '2020-05-20 17:00:43', '0.3'), ('13', '4', '4', null, null, '2020-05-22 17:04:17', '2020-05-20 17:04:17', '0.3'), ('14', '4', '5', null, null, '2020-05-22 17:04:20', '2020-05-21 17:04:20', '0.2'), ('15', '2', '1', null, null, '2020-05-22 17:05:21', '2020-05-07 17:05:21', '1.6');
COMMIT;

-- ----------------------------
--  Table structure for `ms_category`
-- ----------------------------
DROP TABLE IF EXISTS `ms_category`;
CREATE TABLE `ms_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL COMMENT '类别号',
  `category_name` varchar(255) DEFAULT NULL COMMENT '类别名称',
  `del_flg` int(1) DEFAULT NULL COMMENT '0表示已删除，1表示未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ms_category`
-- ----------------------------
BEGIN;
INSERT INTO `ms_category` VALUES ('1', '1', '小说', '1'), ('2', '2', '文学', '1'), ('3', '3', '动漫', '1'), ('4', '4', '文化', '1'), ('5', '5', '传记', '1'), ('6', '6', '艺术', '1'), ('7', '7', '童书', '1'), ('8', '8', '古籍', '1'), ('9', '9', '历史', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
