package manager.service;

import manager.mapper.UserMapper;
import manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
