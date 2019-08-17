Simple Java Project using JDBC.
Used for quick tests (PoC).
Database: PostgreSQL and Oracle

## Running the sample
* Run database scripts (./src/main/resources/script_{database}.sql)
* Set connection properties (ConnectionFactory.java)
* Run Test?.java (select the database in the @before method)

Setup a container (postgres image)
``` 
docker run --name bcp-postgres -e POSTGRES_PASSWORD=bcp -d postgres
```
Start/Stop container and get IP Address
``` 
docker start bcp-postgres
docker inspect bcp-postgres | grep IPAddress
docker stop bcp-postgres
```