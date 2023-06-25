package study.toy.everythingshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder //상속받는 클래스가 있으므로 SuperBuilder 사용
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPolicyDTO extends SuperClass {
    private String gradeCd;         //등급코드[COM1003]
    private String gradeNm;         //등급명[COM1003]
    @Range(min = 0, max = 999999999)
    @NotNull(message = "{NotNull.product.registerQuantity}")
    private Integer gradeStandard;   //등급기준

    @Range(min = 0, max = 100)
    @NotNull(message = "{NotNull.product.registerQuantity}")
    private Integer discountRate;   //할인율(%)
    private String startDate;       //적용시작일자
    private String endDate;         //적용종료일자
}
