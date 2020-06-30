**Docker 使用**

  安装 Docker： 查看 [docker](https://docs.docker.com/engine/install/centos/) 官网提供的安装教程。

​	下面提供文档关键步骤：ce 版本是免费的

```shell
$ sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
                  
$ sudo yum install -y yum-utils

$ sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

$ sudo yum install -y docker-ce docker-ce-cli containerd.io
$ sudo systemctl start docker
## docker 设置为开机自启动
$ sudo systemctl enable docker 
```

设置 Docker 镜像加速

```shell
$ sudo mkdir -p /etc/docker
$ sudo tee /etc/docker/daemon.json <<-'EOF'
{
"registry-mirrors": ["https://82m9ar63.mirror.aliyuncs.com"]
}
EOF
$ sudo systemctl daemon-reload
$ sudo systemctl restart docker
```
其中**https://xxxxxxx.mirror.aliyuncs.com**,换成自己申请的镜像加速域名。



添加阿里云yum源

~~~shell
cat > /etc/yum.repos.d/kubernetes.repo << EOF
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enable=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-pack-key.gpg
EOF
~~~

安装kubelet  kubeadm kubectl

~~~she
yum install -y kubelet-1.17.3 kubeadm-1.17.3 kubectl-1.17.3

systemctl enable kubelet
systemctl start kubelet
~~~



若报错：Geladene Plugins: fastestmirror

~~~shell
vim /etc/yum/pluginconf.d/fastestmirror.conf
enabled=0

vim /etc/yum.conf
plugins=0

yum clean dbcache
~~~

