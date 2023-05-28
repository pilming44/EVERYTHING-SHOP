package study.toy.everythingshop.service;

import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.h2.UserMEntity;
import study.toy.everythingshop.entity.mariaDB.User;

import java.util.List;

/**
 * fileName : MyPageService
 * author   : pilming
 * date     : 2023-03-29
 */
public interface MyPageService {

    void updateUserInfo(User user);

    List<ProductOrderDTO> getMyOrderList(ProductSearchDTO productSearchDTO);
}
