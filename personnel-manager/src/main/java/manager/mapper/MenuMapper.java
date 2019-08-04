package manager.mapper;

import manager.pojo.Menu;
import manager.pojo.News;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
* @Description:
* @Author:         2571169797   yang meng bo
* @CreateDate:     2019/8/3 0003 下午 12:46
* @UpdateUser:
* @UpdateDate:     2019/8/3 0003 下午 12:46
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public interface MenuMapper extends Mapper<Menu>, IdListMapper<Menu,Long> {

}
