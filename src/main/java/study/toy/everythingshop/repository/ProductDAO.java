package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.ProductMEntity;

import java.util.List;
import java.util.Optional;

/**
 * fileName : ProductDAOImpl
 * author   : pilming
 * date     : 2023-03-01
 */
public interface ProductDAO {
    List<ProductMEntity> findAll(ProductSearchDTO productSearchDTO);

    int save(ProductMEntity productMEntity);

    ProductMEntity findById(Long id);

}
