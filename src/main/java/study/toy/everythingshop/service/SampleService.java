package study.toy.everythingshop.service;

import study.toy.everythingshop.entity.h2.SampleEntity;

import java.util.List;

public interface SampleService {
    List<SampleEntity> findUsers();
}
