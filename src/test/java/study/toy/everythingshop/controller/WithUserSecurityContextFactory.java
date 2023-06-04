package study.toy.everythingshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.service.UserService;

/**
 * fileName : WithUserSecurityContextFactory
 * author   : pilming
 * date     : 2023-03-31
 */
public class WithUserSecurityContextFactory implements WithSecurityContextFactory<WithUser> {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public SecurityContext createSecurityContext(WithUser withUser) {

        //테스트용 사용자 계정 생성
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUserId(withUser.value());
        joinDTO.setUserPw("testPw");
        joinDTO.setUserNm(withUser.value()+"이름");
        userService.saveNewMember(joinDTO);

        UserDetails principal = userDetailsService.loadUserByUsername(withUser.value());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal
                , principal.getPassword()
                , principal.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
