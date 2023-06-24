package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;
import study.toy.everythingshop.vo.BaseVO;

/**
 * fileName : ProductSearchDTO
 * author   : pilming
 * date     : 2023-03-01
 */
@Data
@SuperBuilder //상속받는 클래스가 있으므로 SuperBuilder 사용
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO extends BaseVO {
    //maria DB용 필드 시작
    @Range(min = 0, max = 999999999)
    private Integer fromPrice;         //시작가격
    @Range(min = 0, max = 999999999)
    private Integer toPrice;           //끝가격
    private Integer userNum; //등록자번호
    private String productNm;     //상품명
    //maria DB용 필드 끝

    //h2 DB용 필드 시작
//    @Range(min = 0, max = 999999999)
//    private Long fromPrice;         //시작가격
//    @Range(min = 0, max = 999999999)
//    private Long toPrice;           //끝가격
//    private Long userNum; //등록자번호
//    private String productName;     //상품명
    //h2 DB용 필드 끝
}
