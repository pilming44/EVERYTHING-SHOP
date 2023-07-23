package study.toy.everythingshop.service;

import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.mariaDB.User;

import java.util.List;
import java.util.Map;

/**
 * fileName : MyPageService
 * author   : pilming
 * date     : 2023-03-29
 */
public interface MyPageService {

    void editUserInfo(User user);

    Map<String, Object> findMyOrderList(ProductSearchDTO productSearchDTO);

    UserInfoDTO findMyPageInfo(String userId);

    void addSellerApply(int userNum);

    Map<String, Object> findSellerApplyList(SellerApplyDTO sellerApplyDTO);

    int findApplyCount(int userNum);

    List<DiscountPolicyDTO> findDiscountPolicy();

    void editDiscountPolicy(List<DiscountPolicyDTO> discountPolicyDTO);

    Map<String, Object> findPointHistory(PointHistoryDTO pointHistoryDTO);

    Map<String, Object> findSalesSummary(SalesSummaryDTO param);

    Map<String, Object>  selectAllUserInfo(UserSearchDTO userSearchDTO);

    int editSellerApply(SellerApplyDTO sellerApplyDTO);

    Map<String, Object> updateOrderStatus(OrderStatusDTO orderStatusDTO, User user);
}
