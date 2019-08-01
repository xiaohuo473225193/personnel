package manager.mapper;

import manager.pojo.College;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author xiaohuo
 * @data 2019/7/15 19:59
 * @description
 */
public interface CollegeMapper extends Mapper<College>, IdListMapper<College,Long> {
}
