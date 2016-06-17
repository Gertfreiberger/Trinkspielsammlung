
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

CREATE TABLE `statements` (
  `statementID` int(11) NOT NULL,
  `statement` text NOT NULL,
  `categorie` varchar(50) NOT NULL DEFAULT 'Other'
);

INSERT INTO `statements` (`statementID`, `statement`, `categorie`) VALUES
(1, 'fantasized about my mom', 'Adult'),
(2, 'kissed a friend''s sibling', 'Adult'),
(3, 'had sex in costume', 'Adult'),
(4, 'had sex in front of an audience', 'Adult'),
(5, 'wanted to have sex with someone in this room', 'Adult'),
(6, 'fantasized about my mom', 'Adult'),
(7, 'kissed a friend''s sibling', 'Adult'),
(8, 'had sex in costume', 'Adult'),
(9, 'had sex in front of an audience', 'Adult'),
(10, 'wanted to have sex with someone in this room', 'Adult'),
(11, 'been so drunk I couldn''t remember how I got home', 'Drinking'),
(12, 'had a hangover', 'Drinking'),
(13, 'had cops show up to my party', 'Drinking'),
(14, 'been so drunk I couldn''t remember how I got home', 'Drinking'),
(15, 'had a hangover', 'Drinking'),
(16, 'had cops show up to my party', 'Drinking'),
(17, 'go to jail', 'Law'),
(18, 'stolen', 'Law'),
(19, 'had trouble with the police', 'Law'),
(20, 'public passing water', 'Law'),
(21, 'used a fake ID', 'Law'),
(22, 'go to jail', 'Law'),
(23, 'stolen', 'Law'),
(24, 'had trouble with the police', 'Law'),
(25, 'public passing water', 'Law'),
(26, 'used a fake ID', 'Law'),
(27, 'had a boy/girlfriend', 'Love'),
(28, 'was falling in love with someone who was already in a relationship', 'Love'),
(29, 'kissed a girl/boy', 'Love'),
(30, 'kissed the same gender', 'Love'),
(31, 'been friendzoned', 'Love'),
(32, 'had a boy/girlfriend', 'Love'),
(33, 'was falling in love with someone who was already in a relationship', 'Love'),
(34, 'kissed a girl/boy', 'Love'),
(35, 'kissed the same gender', 'Love'),
(36, 'been friendzoned', 'Love'),
(37, 'gone to prome alone', 'School'),
(38, 'cheated on an exam', 'School'),
(39, 'been in a food fight', 'School'),
(40, 'been a bully', 'School'),
(41, 'skip school', 'School'),
(42, 'skip work', 'Work'),
(43, 'sleep at work', 'Other'),
(44, 'hooked up with a co-worker', 'Work'),
(45, 'play a prank at work', 'Work'),
(46, 'lied on a job application', 'Work'),
(47, 'cheated on a girlfriend/boyfriend', 'Other'),
(48, 'watched more than 3 episodes of a series in one day', 'Other');

