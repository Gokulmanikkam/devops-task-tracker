# DevOps Task Tracker

A small Java REST API built with Spring Boot for practicing a DevOps workflow: Git/GitHub, Maven, automated tests, Jenkins, Docker, and Kubernetes.

## Requirements
- JDK 21
- Maven 3.6+
- Docker (for container image build)

## Build and test
```bash
mvn clean verify
```

## Run locally
```bash
mvn spring-boot:run
```
The API listens on port 8080.

## API examples
Create a task:
```bash
curl -i -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"Learn Docker","description":"Build and run a container"}'
```

List tasks:
```bash
curl -i http://localhost:8080/api/tasks
```

Get a task:
```bash
curl -i http://localhost:8080/api/tasks/1
```

Update a task:
```bash
curl -i -X PUT http://localhost:8080/api/tasks/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Learn Docker","description":"Image built","status":"IN_PROGRESS"}'
```

Delete a task:
```bash
curl -i -X DELETE http://localhost:8080/api/tasks/1
```

Valid statuses: `TODO`, `IN_PROGRESS`, `DONE`.

## Docker
Build the JAR first, then build and run the image:
```bash
mvn clean verify
docker build -t devops-task-tracker:1.0.0 .
docker run --rm -p 8080:8080 devops-task-tracker:1.0.0
```

## Kubernetes
Build the image in the same Docker environment used by your local Kubernetes cluster, then apply:
```bash
kubectl apply -f k8s/deployment.yaml
kubectl get pods
kubectl get services
```
The manifest expects the image `devops-task-tracker:1.0.0` to be available to the cluster. Image-loading steps vary by cluster.

## Notes
- This learning version stores tasks in memory; data is lost when the app restarts.
- Do not expose port 8080 publicly unless you have intentionally configured access controls.
- Jenkinsfile assumes Jenkins has JDK tool name `jdk21`, Maven tool name `maven3`, and permission to run Docker commands. Configure those in Jenkins before running the pipeline.
