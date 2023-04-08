package study.toy.everythingshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.server.ResponseStatusException;
import study.toy.everythingshop.dto.ErrorResponse;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.service.ProductService;
import study.toy.everythingshop.service.UserService;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
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

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Autowired
    private MessageSource messageSource;

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


}
