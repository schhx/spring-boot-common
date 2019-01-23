## 全局异常处理  exception-handler

spring-boot-web项目必不可少的组件就是全局异常处理，而不同项目关于异常处理的代码几乎是一样的，本模块总结了一些通用的情况，可以直接使用，减少编写重复的代码。

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
    <artifactId>exception-handler</artifactId>
    <version>1.0.0</version>
</dependency>

```
