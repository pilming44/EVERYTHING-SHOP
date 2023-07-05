package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder //상속받는 클래스가 있으므로 SuperBuilder 사용
@NoArgsConstructor
@AllArgsConstructor
public class SalesSummaryDTO extends SuperClass {
    private Integer productNum;         //상품번호
    private String productNm;           //상품명
    private Integer registerQuantity;   //등록수량
    private Integer remainQuantity;     //남은수량
    private Integer orderQuantity;      //판매수량
    private String productStatusCd;     //상품상태코드
    private String productStatusNm;     //상품상태명
    private Double salesRate;           //판매율
    private Integer sumPrice;           //누적판매금액

    private String fromDate;        //시작일자
    private String endDate;         //종료일자
}
