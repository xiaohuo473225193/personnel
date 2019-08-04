package manager.controller;

import manager.pojo.Menu;
import manager.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.Result;

import java.util.List;

/**
 * Created by Administrator on 2019/8/3 0003.
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping("addMenu")
    public Result addMenu(@RequestBody Menu menu){
        menuService.addMenu(menu);
        return new Result(null);
    }

    @DeleteMapping("deleteMenu/{id}")
    public Result deleteMenu(@PathVariable(value = "id")long id ){
        menuService.deleteMenu(id);
        return new Result(null);
    }

    @PutMapping("updateMenu")
    public Result updateMenu(@RequestBody Menu menu){
        menuService.updateMenu(menu);
        return new Result(null);
    }

    @GetMapping("findMenu/{author}")
    public Result<Menu> findMenu(@PathVariable(value = "author")String author){
        List<Menu> menus = menuService.findMenu(author);
        return new Result(menus);
    }

}
