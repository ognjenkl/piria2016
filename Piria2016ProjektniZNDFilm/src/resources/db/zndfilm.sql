-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema zndfilm
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `zndfilm` ;

-- -----------------------------------------------------
-- Schema zndfilm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `zndfilm` DEFAULT CHARACTER SET utf8 ;
USE `zndfilm` ;

-- -----------------------------------------------------
-- Table `zndfilm`.`actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`actors` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`events` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `event_announcement` DATETIME NOT NULL,
  `event_maintained` DATETIME NOT NULL,
  `name` VARCHAR(256) NOT NULL,
  `location` VARCHAR(128) NOT NULL,
  `active` INT(11) NULL DEFAULT '0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`gallery_pictures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`gallery_pictures` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NULL DEFAULT NULL,
  `location` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`genres` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`movies` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `release_date` DATETIME NULL DEFAULT NULL,
  `storyline` VARCHAR(10000) NULL DEFAULT NULL,
  `genres` VARCHAR(45) NULL DEFAULT NULL,
  `trailer_location` VARCHAR(2048) NULL DEFAULT NULL,
  `runtime_minutes` INT(11) NULL DEFAULT NULL,
  `rate` DOUBLE NULL DEFAULT NULL,
  `movie_location` VARCHAR(2048) NULL DEFAULT NULL,
  `moviescol` VARCHAR(45) NULL DEFAULT NULL,
  `added_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `active` INT(11) NULL DEFAULT '1',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `social_no` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `privilege` INT(11) NULL DEFAULT NULL,
  `picture` VARCHAR(256) NULL DEFAULT NULL,
  `active` TINYINT(1) NULL DEFAULT '0',
  `editable` TINYINT(1) NULL DEFAULT '0',
  `registered` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8
COMMENT = '		';


-- -----------------------------------------------------
-- Table `zndfilm`.`users_has_movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`users_has_movies` (
  `users_id` INT(11) NOT NULL,
  `movies_id` INT(11) NOT NULL,
  `favorite` INT(11) NULL DEFAULT NULL,
  `rate` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`users_id`, `movies_id`),
  INDEX `fk_users_has_movies_users_idx` (`users_id` ASC),
  INDEX `fk_users_has_movies_movies1_idx` (`movies_id` ASC),
  CONSTRAINT `fk_users_has_movies_movies1`
    FOREIGN KEY (`movies_id`)
    REFERENCES `zndfilm`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_movies_users`
    FOREIGN KEY (`users_id`)
    REFERENCES `zndfilm`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`users_has_movies_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`users_has_movies_comments` (
  `comment_id` INT(11) NOT NULL AUTO_INCREMENT,
  `users_id` INT(11) NOT NULL,
  `movies_id` INT(11) NOT NULL,
  `comment` VARCHAR(4096) NULL DEFAULT NULL,
  `coomment_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `active` INT(11) NULL DEFAULT '1',
  PRIMARY KEY (`comment_id`, `users_id`, `movies_id`),
  INDEX `fk_users_has_movies1_users2_idx` (`users_id` ASC),
  INDEX `fk_users_has_movies1_movies2_idx` (`movies_id` ASC),
  CONSTRAINT `fk_users_has_movies1_movies2`
    FOREIGN KEY (`movies_id`)
    REFERENCES `zndfilm`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_movies1_users2`
    FOREIGN KEY (`users_id`)
    REFERENCES `zndfilm`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`users_has_movies_lists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`users_has_movies_lists` (
  `list_id` INT(11) NOT NULL AUTO_INCREMENT,
  `users_id` INT(11) NOT NULL,
  `movies_id` INT(11) NOT NULL,
  `list_name` VARCHAR(128) NULL DEFAULT NULL,
  PRIMARY KEY (`list_id`, `users_id`, `movies_id`),
  INDEX `fk_users_has_movies1_users1_idx` (`users_id` ASC),
  INDEX `fk_users_has_movies1_movies1_idx` (`movies_id` ASC),
  CONSTRAINT `fk_users_has_movies1_movies1`
    FOREIGN KEY (`movies_id`)
    REFERENCES `zndfilm`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_movies1_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `zndfilm`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`movies_has_genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`movies_has_genres` (
  `movies_id` INT(11) NOT NULL,
  `genres_id` INT(11) NOT NULL,
  PRIMARY KEY (`movies_id`, `genres_id`),
  INDEX `fk_movies_has_genres_genres1_idx` (`genres_id` ASC),
  INDEX `fk_movies_has_genres_movies1_idx` (`movies_id` ASC),
  CONSTRAINT `fk_movies_has_genres_movies1`
    FOREIGN KEY (`movies_id`)
    REFERENCES `zndfilm`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_genres_genres1`
    FOREIGN KEY (`genres_id`)
    REFERENCES `zndfilm`.`genres` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zndfilm`.`movies_has_actors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zndfilm`.`movies_has_actors` (
  `movies_id` INT(11) NOT NULL,
  `actors_id` INT(11) NOT NULL,
  PRIMARY KEY (`movies_id`, `actors_id`),
  INDEX `fk_movies_has_actors_actors1_idx` (`actors_id` ASC),
  INDEX `fk_movies_has_actors_movies1_idx` (`movies_id` ASC),
  CONSTRAINT `fk_movies_has_actors_movies1`
    FOREIGN KEY (`movies_id`)
    REFERENCES `zndfilm`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_actors_actors1`
    FOREIGN KEY (`actors_id`)
    REFERENCES `zndfilm`.`actors` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
