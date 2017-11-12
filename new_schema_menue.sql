-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: new_schema
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `menue`
--

DROP TABLE IF EXISTS `menue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menue` (
  `idmenue` int(11) NOT NULL,
  `id_restuarants` int(11) unsigned NOT NULL,
  `food` varchar(45) DEFAULT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`idmenue`),
  KEY `id_restaurants_idx` (`id_restuarants`),
  CONSTRAINT `id_restaurants` FOREIGN KEY (`id_restuarants`) REFERENCES `restaurants` (`id_restaurants`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menue`
--

LOCK TABLES `menue` WRITE;
/*!40000 ALTER TABLE `menue` DISABLE KEYS */;
INSERT INTO `menue` VALUES (1,1,'پیتزا پنجره ای',0),(2,1,'پیتزا آروستو',0),(3,1,'سیب زمینی',1),(4,1,'سالاد سزار',1),(5,1,'نوشابه قوطی',2),(6,1,'دوغ کوچک',2),(7,2,'پیتزا پپرونی',0),(8,2,'ساندویچ گریل',0),(9,2,'سالاد فصل',1),(10,2,'قارچ سوخاری',1),(11,2,'آب معدنی',2),(12,2,'نوشابه',2),(13,3,'پیتزا مخلوط',0),(14,3,'پیتزا مخصوص',0),(15,3,'سالاد سبز',1),(16,3,'ماست و خیار',1),(17,3,'آب پرتغال',2),(18,3,'موهیتو',2),(19,3,'آب انار',2);
/*!40000 ALTER TABLE `menue` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-10  0:57:20
