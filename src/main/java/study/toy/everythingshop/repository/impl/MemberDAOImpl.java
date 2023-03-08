package study.toy.everythingshop.repository.impl;

import org.apache.ibatis.session.SqlSession;
import study.toy.everythingshop.entity.UserMEntity;
import study.toy.everythingshop.repository.MemberDAO;

import java.util.List;
import java.util.Optional;

public class MemberDAOImpl implements MemberDAO {

    private SqlSession sqlSession;
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
