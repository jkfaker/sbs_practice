-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: borrow
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_id` int DEFAULT NULL,
  `student_id` bigint NOT NULL,
  `student_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `student_depart` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `student_class` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `teacher_id` bigint DEFAULT NULL,
  `date` date DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_tIme` time DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `num_people` tinyint DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `label` tinyint DEFAULT NULL,
  `reason` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES (1,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',22155040116,'2024-06-20','08:00:00','09:00:00','13657123965',1,'爱心贵州社团活动',1,NULL),(2,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',22155040116,'2024-06-23','08:00:00','09:00:00','13657123965',3,'舞龙社社团活动',2,'预约时间过长'),(3,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',22155040116,'2024-06-23','08:00:00','09:00:00','13657123765',5,'酒店管理学院毕业典礼',1,NULL),(4,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',22155040116,'2024-06-23','09:00:00','10:00:00','13657123965',2,'大学',1,NULL),(5,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',22155040116,'2024-06-24','09:00:00','11:00:00','13657123965',6,'社团活动',1,NULL),(6,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',22155040116,'2024-06-28','11:00:00','13:00:00','13657231965',5,'dadasda',2,''),(7,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',22155040116,'2024-06-29','11:01:00','12:03:00','17682435682',4,'dasdasdada',1,NULL),(8,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',22155040116,'2024-06-28','12:00:00','15:00:00','13657123965',11,'sggfjg',1,NULL),(9,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',NULL,'2024-07-15','12:00:00','13:00:00','13657123965',15,'ASWDADADAD',3,NULL),(10,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',NULL,'2024-07-15','13:30:00','15:45:00','13657123965',15,'aaaaaa',3,NULL),(11,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',NULL,'2024-07-16','08:00:00','08:15:00','18817935993',4,'智慧团建',3,NULL),(12,1,20454040135,'杨栋','酒店管理学院','酒店管理201本科班(国际酒店集团订单班)',NULL,'2024-07-04','12:00:00','12:45:00','13666666855',1,'可以约吗',3,NULL);
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `id` bigint NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `address_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (22155040116,'刘奇铎',1);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'borrow'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-02 23:41:01
