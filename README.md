# Forum-Rest-Api

Back-end RESTful application for forum. 

## Getting Started

With the Forum-Rest-Api you can create forum, deafault this forum is for Tolkien fans but you can change it as you like.
The forum collect informations about Books writen by Tolkien, but also User data. Like in every forum you can write threads and comment them. This app integrates community and is realy helpfull to it. Go ahead and try it. 

### Technology Stack
Mostly used:
* [Spring Boot](https://spring.io/)
* [Mysql](https://www.mysql.com/)
* [JWT](https://jwt.io/)

The project is an example of using several technologies, mainly including:
* Spring Security
* Spring Data Jpa
* REST
* JWT
* Mysql

### Preparing Database

You have to create database and import `rest-api.sql`. In `application.properties`
you can manipulate connection data as you like.

### Running Localy

You can run Forum-Rest-Api from your IDE. All you have to do after import this project is execute `ForumRestApiApplication.java` in package `com.mac2work.forumrestapi`.

Also you can run the following command in a terminal window (in the complete directory):

```
mvnw spring-boot:run
```
## Rest endpoints
Check out list of application endpoints.

### Auth

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| POST   | /auth/authorization | Sign up | N/A | [JSON](#) |
| POST   | /auth/authentication | Sign in | N/A | [JSON](#) |

### Users

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /users | Get all users | ADMIN | N/A |
| GET    | /users/{id} | Get specific user | ADMIN | N/A |
| PUT    | /users/{id} | Update specific user | ADMIN | [JSON] (#) |
| DELETE    | /users/{id} | Delete specific user | ADMIN | N/A |

### Books

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /books | Get all books | USER | N/A |
| GET    | /books/ | Get all books | USER | N/A |
| GET    | /books | Get all books | USER | N/A |

### Threads

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |

### Messages

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |


## Author

* **Maciej Jurczak** 

See also other projects of [Maciek0475](https://github.com/Maciek0475).

