package manager.controller;

import manager.bo.SelectOptionData;
import manager.pojo.College;
import manager.service.CollegeService;
import manager.service.DownloadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
    @Autowired
    private CollegeService collegeService;
    //下载文件 .zip
    @GetMapping("upload/{uid}")
    public Result downloadUploadFileToZip(@PathVariable(value = "uid")Long uid, HttpServletResponse response){
        //在下载的时候先删除以前生成的zip文件，防止过渡积压
        downloadService.deleteUploadZipFile();
        File targetZipFile = downloadService.downloadUploadFileToZip(uid);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+targetZipFile.getName());
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

    //导出数据 下载文件 .xlsx
    @PostMapping("export/{cid}/{author}")
    public void export(@PathVariable(value = "cid") Long cid, @PathVariable(value = "author") String author, String jobNumber, String name, String identityCard, HttpServletResponse response){
        College college = collegeService.findByCid(cid);//获取该部门的名称作为文件的名称
        SelectOptionData data = new SelectOptionData(jobNumber,name,identityCard);
        System.out.println(data);
        String fileName = college.getName() + "成员表.xls";
        System.out.println(fileName);
        response.setContentType("application/octet-stream");
        OutputStream outputStream = null;
        try {
            response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes(),"ISO-8859-1"));
            HSSFWorkbook wb = collegeService.export(cid, author, data);

            outputStream = response.getOutputStream();
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
