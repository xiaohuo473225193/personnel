package manager.controller;

import manager.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import util.MD5Util;


import java.util.Collection;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailsService userDetailsService;
    /***
     * 自定义提交的用户获取对象
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();// 这个获取表单输入的用户名;
        String password = (String) authentication.getCredentials();// 这个获取表单输入的密码;
        //加密
        password = MD5Util.md5(password,username);
        UserInfo userInfo =  (UserInfo) userDetailsService.loadUserByUsername(username);
        if(userInfo == null){
            throw new BadCredentialsException("用户名不存在");
        }
        //密码判断
        if(!password.equals(userInfo.getPassword())){
            throw new BadCredentialsException("密码不正确");
        }
        //获取权限
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        //构建返回用户的登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo,password,authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // 这里直接改成retrun true;表示是开启这个功能
        return true;
    }
}
