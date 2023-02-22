package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.repository.sampleDAO;

@Repository
@RequiredArgsConstructor
public class sampleDAOImpl implements sampleDAO {
    private final SqlSession sqlSession;
}
