package manager.controller;

import manager.pojo.EducationRatio;
import manager.service.RatioService;
import manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.DriverUtil;
import util.Result;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private RatioService ratioService;
    //登录成功后跳转的接口
    @PostMapping("home/index")
    public String index(){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/login-error")
    public String error(){
        System.out.println("login-error");
        return "error";
    }
    @PutMapping("/update/{oldPassword}/{newPassword}")
    @ResponseBody
    public Result updatePassword(@PathVariable("oldPassword") String oldPassword,@PathVariable("newPassword") String newPassword){
        userService.updatePassword(oldPassword,newPassword);
        return new Result("密码修改成功");
    }
    @GetMapping("education/ratio")
    @ResponseBody
    public Result analyzeEducationRatio(){
        List<Map<String,Object>> ratios = ratioService.analyzeEducationRatio();
        return new Result(ratios);
    }
    //磁盘的信息
    @GetMapping("disk/info")
    @ResponseBody
    public Result getDiskInfo(){
        return new Result(DriverUtil.driver());
    }
}
