-- Initialize the MySQL Docker container on first run.
-- See: docker-compose.yml
CREATE DATABASE IF NOT EXISTS skeleton DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

CREATE USER IF NOT EXISTS 'skeluser'@'%' IDENTIFIED BY 'skelpass';
GRANT ALL ON skeleton.* TO 'skeluser'@'%';
