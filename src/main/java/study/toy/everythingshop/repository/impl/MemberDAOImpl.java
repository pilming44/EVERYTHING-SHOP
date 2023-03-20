package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.entity.UserMEntity;
import study.toy.everythingshop.repository.MemberDAO;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {

    private final SqlSession sqlSession;
    @Override
    public int join(UserMEntity userMEntity) {
        return sqlSession.insert("study.toy.everythingshop.repository.MemberDAO.join", userMEntity);
    }
    @Override
    public Optional<UserMEntity> findById(String userId){
        List<UserMEntity> result = sqlSession.selectList("study.toy.everythingshop.repository.MemberDAO.findById", userId);
        return result.stream().findAny();
    }
}
