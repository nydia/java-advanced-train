# 项目结构
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
│   ├── entity 实体
│   │   └── OrderItem.java
│   ├── valueObject 值对象
│   │   └── PaymentMethod.java
│   ├── aggregate 聚合根
│   │   └── Order.java
│   ├── repository
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


假设我们有一个在线商店的领域模型，其中包含以下概念：

Customer（客户） - 实体
Order（订单） - 聚合根
OrderItem（订单项） - 实体
ShippingAddress（配送地址） - 值对象
PaymentMethod（支付方式） - 值对象
在这种情况下，Order 是聚合根，它包含了 OrderItem 实体和 ShippingAddress、PaymentMethod 值对象。Customer 实体可以包含 ShippingAddress 值对象来描述客户的送货地址。