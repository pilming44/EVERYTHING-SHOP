package study.toy.everythingshop.entity.mariaDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class PointHistory {
    private Integer seq;            //순번
    private Integer userNum;        //사용자번호
    private String pointChangeCd;   //표인트변동코드
    private Integer addPoint;       //추가포인트
    private Integer deductPoint;    //차감포인트
    private String registerDt;      //등록일자
    private String changeDt;        //수정일자
}
