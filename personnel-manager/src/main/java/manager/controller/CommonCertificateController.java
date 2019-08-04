package manager.controller;


import manager.pojo.CommonCertificate;
import manager.service.CommonCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * @description 更新个人的常规证书信息
     */
    @PutMapping("update")
    public Result updateCommonCertificate(@RequestBody CommonCertificate certificate){
        //保存图片到服务器
        //保存图片的地址到数据库
        commonCertificateService.updateCommonCertificate(certificate);
        return new Result(null);
    }
    /**
     *@author      473225193    yuanyou
     * @param file
     * @return      util.Result
     * @exception
     * @date        2019/8/3 13:02
     * @description 上传文件到磁盘,并同步到数据库
     */
    @PostMapping("/upload/{type}")
    public Result uploadCommonCertificate(@RequestBody MultipartFile file,@PathVariable(value = "type") String type) throws Exception{
        commonCertificateService.uploadLocalDisk(file.getOriginalFilename(),file.getInputStream());
        commonCertificateService.addUploadCommonCertificate(file.getOriginalFilename(),type);
        return new Result("上传成功");
    }
    /**
     *@author      473225193    yuanyou
     * @param fileName
     * @return      util.Result
     * @exception
     * @date        2019/8/3 17:51
     * @description 删除磁盘文件
     */
    @DeleteMapping("/delete/upload/{fileName}")
    public Result deleteUploadCommonCertificate(@PathVariable(value = "fileName") String fileName){
        try {
            commonCertificateService.deleteUploadLocalDisk(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result("删除成功");
    }
}
