# Portfolio
Personal Portfolio Project using Java & Spring Boot for Pondits (Batch 7)

## Learning Checklist
- [x] Java 17
- [ ] Core Concepts
    - [x] Servlet
    - [x] Convention over Configuration
    - [x] Inversion of Control (IoC)
    - [x] IoC Container
    - [x] Dependency Injection
    - [x] Application Context
    - [x] Bean
- [x] Server-Side (using Template Engine)
    - [x] / (Portfolio Homepage)
    - [x] Practice Thymeleaf Tags (e.g. th:if, th:each, etc.)
- [ ] REST API
    - [X] /api/projects (CRUD for Projects)
    - [ ] Secure Endpoints
    - [ ] Input Validation
    - [x] API Documentation: Swagger
    - [ ] API Paging and Sorting
- [x] application.properties
  - [x] @ConfigurationProperties
  - [x] @Value
  - [x] yaml/yml format
- [ ] Exception
  - [x] Custom Exception Class
  - [ ] Global Exception Handler
- [x] Logging
  - [x] Framework
    - [x] Logback
    - [x] Facade: SLF4J
  - [x] Logging Levels
  - [x] Configure Logging using application.properties
  - [ ] Configure Logback using XML
- [ ] External Service Call
- [ ] Boilerplate Code Reduce
  - [ ] Mapper for Mapping
  - [ ] Lombok for Generating Getters and Setters
- [ ] Spring Security
  - [ ] Basic Auth
  - [ ] JWT
- [ ] Database
  - [x] H2 (In-Memory Database)
  - [ ] PostgreSQL
- [x] Spring Profiles (local, dev, prod)
- [ ] Deployment
  - [x] Platform as-a Service (PaaS): [Render](https://portfolio-6nv7.onrender.com/)
  - [ ] Infrastructure as-a-Service (IaaS): Virtual Machine

## How to Build & Run
1. Install Java 17 (LTS) of any distribution. You can try Amazon Corretto 17. You can find the installation instructions here: https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/what-is-corretto-17.html
2. Open this project with your preferred IDE. We prefer IntelliJ IDEA Ultimate Edition. Other IDEs like VS Code or Spring Tools Suite (STS) will do.
3. To build the project execute the following command:
    ```
    ./mvnw clean package
    ```
   Executing that command will generate a jar under "target" folder which you can run using the following command:
    ```
    java -jar portfolio-0.0.1-SNAPSHOT.jar
    ```
   or, You can run the app directly by following the command:
    ```
    ./mvnw spring-boot:run
    ```
   or, you can run via your preferred IDE's RUN/PLAY button.
4. After running the application, it will be up & running at http://localhost:8081
5. You can also access the Swagger API Doc at: http://localhost:8081/swagger-ui/index.html# . You can go ahead and execute the Organization's REST APIs I prepared for you guys to play with.