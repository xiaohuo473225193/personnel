package manager.service;

import manager.mapper.BaseMapper;
import manager.mapper.StageCertificateMapper;
import manager.pojo.Base;
import manager.pojo.StageCertificate;
import manager.pojo.User;
import manager.vo.SelectDataList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.Code;
import util.PException;

import java.util.ArrayList;
import java.util.List;


@Service
public class StageCertificateService {
    @Autowired
    private StageCertificateMapper stageCertificateMapper;

    public StageCertificate findByUser(User user){
        return stageCertificateMapper.selectByPrimaryKey(user);
    }


    public void addStageCertificate(User user){
        StageCertificate stageCertificate = new StageCertificate();
        if(user.getUid() == null){
            throw new PException(Code.ID_NOT_EXIST,"id不存在");
        }
        stageCertificate.setUid(user.getUid());
        stageCertificateMapper.insert(stageCertificate);
    }
}
