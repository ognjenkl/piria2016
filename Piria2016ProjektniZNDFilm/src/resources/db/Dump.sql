CREATE DATABASE  IF NOT EXISTS `zndfilm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `zndfilm`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: zndfilm
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `actors`
--

DROP TABLE IF EXISTS `actors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actors`
--

LOCK TABLES `actors` WRITE;
/*!40000 ALTER TABLE `actors` DISABLE KEYS */;
INSERT INTO `actors` VALUES (57,' Al Pacino'),(60,' Al Pacino James Caan'),(58,' James Caan'),(27,'a'),(49,'ACtor 1'),(50,'Actor 2'),(51,'Actor 3'),(52,'Actor 4'),(61,'Al Pacino'),(18,'Aleksandar Berček'),(73,'Andy Garcia'),(17,'Bata Stojković'),(21,'Boro Stjepanović'),(77,'Bridget Fonda'),(85,'Burgess Meredith'),(83,'Burt Young'),(84,'Carl Weathers'),(12,'Carrie-Anne Moss'),(6,'Chad Lindberg'),(67,'Diane Keaton'),(81,'Donal Donnelly'),(16,'Dragan Nikolić'),(74,'Eli Wallach'),(80,'Franc D\'Ambrosio'),(72,'G.D. Spradlin'),(76,'George Hamilton'),(55,'Halid Muslimović'),(13,'Hugo Weaving'),(62,'James Caan'),(75,'Joe Mantegna'),(14,'Joe Pantoliano'),(68,'John Cazale'),(7,'Johnny Strong'),(4,'Jordana Brewster'),(10,'Keanu Reeves'),(11,'Laurence Fishburne'),(70,'Lee Strasberg'),(56,'Marlon Brando'),(59,'Marlon Brando Al Pacino'),(9,'Matt Schulze'),(19,'Mića Tomić'),(71,'Michael V. Gazzo'),(3,'Michelle Rodriguez'),(23,'Neda Arnerić'),(2,'Paul Walker'),(15,'Pavle Vujisić'),(79,'Raf Vallone'),(63,'Richard S. Castellano'),(5,'Rick Yune'),(66,'Robert De Niro'),(64,'Robert Duvall'),(54,'Sia'),(22,'Slavko Štimac'),(78,'Sofia Coppola'),(65,'Sterling Hayden'),(82,'Sylvester Stallone'),(69,'Talia Shire'),(20,'Taško Načić'),(8,'Ted Levine'),(1,'Vin Diesel');
/*!40000 ALTER TABLE `actors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_announcement` datetime NOT NULL,
  `event_maintained` datetime NOT NULL,
  `name` varchar(256) NOT NULL,
  `location` varchar(128) NOT NULL,
  `active` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery_pictures`
--

DROP TABLE IF EXISTS `gallery_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery_pictures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `location` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery_pictures`
--

