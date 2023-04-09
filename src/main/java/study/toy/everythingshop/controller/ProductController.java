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
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.ProductMEntity;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private final ProductService productService;
    private final MessageSource messageSource;

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

    @RequestMapping("/register")
    public String productRegister(@Validated @ModelAttribute ProductRegisterDTO productRegisterDTO,
                                  BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("productRegisterDTO",productRegisterDTO);
            log.info("바인딩오류발생");
            return "productRegister";
        }else{
            log.info("등록");
            int result = productService.registerProduct(productRegisterDTO,userDetails);
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

    @RequestMapping("/{productNum}/edit")
    public String productEditView(@PathVariable long productNum, Model model) {
        ProductMEntity productMEntity = productDAO.findByProductNum(productNum);

        log.info("productMEntity 객체 : {}", productMEntity);
        model.addAttribute("product", productMEntity);
        return "productEdit";
    }

    @RequestMapping(value = "/{productNum}/edit", method = RequestMethod.POST)
    public String productEdit(@PathVariable long productNum, @Validated @ModelAttribute("product") ProductRegisterDTO productRegisterDTO,
                              BindingResult bindingResult) {
        //검증. 이전 url의 productNum과 post요청의 productNum이 다를경우 AccessDeniedException
        //todo 추후 role을 적용할때 작성자 또는 권한을 가진사람이 productNum에 대해 수정권한이 있는지 체크 할것.

        if(bindingResult.hasErrors()) {
            log.info("bindingResult: {}", bindingResult);
            return "productEdit";
        }
        int updateResult = productService.editProduct(productRegisterDTO);

        //todo updateResult가 0일경우 예외처리 방법 필요
        log.info("updateResult : {}", updateResult);
        return "redirect:/product/"+productNum;
    }
    @GetMapping("/{productNum}/order")
    public String productOrderForm(@PathVariable long productNum, Model model ){
            ProductMEntity productMEntity = productDAO.findByProductNum(productNum);
            ModelMapper modelMapper = new ModelMapper();
            ProductOrderDTO productOrderDTO = modelMapper.map(productMEntity, ProductOrderDTO.class);

            log.info("productMEntity 객체 : {}", productMEntity);
            model.addAttribute("productOrderDTO", productOrderDTO);
            return "productOrder";
    }

    @PostMapping("/{productNum}/order")
    public String productOrder(@Validated @ModelAttribute ProductOrderDTO productOrderDTO,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal UserDetails userDetails){
        if(bindingResult.hasErrors()) {
            model.addAttribute("productOrderDTO",productOrderDTO);
            log.info("바인딩오류발생");
            log.info("ProductOrderDTO : "+productOrderDTO);
            return "productOrder";
        }
        if(productOrderDTO.getQuantity() < productOrderDTO.getOrderQuantity()){
            log.info("재고초과");
            String message = messageSource.getMessage("product.order.overQty", null, Locale.getDefault());
            redirectAttributes.addFlashAttribute("errorMessage", message);
            return "redirect:/product/" + productOrderDTO.getProductNum() + "/order";
        }
        log.info("등록");
        int result = productService.orderProduct(productOrderDTO,userDetails);
        log.info("result"+result);
            if(result >= 3){
                String message = messageSource.getMessage("product.order.success", null, Locale.getDefault());
                redirectAttributes.addFlashAttribute("productOrdr_success", message);
            }
        return "redirect:/product/"+ productOrderDTO.getProductNum() ;

    }

}
