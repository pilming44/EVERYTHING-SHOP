package study.toy.everythingshop.enums;

public enum CommonCode {
    ROLE("COM1001"),
    ACCOUNT_STATUS("COM1002"),
    GRADE("COM1003"),
    SELL_STATUS("COM1004"),
    ORDER_STATUS("COM1005"),
    POINT_CHANGE_HISTORY("COM1006"),
    SELLER_APPLY_STATUS("COM1007");

    private String codeClass;

    CommonCode(String codeClass) {
        this.codeClass = codeClass;
    }
}
