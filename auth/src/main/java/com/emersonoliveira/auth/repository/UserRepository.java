package com.emersonoliveira.auth.repository;

import com.emersonoliveira.auth.entity.Permission;
import com.emersonoliveira.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Permission, Long> {

    @Query("select u from User u where u.userName =:userName")
    User findByUserName(@Param("userName") String userName);
}