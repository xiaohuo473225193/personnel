package manager.service;

import manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import util.*;

import java.io.File;
import java.io.FileNotFoundException;

@Service
public class DownloadService {
    @Autowired
    private UserService userService;
    @Value("${LOCAL_DISK_LOCATION}")
    private String LOCAL_DISK_LOCATION;

    public File downloadUploadFileToZip(Long uid) {
        User user = userService.findByUid(uid);
        String path = null;
        try {
            path = ResourceUtils.getFile(LOCAL_DISK_LOCATION).getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String suffix = "/upload" + "/"+user.getJobNumber()+user.getName();
        File uploadPath = new File(path + suffix);
        if(!uploadPath.exists()){
            throw new PException(Code.FILE_NOT_EXIST,"该文件不存在");
        }
        String randomFileName = UUID32.random() + ".zip";
        File targetZipPath = new File(path + "/zip");
        if(!targetZipPath.exists()){
            targetZipPath.mkdir();
        }
        File targetZipFile = new File(path + "/zip",randomFileName);
        //压缩
        ZipUtil.zip(uploadPath,targetZipFile);
        return targetZipFile;
    }

    public void deleteUploadZipFile() {
        String path = null;
        try {
            path = ResourceUtils.getFile(LOCAL_DISK_LOCATION).getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File targetZipPath = new File(path + "/zip");
        if(targetZipPath.exists()){
            FileUtil.deleteDirectoryOfAllFile(targetZipPath);
        }
    }
}
