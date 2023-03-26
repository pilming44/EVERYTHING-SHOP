package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import study.toy.everythingshop.dto.SignInDTO;
import study.toy.everythingshop.entity.UserMEntity;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * 새로운 멤버 save
     * @param userMEntity
     * @return
     */
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public int insertMember(SignInDTO signInDTO) {
        signInDTO.setUserPw(bCryptPasswordEncoder.encode(signInDTO.getUserPw()));
        return userDAO.join(signInDTO);
    }
    @Override
    public List<UserMEntity> findAll() {
        return null;
    }

    @Override
    public void checkDupId(String userId){
        userDAO.findById(userId)
                .ifPresent(m -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "member.alreadyExists");
                });
    }



}
