/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : job_recommendation

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 03/04/2024 14:32:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '账号状态 0 禁用 | 1启用',
  `avatar` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE,
  UNIQUE INDEX `email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '11111111111', 'e10adc3949ba59abbe56e057f20f883e', 'admin@qq.com', '管理员1', '0', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/avatar.jpg', '2024-03-22 11:13:13', NULL);

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '电话',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `logo` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'logo',
  `enterprise_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '企业描述',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `title_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职位描述',
  `salary_min` decimal(10, 0) NULL DEFAULT NULL COMMENT '最低薪资',
  `salary_max` decimal(10, 0) NULL DEFAULT NULL COMMENT '最高薪资',
  `title_link` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位外链',
  `title_require` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位要求',
  `hiring_manager` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位联系人',
  `materials` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '认证材料',
  `hire_phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人电话',
  `enterprise_auth` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '2' COMMENT '认证状态 （0 已认证 | 1 待认证 2未认证)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '企业信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enterprise
-- ----------------------------
INSERT INTO `enterprise` VALUES (1, '12345678901', 'e10adc3949ba59abbe56e057f20f883e', '智能科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/apple.jpg', '智能科技有限公司是一家领先的技术公司，专注于软件开发和IT咨询服务。', '技术', '中国广东省XX市XX区XX街道123号', '软件工程师', '我们正在寻找一名有才华的软件工程师加入我们的团队，为创新软件解决方案的开发做出贡献。', 50000, 80000, 'https://www.zhipin.com/job_detail/2c08c111e75221011n1539S8EVVQ.html?lid=6GPZx3II9Bv.search.31&securityId=f1xZgMo04Kywv-u1CqKcTn5qoCHek_nDfH_HMLOZDR2U7UD6hrtSKuU33BsHCW7ByiFvb2dqc6C5mDevs78kfFXp601IBEUpA0Rr43wDm5o5vDLg5UeiSXzoNHZYpCYCdkPCHw5liZ3l&sessionId=', '计算机科学或相关领域的学士学位', '张三', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/%E9%A9%AC%E5%85%8B%E6%80%9D%E7%AB%9E%E8%B5%9B%E4%BE%8B%E6%96%871710671100729.doc', NULL, '0', '2024-03-19 10:00:00', '2024-03-23 13:09:31');
INSERT INTO `enterprise` VALUES (2, '12345678911', 'e10adc3949ba59abbe56e057f20f883e', '智慧医疗科技集团', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/byte.jpg', '智慧医疗科技集团是一家领先的医疗科技公司，致力于提供创新的医疗解决方案。', '医疗', '中国XX省XX市XX区XX街道456号', '医疗软件工程师', '我们正在寻找一名有经验的医疗软件工程师加入我们的团队，共同推动医疗行业的发展。', 60000, 90000, 'https://www.example.com/job_detail', '医学、计算机科学或相关领域的学士学位', '李四', 'https://example.com/hiring_materials.pdf', NULL, '2', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (3, '12345678921', 'e10adc3949ba59abbe56e057f20f883e', '智慧教育科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/lego.jpg', '智慧教育科技有限公司是一家致力于教育科技创新的公司，提供教育解决方案和在线教育服务。', '教育', '中国XX省XX市XX区XX街道789号', '教育软件开发工程师', '我们正在寻找一名教育软件开发工程师加入我们的团队，共同推动教育科技行业的发展。', 55000, 85000, 'https://www.example.com/job_detail', '教育、计算机科学或相关领域的学士学位', '王五', '', NULL, '2', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (4, '12345678931', 'e10adc3949ba59abbe56e057f20f883e', '智慧交通科技股份有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/mihoyo.jpg', '智慧交通科技股份有限公司是一家专注于交通领域的科技公司，提供智能交通解决方案。', '交通', '中国XX省XX市XX区XX街道1010号', '智能交通工程师', '我们正在寻找一名有经验的智能交通工程师加入我们的团队，共同推动交通行业的智能化发展。', 58000, 88000, 'https://www.example.com/job_detail', '交通工程、计算机科学或相关领域的学士学位', '赵六', 'https://example.com/hiring_materials.pdf', NULL, '0', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (5, '12345678941', 'e10adc3949ba59abbe56e057f20f883e', '智能家居科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/mobile.jpg', '智能家居科技有限公司是一家领先的智能家居解决方案提供商，致力于打造智能、便捷的家居生活。', '家居', '中国XX省XX市XX区XX街道1212号', '智能家居软件工程师', '我们正在寻找一名有才华的智能家居软件工程师加入我们的团队，共同打造智能家居产品。', 52000, 82000, 'https://www.example.com/job_detail', '计算机科学、电子工程或相关领域的学士学位', '钱七', 'https://example.com/hiring_materials.pdf', NULL, '2', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (6, '12345678951', 'e10adc3949ba59abbe56e057f20f883e', '智慧金融科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/nike.jpg', '智慧金融科技有限公司是一家专注于金融科技创新的公司，提供智能金融解决方案和金融科技服务。', '金融', '中国XX省XX市XX区XX街道1414号', '金融软件工程师', '我们正在寻找一名有经验的金融软件工程师加入我们的团队，共同推动金融科技行业的创新发展。', 65000, 95000, 'https://www.example.com/job_detail', '金融、计算机科学或相关领域的学士学位', '孙八', '', NULL, '2', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (7, '12345678961', 'e10adc3949ba59abbe56e057f20f883e', '智能制造科技集团', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/oil.jpg', '智能制造科技集团是一家致力于智能制造领域的科技公司，提供智能制造解决方案和智能制造设备。', '制造', '中国XX省XX市XX区XX街道1616号', '智能制造工程师', '我们正在寻找一名有经验的智能制造工程师加入我们的团队，共同推动智能制造技术的应用和发展。', 70000, 100000, 'https://www.example.com/job_detail', '机械工程、自动化或相关领域的学士学位', '李华', 'https://example.com/hiring_materials.pdf', NULL, '0', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (8, '12345678971', 'e10adc3949ba59abbe56e057f20f883e', '智慧能源科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/tcl.jpg', '智慧能源科技有限公司是一家专注于能源科技领域的创新企业，提供智能能源解决方案和能源科技服务。', '能源', '中国XX省XX市XX区XX街道1818号', '智能能源工程师', '我们正在寻找一名有才华的智能能源工程师加入我们的团队，共同推动能源科技的创新和应用。', 60000, 90000, 'https://www.example.com/job_detail', '能源工程、电气工程或相关领域的学士学位', '周强', 'https://example.com/hiring_materials.pdf', NULL, '1', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (9, '12345678981', 'e10adc3949ba59abbe56e057f20f883e', '智慧农业科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/tencent.jpg', '智慧农业科技有限公司是一家致力于农业科技创新的企业，提供智能农业解决方案和农业科技服务。', '农业', '中国XX省XX市XX区XX街道2020号', '智能农业工程师', '我们正在寻找一名有经验的智能农业工程师加入我们的团队，共同推动农业科技的发展和应用。', 55000, 85000, 'https://www.example.com/job_detail', '农业工程、生物工程或相关领域的学士学位', '吴明', '', NULL, '2', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (10, '12345678991', 'e10adc3949ba59abbe56e057f20f883e', '智能城市科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/tencent.jpg', '智能城市科技有限公司是一家专注于智能城市建设的科技企业，提供智慧城市解决方案和智能城市服务。', '城市', '中国XX省XX市XX区XX街道2222号', '智能城市规划师', '我们正在寻找一名有经验的智能城市规划师加入我们的团队，共同推动智慧城市建设的进程。', 67000, 97000, 'https://www.example.com/job_detail', '城市规划、地理信息系统或相关领域的学士学位', '李雷', 'https://example.com/hiring_materials.pdf', NULL, '0', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (13, '12345678021', 'e10adc3949ba59abbe56e057f20f883e', '智能云计算科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/tcl.jpg', '智能云计算科技有限公司是一家致力于云计算领域的创新企业，提供云计算解决方案和云计算服务。', '云计算', '中国XX省XX市XX区XX街道2828号', '云计算工程师', '我们正在寻找一名有经验的云计算工程师加入我们的团队，共同推动云计算技术的发展和应用。', 65000, 105000, 'https://www.example.com/job_detail', '计算机科学、云计算或相关领域的学士学位', '赵芳', 'https://example.com/hiring_materials.pdf', NULL, '0', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (14, '12345678031', 'e10adc3949ba59abbe56e057f20f883e', '智能大数据科技有限公司', 'https://example.com/logo.jpg', '智能大数据科技有限公司是一家专注于大数据领域的科技企业，提供大数据解决方案和大数据分析服务。', '大数据', '中国XX省XX市XX区XX街道3030号', '大数据工程师', '我们正在寻找一名有经验的大数据工程师加入我们的团队，共同推动大数据技术的应用和发展。', 68000, 108000, 'https://www.example.com/job_detail', '计算机科学、数据科学或相关领域的学士学位', '张良', 'https://example.com/hiring_materials.pdf', NULL, '1', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (16, '12345678051', 'e10adc3949ba59abbe56e057f20f883e', '智能软件科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/nike.jpg', '智能软件科技有限公司是一家专注于软件开发领域的科技企业，提供软件开发解决方案和软件开发服务。', '软件开发', '中国XX省XX市XX区XX街道3434号', '软件开发工程师', '我们正在寻找一名有经验的软件开发工程师加入我们的团队，共同推动软件开发技术的创新和应用。', 75000, 115000, 'https://www.example.com/job_detail', '计算机科学、软件工程或相关领域的学士学位', '李白', 'https://example.com/hiring_materials.pdf', NULL, '0', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (17, '12345678061', 'e10adc3949ba59abbe56e057f20f883e', '智能虚拟现实科技有限公司', 'https://example.com/logo.jpg', '智能虚拟现实科技有限公司是一家专注于虚拟现实领域的科技企业，提供虚拟现实解决方案和虚拟现实技术服务。', '虚拟现实', '中国XX省XX市XX区XX街道3636号', '虚拟现实工程师', '我们正在寻找一名有经验的虚拟现实工程师加入我们的团队，共同推动虚拟现实技术的研发和应用。', 70000, 110000, 'https://www.example.com/job_detail', '计算机图形学、虚拟现实或相关领域的学士学位', '王明', 'https://example.com/hiring_materials.pdf', NULL, '1', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (18, '12345678071', 'e10adc3949ba59abbe56e057f20f883e', '智能区块链科技有限公司', 'https://example.com/logo.jpg', '智能区块链科技有限公司是一家专注于区块链领域的科技企业，提供区块链解决方案和区块链技术服务。', '区块链', '中国XX省XX市XX区XX街道3838号', '区块链工程师', '我们正在寻找一名有经验的区块链工程师加入我们的团队，共同推动区块链技术的研发和应用。', 78000, 118000, 'https://www.example.com/job_detail', '计算机科学、区块链或相关领域的学士学位', '刘备', '', NULL, '2', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (19, '12345678081', 'e10adc3949ba59abbe56e057f20f883e', '智能物联网科技有限公司', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/mobile.jpg', '智能物联网科技有限公司是一家专注于物联网领域的科技企业，提供物联网解决方案和物联网技术服务。', '物联网', '中国XX省XX市XX区XX街道4040号', '物联网工程师', '我们正在寻找一名有经验的物联网工程师加入我们的团队，共同推动物联网技术的创新和应用。', 73000, 113000, 'https://www.example.com/job_detail', '计算机科学、物联网或相关领域的学士学位', '张飞', 'https://example.com/hiring_materials.pdf', NULL, '0', '2024-03-19 10:00:00', '2024-03-19 10:00:00');
INSERT INTO `enterprise` VALUES (21, '17574892870', 'e10adc3949ba59abbe56e057f20f883e', '上海米哈游网络科技股份有限公司', NULL, NULL, NULL, NULL, '软件工程师', '我们正在寻找一名有经验的智能农业工程师加入我们的团队，共同推动农业科技的发展和应用。', 1000, 13000, NULL, '计算机科学或相关领域的学士学位', '华明', NULL, '19943233219', '2', '2024-03-23 09:44:53', '2024-03-31 14:38:43');
INSERT INTO `enterprise` VALUES (22, '11111111112', 'e10adc3949ba59abbe56e057f20f883e', 'NetEase', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2', '2024-03-23 09:46:41', '2024-03-23 09:46:41');

-- ----------------------------
-- Table structure for resume
-- ----------------------------
DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `origin_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件原来名称',
  `link_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上传后存储的名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简历信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resume
-- ----------------------------
INSERT INTO `resume` VALUES (1, 'John_Doe_Resume.pdf', 'd93bc09e-6249-4422-8318-caca9b782c58.pdf', '2024-03-17 09:00:00');
INSERT INTO `resume` VALUES (12, '陈云.pdf', '0a80bdf2-6125-4ade-adc2-007cd402b3d8.pdf', '2024-03-17 15:13:18');
INSERT INTO `resume` VALUES (13, '王家丽.docx', '1fe76a2c-6c5a-4b10-95b1-3308b7c7a348.docx', '2024-03-17 19:27:50');
INSERT INTO `resume` VALUES (14, '张伟.docx', '07a2712c-dc34-4ab9-aea5-ddb367ab5de2.docx', '2024-03-17 19:28:35');
INSERT INTO `resume` VALUES (15, '李娜.docx', 'f0539fdf-ec08-41e4-a0f0-399fcd2124d2.docx', '2024-03-17 19:51:18');
INSERT INTO `resume` VALUES (16, '赵阳.docx', '982c1a43-d200-4999-b4c8-caf344b92f84.docx', '2024-03-17 19:53:57');
INSERT INTO `resume` VALUES (17, '陈云.docx', '1a120008-c5a6-4cad-a9cf-3e22caf93f04.docx', '2024-03-17 19:56:43');
INSERT INTO `resume` VALUES (18, '王鹏.pdf', '57699351-6d1d-4cb6-9d1f-0a0927fa493b.pdf', '2024-03-17 19:57:48');
INSERT INTO `resume` VALUES (19, '周佳浩.pdf', '73530389-d1ee-4dd8-8fd4-33b15f31224d.pdf', '2024-03-19 21:04:01');
INSERT INTO `resume` VALUES (20, '周佳浩.pdf', '4695ad4d-f16e-45ae-8282-931f7e335b52.pdf', '2024-03-19 21:07:30');
INSERT INTO `resume` VALUES (21, '李明伯.png', 'fdabe2fa-c36c-4db2-b8c6-5efeb9fa397f.png', '2024-03-20 20:50:59');
INSERT INTO `resume` VALUES (22, '李明伯.png', '1a47d3a9-c785-419c-bc11-2b2e9938d091.png', '2024-03-21 08:36:05');
INSERT INTO `resume` VALUES (23, '陈红渝.pdf', '418fddf2-0ef4-4261-babb-1b551973817a.pdf', '2024-03-21 21:28:56');
INSERT INTO `resume` VALUES (24, '陈红渝.pdf', '1e09519b-f3b5-4c11-aec6-d29b46f6776e.pdf', '2024-03-21 21:50:52');
INSERT INTO `resume` VALUES (25, '李娜.docx', 'd956c08f-3ec3-4f35-ae62-59b158e86707.docx', '2024-03-22 18:29:32');
INSERT INTO `resume` VALUES (26, '王家丽.pdf', '2d60e341-bbd4-4784-9611-4f29589f24ff.pdf', '2024-03-23 11:28:30');
INSERT INTO `resume` VALUES (27, '陈云.docx', 'a72407ba-ef16-4685-b133-4bd0f23d1a4a.docx', '2024-03-23 11:31:08');
INSERT INTO `resume` VALUES (28, '陈云.pdf', 'c3354364-57a2-49f0-ad71-c7b691ec6552.pdf', '2024-03-23 11:31:22');
INSERT INTO `resume` VALUES (29, '陈云.pdf', 'e723d56e-95fb-424c-9c9e-c82aa5ffed1a.docx', '2024-03-26 00:06:20');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号/电话',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `born_year` datetime NULL DEFAULT NULL COMMENT '年龄',
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `education` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学历',
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学校',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专业',
  `resume_id` int NULL DEFAULT NULL COMMENT '简历外键',
  `skills` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '技能',
  `experience` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经历',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '6方面反馈的评分',
  `total_score` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '反馈总评分',
  `remark_text` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '反馈的文本信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `resume_id`(`resume_id` ASC) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '17574892870', 'e10adc3949ba59abbe56e057f20f883e', '陈云', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu1.webp', '2000-01-01 00:00:00', '女', 'chenxiaoyun@163.com', '研究生', '北京大学', '软件工程', 29, ' JavaScript编程、前端开发、用户界面设计', '公司: MNO软件\r\n职位: 前端开发工程师\r\n时间: 2022年8月 - 至今\r\n描述: 负责开发公司Web应用的前端部分，与设计团队合作实现用户友好的界面。\r\n公司: PQR科技\r\n职位: 实习软件工程师\r\n时间: 2021年5月 - 2021年10月\r\n描述: 学习并参与公司项目的开发过程，熟悉前端技术栈和开发流程。', '445453', '5', '这是一个十分好的平台，充分挖掘了用户的潜力，解决了最大的难题', '2024-03-06 23:59:29', '2024-03-31 14:25:56');
INSERT INTO `student` VALUES (2, '13984628790', 'e10adc3949ba59abbe56e057f20f883e', '王家丽', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu2.webp', '1999-02-12 00:00:00', '女', 'wangli@qq.com', '本科', '清华大学', '计算机科学与技术', 24, 'Python编程、数据分析、沟通能力', '公司: ABC科技\r\n职位: 数据分析实习生\r\n时间: 2022年6月 - 2023年1月\r\n描述: 负责收集和分析公司数据，撰写报告并向团队提供数据洞察。\r\n公司: XYZ公司\r\n职位: 软件开发实习生\r\n时间: 2021年3月 - 2021年8月\r\n描述: 参与开发和维护公司的Web应用程序，与团队协作解决技术问题。', '254324', '7', '这是一个十分好的平台，充分挖掘了用户的潜力，解决了最大的难题', '2024-03-06 23:59:29', '2024-03-22 00:38:19');
INSERT INTO `student` VALUES (3, '1997867542', 'e10adc3949ba59abbe56e057f20f883e', '张伟', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu3.webp', '2001-05-20 00:00:00', '男', 'zhangwei@126.com', '本科', '复旦大学', '电子信息工程', NULL, 'Java编程、数据库管理、团队合作', '公司: 123软件\r\n职位: Java开发工程师\r\n时间: 2022年2月 - 至今\r\n描述: 参与公司内部项目的开发和维护，负责编写代码和单元测试。\r\n公司: EFG科技\r\n职位: 数据库管理员\r\n时间: 2021年1月 - 2021年12月\r\n描述: 管理公司数据库的日常运维工作，优化数据库性能并执行备份和恢复任务。', '454545', '3', '这是一个十分好的平台，充分挖掘了用户的潜力，解决了最大的难题', '2024-03-06 23:59:29', '2024-03-07 09:39:35');
INSERT INTO `student` VALUES (4, '1867983671', 'e10adc3949ba59abbe56e057f20f883e', '李娜', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu4.webp', '2000-09-15 00:00:00', '女', 'lina@outlook.com', '本科', '上海交通大学', '工商管理', 19, 'C++编程、算法设计、项目管理', '公司: GHI软件\r\n职位: C++软件工程师\r\n时间: 2023年3月 - 至今\r\n描述: 参与公司桌面应用程序的开发，负责代码编写和性能优化。\r\n公司: JKL科技\r\n职位: 项目经理助理\r\n时间: 2022年1月 - 2022年11月\r\n描述: 协助项目经理制定项目计划和进度安排，跟踪项目进展并协调团队资源。', NULL, NULL, NULL, '2024-03-06 23:59:29', '2024-03-07 09:39:35');
INSERT INTO `student` VALUES (5, '1789456895', 'e10adc3949ba59abbe56e057f20f883e', '赵阳', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu5.webp', '2000-03-08 00:00:00', '男', 'zhaoyang@gmail.com', '本科', '浙江大学', '通信工程', 20, '数据挖掘、机器学习、统计分析', '公司: 数据分析有限公司\r\n职位: 数据科学家\r\n时间: 2022年1月 - 至今\r\n描述: 分析和挖掘大数据，构建机器学习模型解决实际业务问题。\r\n公司: AI创新科技\r\n职位: 研究助理\r\n时间: 2021年6月 - 2021年12月\r\n描述: 协助研究团队进行机器学习算法研究和实验，撰写研究报告和论文。', '142532', '6', '这是一个十分好的平台，充分挖掘了用户的潜力，解决了最大的难题', '2024-03-06 23:59:29', '2024-03-19 21:10:54');
INSERT INTO `student` VALUES (6, '15678457829', 'e10adc3949ba59abbe56e057f20f883e', '刘雨晨', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu6.webp', '1998-11-03 00:00:00', '女', 'liuyuchen@qq.com', '本科', '南京大学', '软件工程', NULL, '数据可视化、用户体验设计、产品管理', '公司: 数据可视化工作室\r\n职位: 数据可视化设计师\r\n时间: 2023年2月 - 至今\r\n描述: 设计和实现数据可视化图表和仪表板，优化用户数据展示效果。\r\n公司: 产品创新公司\r\n职位: 产品经理助理\r\n时间: 2021年9月 - 2022年5月\r\n描述: 协助产品经理进行市场调研和用户需求分析，撰写产品需求文档和原型设计。', NULL, NULL, NULL, '2024-03-06 23:59:29', '2024-03-07 09:39:35');
INSERT INTO `student` VALUES (7, '1598735679', 'e10adc3949ba59abbe56e057f20f883e', '王鹏', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu7.webp', '2000-06-18 00:00:00', '男', 'wangpeng@hotmail.com', '本科', '华中科技大学', '机械工程', NULL, '网络安全、信息安全管理、漏洞分析', '公司: 安全技术有限公司\r\n职位: 网络安全工程师\r\n时间: 2022年5月 - 至今\r\n描述: 分析网络安全威胁和漏洞，设计和实施安全解决方案保护公司网络。\r\n公司: 黑客防护科技\r\n职位: 安全研究实习生\r\n时间: 2021年8月 - 2021年12月\r\n描述: 学习网络安全技术和黑客攻防技巧，参与漏洞研究和安全事件响应。', '524514', '9', '这是一个十分好的平台，充分挖掘了用户的潜力，解决了最大的难题', '2024-03-06 23:59:29', '2024-03-07 09:39:35');
INSERT INTO `student` VALUES (8, '1789854679', 'e10adc3949ba59abbe56e057f20f883e', '周佳浩', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu8.webp', '2001-08-25 00:00:00', '女', 'zhoujia@yahoo.com', '本科', '中国人民大学', '经济学', NULL, '人工智能、自然语言处理、深度学习', '公司: AI创新研究院\r\n职位: AI工程师\r\n时间: 2023年1月 - 至今\r\n描述: 参与人工智能项目的开发和实验，研究和应用深度学习技术解决实际问题。\r\n公司: 语义分析科技\r\n职位: 自然语言处理实习生\r\n时间: 2021年7月 - 2021年11月\r\n描述: 学习自然语言处理算法和技术，参与项目实践并进行模型调优。', NULL, NULL, NULL, '2024-03-06 23:59:29', '2024-03-07 09:39:35');
INSERT INTO `student` VALUES (9, '19945367891', 'e10adc3949ba59abbe56e057f20f883e', '李明伯', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu9.webp', '1999-12-10 00:00:00', '男', 'liming@163.com', '本科', '北京航空航天大学', '自动化', NULL, '数字营销、社交媒体管理、内容创作', '公司: 数字营销机构\r\n职位: 数字营销专员\r\n时间: 2022年3月 - 至今\r\n描述: 策划和执行数字营销活动，管理社交媒体平台并撰写营销内容。\r\n公司: 内容创作工作室\r\n职位: 内容编辑实习生\r\n时间: 2021年4月 - 2021年9月\r\n描述: 参与撰写和编辑各类内容，包括文章、视频和图片等，学习内容创作流程和技巧。', '425454', '5', '这是一个十分好的平台，充分挖掘了用户的潜力，解决了最大的难题', '2024-03-06 23:59:29', '2024-03-07 09:39:35');
INSERT INTO `student` VALUES (10, '18909657893', 'e10adc3949ba59abbe56e057f20f883e', '陈红渝', 'https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/stu10.webp', '2000-07-30 00:00:00', '女', 'chenhong@gmail.com', '本科', '同济大学', '建筑工程', NULL, '人力资源管理、招聘面试、员工培训', '公司: 人力资源咨询公司\r\n职位: 人力资源专员\r\n时间: 2022年4月 - 至今\r\n描述: 协助招聘和面试流程，管理员工档案和培训计划，提供人力资源咨询服务。\r\n公司: HR部门\r\n职位: 实习人力资源助理\r\n时间: 2021年10月 - 2022年2月\r\n描述: 协助人力资源团队进行招聘和培训工作，处理员工福利和绩效考核等事务。', NULL, NULL, NULL, '2024-03-06 23:59:29', '2024-03-07 09:39:35');
INSERT INTO `student` VALUES (12, '17978674562', 'e10adc3949ba59abbe56e057f20f883e', '徐坤', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2024-03-23 09:41:30', '2024-03-23 09:41:30');

SET FOREIGN_KEY_CHECKS = 1;
