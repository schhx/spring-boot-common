## 一主多从数据源  dynamic-datasource-multi-slave

本项目适用一个主库多个从库的情况([一主一从版本](https://github.com/schhx/spring-boot-common/tree/1.x/dynamic-datasource))，通过在服务层添加注解的方式动态指定使用主库还是从库，对于服务层来说和使用一个数据源几乎没有区别。

本项目可以与JdbcTemplate、MyBatis、JPA整合。

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
    <artifactId>dynamic-datasource-multi-slave</artifactId>
    <version>1.5.16-SNAPSHOT</version>
</dependency>
```

3. 添加数据库配置

```
spring:
  datasource:
    master:
      url: jdbc:mysql://127.0.0.1:3306/spring_boot_learn?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&autoReconnect=true&useSSL=true
      driver-class-name: com.mysql.jdbc.Driver
      username: root
      password: root
      initialSize : 3
      maxActive: 15
      maxIdle: 15
      minIdle: 3
      test-while-idle: true
      validation-query: select 1 from dual
      validation-interval: 60000
      time-between-eviction-runs-millis: 60000
    slave:
      url: jdbc:mysql://127.0.0.1:3306/spring_boot_learn_2?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&autoReconnect=true&useSSL=true
      driver-class-name: com.mysql.jdbc.Driver
      username: root
      password: root
      initialSize : 3
      maxActive: 15
      maxIdle: 15
      minIdle: 3
      test-while-idle: true
      validation-query: select 1 from dual
      validation-interval: 60000
      time-between-eviction-runs-millis: 60000
    slave2:
      url: jdbc:mysql://127.0.0.1:3306/spring_boot_learn_3?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&autoReconnect=true&useSSL=true
      driver-class-name: com.mysql.jdbc.Driver
      username: root
      password: root
      initialSize : 3
      maxActive: 15
      maxIdle: 15
      minIdle: 3
      test-while-idle: true
      validation-query: select 1 from dual
      validation-interval: 60000
      time-between-eviction-runs-millis: 60000
```

4. 默认使用主库，如果需要使用从库，只需要在方法上添加注解即可

```
@UseSlave
public User getById(String id) {
    return userRepository.findById(id).get();
}
```

5. 存在多个从库时，每次请求会选取一个从库，默认提供的实现是[RoundDataSourceSelector](https://github.com/schhx/spring-boot-common/blob/1.x/dynamic-datasource-multi-slave/src/main/java/org/schhx/springbootcommon/dynamicdatasource/selector/RoundDataSourceSelector.java)，用户可以自定义具体的实现。继承 [AbstractDataSourceSelector](https://github.com/schhx/spring-boot-common/blob/1.x/dynamic-datasource-multi-slave/src/main/java/org/schhx/springbootcommon/dynamicdatasource/selector/AbstractDataSourceSelector.java)，并声明为Bean。

```
@Component
public class MyDataSourceSelector extends AbstractDataSourceSelector {
    @Override
    protected String getSlaveDataSourceKey() {
        return "xxx";
    }
}
```

### 注意事项

1. 如果某个方法注解了```@UseSlave```，正常来说会走从库，但是如果这个方法的上层调用方添加了```@Transactional```注解，
那么不会使用从库，所以```@UseSlave```尽量添加在上层方法上，至少要与```@Transactional```注解平级。


