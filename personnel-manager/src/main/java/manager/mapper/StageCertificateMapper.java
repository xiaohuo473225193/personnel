package manager.mapper;

import manager.pojo.StageCertificate;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author yangmengbo
 * @data 2019/7/15 18:29
 * @description
 */
public interface StageCertificateMapper extends Mapper<StageCertificate>, IdListMapper<StageCertificate,Long> {

}
