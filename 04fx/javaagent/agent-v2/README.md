# nft-dc-server-manager 服务治理

## 概念
### 1. 服务治理需要考虑的各方面
  1. 支持主机监控、容器监控、JVM监控 和 业务监控。支持实时日志查看，日志采集分析。
  2. 支持调用链分析、异常总览、慢 SQL分析、应用拓扑大屏和自定义方法堆栈跟踪等功能， 基于探针技术，完全无侵入代码。
  3. 提供应用诊断功能，支持 GC 诊断、类冲突、类加载分析、对象内存分布、本地方法耗时追踪、热点线程堆栈快照、数据库连接池分析、WEB 服务连接池分析。
  4. 应用运行期检查功能，提供改进报告和一键优化方案。
  5. 支持自定义报警规则，监控信息自动对接报警
  
  
### 2. 治理：logging（日志）、tracing（跟踪）metrics（统计）。
  Logging，Metrics 和 Tracing 有各自专注的部分。
  1. Logging - 日志系统，用于收集和上报分散在各服务应用的日志事件，分析日志。例如，应用程序的调试信息或错误信息，业务发生的信息。是诊断问题的依据。
  2. Metrics - 用于记录可聚合的数据。例如，每一个服务的请求量，最大最小值，平均值，最长调用时间，平均调用时间等。
  3. Tracing - 用于针对每人个请求的跟踪需求。例如，一次远程方法调用的执行过程和耗时。排查系统性能问题的利器。
  
## 测试

D:\workspace\idea-tech\gold\gold-server\gold-apm-agent\target\glod-apm-agent.jar

java -javaagent:D:/workspace/idea-tech/gold/gold-server/gold-apm-agent/target/glod-apm-agent.jar com.mengxiu.gold.MainTest

java com.mengxiu.gold.MainTest
