package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.ProductMEntity;
import study.toy.everythingshop.repository.ProductDAO;

import java.util.List;

/**
 * fileName : ProductDAOImpl
 * author   : pilming
 * date     : 2023-03-01
 */

@Repository
@RequiredArgsConstructor
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
}
