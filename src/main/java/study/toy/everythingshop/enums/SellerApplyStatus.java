package study.toy.everythingshop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellerApplyStatus {
    APPLY_COMPLETE("01", "신청완료"),
    APPROVAL("02", "승인"),
    REJECTION("03", "반려");

    private final String code;
    private final String description;
}
