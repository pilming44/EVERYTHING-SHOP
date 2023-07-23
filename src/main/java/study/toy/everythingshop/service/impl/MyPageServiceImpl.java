package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.mariaDB.PointHistory;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.MyPageDAO;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.CommonService;
import study.toy.everythingshop.service.MyPageService;
import study.toy.everythingshop.util.CommonUtil;
import study.toy.everythingshop.util.PaginationInfo;

import java.time.LocalDate;
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
    public Map<String, Object> findMyOrderList(ProductSearchDTO productSearchDTO) {

        productSearchDTO.setCurrentPageNo(productSearchDTO.getCurrentPageNo() <= 0 ? 1 : productSearchDTO.getCurrentPageNo());
        productSearchDTO.setRecordCountPerPage(productSearchDTO.getRecordCountPerPage() <= 0 ? defaultRecordCountPerPage : productSearchDTO.getRecordCountPerPage());
        int pageSize = productSearchDTO.getPageSize() <= 0 ? defaultPageSize : productSearchDTO.getPageSize();

        int totalRecordCount = productDAO.selectMyOrderListTotalCount(productSearchDTO);

        //페이징 하는데 필요한 값을 계산해주는 클래스 값 세팅
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(productSearchDTO.getCurrentPageNo());
        paginationInfo.setRecordCountPerPage(productSearchDTO.getRecordCountPerPage());
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setTotalRecordCount(totalRecordCount);

        //계산된 값 입력
        productSearchDTO.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        productSearchDTO.setLastRecordIndex(paginationInfo.getLastRecordIndex());
        productSearchDTO.setTotalPageCount(paginationInfo.getTotalPageCount());
        productSearchDTO.setTotalRecordCount(totalRecordCount);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", productDAO.selectMyOrderList(productSearchDTO));
        resultMap.put("paginationInfo", paginationInfo);
        return resultMap;
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

        //날짜 유효성 검사
        if(CommonUtil.strIsNotEmpty(pointHistoryDTO.getFromDate()) && CommonUtil.strIsNotEmpty(pointHistoryDTO.getEndDate())) {
            LocalDate fromLocalDate = LocalDate.parse(pointHistoryDTO.getFromDate());
            LocalDate endLocalDate = LocalDate.parse(pointHistoryDTO.getEndDate());
            //종료날짜가 시작날짜보다 빠르다면 두 값 스왑
            if (endLocalDate.isBefore(fromLocalDate)) {
                String temp = pointHistoryDTO.getFromDate();
                pointHistoryDTO.setFromDate(pointHistoryDTO.getEndDate());
                pointHistoryDTO.setEndDate(temp);
            }
        }

        int totalRecordCount = myPageDAO.selectPointHistoryTotalCount(pointHistoryDTO);

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

    @Override
    public Map<String, Object> findSalesSummary(SalesSummaryDTO param) {
        //값 유효성 검사
        param.setCurrentPageNo(param.getCurrentPageNo() <= 0 ? 1 : param.getCurrentPageNo());
        param.setRecordCountPerPage(param.getRecordCountPerPage() <= 0 ? defaultRecordCountPerPage : param.getRecordCountPerPage());
        int pageSize = param.getPageSize() <= 0 ? defaultPageSize : param.getPageSize();

        //날짜 유효성 검사
        if(CommonUtil.strIsNotEmpty(param.getFromDate()) && CommonUtil.strIsNotEmpty(param.getEndDate())) {
            LocalDate fromLocalDate = LocalDate.parse(param.getFromDate());
            LocalDate endLocalDate = LocalDate.parse(param.getEndDate());
            //종료날짜가 시작날짜보다 빠르다면 두 값 스왑
            if (endLocalDate.isBefore(fromLocalDate)) {
                String temp = param.getFromDate();
                param.setFromDate(param.getEndDate());
                param.setEndDate(temp);
            }
        }

        int totalRecordCount = myPageDAO.selectSalesSummaryTotalCount(param);

        //페이징 하는데 필요한 값을 계산해주는 클래스 값 세팅
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(param.getCurrentPageNo());
        paginationInfo.setRecordCountPerPage(param.getRecordCountPerPage());
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setTotalRecordCount(totalRecordCount);

        //계산된 값 입력
        param.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        param.setLastRecordIndex(paginationInfo.getLastRecordIndex());
        param.setTotalPageCount(paginationInfo.getTotalPageCount());
        param.setTotalRecordCount(totalRecordCount);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", myPageDAO.selectSalesSummary(param));
        resultMap.put("paginationInfo", paginationInfo);

        return resultMap;
    }

    @Override
    public  Map<String, Object> selectAllUserInfo(UserSearchDTO userSearchDTO) {
        //값 유효성 검사
        userSearchDTO.setCurrentPageNo(userSearchDTO.getCurrentPageNo() <= 0 ? 1 : userSearchDTO.getCurrentPageNo());
        userSearchDTO.setRecordCountPerPage(userSearchDTO.getRecordCountPerPage() <= 0 ? defaultRecordCountPerPage : userSearchDTO.getRecordCountPerPage());
        int totalUserCount = myPageDAO.selectAllUserInfoTotalCount(userSearchDTO);
        int pageSize = userSearchDTO.getPageSize() <= 0 ? defaultPageSize : userSearchDTO.getPageSize();


        //페이징 하는데 필요한 값을 계산해주는 클래스 값 세팅
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(userSearchDTO.getCurrentPageNo());
        paginationInfo.setRecordCountPerPage(userSearchDTO.getRecordCountPerPage());
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setTotalRecordCount(totalUserCount);

        //계산된 값 입력
        userSearchDTO.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        userSearchDTO.setLastRecordIndex(paginationInfo.getLastRecordIndex());
        userSearchDTO.setTotalPageCount(paginationInfo.getTotalPageCount());
        userSearchDTO.setTotalRecordCount(totalUserCount);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("userList", myPageDAO.selectAllUserInfo(userSearchDTO));
        resultMap.put("paginationInfo", paginationInfo);
        return resultMap;
    }

    @Override
    public int editSellerApply(SellerApplyDTO sellerApplyDTO) {
        int result =  myPageDAO.updateSellerApply(sellerApplyDTO);
        //get User Info
        if(sellerApplyDTO.getSellerApplyStatusCd().equals("02")){
            result += userDAO.updateUserRoleCd(sellerApplyDTO.getUserNum());
        }
        return result;
    }

    @Override
    public Map<String, Object> updateOrderStatus(OrderStatusDTO orderStatusDTO, User user) {
        orderStatusDTO.setUserNum(user.getUserNum());
        int result =  myPageDAO.updateOrderStatus(orderStatusDTO);
        int update = 0;

        //구매확정 후, 누적금액 check 하여, 등급update
        if(orderStatusDTO.getOrderStatusCd().equals("03")){
            //1.누적금액 check
            Integer totalPayment = myPageDAO.selectMyTotalPayment(orderStatusDTO);
            orderStatusDTO.setTotalPayment(totalPayment);
            // 2. 등급 확인 (등급 정책을 사용하여 새로운 등급 확인)
            String currentGrade = user.getGradeCd();
            DiscountPolicyDTO newGrade = myPageDAO.selectCorrectGrade(totalPayment); // 누적금액에 맞는 등급인지 확인.
            // 3. 새로운 등급 확인
            if (!currentGrade.equals(newGrade.getGradeCd())) {
                user.setGradeCd(newGrade.getGradeCd());
                // 4. 등급 업데이트
                update = myPageDAO.updateUserGrade(user);
            }
            //주문취소시
        }else if(orderStatusDTO.getOrderStatusCd().equals("02")){
            //주문디테일 구하기
            OrderStatusDTO orderStatusDTO1 = myPageDAO.selectOrderDetail(orderStatusDTO);
            //포인트 복구
            user.setHoldingPoint(user.getHoldingPoint() + orderStatusDTO1.getFinalPaymentPrice());
            result += userDAO.updateHoldingPoint(user);

            //포인트 이력테이블에 내역 insert
            PointHistory pointHistory = PointHistory.builder()
                    .userNum(user.getUserNum())
                    .pointChangeCd("04")
                    .addPoint(orderStatusDTO1.getFinalPaymentPrice())
                    .build();
            result += userDAO.insertPointHistory(pointHistory);

        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);
        resultMap.put("update", update);
        return resultMap;
    }


}
