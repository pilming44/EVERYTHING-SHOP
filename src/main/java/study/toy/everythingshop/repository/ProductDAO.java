package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.ProductMEntity;

import java.util.List;

/**
 * fileName : ProductDAOImpl
 * author   : pilming
 * date     : 2023-03-01
 */
public interface ProductDAO {
    List<ProductMEntity> findAll(ProductSearchDTO productSearchDTO);

    int save(ProductMEntity productMEntity);

    ProductMEntity findByProductNum(Long productNum);

    int registerProduct(ProductRegisterDTO productRegisterDTO);

    int editProduct(ProductRegisterDTO productRegisterDTO);

    int orderM(ProductOrderDTO productOrderDTO);

    int orderProduct(ProductOrderDTO productOrderDTO);
}
