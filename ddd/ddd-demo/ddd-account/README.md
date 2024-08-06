# 项目结构
在 Java 中，领域模型的包结构通常遵循以下原则：

领域层（Domain Layer）：这是应用程序的核心层，包含了业务逻辑和领域模型。
entities：存放实体类。
valueObjects：存放值对象类。
aggregates：存放聚合类，通常包含聚合根。
repositories：存放数据访问接口（Repository 接口）。
services：存放业务服务类，处理复杂的业务逻辑。
基础设施层（Infrastructure Layer）：包含了与外部系统交互的部分，如数据库访问实现、网络通信等。
dataAccess：存放 Repository 接口的具体实现。
mappers：存放领域模型和数据传输对象（DTOs）之间的转换器。
应用层（Application Layer）：处理应用级别的逻辑，如使用领域服务来组合多个领域操作。
commands：存放命令对象，代表客户端请求。
queries：存放查询对象，代表客户端查询请求。
services：存放应用服务类，通常调用领域服务。
接口适配器层（Adapter Layer）：提供了与外部系统交互的适配器。
restControllers：RESTful API 控制器。
webControllers：Web 控制器，用于处理 Web 请求。
cliControllers：命令行界面控制器。
测试层（Test Layer）：存放各种类型的测试代码，如单元测试、集成测试等。

src/main/java
└── com.example.application
├── domain
│   ├── entities
│   │   └── User.java
│   ├── valueObjects
│   │   └── Address.java
│   ├── aggregates
│   │   └── Order.java
│   ├── repositories
│   │   └── UserRepository.java
│   ├── services
│   │   └── UserService.java
│   └── OrderService.java
├── infrastructure
│   ├── dataAccess
│   │   └── JpaUserRepository.java
│   └── mappers
│       └── UserMapper.java
├── application
│   ├── commands
│   │   └── CreateUserCommand.java
│   ├── queries
│   │   └── GetUserQuery.java
│   └── services
│       └── ApplicationUserService.java
└── adapters
├── restControllers
│   └── UserController.java
└── cliControllers
└── UserCLIController.java

src/test/java
└── com.example.application
├── domain
│   ├── entities
│   │   └── UserTest.java
│   ├── valueObjects
│   │   └── AddressTest.java
│   ├── aggregates
│   │   └── OrderTest.java
│   ├── repositories
│   │   └── UserRepositoryTest.java
│   ├── services
│   │   └── UserServiceTest.java
│   └── OrderServiceTest.java
└── application
├── commands
│   └── CreateUserCommandTest.java
├── queries
│   └── GetUserQueryTest.java
└── services
└── ApplicationUserServiceTest.java