package study.toy.everythingshop.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import study.toy.everythingshop.auth.CustomUserDetails;
import study.toy.everythingshop.entity.mariaDB.PointHistory;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.repository.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserDAO userDAO;

    @Value("${login.point.amount}")
    private int loginPointAmount;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException{
        //사용자 정보
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        User user = customUserDetails.getUser();

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

        // 로그인 전의 요청 정보
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        String targetUrl = "/";  // 기본 URL
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();  // 로그인 전의 요청 URL
        }

        response.sendRedirect(targetUrl);  // 리다이렉트
    }
}
