package study.toy.everythingshop.entity.mariaDB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PointHistoryTest {

   PointHistory pointHistory;
    @BeforeEach
    void setPointHistory(){
        pointHistory = new PointHistory();
    }

    @DisplayName("결제에 사용된 차감 포인트를 구한다")
    @Test
    void 차감포인트_계산() {
        int paidPoints = 1000;
        pointHistory.reducePoint(paidPoints);
        assertThat(pointHistory.getDeductPoint()).isEqualTo(paidPoints);
    }

    @DisplayName("결제 후 포인트 변동코드 변경")
    @Test
    void 포인트_변경코드() {
        int paidPoints = 1000;

        pointHistory.reducePoint(paidPoints);
        assertThat(pointHistory.getPointChangeCd()).isEqualTo("03");
    }

}