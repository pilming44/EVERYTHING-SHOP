package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.MyPageDAO;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.CommonService;
import study.toy.everythingshop.service.MyPageService;
import study.toy.everythingshop.util.PaginationInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * fileName : MyPageServiceImpl
 * author   : pilming
 * date     : 2023-03-29
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Trace
public class MyPageServiceImpl implements MyPageService {

    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final MyPageDAO myPageDAO;
    private final CommonService commonService;

    @Value("${default.recordCountPerPage}")
    private int defaultRecordCountPerPage;

    @Value("${default.pageSize}")
    private int defaultPageSize;

    @Override
    public int findApplyCount(int userNum) {
        return myPageDAO.selectApplyCount(userNum);
    }

    @Override
    public void editUserInfo(User user) {
        userDAO.updateUserInfo(user);
    }

    @Override
    public List<ProductOrderDTO> findMyOrderList(ProductSearchDTO productSearchDTO) {
       return productDAO.selectMyOrderList(productSearchDTO);
    }

    @Override
    public UserInfoDTO findMyPageInfo(String userId) {
        //사용자정보조회
        User user = userDAO.selectUserById(userId);
        //누적포인트 조회
        int usedPoint = userDAO.selectUsedPoint(userId);
        //DTO에 세팅
        UserInfoDTO myPageDTO = UserInfoDTO.builder()
                .gradeNm(commonService.selectCommonCodeNm("COM1003", user.getGradeCd()))
                .holdingPoint(user.getHoldingPoint())
                .usedPoint(usedPoint)
                .build();

        return myPageDTO;
    }

    @Override
    public void addSellerApply(int userNum) {
        myPageDAO.insertSellerApply(userNum);
    }

    @Override
    public Map<String, Object> findSellerApplyList(SellerApplyDTO sellerApplyDTO) {
        //값 유효성 검사
        sellerApplyDTO.setCurrentPageNo(sellerApplyDTO.getCurrentPageNo() <= 0 ? 1 : sellerApplyDTO.getCurrentPageNo());
        sellerApplyDTO.setRecordCountPerPage(sellerApplyDTO.getRecordCountPerPage() <= 0 ? defaultRecordCountPerPage : sellerApplyDTO.getRecordCountPerPage());
        int pageSize = sellerApplyDTO.getPageSize() <= 0 ? defaultPageSize : sellerApplyDTO.getPageSize();

        int totalRecordCount = myPageDAO.selectSellerApplyTotalCount(sellerApplyDTO.getUserNum());

        //페이징 하는데 필요한 값을 계산해주는 클래스 값 세팅
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(sellerApplyDTO.getCurrentPageNo());
        paginationInfo.setRecordCountPerPage(sellerApplyDTO.getRecordCountPerPage());
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setTotalRecordCount(totalRecordCount);

        //계산된 값 입력
        sellerApplyDTO.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        sellerApplyDTO.setLastRecordIndex(paginationInfo.getLastRecordIndex());
        sellerApplyDTO.setTotalPageCount(paginationInfo.getTotalPageCount());
        sellerApplyDTO.setTotalRecordCount(totalRecordCount);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", myPageDAO.selectSellerApply(sellerApplyDTO));
        resultMap.put("paginationInfo", paginationInfo);
        return resultMap;
    }

    @Override
    public List<DiscountPolicyDTO> findDiscountPolicy() {
        return myPageDAO.selectDiscountPolicy();
    }

    @Override
    public void editDiscountPolicy(List<DiscountPolicyDTO> discountPolicyDTO) {
        //기존 할일정책 조회
        List<DiscountPolicyDTO> oldDiscountPolicy = myPageDAO.selectDiscountPolicy();

        for(DiscountPolicyDTO newPolicy : discountPolicyDTO) {
            for(DiscountPolicyDTO oldPolicy : oldDiscountPolicy) {
                if(newPolicy.getGradeCd().equals(oldPolicy.getGradeCd())
                        && (!newPolicy.getGradeStandard().equals(oldPolicy.getGradeStandard()) || !newPolicy.getDiscountRate().equals(oldPolicy.getDiscountRate()))) {
                    //기존 정책과 다른부분이 있다면 해당 정책 갱신
                    myPageDAO.updateDiscountPolicyEndDate(oldPolicy); //기존 정책 종료일자 설정
                    myPageDAO.insertDiscountPolicy(newPolicy);  //새로운 정책정보 등록
                }
            }
        }
    }

    @Override
    public Map<String, Object> findPointHistory(PointHistoryDTO pointHistoryDTO) {
        //값 유효성 검사
        pointHistoryDTO.setCurrentPageNo(pointHistoryDTO.getCurrentPageNo() <= 0 ? 1 : pointHistoryDTO.getCurrentPageNo());
        pointHistoryDTO.setRecordCountPerPage(pointHistoryDTO.getRecordCountPerPage() <= 0 ? defaultRecordCountPerPage : pointHistoryDTO.getRecordCountPerPage());
        int pageSize = pointHistoryDTO.getPageSize() <= 0 ? defaultPageSize : pointHistoryDTO.getPageSize();

        int totalRecordCount = myPageDAO.selectPointHistoryTotalCount(pointHistoryDTO.getUserNum());

        //페이징 하는데 필요한 값을 계산해주는 클래스 값 세팅
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(pointHistoryDTO.getCurrentPageNo());
        paginationInfo.setRecordCountPerPage(pointHistoryDTO.getRecordCountPerPage());
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setTotalRecordCount(totalRecordCount);

        //계산된 값 입력
        pointHistoryDTO.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        pointHistoryDTO.setLastRecordIndex(paginationInfo.getLastRecordIndex());
        pointHistoryDTO.setTotalPageCount(paginationInfo.getTotalPageCount());
        pointHistoryDTO.setTotalRecordCount(totalRecordCount);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", myPageDAO.selectPointHistory(pointHistoryDTO));
        resultMap.put("paginationInfo", paginationInfo);

        return resultMap;
    }
}
