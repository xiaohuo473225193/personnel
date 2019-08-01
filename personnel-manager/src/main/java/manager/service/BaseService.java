package manager.service;

import manager.mapper.BaseMapper;
import manager.pojo.Base;
import manager.vo.SelectDataList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohuo
 * @data 2019/7/15 18:30
 * @description
 */
@Service
public class BaseService {
    @Autowired
    private BaseMapper baseMapper;
    //根据id查询出:最高学历、最高职称、最高学位、入职状态、人员类型
    public String findByBid(Long bid){
        Base base = baseMapper.selectByPrimaryKey(bid);
        return base.getItemValue();
    }
    //查询所有选择结果
    public List<SelectDataList> findByListAndType(String type){
        Example example = new Example(Base.class);
        example.setOrderByClause("sort");//desc升序
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemType",type);
        List<Base> baseList = baseMapper.selectByExample(example);

        return getSelectDataList(baseList);
    }
    /**
     * @param baseList
     * @return 封装对象
     */
    private List<SelectDataList> getSelectDataList(List<Base> baseList) {
        List<SelectDataList> list = new ArrayList<>();
        SelectDataList selectDataList = null;
        for (Base base : baseList) {
            selectDataList = new SelectDataList(base.getBid(),base.getItemValue());
            list.add(selectDataList);
        }
        return list;
    }
}
