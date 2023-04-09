package study.toy.everythingshop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.entity.ProductMEntity;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.service.ProductService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * fileName : ProductControllerTest
 * author   : pilming
 * date     : 2023-04-08
 */
@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ProductDAO productDAO;

    private ProductRegisterDTO registerProductForEdit() {
        //수정할 상품 데이터 입력
        return ProductRegisterDTO.builder()
                .productName("테스트물품")
                .price(15000L)
                .userNum(1L)
                .quantity(2000L)
                .productStts("01")
                .build();
    }

    @Test
    @DisplayName("상품수정 - 성공")
    void test_1() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //서비스에서 업데이트 성공 리턴하는 mock객체
        doReturn(1).when(productService).editProduct(any(ProductRegisterDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "20000")
                        .param("quantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/"+productRegisterDTO.getProductNum()));
    }

    @Test
    @DisplayName("상품수정 - 상품명 미입력")
    void test_2() {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();
    }

    @Test
    @DisplayName("상품수정 - 가격 미입력")
    void test_3() {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();
    }

    @Test
    @DisplayName("상품수정 - 가격 타입오류")
    void test_4() {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();
    }

    @Test
    @DisplayName("상품수정 - 가격 범위오류")
    void test_5() {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();
    }

    @Test
    @DisplayName("상품수정 - 수량 미입력")
    void test_6() {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();
    }

    @Test
    @DisplayName("상품수정 - 수량 타입오류")
    void test_7() {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();
    }

    @Test
    @DisplayName("상품수정 - 수량 범위오류")
    void test_8() {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();
    }
}