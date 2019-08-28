package manager.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
* @Description:    为kindeditor富文本编译器上传图片编写的类
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/24 19:57
* @UpdateUser:
* @UpdateDate:     2019/8/24 19:57
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("upload")
public class UploadFileController {
    @Value("${NEWS_UPLOAD_IMAGE_LOCATION}")
    private String NEWS_UPLOAD_IMAGE_LOCATION;
    @Value("${IMAGE_SERVER_URL_PREFIX}")
    private String IMAGE_SERVER_URL_PREFIX;
    @PostMapping("image")
    public Map<String,Object> upload(@RequestBody MultipartFile uploadFile){
        Map map = new HashMap<>();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
            String path = ResourceUtils.getFile(NEWS_UPLOAD_IMAGE_LOCATION).getPath();
            outputStream = new FileOutputStream(path + "/notice/" + uploadFile.getOriginalFilename());
            IOUtils.copy(inputStream,outputStream);
            //用于图片回显
            String filePath = IMAGE_SERVER_URL_PREFIX + "/notice/" + uploadFile.getOriginalFilename();
            //定义返回数据
            map.put("error",0);
            map.put("url",filePath);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //定义返回数据
        map.put("error",1);
        map.put("message","上传失败");
        return map;
    }
}
