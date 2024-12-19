# User Fusion

A test project for fetching data from various sources, aggregating it, and presenting it to users in JSON format.

## Overview

The service is designed to collect information from relational databases and return it in JSON format. Currently, it can fetch information from the following database servers:
- H2
- Postgres
- MySQL
- MS SQL Server

It is also possible to connect to Oracle databases, but this type of connection has not been tested yet.

## Project Structure

The project is divided into three modules:
- **user-fusion-spec-api**: Contains the OpenApi 3.0 specification used by OpenAPI Generator to generate the server side. This project is used as a library in the user-fusion-backend project.
- **user-fusion-backend**: Implements the generated REST Service interface and the actual business logic. It uses Spring Boot 3.
- **user-fusion-cloud-config-server**: Used to edit the list of data sources without needing to rebuild user-fusion-backend.

## How It Works

The `application.yml` file contains a block under the `data-sources` section, which lists the following information:
- Alias of the data source
- Type of database to connect to
- Connection string to the database server
- Name of the table to read information from
- Username and password to connect to the database
- List of 4 fields to query from the specified table

Using this information, a `jdbcTemplate` object is created, which performs the formed query to the database and returns the data. This process iterates through all specified sources, collecting information, which is then returned via the REST Controller in JSON format. If necessary, data sources can be added or removed by simply editing `application.yml`.

## Usage

### Prerequisites

Java 17 and Docker 26.1 are required to run the demo in the container.

### Running the Demo

1. First, create a folder on your computer and clone the repository with the command:

    ```sh
    git clone https://github.com/Igorgorb/UserFusion.git
    ```
    ```sh
    cd UserFusion
    ```

2. Then, build the artifacts:
    
    ```sh
    gradlew clean build
    ```

3. Next, build the Docker Compose with the obtained artifacts for the config server and service:
    
    ```sh
    docker compose -f ./docker/compose.yaml up -d
    ```

    This container includes PostgreSQL, MySQL, and MS SQL Server databases. Each server creates a database with a table containing data for demonstration.

4. After building the container and starting all services, the service will be available at:

    [http://localhost:8078/users](http://localhost:8078/users)

### Configuration

Database access settings for retrieving information are located in the file:
[docker/configs/user-fusion-backend.yml](./docker/configs/user-fusion-backend.yml) in the `data-sources` block:

```yaml
data-sources:
  - name: data-base-1
    strategy: postgres
    url: jdbc:postgresql://uf-postgres:5432/mydatabase?user=myuser&password=secret&?options=-c%20search_path=test,public
    table: users
    user: testuser
    password: testpass
    mapping:
      id: user_id
      username: login
      name: first_name
      surname: last_name
  - name: data-base-2
    strategy: mysql
    url: jdbc:mysql://myuser:secret@uf-mysql:3306/mydatabase
    table: user_table
    user: testuser
    password: testpass
    mapping:
      id: oauth_login
      username: us_name
      name: fname
      surname: sname
```

To change an existing connection, edit the corresponding block. To exclude a connection, delete the corresponding block. To add a connection, add and fill in the required data.

### Logging

Service logs are saved in the file:
`docker/logs/application.log`
The `docker/logs` folder is mounted in the container to save service logs.

---
### Local Run

1. First, create a folder on your computer and clone the repository with the command:

    ```sh
    git clone https://github.com/Igorgorb/UserFusion.git
    ```
    ```sh
    cd UserFusion
    ```

2. Configure data sources:
   Before building, edit the `application.yml` file:
   [user-fusion-backend/src/main/resources/application.yml](./user-fusion-backend/src/main/resources/application.yml)
   - Or configure the accessible Cloud Config Server and specify its URL and credentials if necessary in the block:

    ```yaml
    config:
      activate:
        on-profile: default
      name: ${spring.application.name}
      import: optional:configserver:http://uf-config:80
    ```

   - Or edit the access settings blocks in the `data-sources` section to the relevant data.

3. Then, build the artifacts:

    ```sh
    gradlew clean build
    ```

4. Run the service:

    ```sh
    java -jar ./user-fusion-backend/build/libs/user-fusion-backend-0.0.1-SNAPSHOT.jar
    ```

5. After starting, the service will be available at:

    [http://localhost:8080/users](http://localhost:8080/users)

### Logging

For this run option, logging occurs at:

`user-fusion-backend/build/libs/app/logs/application.log`

---

## RoadMap:

- [x] Implement logic for reading from multiple data sources
- [x] Implement REST Controller
- [ ] ~~Obtain OpenApi specification~~



  Stop! Let's start over...



- [x] The OpenApi specification has been written. The result is located in the file:
  [user-fusion-spec-api\specification\1.0.0\api.yaml](./user-fusion-spec-api/specification/1.0.0/api.yaml)
- [x] Create project with three modules: 
    - API
    - Backend
    - Cloud Config Server
- [x] Transfer implemented logic for reading from multiple data sources
- [x] Configure Cloud Config Server
- [x] Create Docker Compose for demonstrating the service, including PostgreSQL, MySQL, and MS SQL Server databases
- [x] Set up database initialization, table creation, and table data population for each database server
- [x] Configure logging
- [x] Write ReadMe
- [x] Addressed code review comments from the Team Lead.
- [x] Add integration tests for reading from multiple sources using Testcontainer
- [ ] Add logic for filtering data on request
- [ ] Add pagination for displaying results
