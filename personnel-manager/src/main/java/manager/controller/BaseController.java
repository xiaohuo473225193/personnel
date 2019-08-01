package manager.controller;

import lombok.AllArgsConstructor;
import manager.service.BaseService;
import manager.vo.SelectDataList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Result;

import java.util.List;

/**
* @Description:    字典表信息的管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 14:36
* @UpdateUser:
* @UpdateDate:     2019/8/1 14:36
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@RestController
@RequestMapping("base")
public class BaseController {
    @Autowired
    private BaseService baseService;
    /**
     *@author      473225193    yuanyou
     * @param       type
     * @return      util.Result<java.util.List<manager.vo.SelectDataList>>
     * @exception
     * @date        2019/8/1 14:45
     * @description 根据字典类型查询相应的字典值
     */
    @GetMapping("/list/{type}")
    public Result<List<SelectDataList>> findByListAndType(@PathVariable("type") String type){
        List<SelectDataList> listAndType = baseService.findByListAndType(type);
        return new Result<>(listAndType);
    }
}
