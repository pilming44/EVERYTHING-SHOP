package study.toy.everythingshop.service;

import study.toy.everythingshop.dto.SignInDTO;
import study.toy.everythingshop.entity.UserMEntity;

import java.util.List;
public interface UserService {

    int insertMember(SignInDTO userMEntity);
    List<UserMEntity> findAll();


    void checkDupId(String userId);
}
