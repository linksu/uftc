CREATE DATABASE  IF NOT EXISTS `uftc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `uftc`;
--
-- P‰‰k‰ytt‰j‰n k‰ytt‰j‰tili
-- 		K‰ytt‰j‰tunnus:	admin
-- 		Salasana:		admin
--
-- K‰ytt‰j‰tunnukset: etunimi+sukunimen ensimm‰inen kirjain
-- Haastajien salasana:	challenger
-- K‰ytt‰jien salasana:	user
--
-- ------------------------------------------------------
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: uftc
-- ------------------------------------------------------
-- Server version	5.5.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `challenge`
--

DROP TABLE IF EXISTS `challenge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challenge` (
  `challengeId` int(11) NOT NULL AUTO_INCREMENT,
  `ENDTIME` datetime NOT NULL,
  `STARTTIME` datetime NOT NULL,
  `TITLE` varchar(50) CHARACTER SET latin1 NOT NULL,
  `TOTALPOINTS` int(11) DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `uftcId` int(11) DEFAULT NULL,
  PRIMARY KEY (`challengeId`),
  KEY `FKF9B28C435877DCE6` (`uftcId`),
  KEY `FKF9B28C435928A43C` (`userId`),
  CONSTRAINT `FKF9B28C435877DCE6` FOREIGN KEY (`uftcId`) REFERENCES `uftc` (`uftcId`),
  CONSTRAINT `FKF9B28C435928A43C` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge`
--

