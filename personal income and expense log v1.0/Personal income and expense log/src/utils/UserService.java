package utils;

import models.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();
    }

    public boolean register(String name, String pwd) {
        for(User u : userList){
            if(u.getUsername().equals(name)){
                return false;
            }
        }
        userList.add(new User(name,pwd));
        return true;
    }

    public User login(String name, String pwd) {
        for(User u : userList){
            if(u.getUsername().equals(name) && u.getPassword().equals(pwd)){
                return u;
            }
        }
        return null;
    }
}
