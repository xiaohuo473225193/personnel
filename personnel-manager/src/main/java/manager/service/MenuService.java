package manager.service;

import manager.mapper.MenuMapper;

import manager.pojo.Menu;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.Code;
import util.PException;
import util.PageResult;

import java.util.List;

/**
 * Created by Administrator on 2019/8/3 0003.
 */

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

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

    public List<Menu> findMenu(String author){
        if(StringUtils.isBlank(author)){
            throw new PException(Code.MENU_NOT_EXIST,"新闻信息不存在");
        }
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        if("3".equals(author)){
            criteria.orEqualTo("author","3");
            criteria.orEqualTo("author","2");
            criteria.orEqualTo("author","1");
        }
        if("2".equals(author)){
            criteria.orEqualTo("author","2");
            criteria.orEqualTo("author","1");
        }
       if("1".equals(author)){
           criteria.andEqualTo("author","1");
       }
        return   menuMapper.selectByExample(example);
    }
}
