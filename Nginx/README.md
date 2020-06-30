**Nginx**

######  安装nginx

~~~shell
# /mydata 创建nginx 目录
mkdir nginx 
docker run -p 8080 --name nginx -d nginx:1.10
# 复制 nginx 配置文件到当前目录
docker container cp nginx:/etc/nginx .
# 卸载docker nginx
docker stop nginx
docker rm  nginx

# 把 nginx 移动到 ./nginx/conf
mv nginx conf
mkdir nginx
mv conf ./nginx

docker run -p 80:80 --name nginx \
-v /mydata/nginx/html:/usr/share/nginx/html \
-v /mydata/nginx/logs:/var/log/nginx \
-v /mydata/nginx/conf:/etc/nginx \
-d nginx:1.10
~~~

