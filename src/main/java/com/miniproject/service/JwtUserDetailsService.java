package com.miniproject.service;

import com.miniproject.dto.ProfileDTO;
import com.miniproject.model.UserDao;
import com.miniproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService  {
    @Autowired
    private UserRepository userRepository;

    public UserDao save(UserDao user) {
        return userRepository.save(user);
    }

    public UserDao find(String s){
        return userRepository.findByEmailId(s);
    }

    public String loginCheck(String email, String password){
        UserDao s = userRepository.findByEmailId(email);
        if (email.equals(s.getEmailId())  &&  password.equals(s.getPassword()) ) {
            return s.getEmailId();
        }
        else {
            return null;
        }
    }

    public String changePassword(String email, String newPassword, String oldPassword) {
        UserDao s= userRepository.findByEmailId(email);
            if (oldPassword.equals(s.getPassword())){
                s.setPassword(newPassword);
                userRepository.save(s);
                return s.getEmailId();
            }
        return null;
    }

    public ProfileDTO profileShow(String email){
        UserDao userDao=userRepository.findByEmailId(email);
        ProfileDTO profileDTO=new ProfileDTO();
        profileDTO.setId(userDao.getId());
        profileDTO.setFirstname(userDao.getFirstname());
        profileDTO.setLastname(userDao.getLastname());
        profileDTO.setEmailId(userDao.getEmailId());
        profileDTO.setAddress(userDao.getAddress());
        profileDTO.setMobileno(userDao.getMobileno());
        profileDTO.setPincode(userDao.getPincode());
        return profileDTO;
    }

    public UserDao updateProfile(UserDao userDao, String email){
        UserDao userDaoUpdate=userRepository.findByEmailId(email);
        userDaoUpdate.setId(userDao.getId());
        userDaoUpdate.setFirstname(checkNullable(userDao.getFirstname(),userDaoUpdate.getFirstname()));
        userDaoUpdate.setLastname(checkNullable(userDao.getLastname(),userDaoUpdate.getFirstname()));
        userDaoUpdate.setAddress(checkNullable(userDao.getAddress(),userDaoUpdate.getAddress()));
        userDaoUpdate.setPassword(checkNullable(userDao.getPassword(),userDaoUpdate.getPassword()));
        userDaoUpdate.setMobileno(checkNullable(userDao.getMobileno(),userDaoUpdate.getMobileno()));
        userDaoUpdate.setPincode(checkNullable(userDao.getPincode(),userDaoUpdate.getPincode()));
        userDaoUpdate.setEmailId(checkNullable(userDao.getEmailId(),userDaoUpdate.getEmailId()));
        return userRepository.save(userDaoUpdate);
    }

    public String checkNullable(String userDao, String userDaoUpdate){
        if (userDao == null){
            return userDaoUpdate;
        }
        else {
            return userDao;
        }
    }

}