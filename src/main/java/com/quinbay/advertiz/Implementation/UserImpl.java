package com.quinbay.advertiz.Implementation;

import com.quinbay.advertiz.Repositories.UserRepository;
import com.quinbay.advertiz.Service.UserService;
import com.quinbay.advertiz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Object checkLogin(String username, String password){
        User user=null;
        try {
            user=userRepository.findByUsernameAndPassword(username, password);
            if(user!= null){
                return user;
            }else{
                return new ResponseEntity("Check your username and password",HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
