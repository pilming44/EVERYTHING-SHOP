package study.toy.everythingshop.repository;

import study.toy.everythingshop.dto.DiscountPolicyDTO;
import study.toy.everythingshop.dto.PointHistoryDTO;
import study.toy.everythingshop.dto.SellerApplyDTO;

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

    int updateDiscountPolicyEndDate(DiscountPolicyDTO discountPolicyDTO);

    int insertDiscountPolicy(DiscountPolicyDTO discountPolicyDTO);

    List<PointHistoryDTO> selectPointHistory(PointHistoryDTO pointHistoryDTO);

    int selectPointHistoryTotalCount(int userNum);
}
