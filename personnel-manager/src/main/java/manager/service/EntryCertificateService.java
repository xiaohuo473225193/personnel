package manager.service;

import manager.mapper.EntryCertificateMapper;
import manager.pojo.EntryCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaohuo
 * @data 2019/7/15 13:10
 * @description
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

