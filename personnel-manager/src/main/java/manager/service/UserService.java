package manager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import manager.bo.Keyword;
import manager.bo.SelectOptionData;
import manager.mapper.UserMapper;
import manager.pojo.College;
import manager.pojo.User;
import manager.vo.CollegeUser;
import manager.vo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import util.Code;
import util.PException;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.*;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @Description:    用户服务层管理
* @Author:         473225193    yuanyou
* @CreateDate:     2019/8/1 15:02
* @UpdateUser:
* @UpdateDate:     2019/8/1 15:02
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private CommonCertificateService commonCertificateService;
    @Autowired
    private EntryCertificateService entryCertificateService;
    @Autowired
    private StageCertificateService stageCertificateService;
    /**
     * @author      2571169797   yang meng bo
     * @param user
     * @return      util.Result
     * @exception
     * @date        2019/8/1 0001 下午 16:15
     * @description  更新个人信息
     */
    public void save(User user){
        if(user.getUid() == null) {
            throw new PException(Code.ID_NOT_EXIST,"非法操作");
        }
        User findUser = userMapper.selectByPrimaryKey(user.getUid());
        if(findUser == null){
            throw new PException(Code.USER_NOT_EXIST,"非法操作");
        }
       /* if(StringUtils.isBlank("")){

        }*/
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<User> findByCid(Long cid, SelectOptionData data){

        if(cid == null){
            throw new PException(Code.ID_NOT_EXIST,"ID不存在");
        }
        Example example = new Example(User.class);
        example.setOrderByClause("sort desc");//降序
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        criteria.andEqualTo("cid" ,cid);

        if(StringUtils.isNotBlank(data.getJobNumber())){
            criteria.andLike("jobNumber","%"+data.getJobNumber()+"%");
        }
        if(StringUtils.isNotBlank(data.getName())){
            criteria.andLike("name","%"+data.getName()+"%");
        }
        if(StringUtils.isNotBlank(data.getIdentityCard())){
            criteria.andLike("identityCard","%"+data.getIdentityCard()+"%");
        }

        List<User> users = userMapper.selectByExample(example);
        return users;
    }
    /**
     * @author      2571169797   yang meng bo
     * @param user
     * @return      void
     * @exception
     * @date        2019/8/2 0002 下午 13:59
     * @description 根据增加数据
     */
    public void addUser(User user,String author){
        User user1 = new User();
        user1.setJobNumber(user.getJobNumber());
        if(userMapper.selectOne(user1) != null ){
            throw new PException(Code.USER_EXIST,"用户已存在");
        }
        user.setAuthor(author);
        user.setPassword(MD5Util.md5(user.getJobNumber(),user.getJobNumber()));
        user.setAudit("1");
        user.setComplete("0");
        user.setStatus("1");
        userMapper.insert(user);
    }

    /**
     * @author      2571169797   yang meng bo
     * @param uids
     * @return      void
     * @exception
     * @date        2019/8/2 0002 下午 14:00
     * @description 根据uid删除数据
     */
    public void deleteUser(List<Long> uids){
        for (Long uid : uids) {
            User user =  userMapper.selectByPrimaryKey(uid);
            if(user == null){
                throw new PException(Code.ID_NOT_EXIST,"ID不存在");
            }
            user.setStatus("0");
            updateByUser(user);
        }
    }
    /**
     * @author      2571169797   yang meng bo
     * @param user
     * @return      void
     * @exception
     * @date        2019/8/2 0002 下午 14:00
     * @description 根据user更新数据
     */
    public void updateByUser(User user){
        if(userMapper.selectByPrimaryKey(user.getUid()) == null ){
            throw new PException(Code.USER_NOT_EXIST,"用户不存在");
        }
        //所有的证书是否上传完成
        if(commonCertificateService.isUpdateComplete(user.getUid()) &&
                entryCertificateService.isUpdateComplete(user.getUid()) &&
                stageCertificateService.isUpdateComplete(user.getUid())){
            user.setComplete("1");
        }else{
            user.setComplete("0");
        }
        userMapper.updateByPrimaryKey(user);
    }
    /**
     * @author      2571169797   yang meng bo
     * @param uid
     * @return      manager.pojo.User
     * @exception
     * @date        2019/8/2 0002 下午 14:01
     * @description 根据uid查找数据
     */
    public User findByUid(long uid){
        User user = userMapper.selectByPrimaryKey(uid);
        if(user == null){
            throw new PException(Code.USER_NOT_EXIST,"用户不存在");
        }

        if("0".equals(user.getStatus())){
            throw new PException(Code.USER_NOT_EXIST,"用户不存在");
        }
        /*if(user.getAuthor() == "1"){
            throw new PException(Code.ID_NOT_EXIST,"非法操作");
        }*/
        return user;
    }
    /**
     * @author      2571169797   yang meng bo
     * @param jobNumber
    * @param name
    * @param identityCard
     * @return      java.util.List<manager.pojo.User>
     * @exception
     * @date        2019/8/2 0002 下午 15:24
     * @description 根据 工号，姓名，身份证号进行模糊查询
     */
    public PageResult<User> fingByExampie(int rows,int size,String jobNumber,String name,String identityCard){
        PageHelper.startPage(rows, size);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(jobNumber)){
            criteria.andLike("jobNumber","%"+jobNumber+"%");
        }
        if(StringUtils.isNotBlank(name)){
            criteria.andLike("name","%"+name+"%");
        }
        if(StringUtils.isNotBlank(identityCard)){
            criteria.andLike("identityCard","%"+identityCard+"%");
        }
        Page<User> userList = (Page<User>)userMapper.selectByExample(example);


        return new PageResult<User>(userList.getTotal(),userList.getResult());
    }

    public User findByJobNumber(String jobNumber) {
        User user = new User();
        user.setJobNumber(jobNumber);
        User targetUser = userMapper.selectOne(user);
        /*if(targetUser == null){
            throw new PException(Code.USER_NOT_EXIST,"用户不存在");
        }*/
        return targetUser;
    }

    public List<User> findByCidOrSelectOptionData(Long cid, SelectOptionData data) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cid",cid);
        if(StringUtils.isNotBlank(data.getJobNumber())){
            criteria.andLike("jobNumber", "%"+data.getJobNumber()+"%");
        }
        if(StringUtils.isNotBlank(data.getIdentityCard())){
            criteria.andLike("identityCard", "%"+data.getIdentityCard()+"%");
        }
        if(StringUtils.isNotBlank(data.getName())){
            criteria.andLike("name", "%"+data.getName()+"%");
        }
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    public User get() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = findByJobNumber(userInfo.getUsername());
        return user;
    }

    public void updatePassword(String oldPassword, String newPassword) {
        //获取当前的用户
        User user = get();
        if(StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
            if(!MD5Util.md5(oldPassword,user.getJobNumber()).equals(user.getPassword())){
                throw new PException(Code.PASSWORD_ERROR, "密码错误");
            }
            String md5Pass = MD5Util.md5(newPassword, user.getJobNumber());
            user.setPassword(md5Pass);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    public PageResult<CollegeUser> searchByKeyword(Keyword key, int page, int size) {
        //判断权限，是否支持该功能
        if("1".equals(key.getAuthor())){
            throw new PException(Code.AUTHOR_ERROR,"权限不足");
        }
        //满足关键字搜索:名称、工号、身份证号、电话
        if(StringUtils.isNotBlank(key.getKeyword())){
            PageHelper.startPage(page,size);

            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.orLike("name","%" + key.getKeyword() + "%");
            criteria.orLike("jobNumber","%" + key.getKeyword() + "%");
            criteria.orLike("identityCard","%" + key.getKeyword() + "%");
            criteria.orLike("telephone","%" + key.getKeyword() + "%");

            Page<User> userList = (Page<User>)userMapper.selectByExample(example);
            List<CollegeUser> collegeUsers = new ArrayList<CollegeUser>();
            for (User user : userList) {
                CollegeUser collegeUser = collegeService.userPackaging(user);
                collegeUsers.add(collegeUser);
            }

            return new PageResult<>(userList.getTotal(),collegeUsers);
        }else{
            throw new PException(Code.PARAM_ERROR,"不能为空");
        }

    }

    public int getUserTotal() {
        int total = userMapper.selectCountByExample(null);
        return total;
    }
}
