package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.mariaDB.DiscountPolicy;
import study.toy.everythingshop.entity.mariaDB.User;

import java.util.List;

/**
 * fileName : MyPageDAO
 * author   : pilming
 * date     : 2023-06-20
 */
public interface MyPageDAO {
    int insertSellerApply(int userNum);

    List<SellerApplyDTO> selectSellerApply(SellerApplyDTO sellerApplyDTO);

    int selectSellerApplyTotalCount(int userNum);

    int selectApplyCount(int userNum);

    List<DiscountPolicyDTO> selectDiscountPolicy();

    int updateDiscountPolicyEndDate(DiscountPolicy discountPolicy);

    int insertDiscountPolicy(DiscountPolicy discountPolicy);

    List<PointHistoryDTO> selectPointHistory(PointHistoryDTO pointHistoryDTO);

    int selectPointHistoryTotalCount(PointHistoryDTO pointHistoryDTO);

    List<SalesSummaryDTO> selectSalesSummary(SalesSummaryDTO salesSummaryDTO);

    int selectSalesSummaryTotalCount(SalesSummaryDTO salesSummaryDTO);

    int selectTotalSalesPrice();

    List<UserInfoDTO> selectAllUserInfo(UserSearchDTO userSearchDTO);

    int selectAllUserInfoTotalCount(UserSearchDTO userSearchDTO);

    int updateSellerApply(SellerApplyDTO sellerApplyDTO);

    int updateOrderStatus(OrderStatusDTO orderStatusDTO);

    Integer selectMyTotalPayment(OrderStatusDTO orderStatusDTO);

    Integer selectTotalPayment(Integer userNum);

    DiscountPolicyDTO selectCorrectGrade(Integer totalPayment);

    String selectCorrectGradeCd(Integer totalPayment);

    int updateUserGrade(User user);

    OrderStatusDTO selectOrderDetail(OrderStatusDTO orderStatusDTO);
}
