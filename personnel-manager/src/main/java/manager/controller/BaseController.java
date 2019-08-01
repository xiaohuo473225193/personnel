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
 * @author xiaohuo
 * @data 2019/7/15 18:51
 * @description
 */
@RestController
@RequestMapping("base")
public class BaseController {
    @Autowired
    private BaseService baseService;
    @GetMapping("/list/{type}")
    public Result<List<SelectDataList>> findByListAndType(@PathVariable("type") String type){
        List<SelectDataList> listAndType = baseService.findByListAndType(type);
        return new Result<>(listAndType);
    }
}
