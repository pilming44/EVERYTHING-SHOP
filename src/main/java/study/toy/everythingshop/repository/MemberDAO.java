package study.toy.everythingshop.repository;

import study.toy.everythingshop.entity.UserMEntity;

import java.util.Optional;

public interface MemberDAO {
    int join(UserMEntity userMEntity);

    Optional<UserMEntity> findById(String userId);
}
