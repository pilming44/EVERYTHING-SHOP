package study.toy.everythingshop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    SALE_READY("01", "판매 준비중"),
    ON_SALE("02", "판매중"),
    SOLD_OUT("03", "품절"),
    SALE_COMPLETED("04", "판매 종료");

    private final String code;
    private final String description;
}
