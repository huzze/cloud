/*
 Navicat Premium Data Transfer

 Source Server         : 10.10.62.121
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 10.10.62.121:3306
 Source Schema         : yf_pms

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 23/10/2023 09:44:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chunk_file
-- ----------------------------
DROP TABLE IF EXISTS `chunk_file`;
CREATE TABLE `chunk_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chunk_num` int(10) NOT NULL DEFAULT 0,
  `chunk_size` bigint(20) NOT NULL DEFAULT 0,
  `cur_chunk_size` bigint(20) NOT NULL DEFAULT 0,
  `total_size` bigint(20) NOT NULL DEFAULT 0,
  `name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `identifier` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `relative_path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `total_chunks` int(10) NOT NULL DEFAULT 0,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
