package manager.service;

import manager.mapper.BaseMapper;
import manager.mapper.StageCertificateMapper;
import manager.pojo.Base;
import manager.pojo.StageCertificate;
import manager.pojo.User;
import manager.vo.SelectDataList;
import org.apache.commons.lang3.StringUtils;
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
        stageCertificate.setComplete("0");
        stageCertificateMapper.insert(stageCertificate);
    }

    public StageCertificate findByUid(Long uid) {
        StageCertificate stageCertificate = stageCertificateMapper.selectByPrimaryKey(uid);
        if(stageCertificate == null){
            throw new PException(Code.USER_NOT_EXIST,"用户不存在");
        }
        return stageCertificate;
    }

    public void updateUploadStageCertificate(StageCertificate stageCertificate) {
        if(stageCertificate.getUid() == null){ //id不能为空
            throw new PException(Code.ID_NOT_EXIST,"非法操作,存在安全隐患");
        }
        findByUid(stageCertificate.getUid());
        int update = stageCertificateMapper.updateByPrimaryKeySelective(stageCertificate);
        if(update != 1){
            throw new PException(Code.FAIL_HANDLER,"操作失败");
        }
        if(isUpdateComplete(stageCertificate.getUid())){
            stageCertificate.setComplete("1");
        }else{
            stageCertificate.setComplete("0");
        }
        stageCertificateMapper.updateByPrimaryKeySelective(stageCertificate);
    }
    public boolean isUpdateComplete(Long id){
        StageCertificate certificate = findByUid(id);
        if(isBlank(certificate.getApplyImage()) ||
            isBlank(certificate.getAppointImage()) ||
            isBlank(certificate.getAssessImage()) ||
            isBlank(certificate.getAwardDispositionImage()) ||
            isBlank(certificate.getChangeApproverImage()) ||
            isBlank(certificate.getHonorImage()) ||
            isBlank(certificate.getLeaveReportImage()) ||
            isBlank(certificate.getPostEvaluateImage()) ||
            isBlank(certificate.getStageEvaluateImage()) ||
            isBlank(certificate.getSynthesizeEvaluteImage()) ||
            isBlank(certificate.getTarnsferApproverImage()) ||
            isBlank(certificate.getTrainApplyImage()) ||
            isBlank(certificate.getTrainServiceImage()) ||
            isBlank(certificate.getWorkSummaryImage()) ||
            isBlank(certificate.getYearInterviewImage())){
                return false;
        }
        return true;
    }
    private boolean isBlank(String val){
        return StringUtils.isBlank(val);
    }
}
