package com.transactions.consumer.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TaskDAOImpl implements TaskDAO {

    private static final String SOURCE_QUEUE_NAME = "transactions:pending";
    private static final long BL_POP_TIMEOUT_SECONDS = 0L;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String popTask() {
        try {
            String item = redisTemplate.opsForList().leftPop(SOURCE_QUEUE_NAME, Duration.ofSeconds(BL_POP_TIMEOUT_SECONDS));
            if (item != null) {
                return item;
            } else {
                log.warn("LPOP timed out on source key '{}'", SOURCE_QUEUE_NAME);
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to LPOP transaction from Redis", e);
        }
    }

    // We can also implement a Reliable Queue
    // https://redis.io/glossary/redis-queue/
}
