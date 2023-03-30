package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

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

    private Long productNum; //상품넘버
    private String productName;     //상품명
    private Long userNum; //등록자번호
    private String productStts; //상품상태
    @Range(min = 0, max = 999999999)
    private Long quantity; //수량
    @Range(min = 0, max = 999999999)
    private Long price;    //가격

}
