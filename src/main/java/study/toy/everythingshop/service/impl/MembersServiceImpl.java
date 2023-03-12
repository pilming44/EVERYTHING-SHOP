package study.toy.everythingshop.service.impl;

import study.toy.everythingshop.entity.UserMEntity;
import study.toy.everythingshop.repository.MemberDAO;
import study.toy.everythingshop.service.MembersService;

import java.util.List;

public class MembersServiceImpl implements MembersService {
    /**
     * 새로운 멤버 save
     * @param userMEntity
     * @return
     */
    public MemberDAO memberDAO;

    @Override
    public int insertMember(UserMEntity userMEntity) {
        checkDupId(userMEntity.getUserId()); //중복 회원 검증
        return memberDAO.join(userMEntity);
    }
    @Override
    public List<UserMEntity> findAll() {
        return null;
    }

    @Override
    public void checkDupId(String userId){
        memberDAO.findById(userId)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }



}
