package manager.controller;

import manager.pojo.EntryCertificate;
import manager.service.EntryCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.Result;

import java.util.List;

/**
* @Description:    入职表单的管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 14:34
* @UpdateUser:     
* @UpdateDate:     2019/8/1 14:34
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("/entry")
public class EntryCertificateController {
    @Autowired
    private EntryCertificateService entryCertificateService;
    @GetMapping("/list")
    public Result<List<EntryCertificate>> findByList(){
        List<EntryCertificate> entryCertificateList = entryCertificateService.findByList();
        return new Result(entryCertificateList);
    }
    /**
     *@author      473225193    yuanyou
     * @param uid
     * @return      util.Result
     * @exception
     * @date        2019/8/5 16:13
     * @description 回显图片地址
     */
    @GetMapping("show/{uid}")
    public Result findByUid(@PathVariable(value = "uid") Long uid){
        EntryCertificate entryCertificate = entryCertificateService.findByUid(uid);
        return new Result(entryCertificate);
    }
    @PutMapping("update")
    public Result updateUploadEntryCertificate(@RequestBody EntryCertificate entryCertificate){
        entryCertificateService.updateUploadEntryCertificate(entryCertificate);
        return new Result("更新成功");
    }
}
