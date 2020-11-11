package com.techdenovo.rolebackend.repository;

import com.techdenovo.rolebackend.model.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DAOUser, Long> {
    DAOUser findByUsername(String username);
}
