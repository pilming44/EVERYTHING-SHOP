package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * fileName : ProductSearchDTO
 * author   : pilming
 * date     : 2023-03-01
 */
@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO {
    private String productName;     //상품명

    @Range(min = 0, max = 999999999)
    private Long fromPrice;         //시작가격

    @Range(min = 0, max = 999999999)
    private Long toPrice;           //끝가격

    private Long userNum; //등록자번호
}
