package com.qlmh.datn_qlmh.redis.repo;

import com.qlmh.datn_qlmh.redis.DetailBillRedis;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Log4j2
@Repository
public class DetailBillRedisRepo {
    private HashOperations hashOperations;


    public DetailBillRedisRepo(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void create(String key, DetailBillRedis redis) {
        this.hashOperations.put(key, redis.getId(), redis);
    }

    public DetailBillRedis get(String key, String redisId) {
        return (DetailBillRedis) this.hashOperations.get(key, redisId);
    }

    public Map<String, DetailBillRedis> getAll(int key) {
        return this.hashOperations.entries(key);
    }

    public List<DetailBillRedis> getAllList(String key) {
        return this.hashOperations.values(key);
    }


    public void update(String key, String redisKey, DetailBillRedis redis) {
        this.hashOperations.put(key, redisKey, redis);
        log.info(String.format("User with ID %s updated", redisKey));
    }

    public void delete(String key, String redisId) {
        this.hashOperations.delete(key, redisId);
        log.info(String.format("User with ID %s deleted", redisId));
    }

    public void deleteAll(String key) {
        this.hashOperations.delete(key);
    }
}
