package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import study.toy.everythingshop.dto.JoinDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.ProductMEntity;
import study.toy.everythingshop.repository.ProductDAO;

/**
 * fileName : ProductController
 * author   : pilming
 * date     : 2023-03-05
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductDAO productDAO;

    @RequestMapping("/{productNum}")
    public String productDetail(@PathVariable long productNum, Model model) {
        ProductMEntity productMEntity = productDAO.findByProductNum(productNum);

        log.info("productMEntity 객체 : {}", productMEntity);
        model.addAttribute("product", productMEntity);
        return "productDetail";
    }

    @GetMapping("/register")
    public String productRegisterForm(ProductRegisterDTO productRegisterDTO){
        return "productRegister";
    }

}
