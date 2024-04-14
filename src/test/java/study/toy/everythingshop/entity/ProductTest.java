package study.toy.everythingshop.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.entity.mariaDB.Product;
import study.toy.everythingshop.entity.mariaDB.ProductViews;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.enums.ProductStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@Transactional
@SpringBootTest
public class ProductTest {
    private Product product;
    private ProductViews productViews;

    @BeforeEach
    public void setUp() {
        // 각 테스트 전에 필요한 초기화 작업 수행

        productViews = ProductViews.builder()
                .views(3)
                .build();

        product = Product.builder()
                .productNum(1)
                .user(new User())
                .productNm("TestProduct")
                .registerQuantity(100)
                .remainQuantity(100)
                .productPrice(10000)
                .productStatusCd("02")
                .postYn("Y")
                .productViews(productViews)
                .build();
    }

    @Test
    @DisplayName("주문에따른 상품재고수량 변경 및 상품상태변경이 적절하게 이루어진다. - 재고가 남았을 때")
    public void testUpdateByOrdered() {
        // 테스트할 객체 생성
        Integer orderedQty = 30;
        product.updateByOrdered(orderedQty);
        // 결과 검증
        assertThat(70).isEqualTo(product.getRemainQuantity());
        assertThat(ProductStatus.ON_SALE.getCode()).isEqualTo(product.getProductStatusCd());
    }

    @Test
    @DisplayName("주문에따른 상품재고수량 변경 및 상품상태변경이 적절하게 이루어진다. - 품절이 되었을 때")
    public void testUpdateByOrdered_soldOut() {
        Integer orderedQty = 100;
        product.updateByOrdered(orderedQty);
        // 결과 검증
        assertThat(0).isEqualTo(product.getRemainQuantity());
        assertThat(ProductStatus.SOLD_OUT.getCode()).isEqualTo(product.getProductStatusCd());
    }

    @Test
    @DisplayName("현재 조회수를 가져온다.")
    public void getViews() {
        // 결과 검증
        assertThat(3).isEqualTo(product.getViews());
    }

    @Test
    @DisplayName("조회수를 추가 한다.")
    public void increaseView() {
        product.increaseView();
        // 결과 검증
        assertThat(4).isEqualTo(productViews.getViews());
    }

    @Test
    @DisplayName("할인율에 따른 할인가격을 가져온다.")
    public void getDiscountPrice() {
        Integer discountRate = 30;
        // 결과 검증
        assertThat(3000).isEqualTo(product.getDiscountPrice(discountRate));
    }

    @Test
    @DisplayName("판매된수량을 가져온다.")
    public void getSalesQuantity(){
        // 결과 검증
        assertThat(0).isEqualTo(product.getSalesQuantity());
    }

    @Test
    @DisplayName("할인된 가격을 가져온다.")
    public void getCurrentPrice(){
        assertThat(7000).isEqualTo(product.getCurrentPrice(3000));
    }

}
