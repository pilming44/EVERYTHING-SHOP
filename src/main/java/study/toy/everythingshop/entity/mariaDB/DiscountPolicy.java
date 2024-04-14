package study.toy.everythingshop.entity.mariaDB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.toy.everythingshop.enums.UserGradeCd;

@Data
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPolicy {
    private String gradeCd;          //등급코드[COM1003]
    private Integer discountRate;    //할인율(%)
    private String startDate;       //적용시작일자
    private String endDate;         //적용종료일자
    private String registerDt;      //등록일자
    private String changeDt;        //수정일자
    private Integer gradeStandard;   //등급기준

    public DiscountPolicy(UserGradeCd gradeCd, Integer discountRate, Integer gradeStandard) {
        this.gradeCd = gradeCd.getCodeCd();
        this.discountRate = discountRate;
        this.gradeStandard = gradeStandard;
    }


    public boolean needToRenewPolicy(DiscountPolicy newPolicy){
        boolean result = false;
        if(this.gradeCd.equals(newPolicy.getGradeCd())){
            if(!this.discountRate.equals(newPolicy.getDiscountRate()) || !this.gradeStandard.equals(newPolicy.getGradeStandard())){
                result = true;
            }
        }
        return result;
    }

}
