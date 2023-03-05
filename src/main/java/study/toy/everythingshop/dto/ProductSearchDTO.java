package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long fromPrice;         //시작가격
    private Long toPrice;           //끝가격
}
