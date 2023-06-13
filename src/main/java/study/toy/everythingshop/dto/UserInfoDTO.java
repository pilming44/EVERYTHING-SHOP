package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private Integer userNum;        //사용자번호
    private String gradeCd;         //등급코드[COM1003]
    private String gradeNm;         //등급명
    private Integer holdingPoint;   //보유포인트
    private Integer usedPoint;      //사용포인트

    private String registerDt;
    private String changeDt;


}
