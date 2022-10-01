package com.example.bdc.network.user;

import com.example.bdc.network.topic.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
   Optional<User> findByName(String name);
   List<User> findByName(String name, Pageable pageable);

   default User saveIfNotExist(User user) {
      return findByName(user.getName()).orElseGet(() -> save(user));
   }
}
