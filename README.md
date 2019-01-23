## spring boot应用的常用组件  spring-boot-common

> 适用于 Spring Boot 1.x 版本，[2.x 版本传送门](https://github.com/schhx/spring-boot-common)

虽然spring boot大大简化了我们开发项目的过程，但是不同项目之间还存在大量雷同的代码，比如异常处理、日志记录等，本项目希望提供一些通用的模块，利用这些模块能够更加快速地搭建应用。

### 模块简介

模块 | 说明
---|---
[exception-handler](https://github.com/schhx/spring-boot-common/tree/1.x/exception-handler)  | 提供了常用的异常处理
[dynamic-datasource](https://github.com/schhx/spring-boot-common/tree/1.x/dynamic-datasource)  | 动态切换数据源(一主一从)
[dynamic-datasource-multi-slave](https://github.com/schhx/spring-boot-common/tree/1.x/dynamic-datasource-multi-slave)  | 动态切换数据源(一主多从)
[distributed-lock](https://github.com/schhx/spring-boot-common/tree/1.x/distributed-lock)  | 分布式锁


