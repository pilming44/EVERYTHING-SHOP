package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.h2.ProductMEntity;
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
    public List<ProductMEntity> findAll(ProductSearchDTO productSearchDTO) {

        return sqlSession.selectList("study.toy.everythingshop.repository.ProductDAO.findAll", productSearchDTO);
    }

    @Override
    public int save(ProductMEntity productMEntity) {

        return sqlSession.insert("study.toy.everythingshop.repository.ProductDAO.save", productMEntity);
    }

    @Override
    public ProductMEntity findByProductNum(Long productNum) {
        return sqlSession.selectOne("study.toy.everythingshop.repository.ProductDAO.findByProductNum", productNum);
    }
    @Override
    public int registerProduct(ProductRegisterDTO productRegisterDTO){
        return sqlSession.insert("study.toy.everythingshop.repository.ProductDAO.registerProduct", productRegisterDTO);
    }

    @Override
    public int editProduct(ProductRegisterDTO productRegisterDTO) {
        return sqlSession.update("study.toy.everythingshop.repository.ProductDAO.editProduct", productRegisterDTO);
    }

    @Override
    public int orderM(ProductOrderDTO productOrderDTO) {
        return sqlSession.insert("study.toy.everythingshop.repository.ProductDAO.orderM", productOrderDTO);
    }

    @Override
    public int orderProduct(ProductOrderDTO productOrderDTO) {
        return sqlSession.insert("study.toy.everythingshop.repository.ProductDAO.orderProduct", productOrderDTO);
    }

    @Override
    public int updateQuantityStts(ProductMEntity productMEntity) {
        return sqlSession.update("study.toy.everythingshop.repository.ProductDAO.updateQuantityStts", productMEntity);
    }

    @Override
    public List<ProductOrderDTO> getMyOrderList(ProductSearchDTO productSearchDTO) {
        return sqlSession.selectList("study.toy.everythingshop.repository.ProductDAO.getMyOrderList", productSearchDTO);
    }


}
