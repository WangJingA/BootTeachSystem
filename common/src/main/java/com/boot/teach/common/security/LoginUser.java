package com.boot.teach.common.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.boot.teach.model.auth.TeachUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * userDetail
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails{
    //用户信息
    TeachUser userModel;
    //用户权限
    List<String> permission;

    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //权限授权
//        permission.forEach(k->{
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(k);
//            authorities.add(simpleGrantedAuthority);
//        });
        authorities = this.permission.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }
    public LoginUser(TeachUser user, List<String> permission){
        this.userModel = user;
        this.permission = permission;
    }

    @Override
    public String getPassword() {
        return userModel.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userModel.getUserAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
