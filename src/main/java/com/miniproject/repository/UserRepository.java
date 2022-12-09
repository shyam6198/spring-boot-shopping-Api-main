package com.miniproject.repository;



import com.miniproject.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDao, Long> {
    UserDao findByEmailId(String emailId);
}
