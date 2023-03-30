package study.toy.everythingshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.dto.SignInDTO;
import study.toy.everythingshop.entity.UserMEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * fileName : LoginProcTest
 * author   : pilming
 * date     : 2023-03-30
 */

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginProcTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;

    @BeforeEach
    void beforeEach() {
        //테스트용 사용자 계정 생성
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setUserId("testId");
        signInDTO.setUserPw("testPw");
        signInDTO.setUserNm("테스트계정");
        userService.insertMember(signInDTO);
    }

    @Test
    @DisplayName("로그인 성공")
    void test_1() throws Exception {
        mockMvc.perform(post("/loginProc")
                        .param("userId", "testId")
                        .param("userPw", "testPw")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection()) //리다이렉트 여부 체크
                .andExpect(redirectedUrl("/")) //리다이렉트 경로 체크
                .andExpect(authenticated().withAuthentication(authentication -> {
                    //사용자 이름 인증 체크
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof UserMEntity) {
                        String userNm = ((UserMEntity) principal).getUserNm();
                        assertThat(userNm).isEqualTo("테스트계정");
                    }
                }));
    }

    @Test
    @DisplayName("로그인 실패")
    void test_2() throws Exception {
        mockMvc.perform(post("/loginProc")
                        .param("userId", "testId")
                        .param("userPw", "wrongTestPw")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection()) //리다이렉트 여부 체크
                .andExpect(redirectedUrl("/users/signIn"))  // 리다이렉트 경로 체크
                .andExpect(unauthenticated()); //비인증 체크
    }

    @Test
    @DisplayName("로그아웃")
    void test_3() throws Exception {
        mockMvc.perform(post("/logout") // 로그아웃요청
                        .with(csrf()))
                .andExpect(status().is3xxRedirection()) //리다이렉트 여부 체크
                .andExpect(redirectedUrl("/users/signIn?logout"))  // 리다이렉트 경로 체크
                .andExpect(unauthenticated()); //비인증 체크
    }
}
