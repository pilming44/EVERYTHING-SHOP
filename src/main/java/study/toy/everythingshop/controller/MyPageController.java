package study.toy.everythingshop.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.toy.everythingshop.auth.CustomUserDetails;
import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.MyPageDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.MyPageService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final MyPageDAO myPageDAO;

    @GetMapping("")
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        //모델 추가
        model.addAttribute("myPageInfo", myPageService.findMyPageInfo(userDetails.getUsername()));
        return "myPage";
    }

    @GetMapping("/myInfo")
    public String findMyInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        //AuthenticationPrincipal어노테이션으로 현재 로그인한 사용자의 정보를 나타내는 객체인 Principal객체를 주입받을수있다
        //이렇게 주입받은 객체는 UserDetails타입으로 캐스팅해서 사용 가능

        //사용자 정보 조회
        User user = userDAO.selectUserById(userDetails.getUsername());

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
        User user = userDAO.selectUserById(userDetails.getUsername());

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
        User user = userDAO.selectUserById(userDetails.getUsername());
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

    @GetMapping("/sellerApplyList")
    public String sellerApplyListView(SellerApplyDTO sellerApplyDTO, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        //사용자 정보 조회
        User user = userDAO.selectUserById(userDetails.getUsername());

        sellerApplyDTO.setUserNum(user.getUserNum());

        Map<String, Object> result = myPageService.findSellerApplyList(sellerApplyDTO);
        //디버깅
        log.debug(">>>>>>>>>>>>user : {}",user);
        model.addAttribute("applyCount", myPageService.findApplyCount(user.getUserNum()));
        model.addAttribute("applyList", result.get("list"));
        model.addAttribute("paginationInfo", result.get("paginationInfo"));
        return "sellerApplyList";
    }

    @PostMapping("/sellerApply")
    public String sellerApply(@AuthenticationPrincipal UserDetails userDetails) {
        //사용자 정보 조회
        User user = userDAO.selectUserById(userDetails.getUsername());
        //디버깅
        log.debug(">>>>>>>>>>>>user : {}",user);
        myPageService.addSellerApply(user.getUserNum());
        return "redirect:/myPage/sellerApplyList";
    }

    @GetMapping("/discountPolicy")
    public String discountPolicyView(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        //TODO 사용자 정보 검증
        User user = userDAO.selectUserById(userDetails.getUsername());

        //타임리프에서 리스트데이터를 받기위한 Wrapper.
        DiscountPolicyWrapper discountPolicyWrapper = new DiscountPolicyWrapper();
        discountPolicyWrapper.setDiscountPolicy(myPageService.findDiscountPolicy());

        model.addAttribute("discountPolicyWrapper", discountPolicyWrapper);
        return "discountPolicy";
    }

    @PostMapping("/discountPolicy")
    public String editDiscountPolicy(@Validated @ModelAttribute DiscountPolicyWrapper discountPolicyWrapper, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        if(bindingResult.hasErrors()) {
            log.info("바인딩오류발생");
            log.info("종료시 bindingResult : {}", bindingResult);
            return "discountPolicy";
        }

        //사용자 정보 조회
        User user = userDAO.selectUserById(userDetails.getUsername());
        //디버깅
        log.debug(">>>>>>>>>>>>discountPolicyWrapper : {}",discountPolicyWrapper.getDiscountPolicy());

        myPageService.editDiscountPolicy(discountPolicyWrapper.getDiscountPolicy());

        return "redirect:/myPage/discountPolicy";
    }

    @GetMapping("/pointHistory")
    public String pointHistoryView(@ModelAttribute PointHistoryDTO pointHistoryDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        //사용자 정보 조회
        User user = userDAO.selectUserById(customUserDetails.getUsername());
        log.debug(">>>>>>>>>>>>getFromDate : {}",pointHistoryDTO.getFromDate());
        log.debug(">>>>>>>>>>>>getEndDate : {}",pointHistoryDTO.getEndDate());

        pointHistoryDTO.setUserNum(user.getUserNum());

        Map<String, Object> result = myPageService.findPointHistory(pointHistoryDTO);
        //디버깅
        log.debug(">>>>>>>>>>>>user : {}",user);
        model.addAttribute("pointHistory", result.get("list"));
        model.addAttribute("paginationInfo", result.get("paginationInfo"));
        return "pointHistory";
    }

    @GetMapping("/salesSummary")
    public String salesSummaryView(@ModelAttribute SalesSummaryDTO salesSummaryDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        //사용자 정보 조회
        User user = userDAO.selectUserById(customUserDetails.getUsername());
        log.debug(">>>>>>>>>>>>getFromDate : {}",salesSummaryDTO.getFromDate());
        log.debug(">>>>>>>>>>>>getEndDate : {}",salesSummaryDTO.getEndDate());

        Map<String, Object> result = myPageService.findSalesSummary(salesSummaryDTO);
        //디버깅
        log.debug(">>>>>>>>>>>>user : {}",user);
        model.addAttribute("salesSummary", result.get("list"));
        model.addAttribute("totalSalesPrice", myPageDAO.selectTotalSalesPrice());
        model.addAttribute("paginationInfo", result.get("paginationInfo"));
        return "salesSummary";
    }

    @GetMapping("/admin/allUserView")
    public String allUserView(@ModelAttribute UserInfoDTO userInfoDTO, @ModelAttribute UserSearchDTO userSearchDTO, Model model) {

        //전체 사용자 정보 조회
        Map<String, Object> userMap = myPageService.selectAllUserInfo(userSearchDTO);
        //디버깅
        log.debug(">>>>>>>>>>>>userList : {}",userMap);
        model.addAttribute("userList", userMap.get("userList"));
        model.addAttribute("paginationInfo", userMap.get("paginationInfo"));
        return "allUserView";
    }

    @GetMapping("/admin/userInfo")
    public String userInfo(@RequestParam("userId") String userId ,@ModelAttribute UserInfoDTO userInfoDTO, Model model) {

        //특정 사용자 정보 조회
        User user = userDAO.selectUserById(userId);
        // User 정보를 UserInfoDTO로 매핑
        ModelMapper modelMapper = new ModelMapper();
        userInfoDTO = modelMapper.map(user, UserInfoDTO.class);
        log.info("userInfoDTO >>>>>>>>>>>>>>>{} ",userInfoDTO);

        model.addAttribute("userInfo",userInfoDTO);
        return "userInfo";
    }

    /**
     * 타임리프에서 리스트데이터를 받기위한 Wrapper.
     * Wrapper사용해서 view로 전달하고 view에서 Wrapper를 submit해야 list로 바인딩이 가능함
     */
    @Getter
    @Setter
    class DiscountPolicyWrapper {
        @Valid
        private List<DiscountPolicyDTO> discountPolicy = new ArrayList<>();
    }
}
