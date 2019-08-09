package manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @PostMapping("home/index")
    public String index(){
        System.out.println("index");
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        System.out.println("login");
        return "login";
    }
    @GetMapping("/login-error")
    public String error(){
        System.out.println("login-error");
        return "error";
    }
}
