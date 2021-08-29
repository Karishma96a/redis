package com.cache.redis.repo;

import com.cache.redis.model.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private String key = "USER";

    private RedisTemplate<String, User> redisTemplate;

    private HashOperations<String, String, User> hashOps;

    public UserRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public void save(User user) {
        hashOps.put(key, user.getId(), user);
    }

    @Override
    public Map<String, User> findAll() {
        return hashOps.entries(key);
    }

    @Override
    public User findById(String id) {
        System.out.println("Hitting the DB");
        return hashOps.get(key, id);
    }

    @Override
    public void update(User user) {
        save(user);
    }

    @Override
    public void delete(String id) {
        hashOps.delete(key, id);
    }
}
