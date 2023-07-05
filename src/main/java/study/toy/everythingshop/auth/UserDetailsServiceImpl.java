package study.toy.everythingshop.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.entity.mariaDB.PointHistory;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.repository.UserDAO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * fileName : MemberDetailsServiceImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDAO userDAO;

    @Value("${login.point.amount}")
    private int loginPointAmount;
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
        log.debug("로그인 성공! : {}", user);

        //로그인 이력 insert
        userDAO.insertLoginHistory(user);
        //오늘 최초 로그인 확인
        int loginCount = userDAO.selectTodayLoginCount(user);

        //포인트 지급
        if(loginCount == 1) {
            //포인트 추가
            user.setHoldingPoint(user.getHoldingPoint()+loginPointAmount);
            userDAO.updateHoldingPoint(user);

            //포인트 이력 추가
            PointHistory pointHistory = PointHistory.builder()
                    .userNum(user.getUserNum())
                    .pointChangeCd("05")
                    .addPoint(loginPointAmount)
                    .build();
            userDAO.insertPointHistory(pointHistory);
        }
        return new UserDetailsImpl(user);
    }
}
