package study.toy.everythingshop.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.Alias;
import study.toy.everythingshop.entity.SampleEntity;

import java.util.List;

public interface SampleDAO {

    List<SampleEntity> findAll();

    int saveSample(SampleEntity sampleEntity);
}
