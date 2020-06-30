Nginx 配置 反向代理
   server {
        listen          80;
        server_name     192.168.18.180;
        localhost ~ /text/ {
            proxy_pass http://192.168.18.180:8001;
            root   html;
            index  index.html index.htm;
        }
         localhost ~ /text2/ {
            proxy_pass http://192.168.18.180:8002;
            root   html;
            index  index.html index.htm;
        }
    }

Nginx 配置 负载均衡
 1.  在 http 模块中配置
     #gzip on
     upstream serverName{
         server 192.168.18.180:8001;
         server 192.168.18.180:8002;
     }
 2. 在 server 中配置
      server {
        listen          80;
        server_name     192.168.18.180;
        localhost ~ /text/ {
            proxy_pass http://serverName;
            root   html;
            index  index.html index.htm;
        }
      }
 分配策略 
 1. 轮询； 
 2. weight 权重 ， 默认为1，越高被分配的客户端越多； 
     #gzip on
     upstream serverName{
         server 192.168.18.180:8001 weight=10;
         server 192.168.18.180:8002 weight=10;
     }
 3. ip_hash 根据访问ip 的 hash结果分配， 这样每个访客访问一个后端， 可以解决session问题；
     #gzip on
     upstream serverName{
         ip_hash;
         server 192.168.18.180:8001;
         server 192.168.18.180:8002;
     } 
 4. fair 根据后端响应时间来分配， 响应时间越短的越优先分配
     #gzip on
     upstream serverName{
         server 192.168.18.180:8001;
         server 192.168.18.180:8002;
         fair;
     } 
 

Nginx 配置 动静分离
  配置
    location /www/{
        root /data/;
        index index.html index.htm;
    }
    location /image/ {
        root /data/;
        autoindex on;
    }

  创建静态文件
    /data/www/test.html; /data/image/test.jpg

  浏览器访问
    localhost/www/test.html
    localhost/image/test.jpg

    localhost/image  访问该地址， 会列出 /data/image 文件目录， 由于配置了 "autoindex on"