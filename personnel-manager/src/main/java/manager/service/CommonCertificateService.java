package manager.service;

import manager.mapper.CommonCertificateMapper;
import manager.pojo.CommonCertificate;
import manager.pojo.User;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import util.Code;
import util.PException;

import java.io.*;

/**
* @Description:    常规证书服务层操作
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 16:12
* @UpdateUser:
* @UpdateDate:     2019/8/1 16:12
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Service
public class CommonCertificateService {
    @Autowired
    private CommonCertificateMapper  commonCertificateMapper;
    @Autowired
    private UserService userService;
    @Value("${IMAGE_SERVER_URL_PREFIX}")
    private String IMAGE_SERVER_URL_PREFIX;
    @Value("${LOCAL_DISK_LOCATION}")
    private String LOCAL_DISK_LOCATION;
    /**
     *@author      473225193    yuanyou
     * @param commonCertificate
     * @return      void
     * @exception
     * @date        2019/8/1 16:23
     * @description 更新数据库中用户的常规证件信息
     */
    public void updateCommonCertificate(CommonCertificate commonCertificate){
        if(commonCertificate.getUid() == null){ //id不能为空
            throw new PException(Code.ID_NOT_EXIST,"非法操作,存在安全隐患");
        }
        CommonCertificate certificate = commonCertificateMapper.selectByPrimaryKey(commonCertificate.getUid());
        if(certificate == null){ //用户信息要存在
            throw new PException(Code.USER_NOT_EXIST,"该用户用户不存在");
        }
        commonCertificateMapper.updateByPrimaryKeySelective(commonCertificate);//选择性更新操作
        if(isUpdateComplete(commonCertificate.getUid())){
            commonCertificate.setComplete("1");
        }else{
            commonCertificate.setComplete("0");
        }
        //再次更新
        commonCertificateMapper.updateByPrimaryKeySelective(commonCertificate);//选择性更新操作
    }
    //判断文件是否全部上传完成，如果完成更新值
    public boolean isUpdateComplete(Long id){
        CommonCertificate certificate = commonCertificateMapper.selectByPrimaryKey(id);
        if(isBlank(certificate.getBackgroundImage()) ||
           isBlank(certificate.getDegreeImage()) ||
           isBlank(certificate.getEducationImage()) ||
           isBlank(certificate.getEmployImage()) ||
           isBlank(certificate.getIdentityImage()) ||
           isBlank(certificate.getPostImage())){
            return false;
        }
        return true;
    }
    private boolean isBlank(String val){
        return StringUtils.isBlank(val);
    }
    public CommonCertificate findByUser(User user){
        return commonCertificateMapper.selectByPrimaryKey(user);
    }

    public void addCommonCertificate(User user){
        CommonCertificate commonCertificate = new CommonCertificate();
        if(user.getUid() == null){
            throw new PException(Code.ID_NOT_EXIST,"id不存在");
        }
        commonCertificate.setUid(user.getUid());
        commonCertificate.setComplete("0");
        commonCertificateMapper.insert(commonCertificate);
    }
    /**
     *@author      473225193    yuanyou
     * @param jobNumber
     * @param originalFilename
    * @param inputStream
     * @return      void
     * @exception
     * @date        2019/8/3 14:55
     * @description 图片上传到本地磁盘
     */
    public String uploadLocalDisk(Long uid, String originalFilename, InputStream inputStream){
        User user = userService.findByUid(uid);
        FileOutputStream fileOutputStream = null;
        String suffix = null;
        try {
            String path = ResourceUtils.getFile(LOCAL_DISK_LOCATION).getPath();
            suffix = "/upload" + "/"+user.getJobNumber() + user.getName();
            File uploadPath = new File(path + suffix);
            if(!uploadPath.exists()){
                uploadPath.mkdirs();
            }
            String uploadFullName = uploadPath.getPath() + "/" + originalFilename;
            fileOutputStream = new FileOutputStream(uploadFullName);
            //流的拷贝
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //返回目标文件存放的路径
        String targetFilePath = IMAGE_SERVER_URL_PREFIX + suffix + "/" + originalFilename;
        return targetFilePath;
    }

    public void deleteUploadLocalDisk(Long uid, String fileName) throws Exception {
        User user = userService.findByUid(uid);
        String path = ResourceUtils.getFile("classpath:templates").getPath();
        String suffix = "/upload" + "/"+user.getJobNumber() + user.getName();
        File uploadPath = new File(path + suffix + "/" + fileName);
        if(!uploadPath.exists()){
            throw new PException(Code.USER_NOT_FILE,"用户没有该文件");
        }
        //删除该文件
        uploadPath.delete();
    }

    public CommonCertificate findByUid(Long uid) {
        CommonCertificate certificate = commonCertificateMapper.selectByPrimaryKey(uid);
        if(certificate == null){
            throw new PException(Code.USER_NOT_EXIST,"用户不存在");
        }
        return certificate;
    }
}
