order by

增大 max_length_for_sort_data 参数的设置， 增大 sort_bufferr_size 参数的设置

 索引 key a_b_b(a,b,c)

​		order by a;

​		order by a, b;

​		order by a,b,c;

​		order by a desc,b desc,c desc;

​     

​		where a = const order by b, c

​		where a=const and b=const order by c

​		where a=const order by b, c

​		where a=const and b>const order by b ,c



​	 不能使用索引

​		order by a asc, b desc, c desc  #排序不一致

​		where g = const order by b,c	# 丢失a索引

​		where a=const order by c	#丢失b索引

​		where a=const order by a, d	# d 不是索引的一部分

​		where a in (...) order by b,c	#对与排序来说，多个相等的条件也是范围查询