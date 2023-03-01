package study.toy.everythingshop.entity;

import lombok.Data;

@Data
public class ProductMEntity {
    private Long productNum;
    private Long userNum;
    private String productName;
    private Long quantity;
    private Long price;
    private String productStts;
    private String registerDt;
    private String changeDt;
}
