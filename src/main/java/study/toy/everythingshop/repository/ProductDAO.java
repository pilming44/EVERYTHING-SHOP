package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.ProductDTO;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.h2.ProductMEntity;

import java.util.List;

/**
 * fileName : ProductDAOImpl
 * author   : pilming
 * date     : 2023-03-01
 */
public interface ProductDAO {
    List<ProductDTO> selectProductList(ProductSearchDTO productSearchDTO);

    int selectProductListTotalCount(ProductSearchDTO productSearchDTO);

    ProductDTO selectByProductNum(Integer productNum);

    int insertNewProduct(ProductRegisterDTO productRegisterDTO);

    int updateProduct(ProductRegisterDTO productRegisterDTO);

    int insertOrder(ProductOrderDTO productOrderDTO);

    int insertOrderedProduct(ProductOrderDTO productOrderDTO);

    int updateQuantityStts(ProductMEntity productMEntity);

    List<ProductOrderDTO> selectMyOrderList(ProductSearchDTO productSearchDTO);

    int selectRemainingQuantity(ProductOrderDTO productOrderDTO);

    int updateProductSoldOut(ProductOrderDTO productOrderDTO);
}
