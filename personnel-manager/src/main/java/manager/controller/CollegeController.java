package manager.controller;

import manager.pojo.College;
import manager.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Result;

import java.util.List;

/**
 * @author xiaohuo
 * @data 2019/7/15 20:02
 * @description
 */
@RestController
@RequestMapping("college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;
    @GetMapping("list")
    public Result<List<College>> findByList(){
        List<College> collegeServiceByList = collegeService.findByList();
        return new Result<>(collegeServiceByList);
    }
}
