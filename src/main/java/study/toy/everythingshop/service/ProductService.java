package study.toy.everythingshop.service;

import org.springframework.security.core.userdetails.UserDetails;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;

public interface ProductService {
    int registerProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails);

    int editProduct(ProductRegisterDTO productRegisterDTO);

    int orderProduct(ProductOrderDTO productOrderDTO, UserDetails userDetails);
}
