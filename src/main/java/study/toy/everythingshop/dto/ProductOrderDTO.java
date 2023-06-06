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
    //maria DB용 필드 시작
    private Integer productNum;    //상품번호
    private Integer orderNum;      //주문번호
    private String productNm;       //상품명
    private Integer productPrice;  //상품가격
    private Integer registerQuantity;   //등록수량

    @NotNull(message = "{NotNull.product.orderQuantity}")
    @Range(min = 1, max = 999999999)
    private Integer orderQuantity; //주문수량(개)

    private Integer discountPrice;  //할인금액(계정등급에따른 할인)
    private Integer finalPaymentPrice;  //최종결제금액(총금액 - 할인금액)
    private Integer userNum;        //사용자번호(구매자)
    //maria DB용 필드 끝

    //h2 DB용 필드 시작
//    private Long productNum; //상품넘버
//    private Long orderNum; //주문넘버
//    private Long orderProductNum; //상품주문넘버
//    private String productName; //상품명
//    private Long price; //가격
//    private Long userNum; //등록자번호
//    private String productStts; //상품상태
//    private Long quantity; //수량
//    @NotNull(message = "{NotNull.product.orderQuantity}")
//    @Range(min = 1, max = 999999999)
//    private Long orderQuantity; //주문수량
//    private Long totalPrice; //총주문가격
    //h2 DB용 필드 끝
}
