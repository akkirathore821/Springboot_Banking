# Spring Boot Banking Microservices Project
(Eureka Server, API Gateway, Kafka, Postgresql, JWT, Authentication, Authorization, Redis, Docker)

# About the project

<ul style="list-style-type:disc">
  <li>This project is based Spring Boot Microservices</li>
  <li>User can register and login through auth service by user role (ADMIN or USER) through api gateway</li>
  <li>User can send any request to relevant service through api gateway with its bearer token</li>
</ul>

7 services whose name are shown below have been devised within the scope of this project.

- Eureka Server
- API Gateway
- Authentication Service
- Account Service
- Transaction Service
- Notification Service
- Fraud Service

### Used Dependencies

* Core
    * Spring
        * Spring Boot
        * Spring Security
            * Spring Security JWT
            * Authentication
            * Authorization
        * Spring Web
            * FeighClient
        * Spring Data
            * Spring Data JPA
            * PostgreSQL
        * Spring Cloud
            * Spring Cloud Gateway Server
            * Spring Cloud Config Server
            * Spring Cloud Config Client
    * Netflix
        * Eureka Server
        * Eureka Client
* Database
    * PostgreSQL
* Kafka
* Redis
* Docker
* Validation
* Openapi UI
* Lombok

### Explore Rest APIs

<table style="width:100%">
  <tr>
      <th>Method</th>
      <th>Url</th>
      <th>Description</th>
      <th>Valid Request Body</th>
  </tr>
  <tr>
      <td>POST</td>
      <td>/api/auth/auth_details/register</td>
      <td>Register for New User</td>
      <td><a href="#register">Info</a></td>
  </tr>
  <tr>
      <td>POST</td>
      <td>/api/auth/auth/login</td>
      <td>Login for User and Admin</td>
      <td><a href="#login">Info</a></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/api/accounts/accounts_details/getAccount</td>
      <td>Get account of a user</td>
      <td><a href="#getAccount">Info</a></td>
  </tr>
  <tr>
      <td>POST</td>
      <td>/api/auth/auth_details/getAuthDetails</td>
      <td>Get Auth details by username</td>
      <td><a href="#getAuthDetails">Info</a></td>
  </tr>

 <tr>
      <td>POST</td>
      <td>/api/accounts/create</td>
      <td>Create account of a user</td>
      <td><a href="#create">Info</a></td>
  </tr>

 <tr>
      <td>POST</td>
      <td>/api/accounts/accounts/update_balance</td>
      <td>Update user bank balance</td>
      <td><a href="#update_balance">Info</a></td>
  </tr>

  <tr>
      <td>POST</td>
      <td>/api/transactions/deposit</td>
      <td>Deposit amount in user account</td>
      <td><a href="#deposit">Info</a></td>
  </tr>

  <tr>
      <td>POST</td>
      <td>/api/transactions/withdraw</td>
      <td>Withdraw amount from user account</td>
      <td><a href="#withdraw">Info</a></td>
  </tr>

  <tr>
      <td>POST</td>
      <td>/api/transactions/transfer</td>
      <td>Transfer amount from user account to another</td>
      <td><a href="#transfer">Info</a></td>
  </tr>
</table>


## Valid Request Body

##### <a id="register"> Register for New User

``` 
    http://localhost:8080/api/auth/auth_details/register
    
        {
            "username" : "string",
            "password" : "string",
            "accountHolder" : "string",
            "role" : "string"    // ROLE_USER or ROLE_ADMIN
        }
        
    Bearer Token : Admin Token
```

##### <a id="login"> Login for User and Admin

```
      http://localhost:8080/api/auth/auth/login
    
       {
         "username": "string",
         "password": "string"
       }
```

##### <a id="getAccount"> 	Get account of a user

```
    http://localhost:8080/api/accounts/accounts_details/getAccount
    
    {}
    
    Bearer Token : Authorized User or Admin
    
    //Token contains the acccount no. of the user
```

