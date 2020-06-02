# Spring Cloud Demo


### 一、本项目包含

#### 1. 服务注册与发现

 - 集成Nacos作为服务注册中心
 - 集成Nacos 配置中心
 - 集成Feign、Ribbon
 - Hystrix熔断、降级

#### 2. 日志集成 ELK

#### 3. 通用组件封装

  包含：
  - 缓存Redis
  - Codis集群
  - Redisson分布式锁(单机、集群)
  - 消息中间件RocketMQ
  - 日期工具类
  - Http请求通用工具类

#### 4. Rest-API 接口通用封装

- rest-api返回结果对象 RestApiResult
- 返回结果通用解析接口 ResultInterface
- 返回码定义枚举 RequestResultEnum

#### 5. 多数据源切换、读写分离

通过注解 @DataSourceAnnotation，可以在接口实现类中标注使用哪一个数据源,切换数据源。
例如：
````
@Override
@DataSourceAnnotation(dbName = DBName.DEMO_R)
public String getUserByid(Integer uid) {
    return userMapper.getUserByid(uid);
}
````

### 二、ELK本地搭建与配置

#### 1. brew install elasticsearch
- $ elasticsearch --version 查看是否安装成功

- 启动ES：brew services start elasticsearch
- 停止ES：brew services stop elasticsearch

#### 2. brew install logstash

新建 config/logstash.conf
````
input {
    tcp {
        mode => "server"
        host => "localhost"
        port => 4560
        codec => json_lines
    }
}
output{
  elasticsearch {
     action => "index"
     hosts => ["localhost:9200"]
     index => "%{[project]}-%{+YYYY.MM.dd}" #用一个项目名称来做索引
  }
  stdout { codec => rubydebug }
}
````
启动：./bin/logstash -f config/logstash.conf

访问：http://localhost:9600


#### 3. brew install kibana

访问：http://localhost:5601

#### 4. 启动spring-cloud-demo项目

启动成功，就可以在kibana后台查看到项目日志。
