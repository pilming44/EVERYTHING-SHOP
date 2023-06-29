package study.toy.everythingshop.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.auth.UserDetailsImpl;
import study.toy.everythingshop.controller.WithUser;
import study.toy.everythingshop.dto.ProductDTO;
import study.toy.everythingshop.dto.ProductEditDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.mariaDB.User;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WithUser(value = "admin") //@BeforeEach 와 @WithUserDetails 사용시 오류로 인해 커스텀 태그 생성
public class ProductDAOTest {

    @Autowired
    ProductDAO productDAO;

    //테스트용으로 생성된 사용자의 정보를 가져오는 매서드
    User getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser();
    }

    @Test
    @DisplayName("한 건 저장")
    void saveOneData() {
        // given
        User user = getUserInfo();
        ProductRegisterDTO productRegisterDTO = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        // when
        productDAO.insertProduct(productRegisterDTO);
        ProductDTO product = productDAO.selectByProductNum(productRegisterDTO.getProductNum());
        // then
        assertThat(productRegisterDTO.getProductNum()).isEqualTo(product.getProductNum());
        assertThat(productRegisterDTO.getProductNm()).isEqualTo(product.getProductNm());
        assertThat(productRegisterDTO.getProductStatusCd()).isEqualTo(product.getProductStatusCd());
        assertThat(productRegisterDTO.getProductPrice()).isEqualTo(product.getProductPrice());
        assertThat(productRegisterDTO.getRegisterQuantity()).isEqualTo(product.getRegisterQuantity());
        assertThat(productRegisterDTO.getPostYn()).isEqualTo(product.getPostYn());
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
        List<ProductDTO> findAll = productDAO.selectProductList(productSearchDTO);
        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("데이터가 한 건만 있을경우 목록조회")
    void noSearchParamAndOneData() {
        // given
        User user = getUserInfo();
        //검색조건은 없음
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(null)
                .toPrice(null).build();

        ProductRegisterDTO productRegisterDTO = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        productDAO.insertProduct(productRegisterDTO);
        // when
        int findCount = productDAO.selectProductListTotalCount(productSearchDTO);
        // then
        assertThat(findCount).isEqualTo(1);
    }

    @Test
    @DisplayName("데이터가 여러건 있을경우 목록조회")
    void noSearchParamAndMultipleData() {
        // given
        //사용자정보 조회
        User user = getUserInfo();
        //검색조건은 없음
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(null)
                .toPrice(null).build();

        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(user.getUserNum()).postYn("Y").build();
        productDAO.insertProduct(productRegisterDTO1);
        productDAO.insertProduct(productRegisterDTO2);
        productDAO.insertProduct(productRegisterDTO3);
        productDAO.insertProduct(productRegisterDTO4);
        // when
        int findCount = productDAO.selectProductListTotalCount(productSearchDTO);

        // then
        assertThat(findCount).isEqualTo(4);
    }

    @Test
    @DisplayName("상품명 검색결과 없음")
    void searchNameAndNoData() {
        // given
        //사용자정보 조회
        User user = getUserInfo();
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm("최재현")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(user.getUserNum()).postYn("Y").build();
        productDAO.insertProduct(productRegisterDTO1);
        productDAO.insertProduct(productRegisterDTO2);
        productDAO.insertProduct(productRegisterDTO3);
        productDAO.insertProduct(productRegisterDTO4);
        // when
        List<ProductDTO> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(0);
    }
    @Test
    @DisplayName("상품명 한건 검색")
    void searchNameAndOneData() {
        // given
        //사용자정보 조회
        User user = getUserInfo();
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm("만물상자")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(user.getUserNum()).postYn("Y").build();
        productDAO.insertProduct(productRegisterDTO1);
        productDAO.insertProduct(productRegisterDTO2);
        productDAO.insertProduct(productRegisterDTO3);
        productDAO.insertProduct(productRegisterDTO4);

        // when
        int findCount = productDAO.selectProductListTotalCount(productSearchDTO);

        // then
        assertThat(findCount).isEqualTo(1);
    }

    @Test
    @DisplayName("상품명 여러건 검색")
    void searchNameAndMuiltyData() {
        // given
        //사용자정보 조회
        User user = getUserInfo();
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm("물상")
                .fromPrice(null)
                .toPrice(null).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물상자").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(user.getUserNum()).postYn("Y").build();
        productDAO.insertProduct(productRegisterDTO1);
        productDAO.insertProduct(productRegisterDTO2);
        productDAO.insertProduct(productRegisterDTO3);
        productDAO.insertProduct(productRegisterDTO4);
        // when
        int findCount = productDAO.selectProductListTotalCount(productSearchDTO);

        // then
        assertThat(findCount).isEqualTo(2);
    }

    @Test
    @DisplayName("시작금액 0원 검색")
    void searchZeroFromPrice() {
        // given
        //사용자정보 조회
        User user = getUserInfo();
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(0)
                .toPrice(null).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(user.getUserNum()).postYn("Y").build();
        productDAO.insertProduct(productRegisterDTO1);
        productDAO.insertProduct(productRegisterDTO2);
        productDAO.insertProduct(productRegisterDTO3);
        productDAO.insertProduct(productRegisterDTO4);
        // when
        int findCount = productDAO.selectProductListTotalCount(productSearchDTO);

        // then
        assertThat(findCount).isEqualTo(4);
    }

    @Test
    @DisplayName("종료금액 0원 검색")
    void searchZeroToPrice() {
        // given
        //사용자정보 조회
        User user = getUserInfo();
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(null)
                .toPrice(0).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(user.getUserNum()).postYn("Y").build();
        productDAO.insertProduct(productRegisterDTO1);
        productDAO.insertProduct(productRegisterDTO2);
        productDAO.insertProduct(productRegisterDTO3);
        productDAO.insertProduct(productRegisterDTO4);
        // when
        List<ProductDTO> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("시작,종료금액 0원 검색")
    void searchZeroPrice() {
        // given
        //사용자정보 조회
        User user = getUserInfo();
        //검색조건
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder()
                .productNm(null)
                .fromPrice(0)
                .toPrice(0).build();

        //상품입력
        ProductRegisterDTO productRegisterDTO1 = ProductRegisterDTO.builder().productNm("만물상자").productStatusCd("02").productPrice(30000).registerQuantity(30).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO2 = ProductRegisterDTO.builder().productNm("과자").productStatusCd("01").productPrice(10000).registerQuantity(21).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO3 = ProductRegisterDTO.builder().productNm("선물").productStatusCd("02").productPrice(23000).registerQuantity(5).userNum(user.getUserNum()).postYn("Y").build();
        ProductRegisterDTO productRegisterDTO4 = ProductRegisterDTO.builder().productNm("책").productStatusCd("01").productPrice(5000).registerQuantity(10).userNum(user.getUserNum()).postYn("Y").build();
        productDAO.insertProduct(productRegisterDTO1);
        productDAO.insertProduct(productRegisterDTO2);
        productDAO.insertProduct(productRegisterDTO3);
        productDAO.insertProduct(productRegisterDTO4);
        // when
        List<ProductDTO> findAll = productDAO.selectProductList(productSearchDTO);

        // then
        assertThat(findAll).hasSize(0);
    }

    @Test
    @DisplayName("상품 수정 - 성공")
    void test_1() {
        // given
        //사용자정보 조회
        User user = getUserInfo();
        //기존 상품 등록
        ProductRegisterDTO productRegisterDTO = ProductRegisterDTO.builder()
                .productNm("테스트물품")
                .productPrice(15000)
                .userNum(user.getUserNum())
                .registerQuantity(2000)
                .productStatusCd("01")
                .build();

        productDAO.insertProduct(productRegisterDTO);

        // when
        ProductEditDTO productEditDTO = ProductEditDTO.builder()
                .productNum(productRegisterDTO.getProductNum())
                .productNm("물품수정")
                .productPrice(10000)
                .userNum(user.getUserNum())
                .registerQuantity(3000)
                .productStatusCd("01")
                .postYn("Y")
                .build();

        productDAO.updateProduct(productEditDTO);

        // then
        ProductDTO editedProduct = productDAO.selectByProductNum(productEditDTO.getProductNum());
        log.debug("editedProduct : {}",editedProduct);
        assertThat(editedProduct.getProductNm()).isEqualTo("물품수정");
        assertThat(editedProduct.getRegisterQuantity()).isEqualTo(3000);
        assertThat(editedProduct.getProductPrice()).isEqualTo(10000);
    }
}