package study.toy.everythingshop.enums;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum UserGradeCd {

    BLACK_HOLE("01"),
    GALAXY("02"),
    SUN("03"),
    EARTH("04"),
    MOON("05");

    private final String code;

    public String getCodeCd() {
        return code;
    }

}
