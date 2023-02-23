package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.entity.SampleEntity;
import study.toy.everythingshop.repository.SampleDAO;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SampleDAOImpl implements SampleDAO {
    private final SqlSession sqlSession;

    @Override
    public List<SampleEntity> findAll() {
        return sqlSession.selectList("study.toy.everythingshop.repository.SampleDAO.findAll");
    }
}
