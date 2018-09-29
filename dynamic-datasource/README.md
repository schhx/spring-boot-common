## 主从数据源  dynamic-datasource

Spring Boot 项目中使用主从数据库有两种方法：

- 一种是使用多数据源，然后在不同的包下面使用不同的数据源；
- 另外一种就是使用动态数据源，在运行时动态指定数据源

第一种方法更适合多个不同的数据库表的情况，而主从数据库更适合使用动态指定的方式，因为主从数据库虽然是两个（或多个）不同的数据源，
但是它们的数据库表却是相同的，多数据源的方式会有很多代码重复，对调用方也很不友好。

本项目适用一个主库一个从库的情况，通过在服务层添加注解的方式动态指定使用主库还是从库，对于服务层来说和使用一个数据源几乎没有区别。

本项目可以与JdbcTemplate、MyBatis、JPA整合。

### 使用方法

1. 下载本项目，然后编译项目

```
git clone git@github.com:schhx/spring-boot-common.git
cd spring-boot-common
mvn clean install
```

2. 在其他项目pom.xml文件中直接引入即可

```
<dependency>
    <groupId>org.schhx.spring-boot-common</groupId>
    <artifactId>dynamic-datasource</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

```

3. 在启动类上禁止数据源自动配置

```
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
```

4. 默认使用主库，如果需要使用从库，只需要在方法上添加注解即可

```
    @UseSlave
    public User getById(String id) {
        return userRepository.findById(id).get();
    }
```

### 注意事项

1. 如果添加```@UseSlave```方法的调用方添加了```@Transactional```注解，那么不会使用从库


