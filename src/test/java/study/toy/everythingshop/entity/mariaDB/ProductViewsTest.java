package study.toy.everythingshop.entity.mariaDB;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductViewsTest {

    @DisplayName("조회수 증가")
    @Test
    void 조회수_증가() {
        ProductViews productViews = new ProductViews();
        productViews.setViews(0);

        productViews.increaseView();

        assertThat(productViews.getViews()).isEqualTo(1);
    }
}