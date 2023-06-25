package study.toy.everythingshop.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    String getUserGradeCd();
}
