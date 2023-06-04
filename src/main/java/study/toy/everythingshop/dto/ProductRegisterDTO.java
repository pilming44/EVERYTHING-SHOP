package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * fileName : ProductRegisterDTO
 * author   : Annie
 * date     : 2023-03-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegisterDTO {
    //maria DB용 필드 시작
    private Integer productNum;             //상품번호

    private Integer userNum;                //사용자번호(판매자)

    @NotBlank(message = "{NotBlank.product.productNm}")
    private String productNm;               //상품명

    @Range(min = 1, max = 999999999)
    @NotNull(message = "{NotNull.product.registerQuantity}")
    private Integer registerQuantity;       //등록수량

    @Range(min = 0, max = 999999999)
    @NotNull(message = "{NotNull.product.productPrice}")
    private Integer productPrice;           //상품가격

    private String productStatusCd;         //상품 상태 코드

    private String postYn;                  //게시여부
    //maria DB용 필드 끝


    //h2 DB용 필드 시작
//    private Long productNum; //상품넘버
//    @NotBlank(message = "{NotBlank.product.productName}")
//    private String productName;     //상품명
//    private Long userNum; //등록자번호
//    private String productStts; //상품상태
//
//    @Range(min = 1, max = 999999999)
//    @NotNull(message = "{NotNull.product.quantity}")
//    private Long quantity; //수량
//
//    @Range(min = 0, max = 999999999)
//    @NotNull(message = "{NotNull.product.price}")
//    private Long price;    //가격
    //h2 DB용 필드 끝

}
