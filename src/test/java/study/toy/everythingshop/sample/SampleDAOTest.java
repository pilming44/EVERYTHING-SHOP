package study.toy.everythingshop.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.entity.h2.SampleEntity;
import study.toy.everythingshop.repository.SampleDAO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
public class SampleDAOTest {
    @Autowired
    SampleDAO sampleDAO;

    @Test
    void 테스트명은한글도가능합니다() {
        /**
         * 샘플 테스트로 간단하게 작성했습니다
         * 실제로는 더 다양한 검증을 진행해야됩니다.
         */
        //given
        SampleEntity sampleEntity1 = new SampleEntity();
        sampleEntity1.setName("최재현본체");

        SampleEntity sampleEntity2 = new SampleEntity();
        sampleEntity2.setName("최재현분신1");

        SampleEntity sampleEntity3 = new SampleEntity();
        sampleEntity3.setName("최재현분신2");

        //when
        sampleDAO.saveSample(sampleEntity1);
        sampleDAO.saveSample(sampleEntity2);
        sampleDAO.saveSample(sampleEntity3);

        //then
        List<SampleEntity> findAll = sampleDAO.findAll();
        assertThat(findAll.size()).isEqualTo(3);
    }
}
