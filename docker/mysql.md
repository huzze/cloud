#### Mysql docker部署
> 一、拉取镜像：

``docker pull mysql:5.7``
 
> 二、运行容器：

``docker run -d -p 3306:3306 --privileged=true -v /usr/local/mysql/log:/var/log/mysql -v /usr/local/mysql/data:/var/lib/mysql -v /usr/local/mysql/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=123456 --restart=always --name mysql mysql:5.7``

> 三、修改默认编码：

``cd /usr/local/mysql/conf``

``vim my.cnf``

``
[client]
default_character_set=utf8mb4
[mysqld]
collation_server=utf8mb4_unicode_ci
character_set_server=utf8mb4
``

``docker restart mysql``