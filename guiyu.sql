/*
 Navicat Premium Data Transfer

 Source Server         : sanchuang
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 39.106.95.176:3306
 Source Schema         : guiyu

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 25/04/2020 22:20:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for browing_history
-- ----------------------------
DROP TABLE IF EXISTS `browing_history`;
CREATE TABLE `browing_history` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `cid` int(10) DEFAULT NULL COMMENT '文章id',
  `uid` int(10) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '浏览时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of browing_history
-- ----------------------------
BEGIN;
INSERT INTO `browing_history` VALUES (1, 22, 9, '2020-04-04 16:30:48');
INSERT INTO `browing_history` VALUES (2, 22, 9, '2020-04-04 16:32:09');
COMMIT;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `coid` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `cid` int(11) DEFAULT NULL COMMENT '内容id',
  `create_time` datetime DEFAULT NULL,
  `deleted` tinyint(2) DEFAULT '0' COMMENT '0未删除，1已删除',
  `author` varchar(100) DEFAULT NULL COMMENT '评论人名',
  `authorid` int(11) DEFAULT NULL COMMENT '评论人id',
  `ownerid` int(11) DEFAULT NULL COMMENT '作者id',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `ip` varchar(64) DEFAULT NULL COMMENT 'ip地址',
  `agent` varchar(200) DEFAULT NULL COMMENT '设备',
  `text` text COMMENT '评论内容',
  `status` tinyint(2) DEFAULT '0' COMMENT '0为可查看;;1为不可查看',
  `parent` int(11) DEFAULT '0' COMMENT '回复的评论id号;0为第一条评论',
  PRIMARY KEY (`coid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '可获得评论最近更新的时间',
  `deleted` tinyint(2) DEFAULT NULL COMMENT '0未删除，1删除',
  `author` varchar(100) DEFAULT NULL COMMENT '评论发起者名字',
  `author_id` int(11) DEFAULT NULL COMMENT '评论者id',
  `ip` varchar(64) DEFAULT NULL COMMENT 'ip地址',
  `agent` varchar(200) DEFAULT NULL COMMENT '设备',
  `text` text CHARACTER SET utf8 COMMENT '评论内容',
  `parent_id` int(11) DEFAULT NULL COMMENT '0为本身就是文章，1为所属文章',
  `replyid` int(11) DEFAULT NULL COMMENT '0为不回复，id为回复的id',
  `view_num` int(11) DEFAULT NULL COMMENT '查看人数',
  `comment_num` int(11) DEFAULT NULL COMMENT '评论人数',
  `thumb_num` int(11) DEFAULT NULL COMMENT '点赞人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of community
-- ----------------------------
BEGIN;
INSERT INTO `community` VALUES (1, '2020-04-04 21:59:10', '2020-04-04 21:59:10', NULL, '马云', 1, 'string', 'string', 'string', 0, 0, 0, 0, 2);
INSERT INTO `community` VALUES (2, '2020-04-04 22:03:01', '2020-04-04 22:03:01', NULL, '马云', 1, 'string', 'string', 'string', 0, 0, 0, 0, 0);
INSERT INTO `community` VALUES (3, '2020-04-04 22:03:23', '2020-04-04 22:03:23', NULL, '马云', 1, 'string', 'string', 'string', 2, 0, 0, 0, 0);
INSERT INTO `community` VALUES (4, '2020-04-04 22:03:25', '2020-04-04 22:03:25', NULL, '马云', 1, 'string', 'string', 'string', 2, 0, 0, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for contents
-- ----------------------------
DROP TABLE IF EXISTS `contents`;
CREATE TABLE `contents` (
  `cid` int(10) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `ctext` longtext COMMENT '内容',
  `uid` int(10) DEFAULT NULL COMMENT '用户id',
  `ctype` varchar(16) DEFAULT 'post' COMMENT '类型:post;post_draft草稿;attachment附件',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态;0为未发布;1为发布',
  `password` varchar(50) DEFAULT NULL COMMENT '加密密码',
  `comments_num` int(11) DEFAULT NULL COMMENT '评论数量',
  `parent` int(10) DEFAULT '0' COMMENT '所属文章',
  `love_count` int(11) DEFAULT '0' COMMENT '点赞人数',
  `views` int(10) DEFAULT '0' COMMENT '浏览数量',
  `allow_comments` tinyint(2) DEFAULT '1' COMMENT '0为不允许;1为允许',
  `is_hot` tinyint(2) DEFAULT '0' COMMENT '0为非热榜;1为热榜',
  `corder` int(10) DEFAULT NULL COMMENT '附件顺序',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of contents
-- ----------------------------
BEGIN;
INSERT INTO `contents` VALUES (33, 'string', '2020-04-04 17:43:08', '2020-04-04 17:43:08', NULL, 9, 'post', 1, 'string', 0, 0, 0, 0, 0, 0, NULL);
INSERT INTO `contents` VALUES (34, NULL, NULL, NULL, 'http://localhost:8080/upload/e5d02d70-3eb7-4e7e-b518-82ff148fbd8b.png', NULL, 'attachment', NULL, NULL, NULL, 33, NULL, NULL, NULL, NULL, 1);
INSERT INTO `contents` VALUES (35, NULL, NULL, NULL, 'http://localhost:8080/upload/49c80b5e-5564-43df-bd63-57c63f7582f2.png', NULL, 'attachment', NULL, NULL, NULL, 33, NULL, NULL, NULL, NULL, 2);
INSERT INTO `contents` VALUES (36, 'string', '2020-04-04 18:28:07', '2020-04-04 18:28:07', NULL, 9, 'post', 1, 'string', 0, 0, 0, 0, 0, 0, NULL);
INSERT INTO `contents` VALUES (37, NULL, NULL, NULL, 'http://localhost:8080/upload/eeeb92b7-b0f5-4c05-af9b-b3119870d5dc.png', NULL, 'attachment', NULL, NULL, NULL, 36, NULL, NULL, NULL, NULL, 1);
INSERT INTO `contents` VALUES (38, NULL, NULL, NULL, 'http://localhost:8080/upload/dd0bca23-717b-4fbe-9ce3-18816ef58b52.png', NULL, 'attachment', NULL, NULL, NULL, 36, NULL, NULL, NULL, NULL, 2);
INSERT INTO `contents` VALUES (39, 'string', '2020-04-04 18:37:56', '2020-04-04 18:37:56', NULL, 1, 'post', 1, 'string', 0, 0, 0, 0, 0, 0, NULL);
INSERT INTO `contents` VALUES (40, NULL, NULL, NULL, 'http://localhost:8080/upload/c7298bdf-458b-4934-bad4-13161193e868.png', NULL, 'attachment', NULL, NULL, NULL, 39, NULL, NULL, NULL, NULL, 1);
INSERT INTO `contents` VALUES (41, NULL, NULL, NULL, 'http://localhost:8080/upload/e211ba12-ed8b-4cae-b3ba-4a9237ee1e77.png', NULL, 'attachment', NULL, NULL, NULL, 39, NULL, NULL, NULL, NULL, 2);
COMMIT;

-- ----------------------------
-- Table structure for love_contents
-- ----------------------------
DROP TABLE IF EXISTS `love_contents`;
CREATE TABLE `love_contents` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `cid` int(10) DEFAULT NULL COMMENT '文章id',
  `uid` int(10) DEFAULT NULL COMMENT '用户id',
  `deleted` tinyint(2) DEFAULT NULL COMMENT '0为为删除，1位删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of love_contents
-- ----------------------------
BEGIN;
INSERT INTO `love_contents` VALUES (1, '2020-04-04 18:19:49', '2020-04-04 18:19:49', 33, 9, 0);
INSERT INTO `love_contents` VALUES (2, '2020-04-04 18:29:25', '2020-04-04 18:34:24', 36, 9, 1);
INSERT INTO `love_contents` VALUES (3, '2020-04-04 18:34:35', '2020-04-04 18:34:35', 38, 9, 0);
COMMIT;

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
CREATE TABLE `user_file` (
  `id` int(16) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(2) NOT NULL COMMENT '0未删除，1已删除',
  `file_url` varchar(255) NOT NULL COMMENT '文件相对路径',
  `topic` varchar(64) DEFAULT NULL COMMENT '主图',
  `describes` varchar(255) DEFAULT NULL COMMENT '描述',
  `user_id` int(16) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_file
-- ----------------------------
BEGIN;
INSERT INTO `user_file` VALUES (5, '2020-04-04 13:40:53', '2020-04-04 13:40:53', 0, 'http://localhost:8080/upload/7139eb49-b3f9-4d23-ae0b-9d38927a99dd.png', '头像', '头像', 9);
INSERT INTO `user_file` VALUES (6, '2020-04-04 13:41:43', '2020-04-04 13:41:43', 0, 'http://localhost:8080/upload/6c6cd57c-de63-4f86-a4a7-19b017418992.jpg', '背景头像', '背景头像', 9);
INSERT INTO `user_file` VALUES (7, '2020-04-04 15:46:08', '2020-04-04 15:46:08', 0, 'http://localhost:8080/upload/257241d2-4c64-4b6d-9e2f-2de727685e33.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (8, '2020-04-04 15:49:15', '2020-04-04 15:49:15', 0, 'http://localhost:8080/upload/1e2dce7f-2a1c-46c1-a098-1c937484a876.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (9, '2020-04-04 15:49:15', '2020-04-04 15:49:15', 0, 'http://localhost:8080/upload/df1da7d9-8ec8-4762-8a7e-8f9655c05ab9.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (10, '2020-04-04 15:53:27', '2020-04-04 15:53:27', 0, 'http://localhost:8080/upload/eea559b6-7193-4245-9dc9-3428eb279a9d.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (11, '2020-04-04 15:54:21', '2020-04-04 15:54:21', 0, 'http://localhost:8080/upload/a89144fe-c9db-4ef6-a6e9-8716e585d5d5.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (12, '2020-04-04 15:56:55', '2020-04-04 15:56:55', 0, 'http://localhost:8080/upload/b2ff65d7-94a1-4d32-a7ae-a4d077bd1763.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (13, '2020-04-04 16:47:42', '2020-04-04 16:47:42', 0, 'http://localhost:8080/upload/0e83dd09-7033-409d-9d8b-ee97cd63dacb.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (14, '2020-04-04 16:47:42', '2020-04-04 16:47:42', 0, 'http://localhost:8080/upload/931ba71d-71a6-4c75-b120-c08a23a6100b.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (15, '2020-04-04 17:11:30', '2020-04-04 17:11:30', 0, 'http://localhost:8080/upload/8b69b496-e4c6-4f0c-a1c0-8cf0edeee06f.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (16, '2020-04-04 17:11:30', '2020-04-04 17:11:30', 0, 'http://localhost:8080/upload/ef124cb1-ce9e-4e42-92a9-45391348d616.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (17, '2020-04-04 17:30:51', '2020-04-04 17:30:51', 0, 'http://localhost:8080/upload/0f5e5833-bd50-4871-9479-feb2f1c19991.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (18, '2020-04-04 17:30:51', '2020-04-04 17:30:51', 0, 'http://localhost:8080/upload/dd3348a8-1e11-460d-a1f4-843f6d793c00.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (19, '2020-04-04 17:40:45', '2020-04-04 17:40:45', 0, 'http://localhost:8080/upload/e5d02d70-3eb7-4e7e-b518-82ff148fbd8b.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (20, '2020-04-04 17:40:45', '2020-04-04 17:40:45', 0, 'http://localhost:8080/upload/49c80b5e-5564-43df-bd63-57c63f7582f2.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (21, '2020-04-04 18:23:40', '2020-04-04 18:23:40', 0, 'http://localhost:8080/upload/eeeb92b7-b0f5-4c05-af9b-b3119870d5dc.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (22, '2020-04-04 18:23:40', '2020-04-04 18:23:40', 0, 'http://localhost:8080/upload/dd0bca23-717b-4fbe-9ce3-18816ef58b52.png', '文章附件', '文章附件', 9);
INSERT INTO `user_file` VALUES (23, '2020-04-04 18:34:14', '2020-04-04 18:34:14', 0, 'http://localhost:8080/upload/c7298bdf-458b-4934-bad4-13161193e868.png', '文章附件', '文章附件', 1);
INSERT INTO `user_file` VALUES (24, '2020-04-04 18:34:14', '2020-04-04 18:34:14', 0, 'http://localhost:8080/upload/e211ba12-ed8b-4cae-b3ba-4a9237ee1e77.png', '文章附件', '文章附件', 1);
INSERT INTO `user_file` VALUES (25, '2020-04-04 22:18:51', '2020-04-04 22:18:51', 0, 'http://39.98.124.97:8080/upload/2153fa7d-fe93-4bed-a172-50ecbb2b2088.png', '头像', '头像', 1);
INSERT INTO `user_file` VALUES (26, '2020-04-04 22:25:10', '2020-04-04 22:25:10', 0, 'http://39.98.124.97:8080/data/sanchuang//0173af51-be04-4f8e-9319-870aecdb66f1.png', '头像', '头像', 1);
INSERT INTO `user_file` VALUES (27, '2020-04-04 22:25:33', '2020-04-04 22:25:33', 0, 'http://39.98.124.97:8080/data/sanchuang//4b4ab536-0900-4fbc-ada9-ae6833eef255.png', '头像', '头像', 1);
INSERT INTO `user_file` VALUES (28, '2020-04-04 22:31:12', '2020-04-04 22:31:12', 0, 'http://39.98.124.97:8080/data/sanchuang/058bb9ad-5944-4a73-b671-d239d1497f57.png', '头像', '头像', 1);
INSERT INTO `user_file` VALUES (29, '2020-04-04 22:46:05', '2020-04-04 22:46:05', 0, 'http://39.98.124.97:8080/data/sanchuang/4583253c-8463-4e87-8b59-b53e996e6b44.png', '头像', '头像', 1);
INSERT INTO `user_file` VALUES (30, '2020-04-04 22:49:18', '2020-04-04 22:49:18', 0, 'http://39.98.124.97:8080/76f1eba5-3d72-40c0-9442-05c0c3bcd15d.png', '头像', '头像', 1);
COMMIT;

-- ----------------------------
-- Table structure for user_follow
-- ----------------------------
DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow` (
  `id` int(16) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(2) NOT NULL COMMENT '0未删除，1已删除',
  `fans_id` int(16) unsigned NOT NULL COMMENT '粉丝id',
  `follow_id` int(16) unsigned NOT NULL COMMENT '被关注者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_follow
-- ----------------------------
BEGIN;
INSERT INTO `user_follow` VALUES (1, '2020-04-04 18:39:13', '2020-04-04 18:39:30', 1, 1, 1);
INSERT INTO `user_follow` VALUES (2, '2020-04-04 18:39:35', '2020-04-04 18:39:35', 0, 1, 1);
INSERT INTO `user_follow` VALUES (3, '2020-04-04 18:43:58', '2020-04-04 18:43:58', 0, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(16) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(2) NOT NULL COMMENT '0未删除，1已删除',
  `account` varchar(16) DEFAULT NULL COMMENT '用户账号',
  `phone` varchar(11) DEFAULT NULL COMMENT '用户手机号',
  `password` varchar(25) NOT NULL COMMENT '用户密码',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `fans` int(16) unsigned DEFAULT '0' COMMENT '用户粉丝数',
  `nick_name` varchar(64) DEFAULT '小萌新' COMMENT '昵称',
  `sex` tinyint(2) DEFAULT NULL COMMENT '0为男，1为女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `location` varchar(64) DEFAULT NULL COMMENT '所在地',
  `school` varchar(16) DEFAULT NULL COMMENT '学校',
  `occupation` varchar(16) DEFAULT NULL COMMENT '职业',
  `introduce` varchar(64) DEFAULT NULL COMMENT '简介',
  `background_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_index` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES (1, NULL, '2020-04-04 22:49:18', 0, NULL, '13370730939', '123456', 'http://39.98.124.97:8080/76f1eba5-3d72-40c0-9442-05c0c3bcd15d.png', 10, '马云', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (2, NULL, '2020-02-06 07:52:33', 0, NULL, '13370730937', '123456', NULL, 0, '小萌新', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (3, NULL, NULL, 0, NULL, '13370730938', '123456', NULL, 0, '小萌新', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (4, NULL, NULL, 0, NULL, '13123006240', '12345678', NULL, 0, '小萌新', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (5, NULL, NULL, 0, NULL, '13224010650', '12345678', NULL, 0, '小萌新', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (9, '2020-04-04 13:38:55', '2020-04-04 14:26:04', 0, NULL, '15310453249', 'sad', 'string', 0, 'string', 0, '2020-04-04', 'string', '呵老司机啊打开了', 'string', 'string', 'string');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
