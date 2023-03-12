package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.toy.everythingshop.entity.UserMEntity;
import study.toy.everythingshop.repository.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MembersController {
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/join")
    public String joinForm(UserMEntity userMEntity){
        return "/join";
    }
    
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute UserMEntity userMEntity, BindingResult bindingResult, Model model){

        return "/join";
    }

    @GetMapping("/join/checkDupId")
    public String join(@Validated @ModelAttribute("userId") String userId, BindingResult bindingResult, Model model){
        membersService.checkDupId(userId);
        return "/join";
    }
    @GetMapping("/signIn")
    public String signIn(Model model, HttpSession session) {
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
    public String testUserJoin(UserMEntity userMEntity) {
        String rawPassword = userMEntity.getUserPw();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userMEntity.setUserPw(encPassword);
        userDAO.save(userMEntity);
        return "redirect:/";
    }
}
