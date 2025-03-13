//package org.example.laboratorybookingmanagement.repository;
//
//import lombok.extern.slf4j.Slf4j;
//import org.example.laboratorybookingmanagement.dox.Lab;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//
//@SpringBootTest
//@Slf4j
//class LabRepositoryTest {
//    @Autowired
//    private LabRepository labRepository;
//    @Test
//    void findGoodLabs() {
//        List<Lab> labs = labRepository.findGoodLabs("01JFHY1JRC5BJN919YEHQYXWAR", "2");
//        for(Lab lab:labs) {
//            log.debug("{}",lab);
//        }
//    }
//    @Test
//    void findBadLabs() {
//        List<Lab> labs = labRepository.findBadLabs("01JFHY1JRC5BJN919YEHQYXWAR", "2");
//        for(Lab lab:labs) {
//            log.debug("{}",lab);
//        }
//    }
//}