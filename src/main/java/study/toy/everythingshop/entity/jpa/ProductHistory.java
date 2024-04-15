package study.toy.everythingshop.entity.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistory {
    private Integer seq;                //순번
    private Integer productNum;         //상품번호
    private Integer userNum;            //사용자번호(판매자)
    private String productNm;           //상품명
    private Integer registerQuantity;   //등록수량
    private Integer productPrice;       //상품가격
    private String productStatusCd;     //상품강태코드[COM1004]
    private String postYn;              //게시여부 Y,N
    private String registerDt;          //등록일자
    private String changeDt;            //수정일자
}
