DROP DATABASE  IF EXISTS `company_management_security`;

CREATE DATABASE  IF NOT EXISTS `company_management_security`;
USE `company_management_security`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(150) NOT NULL,
  `password` char(68) NOT NULL,
  `email`  varchar(150) ,
  `enabled` bit(1) NOT NULL,
  `locked` bit(1) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY (`email`)
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `users` 
VALUES 
('john','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','',1),
('mary','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','',1),
('susan','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','',1);

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `auth_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(50) NOT NULL,
  `username` varchar(150) NOT NULL,
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




INSERT INTO `authorities` 
VALUES 
(1,'ROLE_EMPLOYEE','john'),
(2,'ROLE_EMPLOYEE','mary'),
(3,'ROLE_MANAGER','mary'),
(4,'ROLE_EMPLOYEE','susan'),
(5,'ROLE_ADMIN','susan');


DROP TABLE IF EXISTS `social_users`;
CREATE TABLE `social_users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `email` VARCHAR(200) NULL,
  PRIMARY KEY (`user_id`),
UNIQUE KEY (`name`,`email`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;




DROP TABLE IF EXISTS `token_data`;
CREATE TABLE `token_data` (
  `token_id` bigint(20) NOT NULL,
  `confirm_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `expire_time` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `username` varchar(150) NOT NULL,
  PRIMARY KEY (`token_id`),
  CONSTRAINT `token_data_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

