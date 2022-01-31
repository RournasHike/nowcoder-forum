# 牛客网社区项目文档

## 环境安装

### MySQL安装

1、下载压缩包，解压

2、配置my.ini文件

```mysql
[mysql]
default-character-set=utf8
[mysqld]
port=3306
basedir=D:\environment\db\mysql\mysql-8.0.15-winx64
max_connections=20
character-set-server=utf8
default-storage-engine=INNODB
```

3、设置环境变量

```ini
MYSQL_HOME=MySQL安装目录
将MySQL的bin目录加入到Path中
```

4、初始化和配置MySQL

以管理员身份运行cmd,在命令行中输入以下命令

```mysql
> mysqld --initialize --console
> mysqld install
> net start mysql
```

5、修改mysql数据库系统root用户的密码

在命令行中输入mysql -uroot -p，回车，输入初始密码

看到mysql提示符后，输入命令修改root用户密码

```mysql
mysql> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '你的密码'; 
```

[[MySQL 连接出现 Authentication plugin 'caching_sha2_password' cannot be loaded]](https://www.cnblogs.com/zhurong/p/9898675.html)



## 开发环境

- 构建工具：[Apache Maven](http://maven.apache.org)
- 集成开发工具：IntelliJ IDEA
- 数据库：MySQL、[Redis](https://redis.io/)
- 应用服务器：[Apache Tomcat](https://tomcat.apache.org/)
- 版本控制工具：Git

## 技术栈

| 软件名称      | 描述         | 版本          |
| ------------- | ------------ | ------------- |
| Spring Boot   | 开源框架     | 2.3.0.RELEASE |
| MyBatis       | ORM框架      |               |
| MySQL         | 数据库       | 8.0.16        |
| Kafka         | 消息中间件   |               |
| Redis         | 缓存数据库   |               |
| Git           | 版本控制工具 |               |
| Elasticsearch | 搜索引擎     |               |
| Thymeleaf     | 模板引擎     |               |

## 功能概览

短信验证登录功能：https://blog.csdn.net/weixin_44137464/article/details/106794221