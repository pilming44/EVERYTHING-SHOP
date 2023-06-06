package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.MyPageService;

import java.util.List;

/**
 * fileName : MypageController
 * author   : pilming
 * date     : 2023-03-29
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/myPage")
@Trace
public class MyPageController {

    private final UserDAO userDAO;
    private final MyPageService myPageService;
    private final ProductDAO productDAO;

    @GetMapping("")
    public String myPage() {
        return "myPage";
    }

    @GetMapping("/myInfo")
    public String findMyInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        //AuthenticationPrincipal어노테이션으로 현재 로그인한 사용자의 정보를 나타내는 객체인 Principal객체를 주입받을수있다
        //이렇게 주입받은 객체는 UserDetails타입으로 캐스팅해서 사용 가능

        //사용자 정보 조회
        User user = userDAO.selectByeUserId(userDetails.getUsername());

        //디버깅
        log.info(">>>>>>>>>>>>user : {}",user);

        //모델 추가
        model.addAttribute("userInfo", user);
        return "myInfo";
    }

    @GetMapping("/editMyInfo")
    public String editMyInfoView(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        //AuthenticationPrincipal어노테이션으로 현재 로그인한 사용자의 정보를 나타내는 객체인 Principal객체를 주입받을수있다
        //이렇게 주입받은 객체는 UserDetails타입으로 캐스팅해서 사용 가능

        //사용자 정보 조회
        User user = userDAO.selectByeUserId(userDetails.getUsername());

        //디버깅
        log.info(">>>>>>>>>>>>userMEntity : {}",user);

        //모델 추가
        model.addAttribute("userInfo", user);
        return "editMyInfo";
    }

    @PostMapping("/editMyInfo/{userId}")
    public String editMyInfo(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String userId,
                             @Validated @ModelAttribute("userInfo") User user, BindingResult bindingResult) {
        log.info("user={}", user);
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "editMyInfo";
        }

        //로그인 한 아이디와 수정 요청 한 아이디가 동일한지 체크
        if (!userId.equals(userDetails.getUsername()) || !user.getUserId().equals(userDetails.getUsername())) {
            //todo 예외 페이지로 처리할지 아니면 수정화면에서 bindingResult로 처리할지 결정 필요
            throw new AccessDeniedException("Cannot update other users' information");
        }

        myPageService.editUserInfo(user);

        //디버깅
        log.info(">>>>>>>>>>>>user : {}",user);
        log.info(">>>>>>>>>>>>userId : {}",userId);

        return "redirect:/myPage/myInfo";
    }

    @GetMapping("/myOrderList")
    public String findMyOrderList(@AuthenticationPrincipal UserDetails userDetails, Model model,
                              @Validated  @ModelAttribute ProductSearchDTO productSearchDTO, BindingResult bindingResult) {
        //사용자 정보 조회
        User user = userDAO.selectByeUserId(userDetails.getUsername());
        Integer userNum = user.getUserNum();
        productSearchDTO.setUserNum(userNum);

        if(bindingResult.hasErrors()) {
            //검색값중 잘못된 값이 있다면 검색값 초기화
            productSearchDTO = new ProductSearchDTO();
            log.info("바인딩오류발생");
        }
        List<ProductOrderDTO> myOrderList = myPageService.findMyOrderList(productSearchDTO);
        if (myOrderList.isEmpty()) {
            model.addAttribute("message", "주문 내역이 없습니다.");
        } else {
            model.addAttribute("products", myOrderList);
            model.addAttribute("productSearchDTO", productSearchDTO);
        }
        if(!bindingResult.hasErrors()) {
            model.addAttribute("productSearchDTO", productSearchDTO);
        }
        return "myOrderList";
    }
}
