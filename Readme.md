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
    - [x] DispatcherServlet
    - [ ] Interceptor 
    - [ ] Filter
- [x] Server-Side (using Template Engine)
    - [x] / (Portfolio Homepage)
    - [x] Practice Thymeleaf Tags (e.g. th:if, th:each, etc.)
- [ ] REST API
    - [X] /api/projects (CRUD for Projects)
    - [ ] Secure Endpoints
    - [ ] Input Validation
    - [x] API Documentation: Swagger
    - [x] API Paging and Sorting
- [x] application.properties
  - [x] @ConfigurationProperties
  - [x] @Value
  - [x] yaml/yml format
- [x] Exception
  - [x] Custom Exception Class
  - [x] Global Exception Handler
    - [x] @RestControllerAdvice
    - [x] Problem Details RFC 7807
    - [x] @ExceptionHandler
    - [x] @ControllerAdvice
- [x] Logging
  - [x] Framework
    - [x] Logback
    - [x] Facade: SLF4J
  - [x] Logging Levels
  - [x] Configure Logging using application.properties
  - [ ] Configure Logback using XML
- [ ] External Service Call
  - [ ] HTTP Client
  - [ ] RestTemplate
  - [ ] WebClient
  - [ ] Feign Client
- [ ] Boilerplate Code Reduce
  - [x] Mapper for Mapping
    - [x] Native from Spring: BeanUtils
    - [ ] 3rd Party Libraries
      - [ ] MapStruct
      - [ ] ModelMapper
  - [x] Lombok for Generating Getters and Setters
- [ ] Spring Security
  - [ ] Authentication
    - [ ] Form-Based Authentication
    - [ ] Basic Authentication
    - [ ] JWT (JSON Web Token)
    - [ ] OAuth2
  - [ ] Authorization
    - [ ] Role-Based Access Control (RBAC)
  - [ ] CSRF Protection
  - [ ] CORS
  - [ ] Session Management
  - [ ] Password Encoding
- [ ] Database
    - [ ] Database System
      - [x] H2 (In-Memory Database, Embedded)
      - [ ] PostgreSQL
    - [ ] DB Initialization & Migration
      - [ ] Spring Boot Database Initialization
        - [ ] Schema.sql
        - [ ] Data.sql
        - [x] hibernate.hbm2ddl.auto
      - [ ] DB Migration
        - [ ] Flyway
        - [ ] Liquibase
- [ ] ORM
  - [ ] Hibernate
  - [ ] JPA (Java Persistence API)
- [ ] Spring Data
  - [ ] Spring Data JPA
  - [ ] Spring Data JDBC
  - [ ] Spring Data MongoDB
  - [ ] Spring Data Redis
- [x] Spring Profiles (local, dev, prod)
- [ ] Spring Data JPA
  - [x] @Entity
  - [x] @Table
  - [x] @Id
  - [x] @GeneratedValue
  - [x] @Column
  - [ ] @OneToMany
  - [ ] @ManyToOne
  - [ ] @ManyToMany
  - [ ] @Transactional
- [ ] Spring Data JPA Query
  - [ ] @Query
  - [ ] Native Query
  - [ ] Named Query
  - [ ] Criteria API
  - [ ] Spring Data JPA Specification
- [ ] Spring Actuator
  - [ ] Health Check
  - [ ] Metrics
  - [ ] Info
  - [ ] Custom Endpoints
- [ ] Deployment
  - [x] Platform as-a Service (PaaS): [Render](https://portfolio-6nv7.onrender.com/)
  - [ ] Infrastructure as-a-Service (IaaS): Virtual Machine
- [ ] Good/Best Practices
  - [ ] Code Quality
    - [ ] SonarQube
    - [ ] SonarCloud
  - [x] Code Refactoring
  - [x] Code Review
  - [x] Unit Testing
    - [x] JUnit 5
    - [x] Mockito
  - [ ] Integration Testing
    - [ ] Spring Test
    - [ ] Testcontainers

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
