package manager.controller;

import manager.pojo.User;
import manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Result;

/**
* @Description:    用户的基本信息的管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 14:36
* @UpdateUser:
* @UpdateDate:     2019/8/1 14:36
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     *@author      473225193    yuanyou
     * @param
     * @return      util.Result<manager.pojo.User>
     * @exception
     * @date        2019/8/1 14:48
     * @description 根据登录名称查询出个人信息
     */
    @GetMapping("findOne")
    public Result<User> findOne(){
        User user = userService.findOne();
        return new Result<>(user);
    }
}
