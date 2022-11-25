# bookstore-api

这里是书店项目的后端代码

### 技术栈

- Kotlin
- Spring Boot 3.0
- Gradle
- PostgreSQL
- Redis
- Spring Security
- Spring Data JPA
- Spring Validation

### 项目分层

- Bean  
  定义的数据库实体类，以及作为与前端交互的参数和返回值的 DTO 和 VO
- Common  
  定义项目的业务异常和错误码，包装返回对象，以及常数定义类
- Config  
  配置 Spring Security 认证鉴权以及配置使用 Redis 作为缓存
- Repository  
  定义数据库访问接口，完成对数据的增删改查操作
- Service  
  定义各项服务类，完成业务流程
- Controller  
  向前端提供 API，以及全局异常捕获处理