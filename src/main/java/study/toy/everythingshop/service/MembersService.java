package study.toy.everythingshop.service;

import study.toy.everythingshop.entity.UserMEntity;

import java.util.List;

public interface MembersService {

    int insertMember(UserMEntity userMEntity);
    List<UserMEntity> findAll();


    void checkDupId(String userId);
}
