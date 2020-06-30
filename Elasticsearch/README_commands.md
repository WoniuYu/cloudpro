ES 常用命令

~~~js
GET /_cat/nodes :查看所有节点
GET /GET GET /_cat/health :查看es 健康状况
GET /GET /_cat/master :查看主节点
GET /_cat/indices :查看所有索引
~~~

新增

~~~js
// post 请求可以不带id， 不带id，这条数据永远是新增，不带id 会自动生成id
// 第一次是create ， 第二次是update
POST /customer/external/{id}
// 请求数据
`{
    "name": "woniu"
}`
// 返回数据
`{
    "_index": "customer",
    "_type": "external",
    "_id": "1",
    "_version": 1,
    "result": "created",
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    },
    "_seq_no": 0,
    "_primary_term": 1
}`
PUT /customer/external/{id}

POST 请求可以带 /_update
会去比较值，若值相同，则不做任何操作
// 请求数据
`{
	"doc": {
		"name": "woniu"
	}
}`
// 返回数据
`{
    "_index": "customer",
    "_type": "external",
    "_id": "1",
    "_version": 1,
    "result": "noop",
    "_shards": {
        "total": 0,
        "successful": 0,
        "failed": 0
    },
    "_seq_no": 0,
    "_primary_term": 1
}`
~~~

查询

~~~js
GET /customer/external/{id}

// 乐观锁 
GET /customer/external/{id}?if_seq_no=1&if_primary_term=1
~~~



批量操作 /_bulk

