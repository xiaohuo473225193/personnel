package manager.service;

import manager.mapper.EntryCertificateMapper;
import manager.pojo.CommonCertificate;
import manager.pojo.EntryCertificate;
import manager.pojo.User;
import org.apache.commons.lang3.StringUtils;
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
        entryCertificate.setComplete("0");
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
        if(entryCertificate.getUid() == null){ //id不能为空
            throw new PException(Code.ID_NOT_EXIST,"非法操作,存在安全隐患");
        }
        findByUid(entryCertificate.getUid());//查询该用户是否存在
        entryCertificateMapper.updateByPrimaryKeySelective(entryCertificate);
        if(isUpdateComplete(entryCertificate.getUid())){
            entryCertificate.setComplete("1");
        }else{
            entryCertificate.setComplete("0");
        }
        entryCertificateMapper.updateByPrimaryKeySelective(entryCertificate);
    }
    //是否全部上传
    public boolean isUpdateComplete(Long id){
        EntryCertificate certificate = findByUid(id);
        if(isBlank(certificate.getEntryAgreeImage()) ||
           isBlank(certificate.getEntryNoticeImage()) ||
           isBlank(certificate.getHireImage()) ||
           isBlank(certificate.getSecrecyImage())){
            return false;
        }
        return true;
    }
    private boolean isBlank(String val){
        return StringUtils.isBlank(val);
    }
}

