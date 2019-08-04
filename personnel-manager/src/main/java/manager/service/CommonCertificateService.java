package manager.service;

import manager.mapper.CommonCertificateMapper;
import manager.pojo.CommonCertificate;
import manager.pojo.User;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import util.Code;
import util.PException;

import java.io.*;
import java.lang.reflect.Field;

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
    @Autowired
    private String IMAGE_SERVER_URL;
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
        commonCertificateMapper.updateByPrimaryKey(commonCertificate); //更新操作
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
        commonCertificateMapper.insert(commonCertificate);
    }
    /**
     *@author      473225193    yuanyou
     * @param originalFilename
    * @param inputStream
     * @return      void
     * @exception
     * @date        2019/8/3 14:55
     * @description 图片上传到本地磁盘
     */
    public void uploadLocalDisk(String originalFilename, InputStream inputStream){
        User user = userService.findByJobNumber("002751");
        FileOutputStream fileOutputStream = null;
        try {
            String path = ResourceUtils.getFile("classpath:templates").getPath();
            String suffix = "/upload" + "/"+user.getJobNumber() + " " + user.getName();
            File uploadPath = new File(path + suffix);
            if(!uploadPath.exists()){
                uploadPath.mkdirs();
            }
            File[] files = uploadPath.listFiles();
            for (File file : files) {
                if(originalFilename.equals(file.getName())){
                    throw new PException(Code.USER_EXIST_FILE,"该文件已存在");
                }
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
    }

    public void deleteUploadLocalDisk(String fileName) throws Exception {
        User user = userService.findByJobNumber("002751");
        String path = ResourceUtils.getFile("classpath:templates").getPath();
        String suffix = "/upload" + "/"+user.getJobNumber() + " " + user.getName();
        File uploadPath = new File(path + suffix + "/" + fileName);
        if(!uploadPath.exists()){
            throw new PException(Code.USER_NOT_FILE,"用户没有该文件");
        }
        //删除该文件
        uploadPath.delete();
    }

    public void addUploadCommonCertificate(String originalFilename, String type) {
        CommonCertificate commonCertificate = commonCertificateMapper.selectByPrimaryKey(1);
        //反射动态封装对象
        Class<? extends CommonCertificate> cClass = commonCertificate.getClass();
        String propeties = type + "Image";
        try {
            Field commonField = cClass.getDeclaredField(propeties);
            commonField.setAccessible(true);
            //根据拼接字段，查数据库中是否有该字段
            String attr = (String) commonField.get(propeties);
            if(StringUtils.isEmpty(attr)){
                commonField.set(commonCertificate,IMAGE_SERVER_URL + originalFilename);
            }else{
                //如果有，多个图片之间用该符号[,]，进行隔开
                commonField.set(commonCertificate,attr + "[,]" + IMAGE_SERVER_URL + originalFilename);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        commonCertificateMapper.insertSelective(commonCertificate);
        //如果不存在。进行添加
    }
}
