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

## SQL Script
``` 
docker inspect bcp-postgres | grep IPAddress
psql -h <container-ip> -U postgres

CREATE DATABASE bcpdb;
CREATE USER bcp WITH ENCRYPTED PASSWORD 'bcp';
GRANT ALL PRIVILEGES ON DATABASE bcpdb TO bcp;
psql -h <container-ip> -U bcp -d bcpdb

psql -h 172.17.0.2 -d bcpdb -U bcp -f ./src/main/resources/script_postgre.sql
``` 