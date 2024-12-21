package org.example.laboratorybookingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.laboratorybookingmanagement.dox.User;
import org.example.laboratorybookingmanagement.service.UserService;
import org.example.laboratorybookingmanagement.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("password")
    public ResultVo patchPassword(@RequestBody User user, @RequestAttribute("uid") String uid) {
       userService.updateUserPassword(uid, user.getPassword());
       return ResultVo.ok();
    }
}
