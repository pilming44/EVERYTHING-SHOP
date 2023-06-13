package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.dto.UserInfoDTO;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.CommonService;
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
    private final CommonService commonService;

    @Override
    public void editUserInfo(User user) {
        userDAO.updateUserInfo(user);
    }

    @Override
    public List<ProductOrderDTO> findMyOrderList(ProductSearchDTO productSearchDTO) {
       return productDAO.selectMyOrderList(productSearchDTO);
    }

    @Override
    public UserInfoDTO findMyPageInfo(String userId) {
        //사용자정보조회
        User user = userDAO.selectUserById(userId);
        //누적포인트 조회
        int usedPoint = userDAO.selectUsedPoint(userId);
        //DTO에 세팅
        UserInfoDTO myPageDTO = UserInfoDTO.builder()
                .gradeNm(commonService.selectCommonCodeNm("COM1003", user.getGradeCd()))
                .holdingPoint(user.getHoldingPoint())
                .usedPoint(usedPoint)
                .build();

        return myPageDTO;
    }
}
