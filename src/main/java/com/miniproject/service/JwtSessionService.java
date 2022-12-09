package com.miniproject.service;

import com.miniproject.model.JwtSession;
import com.miniproject.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtSessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public void tokenSave(String email,String token) throws Exception{
        JwtSession saveToken = new JwtSession();
        saveToken.setEmailId(email);
        saveToken.setToken(token);
        sessionRepository.save(saveToken);
    }


    public String checkToken(String email, String token){
        List<JwtSession> check = sessionRepository.findAllByEmailId(email);
        check = (ArrayList<JwtSession>) check.stream().filter(findToken -> findToken.getToken().equals(token)).collect(Collectors.toList());
        if (check!=null){
            return "valid";
        }
        return null;
    }

    public void changePasswordMethod(String email,String token) throws Exception {
        //find all by email id
        List<JwtSession> deleteFiles=sessionRepository.findAllByEmailId(email);
        // Delete all token have same email
        if (deleteFiles == null){
            throw new Exception("Email is no found");
        }
        sessionRepository.deleteAll(deleteFiles);
        JwtSession saveToken = new JwtSession();
        saveToken.setToken(token);
        saveToken.setEmailId(email);
        sessionRepository.save(saveToken);
    }
}
