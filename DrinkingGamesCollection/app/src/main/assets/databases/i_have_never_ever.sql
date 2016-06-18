CREATE TABLE `categories` (
  `catergorieName` varchar(50) NOT NULL
);

INSERT INTO `categories` (`catergorieName`) VALUES
('Adult'),
('Drinking'),
('Law'),
('Love'),
('Other'),
('School'),
('Work');

CREATE TABLE `languages` (
  `languageName` varchar(100) NOT NULL
);

INSERT INTO `languages` (`languageName`) VALUES
('Deutsch'),
('English');

CREATE TABLE `statements` (
  `statementID` int(11) NOT NULL,
  `statement` text NOT NULL,
  `category` varchar(50) NOT NULL DEFAULT 'Other',
  `language` varchar(50) NOT NULL DEFAULT 'English'
) ;

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
(50, 'been so drunk I couldn''t remember how I got home', 'Drinking', 'Deutsch'),
(51, 'schwierige', 'Law', 'Deutsch'),
(52, 'alleine zu einem Ball gegangen', 'School', 'Deutsch'),
(53, 'einen Streich während der Arbeit gespielt', 'Work', 'Deutsch'),
(54, 'bei einem Bewerbungsgesrpäch gelogen', 'Work', 'Deutsch'),
(55, 'meine(n) Freundin/Freund betrogen', 'Other', 'Deutsch');

