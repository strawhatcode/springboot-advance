# Spring Cloud

## 一、nacos配置中心、注册中心

### 1.文档及下载

* [nacos官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)
* [下载地址](https://github.com/alibaba/nacos/releases)

### 2.win10配置nacos server

需要的环境

> 1. 64 bit OS，支持 Linux/Unix/Mac/Windows，推荐选用 Linux/Unix/Mac。
> 2. 64 bit JDK 1.8+；[下载](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) & [配置](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/)。
> 3. Maven 3.2.x+；[下载](https://maven.apache.org/download.cgi) & [配置](https://maven.apache.org/settings.html)。

#### 2.1--单机：

* 解压nacos压缩包后进入**nacos-server-1.3.1\nacos\bin**目录点击**startup.cmd**运行

  启动成功：

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\1.png)

* 通过**127.0.0.1:8848/nacos**（默认这个路径）访问管理界面，帐号密码都是nacos

#### 2.2--集群：

* 持久化处理

  创建一个数据库，然后导入**nacos-server-1.3.1\nacos8848\conf**目录下的**nacos-mysql.sql**数据库文件，这样就会创建nacos所需要的表。

  创建完后的表：

  ![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\3.png)

* 把nacos复制几份

  ![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\2.png)

* 进入**nacos-server-1.3.1\nacos8848\conf**文件夹下，**cluster.conf.example**把这个文件的命名改成**cluster.conf**，（这里可以复制一份再改名字，那就可以保留example文件），在这个文件里添加nacos集群的服务器ip与端口

  * cluster.conf.example的内容

  ```
  #
  # Copyright 1999-2018 Alibaba Group Holding Ltd.
  #
  # Licensed under the Apache License, Version 2.0 (the "License");
  # you may not use this file except in compliance with the License.
  # You may obtain a copy of the License at
  #
  #      http://www.apache.org/licenses/LICENSE-2.0
  #
  # Unless required by applicable law or agreed to in writing, software
  # distributed under the License is distributed on an "AS IS" BASIS,
  # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  # See the License for the specific language governing permissions and
  # limitations under the License.
  #
  
  #it is ip
  #example
  192.168.16.101:8847
  192.168.16.102
  192.168.16.103
  
  ```

  * 只要把里面的ip改成集群的服务器ip与端口，例：

  ```
  #
  # Copyright 1999-2018 Alibaba Group Holding Ltd.
  #
  # Licensed under the Apache License, Version 2.0 (the "License");
  # you may not use this file except in compliance with the License.
  # You may obtain a copy of the License at
  #
  #      http://www.apache.org/licenses/LICENSE-2.0
  #
  # Unless required by applicable law or agreed to in writing, software
  # distributed under the License is distributed on an "AS IS" BASIS,
  # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  # See the License for the specific language governing permissions and
  # limitations under the License.
  #
  
  #it is ip
  #example
  127.0.0.1:8848
  127.0.0.1:8849
  127.0.0.1:8850
  
  ```

  * 同样在**conf**目录下，修改**application.properties**配置文件

    修改的内容如下：

    ```properties
    server.port=8848
    
    spring.datasource.platform=mysql
    
    ### Count of DB:
    db.num=1
    
    ### Connect URL of DB:
    db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
    db.user=root
    db.password=123456
    ```

  * 对nacos8849与nacos8850也做同样的配置，如果单机搭建集群的话端口号要不一样

* 启动集群

  到每个集群的bin目录下打开cmd，输入命令：**startup.cmd -m cluster**启动。

  注意：不能双击**startup.cmd**启动nacos，因为双击启动的话默认会进入**standalone(单机)**模式

* 通过127.0.0.1:8848/nacos登录进管理页面，可以看到集群的信息

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\4.png)

### 3.linux下使用docker配置nacos

使用docker下载nacos。

下载命令：**`docker pull nacos/nacos-server`**。该命令默认下载最新版本。

使用**docker images**命令可以查看下载的内容。

通用属性配置：

