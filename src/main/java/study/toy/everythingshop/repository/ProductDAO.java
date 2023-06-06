package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.h2.ProductMEntity;
import study.toy.everythingshop.entity.mariaDB.Product;

import java.util.List;

/**
 * fileName : ProductDAOImpl
 * author   : pilming
 * date     : 2023-03-01
 */
public interface ProductDAO {
    List<Product> selectProductList(ProductSearchDTO productSearchDTO);

    Product selectByProductNum(Integer productNum);

    int insertNewProduct(ProductRegisterDTO productRegisterDTO);

    int updateProduct(ProductRegisterDTO productRegisterDTO);

    int insertOrder(ProductOrderDTO productOrderDTO);

    int insertOrderedProduct(ProductOrderDTO productOrderDTO);

    int updateQuantityStts(ProductMEntity productMEntity);

    List<ProductOrderDTO> selectMyOrderList(ProductSearchDTO productSearchDTO);
}
