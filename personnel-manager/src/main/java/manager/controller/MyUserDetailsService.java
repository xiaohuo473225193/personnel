package manager.controller;

import manager.pojo.User;
import manager.service.UserService;
import manager.vo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
//认证类
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isNotBlank(username)){//username为工号
            User user = userService.findByJobNumber(username);
            UserInfo userInfo = new UserInfo(username,user.getPassword(),"ROLE_USER"
                                ,true,true,true,true);
            return userInfo;
        }
        return null;
    }
}
