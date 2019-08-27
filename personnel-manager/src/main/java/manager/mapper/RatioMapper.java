package manager.mapper;

import manager.pojo.EducationRatio;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface RatioMapper  extends Mapper<EducationRatio>, IdListMapper<EducationRatio,Integer> {
}
