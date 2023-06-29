package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.CommonCodeDTO;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.CommonDAO;

import java.util.List;

/**
 * fileName : UserDAOImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Repository
@RequiredArgsConstructor
@Trace
public class CommonDAOImpl implements CommonDAO {
    private final SqlSession sqlSession;

    @Override
    public List<CommonCodeDTO> selectCommonCodeList(CommonCodeDTO param) {
        return sqlSession.selectList("maria.CommonDAO.selectCommonCodeList", param);
    }

    @Override
    public CommonCodeDTO selectCommonCodeNm(CommonCodeDTO param) {
        return sqlSession.selectOne("maria.CommonDAO.selectCommonCodeNm", param);
    }
}
