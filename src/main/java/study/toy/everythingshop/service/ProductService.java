package study.toy.everythingshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import study.toy.everythingshop.dto.*;

import java.util.Map;
import study.toy.everythingshop.auth.CustomUserDetails;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;

public interface ProductService {
    Map<String, Object> findProductList(ProductSearchDTO productSearchDTO);

    ProductDTO findProductDetail(Integer productNum, UserDetails userDetails);

    int saveNewProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails);

    int editProduct(ProductEditDTO productEditDTO);

    int saveOrderProduct(ProductOrderDTO productOrderDTO, UserDetails userDetails);

    ProductOrderDTO findOrderDetail(Integer productNum, CustomUserDetails userDetails);
    int saveOrderProduct(ProductOrderDTO productOrderDTO, String userId);
}
