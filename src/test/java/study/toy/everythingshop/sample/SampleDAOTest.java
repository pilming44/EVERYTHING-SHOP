package study.toy.everythingshop.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.repository.SampleDAO;

@Slf4j
@Transactional
@SpringBootTest
public class SampleDAOTest {
    @Autowired
    SampleDAO sampleDAO;

    @Test
    void 테스트명은한글도가능합니다() {
        /**
         * 다양한 검증을 진행해야됩니다.
         */
    }
}
