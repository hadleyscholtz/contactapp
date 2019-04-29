# Contact Application
This application is a POC to test the capabilities of creating new users in an in-memory database. 

There is also functionality to be able to list all registered users. In addition to this, by authenticating a user, that particular user is able to gather information on which users are currently logged in.

## Executing
An executable jar is available to run the application standalone. 
Please keep in mind that the application runs on port **8080** so be sure to keep this port open when running the jar.

## Data Storage
An in-memory database houses the data and can be accessed as follows, provided the application is running.

<http://localhost:8080/h2-console>

**JDCB Url:** *jdbc:h2:./contact*

**User Name:** *contactUser*

**Password:** *pleaseCallMe!*

## Usage
A sample Postman project is available to call the various endpoints on the application.

#### 1.) Add User
The Add User feature allows you to add a new user.

**Sample Request:**

```
{
	"user_name":"awesome_sauce123",
	"phone":"123456790",
	"password": "thisIsMySuperSecretPassword!@#!@#"
}
```

**Sample Response:**

```
User added successfully.
```

#### 2.) List Users
The List Users feature allows you to list all users currently registered in the database.

**Sample Response:**

```
{
    "users": [
        {
            "user_id": 1,
            "phone": "123456790"
        }
    ]
}
```

**Sample Response (Authenticated):**

```
{
    "users": [
        {
            "user_id": 1,
            "phone": "123456790",
            "valid_session": true
        }
    ]
}
```

#### 3.) Login
The login feature allows you to authenticate your user. 
This returns an authentication token which is valid for a total of 180 seconds. 
Once authenticated, you are able to additional to listing all users, also see which users are currently logged in.
In order to see this information, you need to pass the authentication token as http header *User-Token*.

**Sample Request:**

```
{
	"user_name":"awesome_sauce123",
	"password":"thisIsMySuperSecretPassword!@#!@#"
}
```

**Sample Response:**

```
{
    "id": 1,
    "token": "fQa55RpbnblSG+XBNTloEG018AM="
}
```

#### 4.) Logout
The logout feature allows you to invalidate your user session and expire the token.

**Sample Response:**

```
{
    "token": "fQa55RpbnblSG+XBNTloEG018AM="
}
```

## Author
Hadley Scholtz
