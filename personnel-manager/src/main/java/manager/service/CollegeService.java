package manager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import manager.mapper.CollegeMapper;
import manager.pojo.*;
import manager.vo.CollegeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.PageResult;

import java.util.ArrayList;
import java.util.List;

/**
* @Description:    部门服务层管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 15:01
* @UpdateUser:
* @UpdateDate:     2019/8/1 15:01
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Service
public class CollegeService {
    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private CommonCertificateService commonCertificateService;
    @Autowired
    private EntryCertificateService entryCertificateService;
    @Autowired
    private StageCertificateService stageCertificateService;

    public List<College> findByList(){
        return collegeMapper.selectAll();
    }
    
    public PageResult<CollegeUser> findCollegeUserListByCid(Long cid,int rows,int size){
        PageHelper.startPage(rows,size);
        //根据cid找到user集合
        Page<User> users = (Page<User>)userService.findByCid(cid);
        List<User> userList = users.getResult();

        List<CollegeUser> collegeUsers = new ArrayList<CollegeUser>();
        for (User user : userList) {
            CollegeUser collegeUser = userPackaging(user);
            collegeUsers.add(collegeUser);
        }
        return new PageResult<CollegeUser>(users.getTotal(),collegeUsers);
    }

    private CollegeUser userPackaging(User user){
        CollegeUser collegeUser = new CollegeUser();
        collegeUser.setUid(user.getUid());
        collegeUser.setName(user.getName());
        collegeUser.setJobNumber(user.getJobNumber());
        collegeUser.setCid(user.getCid());
        collegeUser.setAuthor(user.getAuthor());
        collegeUser.setPassword(user.getPassword());
        collegeUser.setTypeId(user.getTypeId());
        collegeUser.setPosition(user.getPosition());
        collegeUser.setSex(user.getSex());
        collegeUser.setDegreeId(user.getDegreeId());
        collegeUser.setJobTitle(user.getJobTitle());
        collegeUser.setJobNumber(user.getJobNumber());
        collegeUser.setBirthday(user.getBirthday());
        collegeUser.setAddress(user.getAddress());
        collegeUser.setAudit(user.getAudit());
        collegeUser.setComplete(user.getComplete());
        collegeUser.setEducationId(user.getEducationId());
        collegeUser.setEndTime(user.getEndTime());
        collegeUser.setEntryStatusId(user.getEntryStatusId());
        collegeUser.setGraduateTime(user.getGraduateTime());
        collegeUser.setIdentityCard(user.getIdentityCard());
        collegeUser.setStartTime(user.getStartTime());
        collegeUser.setStatus(user.getStatus());
        collegeUser.setTelephone(user.getTelephone());

        CommonCertificate commonCertificate = commonCertificateService.findByUser(user);
        EntryCertificate entryCertificate = entryCertificateService.findByUser(user);
        StageCertificate  stageCertificate = stageCertificateService.findByUser(user);

        collegeUser.setCommonComplete(commonCertificate.getComplete());
        collegeUser.setEntryComplete(entryCertificate.getComplete());
        collegeUser.setStageComplete(stageCertificate.getComplete());
        return  collegeUser;
    }
}
