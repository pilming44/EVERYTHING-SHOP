package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.DiscountPolicyDAO;
import study.toy.everythingshop.repository.UserDAO;

/**
 * fileName : DiscountPolicyDAOImpl
 * author   : pilming
 * date     : 2023-06-07
 */
@Repository
@RequiredArgsConstructor
@Trace
public class DiscountPolicyDAOImpl implements DiscountPolicyDAO {
    private final SqlSession sqlSession;

    @Override
    public int selectDiscountRate(String gradeCd) {
        return sqlSession.selectOne("maria.DiscountPolicyDAO.selectDiscountRate", gradeCd);
    }
}
