//package org.example.laboratorybookingmanagement.repository;
//
//import lombok.extern.slf4j.Slf4j;
//import org.example.laboratorybookingmanagement.dox.User;
//import org.example.laboratorybookingmanagement.dto.Appointment1;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.List;
//
//
//@SpringBootTest
//@Slf4j
//class UserRepositoryTest {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//
//    @Test
//    void findByAccount() {
//        User u = userRepository.findByAccount("admin");
//        log.debug("{}", u);
//    }
//    @Test
//    public void saveUser() {
//        String account = "2022229078";
//        User user = User.builder()
//                .name("ls")
//                .account(account)
//                .password(passwordEncoder.encode(account))
//                .role(User.LABMANAGER)
//                .telephone("18346789765")
//                .build();
//        userRepository.save(user);
//    }
//    @Test
//    void findById() {
//        userRepository.findById("01JFF12X2T3PV09VPSRJG5A92Z")
//                .ifPresent(user -> log.debug("{}", user));
//    }
//    @Test
//    void count() {
//        log.debug("{}", userRepository.count());
//    }
//
//
//    @Test
//    void findCourseByTeacherId() {
//        List<Appointment1> appointment1s = userRepository.findCourseByTeacherIdAndSemester("24-1","01JFJ5CWY6FD4XTTHR42FBS6A4");
//        for(Appointment1 appointment1 : appointment1s) {
//            log.debug("{}",appointment1);
//        }
//    }
//}