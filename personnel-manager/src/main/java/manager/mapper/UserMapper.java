package manager.mapper;

import manager.pojo.User;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author xiaohuo
 * @data 2019/7/15 14:59
 * @description
 */
public interface UserMapper extends Mapper<User>, IdListMapper<User,Long> {
}
