package manager.controller;

import manager.pojo.Menu;
import manager.service.MenuService;
import manager.vo.MenuList;
import manager.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.Result;

import java.util.*;

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
    //根据id加载出二级菜单
    @GetMapping("findMenu/{uid}")
    public Result<MenuList> findMenu(@PathVariable(value = "uid") Long uid){
        List<MenuList> menus = menuService.findMenu(uid);
        return new Result(menus);
    }
    //根据所给菜单id，查询下一级菜单
    @GetMapping("loadMenu/{id}")
    public Result<List<TreeNode>> loadMenuById(@PathVariable(value = "id") Long id){
        List<TreeNode> nodes = menuService.loadTreeNodeById(id.toString());
        return new Result<>(nodes);
    }
    //异步加载部门的菜单,首先加载父节点
    @GetMapping("load/parent")
    public Result<List<TreeNode>> loadParentTreeMenu(){
        List<TreeNode> nodes = menuService.loadParentTreeMenu();
        return new Result<>(nodes);
    }
    //根据父id加载子节点
    @PostMapping("load/node")
    public Result loadTreeNodeById(String id){
        List<TreeNode> nodes = menuService.loadTreeNodeById(id);
        return new Result<>(nodes);
    }
    @DeleteMapping("delete/{id}")
    public Result removeMenu(@PathVariable(value = "id") Long id){
        menuService.removeMenu(id);
        return new Result("删除成功");
    }
    @PutMapping("rename/{id}/{name}")
    public Result renameMenu(@PathVariable(value = "id") Long id, @PathVariable(value = "name") String name){
        menuService.renameMenu(id,name);
        return new Result("修改成功");
    }
    @PutMapping("drop/{sourceId}/{targetId}")
    public Result dropMenu(@PathVariable(value = "sourceId") Long sourceId, @PathVariable(value = "targetId") Long targetId){
        menuService.dropMenu(sourceId,targetId);
        return new Result("移动成功");
    }
    @PostMapping("add/{pid}/{id}/{name}")
    public Result addMenuNode(@PathVariable(value = "pid") Long pid, @PathVariable(value = "id") Long id, @PathVariable(value = "name") String name){
        Map<String,Object> map = menuService.addMenuNode(pid,id,name);
        return new Result(map);
    }
    //新目录的名称，以及几级权限可见
    @PostMapping("root/{name}")
    public Result addRootMenuNode(@PathVariable(value = "name") String name){
        Map<String,Object> map = menuService.addRootMenuNode(name);
        return new Result(map);
    }
}
