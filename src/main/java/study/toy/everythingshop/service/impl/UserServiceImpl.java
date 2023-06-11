package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.h2.UserMEntity;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Trace
public class UserServiceImpl implements UserService {
    /**
     * 새로운 멤버 save
     * @param userMEntity
     * @return
     */
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public int saveNewMember(JoinDTO joinDTO) {
        joinDTO.setUserPw(bCryptPasswordEncoder.encode(joinDTO.getUserPw()));
        return userDAO.insertUser(joinDTO);
    }
    @Override
    public List<UserMEntity> findAllUser() {
        return null;
    }

    @Override
    public void findDupId(String userId){
        User user = userDAO.selectByeUserId(userId);
        if (user != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user.alreadyExists");
        }
    }



}
