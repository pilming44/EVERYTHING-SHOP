package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.mariaDB.User;

/**
 * fileName : DiscountPolicyDAO
 * author   : pilming
 * date     : 2023-06-07
 */
public interface DiscountPolicyDAO {
    int selectDiscountRate(String gradeCd);
}
