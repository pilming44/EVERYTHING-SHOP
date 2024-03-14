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


    public Integer getDiscountPrice (Integer productPrice,Integer discountRate){
        return productPrice * discountRate / 100;
    }

    //현재 product 금액에서 할인금액을 적용해 현재금액 구하기.
    public Integer getCurrentPrice (Integer productPrice,Integer discountRate){
        Integer discountPrice = getDiscountPrice(productPrice,discountRate);
        return productPrice - discountPrice;
    }

    //판매수량 구해서 남은수량 구하기
    public Integer checkInventoryStatus(Integer registerQuantity,Integer orderQuantity){
        return registerQuantity - orderQuantity;
    }


}
