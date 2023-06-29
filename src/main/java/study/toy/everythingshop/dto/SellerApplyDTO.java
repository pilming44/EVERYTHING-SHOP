package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder //상속받는 클래스가 있으므로 SuperBuilder 사용
@NoArgsConstructor
@AllArgsConstructor
public class SellerApplyDTO extends SuperClass {
    private Integer applyNum;               //신청번호
    private Integer userNum;                //신청자
    private String sellerApplyStatusCd;     //판매자신청상태코드[COM1007]
    private String sellerApplyStatusNm;     //판매자신청상태코드명[COM1007]
    private String rejectReason;            //반려사유
}
