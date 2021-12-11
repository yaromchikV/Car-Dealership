-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: dealership
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `PASSWORD` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`),
  CONSTRAINT `accounts_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `employees` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'evlampochka','evlampochka'),(2,'mashamasha','2j74544jb463z6637adz59do62ck374z'),(3,'pazhilay','868m628lkx22bxg2z1548oake970z78f'),(4,'yaromchikv','7844d964cz3a4vf3c5kw39l843437mun'),(5,'username','26ak49436815v4893g4d7d5c054lz763');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `STYLE_ID` int DEFAULT NULL,
  `MAKE` varchar(30) DEFAULT NULL,
  `MODEL` varchar(30) DEFAULT NULL,
  `YEAR` int DEFAULT NULL,
  `PRICE` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `STYLE_ID` (`STYLE_ID`),
  CONSTRAINT `cars_ibfk_1` FOREIGN KEY (`STYLE_ID`) REFERENCES `styles` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,7,'Tesla','Model X',2020,153000),(2,1,'BMW','M5',2021,103000),(3,3,'Audi','R8',2020,143000),(4,3,'Mercedes','EQS',2021,102310),(5,10,'BMW','Z4',2021,49900),(6,3,'Audi','A8',2020,72000);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `carsview`
--

DROP TABLE IF EXISTS `carsview`;
/*!50001 DROP VIEW IF EXISTS `carsview`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `carsview` AS SELECT 
 1 AS `ID`,
 1 AS `MAKE`,
 1 AS `MODEL`,
 1 AS `STYLE`,
 1 AS `YEAR`,
 1 AS `PRICE`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `SURNAME` varchar(30) DEFAULT NULL,
  `NAME` varchar(30) DEFAULT NULL,
  `MIDDLE_NAME` varchar(30) DEFAULT NULL,
  `DATE_OF_BIRTH` date DEFAULT NULL,
  `PHONE_NUMBER` varchar(30) DEFAULT NULL,
  `EMAIL` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Иванов','Иван','Иванович','1990-11-20','+375291234567','ivanov@mail.ru'),(2,'Сергеев','Сергей','Сергеевич','1956-04-01','+375254758696','sergeev@gmail.com'),(3,'Петров','Петр','Петрович','1989-12-26','80333654679','mailmailmail@gmail.com'),(4,'Дмитриев','Дмитрий','Дмитриевич','2001-11-29','80295855544','mailrowitw@yandex.ru');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `customersview`
--

DROP TABLE IF EXISTS `customersview`;
/*!50001 DROP VIEW IF EXISTS `customersview`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `customersview` AS SELECT 
 1 AS `ID`,
 1 AS `SURNAME`,
 1 AS `NAME`,
 1 AS `MIDDLE_NAME`,
 1 AS `DATE_OF_BIRTH`,
 1 AS `PHONE_NUMBER`,
 1 AS `EMAIL`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `SURNAME` varchar(30) DEFAULT NULL,
  `NAME` varchar(30) DEFAULT NULL,
  `MIDDLE_NAME` varchar(30) DEFAULT NULL,
  `DATE_OF_BIRTH` date DEFAULT NULL,
  `PHONE_NUMBER` varchar(30) DEFAULT NULL,
  `POSITION_ID` int DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `POSITION_ID` (`POSITION_ID`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`POSITION_ID`) REFERENCES `positions` (`ID`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Неверова','Анастасия','Павловна','2001-12-26','80293534567',2,'2021-07-13'),(2,'Солтан','Мария','Вячеславовна','2001-12-18','8025398844',3,'2021-01-02'),(3,'Асташенок','Александр','Викторович','2001-10-28','80294758393',3,'2019-12-12'),(4,'Яромчик','Владислав','Александрович','2002-07-03','80298888844',4,'2018-12-05'),(5,'Сергеев','Петр','Александрович','1995-12-13','80232324244',1,'2021-12-05');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `employeesview`
--

DROP TABLE IF EXISTS `employeesview`;
/*!50001 DROP VIEW IF EXISTS `employeesview`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `employeesview` AS SELECT 
 1 AS `ID`,
 1 AS `SURNAME`,
 1 AS `NAME`,
 1 AS `MIDDLE_NAME`,
 1 AS `DATE_OF_BIRTH`,
 1 AS `PHONE_NUMBER`,
 1 AS `POSITION`,
 1 AS `SALARY`,
 1 AS `START_DATE`,
 1 AS `USERNAME`,
 1 AS `PASSWORD`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `DATE_TIME` datetime DEFAULT NULL,
  `CUSTOMER_ID` int DEFAULT NULL,
  `CAR_ID` int DEFAULT NULL,
  `EMPLOYEE_ID` int DEFAULT NULL,
  `IS_COMPLETED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CUSTOMER_ID` (`CUSTOMER_ID`),
  KEY `CAR_ID` (`CAR_ID`),
  KEY `EMPLOYEE_ID` (`EMPLOYEE_ID`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customers` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`CAR_ID`) REFERENCES `cars` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employees` (`ID`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2021-12-06 18:30:13',4,1,2,0),(2,'2021-12-06 18:30:18',2,3,1,0),(3,'2021-12-06 18:30:24',4,2,3,0),(4,'2021-12-06 18:30:44',1,5,2,1),(5,'2021-12-06 18:31:04',3,4,3,0);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `ordersview`
--

DROP TABLE IF EXISTS `ordersview`;
/*!50001 DROP VIEW IF EXISTS `ordersview`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ordersview` AS SELECT 
 1 AS `ID`,
 1 AS `DATE_TIME`,
 1 AS `CUSTOMER_ID`,
 1 AS `CUSTOMER_FULLNAME`,
 1 AS `CAR_ID`,
 1 AS `CAR_NAME`,
 1 AS `EMPLOYEE_ID`,
 1 AS `EMPLOYEE_FULLNAME`,
 1 AS `STATUS`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `positions`
--

DROP TABLE IF EXISTS `positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `positions` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `SALARY` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `positions`
--

LOCK TABLES `positions` WRITE;
/*!40000 ALTER TABLE `positions` DISABLE KEYS */;
INSERT INTO `positions` VALUES (1,'Ассистент',1400),(2,'Менеджер',2000),(3,'Старший менеджер',2500),(4,'Руководитель отдела',6300);
/*!40000 ALTER TABLE `positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `positionsview`
--

DROP TABLE IF EXISTS `positionsview`;
/*!50001 DROP VIEW IF EXISTS `positionsview`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `positionsview` AS SELECT 
 1 AS `ID`,
 1 AS `NAME`,
 1 AS `SALARY`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `styles`
--

DROP TABLE IF EXISTS `styles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `styles` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `styles`
--

LOCK TABLES `styles` WRITE;
/*!40000 ALTER TABLE `styles` DISABLE KEYS */;
INSERT INTO `styles` VALUES (6,'Внедорожник'),(9,'Кабриолет'),(7,'Кроссовер'),(3,'Купе'),(11,'Лимузин'),(5,'Минивэн'),(8,'Пикап'),(10,'Родстер'),(1,'Седан'),(4,'Универсал'),(2,'Хэтчбэк');
/*!40000 ALTER TABLE `styles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `stylesview`
--

DROP TABLE IF EXISTS `stylesview`;
/*!50001 DROP VIEW IF EXISTS `stylesview`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `stylesview` AS SELECT 
 1 AS `ID`,
 1 AS `NAME`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `carsview`
--

/*!50001 DROP VIEW IF EXISTS `carsview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `carsview` AS select `cars`.`ID` AS `ID`,`cars`.`MAKE` AS `MAKE`,`cars`.`MODEL` AS `MODEL`,`styles`.`name` AS `STYLE`,`cars`.`YEAR` AS `YEAR`,`cars`.`PRICE` AS `PRICE` from (`cars` join `styles` on((`cars`.`STYLE_ID` = `styles`.`ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `customersview`
--

/*!50001 DROP VIEW IF EXISTS `customersview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `customersview` AS select `customers`.`ID` AS `ID`,`customers`.`SURNAME` AS `SURNAME`,`customers`.`NAME` AS `NAME`,`customers`.`MIDDLE_NAME` AS `MIDDLE_NAME`,date_format(`customers`.`DATE_OF_BIRTH`,'%d.%m.%Y') AS `DATE_OF_BIRTH`,`customers`.`PHONE_NUMBER` AS `PHONE_NUMBER`,`customers`.`EMAIL` AS `EMAIL` from `customers` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `employeesview`
--

/*!50001 DROP VIEW IF EXISTS `employeesview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `employeesview` AS select `employees`.`ID` AS `ID`,`employees`.`SURNAME` AS `SURNAME`,`employees`.`NAME` AS `NAME`,`employees`.`MIDDLE_NAME` AS `MIDDLE_NAME`,date_format(`employees`.`DATE_OF_BIRTH`,'%d.%m.%Y') AS `DATE_OF_BIRTH`,`employees`.`PHONE_NUMBER` AS `PHONE_NUMBER`,`positions`.`name` AS `POSITION`,`positions`.`SALARY` AS `SALARY`,date_format(`employees`.`START_DATE`,'%d.%m.%Y') AS `START_DATE`,`accounts`.`username` AS `USERNAME`,`accounts`.`PASSWORD` AS `PASSWORD` from ((`employees` join `positions` on((`employees`.`POSITION_ID` = `positions`.`ID`))) join `accounts` on((`employees`.`ID` = `accounts`.`ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ordersview`
--

/*!50001 DROP VIEW IF EXISTS `ordersview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ordersview` AS select `orders`.`ID` AS `ID`,date_format(`orders`.`DATE_TIME`,'%d.%m.%Y %H:%i:%S') AS `DATE_TIME`,`orders`.`CUSTOMER_ID` AS `CUSTOMER_ID`,concat(`customers`.`SURNAME`,' ',substr(`customers`.`NAME`,1,1),'. ',substr(`customers`.`MIDDLE_NAME`,1,1),'.') AS `CUSTOMER_FULLNAME`,`orders`.`CAR_ID` AS `CAR_ID`,concat_ws(' ',`cars`.`MAKE`,`cars`.`MODEL`) AS `CAR_NAME`,`orders`.`EMPLOYEE_ID` AS `EMPLOYEE_ID`,concat(`employees`.`SURNAME`,' ',substr(`employees`.`NAME`,1,1),'. ',substr(`employees`.`MIDDLE_NAME`,1,1),'.') AS `EMPLOYEE_FULLNAME`,if((`orders`.`IS_COMPLETED` = 1),'Завершён','В обработке') AS `STATUS` from (((`orders` join `customers` on((`orders`.`CUSTOMER_ID` = `customers`.`ID`))) join `cars` on((`orders`.`CAR_ID` = `cars`.`ID`))) join `employees` on((`orders`.`EMPLOYEE_ID` = `employees`.`ID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `positionsview`
--

/*!50001 DROP VIEW IF EXISTS `positionsview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `positionsview` AS select `positions`.`ID` AS `ID`,`positions`.`name` AS `NAME`,`positions`.`SALARY` AS `SALARY` from `positions` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `stylesview`
--

/*!50001 DROP VIEW IF EXISTS `stylesview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `stylesview` AS select `styles`.`ID` AS `ID`,`styles`.`name` AS `NAME` from `styles` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-12  1:04:58
