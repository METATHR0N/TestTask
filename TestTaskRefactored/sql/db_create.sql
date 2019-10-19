DROP DATABASE IF EXISTS aimprosoft;
CREATE DATABASE aimprosoft CHARACTER SET utf8 COLLATE utf8_bin;

USE aimprosoft;

SET GLOBAL time_zone = '+2:00';

CREATE TABLE Departments (
  d_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Name VARCHAR(60) NOT NULL UNIQUE,
  PRIMARY KEY(d_id)
);

CREATE TABLE `employees` (
  `e_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Departments_d_id` int(10) unsigned NOT NULL,
  `mail` varchar(60) NOT NULL UNIQUE,
  `name` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`e_id`),
  KEY `Employees_FKIndex1` (`Departments_d_id`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`Departments_d_id`) REFERENCES `departments` (`d_id`) ON DELETE CASCADE ON UPDATE CASCADE
)