| 属性名称                          | 描述                                                         | 选项                                                         |
| --------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| MODE                              | 系统启动方式: 集群/单机                                      | cluster/standalone默认 **cluster**                           |
| NACOS_SERVERS                     | nacos cluster address                                        | p1:port1空格ip2:port2 空格ip3:port3                          |
| PREFER_HOST_MODE                  | 支持IP还是域名模式                                           | hostname/ip 默认 **ip**                                      |
| NACOS_SERVER_PORT                 | Nacos 运行端口                                               | 默认 **8848**                                                |
| NACOS_SERVER_IP                   | 多网卡模式下可以指定IP                                       |                                                              |
| SPRING_DATASOURCE_PLATFORM        | standalone support mysql                                     | mysql / 空 默认:空                                           |
| MYSQL_SERVICE_HOST                | mysql地址                                                    |                                                              |
| MYSQL_SERVICE_PORT                | mysql端口号                                                  | 默认 : **3306**                                              |
| MYSQL_SERVICE_DB_NAME             | mysql数据库名称                                              |                                                              |
| MYSQL_SERVICE_USER                | 数据库用户名                                                 |                                                              |
| MYSQL_SERVICE_PASSWORD            | 数据库密码                                                   |                                                              |
| ~~MYSQL_MASTER_SERVICE_HOST~~     | **latest(目前latest 是1.1.4)以后**版本镜像移除, 使用 MYSQL_SERVICE_HOST |                                                              |
| ~~MYSQL_MASTER_SERVICE_PORT~~     | **latest(目前latest 是1.1.4)以后**版本镜像移除, 使用 using MYSQL_SERVICE_PORT | 默认 : **3306**                                              |
| ~~MYSQL_MASTER_SERVICE_DB_NAME~~  | **latest(目前latest 是1.1.4)以后**版本镜像移除, 使用 MYSQL_SERVICE_DB_NAME |                                                              |
| ~~MYSQL_MASTER_SERVICE_USER~~     | **latest(目前latest 是1.1.4)以后**版本镜像移除, 使用 MYSQL_SERVICE_USER |                                                              |
| ~~MYSQL_MASTER_SERVICE_PASSWORD~~ | **latest(目前latest 是1.1.4)以后**版本镜像移除, 使用, using MYSQL_SERVICE_PASSWORD |                                                              |
| ~~MYSQL_SLAVE_SERVICE_HOST~~      | **latest(目前latest 是1.1.4)以后**版本镜像移除               |                                                              |
| ~~MYSQL_SLAVE_SERVICE_PORT~~      | **latest(目前latest 是1.1.4)以后**版本镜像移除               | 默认 :3306                                                   |
| MYSQL_DATABASE_NUM                | It indicates the number of database                          | 默认 :**1**                                                  |
| JVM_XMS                           | -Xms                                                         | 默认 :2g                                                     |
| JVM_XMX                           | -Xmx                                                         | 默认 :2g                                                     |
| JVM_XMN                           | -Xmn                                                         | 默认 :1g                                                     |
| JVM_MS                            | -XX:MetaspaceSize                                            | 默认 :128m                                                   |
| JVM_MMS                           | -XX:MaxMetaspaceSize                                         | 默认 :320m                                                   |
| NACOS_DEBUG                       | enable remote debug                                          | y/n 默认 :n                                                  |
| TOMCAT_ACCESSLOG_ENABLED          | server.tomcat.accesslog.enabled                              | 默认 :false                                                  |
| NACOS_AUTH_SYSTEM_TYPE            | 权限系统类型选择,目前只支持nacos类型                         | 默认 :nacos                                                  |
| NACOS_AUTH_ENABLE                 | 是否开启权限系统                                             | 默认 :false                                                  |
| NACOS_AUTH_TOKEN_EXPIRE_SECONDS   | token 失效时间                                               | 默认 :18000                                                  |
| NACOS_AUTH_TOKEN                  | token                                                        | 默认 :SecretKey012345678901234567890123456789012345678901234567890123456789 |
| NACOS_AUTH_CACHE_ENABLE           | 权限缓存开关 ,开启后权限缓存的更新默认有15秒的延迟           | 默认 : false                                                 |

#### 3.1--单机：

启动命令：

```
docker run 
--name nacos-standalone 
-e MODE=standalone 
-p 8848:8848 \
-e SPRING_DATASOURCE_PLATFORM= \
-e MYSQL_SERVICE_HOST= \
-e MYSQL_SERVICE_PORT= \
-e MYSQL_SERVICE_DB_NAME= \
-e MYSQL_SERVICE_USER= \
-e MYSQL_SERVICE_PASSWORD= \
-d nacos/nacos-server:latest
```

#### 3.2--集群：

部署nacos集群---docker需要使用**docker-compose**来部署

需要安装**docker-compose**

