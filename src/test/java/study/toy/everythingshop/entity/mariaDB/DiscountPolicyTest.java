package study.toy.everythingshop.entity.mariaDB;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountPolicyTest {

    @DisplayName("정책이 변경되지 않았습니다")
    @Test
    void 정책변경_없음() {
        String gradeCd = "01";

        DiscountPolicy old_discountPolicy = new DiscountPolicy(gradeCd,15,10000000);
        DiscountPolicy new_discountPolicy = new DiscountPolicy(gradeCd,15,10000000);

        assertThat(old_discountPolicy.needToRenewPolicy(new_discountPolicy)).isEqualTo(false);

    }

    @DisplayName("할인률이 변경되었습니다")
    @Test
    void 할인률_변경() {
        String gradeCd = "01";

        DiscountPolicy old_discountPolicy = new DiscountPolicy(gradeCd,15,10000000);
        DiscountPolicy new_discountPolicy = new DiscountPolicy(gradeCd,13,10000000);

        assertThat(old_discountPolicy.needToRenewPolicy(new_discountPolicy)).isEqualTo(true);

    }

    @DisplayName("기본지급금액이 변경되었습니다")
    @Test
    void 기본지급금액_변경() {
        String gradeCd = "01";

        DiscountPolicy old_discountPolicy = new DiscountPolicy(gradeCd,15,10000000);
        DiscountPolicy new_discountPolicy = new DiscountPolicy(gradeCd,15,50000000);

        assertThat(old_discountPolicy.needToRenewPolicy(new_discountPolicy)).isEqualTo(true);

    }
    
}