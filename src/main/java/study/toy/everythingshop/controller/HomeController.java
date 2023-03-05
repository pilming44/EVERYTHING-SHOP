package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.ProductMEntity;
import study.toy.everythingshop.repository.ProductDAO;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final ProductDAO productDAO;

//    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(@Validated  @ModelAttribute ProductSearchDTO productSearchDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            //검색값중 잘못된 값이 있다면 검색값 초기화
            productSearchDTO = new ProductSearchDTO();
        }

        if(productSearchDTO.getFromPrice() != null && productSearchDTO.getToPrice() != null
                && (productSearchDTO.getFromPrice() > productSearchDTO.getToPrice())) {
            //검색 시작가격이 종료가격보다 크다면 스왑
            Long tempPrice = productSearchDTO.getFromPrice();
            productSearchDTO.setFromPrice(productSearchDTO.getToPrice());
            productSearchDTO.setToPrice(tempPrice);
        }

        List<ProductMEntity> products = productDAO.findAll(productSearchDTO);
        model.addAttribute("products", products);

        return "home";
    }
}
