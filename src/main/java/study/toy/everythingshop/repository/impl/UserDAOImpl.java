package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.SignInDTO;
import study.toy.everythingshop.entity.UserMEntity;
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
public class UserDAOImpl implements UserDAO {
    private final SqlSession sqlSession;

    @Override
    public UserMEntity findByUserId(String userId) {
        return sqlSession.selectOne("study.toy.everythingshop.repository.UserDAO.findByUserId", userId);
    }

    @Override
    public Optional<SignInDTO> findById(String userId){
        List<SignInDTO> result = sqlSession.selectList("study.toy.everythingshop.repository.UserDAO.findById", userId);
        return result.stream().findAny();
    }

    @Override
    public int join(SignInDTO signInDTO) {
        return sqlSession.insert("study.toy.everythingshop.repository.UserDAO.join", signInDTO);
    }

    @Override
    public int save(UserMEntity userMEntity) {
        return sqlSession.insert("study.toy.everythingshop.repository.UserDAO.save", userMEntity);
    }
}
