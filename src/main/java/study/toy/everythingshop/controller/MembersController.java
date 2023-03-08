package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.toy.everythingshop.entity.UserMEntity;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.service.MembersService;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MembersController {

    private final MembersService membersService;

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




}
