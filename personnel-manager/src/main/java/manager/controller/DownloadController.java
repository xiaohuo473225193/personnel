package manager.controller;

import manager.pojo.User;
import manager.service.DownloadService;
import manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.Result;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
* @Description:    各种文件的下载操作
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/5 19:21
* @UpdateUser:
* @UpdateDate:     2019/8/5 19:21
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Controller
@RequestMapping("download")
public class DownloadController {
    @Autowired
    private DownloadService downloadService;
    @GetMapping("upload/{uid}")
    public Result downloadUploadFileToZip(@PathVariable(value = "uid")Long uid, HttpServletResponse response){
        //在下载的时候先删除以前生成的zip文件，防止过渡积压
        downloadService.deleteUploadZipFile();
        File targetZipFile = downloadService.downloadUploadFileToZip(uid);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+targetZipFile.getName());
        System.out.println(response);
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(targetZipFile);
            //用于输出
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) != -1){
                outputStream.write(bytes,0, bytes.length);
            }
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
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Result("下载成功");
    }

}
