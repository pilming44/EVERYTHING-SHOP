package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.mariaDB.PointHistory;
import study.toy.everythingshop.entity.mariaDB.User;

/**
 * fileName : UserDAO
 * author   : pilming
 * date     : 2023-03-08
 */
public interface UserDAO {
    int insertNewUser(JoinDTO joinDto);

    User selectUserById(String userId);

    int updateUserInfo(User user);

    int updateHoldingPoint(User user);

    int insertPointHistory(PointHistory pointHistory);

    int selectUsedPoint(String userId);
}
