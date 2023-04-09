package study.toy.everythingshop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.service.ProductService;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * fileName : ProductControllerTest
 * author   : Annie
 * date     : 2023-04-02
 */
@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ProductDAO productDAO;

    @Test
    @DisplayName("상품등록 - 가격 오류")
    public void testProductRegisterWithBindingErrors1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/register")
                .param("productName", "test상품명")
                .param("price", "-10") // 가격을 -로 해서 바인딩 오류 발생
                .param("quantity", "100")
                .param("productStts", "01")
                .with(user("test").password("test").roles("01"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("productRegisterDTO", "price", "Range"))
                .andExpect(view().name("productRegister"));
    }

    @Test
    @DisplayName("상품등록 - 수량 오류")
    public void testProductRegisterWithBindingErrors2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/register")
                .param("productName", "test상품명")
                .param("price", "10")
                .param("quantity", "-100")
                .param("productStts", "01")
                .with(user("test").password("test").roles("01"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("productRegisterDTO", "quantity"))
                .andExpect(view().name("productRegister"));
    }
    @Test
    @DisplayName("상품등록 - 성공")
    public void testProductRegister_success() throws Exception {
        // given
        ProductRegisterDTO dto = new ProductRegisterDTO();
        dto.setProductName("test상품명");
        dto.setPrice(10L);
        dto.setQuantity(100L);
        dto.setProductStts("01");

        UserDetails userDetails = new User("test", "password", AuthorityUtils.createAuthorityList("01"));

        doReturn(1).when(productService).registerProduct(dto, userDetails);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/product/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("productRegisterDTO", dto)
                .with(user(userDetails))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("productRegi_success"))
                .andExpect(redirectedUrl("/home"));
    }
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
    void test_2() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "")
                        .param("price", "20000")
                        .param("quantity", "100"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "productName", "NotBlank"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 가격 미입력")
    void test_3() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "")
                        .param("quantity", "100"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "price", "NotNull"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 가격 타입오류")
    void test_4() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "문자열")
                        .param("quantity", "100"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "price", "typeMismatch"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 가격 범위오류(under)")
    void test_5() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "-1")
                        .param("quantity", "100"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "price", "Range"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 가격 범위오류(over)")
    void test_6() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "1000000000")
                        .param("quantity", "100"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "price", "Range"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 수량 미입력")
    void test_7() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "15000")
                        .param("quantity", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "quantity", "NotNull"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 수량 타입오류")
    void test_8() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "15000")
                        .param("quantity", "문자열"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "quantity", "typeMismatch"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 수량 범위오류(under)")
    void test_9() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "15000")
                        .param("quantity", "0"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "quantity", "Range"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 수량 범위오류(over)")
    void test_10() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "수정테스트물품")
                        .param("price", "15000")
                        .param("quantity", "1000000000"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "quantity", "Range"))
                .andExpect(view().name("productEdit"));
    }

    @Test
    @DisplayName("상품수정 - 복합검증오류")
    void test_11() throws Exception {
        //수정할 상품 데이터 입력
        ProductRegisterDTO productRegisterDTO = registerProductForEdit();

        productDAO.registerProduct(productRegisterDTO);

        //service진입하지않고 리턴됨.
        mockMvc.perform(MockMvcRequestBuilders.post("/product/{productNum}/edit", productRegisterDTO.getProductNum())
                        .param("productName", "")
                        .param("price", "-1")
                        .param("quantity", "0"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("product", "productName", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("product", "price", "Range"))
                .andExpect(model().attributeHasFieldErrorCode("product", "quantity", "Range"))
                .andExpect(view().name("productEdit"));
    }
}
