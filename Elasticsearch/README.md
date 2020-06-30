**sElasticsearch**

安装

~~~she
docker pull elasticsearch:7.4.2

mkdir -p /mydata/elasticsearch/config
mkdir -p /mydata/elasticsearch/data

echo "http.host: 0.0.0.0" >> /mydata/elasticsearch/config/elasticsearch.yml

docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms128m -Xmx256m" \
-v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.4.2

// 需要把es 所在目录权限改为所有用户都为 7 权限
chmod -R 777 /mydata/elasticsearch
~~~



安装 kibana 可视化界面

~~~she
docker pull kibana:6.4.2

docker run --name kibana --link=elasticsearch:test -p 5601:5601 -d kibana:7.4.2
~~~



安装 ES 插件

~~~she
// 解压插件到  /mydata/elasticsearch/plugins
unzip -d ./ik *.zip
// 删除压缩包
rm -rf *.zip
// 可以修改解压目录的权限  777
// 到ES 容器内部的 /bin  elasticsearch-plugin list 查看是否安装好插件
~~~



自定义词库

需要安装 [nginx](../Nginx/README.md/#安装nginx)

~~~shell
<!-- 创建 /mydata/nginx/html/es/custom.txt 词库 
	确保 http://192.168.18.180/es/custom.txt 能访问到
	修改 ik 插件 /mydata/elasticsearch/plugins/ik/config vim IKAnalyzer.cfg.xml      
    <entry key="remote_ext_dict">http://192.168.18.180/es/fenci.txt</entry>
-->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
        <comment>IK Analyzer 扩展配置</comment>
        <!--用户可以在这里配置自己的扩展字典 -->
        <entry key="ext_dict"></entry>
         <!--用户可以在这里配置自己的扩展停止词字典-->
        <entry key="ext_stopwords"></entry>
        <!--用户可以在这里配置远程扩展字典 -->
         <entry key="remote_ext_dict">http://192.168.18.180/es/fenci.txt</entry>
        <!--用户可以在这里配置远程扩展停止词字典-->
        <!-- <entry key="remote_ext_stopwords">words_location</entry> -->
</properties>

<!-- 这样自定义词库就定义好了 -->

~~~

