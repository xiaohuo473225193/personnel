package manager.mapper;

import manager.pojo.EntryCertificate;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author xiaohuo
 * @data 2019/7/15 12:45
 * @description
 */
public interface EntryCertificateMapper extends Mapper<EntryCertificate>, IdListMapper<EntryCertificate,Long> {

}
