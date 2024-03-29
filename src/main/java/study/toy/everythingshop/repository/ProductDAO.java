package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.h2.ProductMEntity;
import study.toy.everythingshop.entity.mariaDB.Product;

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

    Product selectProductsWithViews(Integer productNum);

    int updateProductViews(Product product);

    int insertProduct(ProductRegisterDTO productRegisterDTO);

    int updateProduct(ProductEditDTO productEditDTO);

    int insertOrder(ProductOrderDTO productOrderDTO);

    int insertOrderedProduct(ProductOrderDTO productOrderDTO);

    int updateQuantityStts(ProductMEntity productMEntity);

    List<ProductOrderDTO> selectMyOrderList(ProductSearchDTO productSearchDTO);

    int selectMyOrderListTotalCount(ProductSearchDTO productSearchDTO);

    int selectRemainingQuantity(ProductOrderDTO productOrderDTO);

    int updateProductSoldOut(ProductOrderDTO productOrderDTO);

    Integer selectOrderedQty(Integer productNum);

    int updateProductViewCount(Integer productNum);

    int updateRemainQtyNStts(Product product);


}
