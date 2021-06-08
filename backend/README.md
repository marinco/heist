# Backend
Backend part for Heist organising application. 

## Install & Run
```
git clone https://github.com/marinco/heist
cd heist/backend
mvn package
java -jar target/heist-1.0.0.jar 
```

or with Docker
```
docker-compose up
```

## Test
Easiest way is to import file `heist.postman_collection.json` to Postman and test the REST API.