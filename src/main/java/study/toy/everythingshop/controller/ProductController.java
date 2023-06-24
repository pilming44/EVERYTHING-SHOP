package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.toy.everythingshop.dto.ProductOrderDTO;
import study.toy.everythingshop.dto.ProductRegisterDTO;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.service.ProductService;
import study.toy.everythingshop.vo.ProductVO;

import java.util.Locale;

/**
 * fileName : ProductController
 * author   : pilming
 * date     : 2023-03-05
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
@Trace
public class ProductController {

    private final ProductDAO productDAO;
    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping("/{productNum}")
    public String findProductDetail(@PathVariable Integer productNum, Model model) {
        ProductVO product = productDAO.selectByProductNum(productNum);

        log.info("Product 객체 : {}", product);
        model.addAttribute("product", product);
        return "productDetail";
    }

    @GetMapping("/register")
    public String findProductRegisterForm(ProductRegisterDTO productRegisterDTO){
        return "productRegister";
    }

    @PostMapping("/register")
    public String saveProductRegister(@Validated @ModelAttribute ProductRegisterDTO productRegisterDTO,
                                  BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("productRegisterDTO",productRegisterDTO);
            log.info("바인딩오류발생");
            return "productRegister";
        }else{
            log.info("등록");
            int result = productService.saveNewProduct(productRegisterDTO,userDetails);
           if(result > 0){
               String message = messageSource.getMessage("product.register.success", null, Locale.getDefault());
               redirectAttributes.addFlashAttribute("productRegi_success", message);
           }else{
               String message = messageSource.getMessage("product.register.fail", null, Locale.getDefault());
               redirectAttributes.addFlashAttribute("productRegi_fail", message);
           }
            return "redirect:/home";
        }
    }

    @GetMapping("/{productNum}/edit")
    public String editProductView(@PathVariable Integer productNum, Model model) {
        ProductVO product = productDAO.selectByProductNum(productNum);

        log.info("product 객체 : {}", product);
        model.addAttribute("product", product);
        return "productEdit";
    }

    @PostMapping("/{productNum}/edit")
    public String editProduct(@PathVariable Integer productNum, @Validated @ModelAttribute("product") ProductRegisterDTO productRegisterDTO,
                              BindingResult bindingResult) {
        //todo 추후 role을 적용할때 작성자 또는 권한을 가진사람이 productNum에 대해 수정권한이 있는지 체크 할것.

        if(bindingResult.hasErrors()) {
            log.info("bindingResult: {}", bindingResult);
            return "productEdit";
        }
        log.info("productRegisterDTO : {}", productRegisterDTO);
        int updateResult = productService.editProduct(productRegisterDTO);

        //todo updateResult가 0일경우 예외처리 방법 필요
        log.info("updateResult : {}", updateResult);
        return "redirect:/product/"+productNum;
    }
    @GetMapping("/{productNum}/order")
    public String findProductOrderForm(@PathVariable Integer productNum, Model model ){
            ProductVO product = productDAO.selectByProductNum(productNum);
            ModelMapper modelMapper = new ModelMapper();
            ProductOrderDTO productOrderDTO = modelMapper.map(product, ProductOrderDTO.class);

            log.info("product 객체 : {}", product);
            model.addAttribute("productOrderDTO", productOrderDTO);
            return "productOrder";
    }

    @PostMapping("/{productNum}/order")
    public String saveProductOrder(@Validated @ModelAttribute ProductOrderDTO productOrderDTO,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal UserDetails userDetails){
        if(bindingResult.hasErrors()) {
            model.addAttribute("productOrderDTO",productOrderDTO);
            log.info("바인딩오류발생");
            log.info("ProductOrderDTO : "+productOrderDTO);
            return "productOrder";
        }
        if(productOrderDTO.getRegisterQuantity() < productOrderDTO.getOrderQuantity()){
            log.info("재고초과");
            String message = messageSource.getMessage("product.order.overQty", null, Locale.getDefault());
            redirectAttributes.addFlashAttribute("errorMessage", message);
            return "redirect:/product/" + productOrderDTO.getProductNum() + "/order";
        }
        log.info("등록");
        int result = productService.saveOrderProduct(productOrderDTO,userDetails.getUsername());
        log.info("result"+result);
        if(result >= 3){
            String message = messageSource.getMessage("product.order.success", null, Locale.getDefault());
            redirectAttributes.addFlashAttribute("productOrdr_success", message);
        }
        return "redirect:/product/"+ productOrderDTO.getProductNum() ;

    }

}
