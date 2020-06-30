

连接

select  concat(field, field)  from table; 



null 处理

IFNULL(field, 0)



转义

select *  from table user name like '\__\\_\_%';

select *  from table user name like '\__a_\_%' ESCAPE 'a';  //  任意字符`都可以用作转义字符



安全等于 <=>

   区别  IS  NULL  只能判断  null

   <=>   可以判断null，和普通数值  field <=> null    field<=> '1200'  field is null   



函数

length()   /// 获取参数字节数

concat(field, "_", field)   // hh hh    hh_hh

upper()

lower()

substr(str, index,  length) |  substring(str, index,  length)

instr(str, arg)      arg 在 str第一次出现的索引， 没有就是 0

trim(arg from  str)  去除 str 前后的 arg ， 只有str时，去除前后空格

lpad(str,  len, agr)   左填充到 len 长度

rpadstr,  len, agr)   右填充到 len 长度

replace(str, agr1, agr2)   把str 中所有  agr1 替换为 arg2



round(arg, len)  四舍五入   arg 保留小数点后 len 长度， 如没有len， 则取整

ceil()  向上取整

floor() 向下取整

truncate(arg, len)  截断， 小数点后截取len 位

mod()  取余



now()    当前日期+ 时间

curdate()   当前日期

curtime   当前时间

year()

month()

...

str_to_date(strYear, format)

date_format(date, format)

datediff(year1, year2)    year1 -  year2 的天数



if(condition, '1', '2')  类似三元运算符  condition=true  -> 1  else   2

case：

  1

​	case  要判断的字段或表达式

​    when 常量1 then 要显示的值

​	when 常量2 then 要显示的值

​	else  要显示的值

​	end

 

 2

case  

​    when field> 2000 then 要显示的值

​	when field> 1000 then 要显示的值

​	else  要显示的值

​	end

 

sum(), avg(), min(),max(),count()



distinct 去重

exists()  判断是否存在

group by

having  分组后 进行筛选





连接

内连接  inner （可省略）

​		等值连接，非等值连接， 自然连接

外连接   left  right

​	左连接，   右连接

 交叉连接 cross  笛卡儿积



约束

​	主键，  非空， 唯一， 默认， 外键， 检查（mysql 不支持)

​		列级约束   除了外键都可以，但不能起约束名

​		表级约束  除了非空和默认都可以， 可以起约束名，但主键起约束名无效



DESC table  查看表设计

索引 

​	查看索引    show index from table



事务   truncate table  xxx   不支持事务

 开启事务  set autocommit=0;

​				   start transaction;  #可省略

​					sql

​				   savepoint   可设置回滚点，回滚时 能回滚到指定的回滚点

​				   commit； |  rollback to  回滚点；

​	脏读 ： 		   读到未提交的数据

​	不可重复读： 同一个事务内两次读到的数据不同

​	幻读： 			同一是事务内，两次读到的行不同

 隔离级别

​	读未提交

​	读已提交

​	可重复读

​	串行化



select @@tx_isolation;  查看隔离级别

set @@global.变量=v   设置全局变量

show global  variables； 查看所有全局变量

​	

设置变量

set @变量名=值；或

set @变量名:=值；或

select  @变量名:=值；

查看变量

select  @变量名；



delimeter  $   设置结束标志为  $





explain  

id    type  （system>const>eq_ref>ref>rang>index>all）