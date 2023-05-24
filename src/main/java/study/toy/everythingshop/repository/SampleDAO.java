package study.toy.everythingshop.repository;

import study.toy.everythingshop.entity.h2.SampleEntity;

import java.util.List;

public interface SampleDAO {

    List<SampleEntity> findAll();

    int saveSample(SampleEntity sampleEntity);
}
