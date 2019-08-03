package manager.controller;

import com.github.pagehelper.PageInfo;
import manager.pojo.College;
import manager.pojo.User;
import manager.service.CollegeService;
import manager.service.UserService;
import manager.vo.CollegeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.rsa.RSAPrivateKeyImpl;
import util.Code;
import util.PException;
import util.PageResult;
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
     * @description 根据该用户id获取该部门下的员工数据     */
    @GetMapping("find/{userId}/{rows}/{size}")
    public PageResult<CollegeUser> findCollegeUserListByUid(@PathVariable(value = "userId")Long userId,
         @PathVariable(value = "size")int size,@PathVariable(value = "rows")int rows){

        User user = userService.findByUid(userId);
        Long cid = user.getCid();
        return collegeService.findCollegeUserListByCid(cid, rows, size);
    }
}
