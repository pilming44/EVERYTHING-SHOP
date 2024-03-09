package study.toy.everythingshop.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonUtil {
    public static boolean strIsNotEmpty(String param) {
        return param != null && !param.equals("");
    }

    public static boolean isFirstProductView(Integer productNum, HttpServletRequest request, HttpServletResponse response) {
        ProductViewCookieManager cookieManager = new ProductViewCookieManager();
        return cookieManager.isFirstProductView(productNum, request, response);
    }
}
