package study.toy.everythingshop.entity.mariaDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProduct {
    private Integer orderNum;           //주문번호
    private Integer productNum;         //상품번호
    private Integer productPrice;       //상품가격
    private Integer orderQuantity;      //주문수량
    private Integer discountPrice;      //할인금액
    private Integer finalPaymentPrice;  //최종결제금액
    private String registerDt;          //등록일자
    private String changeDt;            //수정일자

}
