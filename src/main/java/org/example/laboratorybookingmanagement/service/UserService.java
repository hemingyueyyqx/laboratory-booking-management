package org.example.laboratorybookingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.Lab;
import org.example.laboratorybookingmanagement.dox.User;
import org.example.laboratorybookingmanagement.dto.EnableEquipmentCount;
import org.example.laboratorybookingmanagement.dto.LabCountByDayofweekDTO;
import org.example.laboratorybookingmanagement.dto.LabCountDTO;
import org.example.laboratorybookingmanagement.exception.Code;
import org.example.laboratorybookingmanagement.exception.XException;
import org.example.laboratorybookingmanagement.repository.AppointmentRepository;
import org.example.laboratorybookingmanagement.repository.LabRepository;
import org.example.laboratorybookingmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LabRepository labRepository;
    private final AppointmentRepository appointmentRepository;

    //通过账号查找用户
    public User getUser(String account) {
        return userRepository.findByAccount(account);
    }
    //管理员重置密码
    @Transactional
    public void updateUserPassword(String account) {
        User user = userRepository.findByAccount(account);
        if(user == null) {
            throw  XException.builder().number(Code.ERROR).message("用户不存在").build();
        }
        user.setPassword(passwordEncoder.encode(account));
        //保存
        userRepository.save(user);
    }
    //指定用户改密码
    @Transactional
    public void updateUserPassword(String uid, String password) {
//        log.debug(uid);
//        log.debug(password);
        User user = userRepository.findById(uid).orElse(null);
        if(user == null) {
            throw  XException.builder().number(Code.ERROR).message("用户不存在").build();
        }
        user.setPassword(passwordEncoder.encode(password));
        //保存
        userRepository.save(user);
    }
    //管理员添加用户
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getAccount()));
        userRepository.save(user);
    }
    //列出全部用户
    public List<User> listUsers() {
        return userRepository.findAll();
    }
    //列出所有实验室
    public List<Lab> listLabs() {
        return labRepository.findAll();
    }
    @Transactional
    public Map<String, List<?>> getLabState(int week) {
//        查看每个状态的实验室数
        List<LabCountDTO>  labCountDTOList = labRepository.countLabByState();
//        查看每周每一天有多少个实验室使用（不看节次，只要有课就是使用）
        List<LabCountByDayofweekDTO> labCountByDayofweekDTOList = appointmentRepository.countLabByDayofweek(week);
//        查看每个实验室能用的设备数量
        List<EnableEquipmentCount> enableEquipmentCountList = labRepository.countEnableEquipment();
        return Map.of("labCountDTOList",labCountDTOList,
                "labCountByDayofweekDTOList",labCountByDayofweekDTOList,
                "enableEquipmentCountList",enableEquipmentCountList);
    }

}
