package org.example.laboratorybookingmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.Lab;
import org.example.laboratorybookingmanagement.dox.News;
import org.example.laboratorybookingmanagement.dox.User;
import org.example.laboratorybookingmanagement.exception.Code;
import org.example.laboratorybookingmanagement.exception.XException;
import org.example.laboratorybookingmanagement.repository.LabRepository;
import org.example.laboratorybookingmanagement.repository.NewsRepository;
import org.example.laboratorybookingmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LabManagerService {
    private final NewsRepository newsRepository;
    private final LabRepository labRepository;
    private final UserRepository userRepository;
    //更改实验室状态
    //添加公告
    @Transactional
    public void addNews(String role, News news) {
        if(!role.equals("lM07")) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        newsRepository.save(news);
    }
    //删除公告
    @Transactional
    public void deleteNews(String role,String id) {
        News n = newsRepository.findById(id).orElse(null);
        if(n == null) {
            throw XException.builder().number(Code.ERROR).message("公告不存在").build();

        }
        if(!role.equals("lM07")) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }
        newsRepository.deleteById(id);
    }
    //修改公告
    @Transactional
    public void updateNews(String role,News news) {
        News n = newsRepository.findById(news.getId()).orElse(null);
        if(n == null) {
            throw XException.builder().number(Code.ERROR).message("公告不存在").build();
        }
        if(!role.equals("lM07")) {
            throw XException.builder()
                    .code(Code.FORBIDDEN)
                    .number(Code.FORBIDDEN.getCode())
                    .message(Code.FORBIDDEN.getMessage())
                    .build();
        }

        newsRepository.save(news);
    }
    //查看全部公告
    @Transactional
    public List<News> listNews() {
        return newsRepository.findAll();
    }
     //修改实验室状态
    @Transactional
    public void updateLab(Lab lab) {
        Lab l = labRepository.findById(lab.getId()).orElse(null);
        if(l == null) {
            throw XException.builder().number(Code.ERROR).message("实验室不存在").build();
        }
        labRepository.save(lab);
    }
    //基于指定实验室管理员id,查看所有实验室
    @Transactional
    public List<Lab> getLabs(String id) {
        User u  = userRepository.findById(id).orElse(null);
        if(u == null) {
            throw XException.builder().number(Code.ERROR).message("老师不存在").build();
        }
       return labRepository.findLabs(id);
    }
}
