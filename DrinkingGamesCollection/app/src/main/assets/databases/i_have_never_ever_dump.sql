-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 20, 2016 at 01:24 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `i_have_never_ever`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `catergorieName` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`catergorieName`) VALUES
('Adult'),
('Drinking'),
('Law'),
('Love'),
('Other'),
('School'),
('Work');

-- --------------------------------------------------------

--
-- Table structure for table `languages`
--

CREATE TABLE `languages` (
  `languageName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `languages`
--

INSERT INTO `languages` (`languageName`) VALUES
('Deutsch'),
('English');

-- --------------------------------------------------------

--
-- Table structure for table `statements`
--

CREATE TABLE `statements` (
  `statementID` int(11) NOT NULL,
  `statement` text NOT NULL,
  `category` varchar(50) NOT NULL DEFAULT 'Other',
  `language` varchar(50) NOT NULL DEFAULT 'English'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `statements`
--

INSERT INTO `statements` (`statementID`, `statement`, `category`, `language`) VALUES
(1, 'fantasized about my mom', 'Adult', 'English'),
(2, 'kissed a friend''s sibling', 'Adult', 'English'),
(3, 'had sex in costume', 'Adult', 'English'),
(4, 'had sex in front of an audience', 'Adult', 'English'),
(5, 'wanted to have sex with someone in this room', 'Adult', 'English'),
(6, 'fantasized about my mom', 'Adult', 'English'),
(7, 'kissed a friend''s sibling', 'Adult', 'English'),
(8, 'had sex in costume', 'Adult', 'English'),
(9, 'had sex in front of an audience', 'Adult', 'English'),
(10, 'wanted to have sex with someone in this room', 'Adult', 'English'),
(11, 'been so drunk I couldn''t remember how I got home', 'Drinking', 'English'),
(12, 'had a hangover', 'Drinking', 'English'),
(13, 'had cops show up to my party', 'Drinking', 'English'),
(14, 'been so drunk I couldn''t remember how I got home', 'Drinking', 'English'),
(15, 'had a hangover', 'Drinking', 'English'),
(16, 'had cops show up to my party', 'Drinking', 'English'),
(17, 'go to jail', 'Law', 'English'),
(18, 'stolen', 'Law', 'English'),
(19, 'had trouble with the police', 'Law', 'English'),
(20, 'public passing water', 'Law', 'English'),
(21, 'used a fake ID', 'Law', 'English'),
(22, 'go to jail', 'Law', 'English'),
(23, 'stolen', 'Law', 'English'),
(24, 'had trouble with the police', 'Law', 'English'),
(25, 'public passing water', 'Law', 'English'),
(26, 'used a fake ID', 'Law', 'English'),
(27, 'had a boy/girlfriend', 'Love', 'English'),
(28, 'was falling in love with someone who was already in a relationship', 'Love', 'English'),
(29, 'kissed a girl/boy', 'Love', 'English'),
(30, 'kissed the same gender', 'Love', 'English'),
(31, 'been friendzoned', 'Love', 'English'),
(32, 'had a boy/girlfriend', 'Love', 'English'),
(33, 'was falling in love with someone who was already in a relationship', 'Love', 'English'),
(34, 'kissed a girl/boy', 'Love', 'English'),
(35, 'kissed the same gender', 'Love', 'English'),
(36, 'been friendzoned', 'Love', 'English'),
(37, 'gone to prome alone', 'School', 'English'),
(38, 'cheated on an exam', 'School', 'English'),
(39, 'been in a food fight', 'School', 'English'),
(40, 'been a bully', 'School', 'English'),
(41, 'skip school', 'School', 'English'),
(42, 'skip work', 'Work', 'English'),
(43, 'sleep at work', 'Other', 'English'),
(44, 'hooked up with a co-worker', 'Work', 'English'),
(45, 'play a prank at work', 'Work', 'English'),
(46, 'lied on a job application', 'Work', 'English'),
(47, 'cheated on a girlfriend/boyfriend', 'Other', 'English'),
(48, 'watched more than 3 episodes of a series in one day', 'Other', 'English'),
(49, 'etwas gestohlen', 'Law', 'Deutsch'),
(53, 'einen Streich während der Arbeit gespielt', 'Work', 'Deutsch'),
(54, 'bei einem Bewerbungsgesrpäch gelogen', 'Work', 'Deutsch'),
(55, 'meine(n) Freundin/Freund betrogen', 'Other', 'Deutsch'),
(56, 'einem Lehrer / einer Lehrerin auf den Arsch gestarrt', 'School', 'Deutsch'),
(57, 'mit mir selbst geredet.', 'Other', 'Deutsch'),
(58, 'meine Eltern beim Sex erwischt.', 'Adult', 'Deutsch'),
(59, 'in der Öffentlichkeit in die Hosen gemacht.', 'Other', 'Deutsch'),
(60, 'in einen Busch gepinkelt.', 'Other', 'Deutsch'),
(61, 'in der Dusche gesungen.', 'Other', 'Deutsch'),
(62, 'über Sex geträumt.', 'Adult', 'Deutsch'),
(63, 'nackt im Freibad / See gebadet.', 'Other', 'Deutsch'),
(64, 'meinen Namen gegoogelt.', 'Other', 'Deutsch'),
(65, 'ein Tagebuch geführt.', 'Other', 'Deutsch'),
(66, 'jemanden angekotzt.', 'Drinking', 'Deutsch'),
(67, 'wegen einer Spinne gekreischt.', 'Other', 'Deutsch'),
(68, 'Angst im Dunkeln gehabt.', 'Other', 'Deutsch'),
(69, 'das ABC gerülpst.', 'Other', 'Deutsch'),
(70, 'an einen elektrischen Zaun gefasst.', 'Other', 'Deutsch'),
(71, 'einen Zungenkuss gehabt', 'Love', 'Deutsch'),
(72, 'meinem Kuscheltier meine Sorgen erzählt', 'Other', 'Deutsch'),
(73, 'länger wie 2/3 Tage lang nicht geduscht', 'Other', 'Deutsch'),
(74, 'Gurkensaft getrunken', 'Other', 'Deutsch'),
(75, 'Sex auf einer Party gehabt', 'Adult', 'Deutsch'),
(76, 'Einen Fetish gehabt', 'Adult', 'Deutsch'),
(77, 'Sex in der Umkleide gehabt', 'Adult', 'Deutsch'),
(78, 'Einen Blog gehabt', 'Other', 'Deutsch'),
(79, 'Special Brownies gehabt', 'Other', 'Deutsch'),
(80, 'Mit jemanden geschlafen, den ich aus dem Internet kannte.', 'Adult', 'Deutsch'),
(81, 'Für eine Raubkopie Geld bezahlen müssen', 'Law', 'Deutsch'),
(82, 'Blöde Trinkspiele gespielt', 'Drinking', 'Deutsch'),
(83, 'Jemanden von meinem eigenen Geschlecht geküsst', 'Law', 'Deutsch'),
(84, 'Etwas teures geklaut', 'Law', 'Deutsch'),
(85, 'Von der Polizei abgeholt werden müssen', 'Law', 'Deutsch'),
(86, 'Jemanden auf Facebook blockiert', 'Other', 'Deutsch'),
(87, 'Für Sex bezahlt', 'Adult', 'Deutsch'),
(88, 'Mit einer Kommilitonin / einem Kommilitionen rumgemacht', 'School', 'Deutsch'),
(89, 'Einen Alkohol getrunken, den ich jetzt nicht mehr trinken kann', 'Drinking', 'Deutsch'),
(90, 'Mit jemanden geschlafen, dessen Namen ich morgens nicht mehr wusste', 'Adult', 'Deutsch'),
(91, 'In Socken geduscht', 'Other', 'Deutsch'),
(92, 'Mein Handy in einen Fluss fallen lassen', 'Other', 'Deutsch'),
(93, 'Sex auf einem Bürgersteig gehabt', 'Adult', 'Deutsch'),
(94, 'Liebeskummer gehabt', 'Love', 'Deutsch'),
(95, 'Weed geraucht', 'Law', 'Deutsch');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`catergorieName`);

--
-- Indexes for table `languages`
--
ALTER TABLE `languages`
  ADD PRIMARY KEY (`languageName`);

--
-- Indexes for table `statements`
--
ALTER TABLE `statements`
  ADD PRIMARY KEY (`statementID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `statements`
--
ALTER TABLE `statements`
  MODIFY `statementID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
