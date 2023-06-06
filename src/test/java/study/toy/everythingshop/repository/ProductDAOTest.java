package study.toy.everythingshop.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.mariaDB.Product;

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
        ProductRegisterDTO productRegisterDTO = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        // when
        productDAO.insertNewProduct(productRegisterDTO);
        Product product = productDAO.selectByProductNum(productRegisterDTO.getProductNum());
        // then
        assertThat(productRegisterDTO).isEqualTo(product);
    }

    @Test
    @DisplayName("데이터가 한 건도 없을경우 목록조회")
    void noSearchParamAndNoData() {
        // given
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(null)
                .toPrice(null).build();
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);
        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("데이터가 한 건만 있을경우 목록조회")
    void noSearchParamAndOneData() {
        // given
        //검색조건은 없음
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(null)
                .toPrice(null).build();

        ProductRegisterDTO productRegisterDTO = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        productDAO.insertNewProduct(productRegisterDTO);
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);
        // then
        assertThat(findAll).hasSize(1);
    }

    @Test
    @DisplayName("데이터가 여러건 있을경우 목록조회")
    void noSearchParamAndMultipleData() {
        // given
        //검색조건은 없음
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(null)
                .toPrice(null).build();

        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(1).build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(1).build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(1).build();
        productDAO.insertNewProduct(productRegisterDTO1);
        productDAO.insertNewProduct(productRegisterDTO2);
        productDAO.insertNewProduct(productRegisterDTO3);
        productDAO.insertNewProduct(productRegisterDTO4);
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);
        // then
        assertThat(findAll).hasSize(4);
    }

    @Test
    @DisplayName("상품명 검색결과 없음")
    void searchNameAndNoData() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm("최재현")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(1).build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(1).build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(1).build();
        productDAO.insertNewProduct(productRegisterDTO1);
        productDAO.insertNewProduct(productRegisterDTO2);
        productDAO.insertNewProduct(productRegisterDTO3);
        productDAO.insertNewProduct(productRegisterDTO4);
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(0);
    }
    @Test
    @DisplayName("상품명 한건 검색")
    void searchNameAndOneData() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm("만물상자")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(1).build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(1).build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(1).build();
        productDAO.insertNewProduct(productRegisterDTO1);
        productDAO.insertNewProduct(productRegisterDTO2);
        productDAO.insertNewProduct(productRegisterDTO3);
        productDAO.insertNewProduct(productRegisterDTO4);
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(1);
        assertThat(findAll.contains(productRegisterDTO1)).isTrue();
    }

    @Test
    @DisplayName("상품명 여러건 검색")
    void searchNameAndMuiltyData() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm("물상")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(1).build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(1).build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(1).build();
        productDAO.insertNewProduct(productRegisterDTO1);
        productDAO.insertNewProduct(productRegisterDTO2);
        productDAO.insertNewProduct(productRegisterDTO3);
        productDAO.insertNewProduct(productRegisterDTO4);
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(2);
        assertThat(findAll.contains(productRegisterDTO1)).isTrue();
        assertThat(findAll.contains(productRegisterDTO3)).isTrue();
    }

    @Test
    @DisplayName("시작금액 0원 검색")
    void searchZeroFromPrice() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(0)
                .toPrice(null).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(1).build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(1).build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(1).build();
        productDAO.insertNewProduct(productRegisterDTO1);
        productDAO.insertNewProduct(productRegisterDTO2);
        productDAO.insertNewProduct(productRegisterDTO3);
        productDAO.insertNewProduct(productRegisterDTO4);
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(4);
        assertThat(findAll.contains(productRegisterDTO1)).isTrue();
        assertThat(findAll.contains(productRegisterDTO2)).isTrue();
        assertThat(findAll.contains(productRegisterDTO3)).isTrue();
        assertThat(findAll.contains(productRegisterDTO4)).isTrue();
    }

    @Test
    @DisplayName("종료금액 0원 검색")
    void searchZeroToPrice() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(null)
                .toPrice(0).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(1).build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(1).build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(1).build();
        productDAO.insertNewProduct(productRegisterDTO1);
        productDAO.insertNewProduct(productRegisterDTO2);
        productDAO.insertNewProduct(productRegisterDTO3);
        productDAO.insertNewProduct(productRegisterDTO4);
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("시작,종료금액 0원 검색")
    void searchZeroPrice() {
        // given
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(0)
                .toPrice(0).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(1).build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(1).build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(1).build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(1).build();
        productDAO.insertNewProduct(productRegisterDTO1);
        productDAO.insertNewProduct(productRegisterDTO2);
        productDAO.insertNewProduct(productRegisterDTO3);
        productDAO.insertNewProduct(productRegisterDTO4);
        // when
        List<Product> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("상품 수정 - 성공")
    void test_1() {
        // given
        //기존 상품 등록
        ProductRegisterDTO productRegisterDTO = ProductRegisterDTO.builder()
                .productNm("테스트물품")
                .productPrice(15000)
                .userNum(1)
                .registerQuantity(2000)
                .productStatusCd("01")
                .build();

        productDAO.insertNewProduct(productRegisterDTO);

        // when
        productRegisterDTO.setProductNm("물품수정");
        productRegisterDTO.setRegisterQuantity(100);
        productRegisterDTO.setProductPrice(10000);

        productDAO.updateProduct(productRegisterDTO);

        // then
        Product editedProduct = productDAO.selectByProductNum(productRegisterDTO.getProductNum());
        assertThat(editedProduct.getProductNm()).isEqualTo("물품수정");
        assertThat(editedProduct.getRegisterQuantity()).isEqualTo(100);
        assertThat(editedProduct.getProductPrice()).isEqualTo(10000);
    }
}
