package com.miniproject.repository;

import com.miniproject.model.JwtSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<JwtSession,Integer> {

    List<JwtSession> findAllByEmailId(String emailId);
}
