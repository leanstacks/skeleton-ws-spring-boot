/*
 * MySQL script.
 * Create the database schema for the application.
 */
DROP TABLE IF EXISTS `Greeting`;

CREATE TABLE `Greeting` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `referenceId` varchar(255) NOT NULL,
  `text` varchar(100) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedBy` varchar(100) DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Greeting_ReferenceId` (`referenceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `AccountRole`;
DROP TABLE IF EXISTS `Account`;
DROP TABLE IF EXISTS `Role`;

CREATE TABLE `Account` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `referenceId` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `enabled` bit(1) NOT NULL DEFAULT 1,
  `credentialsexpired` bit(1) NOT NULL DEFAULT 0,
  `expired` bit(1) NOT NULL DEFAULT 0,
  `locked` bit(1) NOT NULL DEFAULT 0,
  `version` int(10) unsigned NOT NULL,
  `createdBy` varchar(100) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedBy` varchar(100) DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Account_ReferenceId` (`referenceId`),
  UNIQUE KEY `UQ_Account_Username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Role` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `code` varchar(50) NOT NULL,
  `label` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_Role_Code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `AccountRole` (
  `accountId` bigint(20) unsigned NOT NULL,
  `roleId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`accountId`, `roleId`),
  UNIQUE KEY `UQ_AccountRole_AccountId_RoleId` (`accountId`, `roleId`),
  KEY `FK_AccountRole_AccountId` (`accountId`),
  KEY `FK_AccountRole_RoleId` (`roleId`),
  CONSTRAINT `FK_AccountRole_AccountId` FOREIGN KEY (`accountId`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK_AccountRole_RoleId` FOREIGN KEY (`roleId`) REFERENCES `Role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;