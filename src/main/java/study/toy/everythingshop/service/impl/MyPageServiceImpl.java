package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.entity.UserMEntity;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.MyPageService;

/**
 * fileName : MyPageServiceImpl
 * author   : pilming
 * date     : 2023-03-29
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageServiceImpl implements MyPageService {

    private final UserDAO userDAO;

    @Override
    public void updateUserInfo(UserMEntity userMEntity) {
        userDAO.updateUserInfo(userMEntity);
    }
}
