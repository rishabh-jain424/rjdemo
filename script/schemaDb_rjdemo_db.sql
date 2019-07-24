--
-- Database: `rjdemo_db`
--

CREATE DATABASE IF NOT EXISTS `rjdemo_db`;
USE `rjdemo_db`;


-- ENTITIES

--
-- Struttura della tabella `user`
--

CREATE TABLE IF NOT EXISTS `user` (
	`password` varchar(130)  NOT NULL,
	`username` varchar(130)  NOT NULL,
	
	-- RELAZIONI

	`_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT 

);


-- Security

INSERT INTO `rjdemo_db`.`user` (`username`, `password`, `_id`) VALUES ('admin', '62f264d7ad826f02a8af714c0a54b197935b717656b80461686d450f7b3abde4c553541515de2052b9af70f710f0cd8a1a2d3f4d60aa72608d71a63a9a93c0f5', 1);

CREATE TABLE IF NOT EXISTS `roles` (
	`role` varchar(30) ,
	
	-- RELAZIONI

	`_user` int(11)  NOT NULL REFERENCES user(_id),
	`_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT 

);
INSERT INTO `rjdemo_db`.`roles` (`role`, `_user`, `_id`) VALUES ('ADMIN', '1', 1);





--
-- Struttura della tabella `ssnotes`
--

CREATE TABLE IF NOT EXISTS `ssnotes` (
	`Author` varchar(130) ,
	`CoverImage` varchar(30) ,
	`DatePublished` date ,
	`Description` varchar(130) ,
	`title` varchar(130)  NOT NULL,
	
	-- RELAZIONI
	`login` int(11)  REFERENCES user(_id),

	`_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT 

);






