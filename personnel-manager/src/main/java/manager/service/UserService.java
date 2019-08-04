package manager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import manager.mapper.UserMapper;
import manager.pojo.College;
import manager.pojo.User;
import manager.vo.CollegeUser;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Code;
import util.PException;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.*;

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


    public User findOne(){
        return userMapper.selectByPrimaryKey(2);
    }
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

    public List<User> findByCid(Long cid){

        Example example = new Example(User.class);
        example.setOrderByClause("sort desc");//降序
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        criteria.andEqualTo("cid" ,cid);
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

        if(userMapper.selectByPrimaryKey(user) != null ){
            throw new PException(Code.USER_EXIST,"用户已存在");
        }
        user.setAuthor(author);
        user.setPassword(user.getJobNumber());
        user.setAudit("1");
        user.setComplete("0");
        user.setStatus("1");
        userMapper.insert(user);
    }
    /**
     * @author      2571169797   yang meng bo
     * @param uid
     * @return      void
     * @exception
     * @date        2019/8/2 0002 下午 14:00
     * @description 根据uid删除数据
     */
    public void deleteByUid(Long uid){
        if(uid == null){
            throw new PException(Code.ID_NOT_EXIST,"ID不存在");
        }
        User user =  userMapper.selectByPrimaryKey(uid);
        user.setStatus("0");
        updateByUser(user);
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

        if(user.getAuthor() == "1"){
            throw new PException(Code.ID_NOT_EXIST,"非法操作");
        }
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
        if(targetUser == null){
            throw new PException(Code.USER_NOT_EXIST,"用户不存在");
        }
        return targetUser;
    }
}
