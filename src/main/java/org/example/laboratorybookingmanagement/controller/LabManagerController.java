package org.example.laboratorybookingmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.laboratorybookingmanagement.dox.Lab;
import org.example.laboratorybookingmanagement.dox.News;
import org.example.laboratorybookingmanagement.exception.Code;
import org.example.laboratorybookingmanagement.exception.XException;
import org.example.laboratorybookingmanagement.service.LabManagerService;
import org.example.laboratorybookingmanagement.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/labmanager/")
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
    @DeleteMapping("news/{newsId}")
    public ResultVo deleteNews(@RequestAttribute("role") String role,@PathVariable String newsId) {
        labManagerService.deleteNews(role,newsId);
        return ResultVo.ok();
    }
    @GetMapping("news")
    public ResultVo listNews() {
        return ResultVo.success(labManagerService.listNews());
    }
    @PostMapping("lab")
    public ResultVo updateLab(@RequestBody Lab lab) {
        labManagerService.updateLab(lab);
        return ResultVo.ok();
    }
    @GetMapping("lab")
    public ResultVo getlabs(@RequestAttribute("uid") String id) {

        return ResultVo.success(labManagerService.getLabs(id));
    }

}
