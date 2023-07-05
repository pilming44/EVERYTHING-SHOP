package study.toy.everythingshop.auth;

import org.springframework.security.core.userdetails.UserDetails;
import study.toy.everythingshop.entity.mariaDB.User;

public interface CustomUserDetails extends UserDetails {

    User getUser();

    String getGradeCd();

    Integer getUserNum();

    Integer getHoldingPoint();

    String getGradeNm();
}
