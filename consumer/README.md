# Consumer

Asynchronously take elements from a redis list and process them


### Redis configuration

https://docs.spring.io/spring-data/redis/docs/3.1.9/reference/html/

```java

@Value("${spring.data.redis.host}")
private String redisHost;

@Value("${spring.data.redis.port}")
private Integer redisPort;

@Bean
LettuceConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
}

@Bean
RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
}

```