##### <a id="getAuthDetails"> Get Auth details by username

``` 
    http://localhost:8080/api/auth/auth_details/getAuthDetails
    
    {
        "username" : "string"
    }
    
    Bearer Token : Admin Token
```

##### <a id="create"> Create account of a user

``` 
    http://localhost:8080/api/accounts/create
    
    {
        "accountNumber" : "string",
        "accountHolder" : "string"
    }
    
    Bearer Token : Admin Token
```

##### <a id="update_balance"> Update user bank balance

``` 
    http://localhost:8080/api/accounts/accounts/update_balance
    
    {
        "accountNumber" : "string",
        "amount" : integer,
        "operation" : "string"  // CREDIT or DEBIT
    }
    
    Bearer Token : Admin Token
```

##### <a id="deposit"> Deposit amount in user account

``` 
    http://localhost:8080/api/transactions/deposit
    
    {
        "amount" : integer
    }
        
    Bearer Token : Authorized User or Admin
```

##### <a id="advertCreate"> Withdraw amount from user account

``` 
    http://localhost:8080/api/transactions/withdraw
    
    {
        "amount" : integer
    }
        
    Bearer Token : Authorized User or Admin
```

##### <a id="transfer"> Transfer amount from user account to another

``` 
    http://localhost:8080/api/transactions/transfer
        
    {
        "amount" : 1346,
        "receiverAccountNumber" : "ACC548864"
    }
        
    Bearer Token : Authorized User or Admin
```

[//]: # ()
[//]: # (### 🔨 Run the App)

[//]: # ()
[//]: # (<b>Local</b>)

[//]: # ()
[//]: # (<b>1 &#41;</b> Clone project `git clone https://github.com/devsyx/spring-boot-microservices.git`)

[//]: # ()
[//]: # (<b>2 &#41;</b> Go to the project's home directory :  `cd spring-boot-microservices`)

[//]: # ()
[//]: # (<b>3 &#41;</b> Run docker compose <b>`docker compose up`</b></b>)

[//]: # ()
[//]: # (<b>4 &#41;</b> Run <b>Eureka Server</b>)

[//]: # ()
[//]: # (<b>5 &#41;</b> Run <b>Gateway</b>)

[//]: # ()
[//]: # (<b>6 &#41;</b> Run <b>Config Server</b>)

[//]: # ()
[//]: # (<b>7 &#41;</b> Run other services &#40;<b>auth-service</b>, <b>user-service</b>, <b>job-service</b>, <b>notification-service</b>  and lastly <b>)

[//]: # (file-storage</b>&#41;)

[//]: # ()
[//]: # (<b>8 &#41;</b> For swagger ui localhost:8080/v1/{service-name}/swagger-ui/index.html</b>)

[//]: # ()
[//]: # ()
[//]: # (### Screenshots)

[//]: # ()
[//]: # (<details>)

[//]: # (<summary>Click here to show the screenshot of project</summary>)

[//]: # (    <p> Eureka Server</p>)

[//]: # (    <img src ="screenshots/eureka.png" alt="">)

[//]: # (    <p>User Service Swagger UI</p>)

[//]: # (    <img src ="screenshots/user.png" alt="">)

[//]: # (    <p>Job Service Swagger UI</p>)

[//]: # (    <img src ="screenshots/category-advert.png" alt="">)

[//]: # (    <img src ="screenshots/offer-job.png" alt="">)

[//]: # (    <p> Auth Service Swagger UI </p>)

[//]: # (    <img src ="screenshots/auth.png" alt="">)

[//]: # (    <p>Notification Kafka UI</p>)

[//]: # (    <img src ="screenshots/kafka-ui.png" alt="">)

[//]: # (    <p>File Storage Postman</p>)

[//]: # (    <img src ="screenshots/file-upload.png" alt="">)

[//]: # (    <img src ="screenshots/file-download.png" alt="">)

[//]: # (</details>)
