package br.com.joelf.rinha_backend_2023_q3.infrastructure.database.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.CacheRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CacheRepositoryImpl<Key, Value> implements CacheRepository<Key, Value> {

    private final RedisTemplate<Key, Value> redisTemplate;

    private static final Long SET_ITEM_TIMEOUT = 1L;
    private static final TimeUnit SET_ITEM_TIME_UNIT = TimeUnit.MINUTES;

    @Override
    public Value get(Key key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(Key key, Value value) {
        redisTemplate.opsForValue().set(key, value, SET_ITEM_TIMEOUT, SET_ITEM_TIME_UNIT);
    }
}