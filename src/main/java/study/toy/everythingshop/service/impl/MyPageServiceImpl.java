package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.h2.UserMEntity;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.MyPageService;

import java.util.List;

/**
 * fileName : MyPageServiceImpl
 * author   : pilming
 * date     : 2023-03-29
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Trace
public class MyPageServiceImpl implements MyPageService {

    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    @Override
    public void updateUserInfo(UserMEntity userMEntity) {
        userDAO.updateUserInfo(userMEntity);
    }

    @Override
    public List<ProductOrderDTO> getMyOrderList(ProductSearchDTO productSearchDTO) {
       return productDAO.getMyOrderList(productSearchDTO);
    }
}
