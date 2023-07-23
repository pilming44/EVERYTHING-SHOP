package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {
    //maria DB용 필드 시작
    private Integer orderNum;           //주문번호
    private String orderStatus;         //주문상태
    private String orderStatusCd;      //주문상태코드
    private Integer userNum;            //유저넘버
    private Integer totalPayment;        //누적 구매 수량
}
