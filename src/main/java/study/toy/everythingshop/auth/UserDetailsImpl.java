package study.toy.everythingshop.auth;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.toy.everythingshop.entity.UserMEntity;

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
public class UserDetailsImpl implements UserDetails {
    private UserMEntity userMEntity;

    public UserDetailsImpl(UserMEntity userMEntity) {
        this.userMEntity = userMEntity;
        log.info("생성된 userMEntity 객체 : {}", this.userMEntity);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userMEntity.getUserRoleCd()));
        /*권한이 여러개일경우 예시. 반목문을 사용해도 됨(추가로직필요)*/
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        log.info("권한리턴 : {}", authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return userMEntity.getUserPw();
    }

    @Override
    public String getUsername() {
        return userMEntity.getUserId();
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
