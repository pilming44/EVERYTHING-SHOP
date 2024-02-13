package study.toy.everythingshop.service;

import study.toy.everythingshop.dto.CommonCodeDTO;
import study.toy.everythingshop.enums.CommonCodeClassEnum;

import java.util.List;

public interface CommonService {

    /**
     * 공통코드 목록 조회 (정렬순서 없음)
     * @param codeClass
     * @return
     */
    List<CommonCodeDTO> selectCommonCodeList(CommonCodeClassEnum codeClass);

    /**
     * 공통코드명 조회
     * @param codeClass
     * @param codeNum
     * @return
     */
    String selectCommonCodeNm(CommonCodeClassEnum codeClass, String codeNum);
}
