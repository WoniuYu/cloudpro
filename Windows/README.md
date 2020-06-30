**Windows 一些配置项**



使用 JMeter 出现 Address Already in use 错误

由于 windows 本身提供端口访问机制出现的问题，windows 提供给 TCP/IP 链接的端口为 1024- 5000， 并且 在四分钟来循环回收，这样就导致在短时间之内跑大量请求时，将 端口占满了；

解决

cmd 中  用 regedit 打开 注册表

HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\Tcpip\Parameters 路径下

新建 DWORD, 名字为 MaxUserPort ，值为 65534 （十进制）

新建 DWORD，名字为 TCPTimeWaitDelay , 值为 30 （十进制）

修改 完之后，需要重启电脑，才生效。