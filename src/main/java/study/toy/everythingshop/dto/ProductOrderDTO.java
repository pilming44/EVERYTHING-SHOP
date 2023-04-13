package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDTO {

    private Long productNum; //상품넘버
    private Long orderNum; //주문넘버
    private Long orderProductNum; //상품주문넘버
    private String productName; //상품명
    private Long price; //가격
    private Long userNum; //등록자번호
    private String productStts; //상품상태
    private Long quantity; //수량
    @NotNull(message = "{NotNull.product.orderQuantity}")
    @Range(min = 1, max = 999999999)
    private Long orderQuantity; //주문수량
    private Long totalPrice; //총주문가격

}
