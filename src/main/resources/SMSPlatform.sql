/*
Navicat MySQL Data Transfer

Source Server         : 101.132.141.83
Source Server Version : 50724
Source Host           : 101.132.141.83:8806
Source Database       : SMSPlatform

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2018-12-14 16:58:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for consumer
-- ----------------------------
DROP TABLE IF EXISTS `consumer`;
CREATE TABLE `consumer` (
  `user_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `sms_amount` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer
-- ----------------------------
INSERT INTO `consumer` VALUES ('1', 'bnn', '1000', 'banannan', '1');
INSERT INTO `consumer` VALUES ('2', 'bright', '100', 'bright456', '1');

-- ----------------------------
-- Table structure for sendlist
-- ----------------------------
DROP TABLE IF EXISTS `sendlist`;
CREATE TABLE `sendlist` (
  `list_id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `send_to` varchar(255) DEFAULT NULL,
  `send_time` varchar(255) DEFAULT NULL,
  `send_status` varchar(255) DEFAULT NULL,
  `sms_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sendlist
-- ----------------------------
