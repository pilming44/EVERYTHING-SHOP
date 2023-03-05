package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.entity.SampleEntity;
import study.toy.everythingshop.repository.SampleDAO;
import study.toy.everythingshop.service.SampleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {

    private final SampleDAO sampleDAO;

    @Override
    public List<SampleEntity> findUsers() {
        return sampleDAO.findAll();
    }
}
