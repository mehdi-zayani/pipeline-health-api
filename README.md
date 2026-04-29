# Pipeline Health API

## Overview

Pipeline Health API is a backend microservice designed to monitor and analyze CI/CD pipelines.

It provides aggregated metrics such as build success rate, execution time, and pipeline stability to help DevOps teams improve delivery performance.

---

## Features (MVP)

- Retrieve CI/CD pipeline data
- Compute success rate
- Analyze build durations
- Expose REST endpoints for monitoring

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- H2 (dev database)

---

##  Getting Started

### Run the application

```bash
./mvnw spring-boot:run
```

### Access H2 Console

```bash
http://localhost:8080/h2-console
```

## API (coming soon)

/api/health

## Roadmap
- Add PostgreSQL support
- Dockerize application
- Integrate GitLab/GitHub APIs
- Add monitoring metrics

## License

This project is under MIT LICENSE