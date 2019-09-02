package manager.controller;

import manager.bo.SelectOptionData;
import manager.pojo.College;
import manager.pojo.User;
import manager.service.CollegeService;
import manager.service.UserService;
import manager.vo.CollegeUser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import util.PageResult;
import util.Result;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
* @Description:    部门基本信息的管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 14:36
* @UpdateUser:
* @UpdateDate:     2019/8/1 14:36
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("college")
public class CollegeController {
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private UserService userService;

    /**
     *@author      473225193    yuanyou
     * @param
     * @return      util.Result<java.util.List<manager.pojo.College>>
     * @exception
     * @date        2019/8/1 14:47
     * @description 查询所有部门信息
     */
    @GetMapping("list")
    public Result<List<College>> findByList(){
        List<College> collegeServiceByList = collegeService.findByList();
        return new Result<>(collegeServiceByList);
    }
    /**
     * @author      2571169797   yang meng bo
     * @param
     * @return      util.Result<java.util.List<manager.pojo.User>>
     * @exception
     * @date        2019/8/2 0002 上午 10:18
     * @description 提供给二级权限用户使用的 -----> 根据该用户id获取该部门下的员工数据     */
    @PostMapping("findCollegeUserListByUid/{userId}/{rows}/{size}")
    public PageResult<CollegeUser> findCollegeUserListByUid(@PathVariable(value = "userId")Long userId,
                                                            @RequestBody SelectOptionData data,
                                                            @PathVariable(value = "size")int size,
                                                            @PathVariable(value = "rows")int rows){
        User user = userService.findByUid(userId);
        Long cid = user.getCid();
        return collegeService.findCollegeUserListByCid(cid, data, rows, size);
    }

    @PostMapping("findCollegeUserListByCid/{cid}/{rows}/{size}")
    public PageResult<CollegeUser> findCollegeUserListByCid(@PathVariable(value = "cid")Long cid,
                                                            @RequestBody SelectOptionData data,
                                                            @PathVariable(value = "size")int size,
                                                            @PathVariable(value = "rows")int rows){
        return collegeService.findCollegeUserListByCid(cid, data, rows, size);
    }
    @PostMapping("addCollege")
    public Result addCollege(@RequestBody College college){
        collegeService.addCollege(college);
        return new Result(null);
    }

    @DeleteMapping("deleteCollege/{id}")
    public Result deleteCollege(@PathVariable(value = "id")long id ){
        collegeService.deleteCollege(id);
        return new Result(null);
    }

    @PutMapping("updateCollege")
    public Result updateCollege(@RequestBody College College){
        collegeService.updateCollege(College);
        return new Result(null);
    }
    @GetMapping("getName/{cid}")
    public Result getCollegeName(@PathVariable(value = "cid")Long cid){
        Map<String,String> map = collegeService.getCollegeName(cid);
        return new Result(map);
    }

    @PutMapping("findCollege")
    public Result<List<College>> findCollege(){
        List<College> collegeList = collegeService.findCollege();
        return new Result(collegeList);
    }

    @PostMapping("import")
    public Result importData(@RequestBody MultipartFile file){
        InputStream inputStream = null;
        System.out.println(file.getOriginalFilename() + "   ---->   "+ file.getContentType());
        //xlsx类型形式的文件 2010以上的版本
        if(file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            if(!file.getOriginalFilename().contains(".xlsx")){
                return new Result("文件格式错误");
            }
            try {
                inputStream = file.getInputStream();
                //提供不同的实现类，实现多态
                collegeService.importData(new XSSFWorkbook(inputStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(file.getContentType().equals("application/vnd.ms-excel")) {//xls类型的文件 2010以下包括2010的版本
            if(!file.getOriginalFilename().contains(".xls")){
                return new Result("文件格式错误");
            }
            try {
                inputStream = file.getInputStream();
                //提供不同的实现类，实现多态
                collegeService.importData(new HSSFWorkbook(inputStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Result("导入成功");
    }

}
