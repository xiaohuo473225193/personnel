package manager.mapper;

import manager.pojo.Base;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author xiaohuo
 * @data 2019/7/15 18:29
 * @description
 */
public interface BaseMapper extends Mapper<Base>, IdListMapper<Base,Long> {

}
