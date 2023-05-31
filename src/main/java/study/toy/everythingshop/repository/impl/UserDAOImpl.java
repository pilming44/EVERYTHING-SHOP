package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.h2.UserMEntity;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.UserDAO;

import java.util.List;
import java.util.Optional;

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
    public User findByUserId(String userId) {
        return sqlSession.selectOne("maria.UserDAO.findByUserId", userId);
    }

    @Override
    public int save(JoinDTO userMEntity) {
        return sqlSession.insert("maria.UserDAO.save", userMEntity);
    }

    @Override
    public int updateUserInfo(User user) {
        return sqlSession.update("maria.UserDAO.updateUserInfo", user);
    }
}