下载**[nacos-docker](https://github.com/nacos-group/nacos-docker)**

直接使用git命令克隆下来**git clone https://github.com/nacos-group/nacos-docker.git**

进入**nacos-docker/example/**目录，编写一个yml文件，比如：**nacos-cluster.yml**。

可以参考**example**目录下的**cluster-hostname.yml（根据服务器域名(hostname)来集群部署）**和**cluster-ip.yml（根据服务器ip来集群部署）**。

**nacos-cluster.yml**内容如下（**根据hostname来实现集群部署**）：

```yaml
![6](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\6.png)version: "3"
services:
  nacos1:
    hostname: nacos1      # 域名
    container_name: nacos1     # 容器名字
    image: nacos/nacos-server:latest    # 镜像版本
    volumes:
      - ./cluster-logs/nacos1:/root/docker/nacos/logs/1   # 把容器内的./cluster-logs/nacos1挂在到/root/docker/nacos/logs/1
      - ./init.d/custom.properties:/root/docker/nacos/init.d/1/custom.properties #同上
    ports:
      - "8848:8848"    # 把容器内的8848映射到本地的8848端口
      - "9555:9555"
    environment:
      - MODE=cluster # 集群模式
      - NACOS_SERVERS=nacos1:8848 nacos2:8848 nacos3:8848
      - PREFER_HOST_MODE=hostname
      - SPRING_DATASOURCE_PLATFORM=mysql   # 持久化处理，下面的信息填上面创建的数据库
      - MYSQL_SERVICE_HOST=      # mysql数据库对应的ip
      - MYSQL_SERVICE_PORT=      # mysql数据库对应的端口
      - MYSQL_SERVICE_DB_NAME=   # mysql数据库库名
      - MYSQL_SERVICE_USER=      # mysql数据库用户名
      - MYSQL_SERVICE_PASSWORD=  # mysql数据库密码
      - MYSQL_DATABASE_NUM=1     # 数据源为1个
      - JVM_XMS=128m             # java虚拟机内存大小，默认是2g，根据自己服务器内存大小调整
      - JVM_XMX=128m
      - JVM_XMN=128m
    restart: on-failure          # 失败时重启
  nacos2:
    hostname: nacos2
    image: nacos/nacos-server:latest
    container_name: nacos2
    volumes:
      - ./cluster-logs/nacos2:/root/docker/nacos/logs/2
      - ./init.d/custom.properties:/root/docker/nacos/init.d/2/custom.properties
    ports:
      - "8849:8848"   # 把容器内的8848映射到本地的8849端口
    environment:
      - MODE=cluster # 集群模式
      - NACOS_SERVERS=nacos1:8848 nacos2:8848 nacos3:8848
      - PREFER_HOST_MODE=hostname
      - SPRING_DATASOURCE_PLATFORM=mysql
      - MYSQL_SERVICE_HOST=       # mysql数据库对应的ip
      - MYSQL_SERVICE_PORT=       # mysql数据库对应的端口
      - MYSQL_SERVICE_DB_NAME=
      - MYSQL_SERVICE_USER=
      - MYSQL_SERVICE_PASSWORD=
      - MYSQL_DATABASE_NUM=1      # 数据源为1个
      - JVM_XMS=128m
      - JVM_XMX=128m
      - JVM_XMN=128m
    restart: on-failure
  nacos3:
    hostname: nacos3
    image: nacos/nacos-server:latest
    container_name: nacos3
    volumes:
      - ./cluster-logs/nacos3:/root/docker/nacos/logs/3
      - ./init.d/custom.properties:/root/docker/nacos/init.d/3/custom.properties
    ports:
      - "8850:8848"
    environment:
      - MODE=cluster # 集群模式
      - NACOS_SERVERS=nacos1:8848 nacos2:8848 nacos3:8848
      - PREFER_HOST_MODE=hostname
      - SPRING_DATASOURCE_PLATFORM=mysql
      - MYSQL_SERVICE_HOST=    # mysql数据库对应的ip
      - MYSQL_SERVICE_PORT=    # mysql数据库对应的端口
      - MYSQL_SERVICE_DB_NAME=
      - MYSQL_SERVICE_USER=
      - MYSQL_SERVICE_PASSWORD=
      - MYSQL_DATABASE_NUM=1   # 数据源为1个
      - JVM_XMS=128m
      - JVM_XMX=128m
      - JVM_XMN=128m
    restart: on-failure

```

编写完成后保存该文件，在**nacos-docker/example/**目录下使用**docker-compose命令启动**nacos集群

`docker-compose -f nacos-cluster.yml up -d`

启动成功后有类似如下的提示信息

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\6.png)

由于我之前有启动过一次，所以我现在再启动时会变成**Recreating**，主要看nacos后面的**done**

启动成功后可以使用**`docker ps`**命令来查看自己启动的nacos集群(这里我服务器内存小，所以只启动了两个)

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\7.png)

然后可以在浏览器**127.0.0.1:8848/nacos（127.0.0.1:8849/nacos）**登录查看nacos的状态，**LEADER是当前的主节点，如果LEADER挂了的话会从FOLLOWER中竞选出一个新的LEADER**

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\5.png)

### 4.nacos管理界面介绍

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\8.png)

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\9.png)

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\10.png)

### 5.nacos配置中心

首先先在nacos配置中心中新增一个配置文件,填写完信息后点击发布即可

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\11.png)

发布完成后可以看到该配置文件（这里我是在**默认的命名空间（public）**下创建的）

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\12.png)

然后我们新创建一个spirngboot项目。

在pom文件中添加nacos配置中心的依赖（目前nacos只支持**springboot2.3之前的版本**）

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>2.2.1.RELEASE</version>
</dependency>
```

然后创建**bootstrap.yml**文件来读取nacos配置中心里的配置文件

```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848,127.0.0.1:8849   # nacos配置中心的地址，集群用逗号（,）分开
        # 配置文件的文件类型（与自己在配置中心起的dataId后的扩展名一致，
        # 比如test-config.yml，那就yml；如test-config.yaml，那就yaml。默认是properties）
        file-extension: yml    
#        name: test-config     # DataId  (不填该属性时默认为 ${spring.application.name} 的值)
        group: TEST_GROUP      # Group组名（默认是 DEFAULT_GROUP）
        namespace:   # 命名空间，默认是public。如果有其他的命名空间则填命名空间id（如：6c485c62-aad3-49f1-b988-697ebea81c6f）
        username: nacos   #  nacos服务器用户名，默认是nacos
        password: nacos   #  nacos服务器密码，默认是nacos
  application:
    name: test-config     # 该应用的名字，这里使用了nacos配置中心里的DataId
```

接着创建**application.yml**配置文件（可以不用）

```yaml
test:
  name: ${name}
  age: ${age}
```

写一个测试用的**controller**类，这里我用@Value注解来读取**application.yml**里的属性

```java
package com.hat.nacos_server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @Value("${test.name}")
    private String name;
    @Value("${test.age}")
    private String age;


    @GetMapping("t")
    public String t(){
        return name+age;
    }
}
```

然后启动应用，请求该接口获取到属性的值

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\13.png)

可以获取到nacos配置中心里的name与age

但是，如果现在修改配置中心里的**test-config**配置文件里的属性值，再次请求测试接口，返回的内容还是修改之前的内容。

我们把age的值修改成66

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\14.png)

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\15.png)

点击确认发布后，可以看下我们启动的程序的日志,可以看到他会把新的属性值打印出来

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\16.png)

但是我们现在再次请求测试接口,获取到的属性值并没有改变

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\17.png)

所以我们现在需要动态刷新配置文件，只需要在**testController类**上添加注解**@RefreshScope**

```java
package com.hat.nacos_server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class testController {

    @Value("${test.name}")
    private String name;
    @Value("${test.age}")
    private String age;


    @GetMapping("t")
    public String t(){
        return name+age;
    }
}
```

然后我们重启应用，再次请求测试接口，现在从配置中读取到的**age**是**66**

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\18.png)

现在再去配置中心修改**age**的值，修改成**123**

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\19.png)

修改完成后我们也可以现在应用的日志里查看改变的内容

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\20.png)

然后再次请求测试接口，可以发现age的值已经更新了

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\21.png)

**需要注意的是：**

这里**@RefreshScope**注解是用来类上面的，而且使用**@Value**注解来获取属性值的话必须用在类里面自动刷新才可以生效，否则**@Value**是不生效的，如果类上没有**@RefreshScope**注解，那么**@value**注解拿到的属性值还是项目启动时获取到的值。

我们也可以将application.yml里的属性映射到实体类中，然后统一进行刷新，这样就不用每次在代码中**使用@Value**读取属性值时都要在类里写一个**@RefreshScope**注解。

创建一个实体类**ConfigProperties**

```java
package com.hat.nacos_server.bean.configModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component    //注入到spring容器
@Setter    //lombok的set
@Getter    //lombok的get
@ConfigurationProperties(prefix = "test")   //映射application.yml配置文件里前缀是test的属性
public class ConfigProperties {
    private String name;
    private String age;
}

```

使用这些属性时只要使用**@Autowired**注解把这个实体类注入进来就可以使用，testController改成如下

```java
![22](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\22.png)![22](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\22.png)package com.hat.nacos_server.controller;

import com.hat.nacos_server.bean.configModel.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @Autowired
    private ConfigProperties properties;

    @GetMapping("t")
    public String t(){
        return properties.getName()+properties.getAge();
    }
}

```

然后我们发送请求，结果如下：

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\22.png)

然后我们修改配置中心里的配置文件

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\23.png)

再次发送请求，可以看到读取到的属性值也已经改变了

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\24.png)

### 6.nacos配置中心--扩展配置

**在一个项目中，往往不会只有一个配置文件，一般都会有多个配置文件组合起来。比如开发环境、各种工具的连接等等这些配置文件都可以区分出来。所以可以使用nacos扩展配置来读取这些配置。**

在配置中心上再创建两个配置文件**test2-config.yml**、**test3-config.yml**

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\26.png)

**test2-config.yml**，这个配置文件里面有个**test.city**属性：

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\25.png)

**test3-config.yml**，这个配置文件里也有**test.city**属性：

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\27.png)

然后修改程序的**bootstrap.yml**配置文件

```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848,127.0.0.1:8849   # nacos配置中心的地址，集群用逗号（,）分开
        # 配置文件的文件类型（与自己在配置中心起的dataId后的扩展名一致，
        # 比如test-config.yml，那就yml；如test-config.yaml，那就yaml。默认是properties）
        file-extension: yml
        name: test-config     # DataId  (不填该属性时默认为  spring.application.name 的值)
        group: TEST_GROUP      # Group组名（默认是 DEFAULT_GROUP）
        extension-configs[0]:   # 不自动刷新
          data-id: test2-config.yml   # 配置文件的名字，这里必须加上后缀
          group: DEFAULT_GROUP   # 配置文件所在的group，不填默认是DEFAULT_GROUP
        extension-configs[1]:
          data-id: test3-config.yml
          group: TEST_GROUP
          refresh: true    # 自动刷新
  application:
    name: test-config     # 该应用的名字，这里使用了nacos配置中心里的DataId（不包括后面的.yml）
```

>扩展配置的属性**extension-configs**源码里是个List列表，所以使用这个属性时可以**extension-configs[0]、extension-configs[1]、extension-configs[2]**这样命名。其中**[n]**里**n**的值越大，优先级越高。

```java
/**
* a set of extensional configurations .e.g:
* spring.cloud.nacos.config.extension-configs[0]=xxx .
*/
private List<Config> extensionConfigs;
```

在**testController**类中添加一个测试方法

```java
    @GetMapping("tt")
    public String tt(){
        return properties.getAddress()+"-"+properties.getCity()+"-"+properties.getNum() + " "+properties.getPhone();
    }
```

在**configProperties**类中添加新增的属性

```java
![28](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\28.png)package com.hat.nacos_server.bean.configModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component    //注入到spring容器
@Setter    //lombok的set
@Getter    //lombok的get
@ConfigurationProperties(prefix = "test")   //映射application.yml配置文件里前缀是test的属性
public class ConfigProperties {
    private String name;
    private String age;
    // 测试多配置文件时新增的属性
    private String address;
    private String city;
    private String phone;
    private String num;
}

```

然后启动程序。在日志中可以看到加载的配置文件的信息。

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\35.png)

调用下刚才写的测试接口。

在**test2-config.yml**配置文件里有`test.city=shenzhen`；而**test3-config.yml**有`test.city=guangzhou`。

由于在**bootstrap.yml**配置文件中我配置的**extension-configs[n]**属性中，其中**test3-config.yml**的**n**值大于

**test2-config.yml**的**n**值。因此最后读取到的**test.city**的值是**guangzhou**。

结果如下：

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\28.png)

test2-config.yml这个扩展配置文件没有配置自动刷新，所以我们在nacos配置中心修改该配置文件时，程序是不会进行自动更新的。

```yaml
extension-configs[0]:   # 不自动刷新
    data-id: test2-config.yml   # 配置文件的名字，这里必须加上后缀
    group: DEFAULT_GROUP   # 配置文件所在的group，不填默认是DEFAULT_GROUP
```

修改test2-config.yml配置文件

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\29.png)

修改完后程序的监听事件并没有监听到配置文件的改变

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\32.png)

再次请求接口，发现**没有自动刷新属性的值**。结果如下：

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\30.png)

修改test3-config.yml配置文件

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\31.png)

修改完后，程序的监听事件就会监听到配置文件的改变

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\33.png)

然后再发送请求，发现属性已经刷新了

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\34.png)

**注意：**

**即使test2-config.yml开启了自动刷新。由于在bootstrap.yml中添加扩展配置文件时n的值永远比test3-config.yml的n值小，所以test2-config.yml怎么改变test.city的值都不会有效，读取到的永远是test3-config.yml中test.city的值**

### 7.nacos配置中心--共享配置

**在项目中，有些配置属性可能会被多套环境使用，即多个环境使用一个配置属性，那么我们可以使用共享配置来读取配置文件。**

>我们可以使用**shared-configs[n]**属性来配置共享配置，用法与**extension-configs[n]**一样，都是列表。都是**n**的值越大优先级越高

添加两个配置文件**share-conig.yml**和**share2-config.yml**

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\36.png)

share-config.yml

```yaml
test:
  share:
    language: chinese
    location: None
```

share2-config.yml

```yaml
test:
  share:
    location: china
```

bootstrap.yml

```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848,127.0.0.1:8849   # nacos配置中心的地址，集群用逗号（,）分开
        # 配置文件的文件类型（与自己在配置中心起的dataId后的扩展名一致，
        # 比如test-config.yml，那就yml；如test-config.yaml，那就yaml。默认是properties）
        file-extension: yml
        name: test-config     # DataId  (不填该属性时默认为  spring.application.name 的值)
        group: TEST_GROUP      # Group组名（默认是 DEFAULT_GROUP）
        # 扩展配置
        extension-configs[0]:   # 不自动刷新
          data-id: test2-config.yml   # 配置文件的名字，这里必须加上后缀
          group: DEFAULT_GROUP   # 配置文件所在的group，不填默认是DEFAULT_GROUP
          refresh: true    # 自动刷新
        extension-configs[1]:
          data-id: test3-config.yml
          group: TEST_GROUP
          refresh: true    # 自动刷新
        # 共享配置
        shared-configs[0]:
          data-id: share-config.yml
          group: DEFAULT_GROUP   # 配置文件所在的group，不填默认是DEFAULT_GROUP
          refresh: true   # 自动刷新
        shared-configs[1]:
          data-id: share2-config.yml
          group: DEFAULT_GROUP  
          refresh: true
  application:
    name: test-config     # 该应用的名字，这里使用了nacos配置中心里的DataId（不包括后面的.yml）
```

configProperties

```java
package com.hat.nacos_server.bean.configModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component    //注入到spring容器
@Setter    //lombok的set
@Getter    //lombok的get
@ConfigurationProperties(prefix = "test")   //映射application.yml配置文件里前缀是test的属性
public class ConfigProperties {
    private String name;
    private String age;
    // 测试多配置文件时新增的属性
    private String address;
    private String city;
    private String phone;
    private String num;
    // 测试共享配置
    private ShareProperties share;
}

```

ShareProperties

```java
package com.hat.nacos_server.bean.configModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShareProperties {
    private String language;
    private String location;
}

```

testController新增测试接口

```java
    @GetMapping("share")
    public String testShare(){
        return properties.getShare().getLanguage()+"--"+properties.getShare().getLocation();
    }
```

启动应用,在日志可以看到程序订阅和监听这两个共享配置文件

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\37.png)

请求这个测试接口。可以看到**locaiton的值是china而不是None**。所以**shared-configs[n]**中**n**的值**越大优先级就越高**

![](D:\Study\IdeaProjects\springboot-advance\06_spring-cloud\img\38.png)

**配置的优先级**

**A：spring.cloud.nacos.config.{spring.application.name}.{profile} （bootstrap.yml配置文件最外层）**

**B：spring.cloud.nacos.config.extension-configs[n]（扩展配置文件）**

**C：spring.cloud.nacos.config.share-config[n]（共享配置文件）**

**优先级顺序 A > B > C**    

**即A、B、C中有共同的属性时，A的会覆盖B和C的属性。B会覆盖C的属性**

### 8.nacos服务注册与服务发现

