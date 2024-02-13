package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.CommonCodeDTO;
import study.toy.everythingshop.enums.CommonCodeClassEnum;
import study.toy.everythingshop.repository.CommonDAO;
import study.toy.everythingshop.service.CommonService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonDAO commonDAO;

    @Override
    public List<CommonCodeDTO> selectCommonCodeList(CommonCodeClassEnum codeClass) {
        //매개변수 객체
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.builder().codeClass(codeClass.name()).build();

        return commonDAO.selectCommonCodeList(commonCodeDTO);
    }

    @Override
    public String selectCommonCodeNm(CommonCodeClassEnum codeClass, String codeNum) {
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.builder().codeClass(codeClass.name()).codeNum(codeNum).build();
        return commonDAO.selectCommonCodeNm(commonCodeDTO).getCodeNm();
    }
}
