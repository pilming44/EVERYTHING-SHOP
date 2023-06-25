package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder //상속받는 클래스가 있으므로 SuperBuilder 사용
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends SuperClass {
    private Integer productNum;         //상품번호
    private Integer userNum;            //사용자번호(판매자)
    private String productNm;           //상품명
    private Integer registerQuantity;   //등록수량
    private Integer remainQuantity;     //잔여수량
    private Integer productPrice;       //상품가격
    private String productStatusCd;     //판매상태코드[COM1004]
    private String productStatusNm;     //판매상태명[COM1004]
    private String postYn;              //게시여부(Y, N)
    private Integer discountPrice;      //할인금액
    private Integer views;              //조회수
}
