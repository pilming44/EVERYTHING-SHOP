package study.toy.everythingshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import study.toy.everythingshop.auth.CustomUserDetails;
import study.toy.everythingshop.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ProductService {
    Map<String, Object> findProductList(ProductSearchDTO productSearchDTO);

    ProductDTO oldFindProductDetail(Integer productNum, boolean firstView, UserDetails userDetails);

    ProductDTO findProductDetail(Integer productNum, boolean firstView, UserDetails userDetails);

    int saveNewProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails);

    int editProduct(ProductEditDTO productEditDTO);

    ProductOrderDTO findOrderDetail(Integer productNum, CustomUserDetails userDetails);

    int saveOrderProduct(ProductOrderDTO productOrderDTO, CustomUserDetails userDetails);

    boolean productViewCheck(Integer productNum, HttpServletRequest request, HttpServletResponse response);
}
