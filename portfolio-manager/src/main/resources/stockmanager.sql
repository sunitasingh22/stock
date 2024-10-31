-- Host: localhost    Database: stockmanager
--
-- Table structure for table `stocks`
--

CREATE DATABASE stockman;
USE stockmanager;

DROP TABLE IF EXISTS `stocks`;
CREATE TABLE `stocks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `symbol` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;



--
-- Table structure for table `portfolios`
--

DROP TABLE IF EXISTS `portfolios`;
CREATE TABLE `portfolios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `stock_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `added_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `stock_id` (`stock_id`),
  CONSTRAINT `portfolios_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `portfolios_ibfk_2` FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;


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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

INSERT INTO `stocklist` VALUES (1,'AAPL','Apple Inc'),(2,'ABLLL','Abacus Life Inc'),(3,'ACA','Arcosa Inc'),(4,'ADBE','Adobe Inc'),(5,'ADC','Agree Realty Corp'),(6,'ADEA','Adeia Inc'),(7,'ADSK','Autodesk Inc'),(8,'ADTX','Aditxt Inc'),(9,'AEG','Aegon Ltd'),(10,'AEMD','Aethlon Medical Inc'),(11,'AGEN','Agenus Inc'),(12,'BIDU','Baidu Inc'),(13,'BIIB','Biogen Inc'),(14,'BLMZ','BloomZ Inc'),(15,'BLMZ','BloomZ Inc'),(16,'BMRA','Biomerica Inc'),(17,'BPOP','Popular Inc'),(18,'C','Citigroup Inc'),(19,'CSCO','Cisco Systems Inc'),(20,'EBAY','EBay Inc'),(21,'EFOI','Energy Focus Inc'),(22,'ENS','Enersys'),(23,'FIVE','Five Below Inc'),(24,'FIVN','Five9 Inc'),(25,'FOSL','Fossil Group Inc');



