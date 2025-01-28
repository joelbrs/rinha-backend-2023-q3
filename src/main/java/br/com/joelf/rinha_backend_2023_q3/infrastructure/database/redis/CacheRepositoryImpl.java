package br.com.joelf.rinha_backend_2023_q3.infrastructure.database.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.CacheRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CacheRepositoryImpl<Key, Value> implements CacheRepository<Key, Value> {

    private final RedisTemplate<Key, Value> redisTemplate;

    private static final Integer START_RANGE_LIST = 0;
    private static final Integer END_RANGE_LIST = -1;
    private static final Long SET_ITEM_TIMEOUT = 5L;
    private static final TimeUnit SET_ITEM_TIME_UNIT = TimeUnit.MINUTES;

    @Override
    public Value get(Key key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(Key key, Value value) {
        redisTemplate.opsForValue().set(key, value, SET_ITEM_TIMEOUT, SET_ITEM_TIME_UNIT);
    }

    @Override
    public List<Value> getList(Key key) {
        return redisTemplate.opsForList().range(key, START_RANGE_LIST, END_RANGE_LIST);
    }

    @Override
    public void setList(Key key, List<Value> value) {
        if (!value.isEmpty()) {
            redisTemplate.opsForList().rightPushAll(key, value);
            redisTemplate.expire(key, SET_ITEM_TIMEOUT, SET_ITEM_TIME_UNIT);
        }
    }
}
