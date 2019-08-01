package manager.mapper;

import manager.pojo.CommonCertificate;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CommonCertificateMapper extends Mapper<CommonCertificate>, IdListMapper<CommonCertificate,Long> {
}
