package manager.controller;

import manager.pojo.EntryCertificate;
import manager.pojo.StageCertificate;
import manager.service.StageCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.Result;

/**
* @Description:    阶段性鉴证材料控制层管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/5 17:03
* @UpdateUser:
* @UpdateDate:     2019/8/5 17:03
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("stage")
public class StageCertificateController {
    @Autowired
    private StageCertificateService stageCertificateService;
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
        StageCertificate stageCertificate = stageCertificateService.findByUid(uid);
        return new Result(stageCertificate);
    }
    @PutMapping("update")
    public Result updateUploadEntryCertificate(@RequestBody StageCertificate stageCertificate){
        stageCertificateService.updateUploadStageCertificate(stageCertificate);
        return new Result("更新成功");
    }
}
