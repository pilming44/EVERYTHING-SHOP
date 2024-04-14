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
import study.toy.everythingshop.entity.mariaDB.Product;
import study.toy.everythingshop.entity.mariaDB.User;
import study.toy.everythingshop.enums.CommonCodeClassEnum;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.DiscountPolicyDAO;
import study.toy.everythingshop.repository.ProductDAO;
import study.toy.everythingshop.repository.UserDAO;
import study.toy.everythingshop.service.CommonService;
import study.toy.everythingshop.service.ProductService;
import study.toy.everythingshop.util.PaginationHelper;
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
    private final CommonService commonService;

    @Value("${default.recordCountPerPage}")
    private int defaultRecordCountPerPage;

    @Value("${default.pageSize}")
    private int defaultPageSize;

    @Override
    public Map<String, Object> findProductList(ProductSearchDTO productSearchDTO) {
        productSearchDTO.setTotalRecordCount(productDAO.selectProductListTotalCount(productSearchDTO));

        PaginationInfo paginationInfo = PaginationHelper.configurePagination(productSearchDTO);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", productDAO.selectProductList(productSearchDTO));
        resultMap.put("paginationInfo", paginationInfo);
        return resultMap;
    }

    @Override
    public ProductDTO oldFindProductDetail(Integer productNum, boolean firstView, UserDetails userDetails) {
        //쿠키 기반 최초 조회라면 조회수 증가
        if(firstView) {
            productDAO.updateProductViewCount(productNum);
        }

        //상품조회
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
    public ProductDTO findProductDetail(Integer productNum, boolean firstView, UserDetails userDetails) {
        //상품조회
        Product product = productDAO.selectProductsWithViews(productNum);

        //조회수 증가
        increaseViews(product, firstView);

        // 코드에 해당하는 명칭 조회
        String productStatusNm = commonService.selectCommonCodeNm(CommonCodeClassEnum.SELL_STATUS ,product.getProductStatusCd());

        //할인가격
        int discountPrice = calculateDiscountPrice(product, userDetails);

        return ProductDTO.builder()
                .productNum(product.getProductNum())
                .productNm(product.getProductNm())
                .productPrice(product.getProductPrice())
                .discountPrice(discountPrice)
                .remainQuantity(product.getRemainQuantity())
                .salesQuantity(product.getSalesQuantity())
                .views(product.getViews())
                .productStatusCd(product.getProductStatusCd())
                .productStatusNm(productStatusNm)
                .build();
    }

    private void increaseViews(Product product, boolean firstView) {
        if(firstView) {
            product.increaseView();//조회수 증가
            productDAO.updateProductViews(product);//조회수 업데이트
        }
    }

    private int calculateDiscountPrice(Product product, UserDetails userDetails) {
        if(userDetails != null) {
            User user = userDAO.selectUserById(userDetails.getUsername());
            int discountRate = userDAO.selectUserDiscountRate(user.getUserNum());
            return product.getDiscountPrice(discountRate);
        }
        return 0;
    }

    @Override
    public int editProduct(ProductEditDTO productEditDTO) {
        return productDAO.updateProduct(productEditDTO);
    }

    @Override
    public int oldSaveOrderProduct(ProductOrderDTO productOrderDTO, CustomUserDetails userDetails) {
        log.info("userDetails : {} ",userDetails);
        //주문 사용자
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productOrderDTO , Product.class);
        User user = userDetails.getUser();
        productOrderDTO.setUserNum(user.getUserNum());

        int result = 0;

        //주문테이블 insert
        result += productDAO.insertOrder(productOrderDTO);

        //주문 상품 테이블 insert
        result += productDAO.insertOrderedProduct(productOrderDTO);

        //상품 재고 update + 재고가 없다면 품절처리
        product.updateByOrdered(productOrderDTO.getOrderQuantity());
        result += productDAO.updateRemainQtyNStts(product);

        //사용자 보유 포인트 차감
        user.usePoints(productOrderDTO.getFinalPaymentPrice());
        result += userDAO.updateHoldingPoint(user);

        //포인트 이력테이블에 내역 insert
        PointHistory pointHistory = new PointHistory(user.getUserNum());
        pointHistory.reducePoint(productOrderDTO.getFinalPaymentPrice());
        result += userDAO.insertPointHistory(pointHistory);

        return result;
    }

    @Override
    public int saveOrderProduct(ProductOrderDTO productOrderDTO, CustomUserDetails userDetails) {
        log.info("userDetails : {} ",userDetails);
        //주문 사용자
        User user = userDetails.getUser();
        productOrderDTO.setUserNum(user.getUserNum());

        int result = 0;

        //주문테이블 insert
        result += productDAO.insertOrder(productOrderDTO);

        //주문 상품 테이블 insert
        result += productDAO.insertOrderedProduct(productOrderDTO);

        Product product = productDAO.selectProductsWithViews(productOrderDTO.getProductNum());
        //상품 재고 update + 재고가 없다면 품절처리
        product.updateByOrdered(productOrderDTO.getOrderQuantity());

        result += productDAO.updateRemainQtyNStts(product);

        //사용자 보유 포인트 차감
        user.usePoints(productOrderDTO.getFinalPaymentPrice());
        result += userDAO.updateHoldingPoint(user);

        //포인트 이력테이블에 내역 insert
        PointHistory pointHistory = new PointHistory(user.getUserNum());
        pointHistory.reducePoint(productOrderDTO.getFinalPaymentPrice());
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
        Product product = productDAO.selectProductsWithViews(productNum);

        //User의 등급으로 현재 적용된 할인율 가져오기
        Integer discountRate = discountPolicyDAO.selectDiscountRateByGrade(userDetails.getGradeCd());
        //현재 product 금액에서 할인율 적용하여 할인금액, 현재금액 구하기.
        Integer discountPrice = product.getDiscountPrice(discountRate);
        Integer currentPrice = product.getCurrentPrice(discountPrice);
        //판매수량 구해서 남은수량 구하기
        Integer remainQuantity = product.getRemainQuantity(productDAO.selectOrderedQty(productNum));

        return ProductOrderDTO.builder()
                .productNm(product.getProductNm())
                .productPrice(product.getProductPrice())
                .discountPrice(discountPrice)
                .remainQuantity(remainQuantity)
                .currentPrice(currentPrice)
                .build();
    }
}
