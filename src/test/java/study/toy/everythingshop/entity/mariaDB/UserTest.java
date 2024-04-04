package study.toy.everythingshop.entity.mariaDB;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @DisplayName("포인트 사용 후 남은 포인트 계산")
    @Test
    void 포인트_사용() {
        int usePoint = 2000;
        int holdingPoint = 5000;
        User user = new User();
        user.setHoldingPoint(holdingPoint);

        user.usePoints(usePoint);
        assertThat(user.getHoldingPoint()).isEqualTo(holdingPoint-usePoint);
    }

    @DisplayName("등급에 변경이 있는지 확인한다")
    @Test
    void 등급_변경() {
        String gradeCd = "01";
        String new_gradeCD ="02";

        User user = new User();
        user.setGradeCd(gradeCd);

        if(!gradeCd.equals(new_gradeCD)) {
            assertThat(user.isUpdateGrade(new_gradeCD)).isEqualTo(true);
            System.out.println("새로운 등급으로 변경이 필요합니다");
        }else{
            assertThat(user.isUpdateGrade(new_gradeCD)).isEqualTo(false);
            System.out.println("등급 변동이 없었습니다.");
        }

    }

    @DisplayName("포인트로 환불금액이 더해진다")
    @Test
    void 환불_포인트() {
        int refundPrice = 3000;
        int holdingPrice = 5000;

        User user = new User();
        user.setHoldingPoint(holdingPrice);

        user.refund(refundPrice);
        assertThat(user.getHoldingPoint()).isEqualTo(holdingPrice+refundPrice);

    }
}