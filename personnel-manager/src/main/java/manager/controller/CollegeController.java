package manager.controller;

import com.github.pagehelper.PageInfo;
import manager.pojo.College;
import manager.pojo.User;
import manager.service.CollegeService;
import manager.service.UserService;
import manager.vo.CollegeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.security.rsa.RSAPrivateKeyImpl;
import util.Code;
import util.PException;
import util.PageResult;
import util.Result;

import java.util.ArrayList;
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
    @Autowired
    private UserService userService;

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
    /**
     * @author      2571169797   yang meng bo
     * @param
     * @return      util.Result<java.util.List<manager.pojo.User>>
     * @exception
     * @date        2019/8/2 0002 上午 10:18
     * @description 提供给二级权限用户使用的 -----> 根据该用户id获取该部门下的员工数据     */
    @GetMapping("findCollegeUserListByUid/{userId}/{rows}/{size}")
    public PageResult<CollegeUser> findCollegeUserListByUid(@PathVariable(value = "userId")Long userId,
         @PathVariable(value = "size")int size,@PathVariable(value = "rows")int rows){
        User user = userService.findByUid(userId);
        Long cid = user.getCid();
        return collegeService.findCollegeUserListByCid(cid, rows, size);
    }

    @GetMapping("findCollegeUserListByCid/{cid}/{rows}/{size}")
    public PageResult<CollegeUser> findCollegeUserListByCid(@PathVariable(value = "cid")Long cid,
                                                            @PathVariable(value = "size")int size,
                                                            @PathVariable(value = "rows")int rows){
        return collegeService.findCollegeUserListByCid(cid, rows, size);
    }
    @PostMapping("addCollege")
    public Result addCollege(@RequestBody College college){
        collegeService.addCollege(college);
        return new Result(null);
    }

    @DeleteMapping("deleteCollege/{id}")
    public Result deleteCollege(@PathVariable(value = "id")long id ){
        collegeService.deleteCollege(id);
        return new Result(null);
    }

    @PutMapping("updateCollege")
    public Result updateCollege(@RequestBody College College){
        collegeService.updateCollege(College);
        return new Result(null);
    }


    @PutMapping("findCollege")
    public Result<List<College>> findCollege(){
        List<College> collegeList = collegeService.findCollege();
        return new Result(collegeList);
    }

    @PutMapping("excel")
    public void expot(@RequestBody List<CollegeUser> list){
        collegeService.export("filename",list,"E:\\x.xls");
    }

}
