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
* [Docker](https://www.docker.com/)

The project is an example of using several technologies, mainly including:
* Spring Security
* Spring Data Jpa
* REST
* JWT
* Mysql
* Docker

### Running Localy

If you want to easily run this application, you must first have docker on your machine.

#### Start Application

Only one command is required to start Yacht-Charter-Api:

```console
docker compose up
```

#### Stop Application

If you want to stop application you should use command below:

```console
docker compose down
```
## Rest endpoints
Check out list of application endpoints.

### Auth

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| POST   | /auth/register | Sign up | N/A | [JSON](#signup) |
| POST   | /auth/authenticate | Sign in | N/A | [JSON](#signin) |

### Users

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /users | Get all users | ADMIN | N/A |
| GET    | /users/{id} | Get specific user | ADMIN | N/A |
| PUT    | /users/{id} | Update specific user | ADMIN | [JSON](#updateuser) |
| DELETE | /users/{id} | Delete specific user | ADMIN | N/A |

### Books

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /books | Get all books | USER | N/A |
| GET    | /books/{id} | Get specific book | USER | N/A |
| POST   | /books | Add book | ADMIN | [JSON](#createbook) |
| PUT    | /books/{id} | Update specific book | ADMIN | [JSON](#updatebook) |
| DELETE | /books/{id} | Delete specific book | ADMIN | N/A |

### Threads

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /threads | Get all threads | USER | N/A |
| GET    | /threads/{id} | Get specific thread | USER | N/A |
| GET    | /books/{id}/threads | Get specific book threads | USER | N/A |
| POST   | /threads | Add thread | USER | [JSON](#createthread) |
| PUT    | /threads/{id} | Update specific thread | USER(*) | [JSON](#updatethread) |
| DELETE   | /threads/{id} | Delete specific thread | USER(*) | N/A |


### Messages

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /threads/{id}/messages | Get specific thread messages | USER | N/A |
| POST   | /threads/{id}/messages | Add specific thread message| USER | [JSON](#createmessage) |
| PUT    | /messages/{id} | Update specific message | USER(*) | [JSON](#updatemessage) |
| DELETE   | /messages/{id} | Delete specific message | USER(*) | N/A |

*(\*) Required USER who created it or ADMIN*

## Request body examples

#### <a id="signup">Sign Up (/auth/register)</a>
```json
{
	"firstName": "Jan",
	"lastName": "Kowalski",
	"email": "jan.kowalski@smh.com",
	"password": "password"
}
```

#### <a id="signin">Sign In (/auth/authenticate)</a>
```json
{
	"email": "jan.kowalski@smh.com",
	"password": "password"
}
```

#### <a id="updateuser">Update User (/users/{id})</a>
```json
{
	"firstName": "Jan",
	"lastName": "Kowalski",
	"email": "jan.kowalski@smh.com",
	"password": "password"
}
```

#### <a id="createbook">Create Book (/books)</a>
```json
{
	"name": "Book",
	"publicationYear": "2023",
	"description": "An example of book"
}
```

#### <a id="updatebook">Update Book (/books/{id})</a>
```json
{
	"name": "Updated Book",
	"publicationYear": "2023",
	"description": "An updated example of book"
}
```

#### <a id="createthread">Create Thread (/threads)</a>
```json
{
	"name": "Thread",
	"bookId": "1",
	"content": "What do you think about this book?"
}
```

#### <a id="updatethread">Update Thread (/threads/{id})</a>
```json
{
	"name": "Updated Thread",
	"bookId": "1",
	"content": "What do you think about this book?"
}
```

#### <a id="createmessage">Create Message (/threads/{id}/messages)</a>
```json
{
	"content": "I think this book is good."
}
```

#### <a id="updatemessage">Update Message (/messages/{id})</a>
```json
{
	"content": "I think this book is great!"
}
```

## Author

* **Maciej Jurczak** 

See also other projects of [Maciek0475](https://github.com/Maciek0475).

