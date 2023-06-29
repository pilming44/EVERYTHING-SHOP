package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.CommonCodeDTO;

import java.util.List;

/**
 * fileName : CommonDAO
 * author   : pilming
 * date     : 2023-06-13
 */
public interface CommonDAO {
    /**
     * 공통코드 목록 조회
     * @param param
     * @return
     */
    List<CommonCodeDTO> selectCommonCodeList(CommonCodeDTO param);

    /**
     * 공통코드명 조회
     * @param param
     * @return
     */
    CommonCodeDTO selectCommonCodeNm(CommonCodeDTO param);
}
