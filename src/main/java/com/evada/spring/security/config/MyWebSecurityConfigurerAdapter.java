package com.evada.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * springSecurityFilterChain
 * @author dingqin
 * @date 2017/11/16
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true(对应@Secured), prePostEnabled = true, jsr250Enabled = true(对应@RolesAllowed))//方法启用保护
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    public MyWebSecurityConfigurerAdapter() {
        System.out.println();
    }

    /**
     * 内存的形式存储
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("ding").password("ding").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("dba").password("dba").roles("ADMIN","DBA");//dba have two roles.
    }

    /**
     * http请求过滤
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()//任何人都有权限访问"/"或"/home"
                .antMatchers("/admin/**").access("hasRole('ADMIN')")//只有admin权限的才能访问"/admin/**"路径的请求
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .and().formLogin().loginPage("/login")//自定义login(这里访问的是URL不是jsp)，如果没有则formLogin 方法提供了基于表单的权限验证，将会产生一个默认的对用户的表单请求
                .usernameParameter("name").passwordParameter("password")
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage("/Access_Denied")
               .and().requiresChannel().antMatchers("/").requiresSecure();//为需要安全通道（通过调用requiresChannel()确定的）并自动将请求重定向到HTTPS上

    } }