LOCK TABLES `challenge` WRITE;
/*!40000 ALTER TABLE `challenge` DISABLE KEYS */;
INSERT INTO `challenge` VALUES (1,'2013-03-31 00:00:00','2013-02-01 00:00:00','Google California',1265,17,2,1),(2,'2013-06-30 00:00:00','2013-01-01 00:00:00','Samsung Korea',1815,22,6,1),(3,'2013-12-31 00:00:00','2013-01-01 00:00:00','Nokia UK',2080,21,11,1),(4,'2013-12-31 00:00:00','2013-01-01 00:00:00','Nokia Finland',1455,10,11,1),(5,'2013-09-30 00:00:00','2013-01-01 00:00:00','Google New York',1100,19,2,1),(6,'2013-07-31 00:00:00','2013-01-01 00:00:00','Samsung Germany',1470,17,6,1),(7,'2013-06-30 00:00:00','2013-01-01 00:00:00','Google London',1300,12,2,1);
/*!40000 ALTER TABLE `challenge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challenge_non_approved_users`
--

DROP TABLE IF EXISTS `challenge_non_approved_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challenge_non_approved_users` (
  `userId` int(11) NOT NULL,
  `challengeId` int(11) NOT NULL,
  KEY `FKED14938E94E029B6` (`challengeId`),
  KEY `FKED14938E5928A43C` (`userId`),
  CONSTRAINT `FKED14938E5928A43C` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `FKED14938E94E029B6` FOREIGN KEY (`challengeId`) REFERENCES `challenge` (`challengeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge_non_approved_users`
--

LOCK TABLES `challenge_non_approved_users` WRITE;
/*!40000 ALTER TABLE `challenge_non_approved_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `challenge_non_approved_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challengesportevent`
--

DROP TABLE IF EXISTS `challengesportevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challengesportevent` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POINTFACTOR` int(11) NOT NULL,
  `POINTFACTORTYPE` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `TITLE` varchar(50) CHARACTER SET latin1 NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `challengeId` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK2988142994E029B6` (`challengeId`),
  CONSTRAINT `FK2988142994E029B6` FOREIGN KEY (`challengeId`) REFERENCES `challenge` (`challengeId`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challengesportevent`
--

LOCK TABLES `challengesportevent` WRITE;
/*!40000 ALTER TABLE `challengesportevent` DISABLE KEYS */;
INSERT INTO `challengesportevent` VALUES (1,10,'Kilometer','Running',6,1),(2,5,'Kilometer','Walking',6,1),(3,15,'Kilometer','Cycling',6,1),(4,10,'Kilometer','Hiking',6,1),(5,6,'Minutes','Swimming',6,1),(6,7,'Minutes','Riding',6,1),(7,10,'Kilometer','Running',11,2),(8,5,'Kilometer','Walking',11,2),(9,15,'Kilometer','Cycling',11,2),(10,10,'Kilometer','Hiking',11,2),(11,6,'Minutes','Swimming',11,2),(12,7,'Minutes','Riding',11,2),(13,10,'Kilometer','Running',10,3),(14,5,'Kilometer','Walking',10,3),(15,15,'Kilometer','Cycling',10,3),(16,10,'Kilometer','Hiking',10,3),(17,6,'Minutes','Swimming',10,3),(18,7,'Minutes','Riding',10,3),(19,10,'Kilometer','Running',4,4),(20,5,'Kilometer','Walking',4,4),(21,15,'Kilometer','Cycling',4,4),(22,10,'Kilometer','Hiking',4,4),(23,6,'Minutes','Swimming',4,4),(24,7,'Minutes','Riding',4,4),(25,10,'Kilometer','Running',9,5),(26,5,'Kilometer','Walking',9,5),(27,15,'Kilometer','Cycling',9,5),(28,10,'Kilometer','Hiking',9,5),(29,6,'Minutes','Swimming',9,5),(30,7,'Minutes','Riding',9,5),(31,10,'Kilometer','Running',7,6),(32,5,'Kilometer','Walking',7,6),(33,15,'Kilometer','Cycling',7,6),(34,10,'Kilometer','Hiking',7,6),(35,6,'Minutes','Swimming',7,6),(36,7,'Minutes','Riding',7,6),(37,10,'Kilometer','Running',6,7),(38,5,'Kilometer','Walking',6,7),(39,15,'Kilometer','Cycling',6,7),(40,10,'Kilometer','Hiking',6,7),(41,6,'Minutes','Swimming',6,7),(42,7,'Minutes','Riding',6,7),(43,3,'Repetition','Push-ups',2,1),(44,3,'Repetition','Sit-ups',0,1),(45,5,'Minutes','Gym',0,2),(46,5,'Minutes','Gym',0,6),(47,9,'Minutes','Aerobic',0,3),(48,6,'Minutes','Futsal',0,3),(49,8,'Minutes','Rugby',0,3),(50,10,'Hours','Golf',0,5);
/*!40000 ALTER TABLE `challengesportevent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sportevent`
--

DROP TABLE IF EXISTS `sportevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sportevent` (
  `sportEventId` int(11) NOT NULL AUTO_INCREMENT,
  `POINTFACTOR` int(11) NOT NULL,
  `POINTFACTORTYPE` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `TITLE` varchar(50) CHARACTER SET latin1 NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `uftcId` int(11) NOT NULL,
  PRIMARY KEY (`sportEventId`),
  KEY `FK1CB9BFA65877DCE6` (`uftcId`),
  CONSTRAINT `FK1CB9BFA65877DCE6` FOREIGN KEY (`uftcId`) REFERENCES `uftc` (`uftcId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sportevent`
--

LOCK TABLES `sportevent` WRITE;
/*!40000 ALTER TABLE `sportevent` DISABLE KEYS */;
INSERT INTO `sportevent` VALUES (1,10,'Kilometer','Running',0,1),(2,5,'Kilometer','Walking',0,1),(3,15,'Kilometer','Cycling',0,1),(4,10,'Kilometer','Hiking',2,1),(5,6,'Minutes','Swimming',0,1),(6,7,'Minutes','Riding',2,1);
/*!40000 ALTER TABLE `sportevent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uftc`
--

DROP TABLE IF EXISTS `uftc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uftc` (
  `uftcId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uftcId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uftc`
--

LOCK TABLES `uftc` WRITE;
/*!40000 ALTER TABLE `uftc` DISABLE KEYS */;
INSERT INTO `uftc` VALUES (1);
/*!40000 ALTER TABLE `uftc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `AUTHORITY` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `FIRSTNAME` varchar(30) CHARACTER SET latin1 NOT NULL,
  `LASTNAME` varchar(50) CHARACTER SET latin1 NOT NULL,
  `PASSWORD` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `USERNAME` varchar(20) CHARACTER SET latin1 NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `uftcId` int(11) NOT NULL,
  PRIMARY KEY (`userId`),
  KEY `FK27E3CB5877DCE6` (`uftcId`),
  CONSTRAINT `FK27E3CB5877DCE6` FOREIGN KEY (`uftcId`) REFERENCES `uftc` (`uftcId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ROLE_ADMIN','','Melinda','Macdonald','21232f297a57a5a743894a0e4a801fc3','admin',18,1),(2,'ROLE_CHALLENGER','','Luke','Wiggins','2bf76f87471d1357562065abf45c59b0','lukew',10,1),(3,'ROLE_USER','','Holly','Williams','ee11cbb19052e40b07aac0ca060c23ee','hollyw',9,1),(4,'ROLE_USER','','Brian','Wilson','ee11cbb19052e40b07aac0ca060c23ee','brianw',3,1),(5,'ROLE_USER','','Ashley','Bennett','ee11cbb19052e40b07aac0ca060c23ee','ashleyb',3,1),(6,'ROLE_CHALLENGER','','Mark','Collins','2bf76f87471d1357562065abf45c59b0','markc',3,1),(7,'ROLE_USER','','Amanda','Cooper','ee11cbb19052e40b07aac0ca060c23ee','amandac',4,1),(8,'ROLE_USER','','Steven','Parker','ee11cbb19052e40b07aac0ca060c23ee','stevenp',3,1),(9,'ROLE_USER','','Jennifer','Murphy','ee11cbb19052e40b07aac0ca060c23ee','jenniferm',3,1),(10,'ROLE_USER','','Ryan','Reed','ee11cbb19052e40b07aac0ca060c23ee','ryanr',4,1),(11,'ROLE_CHALLENGER','','Amber','Evans','2bf76f87471d1357562065abf45c59b0','ambere',4,1),(12,'ROLE_USER','','Kyle','Ward','ee11cbb19052e40b07aac0ca060c23ee','kylew',2,1),(13,'ROLE_USER','','Rachel','Gray','ee11cbb19052e40b07aac0ca060c23ee','rachelg',2,1),(14,'ROLE_USER','','Leeroy','Jenkins','ee11cbb19052e40b07aac0ca060c23ee','leeroyj',6,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_challenge`
--

DROP TABLE IF EXISTS `user_challenge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_challenge` (
  `userId` int(11) NOT NULL,
  `challengeId` int(11) NOT NULL,
  KEY `FKB10260EF94E029B6` (`challengeId`),
  KEY `FKB10260EF5928A43C` (`userId`),
  CONSTRAINT `FKB10260EF5928A43C` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `FKB10260EF94E029B6` FOREIGN KEY (`challengeId`) REFERENCES `challenge` (`challengeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_challenge`
--

LOCK TABLES `user_challenge` WRITE;
/*!40000 ALTER TABLE `user_challenge` DISABLE KEYS */;
INSERT INTO `user_challenge` VALUES (3,1),(2,1),(3,7),(2,7),(5,7),(3,5),(2,5),(4,5),(5,5),(7,6),(9,6),(10,6),(7,2),(6,2),(8,2),(10,2),(13,4),(14,4),(12,3),(11,3),(14,3);
/*!40000 ALTER TABLE `user_challenge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout`
--

DROP TABLE IF EXISTS `workout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workout` (
  `workoutId` int(11) NOT NULL AUTO_INCREMENT,
  `REPETITION` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `version` int(11) DEFAULT NULL,
  `challengeSportEvent` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`workoutId`),
  KEY `FK85B3CE7DA14886E7` (`challengeSportEvent`),
  KEY `FK85B3CE7D5928A43C` (`userId`),
  CONSTRAINT `FK85B3CE7D5928A43C` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `FK85B3CE7DA14886E7` FOREIGN KEY (`challengeSportEvent`) REFERENCES `challengesportevent` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout`
--

LOCK TABLES `workout` WRITE;
/*!40000 ALTER TABLE `workout` DISABLE KEYS */;
INSERT INTO `workout` VALUES (1,40,'2013-02-17 16:48:03',0,43,2),(2,5,'2013-02-10 16:48:13',0,4,2),(3,8,'2013-02-17 16:48:36',0,2,2),(4,30,'2013-02-10 16:48:51',0,43,2),(5,30,'2013-02-17 16:49:06',0,44,2),(6,8,'2013-02-10 16:49:39',0,1,3),(7,3,'2013-02-17 16:49:46',0,2,3),(8,60,'2013-02-10 16:50:04',0,5,3),(9,60,'2013-02-17 16:50:15',0,6,3),(10,10,'2013-02-10 16:50:49',0,40,3),(11,60,'2013-02-17 16:51:02',0,42,3),(12,90,'2013-02-10 16:52:04',0,29,3),(13,10,'2013-02-12 16:52:12',0,26,3),(14,20,'2013-02-12 16:52:55',0,27,4),(15,10,'2013-02-15 16:53:53',0,26,4),(16,15,'2013-02-15 16:55:20',0,40,5),(17,10,'2013-02-11 16:55:52',0,39,5),(18,8,'2013-02-11 16:56:06',0,26,5),(19,30,'2013-02-14 16:58:22',0,11,6),(20,30,'2013-02-14 16:59:32',0,45,6),(21,10,'2013-02-14 17:01:01',2,32,7),(22,10,'2013-02-18 17:01:01',2,34,7),(23,60,'2013-02-18 17:02:34',0,46,7),(24,10,'2013-02-18 17:02:45',0,8,7),(25,60,'2013-02-13 17:03:20',0,45,8),(26,13,'2013-02-13 17:03:30',0,9,8),(27,60,'2013-02-13 17:03:49',0,12,8),(28,60,'2013-02-17 17:04:56',0,36,9),(29,6,'2013-02-17 17:05:10',0,32,9),(30,45,'2013-02-16 17:05:21',0,35,9),(31,15,'2013-02-16 17:06:02',0,34,10),(32,10,'2013-02-16 17:06:45',0,33,10),(33,7,'2013-02-16 17:06:58',0,7,10),(34,90,'2013-02-19 17:07:09',0,45,10),(35,90,'2013-02-19 17:09:47',0,47,11),(36,30,'2013-02-19 17:10:08',0,17,11),(37,8,'2013-02-19 17:10:16',0,14,11),(38,60,'2013-02-11 17:10:56',0,48,12),(39,10,'2013-02-11 17:11:01',0,15,12),(40,20,'2013-02-11 17:11:59',0,22,13),(41,60,'2013-02-13 17:12:06',0,24,13),(42,60,'2013-02-13 17:12:42',0,24,14),(43,30,'2013-02-13 17:12:52',0,23,14),(44,20,'2013-02-13 17:12:59',0,19,14),(45,7,'2013-02-15 17:13:06',0,20,14),(46,3,'2013-02-15 17:15:21',0,50,2),(47,6,'2013-02-15 17:15:28',0,26,2),(48,30,'2013-02-15 17:16:28',0,41,2),(49,30,'2013-02-15 17:16:41',0,37,2),(50,3,'2013-02-16 17:17:14',0,50,4),(51,3,'2013-02-16 17:17:35',0,50,3),(52,60,'2013-02-17 17:30:33',0,18,14),(53,15,'2013-02-17 17:30:40',0,49,14);
/*!40000 ALTER TABLE `workout` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-02-17 17:32:27
