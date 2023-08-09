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
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` int NOT NULL,
  `added_date` datetime(6) DEFAULT NULL,
  `content` varchar(10000) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `title` varchar(150) NOT NULL,
  `category_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg6l1ydp1pwkmyj166teiuov1b` (`category_id`),
  KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
  CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKg6l1ydp1pwkmyj166teiuov1b` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'2023-07-10 04:24:54.183000','Ukrain wants to Join Nato and Russia fears that if Ukrain joins NATO they will try to get back Crimea. Moreover Ukrain signed the Minsk Agreement with Russia, but Ukrain is not following it.','Default.png','What is the reason behind ongoing Russia and Ukrain conflict!',3,2),(2,'2023-07-10 04:39:37.073000','Java has all the Object Oriented features that makes enterprise software development easier with proper structure and maintainabality. Java library ecosystem is huge and so many option is avialbe for developing any kind of software. Also Java Community support is one of the best.','Default.png','Reason behind Java Programming is considered better.',2,3),(3,'2023-07-10 04:41:48.125000','With the Rise of AI and Machine Learning pyhtons usage and popularity is increasing day by day. From Web Deveopment to AI it covers everything that a modern Deveopment team want. Also Its backed by Google, So future might be with Python.','Default.png','Why Python might win in the long run?',2,3),(4,'2023-08-09 20:27:47.244000','Lorem Ipsum Dolor','Default.png','Lorem Ipsum Dolor',3,2),(103,'2023-07-10 05:53:55.636000','Dhalywood represents the current movie industry of Bangladesh former East Bengal. Alongside with West Bengal, Bengali movie Industry has a long legacy. Before the Partition of British India during 1947, Bengali movie industry was one of the best throughout India. It produced greats like Indias first Oscar winner Satyajeet Ray. It also produced great actors like Uttam Kumar, Shuchitra Bannerjee, Shoumittra Mukhopadhay etc. However after the partition the Industry lost its direction.\nNow after 50 years of Bangladesh\'s Independence Dhalywood Industry is trying everything possible to return the industrys tracks to a progressive and thriving future. With so many talednted and new artist there is hope. We only lack Investment, quality writer and new quality halls for the audience',NULL,'The future of Dhalywood movie Industry',1,1),(152,'2023-07-10 06:03:06.505000','Reasons and Beyond. Ukrain wants to Join Nato and Russia fears that if Ukrain joins NATO they will try to get back Crimea. Moreover Ukrain signed the Minsk Agreement with Russia, but Ukrain is not following it.',NULL,'Where the Ukrain war heading!!',3,2),(402,'2023-07-11 01:18:07.888000','Introduction: Politics shapes societies, influences policies, and drives change. In this article, we explore the dynamics of politics, its impact on individuals and communities, and the power of collective action.\nThe Power of Politics: From governance systems to social welfare, politics affects our lives and empowers us to shape our future.\nIdeologies and Governance: Political beliefs guide decision-making and shape societal values. Understanding political systems illuminates their implications and the importance of transparency and citizen participation. Social Movements and Change: Grassroots movements drive positive change, advocating for rights and sustainability. They inspire action and hold governments accountable.\nConclusion: Politics influences our world, and understanding its impact and the potential for change empowers us to shape a better future.','Default.png','Politics Unveiled: Understanding Impact and Change',4,4),(403,'2023-07-11 01:19:37.266000','Film is a captivating medium that has the power to entertain, educate, and inspire audiences around the world. It offers a diverse range of genres, from action-packed adventures to heartwarming romances and thought-provoking dramas. Films reflect and shape our culture, sparking conversations and challenging societal norms. With advancements in technology, filmmakers continue to push creative boundaries, creating visually stunning and immersive experiences. Discover the magic of film and let it transport you to captivating worlds of storytelling.','Default.png','The Magic of Film: Exploring its Impact and Diversity',1,1),(452,'2023-07-15 04:03:36.916000','Anime has huge influence on youth. Japan can spread the Soft power using their Anime series. Three things drive the global politics, Military Power, Economic Power and Soft Power. With Anime series Japan are spreading their soft power through the youths of the world.','https://res.cloudinary.com/de6b8scfh/image/upload/v1691190665/blog_app/ofr21b1wssg2bh17qtyd.jpg','Japan and Its soft politics with the Anime culture',4,4);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-09 20:42:22
