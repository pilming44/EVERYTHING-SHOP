package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class SellerApplyDTO {
    private Integer applyNum;               //신청번호
    private Integer userNum;                //신청자
    private String sellerApplyStatusCd;     //판매자신청상태코드[COM1007]
    private String sellerApplyStatusNm;     //판매자신청상태코드명[COM1007]
    private String rejectReason;            //반려사유
    private String registerDt;              //등록일자
    private String changeDt;                //수정일자
}
