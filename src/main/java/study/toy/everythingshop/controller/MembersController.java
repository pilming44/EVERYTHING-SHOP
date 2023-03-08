package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
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

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MembersController {
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/join")
    public String join(){
        return "/members/join";
    }

    @GetMapping("/signIn")
    public String signIn() {
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
