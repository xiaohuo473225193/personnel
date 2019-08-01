package manager.controller;

import manager.pojo.User;
import manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Result;

/**
 * @author xiaohuo
 * @data 2019/7/15 14:58
 * @description
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("findOne")
    public Result<User> findOne(){
        User user = userService.findOne();
        return new Result<>(user);
    }
}
