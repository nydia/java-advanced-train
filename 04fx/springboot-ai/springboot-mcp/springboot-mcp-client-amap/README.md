# 项目文档

## 1. 项目概述
Spring Boot AI应用，集成高德地图MCP服务和阿里云Dashscope平台AI能力

## 2. 核心功能
- 通过REST API与AI模型交互
- 支持自然语言处理请求
- 集成高德地图服务

## 3. 架构设计
- **前端**: REST API接口
- **业务层**: Spring Boot应用
    - Controller层处理HTTP请求
    - Service层(待扩展业务逻辑)
- **基础设施**:
    - Spring AI框架
    - 阿里云Dashscope平台
    - 高德地图MCP服务

## 4. 快速开始
### 启动服务
```bash
sudo java -jar mcp-0.0.1-SNAPSHOT.jar
```

### API调用示例
```bash
http://localhost:8080/tools?prompt=昆明今天天气
```
其他提示词
http://localhost:8080/tools?prompt=北京3天旅行攻略

## 5. 技术栈
- Spring Boot 3.x
- Spring AI
- 阿里云Dashscope平台
- 高德地图MCP服务

## 6. 配置说明
- 阿里云API密钥配置
- 高德地图API密钥配置
- AI模型配置(qwen-max)