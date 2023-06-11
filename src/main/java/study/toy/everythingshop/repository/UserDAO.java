package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.mariaDB.User;

/**
 * fileName : UserDAO
 * author   : pilming
 * date     : 2023-03-08
 */
public interface UserDAO {
    int insertUser(JoinDTO userMEntity);

    User selectByeUserId(String userId);

    int updateUserInfo(User userMEntity);
}
