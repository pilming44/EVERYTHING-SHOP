package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.CommonCodeDTO;
import study.toy.everythingshop.repository.CommonDAO;
import study.toy.everythingshop.service.CommonService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonDAO commonDAO;

    @Override
    public List<CommonCodeDTO> selectCommonCodeList(String codeClass, String order) {
        //order매개변수 유효성검사
        if (order != null) {
            order = order.trim().toUpperCase(); //공백제거 후 대문자로
            if (!order.equals("ASC") && !order.equals("DESC")) {
                order = "ASC";
            }
        } else {
            order = "ASC";  //null인경우 기본값으로 ASC
        }
        //매개변수 객체
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.builder().codeClass(codeClass).order(order).build();

        return commonDAO.selectCommonCodeList(commonCodeDTO);
    }

    @Override
    public List<CommonCodeDTO> selectCommonCodeList(String codeClass) {
        //매개변수 객체
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.builder().codeClass(codeClass).build();

        return commonDAO.selectCommonCodeList(commonCodeDTO);
    }

    @Override
    public String selectCommonCodeNm(String codeClass, String codeNum) {
        CommonCodeDTO commonCodeDTO = CommonCodeDTO.builder().codeClass(codeClass).codeNum(codeNum).build();
        return commonDAO.selectCommonCodeNm(commonCodeDTO).getCodeNm();
    }
}
