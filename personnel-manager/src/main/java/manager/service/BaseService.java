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
* @Description:    字典表服务管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 14:22
* @UpdateUser:
* @UpdateDate:     2019/8/1 14:22
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Service
public class BaseService {
    @Autowired
    private BaseMapper baseMapper;
    //根据id查询出:最高学历、最高职称、最高学位、入职状态、人员类型
    /**
     *@author      473225193    yuanyou
     * @param       type
     * @return      java.lang.String
     * @exception
     * @date        2019/8/1 14:58
     * @description 根据字典类型查询选项的值
     */
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
     *@author      473225193    yuanyou
     * @param baseList
     * @return      java.util.List<manager.vo.SelectDataList>
     * @exception
     * @date        2019/8/1 15:00
     * @description 把字典值数据封装成需要的格式数据
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
