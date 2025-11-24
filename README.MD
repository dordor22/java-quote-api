# 🚀 Quote API — DevOps Assignment (Java + Docker + CI/CD + Terraform)

This repository contains a small Java Spring Boot application that exposes a simple "Quote of the Day" API, wrapped with a complete DevOps pipeline including Docker, GitHub Actions CI/CD, and AWS deployment using Terraform.  
The project was built step-by-step as part of a home assignment, with each part implemented gradually and intentionally—not auto-generated—to simulate real-world workflow and commit history.

---

## 📌 Application Overview

The application is a minimal **Spring Boot** REST API:

- `GET /health` → returns service status  
- `GET /api/quote` → returns a random quote from a predefined list  

It is intentionally lightweight to keep the focus on DevOps, CI/CD and infrastructure—not the business logic.

---

## 🛠️ Technologies Used

### 🔹 Application
- **Java 17**
- **Spring Boot 3**
- Maven (build & tests)

### 🔹 Containerization
- Docker multi-stage build (Maven → Alpine JRE runtime)
- Images pushed to **Docker Hub**

### 🔹 CI/CD
- **GitHub Actions**  
  - CI workflow: Build, run tests, build Docker image, push to Docker Hub  
  - CD workflow: Run Terraform to deploy latest version to AWS

### 🔹 Infrastructure (IaC)
- **Terraform**
- AWS EC2 (Ubuntu 22.04)
- Security Group exposing port **8080**
- `user_data` installs Docker and runs the application container automatically

---

## 📁 Repository Structure

```
java-quote-api/
│
├── Dockerfile
├── .dockerignore
├── pom.xml
├── README.md
│
├── src/
│   ├── main/java/com/dor/quoteapi/...
│   └── test/java/com/dor/quoteapi/...
│
├── .github/
│   └── workflows/
│       ├── ci.yml       # build + test + docker push
│       └── cd.yml       # terraform apply (deployment)
│
└── terraform/
    ├── main.tf
    ├── variables.tf
    ├── outputs.tf
    ├── terraform.tfvars.example
    └── (local terraform.tfvars not committed)
```

---

## ▶️ Running Locally

### 1. Build & test
```
mvn clean test package
```

### 2. Run locally
```
mvn spring-boot:run
```

Endpoints:
```
http://localhost:8080/health
http://localhost:8080/api/quote
```

---

## 🐳 Running with Docker

### Build:
```
docker build -t quote-api:local .
```

### Run:
```
docker run -p 8080:8080 quote-api:local
```

---

## 🔄 CI — Continuous Integration (GitHub Actions)

Every push to `main` triggers CI:

1. Checkout code  
2. Build with Maven  
3. Run unit tests  
4. Docker build (multi-stage)  
5. Push image to Docker Hub  
   - `latest`  
   - `<commit-sha>`

Secrets required:
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

---

## 🚀 CD — Continuous Deployment (Terraform on AWS)

When CI completes successfully, the **CD workflow** runs:

- Initializes Terraform
- Applies the full infrastructure stack
- Creates EC2 instance
- Installs Docker
- Pulls the latest Docker Hub image
- Runs the application on port **8080**

Secrets required:
- `AWS_ACCESS_KEY_ID`  
- `AWS_SECRET_ACCESS_KEY`  
- `AWS_REGION`  
- `EC2_KEYPAIR_NAME`  
- `EC2_SSH_CIDR`

---

## 🌩️ Terraform — Infrastructure Breakdown

### Terraform provisions:
- EC2 instance (Ubuntu 22.04)
- Security Group exposing port 8080
- Docker installation via `user_data`
- Automatic container startup on boot:
```
docker pull <image>
docker run -d --restart unless-stopped -p 8080:8080 <image>
```

### Outputs:
Terraform prints:
- Public IP  
- Direct application URL

Example:
```
app_url = "http://<public-ip>:8080/api/quote"
public_ip = "<public-ip>"
```

---

## ❗Note About Terraform State (Real-World Detail)

This project **does not use a remote backend** (S3 + DynamoDB).  
As a result:

- Running `terraform apply` locally creates **local state**  
- Running Terraform inside GitHub Actions uses a **separate ephemeral state**  
- After running `tf destroy` locally, AWS resources created by the CI/CD pipeline **are not tracked** locally  
  (because the GitHub Actions runner had its own temporary state)

Therefore, after completing the assignment, the EC2 instance was removed **manually** through the AWS Console to avoid unnecessary costs.

This behavior is completely normal when not using `backend "s3"`.

---

## 🧹 Cleaning Up (Avoiding AWS Costs)

To delete all resources created through Terraform:

```
terraform destroy
```

If the CD workflow created an instance that Terraform local state does not recognize,  
it can be safely removed manually from the AWS EC2 console.

---

## ✔️ Summary

This project demonstrates a full DevOps workflow:

- Java application  
- Docker multi-stage build  
- Automated CI with tests & Docker push  
- Automated CD deploying to AWS EC2  
- Infrastructure managed with Terraform  
- Real-world behavior around Terraform state  
- Clean and incremental commits, mirroring how real engineers work  

Feel free to clone, test, or extend this project.


