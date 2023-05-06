# OCRProjectJava
spring boot project
## Requirements

For building and running the application you need:

- [JDK > 17.0.7](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3](https://maven.apache.org)
- [Mysql]

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `api\src\main\java\com\rentals\api\ApiApplication.java` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
## DATABASE

Install mysql https://dev.mysql.com/doc/refman/8.0/en/installing.html
Create rentals database "CREATE DATABASE RENTALS;"
Import sql file "ressources\sql\script.sql" (code example : database_name < file.sql)

