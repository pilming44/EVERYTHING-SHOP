package study.toy.everythingshop.repository;
/**
 * fileName : DiscountPolicyDAO
 * author   : annie
 * date     : 2023-06-07
 */
public interface DiscountPolicyDAO {
    Integer selectDiscountRateByGrade(String gradeCd);


}
