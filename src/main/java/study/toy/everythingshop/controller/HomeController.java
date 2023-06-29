package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.service.ProductService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@Trace
public class HomeController {

    private final ProductService productService;
    private final ProductDAO productDAO;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(@Validated  @ModelAttribute ProductSearchDTO productSearchDTO, BindingResult bindingResult, Model model) {
        log.info("시작시 productSearchDTO : {}", productSearchDTO);
        if(bindingResult.hasErrors()) {
            //검색값중 잘못된 값이 있다면 검색값 초기화
            productSearchDTO = new ProductSearchDTO();
            log.info("바인딩오류발생");
        }

        if(productSearchDTO.getFromPrice() != null && productSearchDTO.getToPrice() != null
                && (productSearchDTO.getFromPrice() > productSearchDTO.getToPrice())) {
            //검색 시작가격이 종료가격보다 크다면 스왑
            Integer tempPrice = productSearchDTO.getFromPrice();
            productSearchDTO.setFromPrice(productSearchDTO.getToPrice());
            productSearchDTO.setToPrice(tempPrice);
        }

        Map<String, Object> result = productService.findProductList(productSearchDTO);
        log.debug(" result : {}", result);
        model.addAttribute("products", result.get("list"));
        model.addAttribute("paginationInfo", result.get("paginationInfo"));
        if(!bindingResult.hasErrors()) {
            model.addAttribute("productSearchDTO", productSearchDTO);
        }
        log.info("종료시 productSearchDTO : {}", productSearchDTO);
        log.info("종료시 bindingResult : {}", bindingResult);
        return "home";
    }
}
