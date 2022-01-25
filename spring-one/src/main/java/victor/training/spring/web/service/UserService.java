package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService  {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CacheManager cacheManager;

    // List<Country> findAllCountries();
    @Cacheable("users-count")
    public long countUsers() {
//        cacheManager.getCache("users-count").get(null).get();
        log.debug("Calling service method");
//        new RuntimeException().printStackTrace();
        return userRepo.count();
    }
// A proxy is an object besides the behavior of a bean
// adds additional behavior to your methods

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
    @CacheEvict(value = "users-count", allEntries = true)
    public void createUser(String username) {
//        cacheManager.getCache("users-count").evict(username);
        userRepo.save(new User(username));
    }


    // TODO 5 key-based cache entries
    @Cacheable("user-data")
    public UserDto getUser(long id) {
        return new UserDto(userRepo.findById(id).get());
    }

    @CacheEvict(value = "user-data", key = "#id")
    public void updateUser(long id, String newName) {
//        cacheManager.getCache("user-data").evict(id);

        // TODO 6 update profile too -> pass Dto
        User user = userRepo.findById(id).get();
        user.setName(newName);
    }
}

