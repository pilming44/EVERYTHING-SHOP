package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor

@AllArgsConstructor
public class UserInfoDTO  {

    private Integer userNum;        //사용자번호
    private String userNm;          //사용자이름
    private String userId;          //사용자 id
    private String gradeCd;         //등급코드[COM1003]
    private String gradeNm;         //등급명
    private Integer holdingPoint;   //보유포인트
    private Integer usedPoint;      //사용포인트
    private Integer totalPayment;    //누적금액
    private String roleNm;          //권한
    private String roleCd;         //권한코드
    private Integer applyNum;       //판매자 신청 넘버

    private String registerDt;
    private String changeDt;


}
