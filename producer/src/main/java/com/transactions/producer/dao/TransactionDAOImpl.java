package com.transactions.producer.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "TransactionDAO")
@Repository
@RequiredArgsConstructor
public class TransactionDAOImpl implements TransactionDAO {
    private static final String SOURCE_QUEUE_NAME = "transactions:pending";
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void pushTask(String item) {
        try {
            Long result = redisTemplate.opsForList().rightPush(SOURCE_QUEUE_NAME, item);
            if (result == null || result == 0L) {
                log.debug("Failed to RPOP {} transaction to Redis", item);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to RPOP transaction to Redis", e);
        }
    }
}
