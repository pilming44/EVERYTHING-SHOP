package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.entity.ProductMEntity;
import study.toy.everythingshop.entity.UserMEntity;
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
        return productDAO.editProduct(productRegisterDTO);
    }

    @Override
    public int orderProduct(ProductOrderDTO productOrderDTO, UserDetails userDetails) {
        UserMEntity userMEntity = userDAO.findByUserId(userDetails.getUsername());
        productOrderDTO.setUserNum(userMEntity.getUserNum());
        int result = productDAO.orderM(productOrderDTO);
        result += productDAO.orderProduct(productOrderDTO);

        // 상품 수량 업데이트
        ProductMEntity productMEntity = productDAO.findByProductNum(productOrderDTO.getProductNum());
        Long remainingQuantity = productMEntity.getQuantity() - productOrderDTO.getOrderQuantity();
        productMEntity.setQuantity(remainingQuantity);
        if(remainingQuantity < 1){
            productMEntity.setProductStts("04");
        }
        result +=  productDAO.updateQuantityStts(productMEntity);

        return result;
    }

    public int registerProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails){
        UserMEntity userMEntity = userDAO.findByUserId(userDetails.getUsername());
        productRegisterDTO.setUserNum(userMEntity.getUserNum());
        return productDAO.registerProduct(productRegisterDTO);
    };




}
