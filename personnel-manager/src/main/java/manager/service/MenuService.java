package manager.service;

import manager.mapper.MenuMapper;

import manager.pojo.College;
import manager.pojo.Menu;

import manager.pojo.User;
import manager.vo.MenuList;
import manager.vo.MenuNodeData;
import manager.vo.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import util.Code;
import util.PException;
import util.PageResult;

import java.util.*;

/**
 * Created by Administrator on 2019/8/3 0003.
 */

@Service
public class MenuService {
    @Value("${TREENODE_PAGE_PATH}")
    private String TREENODE_PAGE_PATH;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CollegeService collegeService;

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
        criteria.andEqualTo("status","1");//状态
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
        for (Menu menu : menus) {//查询对应的第二级菜单
            Example example = new Example(Menu.class);
            Example.Criteria criteria = example.createCriteria();
            //进行模糊查询
            criteria.andLike("pid","%"+menu.getId()+"%");
            criteria.andEqualTo("status","1");//状态
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
        for (Menu menu : menuList) {//遍历每一个list节点的数据
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
    public List<TreeNode> loadParentTreeMenu() {
        //判断当前用户是否符合权限
        checkUserAuthor();
        Menu menu = new Menu();
        menu.setPid("0");
        menu.setStatus("1");
        menu.setAuthor("3");
        List<Menu> menus = menuMapper.select(menu);
        List<TreeNode> treeNodes = getTreeNodesByMenu(menus);
        return treeNodes;
    }
    private List<TreeNode> getTreeNodesByMenu(List<Menu> menus){
        List<TreeNode> targetNodes = new ArrayList<>();
        TreeNode node = null;
        for (Menu menu : menus) {
            node = new TreeNode();
            node.setId(menu.getId().toString());
            //是否为父节点，0为父节点
            if("1".equals(menu.getIsParent())){
                node.setIsParent("true");
            }else{
                node.setIsParent("false");
            }
            node.setUrl(menu.getUrl());
            node.setName(menu.getText());
            node.setOpen("false");
            node.setPid(menu.getPid());
            targetNodes.add(node);
        }
        return targetNodes;
    }
    private void checkUserAuthor(){
        User user = userService.get();
        if(!"3".equals(user.getAuthor())){
            throw new PException(Code.AUTHOR_ERROR,"权限不足");
        }
    }
    public List<TreeNode> loadTreeNodeById(String id) {
        checkUserAuthor();
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        //模糊查询
        criteria.andLike("pid","%"+id+"%");
        criteria.andEqualTo("status","1");
        List<Menu> menuList = menuMapper.selectByExample(example);
        //也可能为空
        if(!CollectionUtils.isEmpty(menuList)){
            //去掉可能出错的模糊查询
            packageUserMenu(menuList,userService.get());
            menuList = cancelErrorResult(menuList,Long.valueOf(id));
            List<TreeNode> treeNodes = getTreeNodesByMenu(menuList);
            for (TreeNode treeNode : treeNodes) {
                treeNode.setPid(id);
            }
            return treeNodes;
        }
        return null;
    }
    private Menu getMenuAndCheckUserAuthor(Long id){
        checkUserAuthor();
        //菜单和部门之间是一一对应的，每一个部门对应一个菜单
        Menu menu = menuMapper.selectByPrimaryKey(id);
        if(menu == null){
            throw new PException(Code.MENU_NOT_EXIST,"该菜单不存在");
        }
        return menu;
    }
    public void removeMenu(Long id) {
        Menu menu = getMenuAndCheckUserAuthor(id);
        menu.setStatus("0");
        menuMapper.updateByPrimaryKeySelective(menu);
        collegeService.deleteCollege(menu.getCid());
    }

    public void renameMenu(Long id, String name) {
        Menu menu = getMenuAndCheckUserAuthor(id);
        menu.setText(name);
        menuMapper.updateByPrimaryKeySelective(menu);
        //该菜单是否在部门中有对应的
        if(collegeService.isExistCid(menu.getCid())){
            //在更新名称的时候，也修改该部门的名称
            College college = new College();
            college.setCid(menu.getCid());
            college.setName(name);
            collegeService.updateCollege(college);
        }
    }

    public void dropMenu(Long sourceId, Long targetId) {
        Menu menu = getMenuAndCheckUserAuthor(sourceId);
        //查询父类是否是 父节点，如果不是更新为父节点
        setIsParentNode(targetId);
        String pid = menu.getPid();
        pid = pid.substring(0,pid.indexOf(",") + 1);
        menu.setPid(pid + targetId);
        menuMapper.updateByPrimaryKeySelective(menu);
    }
    private void setIsParentNode(Long id){
        Menu parentMenu = menuMapper.selectByPrimaryKey(id);
        if(!"1".equals(parentMenu.getIsParent())){
            parentMenu.setIsParent("1");
            menuMapper.updateByPrimaryKeySelective(parentMenu);
        }
    }
    //添加节点
    public Map<String, Object> addMenuNode(Long pid, Long id, String name) {
        checkUserAuthor();
        //查询父类是否是 父节点，如果不是更新为父节点
        setIsParentNode(pid);
        Map<String, Object> map = new HashMap<>();
        Menu menu = new Menu();
        menu.setPid("6,"+pid);
        menu.setText(name);
        menu.setStatus("1");
        menu.setAuthor("2,3");//只要是添加部门的权限都为 2,3 固定格式

        College college = new College();//生成新的部门
        college.setName(name);
        College newCollege = collegeService.addCollege(college);

        menu.setCid(newCollege.getCid());//新添加的部门id
        //添加时间戳，防止请求缓存
        Date timetamp = new Date();
        menu.setUrl(TREENODE_PAGE_PATH + "?_t=" + timetamp.getTime() + "#?collegeId=" + newCollege.getCid());
        menu.setIsParent("0");
        menuMapper.insert(menu);

        map.put("id",menu.getId());//依靠数据库生成唯一的id
        map.put("name",menu.getText());
        return map;
    }
    //只是个新目录，无需创建对应部门
    public Map<String, Object> addRootMenuNode(String name) {
        checkUserAuthor();
        Map<String, Object> map = new HashMap<>();
        Menu menu = new Menu();
        menu.setPid("0");
        menu.setText(name);
        menu.setStatus("1");
        menu.setAuthor("3");//只要是添加部门的权限都为 2,3 固定格式
        menu.setIsParent("1");

        menuMapper.insert(menu);
        map.put("id",menu.getId());//依靠数据库生成唯一的id
        map.put("name",menu.getText());
        return map;
    }
    //查询出上一级的父类
    public Menu findParentById(Long cid) {
        Menu menu = menuMapper.selectByPrimaryKey(cid);
        if(menu == null){
            throw new PException(Code.MENU_NOT_EXIST,"菜单不存在");
        }
        return menu;
    }
}
