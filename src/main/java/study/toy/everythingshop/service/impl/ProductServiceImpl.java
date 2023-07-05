package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.toy.everythingshop.auth.CustomUserDetails;
import study.toy.everythingshop.dto.*;
import study.toy.everythingshop.entity.mariaDB.PointHistory;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.DiscountPolicyDAO;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.ProductService;
import study.toy.everythingshop.util.PaginationInfo;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Trace
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private final DiscountPolicyDAO discountPolicyDAO;

    @Value("${default.recordCountPerPage}")
    private int defaultRecordCountPerPage;

    @Value("${default.pageSize}")
    private int defaultPageSize;

    @Override
    public Map<String, Object> findProductList(ProductSearchDTO productSearchDTO) {
        //값 유효성 검사
        productSearchDTO.setCurrentPageNo(productSearchDTO.getCurrentPageNo() <= 0 ? 1 : productSearchDTO.getCurrentPageNo());
        productSearchDTO.setRecordCountPerPage(productSearchDTO.getRecordCountPerPage() <= 0 ? defaultRecordCountPerPage : productSearchDTO.getRecordCountPerPage());
        int pageSize = productSearchDTO.getPageSize() <= 0 ? defaultPageSize : productSearchDTO.getPageSize();

        int totalRecordCount = productDAO.selectProductListTotalCount(productSearchDTO);

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
        resultMap.put("list", productDAO.selectProductList(productSearchDTO));
        resultMap.put("paginationInfo", paginationInfo);
        return resultMap;
    }

    @Override
    public ProductDTO findProductDetail(Integer productNum, UserDetails userDetails) {
        ProductDTO product = productDAO.selectByProductNum(productNum);

        if(userDetails != null) {
            User user = userDAO.selectUserById(userDetails.getUsername());
            int discountRate = userDAO.selectUserDiscountRate(user.getUserNum());
            int discountPrice = (int) Math.round(product.getProductPrice() * (discountRate / 100.0)) ;
            discountPrice = (int) Math.ceil(discountPrice / 10.0) * 10; //1원단위 반올림
            product.setDiscountPrice(discountPrice);
        } else {
            product.setDiscountPrice(0);
        }

        return product;
    }

    @Override
    public int editProduct(ProductEditDTO productEditDTO) {
        return productDAO.updateProduct(productEditDTO);
    }

    @Override
    public int saveOrderProduct(ProductOrderDTO productOrderDTO, CustomUserDetails userDetails) {
        log.info("userDetails : {} ",userDetails);
        //주문 사용자
        ModelMapper modelMapper = new ModelMapper();
        User user = userDetails.getUser();
        productOrderDTO.setUserNum(user.getUserNum());

        int result = 0;

        int finalPaymentPrice = productOrderDTO.getFinalPaymentPrice();   //최종 결제금액
        int userHoldingPoint = user.getHoldingPoint(); //보유포인트

        //주문테이블 insert
        result += productDAO.insertOrder(productOrderDTO);

        //주문 상품 테이블 insert
        result += productDAO.insertOrderedProduct(productOrderDTO);

        //재고가 없다면 품절처리
        if(productDAO.selectRemainingQuantity(productOrderDTO) == 0) {
            result += productDAO.updateProductSoldOut(productOrderDTO);
        }
        //사용자 보유 포인트 차감
        user.setHoldingPoint(userHoldingPoint - finalPaymentPrice);
        result += userDAO.updateHoldingPoint(user);

        //포인트 이력테이블에 내역 insert
        PointHistory pointHistory = PointHistory.builder()
                .userNum(user.getUserNum())
                .pointChangeCd("03")
                .deductPoint(finalPaymentPrice)
                .build();
        result += userDAO.insertPointHistory(pointHistory);

        return result;
    }

    @Override
    public int saveNewProduct(ProductRegisterDTO productRegisterDTO, UserDetails userDetails){
        User user = userDAO.selectUserById(userDetails.getUsername());
        productRegisterDTO.setUserNum(user.getUserNum());
        return productDAO.insertProduct(productRegisterDTO);
    };

    @Override
    public ProductOrderDTO findOrderDetail(Integer productNum, CustomUserDetails userDetails){
        //product의 정보 가져오기
        ProductDTO product = productDAO.selectByProductNum(productNum);
        ModelMapper modelMapper = new ModelMapper();
        ProductOrderDTO productOrderDTO = modelMapper.map(product, ProductOrderDTO.class);
        //User의 등급으로 현재 적용된 할인율 가져오기
        Integer discountRate = discountPolicyDAO.selectDiscountRateByGrade(userDetails.getGradeCd());
        //현재 product 금액에서 할인율 적용하여 할인금액, 현재금액 구하기.
        Integer discountPrice  = product.getProductPrice() * discountRate / 100;
        Integer currentPrice = product.getProductPrice() - discountPrice;
        productOrderDTO.setDiscountPrice(discountPrice);
        productOrderDTO.setCurrentPrice(currentPrice);
        //판매수량 구해서 남은수량 구하기
        Integer remainQuantity = productOrderDTO.getRegisterQuantity() - productDAO.selectOrderedQty(productNum);
        productOrderDTO.setRemainQuantity(remainQuantity);
        return productOrderDTO;
    }
}
