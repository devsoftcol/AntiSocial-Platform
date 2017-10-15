# AntiSocial v1.0.2


A simple social platform to write articles on topics or comment to other's articles!

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
You have to edit paintLogin.java Servlet to handle SQL communications if it is not compatible with your database.
Also you need to configure context data source and fill the necessary blanks.
If you want to use any database other than MySQL, you have to make necessary changes.
```

For Tomcat Application Server

```
Use Tomcat 8 or above. If you are running in online server like Amazon Elastic Beanstalk read the steps in paintLogin.java
and make changes to Data Source.
```

### Database 
This is a simple application so it has a simple database. SQL Script is below.

```
CREATE TABLE Users (
	UserID int NOT NULL AUTO_INCREMENT,
    Username varchar(40) UNIQUE NOT NULL,
    UserPassword varchar(40) NOT NULL,
    UserEmail varchar(40) NOT NULL,
    PRIMARY KEY(UserID),
    CONSTRAINT users_unique UNIQUE (Username)
);

```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [JSColor](http://jscolor.com/) - Color selecting
* [GSON](https://github.com/google/gson) - JSON Object converting 
* [Connector/J](https://dev.mysql.com/downloads/connector/j/5.1.html) - Connecting to MySQL Database Server
* [Jquery](https://jquery.com/) - AJAX Requests 


## Known Bugs
* There is a bug on mobile or tablets that prevents you from picking a color, you have to spam the button.
* If the server gets overloaded with data It can crash. See Disclaimer for recommended comminication ways.

## Contributing

If you want to contribute to this project you can e-mail me - antkaynak1@gmail.com
or you can pull request.

## Versioning

This project does not have versioning and made with learning purposes.


## Authors 

* **Ant Kaynak** - *Initial work* - [Exercon](https://github.com/Exercon)


## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/Exercon/Painterino-Online-Paint/blob/master/LICENSE) file for details.

JSColor has different license. [JSLicense](http://www.gnu.org/licenses/gpl-3.0.txt) - for details
visit their page [JSColor](http://jscolor.com/)

GSON has different license. [License](http://www.apache.org/licenses/LICENSE-2.0) - for details 
visit their page [GSON](https://github.com/google/gson)

Connector/J [License](https://downloads.mysql.com/docs/licenses/connector-j-5.1-gpl-en.pdf) - for details
visit their page [MySQL](https://dev.mysql.com/downloads/connector/j/5.1.html)


## Acknowledgments

* A huge thanks to BalusC for his [post](https://stackoverflow.com/questions/3679465/find-number-of-active-sessions-created-from-a-given-client-ip/3679783#3679783)
* Another huge thanks to [GSON](https://github.com/google/gson)
* Readme template by [PurpleBooth](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
* Login screen [Aigars Silkalns](https://codepen.io/colorlib/)

# Questions
If you have any questions mail me -  antkaynak1@gmail.com

