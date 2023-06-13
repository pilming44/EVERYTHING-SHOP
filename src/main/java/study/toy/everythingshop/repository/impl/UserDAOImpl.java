package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.mariaDB.PointHistory;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.UserDAO;

/**
 * fileName : UserDAOImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Repository
@RequiredArgsConstructor
@Trace
public class UserDAOImpl implements UserDAO {
    private final SqlSession sqlSession;

    @Override
    public User selectUserById(String userId) {
        return sqlSession.selectOne("maria.UserDAO.selectUserById", userId);
    }

    @Override
    public int insertNewUser(JoinDTO joinDTO) {
        return sqlSession.insert("maria.UserDAO.inserNewUser", joinDTO);
    }

    @Override
    public int updateUserInfo(User user) {
        return sqlSession.update("maria.UserDAO.updateUserInfo", user);
    }

    @Override
    public int updateHoldingPoint(User user) {
        return sqlSession.update("maria.UserDAO.updateHoldingPoint", user);
    }
    @Override
    public int insertPointHistory(PointHistory pointHistory) {
        return sqlSession.insert("maria.UserDAO.insertPointHistory", pointHistory);
    }

    @Override
    public int selectUsedPoint(String userId) {
        return sqlSession.selectOne("maria.UserDAO.selectUsedPoint", userId);
    }
}
