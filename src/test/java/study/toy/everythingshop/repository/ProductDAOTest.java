package study.toy.everythingshop.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.h2.ProductMEntity;

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
        ProductMEntity productMEntity1 = productDAO.findByProductNum(productMEntity.getProductNum());
        // then
        assertThat(productMEntity).isEqualTo(productMEntity1);
    }

    @Test
    @DisplayName("데이터가 한 건도 없을경우 목록조회")
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
    @DisplayName("데이터가 한 건만 있을경우 목록조회")
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
    @DisplayName("데이터가 여러건 있을경우 목록조회")
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

    @Test
    @DisplayName("상품명 검색결과 없음")
    void searchNameAndNoData() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName("최재현")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
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
        assertThat(findAll).hasSize(0);
    }
    @Test
    @DisplayName("상품명 한건 검색")
    void searchNameAndOneData() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName("만물상자")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
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
        assertThat(findAll).hasSize(1);
        assertThat(findAll.contains(productMEntity1)).isTrue();
    }

    @Test
    @DisplayName("상품명 여러건 검색")
    void searchNameAndMuiltyData() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName("물상")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
        ProductMEntity productMEntity1 = ProductMEntity.builder().productName("만물상자").productStts("02").price(30000L).quantity(30L).userNum(1L).build();
        ProductMEntity productMEntity2 = ProductMEntity.builder().productName("과자").productStts("01").price(10000L).quantity(21L).userNum(1L).build();
        ProductMEntity productMEntity3 = ProductMEntity.builder().productName("선물상자").productStts("02").price(23000L).quantity(5L).userNum(1L).build();
        ProductMEntity productMEntity4 = ProductMEntity.builder().productName("책").productStts("01").price(5000L).quantity(10L).userNum(1L).build();
        productDAO.save(productMEntity1);
        productDAO.save(productMEntity2);
        productDAO.save(productMEntity3);
        productDAO.save(productMEntity4);
        // when
        List<ProductMEntity> findAll = productDAO.findAll(productSearchDTO);

        // then
        assertThat(findAll).hasSize(2);
        assertThat(findAll.contains(productMEntity1)).isTrue();
        assertThat(findAll.contains(productMEntity3)).isTrue();
    }

    @Test
    @DisplayName("시작금액 0원 검색")
    void searchZeroFromPrice() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName(null)
                .fromPrice(0L)
                .toPrice(null).build();

        //상품입력
        ProductMEntity productMEntity1 = ProductMEntity.builder().productName("만물상자").productStts("02").price(30000L).quantity(30L).userNum(1L).build();
        ProductMEntity productMEntity2 = ProductMEntity.builder().productName("과자").productStts("01").price(10000L).quantity(21L).userNum(1L).build();
        ProductMEntity productMEntity3 = ProductMEntity.builder().productName("선물상자").productStts("02").price(23000L).quantity(5L).userNum(1L).build();
        ProductMEntity productMEntity4 = ProductMEntity.builder().productName("책").productStts("01").price(5000L).quantity(10L).userNum(1L).build();
        productDAO.save(productMEntity1);
        productDAO.save(productMEntity2);
        productDAO.save(productMEntity3);
        productDAO.save(productMEntity4);
        // when
        List<ProductMEntity> findAll = productDAO.findAll(productSearchDTO);

        // then
        assertThat(findAll).hasSize(4);
        assertThat(findAll.contains(productMEntity1)).isTrue();
        assertThat(findAll.contains(productMEntity2)).isTrue();
        assertThat(findAll.contains(productMEntity3)).isTrue();
        assertThat(findAll.contains(productMEntity4)).isTrue();
    }

    @Test
    @DisplayName("종료금액 0원 검색")
    void searchZeroToPrice() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName(null)
                .fromPrice(null)
                .toPrice(0L).build();

        //상품입력
        ProductMEntity productMEntity1 = ProductMEntity.builder().productName("만물상자").productStts("02").price(30000L).quantity(30L).userNum(1L).build();
        ProductMEntity productMEntity2 = ProductMEntity.builder().productName("과자").productStts("01").price(10000L).quantity(21L).userNum(1L).build();
        ProductMEntity productMEntity3 = ProductMEntity.builder().productName("선물상자").productStts("02").price(23000L).quantity(5L).userNum(1L).build();
        ProductMEntity productMEntity4 = ProductMEntity.builder().productName("책").productStts("01").price(5000L).quantity(10L).userNum(1L).build();
        productDAO.save(productMEntity1);
        productDAO.save(productMEntity2);
        productDAO.save(productMEntity3);
        productDAO.save(productMEntity4);
        // when
        List<ProductMEntity> findAll = productDAO.findAll(productSearchDTO);

        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("시작,종료금액 0원 검색")
    void searchZeroPrice() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productName(null)
                .fromPrice(0L)
                .toPrice(0L).build();

        //상품입력
        ProductMEntity productMEntity1 = ProductMEntity.builder().productName("만물상자").productStts("02").price(30000L).quantity(30L).userNum(1L).build();
        ProductMEntity productMEntity2 = ProductMEntity.builder().productName("과자").productStts("01").price(10000L).quantity(21L).userNum(1L).build();
        ProductMEntity productMEntity3 = ProductMEntity.builder().productName("선물상자").productStts("02").price(23000L).quantity(5L).userNum(1L).build();
        ProductMEntity productMEntity4 = ProductMEntity.builder().productName("책").productStts("01").price(5000L).quantity(10L).userNum(1L).build();
        productDAO.save(productMEntity1);
        productDAO.save(productMEntity2);
        productDAO.save(productMEntity3);
        productDAO.save(productMEntity4);
        // when
        List<ProductMEntity> findAll = productDAO.findAll(productSearchDTO);

        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("상품 수정 - 성공")
    void test_1() {
        // given
        //기존 상품 등록
        ProductRegisterDTO productRegisterDTO = ProductRegisterDTO.builder()
                .productName("테스트물품")
                .price(15000L)
                .userNum(1L)
                .quantity(2000L)
                .productStts("01")
                .build();

        productDAO.registerProduct(productRegisterDTO);

        // when
        productRegisterDTO.setProductName("물품수정");
        productRegisterDTO.setQuantity(100L);
        productRegisterDTO.setPrice(10000L);

        productDAO.editProduct(productRegisterDTO);

        // then
        ProductMEntity editedProduct = productDAO.findByProductNum(productRegisterDTO.getProductNum());
        assertThat(editedProduct.getProductName()).isEqualTo("물품수정");
        assertThat(editedProduct.getQuantity()).isEqualTo(100L);
        assertThat(editedProduct.getPrice()).isEqualTo(10000L);
    }
}
