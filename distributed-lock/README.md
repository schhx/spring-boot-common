## 分布式锁  distributed-lock

默认使用redis实现，可通过实现LockOperation做自定义实现。

### 使用方法

1. 下载本项目，然后编译项目

```
git clone git@github.com:schhx/spring-boot-common.git
cd spring-boot-common
mvn clean install
```

2. 引入依赖

```
<dependency>
    <groupId>org.schhx.spring-boot-common</groupId>
    <artifactId>distributed-lock</artifactId>
    <version>1.0.0</version>
</dependency>
```

3. 添加Redis配置

```
spring:
  redis:
    host: localhost
    port: 6379
    password:
    database: 0
```

4. 添加注解

在需要加锁的方法上注解```@DistributedLock```，例如：```@DistributedLock(prefix = "lock-test", key = "#id")```


