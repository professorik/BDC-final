package com.example.bdc.network.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   /**
    * @param name String
    * @return Optional<User> returns User or Optional.empty() otherwise.
    * @see User
    */
   Optional<User> findByName(String name);
}
