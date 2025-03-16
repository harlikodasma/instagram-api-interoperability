Create a .env file in project's root and add your desired database name, username and password:  
```
POSTGRES_DB=instagram
POSTGRES_USERNAME=myuser
POSTGRES_PASSWORD=mypassword
```

For running the Spring Boot application locally, add these VM options:
```
-DPOSTGRES_DB=instagram
-DPOSTGRES_USERNAME=myuser
-DPOSTGRES_PASSWORD=mypassword
```
