package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.MyPageDAO;

/**
 * fileName : MyPageDAOImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Repository
@RequiredArgsConstructor
@Trace
public class MyPageDAOImpl implements MyPageDAO {
    private final SqlSession sqlSession;

    @Override
    public int insertSellerApply(int userNum) {
        return sqlSession.insert("maria.MyPageDAO.insertSellerApply", userNum);
    }
}
