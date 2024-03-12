package study.toy.everythingshop.entity.mariaDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;
import study.toy.everythingshop.dto.ProductOrderDTO;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class PointHistory {
    private Integer seq;            //순번
    private Integer userNum;        //사용자번호
    private String pointChangeCd;   //표인트변동코드[COM1006]
    private Integer addPoint;       //추가포인트
    private Integer deductPoint;    //차감포인트
    private String registerDt;      //등록일자
    private String changeDt;        //수정일자

    public PointHistory(Integer userNum){
        this.userNum = userNum;
    }
    public void reducePoint(Integer finalPayment){
        this.pointChangeCd = "03";
        this.deductPoint = finalPayment;
    }
}
