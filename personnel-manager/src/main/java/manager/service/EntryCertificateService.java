package manager.service;

import manager.mapper.EntryCertificateMapper;
import manager.pojo.EntryCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Description:    入职表单服务层管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 15:02
* @UpdateUser:
* @UpdateDate:     2019/8/1 15:02
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Service
public class EntryCertificateService {
    @Autowired
    private EntryCertificateMapper entryCertificateMapper;

    public List<EntryCertificate> findByList(){
        List<EntryCertificate> entryCertificateList = entryCertificateMapper.selectAll();
        return entryCertificateList;
    }
}