LOCK TABLES `gallery_pictures` WRITE;
/*!40000 ALTER TABLE `gallery_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `gallery_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genres` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (1,'Action'),(2,'Drama'),(3,'Horror'),(4,'Crime'),(5,'Sci-Fi'),(6,'Comedy'),(7,'Sport');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `release_date` datetime DEFAULT NULL,
  `storyline` varchar(10000) DEFAULT NULL,
  `trailer_location` varchar(2048) DEFAULT NULL,
  `runtime_minutes` int(11) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `movie_location_type` tinyint(4) DEFAULT NULL,
  `movie_location` varchar(2048) DEFAULT NULL,
  `added_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `active` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (18,'The Fast and the Furious','2001-06-22 00:00:00','Los Angeles street racer Dominic Toretto falls under the suspicion of the LAPD as a string of high-speed electronics truck robberies rocks the area. Brian O\'Connor, an officer of the LAPD, joins the ranks of Toretto\'s highly skilled racing crew undercover to convict Toretto. However, O\'Connor finds himself both enamored with this new world and in love with Toretto\'s sister, Mia. As a rival racing crew gains strength, O\'Connor must decide where his loyalties really lie.','https://www.youtube.com/watch?v=ZsJz2TJAPjw',106,NULL,NULL,NULL,'2016-12-28 22:26:52',1),(19,'The Matrix','1999-03-31 00:00:00','Thomas A. Anderson is a man living two lives. By day he is an average computer programmer and by night a hacker known as Neo. Neo has always questioned his reality, but the truth is far beyond his imagination. Neo finds himself targeted by the police when he is contacted by Morpheus, a legendary computer hacker branded a terrorist by the government. Morpheus awakens Neo to the real world, a ravaged wasteland where most of humanity have been captured by a race of machines that live off of the humans\' body heat and electrochemical energy and who imprison their minds within an artificial reality known as the Matrix. As a rebel against the machines, Neo must return to the Matrix and confront the agents: super-powerful computer programs devoted to snuffing out Neo and the entire human rebellion.','https://www.youtube.com/watch?v=m8e-FF8MsqU',136,NULL,NULL,NULL,'2016-12-28 22:32:28',1),(21,'Ko to tamo peva','1980-01-01 00:00:00','On April 5, 1941, a date Serbs will recognize, men on a country road board Krstic\'s bus for Belgrade: two Gypsies who occasionally sing about misery, an aging war vet, a Nazi sympathizer, a dapper singer, a consumptive, and a man with a shotgun. Krstic is a world-weary cynic, out for a buck; the driver is his son, the simple, cheerful Misko. En route they pick up a priest and young newlyweds going to the seaside. Along the way, mis-adventure strikes: a flat tire, a rickety bridge, a farmer who\'s plowed the road, a funeral, two feuding families, an army detail, and a lost wallet slow the bus and expose rifts among the travelers. On April 6, amid rumors of war, they reach Belgrade...','',83,NULL,2,'https://www.youtube.com/watch?v=ZwozSLas8DM','2016-12-29 00:20:15',1),(27,'sss',NULL,'','',NULL,NULL,1,'Sia - Cheap Thrills (Lyric Video) ft. Sean Paul 360p.mp4','2017-01-03 13:53:11',1),(28,'Halid Muslimovic - Stoj, jarane - (Audio 1983) HD',NULL,'','',NULL,NULL,2,'https://www.youtube.com/watch?v=xxgQ6pVvDIQ','2017-01-03 13:58:12',1),(29,'The Godfather','1972-03-24 00:00:00','When the aging head of a famous crime family decides to transfer his position to one of his subalterns, a series of unfortunate events start happening to the family, and a war begins between all the well-known families leading to insolence, deportation, murder and revenge, and ends with the favorable successor being finally chosen. ','https://www.youtube.com/watch?v=sY1S34973zA',175,NULL,2,'https://www.youtube.com/watch?v=hO3jACt8J7Y','2017-01-03 22:03:21',1),(30,'The Godfather: Part II','1974-12-20 00:00:00','The continuing saga of the Corleone crime family tells the story of a young Vito Corleone growing up in Sicily and in 1910s New York; and follows Michael Corleone in the 1950s as he attempts to expand the family business into Las Vegas, Hollywood and Cuba.','https://www.youtube.com/watch?v=qJr92K_hKl0',202,NULL,2,'https://www.youtube.com/watch?v=fiiazlRSPrE','2017-01-04 07:22:22',1),(31,'The Godfather: Part III','1990-12-25 00:00:00','In the final instalment of the Godfather Trilogy, an aging Don Michael Corleone seeks to legitimize his crime family\'s interests and remove himself from the violent underworld but is kept back by the ambitions of the young. While he attempts to link the Corleone\'s finances with the Vatican, Michael must deal with the machinations of a hungrier gangster seeking to upset the existing Mafioso order and a young protoge\'s love affair with his daughter.','https://www.youtube.com/watch?v=kCAcWDDPdNY',162,NULL,2,'https://www.youtube.com/watch?v=lxVo9Lf63rI','2017-01-04 07:32:40',1),(33,'Rocky','1976-12-03 00:00:00','Rocky Balboa is a struggling boxer trying to make the big time, working as a debt collector for a pittance. When heavyweight champion Apollo Creed visits Philadelphia, his managers want to set up an exhibition match between Creed and a struggling boxer, touting the fight as a chance for a \"nobody\" to become a \"somebody\". The match is supposed to be easily won by Creed, but someone forgot to tell Rocky, who sees this as his only shot at the big time.','https://www.youtube.com/watch?v=3VUblDwa648',120,NULL,2,'https://www.youtube.com/watch?v=Wxh5QGSQ5vI','2017-01-04 13:49:21',1),(34,'Rocky II','1979-06-15 00:00:00','Rocky Balboa is enjoying life. He has a lovely wife, Adrian, had a successful fight with Apollo Creed and is able to enjoy the money he earned from the fight and a new endorsement deal. Unfortunately, Rocky becomes embarrassed when failing to complete an advert and ends up working in a meat packing company. He believes that he will no longer have a career as a boxer. Apollo wants to rematch with Rocky to prove all his critics wrong that he can beat Rocky. Can Rocky once again have a successful fight?','https://www.youtube.com/watch?v=U2J_n8_x11g',119,NULL,2,'','2017-01-04 13:55:50',1),(35,'Rocky III','1982-05-28 00:00:00','Three years and 10 successful title defenses after beating Apollo Creed, with whom he has become great friends, a now wealthy Rocky Balboa is considering retirement. Fame and complacency soon cause Balboa to lose his title to Clubber Lang, who inadvertently causes the death of Rocky\'s trainer Mickey. Rocky sinks into a depression, and Apollo decides to train Rocky for a rematch against Lang so Rocky can try to win the title back.','https://www.youtube.com/watch?v=0fHDMTQUClE',99,NULL,2,'https://www.youtube.com/watch?v=9SJnhSQOjIQ','2017-01-04 13:58:41',1);
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies_has_actors`
--

DROP TABLE IF EXISTS `movies_has_actors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies_has_actors` (
  `movies_id` int(11) NOT NULL,
  `actors_id` int(11) NOT NULL,
  PRIMARY KEY (`movies_id`,`actors_id`),
  KEY `fk_movies_has_actors_actors1_idx` (`actors_id`),
  KEY `fk_movies_has_actors_movies1_idx` (`movies_id`),
  CONSTRAINT `fk_movies_has_actors_actors1` FOREIGN KEY (`actors_id`) REFERENCES `actors` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_actors_movies1` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies_has_actors`
--

LOCK TABLES `movies_has_actors` WRITE;
/*!40000 ALTER TABLE `movies_has_actors` DISABLE KEYS */;
INSERT INTO `movies_has_actors` VALUES (18,1),(18,2),(18,3),(18,4),(18,5),(18,6),(18,7),(18,8),(18,9),(19,10),(19,11),(19,12),(19,13),(19,14),(21,15),(21,16),(21,17),(21,18),(21,19),(21,20),(21,21),(21,22),(21,23),(27,54),(28,55),(29,56),(29,57),(29,58),(29,59),(29,60),(29,61),(30,61),(31,61),(29,62),(29,63),(29,64),(30,64),(29,65),(30,66),(30,67),(31,67),(30,68),(30,69),(31,69),(33,69),(34,69),(35,69),(30,70),(30,71),(30,72),(31,73),(31,74),(31,75),(31,76),(31,77),(31,78),(31,79),(31,80),(31,81),(33,82),(34,82),(35,82),(33,83),(34,83),(35,83),(33,84),(34,84),(35,84),(35,85);
/*!40000 ALTER TABLE `movies_has_actors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies_has_genres`
--

DROP TABLE IF EXISTS `movies_has_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies_has_genres` (
  `movies_id` int(11) NOT NULL,
  `genres_id` int(11) NOT NULL,
  PRIMARY KEY (`movies_id`,`genres_id`),
  KEY `fk_movies_has_genres_genres1_idx` (`genres_id`),
  KEY `fk_movies_has_genres_movies1_idx` (`movies_id`),
  CONSTRAINT `fk_movies_has_genres_genres1` FOREIGN KEY (`genres_id`) REFERENCES `genres` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_genres_movies1` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies_has_genres`
--

LOCK TABLES `movies_has_genres` WRITE;
/*!40000 ALTER TABLE `movies_has_genres` DISABLE KEYS */;
INSERT INTO `movies_has_genres` VALUES (18,1),(19,1),(21,2),(29,2),(30,2),(31,2),(33,2),(34,2),(35,2),(18,4),(29,4),(30,4),(31,4),(19,5),(21,6),(33,7),(34,7),(35,7);
/*!40000 ALTER TABLE `movies_has_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `social_no` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `privilege` int(11) DEFAULT NULL,
  `picture` varchar(256) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '0',
  `editable` tinyint(1) DEFAULT '0',
  `registered` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (16,'a','a','a','a','a','a',1,'a',1,0,'2016-11-04 13:11:32'),(17,'s','s','s','s','s','s',2,'s',1,0,'2016-11-04 13:11:41'),(19,'u','u','u','u','u','u',3,'u',1,0,'2016-11-04 13:19:36'),(20,'e','e','ee','ee','ee','ee',3,'ee',1,0,'2016-11-04 13:19:45');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_has_movies`
--

DROP TABLE IF EXISTS `users_has_movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_has_movies` (
  `users_id` int(11) NOT NULL,
  `movies_id` int(11) NOT NULL,
  `favorite` int(11) DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  PRIMARY KEY (`users_id`,`movies_id`),
  KEY `fk_users_has_movies_users_idx` (`users_id`),
  KEY `fk_users_has_movies_movies1_idx` (`movies_id`),
  CONSTRAINT `fk_users_has_movies_movies1` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_movies_users` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_has_movies`
--

LOCK TABLES `users_has_movies` WRITE;
/*!40000 ALTER TABLE `users_has_movies` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_has_movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_has_movies_comments`
--

DROP TABLE IF EXISTS `users_has_movies_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_has_movies_comments` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `movies_id` int(11) NOT NULL,
  `comment` varchar(4096) DEFAULT NULL,
  `coomment_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `active` int(11) DEFAULT '1',
  PRIMARY KEY (`comment_id`,`users_id`,`movies_id`),
  KEY `fk_users_has_movies1_users2_idx` (`users_id`),
  KEY `fk_users_has_movies1_movies2_idx` (`movies_id`),
  CONSTRAINT `fk_users_has_movies1_movies2` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_movies1_users2` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_has_movies_comments`
--

LOCK TABLES `users_has_movies_comments` WRITE;
/*!40000 ALTER TABLE `users_has_movies_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_has_movies_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_has_movies_lists`
--

DROP TABLE IF EXISTS `users_has_movies_lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_has_movies_lists` (
  `list_id` int(11) NOT NULL AUTO_INCREMENT,
  `users_id` int(11) NOT NULL,
  `movies_id` int(11) NOT NULL,
  `list_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`list_id`,`users_id`,`movies_id`),
  KEY `fk_users_has_movies1_users1_idx` (`users_id`),
  KEY `fk_users_has_movies1_movies1_idx` (`movies_id`),
  CONSTRAINT `fk_users_has_movies1_movies1` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_movies1_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_has_movies_lists`
--

LOCK TABLES `users_has_movies_lists` WRITE;
/*!40000 ALTER TABLE `users_has_movies_lists` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_has_movies_lists` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-04 15:00:03
