package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.entity.mariaDB.PointHistory;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.DiscountPolicyDAO;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.ProductService;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Trace
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private final DiscountPolicyDAO discountPolicyDAO;

    @Override
    public int editProduct(ProductRegisterDTO productRegisterDTO) {
        return productDAO.updateProduct(productRegisterDTO);
    }

    @Override
    public int saveOrderProduct(ProductOrderDTO productOrderDTO, String userId) {
        //주문 사용자
        User user = userDAO.selectUserById(userId);
        productOrderDTO.setUserNum(user.getUserNum());

        int result = 0;
        //주문 금액 계산
        int orderedPrice = productOrderDTO.getOrderQuantity() * productOrderDTO.getProductPrice(); //주문수량 * 주문한 상품의 가격

        //사용자 등급별 할인 적용
        int discountRate = discountPolicyDAO.selectDiscountRate(user.getGradeCd()); //할인율(%)
        int discountPrice = (int)(orderedPrice * (discountRate / 100.0));    //할인가격
        int finalPaymentPrice = orderedPrice - discountPrice;   //최종 결제금액

        //보유포인트
        int userHoldingPoint = user.getHoldingPoint();

        //TODO 보유포인트가 결제 금액보다 적다면 주문 불가능 -> 주문시 컨트롤러단에서 해결할지?
        if(userHoldingPoint < finalPaymentPrice) {
            return 0;
        }

        //주문테이블 insert
        result += productDAO.insertOrder(productOrderDTO);

        //주문 상품 테이블 insert
        productOrderDTO.setDiscountPrice(discountPrice);
        productOrderDTO.setFinalPaymentPrice(finalPaymentPrice);
        result += productDAO.insertOrderedProduct(productOrderDTO);

        //재고가 없다면 품절처리
        if(productDAO.selectRemainingQuantity(productOrderDTO) == 0) {
            result += productDAO.updateProductSoldOut(productOrderDTO);
        }
        //사용자 보유 포인트 차감
        user.setHoldingPoint(userHoldingPoint - finalPaymentPrice);
        result += userDAO.updateHoldingPoint(user);

        //포인트 이력테이블에 내역 insert
        PointHistory pointHistory = PointHistory.builder()
                .userNum(user.getUserNum())
                .pointChangeCd("03")
                .deductPoint(finalPaymentPrice)
                .build();
        result += userDAO.insertPointHistory(pointHistory);

        return result;
    }

    public int saveNewProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails){
        User user = userDAO.selectUserById(userDetails.getUsername());
        productRegisterDTO.setUserNum(user.getUserNum());
        return productDAO.insertNewProduct(productRegisterDTO);
    };




}
