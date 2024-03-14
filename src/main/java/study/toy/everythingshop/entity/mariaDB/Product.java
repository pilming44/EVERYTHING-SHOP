package study.toy.everythingshop.entity.mariaDB;

import lombok.Getter;
import study.toy.everythingshop.enums.ProductStatus;

@Getter
public class Product {
    private Integer productNum;         //상품번호
    private User user;                  //사용자
    private String productNm;           //상품명
    private Integer registerQuantity;   //등록수량
    private Integer remainQuantity;     //남은수량
    private Integer productPrice;       //상품가격
    private String productStatusCd;     //상품상태코드[COM1004]
    private String postYn;              //게시여부(Y, N)
    private String registerDt;          //등록일자
    private String changeDt;            //수정일자
    private ProductViews productViews;

    public void getOrdered(Integer orderedQty){
        this.remainQuantity = remainQuantity - orderedQty;
        updateStts();
    }
    public void updateStts(){
        if(remainQuantity == 0){
            productStatusCd = ProductStatus.SOLD_OUT.getCode();
        }
    }
    public Integer getViews() {
        return productViews.getViews();
    }
    public void increaseView() {
        this.productViews.increaseView();
    }
    public Integer getDiscountPrice(int discountRate) {
        int discountPrice = (int) Math.round(this.productPrice * (discountRate / 100.0)) ;
        return (int) Math.ceil(discountPrice / 10.0) * 10; //1원단위 반올림
    }
    public Integer getSalesQuantity() {
        return this.registerQuantity - this.remainQuantity;
    }
}
