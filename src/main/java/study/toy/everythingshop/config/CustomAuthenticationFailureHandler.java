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
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage = "Invalid username or password";
        if (exception instanceof LockedException) {
            errorMessage = "User account is locked";
        } else if (exception instanceof DisabledException) {
            errorMessage = "User account is disabled";
        } else if (exception instanceof AccountExpiredException) {
            errorMessage = "User account has expired";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "User credentials have expired";
        }

        request.setAttribute("errorMessage", errorMessage);

        redirectStrategy.sendRedirect(request, response, "/members/signIn?error=true");
    }
}
