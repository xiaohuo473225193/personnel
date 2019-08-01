package manager.service;

import manager.mapper.CommonCertificateMapper;
import manager.pojo.CommonCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Code;
import util.PException;

/**
* @Description:    常规证书服务层操作
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 16:12
* @UpdateUser:
* @UpdateDate:     2019/8/1 16:12
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Service
public class CommonCertificateService {
    @Autowired
    private CommonCertificateMapper  commonCertificateMapper;
    /**
     *@author      473225193    yuanyou
     * @param commonCertificate
     * @return      void
     * @exception
     * @date        2019/8/1 16:23
     * @description 更新数据库中用户的常规证件信息
     */
    public void updateCommonCertificate(CommonCertificate commonCertificate){
        if(commonCertificate.getUid() == null){ //id不能为空
            throw new PException(Code.ID_NOT_EXIST,"非法操作,存在安全隐患");
        }
        CommonCertificate certificate = commonCertificateMapper.selectByPrimaryKey(commonCertificate.getUid());
        if(certificate == null){ //用户信息要存在
            throw new PException(Code.USER_NOT_EXIST,"该用户用户不存在");
        }
        commonCertificateMapper.updateByPrimaryKey(commonCertificate); //更新操作
    }
}
