-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: blog_app_apis
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `about` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'I am a Bangladeshi Actor, Politician.','h.alom@dhalywood.com','Hero Alom','$2a$12$r1jQjNQfXgVBS9buMA00AOP0FoG0NETxTDfXT01952sfiuDT4PNzu'),(2,'asaas','vombol@gmail.com','Vombol Das','$2a$12$d8AKPZ2rq3pr1JaCMdGO8ulQCBgZpfRmUhU73n4ftPj1yn4Ri5EkG'),(3,'I am The warriar Julius Ceaser','julius@gmail.com','Ceaser Julius','$2a$12$plqVI7pYZ6LK.gIiE7JH1ujlIsM0X2x5dM90SCRmK6zUFKv10iVDy'),(4,'I am a Bangladeshi Politician, the President of Bangladesh Awamileague party.','shasina@awamileague.com','Sheikh Hasina','$2a$12$.4ync0q4ufzozd2tHOoa4OeMembG.g0jrP/XafxSGqi75mOmz631O'),(5,'I am the admin of this website','dipto@gmail.com','Dipto Paul','$2a$10$PAJMr0eew4LeH.BJeBd.OOJklLeabQ0YkgZ14fApkP72wYHJSEjhq'),(152,'Gangster of Birmingham.','shelby@gmail.com','Thomas Shelby','$2a$10$dC9twS47bWoH8fgVTm68Hub592WsQfA9uQmHY3Mj0qJtE8AxkodfS'),(802,'A professor and Entrprenuer.','dpauldpro@gmail.com','Mr Paul Pro','$2a$10$S2n8CaM0/UoVjKagM66VUOCnMFo33/LeltpyR1I5mLstf1gxPAbM6'),(1002,'I am a Bangladeshi Biker.','abir@gmail.com','Abir Paul','$2a$10$iOc4mMl.4BDU9Dn1ECnQB..JM8QOWzloVCh539RxeEwP3SwWCwdWa'),(1052,'The owner of Rolex Brand','rolex@brand.com','Rolex Kumar','$2a$12$3xTanBmb2lHMPSbB72RReeebUM9pRaztXLWfiwyXOpqK3yxj8qp8y');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-09 20:42:21
