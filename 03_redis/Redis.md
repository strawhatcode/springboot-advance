# Redis

## Redis介绍

Redis 是一个开源（BSD许可）的、使用 ANSI C 语言编写的数据库，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。它是一种NoSQL（非关系型数据库），采用key-value形式存放数据，且数据都存储在内存上，因此速度非常快，而且它也提供了两种数据持久化方式，RDB和AOF。它提供多种数据类型，常用的有**字符串（strings）， 散列（hashes）， 列表（lists）， 集合（sets）， 有序集合（sorted sets）**



## docker方式安装redis

- 下载redis,不指定版本则默认下载最新版本

  ```
  docker pull redis
  ```

- 创建**redis.conf**文件作为**redis**的配置文件，因为redis默认是不带配置文件的，不过它会按照它自己特定一种配置启动，然后可以去官网下载redis的配置文件，也可以复制粘贴。**注意：尽量不要使用最新的配置，最新的配置会导致无法使用docker来启动redis。**

  1. [官网配置](https://redis.io/topics/config),官网提供了好几个版本的配置文件

     > - The self documented [redis.conf for Redis 4.0](https://raw.githubusercontent.com/antirez/redis/4.0/redis.conf).
     > - The self documented [redis.conf for Redis 3.2](https://raw.githubusercontent.com/antirez/redis/3.2/redis.conf).
     > - The self documented [redis.conf for Redis 3.0](https://raw.githubusercontent.com/antirez/redis/3.0/redis.conf).
     > - The self documented [redis.conf for Redis 2.8](https://raw.githubusercontent.com/antirez/redis/2.8/redis.conf).
     > - The self documented [redis.conf for Redis 2.6](https://raw.githubusercontent.com/antirez/redis/2.6/redis.conf).
     > - The self documented [redis.conf for Redis 2.4](https://raw.githubusercontent.com/antirez/redis/2.4/redis.conf).

  2. [官方github的配置](https://github.com/antirez/redis/blob/unstable/redis.conf)，在github上的配置似乎是最新的。

- 修改**redis.conf**配置文件，主要修改以下5个配置即可

  ```
  #bind 127.0.0.1  //绑定访问地址，把这行注释掉，这个配置是绑定了本机的地址，意思只有本机可以访问redis
  protected-mode no  //保护模式，把这行设置成no，关闭保护模式，否者外部无法连接redis
  daemonize no     //守护进程，把这行设置成no，如果为yes则无法使用docker开启redis
  requirepass 123456  //密码，设置密码，连接redis时需要用的密码，默认不用密码
  appendonly yes   //AOF持久化，设置成yes开启持久化
  ```

- 使用docker开启redis

  ```
  hat@ubuntu:~/docker/redis$ docker run \                //docker启动
  > -d \											   //后台启动
  > -p 6379:6379 \									//映射的端口号
  > --name myredis \									//容器名称
  > -v $PWD/conf/redis.conf:/etc/redis/redis.conf \	   //把redis的配置文件挂载到本机
  > -v $PWD/data:/data \								//把redis的数据文件挂载到本机
  > redis redis-server /etc/redis/redis.conf	   //启动的容器名称，使用自己的配置文件启动redis-server
  ```

- 启动成功图

  ![](images/1.jpg)

  > **如果修改配置后还是无法启动容器则去官网下载低版本的配置文件。**

## springboot整合redis

