package study.toy.everythingshop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.service.UserService;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * fileName : myPageControllerTest
 * author   : pilming
 * date     : 2023-03-30
 */

@SpringBootTest
@AutoConfigureMockMvc
@Transactional //계정 중복 생성을 막기위해 추가
public class myPageControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("비로그인 마이페이지 접근시 리다이렉트")
    void test_1() throws Exception {
        mockMvc.perform(get("/myPage"))
                .andExpect(status().is3xxRedirection()) //상태코드가 3xx인지 체크
                .andExpect(redirectedUrlPattern("**/users/signIn")); //리다이렉트 경로 체크
    }

    @Test
    @DisplayName("로그인 사용자 마이페이지 접근")
    @WithMockUser
    void test_2() throws Exception {
        mockMvc.perform(get("/myPage"))
                .andExpect(status().isOk()) //상태코드가 OK(200)인지 체크
                .andExpect(view().name("myPage")); //화면 이름 체크
    }

    @Test
    @DisplayName("로그인 사용자 내정보 조회 접근")
    @WithUser(value = "admin") //@BeforeEach 와 @WithUserDetails 사용시 오류로 인해 커스텀 태그 생성
    void test_3() throws Exception{
        mockMvc.perform(get("/myPage/myInfo"))
                .andExpect(status().isOk()) //상태코드가 OK(200)인지 체크
                .andExpect(view().name("myInfo")) //화면 이름 체크
                .andExpect(model().attributeExists("userInfo")) //모델 객체 체크
                .andExpect(model().attribute("userInfo", hasProperty("userId", is("admin")))) //모델 객체의 userId 속성 체크
                .andExpect(model().attribute("userInfo", hasProperty("userNm", is("admin이름")))); //모델 객체의 userNm 속성 체크
    }

    @Test
    @DisplayName("로그인 사용자 내정보 수정")
    @WithUser(value = "admin") //@BeforeEach 와 @WithUserDetails 사용시 오류로 인해 커스텀 태그 생성
    void test_4() throws Exception{
        //todo
    }
}
