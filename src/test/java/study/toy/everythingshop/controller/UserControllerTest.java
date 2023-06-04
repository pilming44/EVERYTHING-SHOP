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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;
import study.toy.everythingshop.dto.ErrorResponse;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.service.UserService;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * fileName : UserControllerTest
 * author   : Annie
 * date     : 2023-04-01
 */
@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Test
    @DisplayName("회원가입 테스트 - 성공")
    public void testJoin_success() throws Exception {
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUserId("testuser");
        joinDTO.setUserPw("testpassword");
        joinDTO.setUserNm("testName");

        doNothing().when(userService).findDupId(anyString());
        doReturn(1).when(userService).saveNewMember(joinDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/join")
                .param("userId", "testuser")
                .param("userPw", "testpassword")
                .param("userNm", "testName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users/signIn"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("successMessage"));
    }

    @Test
    @DisplayName("회원가입 테스트 - 실패 (중복 ID)")
    public void testJoin_failure_duplicateId() throws Exception {
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setUserId("testuser");
        joinDTO.setUserPw("testpassword");
        joinDTO.setUserNm("testName");

        doThrow(new ResponseStatusException(HttpStatus.CONFLICT)).when(userService).findDupId(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/join")
                .param("userId", "testuser")
                .param("userPw", "testpassword")
                .param("userNm", "testName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users/join"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("failMessage"));
    }

    @Test
    @DisplayName("중복 ID 검사 테스트 - 사용 가능")
    public void testCheckDupId_available() throws Exception {
        String userId = "testuser";
        doNothing().when(userService).findDupId(userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/join/checkDupId")
                .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(messageSource.getMessage("id.available", null, Locale.getDefault())));
    }

    @Test
    @DisplayName("중복 ID 검사 테스트 - 사용 불가능")
    public void testCheckDupId_notAvailable() throws Exception {
        String userId = "testuser";

        String errorMessage = messageSource.getMessage("user.alreadyExists", null, Locale.getDefault());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), errorMessage);
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT, errorMessage)).when(userService).findDupId(userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/join/checkDupId")
                .param("userId", userId))
                .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)));
    }

}
