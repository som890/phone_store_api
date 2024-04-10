package com.dev.phonestore.phonestore.repository;

import com.dev.phonestore.phonestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

   // @Query("SELECT u FROM User u WHERE u.userName= :userName")
    public User findByUserName(String userName);
}
