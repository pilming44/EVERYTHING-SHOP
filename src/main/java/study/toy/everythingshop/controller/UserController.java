package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.toy.everythingshop.dto.ErrorResponse;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpSession;

import java.util.Locale;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Trace
public class UserController {
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/join")
    public String findJoinForm(JoinDTO joinDTO){
        return "join";
    }
    
    @PostMapping("/join")
    public String saveJoin(@Validated @ModelAttribute JoinDTO joinDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("joinDTO", joinDTO);
            return "/join";
        }

        // Spring Security를 사용하여 현재 사용자의 역할을 확인합니다.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_01"));

        String url = "/users/signIn";
        String message = "";

        if(isAdmin){
            url = "/myPage/admin/allUserView";
        }

        int result = userService.saveMember(joinDTO);
        if(result > 0){
            if(isAdmin){
                message = messageSource.getMessage("user.insertUser", null, Locale.getDefault());
            }else{
                message = messageSource.getMessage("id.joinSuccess", null, Locale.getDefault());
            }
            redirectAttributes.addFlashAttribute("successMessage", message);
            return "redirect:"+url;
        }else{
            if(isAdmin){
                message = messageSource.getMessage("user.failInsertUser", null, Locale.getDefault());
            }else{
                message = messageSource.getMessage("id.joinFail", null, Locale.getDefault());
            }
            redirectAttributes.addFlashAttribute("failMessage", message);
            return "redirect:"+url;
        }

    }

    @GetMapping("/join/checkDupId")
    @ResponseBody
    public Object findCheckDupId(@RequestParam("userId") String userId) {
        try {
            userService.findDupId(userId);
            String message = messageSource.getMessage("id.available", null, Locale.getDefault());
            return message;
        } catch (ResponseStatusException ex) {
            HttpStatus status = ex.getStatus();
            String message = messageSource.getMessage("user.alreadyExists", null, Locale.getDefault());
            ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
            return errorResponse;
        }
    }
    @GetMapping("/signIn")
    public String saveSignIn(Model model, HttpSession session) {
        String errorMessage = (String) session.getAttribute("errorMessage");
        //log.info(errorMessage);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            //세션정보 담은 후 세션은 삭제
            session.removeAttribute("errorMessage");
        }
        return "signIn";
    }

    @GetMapping("/testJoin")
    public String testJoin(){
        return "testJoin";
    }

    @PostMapping("/testJoin")
    public String testUserJoin(JoinDTO joinDTO) {
        String rawPassword = joinDTO.getUserPw();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        joinDTO.setUserPw(encPassword);
        userDAO.insertUser(joinDTO);
        return "redirect:/";
    }


}
