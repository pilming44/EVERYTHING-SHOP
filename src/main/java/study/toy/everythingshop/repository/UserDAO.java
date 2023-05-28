package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.h2.UserMEntity;
import study.toy.everythingshop.entity.mariaDB.User;

import java.util.Optional;

/**
 * fileName : UserDAO
 * author   : pilming
 * date     : 2023-03-08
 */
public interface UserDAO {
    int save(JoinDTO userMEntity);

    User findByUserId(String userId);

    int updateUserInfo(User userMEntity);
}
