package study.toy.everythingshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;

import java.util.Map;

public interface ProductService {
    Map<String, Object> findProductList(ProductSearchDTO productSearchDTO);
    int saveNewProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails);

    int editProduct(ProductRegisterDTO productRegisterDTO);

    int saveOrderProduct(ProductOrderDTO productOrderDTO, String userId);
}
