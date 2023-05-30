package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.h2.ProductMEntity;
import study.toy.everythingshop.entity.mariaDB.Product;
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
    public List<Product> findAll(ProductSearchDTO productSearchDTO) {

        return sqlSession.selectList("maria.ProductDAO.findAll", productSearchDTO);
    }

    @Override
    public Product findByProductNum(Integer productNum) {
        return sqlSession.selectOne("maria.ProductDAO.findByProductNum", productNum);
    }
    @Override
    public int registerProduct(ProductRegisterDTO productRegisterDTO){
        return sqlSession.insert("maria.ProductDAO.registerProduct", productRegisterDTO);
    }

    @Override
    public int editProduct(ProductRegisterDTO productRegisterDTO) {
        return sqlSession.update("maria.ProductDAO.editProduct", productRegisterDTO);
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
        return sqlSession.update("h2.ProductDAO.updateQuantityStts", productMEntity);
    }

    @Override
    public List<ProductOrderDTO> getMyOrderList(ProductSearchDTO productSearchDTO) {
        return sqlSession.selectList("h2.ProductDAO.getMyOrderList", productSearchDTO);
    }


}
