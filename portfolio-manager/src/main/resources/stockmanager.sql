-- Host: localhost    Database: stockmanager
--
--
DROP DATABASE IF EXISTS stockmanager;
CREATE DATABASE stockmanager;

USE stockmanager;


--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `stocklist`;
CREATE TABLE `stocklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `symbol` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

INSERT INTO `stocklist` VALUES (1,'AAPL','Apple Inc'),(2,'ABLLL','Abacus Life Inc'),(3,'ACA','Arcosa Inc'),(4,'ADBE','Adobe Inc'),(5,'ADC','Agree Realty Corp'),(6,'ADEA','Adeia Inc'),(7,'ADSK','Autodesk Inc'),(8,'ADTX','Aditxt Inc'),(9,'AEG','Aegon Ltd'),(10,'AEMD','Aethlon Medical Inc'),(11,'AGEN','Agenus Inc'),(12,'BIDU','Baidu Inc'),(13,'BIIB','Biogen Inc'),(14,'BLMZ','BloomZ Inc'),(15,'BMRA','Biomerica Inc'),(16,'BPOP','Popular Inc'),(17,'C','Citigroup Inc'),(18,'CSCO','Cisco Systems Inc'),(19,'EBAY','EBay Inc'),(20,'EFOI','Energy Focus Inc'),(21,'ENS','Enersys'),(22,'FIVE','Five Below Inc'),(23,'FIVN','Five9 Inc'),(24,'FOSL','Fossil Group Inc');

--
-- Table structure for table `portfolios`
--

DROP TABLE IF EXISTS `portfolios`;
CREATE TABLE `portfolios` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
   `user_id` BIGINT(20) NOT NULL,
   `stock_id` BIGINT(20) NOT NULL,
   `quantity` INT(11) NOT NULL,
   `added_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `user_id` (`user_id`),
   KEY `stock_id` (`stock_id`),
   CONSTRAINT `portfolios_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ,
   CONSTRAINT `portfolios_ibfk_2` FOREIGN KEY (`stock_id`) REFERENCES `stocklist` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP DATABASE IF EXISTS stockmanager_test;
CREATE DATABASE stockmanager_test;
