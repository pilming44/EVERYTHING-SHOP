package study.toy.everythingshop.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.ProductMEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * fileName : ProductDAOTest
 * author   : pilming
 * date     : 2023-03-01
 */

@Slf4j
@Transactional
@SpringBootTest
public class ProductDAOTest {

    @Autowired
    ProductDAO productDAO;

    @Test
    @DisplayName("한 건 저장")
    void saveOneData() {
        // given
        ProductMEntity productMEntity = ProductMEntity.builder().productName("만물상자").productStts("02").price(30000L).quantity(30L).userNum(1L).build();
        // when
        productDAO.save(productMEntity);
        ProductMEntity productMEntity1 = productDAO.findById(productMEntity.getProductNum());
        // then
        assertThat(productMEntity).isEqualTo(productMEntity1);
    }

    @Test
    @DisplayName("데이터가 한 건도 없을경우")
    void noSearchParamAndNoData() {
        // given
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName(null)
                .fromPrice(null)
                .toPrice(null).build();
        // when
        List<ProductMEntity> findAll = productDAO.findAll(productSearchDTO);
        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("데이터가 한 건만 있을경우")
    void noSearchParamAndOneData() {
        // given
        //검색조건은 없음
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName(null)
                .fromPrice(null)
                .toPrice(null).build();

        ProductMEntity productMEntity = ProductMEntity.builder().productName("만물상자").productStts("02").price(30000L).quantity(30L).userNum(1L).build();
        productDAO.save(productMEntity);
        // when
        List<ProductMEntity> findAll = productDAO.findAll(productSearchDTO);
        // then
        assertThat(findAll).hasSize(1);
    }

    @Test
    @DisplayName("데이터가 여러건 있을경우")
    void noSearchParamAndMultipleData() {
        // given
        //검색조건은 없음
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName(null)
                .fromPrice(null)
                .toPrice(null).build();

        ProductMEntity productMEntity1 = ProductMEntity.builder().productName("만물상자").productStts("02").price(30000L).quantity(30L).userNum(1L).build();
        ProductMEntity productMEntity2 = ProductMEntity.builder().productName("과자").productStts("01").price(10000L).quantity(21L).userNum(1L).build();
        ProductMEntity productMEntity3 = ProductMEntity.builder().productName("선물").productStts("02").price(23000L).quantity(5L).userNum(1L).build();
        ProductMEntity productMEntity4 = ProductMEntity.builder().productName("책").productStts("01").price(5000L).quantity(10L).userNum(1L).build();
        productDAO.save(productMEntity1);
        productDAO.save(productMEntity2);
        productDAO.save(productMEntity3);
        productDAO.save(productMEntity4);
        // when
        List<ProductMEntity> findAll = productDAO.findAll(productSearchDTO);
        // then
        assertThat(findAll).hasSize(4);
    }

}
