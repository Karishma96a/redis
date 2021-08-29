package com.cache.redis;

import com.cache.redis.model.User;
import com.cache.redis.repo.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/{id}/{name}/{salary}")
    public User addUser(@PathVariable("id") final String id,
                        @PathVariable("name") final String name,
                        @PathVariable("salary") final Long salary) {
        userRepository.save(new User(id, name, salary));
        return userRepository.findById(id);
    }

    @PutMapping("/{id}/{name}")
    public User updateUser(@PathVariable("id") final String id,
                           @PathVariable("name") final String name) {
        userRepository.update(new User(id, name, 1000L));
        return userRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public Map<String, User> delete(@PathVariable("id") final String id) {
        userRepository.delete(id);
        return all();
    }

    @GetMapping("/all")
    public Map<String, User> all() {
        return userRepository.findAll();
    }

    @Cacheable(key = "#id", value = "user")
    @GetMapping("/{id}")
    public User findById(@PathVariable("id") final String id) {
        return userRepository.findById(id);
    }
}