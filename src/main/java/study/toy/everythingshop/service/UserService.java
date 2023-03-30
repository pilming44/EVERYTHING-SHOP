package study.toy.everythingshop.service;

import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.UserMEntity;

import java.util.List;
public interface UserService {

    int insertMember(JoinDTO joinDTO);
    List<UserMEntity> findAll();


    void checkDupId(String userId);
}
