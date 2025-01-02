package org.example.laboratorybookingmanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratorybookingmanagement.dox.User;
import org.example.laboratorybookingmanagement.service.UserService;
import org.example.laboratorybookingmanagement.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    //添加用户
    @PostMapping("users")
    public ResultVo postUser(@RequestBody User user) {
        userService.addUser(user);
        return ResultVo.ok();
    }
    //重置密码
    @PutMapping("users/{account}/password")
    public ResultVo putPassword(@PathVariable String account) {
        userService.updateUserPassword(account);
        return ResultVo.ok();
    }
    //获取全部用户信息
    @GetMapping("users")
    public ResultVo getUsers() {
        return ResultVo.success(userService.listUsers());
    }
    //查看所有实验室
    @GetMapping("labs")
    public ResultVo listLabs() {
        return ResultVo.success(userService.listLabs());
    }
    @GetMapping("labstate")
    public ResultVo showLabState(@RequestBody int week) {
        Map<String, List<?>> labState = userService.getLabState(week);
        return ResultVo.success(labState);
    }
}
