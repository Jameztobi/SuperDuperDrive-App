package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private UserMapper userMapper;
    private HashService hashService;
    private Integer userid;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        if(userMapper.getUser(username) == null){return true;}
        return false;
    }

    public int addUser(Users user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        user.setPassword(hashedPassword);
        this.userid=userMapper.insert(new Users(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));
        return userid;
    }

    public boolean deleteUser(Users user ){
        userMapper.delete(user);
      return true;
    }

    public Users getUser(String username){
        return userMapper.getUser(username);
    }






}
