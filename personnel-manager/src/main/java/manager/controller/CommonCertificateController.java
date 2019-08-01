package manager.controller;


import manager.pojo.CommonCertificate;
import manager.service.CommonCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.Result;

/**
* @Description:    常规证书信息管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 16:09
* @UpdateUser:
* @UpdateDate:     2019/8/1 16:09
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("common")
public class CommonCertificateController {
    @Autowired
    private CommonCertificateService commonCertificateService;
    /**
     *@author      473225193    yuanyou
     * @param certificate
     * @return      util.Result
     * @exception
     * @date        2019/8/1 16:28
     * @description
     */
    @PutMapping("update")
    public Result updateCommonCertificate(@RequestBody MultipartFile files[], @RequestBody CommonCertificate certificate){
        //保存图片到服务器
        //保存图片的地址到数据库
        commonCertificateService.updateCommonCertificate(certificate);
        return new Result(null);
    }
}