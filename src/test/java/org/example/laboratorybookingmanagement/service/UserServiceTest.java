package org.example.laboratorybookingmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
@Slf4j
class UserServiceTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Test
    void getUser() {
        String account = "admin";
        User user = userService.getUser(account);
        log.debug("{}",user);
    }

    @Test
    void updateUserPassword() {
        String account = "2022222908";
        userService.updateUserPassword(account);
    }

    @Test
    void testUpdateUserPassword() {
        String uid = "01JFHY1JRC5BJN919YEHQYXWAR";
        String password = "2022222917";
        userService.updateUserPassword(uid, password);
    }

    @Test
    void addUser() {
        User user = User.builder().name("张三").account("2089097867").role(User.Teacher).telephone("18978654532").build();
        userService.addUser(user);
    }

    @Test
    void listUsers() {
        List<User> users = userService.listUsers();
        for (User u : users) {
            log.debug("{}",u);
        }
    }
}