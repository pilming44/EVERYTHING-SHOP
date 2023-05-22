package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.h2.UserMEntity;

import java.util.Optional;

/**
 * fileName : UserDAO
 * author   : pilming
 * date     : 2023-03-08
 */
public interface UserDAO {
    int save(UserMEntity userMEntity);

    UserMEntity findByUserId(String userId);

    Optional<JoinDTO> findById(String userId);

    int join(JoinDTO userMEntity);

    int updateUserInfo(UserMEntity userMEntity);
}
