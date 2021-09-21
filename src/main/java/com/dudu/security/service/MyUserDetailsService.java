package com.dudu.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dudu.security.entity.Users;
import com.dudu.security.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:实现UserDetailsService接口
 * @author:zhaomeng
 * @date:2021/9/18 6:57 下午
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用userMapper的方法，根据用户名查询数据库
        QueryWrapper<Users>  wrapper  = new  QueryWrapper();
        wrapper.eq("username",username);
        Users user = usersMapper.selectOne(wrapper);
        if (user == null){
            throw new UsernameNotFoundException("用户找不到");
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_sale,admins");
        return new User(username,new BCryptPasswordEncoder().encode(user.getPassword()),grantedAuthorities);
    }
}
