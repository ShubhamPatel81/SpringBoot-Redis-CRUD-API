# SpringBoot-Redis-CRUD-API

# Spring Boot + Redis Caching in CRUD Operations

This project demonstrates how to integrate **Redis caching** into a **Spring Boot CRUD application** using annotations like `@Cacheable` and `@CacheEvict`.

## Tech Stack
- Java 17+
- Spring Boot
- Spring Data JPA ( MongoDB)
- Spring Cache
- Redis (via Jedis or Lettuce)
- Maven

---

##  Dependencies (from `pom.xml`)

```xml
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```

# Redis Installation and Setup
# For Ubuntu/Linux:
```
sudo apt update
sudo apt install redis-server
sudo systemctl enable redis-server
redis-server                          # Start Redis manually
sudo service redis-server start      # Start using system service
redis-cli ping                        # Check connection (should return PONG)
sudo service redis-server stop       # Stop Redis server
```
# Check if Redis is running:
```
redis-cli ping
# Response should be: PONG
```
# Spring Boot Redis Configuration (in application.properties)

```yml
spring.application.name=demo

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/redis

# Redis
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.timeout=60000
spring.redis.lettuce.pool.max-active=8
spring.redis.lettuce.pool.max-wait=-1
spring.redis.lettuce.pool.max-idle=8
spring.redis.lettuce.pool.min-idle=8

# Caching
spring.cache.type=redis
spring.cache.redis.time-to-live=30000
spring.cache.redis.cache-null-values=false
```
# Redis Configuration (Java Code)
```
@Configuration
public class Config {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public RedisTemplate<String,Object> objectRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
```




