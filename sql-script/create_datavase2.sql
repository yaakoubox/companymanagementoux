











DROP TABLE IF EXISTS `department`;



CREATE TABLE `department` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(50) NOT NULL,
  `dept_location` varchar(150) NOT NULL,
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY (`dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;








DROP TABLE IF EXISTS `project`;



CREATE TABLE `project` (
  `proj_id` int(11) NOT NULL AUTO_INCREMENT,
  `proj_name` varchar(50) NOT NULL,
  `proj_location` varchar(150) NOT NULL,
  `dept_id` int(11) NOT NULL,
  PRIMARY KEY (`proj_id`),
  UNIQUE KEY (`proj_name`,`dept_id`),
  FOREIGN KEY (`dept_id`) REFERENCES `department` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;









DROP TABLE IF EXISTS `workon`;



CREATE TABLE `workon` (
  `workon_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  PRIMARY KEY (`workon_id`),
 // UNIQUE KEY (`emp_id`,`proj_id`),
  FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`),
  FOREIGN KEY (`proj_id`) REFERENCES `project` (`proj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;