package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.ProductService;

@Service
@RequiredArgsConstructor
@Slf4j
@Trace
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final UserDAO userDAO;

    @Override
    public int editProduct(ProductRegisterDTO productRegisterDTO) {
        return productDAO.updateProduct(productRegisterDTO);
    }

    @Override
    public int saveOrderProduct(ProductOrderDTO productOrderDTO, UserDetails userDetails) {
        User user = userDAO.selectByeUserId(userDetails.getUsername());
        productOrderDTO.setUserNum(user.getUserNum());
        int result = 0;
        //최종결제금액 계산

        //보유포인트 결제가능 여부 확인

        //주문테이블 insert
        result += productDAO.insertOrder(productOrderDTO);

        //주문 상품 테이블 insert
        result += productDAO.insertOrderedProduct(productOrderDTO);

        //사용자 보유 포인트 차감

        //포인트 이력테이블에 내역 insert

        //TODO db 구조 변경으로 인한 주석처리. 작성자 확인 후 삭제할것
//        Product product = productDAO.findByProductNum(productOrderDTO.getProductNum());
//        Integer remainingQuantity = product.getRegisterQuantity() - productOrderDTO.getOrderQuantity();
//        product.setRegisterQuantity(remainingQuantity);
//        if(remainingQuantity < 1){
//            product.setProductStts("04");
//        }
//        result +=  productDAO.updateQuantityStts(productMEntity);

        return result;
    }

    public int saveNewProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails){
        User user = userDAO.selectByeUserId(userDetails.getUsername());
        productRegisterDTO.setUserNum(user.getUserNum());
        return productDAO.insertNewProduct(productRegisterDTO);
    };




}
