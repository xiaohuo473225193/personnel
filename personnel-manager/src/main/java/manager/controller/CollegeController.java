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
* @Description:    部门基本信息的管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 14:36
* @UpdateUser:
* @UpdateDate:     2019/8/1 14:36
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;
    /**
     *@author      473225193    yuanyou
     * @param
     * @return      util.Result<java.util.List<manager.pojo.College>>
     * @exception
     * @date        2019/8/1 14:47
     * @description 查询所有部门信息
     */
    @GetMapping("list")
    public Result<List<College>> findByList(){
        List<College> collegeServiceByList = collegeService.findByList();
        return new Result<>(collegeServiceByList);
    }
}
