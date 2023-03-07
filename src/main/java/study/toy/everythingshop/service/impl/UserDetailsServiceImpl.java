package study.toy.everythingshop.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.entity.UserMEntity;

/**
 * fileName : MemberDetailsServiceImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * WebSecurityConfig의 /loginProc 주소로 들어오면
     * UserDetailService를 구현한 클래스(@Service가 붙은 클래스) 내에 loadUserByUsername 메소드가 자동으로 실행
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        UserMEntity user = userRepository.findByUsername(userId);
//        if(user != null){
//            return new MemberDetails(user);
//        }
        return null;
    }
}
