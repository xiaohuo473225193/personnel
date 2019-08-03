package manager.controller;

import com.github.pagehelper.PageInfo;
import manager.bo.SelectOptionData;
import manager.pojo.User;
import manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.genid.GenId;
import util.PException;
import util.PExceptionHandler;
import util.PageResult;
import util.Result;

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
    @GetMapping("findOne")
    public Result<User> findOne(){
        User user = userService.findOne();
        return new Result<>(user);
    }

    @PutMapping("save")
    public Result save(@RequestBody User user) {
        userService.save(user);
        return new Result<>();
    }

    @PostMapping("addUser/{author}")
    public Result addUser(@RequestBody User user,@PathVariable(value = "author")String author) {
       userService.addUser(user,author);
        commonCertificateService.addCommonCertificate(user);
        entryCertificateService.addEntryCertificate(user);
        stageCertificateService.addStageCertificate(user);
        return new Result<>();
    }

    @DeleteMapping("deleteByUid")
    public Result deleteByUid(@RequestBody User user) {
        userService.deleteByUid(user.getUid());
        return new Result<>();
    }

    @PutMapping("updateByUser")
    public Result updateByUser(@RequestBody User user) {
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

}
