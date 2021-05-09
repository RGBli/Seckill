# Seckill
---
### 1.是什么
Seckill 是一个支持高并发的电商秒杀系统。
<br/><br/>  

### 2.技术栈
1）SpringBoot2.3  

2）MySQL5.7  

3）Redis6.0   

4）Kafka2.3   <br/><br/>

### 3.设计思路
1）没有使用传统的悲观锁，而是通过版本号机制，通过 MySQL 乐观锁来提高并发性。

2）通过在数据库层之前添加了缓存层，来缓解 MySQL 压力。

3）通过 Cache Aside 策略，在保证数据库和缓存的一致性。

4）通过消息队列实现了请求的异步削峰。

5）通过 Redis 的 Lua 脚本实现接口限流功能，避免系统被突发大流量冲垮。
<br/><br/>

### 4.参考文章
https://github.com/yunfeiyanggzq/miaosha