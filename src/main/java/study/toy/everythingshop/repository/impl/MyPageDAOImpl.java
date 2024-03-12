package study.toy.everythingshop.repository.impl;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.mariaDB.DiscountPolicy;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.MyPageDAO;

import java.util.List;

/**
 * fileName : MyPageDAOImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Repository
@RequiredArgsConstructor
@Trace
public class MyPageDAOImpl implements MyPageDAO {
    private final SqlSession sqlSession;

    @Override
    public int insertSellerApply(int userNum) {
        return sqlSession.insert("maria.MyPageDAO.insertSellerApply", userNum);
    }

    @Override
    public List<SellerApplyDTO> selectSellerApply(SellerApplyDTO sellerApplyDTO) {
        return sqlSession.selectList("maria.MyPageDAO.selectSellerApply", sellerApplyDTO);
    }

    @Override
    public int selectSellerApplyTotalCount(int userNum) {
        return sqlSession.selectOne("maria.MyPageDAO.selectSellerApplyTotalCount", userNum);
    }

    @Override
    public int selectApplyCount(int userNum) {
        return sqlSession.selectOne("maria.MyPageDAO.selectApplyCount", userNum);
    }

    @Override
    public List<DiscountPolicyDTO> selectDiscountPolicy() {
        return sqlSession.selectList("maria.MyPageDAO.selectDiscountPolicy");
    }

    @Override
    public int updateDiscountPolicyEndDate(DiscountPolicy discountPolicy) {
        return sqlSession.update("maria.MyPageDAO.updateDiscountPolicyEndDate", discountPolicy);
    }

    @Override
    public int insertDiscountPolicy(DiscountPolicy discountPolicy) {
        return sqlSession.insert("maria.MyPageDAO.insertDiscountPolicy", discountPolicy);
    }

    @Override
    public List<PointHistoryDTO> selectPointHistory(PointHistoryDTO pointHistoryDTO) {
        return sqlSession.selectList("maria.MyPageDAO.selectPointHistory", pointHistoryDTO);
    }

    @Override
    public int selectPointHistoryTotalCount(PointHistoryDTO pointHistoryDTO) {
        return sqlSession.selectOne("maria.MyPageDAO.selectPointHistoryTotalCount", pointHistoryDTO);
    }

    @Override
    public List<SalesSummaryDTO> selectSalesSummary(SalesSummaryDTO salesSummaryDTO) {
        return sqlSession.selectList("maria.MyPageDAO.selectSalesSummary", salesSummaryDTO);
    }

    @Override
    public int selectSalesSummaryTotalCount(SalesSummaryDTO salesSummaryDTO) {
        return sqlSession.selectOne("maria.MyPageDAO.selectSalesSummaryTotalCount", salesSummaryDTO);
    }

    @Override
    public int selectTotalSalesPrice() {
        return sqlSession.selectOne("maria.MyPageDAO.selectTotalSalesPrice");
    }

    @Override
    public List<UserInfoDTO> selectAllUserInfo(UserSearchDTO userSearchDTO) {
        return sqlSession.selectList("maria.MyPageDAO.selectAllUserInfo",userSearchDTO);
    }

    @Override
    public int selectAllUserInfoTotalCount(UserSearchDTO userSearchDTO) {
        return sqlSession.selectOne("maria.MyPageDAO.selectAllUserInfoTotalCount", userSearchDTO);
    }

    @Override
    public int updateSellerApply(SellerApplyDTO sellerApplyDTO) {
        return sqlSession.update("maria.MyPageDAO.updateSellerApply", sellerApplyDTO);
    }

    @Override
    public int updateOrderStatus(OrderStatusDTO orderStatusDTO) {
        return sqlSession.update("maria.MyPageDAO.updateOrderStatus", orderStatusDTO);
    }

    @Override
    public Integer selectMyTotalPayment(OrderStatusDTO orderStatusDTO) {
        return sqlSession.selectOne("maria.MyPageDAO.selectMyTotalPayment", orderStatusDTO);
    }

    @Override
    public DiscountPolicyDTO selectCorrectGrade(Integer totalPayment) {
        return sqlSession.selectOne("maria.MyPageDAO.selectCorrectGrade", totalPayment);
    }

    @Override
    public int updateUserGrade(User user) {
        return sqlSession.update("maria.MyPageDAO.updateUserGrade", user);
    }

    @Override
    public OrderStatusDTO selectOrderDetail(OrderStatusDTO orderStatusDTO) {
        return sqlSession.selectOne("maria.MyPageDAO.selectOrderDetail", orderStatusDTO);
    }


}
