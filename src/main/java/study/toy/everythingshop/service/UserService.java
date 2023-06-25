package study.toy.everythingshop.service;

import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.h2.UserMEntity;

import java.util.List;
public interface UserService {

    int saveMember(JoinDTO joinDTO);
    List<UserMEntity> findAllUser();


    void findDupId(String userId);

}
