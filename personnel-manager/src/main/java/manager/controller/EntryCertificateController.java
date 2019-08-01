package manager.controller;

import manager.pojo.EntryCertificate;
import manager.service.EntryCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
