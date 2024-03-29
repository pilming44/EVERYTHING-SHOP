package study.toy.everythingshop.auth;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import study.toy.everythingshop.entity.mariaDB.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * fileName : UserDetailsImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Data
@Slf4j
public class UserDetailsImpl implements CustomUserDetails {
    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public User getUser(){return user;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRoleCd()));
        /*권한이 여러개일경우 예시. 반목문을 사용해도 됨(추가로직필요)*/
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        log.info("권한리턴 : {}", authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public String getGradeCd(){ return user.getGradeCd(); }

    @Override
    public Integer getUserNum(){return user.getUserNum(); }
    @Override
    public Integer getHoldingPoint(){return user.getHoldingPoint();}

    @Override
    public String getGradeNm(){ return user.getGradeNm();}

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
