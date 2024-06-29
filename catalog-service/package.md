# Running with Docker

## Creating the network

```bash
docker network create catalog-net
```

## Postgres Container
```bash
docker run  --name catalogdb -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=catalog -p 5432:5432 --net catalog-net postgres:14.4
```

## Build and Run catalog-configurations-service
```bash
./gradlew clean bootJar
docker build -t catalog .
```

## Build and Run catalog-service 
```bash
./gradlew clean bootJar
docker build -t catalog-config .

```

## Running services
```bash
docker run -d --name catalog-config --net catalog-net -p 8087:8087 catalog-config
docker run -d --name catalog --net catalog-net -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://catalogdb:5432/catalog -e SPRING_PROFILES_ACTIVE=dev catalog
```

## Package and Publish
```bash
./gradlew bootBuildImage --imageName ghcr.io/leandrodalbo/catalog --publishImage --P registryUrl=ghcr.io --P registryUsername=<> --P registryToken=<>  
```
