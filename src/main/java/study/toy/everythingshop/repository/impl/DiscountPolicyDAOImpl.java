package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.DiscountPolicyDAO;

/**
 * fileName : DiscountPolicyDAOImpl
 * author   : annie
 * date     : 2023-06-18
 */
@Repository
@RequiredArgsConstructor
@Trace
public class DiscountPolicyDAOImpl implements DiscountPolicyDAO {

    private final SqlSession sqlSession;

    @Override
    public Integer selectDiscountRateByGrade(String gradeCd) {
        return sqlSession.selectOne("maria.DiscountPolicyDAO.selectDiscountRateByGrade", gradeCd);
    }


}
