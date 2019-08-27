package manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private AuthenticationProvider provider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//  定义当需要用户登录时候，转到的登录页面
                .loginPage("/login.html") // 定义当需要登录时候，跳转的页面
                .loginProcessingUrl("/personnel/login") //用户自定义的结果，在登录页需要填写该url
                .failureUrl("/error.html") //登录失败跳转的页面
                .permitAll()  //表单登录，permitAll()表示这个不需要验证 登录页面，登录失败页面
                .defaultSuccessUrl("/index.html") //登录成功跳转的页面
                .and()
                .authorizeRequests()
                //.antMatchers("/login","/login-error").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();//可以进行iframe跳转
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider); //执行自己的业务
    }
    //解决静态资源被拦截的问题
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**",
                "/font-awesome/**",
                "/images/**",
                "/img/**",
                "/import/**",
                "/js/**",
                "/plugins/**",
                "/upload/**");
    }
}
