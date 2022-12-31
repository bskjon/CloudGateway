# Docker environment


[Docker image](https://hub.docker.com/r/bskjon/cloud-gateway)

## Docker Compose
### Single instance
```yaml
version: "3"
services:
  
  cloud_gateway:
    restart: always
    image: bskjon/cloud-gateway:kotlin-snapshot
    ports:
      - "127.0.0.1:8080:8080"
    volumes:
      - ./data/cloudgw/:/root/
    environment:
      GOOGLE_APPLICATION_CREDENTIALS: "/root/service-account-file.json"
```

### Multi instance
```yaml
version: "3"
services:
  
  cloud_gateway:
    restart: always
    image: bskjon/cloud-gateway:kotlin-snapshot
    ports:
      - "127.0.0.1:8080:8080"
    volumes:
      - ./data/cloudgw/:/root/
    environment:
      FIREBASE_CREDENTIALS: "/root/multitenant.json"
```
Please see [Multi instance](FCM%20Multi%20Instance.md) for how to create `multitenant.json`