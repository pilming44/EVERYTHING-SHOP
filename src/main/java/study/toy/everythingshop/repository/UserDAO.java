package study.toy.everythingshop.repository;

import study.toy.everythingshop.entity.ProductMEntity;
import study.toy.everythingshop.entity.UserMEntity;

/**
 * fileName : UserDAO
 * author   : pilming
 * date     : 2023-03-08
 */
public interface UserDAO {
    int save(UserMEntity userMEntity);

    UserMEntity findByUserId(String userId);
}
