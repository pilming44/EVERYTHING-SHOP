package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder //상속받는 클래스가 있으므로 SuperBuilder 사용
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryDTO extends SuperClass {
    private Integer seq;            //순번
    private Integer userNum;        //사용자번호
    private String pointChangeCd;   //표인트변동코드[COM1006]
    private String pointChangeNm;   //표인트변동명[COM1006]
    private Integer addPoint;       //추가포인트
    private Integer deductPoint;    //차감포인트
    private Integer remainPoint;    //잔여포인트
}
