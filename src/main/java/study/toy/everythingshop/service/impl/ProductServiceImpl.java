package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.entity.h2.ProductMEntity;
import study.toy.everythingshop.entity.h2.UserMEntity;
import study.toy.everythingshop.entity.mariaDB.Product;
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
        return productDAO.editProduct(productRegisterDTO);
    }

    @Override
    public int orderProduct(ProductOrderDTO productOrderDTO, UserDetails userDetails) {
        User user = userDAO.findByUserId(userDetails.getUsername());
        productOrderDTO.setUserNum(user.getUserNum());
        int result = productDAO.orderM(productOrderDTO);
        result += productDAO.orderProduct(productOrderDTO);

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

    public int registerProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails){
        User user = userDAO.findByUserId(userDetails.getUsername());
        productRegisterDTO.setUserNum(user.getUserNum());
        return productDAO.registerProduct(productRegisterDTO);
    };




}
