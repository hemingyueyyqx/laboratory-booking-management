package org.example.laboratorybookingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.laboratorybookingmanagement.dox.Lab;
import org.example.laboratorybookingmanagement.dox.News;
import org.example.laboratorybookingmanagement.exception.Code;
import org.example.laboratorybookingmanagement.exception.XException;
import org.example.laboratorybookingmanagement.service.LabManagerService;
import org.example.laboratorybookingmanagement.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labadmin/")
@RequiredArgsConstructor
public class LabManagerController {
    private final LabManagerService labManagerService;
    @PostMapping("addnews")
    public ResultVo addNews(@RequestAttribute("role") String role,@RequestBody News news) {
        labManagerService.addNews(role,news);
        return ResultVo.ok();
    }
    @PostMapping("updatenews")
    public ResultVo updateNews(@RequestAttribute("role") String role,@RequestBody News news) {
        labManagerService.updateNews(role,news);
        return ResultVo.ok();
    }
    @DeleteMapping("news/{newsIds}")
    public ResultVo deleteNews(@RequestAttribute("role") String role,@PathVariable List<String> newsIds) {
        labManagerService.deleteNews(role,newsIds);
        return ResultVo.ok();
    }
    @GetMapping("news")
    public ResultVo listNews() {
        return ResultVo.success(labManagerService.listNews());
    }
    @GetMapping("news/{author}")
    public ResultVo listNewsById(@PathVariable String author) {
        return ResultVo.success(labManagerService.listNewsByAuthor(author));
    }
    @PostMapping("lab")
    public ResultVo updateLab(@RequestAttribute("role") String role,@RequestBody Lab lab) {
        labManagerService.updateLab(role,lab);
        return ResultVo.ok();
    }
    @GetMapping("lab")
    public ResultVo getlabs(@RequestAttribute("uid") String id) {

        return ResultVo.success(labManagerService.getLabs(id));
    }

}
