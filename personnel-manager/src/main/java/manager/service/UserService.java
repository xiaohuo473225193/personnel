package manager.service;

import manager.mapper.UserMapper;
import manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Code;
import util.PException;

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
        userMapper.updateByPrimaryKeySelective(user);
    }
}
