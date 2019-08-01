package manager.service;

import manager.mapper.UserMapper;
import manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaohuo
 * @data 2019/7/15 14:59
 * @description
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User findOne(){
        return userMapper.selectByPrimaryKey(2);
    }
}
