package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.entity.h2.UserMEntity;
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
    public int insertMember(JoinDTO joinDTO) {
        joinDTO.setUserPw(bCryptPasswordEncoder.encode(joinDTO.getUserPw()));
        return userDAO.join(joinDTO);
    }
    @Override
    public List<UserMEntity> findAll() {
        return null;
    }

    @Override
    public void checkDupId(String userId){
        userDAO.findById(userId)
                .ifPresent(m -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "user.alreadyExists");
                });
    }



}
