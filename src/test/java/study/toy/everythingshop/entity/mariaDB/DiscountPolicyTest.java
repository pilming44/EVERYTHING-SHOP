package study.toy.everythingshop.entity.mariaDB;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountPolicyTest {

    @DisplayName("정책에 변경이 있는지 확인한다")
    @Test
    void 정책_변경() {

        String gradeCd = "01";

        DiscountPolicy old_discountPolicy = new DiscountPolicy();
        old_discountPolicy.setGradeCd(gradeCd);
        old_discountPolicy.setDiscountRate(15);
        old_discountPolicy.setGradeStandard(10000000);

        DiscountPolicy new_discountPolicy = new DiscountPolicy();
        new_discountPolicy.setGradeCd(gradeCd);
        new_discountPolicy.setDiscountRate(12);
        new_discountPolicy.setGradeStandard(50000000);

        if(old_discountPolicy.getGradeCd().equals(new_discountPolicy.getGradeCd())){
            if(!old_discountPolicy.getDiscountRate().equals(new_discountPolicy.getDiscountRate())){
                assertThat(old_discountPolicy.needToRenewPolicy(new_discountPolicy)).isEqualTo(true);
                System.out.println("할인률에 변경이 있습니다");
            }
            if(!old_discountPolicy.getGradeStandard().equals(new_discountPolicy.getGradeStandard())){
                assertThat(old_discountPolicy.needToRenewPolicy(new_discountPolicy)).isEqualTo(true);
                System.out.println("기본지급금액에 변경이 있습니다");
            }
        }
    }
}