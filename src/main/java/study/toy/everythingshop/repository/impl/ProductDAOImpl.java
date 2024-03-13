package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.h2.ProductMEntity;
import study.toy.everythingshop.entity.mariaDB.Product;
import study.toy.everythingshop.entity.mariaDB.ProductN;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.ProductDAO;

import java.util.List;

/**
 * fileName : ProductDAOImpl
 * author   : pilming
 * date     : 2023-03-01
 */

@Repository
@RequiredArgsConstructor
@Trace
public class ProductDAOImpl implements ProductDAO {

    private final SqlSession sqlSession;

    @Override
    public List<ProductDTO> selectProductList(ProductSearchDTO productSearchDTO) {

        return sqlSession.selectList("maria.ProductDAO.selectProductList", productSearchDTO);
    }

    @Override
    public int selectProductListTotalCount(ProductSearchDTO productSearchDTO) {
        return sqlSession.selectOne("maria.ProductDAO.selectProductListTotalCount", productSearchDTO);
    }

    @Override
    public ProductDTO selectByProductNum(Integer productNum) {
        return sqlSession.selectOne("maria.ProductDAO.selectByProductNum", productNum);
    }
    @Override
    public int insertProduct(ProductRegisterDTO productRegisterDTO){
        return sqlSession.insert("maria.ProductDAO.insertProduct", productRegisterDTO);
    }

    @Override
    public int updateProduct(ProductEditDTO productEditDTO) {
        return sqlSession.update("maria.ProductDAO.updateProduct", productEditDTO);
    }

    @Override
    public int insertOrder(ProductOrderDTO productOrderDTO) {
        return sqlSession.insert("maria.ProductDAO.insertOrder", productOrderDTO);
    }

    @Override
    public int insertOrderedProduct(ProductOrderDTO productOrderDTO) {
        return sqlSession.insert("maria.ProductDAO.insertOrderedProduct", productOrderDTO);
    }

    @Override
    public int updateQuantityStts(ProductMEntity productMEntity) {
        return sqlSession.update("maria.ProductDAO.updateQuantityStts", productMEntity);
    }

    @Override
    public List<ProductOrderDTO> selectMyOrderList(ProductSearchDTO productSearchDTO) {
        return sqlSession.selectList("maria.ProductDAO.selectMyOrderList", productSearchDTO);
    }

    @Override
    public int selectMyOrderListTotalCount(ProductSearchDTO productSearchDTO) {
        return sqlSession.selectOne("maria.ProductDAO.selectMyOrderListTotalCount", productSearchDTO);
    }

    @Override
    public int selectRemainingQuantity(ProductOrderDTO productOrderDTO) {
        return sqlSession.selectOne("maria.ProductDAO.selectRemainingQuantity", productOrderDTO);
    }
    @Override
    public Integer selectOrderedQty(Integer productNum) {
        return sqlSession.selectOne("maria.ProductDAO.selectOrderedQty", productNum);
    }


    @Override
    public int updateProductSoldOut(ProductOrderDTO productOrderDTO) {
        return sqlSession.update("maria.ProductDAO.updateProductSoldOut", productOrderDTO);
    }

    @Override
    public int updateProductViewCount(Integer productNum) {
        return sqlSession.update("maria.ProductDAO.updateProductViewCount", productNum);
    }

    @Override
    public int updateRemainQtyNStts(Product product) {
        return sqlSession.update("maria.ProductDAO.updateRemainQtyNStts", product);
    }
  
    @Override
    public ProductN selectProductsWithViews(Integer productNum) {
        return sqlSession.selectOne("maria.ProductDAO.selectProductsWithViews", productNum);
    }

    @Override
    public int updateProductViews(ProductN product) {
        return sqlSession.update("maria.ProductDAO.updateProductViews", product);
    }
}
