ssh 方式连接到git
 1. 设置配置信息： 
     git config --global user.name "WoniuYu" 
     git config --global user.email "18141186450@163.com"
 2. 生成密钥：
    ssh-keygen -t rsa -C "18141186450@163.com"  
    全程回车
     cat ~/.ssh/id_rsa.pub  可查看生成的密钥
 3. 把 id_rsa.pub 内容复制到 git
 4. 验证： ssh git@github.com 
     出现 Connection to github.com closed   则说明成功。
    验证码云： ssh -T git@gitee.com
             出现ll access. 则连接成功