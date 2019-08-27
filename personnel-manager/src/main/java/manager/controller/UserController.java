package manager.controller;

import com.github.pagehelper.PageInfo;
import manager.bo.Keyword;
import manager.bo.SelectOptionData;
import manager.pojo.User;
import manager.service.*;
import manager.vo.CollegeUser;
import manager.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.genid.GenId;
import util.PException;
import util.PExceptionHandler;
import util.PageResult;
import util.Result;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private CommonCertificateService commonCertificateService;
    @Autowired
    private EntryCertificateService entryCertificateService;
    @Autowired
    private StageCertificateService stageCertificateService;
    /**
     *@author      473225193    yuanyou
     * @param
     * @return      util.Result<manager.pojo.User>
     * @exception
     * @date        2019/8/1 14:48
     * @description 根据登录名称查询出个人信息
     */
    @GetMapping("get")
    public Result<User> getUserInfo(){
        User user = userService.get();
        return new Result<>(user);
    }
    @PutMapping("save")
    public Result save(@RequestBody User user) {
        userService.save(user);
        return new Result<>(null);
    }

    @PostMapping("addUser/{author}")
    public Result addUser(@RequestBody User user,@PathVariable(value = "author")String author) {
        userService.addUser(user,author);
        commonCertificateService.addCommonCertificate(user);
        entryCertificateService.addEntryCertificate(user);
        stageCertificateService.addStageCertificate(user);
        return new Result<>(null);
    }

    @DeleteMapping("deleteByUids/{uids}")
    public Result deleteByUid(@PathVariable(value = "uids") List<Long> uids) {
        userService.deleteUser(uids);
        return new Result<>(null);
    }

    @PutMapping("updateByUser")
    public Result updateByUser(@RequestBody User user) {
        System.out.println(user);
        userService.updateByUser(user);
        return new Result<>(null);
    }

    @GetMapping("findByUid/{uid}")
    public Result findByUid(@PathVariable(value = "uid") Long uid) {
        User user = userService.findByUid(uid);
        return new Result<>(user);
    }

    @PutMapping("findByExample/{rows}/{size}")
    public PageResult<User> findByExample(@RequestBody SelectOptionData selectOptionData,
             @PathVariable(value = "size")int size, @PathVariable(value = "rows")int rows) {
        PageResult<User> userPageResult = userService.fingByExampie(rows, size, selectOptionData.
                getJobNumber(),selectOptionData.getName(),selectOptionData.getIdentityCard());
        return userPageResult;
    }
    @PostMapping("search/{page}/{size}")
    public Result searchByKeyword(@PathVariable(value = "page")int page, @PathVariable(value = "size")int size, @RequestBody Keyword keywords){
        //满足关键字搜索:名称、工号、身份证号、电话
        PageResult<CollegeUser> users = userService.searchByKeyword(keywords,page,size);
        return new Result(users);
    }
    @GetMapping("total")
    public Result getUserTotal(){
        int total = userService.getUserTotal();
        return new Result(total);
    }
}
