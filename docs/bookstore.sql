/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50548
Source Host           : localhost:3306
Source Database       : bookstore

Target Server Type    : MYSQL
Target Server Version : 50548
File Encoding         : 65001

Date: 2019-04-27 21:08:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(20) DEFAULT NULL,
  `book_name` varchar(50) DEFAULT NULL,
  `brief` varchar(200) DEFAULT NULL,
  `category` varchar(30) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `publish_house` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '俞敏洪', '六级词汇词根+联想记忆法', '出版于西安交通大学出版社，新东方出品。', '工具书', '32', '2013-11-01', '西安交通大学出版社');
INSERT INTO `book` VALUES ('2', '周勇，朱砾', '线性代数', '出版于复旦大学出版社的线性代数大学教科书', '教科书', '29.5', '2014-11-01', '复旦大学出版社');
INSERT INTO `book` VALUES ('3', '黄立宏，黄云清', '高等数学（第三版）下册', '出版于复旦大学出版社的高等数学大学教科书。', '教科书', '34', '2010-05-01', '复旦大学出版社');
