package study.toy.everythingshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.toy.everythingshop.dto.ProductSearchDTO;
import study.toy.everythingshop.entity.h2.ProductMEntity;
import study.toy.everythingshop.repository.ProductDAO;

import java.util.List;

/**
 * fileName : RestHomeController
 * author   : pilming
 * date     : 2023-03-05
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RestHomeController {
    private final ProductDAO productDAO;

    @GetMapping("/home")
    public List<ProductMEntity> home(@Validated @ModelAttribute ProductSearchDTO productSearchDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid Request");
        }

        if(productSearchDTO.getFromPrice() != null && productSearchDTO.getToPrice() != null
                && (productSearchDTO.getFromPrice() > productSearchDTO.getToPrice())) {
            //검색 시작가격이 종료가격보다 크다면 스왑
            Long tempPrice = productSearchDTO.getFromPrice();
            productSearchDTO.setFromPrice(productSearchDTO.getToPrice());
            productSearchDTO.setToPrice(tempPrice);
        }

        List<ProductMEntity> products = productDAO.findAll(productSearchDTO);

        return products;
    }

    @ExceptionHandler(value = { InvalidRequestException.class })
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    static class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String message) {
            super(message);
        }
    }
}
