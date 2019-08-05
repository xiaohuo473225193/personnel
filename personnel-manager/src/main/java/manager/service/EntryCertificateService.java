package manager.service;

import manager.mapper.EntryCertificateMapper;
import manager.pojo.EntryCertificate;
import manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Code;
import util.PException;

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

    public EntryCertificate findByUser(User user){
        return entryCertificateMapper.selectByPrimaryKey(user);
    }

    public void addEntryCertificate(User user){
        EntryCertificate entryCertificate = new EntryCertificate();
        if(user.getUid() == null){
            throw new PException(Code.ID_NOT_EXIST,"id不存在");
        }
        entryCertificate.setUid(user.getUid());
        entryCertificateMapper.insert(entryCertificate);
    }

    public EntryCertificate findByUid(Long uid) {
        EntryCertificate entryCertificate = entryCertificateMapper.selectByPrimaryKey(uid);
        if(entryCertificate == null){
            throw new PException(Code.USER_NOT_EXIST,"用户不存在");
        }
        return entryCertificate;
    }

    public void updateUploadEntryCertificate(EntryCertificate entryCertificate) {
        findByUid(entryCertificate.getUid());//查询该用户是否存在
        entryCertificateMapper.updateByPrimaryKeySelective(entryCertificate);
    }
}

