package manager.service;

import manager.mapper.MenuMapper;

import manager.pojo.Menu;

import manager.pojo.User;
import manager.vo.MenuList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.Code;
import util.PException;
import util.PageResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2019/8/3 0003.
 */

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserService userService;

    public void addMenu(Menu menu){

        if(menuMapper.selectByPrimaryKey(menu) != null){
            throw new PException(Code.MENU_EXIST,"菜单信息已存在");
        }
        menu.setAuthor("1");
        menuMapper.insert(menu);
    }

    public void deleteMenu(Long id){
        if(id == null){
            throw new PException(Code.MENU_NOT_EXIST,"新闻信息不存在");
        }
        menuMapper.deleteByPrimaryKey(id);
    }

    public void updateMenu(Menu menu){
        if(menuMapper.selectByPrimaryKey(menu) == null){
            throw new PException(Code.MENU_NOT_EXIST,"新闻信息不存在");
        }
        menuMapper.updateByPrimaryKey(menu);
    }

    public List<MenuList> findMenu(Long uid){
        User user = userService.findByUid(uid);
        //首先根据pid为0和权限查询出该用户的菜单
        Example example = new Example(Menu.class);
        example.setOrderByClause("sort");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid",0);
        if(user.getAuthor().equals("1")){
            criteria.andEqualTo("author",1);
        }else if(user.getAuthor().equals("2")){
            criteria.andIn("author", Arrays.asList(1,2));
        }else{
            criteria.andIn("author", Arrays.asList(1,3));
        }
        //得到需要的菜单类型
        List<Menu> menus = menuMapper.selectByExample(example);
        List<MenuList> menuLists = packageUserMenu(menus,user);

        return menuLists;
    }
    /***
     *  得到的类型为权限格式：
     *      [{"个人基本信息",[{"个人信息"},{"常规证件"}...]},...]
     */

    private List<MenuList> packageUserMenu(List<Menu> menus,User user) {
        List<MenuList> menuLists = new ArrayList<>();
        MenuList targetMenu = null;
        //子菜单集合
        List<Menu> menuList = null;
        //根据父级菜单，查询子菜单
        Menu option = null;
        for (Menu menu : menus) {
            Example example = new Example(Menu.class);
            Example.Criteria criteria = example.createCriteria();
            //进行模糊查询
            criteria.andLike("pid","%"+menu.getId()+"%");

            //如果是二级权限的话，还需要对部门id进行判断,一级和三级不需要
            if(menu.getAuthor().equals(user.getAuthor()) && "2".equals(user.getAuthor())){
                criteria.andEqualTo("cid",user.getCid());
            }
            //得到子菜单
            menuList = menuMapper.selectByExample(example);
            //去掉可能出错的模糊查询
            menuList = cancelErrorResult(menuList,menu.getId());
            //封装对象
            targetMenu = new MenuList(menu.getText(),menuList);
            menuLists.add(targetMenu);
        }
        return menuLists;
    }

    private List<Menu> cancelErrorResult(List<Menu> menuList, Long id) {
        List<Menu> successMenu = new ArrayList<>();
        for (Menu menu : menuList) {
            String pid = menu.getPid();
            //是否包含逗号
            if(pid.contains(",")){
                String[] fields = pid.split(",");
                for (String field : fields) {
                    if(field.equals(id+"")){
                        successMenu.add(menu);//添加，该结果包含这个id
                    }
                }
            }else{
                if(pid.equals(id+"")){
                    successMenu.add(menu);//添加，该结果包含这个id
                }
            }
        }
        return successMenu;
    }


}
