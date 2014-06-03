-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas wygenerowania: 04 Cze 2014, 00:45
-- Wersja serwera: 5.5.31
-- Wersja PHP: 5.4.4-14+deb7u7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `test`
--
CREATE DATABASE `test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `test`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Attraction`
--

CREATE TABLE IF NOT EXISTS `Attraction` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `CRoom`
--

CREATE TABLE IF NOT EXISTS `CRoom` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Zrzut danych tabeli `CRoom`
--

INSERT INTO `CRoom` (`id`) VALUES
(1),
(2),
(3),
(4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `GoldCard`
--

CREATE TABLE IF NOT EXISTS `GoldCard` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `own` int(10) unsigned NOT NULL,
  `disc` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `own` (`own`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Movie`
--

CREATE TABLE IF NOT EXISTS `Movie` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `genere` varchar(30) DEFAULT NULL,
  `rating` varchar(20) DEFAULT NULL,
  `descr` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `Movie`
--

INSERT INTO `Movie` (`id`, `name`, `genere`, `rating`, `descr`) VALUES
(1, 'Star Wars', 'Action', 'adult', 'long long time ago....'),
(2, 'Matrix', 'Action', 'everyone', 'everything that has a beginning, has an end...');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Product`
--

CREATE TABLE IF NOT EXISTS `Product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `typ` int(11) NOT NULL,
  `price` double NOT NULL,
  `pcount` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Zrzut danych tabeli `Product`
--

INSERT INTO `Product` (`id`, `name`, `typ`, `price`, `pcount`) VALUES
(1, 'Prod1', 1, 2, 23),
(2, 'Prod2', 2, 2, 20),
(3, 'Prod1', 1, 2.2, 23),
(4, 'Prod2', 2, 2.4, 20);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Res`
--

CREATE TABLE IF NOT EXISTS `Res` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `imienazwisko` varchar(120) NOT NULL,
  `showid` int(10) unsigned NOT NULL,
  `seat` varchar(100) DEFAULT NULL,
  `checked` tinyint(1) DEFAULT NULL,
  `ok` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `showid` (`showid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Zrzut danych tabeli `Res`
--

INSERT INTO `Res` (`id`, `imienazwisko`, `showid`, `seat`, `checked`, `ok`) VALUES
(3, 'Jakub Palac', 1, '1:1,1:2,1:3', NULL, NULL),
(4, 'Dummy Dummy', 2, '2:3,2:4,2:5,2:6', NULL, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Shows`
--

CREATE TABLE IF NOT EXISTS `Shows` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mov` int(10) unsigned NOT NULL,
  `room` int(10) unsigned NOT NULL,
  `timeid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mov` (`mov`),
  KEY `room` (`room`),
  KEY `timeid` (`timeid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Zrzut danych tabeli `Shows`
--

INSERT INTO `Shows` (`id`, `mov`, `room`, `timeid`) VALUES
(1, 1, 1, 2),
(2, 1, 3, 2),
(3, 2, 2, 2),
(4, 2, 3, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Ticket`
--

CREATE TABLE IF NOT EXISTS `Ticket` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `typ` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `TimeDate`
--

CREATE TABLE IF NOT EXISTS `TimeDate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `thour` int(11) NOT NULL,
  `tminute` int(11) NOT NULL,
  `tday` int(11) NOT NULL,
  `tmonth` int(11) NOT NULL,
  `tyear` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `TimeDate`
--

INSERT INTO `TimeDate` (`id`, `thour`, `tminute`, `tday`, `tmonth`, `tyear`) VALUES
(1, 12, 12, 12, 12, 2012),
(2, 13, 13, 13, 12, 2013);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `password` varchar(40) NOT NULL,
  `utype` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `GoldCard`
--
ALTER TABLE `GoldCard`
  ADD CONSTRAINT `GoldCard_ibfk_1` FOREIGN KEY (`own`) REFERENCES `Users` (`id`);

--
-- Ograniczenia dla tabeli `Res`
--
ALTER TABLE `Res`
  ADD CONSTRAINT `Res_ibfk_1` FOREIGN KEY (`showid`) REFERENCES `Shows` (`id`);

--
-- Ograniczenia dla tabeli `Shows`
--
ALTER TABLE `Shows`
  ADD CONSTRAINT `Shows_ibfk_1` FOREIGN KEY (`mov`) REFERENCES `Movie` (`id`),
  ADD CONSTRAINT `Shows_ibfk_2` FOREIGN KEY (`room`) REFERENCES `CRoom` (`id`),
  ADD CONSTRAINT `Shows_ibfk_3` FOREIGN KEY (`timeid`) REFERENCES `TimeDate` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
