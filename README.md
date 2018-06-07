# springboot-aliyunmq
springboot集成阿里云MQ

##### 通用参数说明

| 参数名	     | 参数说明  |
| --------   | -----  |
| ONSAddr        | 设置 MQ TCP 协议接入点，参考上面表格（推荐）      |
| NAMESRV_ADDR        | 设置 Name Server 列表（不推荐），与 ONSAddr 二选一      |
| AccessKey        | 您在阿里云账号管理控制台中创建的 AccessKey，用于身份认证      |
| SecretKey       | 您在阿里云账号管理控制台中创建的 SecretKey，用于身份认证     |
|OnsChannel       | 用户渠道，默认为：ALIYUN，聚石塔用户为：CLOUD      |



##### 发送消息参数说明

|参数名|	参数说明|
| --------   | -----  |
|ProducerId|	您在控制台创建的 Producer ID|
|SendMsgTimeoutMillis	|设置消息发送的超时时间，单位（毫秒），默认：3000|
|CheckImmunityTimeInSeconds（事务消息）|	设置事务消息第一次回查的最快时间，单位（秒）|
|shardingKey（顺序消息）|	顺序消息中用来计算不同分区的值|

![image](http://om9j2ardo.bkt.clouddn.com/QQ%E5%9B%BE%E7%89%8720180607122953.png)
##### 订阅消息参数说明

|参数名|	参数说明|
| --------   | -----  |
|ConsumerId	|您在 MQ 控制台上创建的 Consumer ID|
|MessageModel|设置 Consumer 实例的消费模式，默认为集群消费（值：CLUSTERING）;广播消费（BROADCASTING）|
|ConsumeThreadNums	|设置 Consumer 实例的消费线程数，默认：64|
|MaxReconsumeTimes|	设置消息消费失败的最大重试次数，默认：16|
|ConsumeTimeout	|设置每条消息消费的最大超时时间，超过设置时间则被视为消费失败，等下次重新投递再次消费。每个业务需要设置一个合理的值，单位（分钟）。默认：15|
|suspendTimeMillis|（顺序消息）	只适用于顺序消息，设置消息消费失败的重试间隔时间|

![image](http://om9j2ardo.bkt.clouddn.com/QQ%E5%9B%BE%E7%89%8720180607122207.png)

##### TCP 协议接入域名

|环境说明	|接入点|
| --------   | -----  |
|公共云内网接入|（阿里云经典网络/VPC）：
华东1、华东2、华北1、华北2、华南1、香港|	http://onsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal|
|公共云公网接入（非阿里云选择这个）|	http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet|
|公共云 Region：亚太东南1(新加坡)	|http://ap-southeastaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal|
|公共云 Region：亚太东南3(吉隆坡)	|http://ons-ap-southeast-3-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal|
|公共云 Region：亚太东北 1 (东京)|	http://ons-ap-northeast-1-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal|
|金融云 Region：华东1	|http://jbponsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal|
|金融云 Region：华东2、华南1|	http://mq4finance-sz.addr.aliyun.com:8080/rocketmq/nsaddr4client-internal|
