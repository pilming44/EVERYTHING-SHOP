package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.mariaDB.DiscountPolicy;
import study.toy.everythingshop.entity.mariaDB.PointHistory;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.enums.CommonCodeClassEnum;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.MyPageDAO;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.CommonService;
import study.toy.everythingshop.service.MyPageService;
import study.toy.everythingshop.util.CommonUtil;
import study.toy.everythingshop.util.PaginationHelper;
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
        productSearchDTO.setTotalRecordCount(productDAO.selectMyOrderListTotalCount(productSearchDTO));

        PaginationInfo paginationInfo = PaginationHelper.configurePagination(productSearchDTO);

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
                .gradeNm(commonService.selectCommonCodeNm(CommonCodeClassEnum.GRADE, user.getGradeCd()))
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
        sellerApplyDTO.setTotalRecordCount(myPageDAO.selectSellerApplyTotalCount(sellerApplyDTO.getUserNum()));

        PaginationInfo paginationInfo = PaginationHelper.configurePagination(sellerApplyDTO);

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
    public void editDiscountPolicy(List<DiscountPolicyDTO> discountPolicyList){
        //기존 할인정책 조회
        List<DiscountPolicyDTO> oldDiscountPolicyList = myPageDAO.selectDiscountPolicy();

        for(DiscountPolicyDTO newPolicyDTO : discountPolicyList) {
            for(DiscountPolicyDTO oldPolicyDTO : oldDiscountPolicyList) {
                checkNUpdatePolicy(newPolicyDTO,oldPolicyDTO);
            }
        }
    }

    private void checkNUpdatePolicy(DiscountPolicyDTO newPolicyDTO, DiscountPolicyDTO oldPolicyDTO){
       //DTO to Entity
        ModelMapper modelMapper = new ModelMapper();
        DiscountPolicy newPolicy = modelMapper.map(newPolicyDTO, DiscountPolicy.class);
        DiscountPolicy oldPolicy = modelMapper.map(oldPolicyDTO, DiscountPolicy.class);

        //기존정책과 다른부분이 있다면 해당정책 갱신
        if(oldPolicy.needToRenewPolicy(newPolicy)) {
            myPageDAO.updateDiscountPolicyEndDate(oldPolicy); //기존 정책 종료일자 설정
            myPageDAO.insertDiscountPolicy(newPolicy);  //새로운 정책정보 등록
        }
    }

    @Override
    public Map<String, Object> findPointHistory(PointHistoryDTO pointHistoryDTO) {
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
        pointHistoryDTO.setTotalRecordCount(myPageDAO.selectPointHistoryTotalCount(pointHistoryDTO));

        PaginationInfo paginationInfo = PaginationHelper.configurePagination(pointHistoryDTO);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", myPageDAO.selectPointHistory(pointHistoryDTO));
        resultMap.put("paginationInfo", paginationInfo);

        return resultMap;
    }

    @Override
    public Map<String, Object> findSalesSummary(SalesSummaryDTO param) {
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
        param.setTotalRecordCount(myPageDAO.selectSalesSummaryTotalCount(param));

        PaginationInfo paginationInfo = PaginationHelper.configurePagination(param);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", myPageDAO.selectSalesSummary(param));
        resultMap.put("paginationInfo", paginationInfo);

        return resultMap;
    }

    @Override
    public  Map<String, Object> selectAllUserInfo(UserSearchDTO userSearchDTO) {
        userSearchDTO.setTotalRecordCount(myPageDAO.selectAllUserInfoTotalCount(userSearchDTO));

        PaginationInfo paginationInfo = PaginationHelper.configurePagination(userSearchDTO);

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
    public Map<String, Object> oldUpdateOrderStatus(OrderStatusDTO orderStatusDTO, User user) {
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

    @Override
    public void updateOrderStatus(OrderStatusDTO orderStatusDTO, User user) {
        myPageDAO.updateOrderStatus(orderStatusDTO);
        if(orderStatusDTO.getOrderStatusCd().equals("02")){
            //주문취소시
            withdrawOrder(orderStatusDTO, user);
        }
    }

    @Override
    public boolean isUpdateGrade(User user) {
        Integer totalPayment = myPageDAO.selectTotalPayment(user.getUserNum());
        String newGrade = myPageDAO.selectCorrectGradeCd(totalPayment);
        if (user.isUpdateGrade(newGrade)) {
            myPageDAO.updateUserGrade(user);
            return true;
        }
        return false;
    }

    private void withdrawOrder(OrderStatusDTO orderStatusDTO, User user) {
        //주문디테일 구하기
        OrderStatusDTO orderStatusDTO1 = myPageDAO.selectOrderDetail(orderStatusDTO);
        //포인트 환불
        user.refund(orderStatusDTO1.getFinalPaymentPrice());

        userDAO.updateHoldingPoint(user);

        //포인트 이력테이블에 내역 insert
        PointHistory pointHistory = PointHistory.builder()
                .userNum(user.getUserNum())
                .pointChangeCd("04")
                .addPoint(orderStatusDTO1.getFinalPaymentPrice())
                .build();
        userDAO.insertPointHistory(pointHistory);
    }
}
