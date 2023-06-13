package study.toy.everythingshop.service;

import study.toy.everythingshop.dto.CommonCodeDTO;
import study.toy.everythingshop.entity.h2.SampleEntity;

import java.util.List;

public interface CommonService {

    /**
     * 공통코드 목록 조회 (정렬순서 없음)
     * @param codeClass
     * @return
     */
    List<CommonCodeDTO> selectCommonCodeList(String codeClass);

    /**
     * 공통코드 목록 조회 (정렬순서 있음)
     * @param codeClass
     * @param order
     * @return
     */
    List<CommonCodeDTO> selectCommonCodeList(String codeClass, String order);

    /**
     * 공통코드명 조회
     * @param codeClass
     * @param codeNum
     * @return
     */
    String selectCommonCodeNm(String codeClass, String codeNum);
}
