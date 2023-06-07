package study.toy.everythingshop.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.repository.UserDAO;

/**
 * fileName : MemberDetailsServiceImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDAO userDAO;
    /**
     * WebSecurityConfig의 /loginProc 주소로 들어오면
     * UserDetailService를 구현한 클래스(@Service가 붙은 클래스) 내에 loadUserByUsername 메소드가 자동으로 실행
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userDAO.selectUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + userId);
        }
        return new UserDetailsImpl(user);
    }
}
