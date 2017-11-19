# AntiSocial v1.0.2


A simple social platform to write articles on topics or comment to other's articles!

##  Update
Spring Boot version has been added.
It has the same features but made with Spring Boot 2.0.0+
Make sure to check it out! :)

## Some Screenshots

![alt text](https://github.com/Exercon/AntiSocial-Platform/blob/master/Pictures/Screenshot%20from%202017-10-16%2001-18-34.png)

![alt text](https://github.com/Exercon/AntiSocial-Platform/blob/master/Pictures/Screenshot%20from%202017-10-16%2001-20-22.png)

![alt text](https://github.com/Exercon/AntiSocial-Platform/blob/master/Pictures/Screenshot%20from%202017-10-16%2001-22-14.png)

![alt text](https://github.com/Exercon/AntiSocial-Platform/blob/master/Pictures/Screenshot%20from%202017-10-16%2001-23-34.png)

![alt text](https://github.com/Exercon/AntiSocial-Platform/blob/master/Pictures/Screenshot%20from%202017-10-16%2001-31-14.png)

![alt text](https://github.com/Exercon/AntiSocial-Platform/blob/master/Pictures/Screenshot%20from%202017-10-16%2001-32-08.png)


## Getting Started
  This project does not use maven or gradle. Please consider checking library list given below.

## About This Project

### Registration
* Your password needs to contain an uppercase character, a lowercase character at least a number and a special character
and must be at least 8 digits long.
* After registration you have to confirm your email by logging in to your mail and clickling to activation link.
* You can check the remember me checkbox and use the cookie to login any time you browse this site.
* Technical details can be found below.

### Usage

* You can publish articles on the given categories.
* You can upload profile picture and profile background picture for your profile page.
* You can write your bio and/or update your full name.
* You can search any article or user with the search bar above.
* If an user is online the circle in their profile will be green, if not it will be gray.
* You can comment on any article and delete your comments any time you want.
* Admins can ban or reconfigure users.
* Mobile responsive site.
* You can turn on the dark theme in the settings tab.
* You can browse through categories with the browse button and search a category name.
* Anonymous users can only visit single article URL's or the about page.

### Disclaimer
This project is made for learning purposes. This project does not make any profit.


### Prerequisites

What things you need to install

```
You need Tomcat server 8 or above installed.
Locally installed MySQL or a MySQL server.
Compatible IDE, Intellij IDEA recommended for this project.
Web application 3.1 ( No web.xml used for this project. Java configuration included. )


```

### Installing



For MySQL Database 

```
You have to edit application.properties for your own usage.
If you want to use any database other than MySQL, you have to make necessary changes.

```

For Tomcat Application Server

```
Use Tomcat 8.5.23. 

```

### Database 
This application uses hibernate and cascading. SQL Script is below.

```

DROP TABLE IF EXISTS `user_detail`;

CREATE TABLE `user_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `user_bio` TEXT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sso_id` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `user_detail_id` int(11),
  PRIMARY KEY (`id`),
  UNIQUE(`sso_id`),
  UNIQUE (`email`),
  FOREIGN KEY (`user_detail_id` ) REFERENCES `user_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` int(11) NOT NULL,
  `article_header` TEXT NOT NULL,
  `article_body` TEXT NOT NULL,
  `article_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_owner` varchar(45) NOT NULL,
  `comment_owner_ssoid` varchar(45) NOT NULL,
  `comment_body` TEXT NOT NULL,
  `comment_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
    `username` VARCHAR(64) NOT NULL,
    `series` VARCHAR(64) NOT NULL,
    `token` VARCHAR(64) NOT NULL,
    `last_used` TIMESTAMP NOT NULL,
    PRIMARY KEY (`series`)
);

DROP TABLE IF EXISTS `verification_token`;

CREATE TABLE `verification_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `expiry_date` TIMESTAMP NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS = 1;

```


## Built With

* [Spring MVC](https://spring.io) - Spring 4.3.10.RELEASE
* [Spring Security](https://projects.spring.io/spring-security) - Spring Security 3.2.5
* [Hibernate](http://hibernate.org) - Hibernate 5.2.10.Final and Hibernate Validator
* [Connector/J](https://dev.mysql.com/downloads/connector/j/5.1.html) - Connecting to MySQL Database Server
* [Jquery](https://jquery.com/) - AJAX Requests 

### Other Libraries

* jackson-annotations-2.8.0
* jackson-core-2.8.2
* jackson-databind-2.8.2
* jstl-1.2
* mchange-commons-java-0.2.11
* c3p0-0.9.5.2
* hibernate-c3p0-5.2.10.Final
* hibernate-validator-5.4.1.Final
* javax.mail-1.4.7
* commons-fileupload-1.3.2
* commons-io-2.5
* javax.servlet-api-3.1.0
* hibernate-ehcache-5.2.10.Final
* ehcache-2.10.4
* slf4j-api-1.7.25


## Known Bugs
* When an admin changes the category name, the image of the category will not change.
* Admin panel is missing some components.
* If an user changes his/her full name, their previous comments will not update to the new name.
* Some UI bugs in mobile.

## Contributing

If you want to contribute to this project you can e-mail me - antkaynak1@gmail.com
or you can pull request.

## Versioning

This project does not have versioning and made with learning purposes.


## Authors 

* **Ant Kaynak** - *Initial work* - [Exercon](https://github.com/Exercon)


## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/Exercon/AntiSocial-Platform/blob/master/LICENSE) file for details.


## Acknowledgments
* Login screen [Aigars Silkalns](https://codepen.io/colorlib/)
* Profile page [Thomas d'Aubenton](https://codepen.io/ThomasDaubenton/)

# Questions
If you have any questions mail me -  antkaynak1@gmail.com

