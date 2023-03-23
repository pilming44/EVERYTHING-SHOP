package study.toy.everythingshop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * fileName : CustomAuthenticationFailureHandler
 * author   : pilming
 * date     : 2023-03-09
 */
@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /*예외에따른 문구,화면에 표시방법 생각할것*/
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = getErrorMessage(exception);

        HttpSession session = request.getSession();
        session.setAttribute("errorMessage", errorMessage);

        redirectStrategy.sendRedirect(request, response, "/users/signIn");
    }

    private String getErrorMessage(AuthenticationException exception) {
        String errorMessage = "ID 또는 PASSWORD가 일치하지 않습니다.";
        if (exception instanceof LockedException) {
            errorMessage = "해당 계정은 잠금 상태입니다.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "해당 계정은 비활성 상태입니다.";
        } else if (exception instanceof AccountExpiredException) {
            errorMessage = "해당 계정은 휴면상태입니다. 관리자에게 문의하세요.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "사용자의 인증 정보가 만료되었습니다.";
        }
        return errorMessage;
    }
}
