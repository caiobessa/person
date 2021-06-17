# Requirements 

- JDK 11+

# Start app with Database in memory

Install de dependencies
```shell
./mvnw install
```

Start the app
```shell
./mvnw spring-boot:run
```

# Start with database saving in file

Start the app
```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=data
```

### To access the database:
- Access: http://localhost:8080/h2-console/

- JDBC URL: jdbc:h2:~/persondb
- User Name: sa
- click in connect ( Does not need password)

# Web services

- Create Person : ```POST http://localhost:8080/person```
```json
{ 
  "firstName" : "James" ,
  "lastName": "Smith"
}
```
- Edit Person : ```POST http://localhost:8080/person```
```json
{ 
  "id" : 1,
  "firstName" : "James" ,
  "lastName": "NewLastName"
}
```
- Get Person by Id: ```GET http://localhost:8080/person/{id}```

- Delete Person: ```DELETE http://localhost:8080/person/{id}```

- Create Address: ```POST http://localhost:8080/address```
```json
{
    "person" : {"id" : 1 },
    "city": "Dublin",
    "street": "8 Street",
    "postalCode" : "010101",
    "state": "Dublin"
}
```

- Edit Address: ```POST http://localhost:8080/address```
```json
{
    "id" : 2
    "person" : {"id" : 1 },
    "city": "Dublin new",
    "street": "8 Street new",
    "postalCode" : "010101",
    "state": "Dublin new"
}
```

- Delete Address by Id: ```DELETE http://localhost:8080/address/{id}```

- List All Person: ```GET http://localhost:8080/person```
